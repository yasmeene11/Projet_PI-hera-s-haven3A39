package services;

import entities.*;
import org.springframework.security.core.parameters.P;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static services.ServiceProduct.con;

public class ServicePD implements IService<ProductDonation> {

    @Override
    public void add(ProductDonation productDonation) throws SQLException {
        String req = "INSERT INTO donation_product (Donation_Key, Product_Key) " +
                "VALUES ( ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        DonationP donation = productDonation.getDonation_Key();
        Product product = productDonation.getProduct_Key();

        // Check if the donation key and product key exist in their respective tables
        if (!isDonationValid(donation) || !isProductValid(product)) {
            // Handle invalid donation or product (e.g., throw an exception or log an error)
            throw new IllegalArgumentException("Invalid donation or product");
        }

        pre.setObject(1, donation.getDonationPId());
        pre.setObject(2, product.getProductId());

        pre.executeUpdate();
    }

    private boolean isDonationValid(DonationP donation) throws SQLException {
        String query = "SELECT COUNT(*) FROM donation_p WHERE donationPId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, donation.getDonationPId());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }

    private boolean isProductValid(Product product) throws SQLException {
        String query = "SELECT COUNT(*) FROM product WHERE donationPId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, product.getProductId());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }

    @Override
    public void update(ProductDonation productDonation) throws SQLException {

    }

    @Override
    public void delete(ProductDonation productDonation) throws SQLException {

    }

    @Override
    public List<ProductDonation> Show() throws SQLException {
        List<ProductDonation> pd = new ArrayList<>();
        String req = "SELECT dp.dpId, dp.donation_key, dp.product_key " +
                "FROM donation_product dp";
        Statement ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            int dpId = res.getInt(1);
            int donationKey = res.getInt(2);
            int productKey = res.getInt(3);

            // Fetch DonationP object from database using donationKey
            DonationP donation = fetchDonation(donationKey);
            // Fetch Product object from database using productKey
            Product product = fetchProduct(productKey);

            // Assuming ProductDonation constructor takes dpId, DonationP, and Product as parameters
            ProductDonation productDonation = new ProductDonation(dpId, donation, product);
            pd.add(productDonation);
        }
        return pd;
    }

    private DonationP fetchDonation(int donationKey) throws SQLException {
        // Implement logic to fetch DonationP object from database using donationKey
        // Example:
        String query = "SELECT * FROM donation_p WHERE donationPId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, donationKey);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            // Assuming DonationP constructor takes necessary parameters
           return new DonationP();
        } else {
            // Handle case where donation is not found (return null, throw exception, etc.)
            return null;
        }
    }

    private Product fetchProduct(int productKey) throws SQLException {
        // Implement logic to fetch Product object from database using productKey
        // Example:
        String query = "SELECT * FROM product WHERE productId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, productKey);
        ResultSet rs = stmt.executeQuery();
        ServiceProduct p= new ServiceProduct();
        Category category = p.fetchCategoryById(rs.getInt("Category_Key"));
        if (rs.next()) {
           return new Product(rs.getInt("productId"),
                   rs.getString("product_name"),
                   rs.getString("product_label"),
                   rs.getInt("product_quantity"),
                   rs.getDate("expiration_date"),
                   rs.getString("product_image"),
                   category);
        } else {
            // Handle case where product is not found (return null, throw exception, etc.)
            return null;
        }

    }
}
