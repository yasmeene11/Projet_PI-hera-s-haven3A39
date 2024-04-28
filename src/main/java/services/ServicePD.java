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
import utils.MyBD;
import java.sql.*;
public class ServicePD implements IService<ProductDonation> {
    public static Connection con;
    public static Statement ste;
    public ServicePD(){
        con= MyBD.getInstance().getCon();
    }
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
        String query = "SELECT COUNT(*) FROM product WHERE productId = ?";
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
        String req = "SELECT * FROM donation_product";
         ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
            while (res.next()) {
                int dpId = res.getInt("dpId");
                int donationKey = res.getInt("Donation_Key");
                int productKey = res.getInt("Product_Key");

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
        String query = "SELECT * FROM donation_p WHERE donationPId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, donationKey);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            // Assuming DonationP constructor takes necessary parameters
            return new DonationP(rs.getString("donation_product_name"), rs.getInt("donation_product_quantity"),
                    rs.getString("donation_product_label"), rs.getDate("donation_product_expiration_date"),
                    rs.getDate("donation_p_date"), rs.getString("donation_p_type"));
        } else {
            return null;
        }
    }

    private Product fetchProduct(int productKey) throws SQLException {
        String query = "SELECT * FROM product WHERE productId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, productKey);
        ResultSet rs = stmt.executeQuery();

        // Check if the ResultSet has any rows
        if (rs.next()) {
            // Fetch category for the product
            ServiceProduct p = new ServiceProduct();
            Category category = p.fetchCategoryById(rs.getInt("Category_Key"));

            return new Product(rs.getInt("productId"), rs.getString("product_name"), rs.getString("product_label"),
                    rs.getInt("product_quantity"), rs.getDate("expiration_date"), rs.getString("product_image"), category);
        } else {
            // Handle case where product is not found (return null, throw exception, etc.)
            return null;
        }

    }

    public int getDonationCount(int productId) {
        try {
            String sql = "SELECT COUNT(*) AS donationCount FROM donation_product WHERE Product_Key = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, productId);

            // Execute the query and retrieve the donation count
            ResultSet resultSet = statement.executeQuery();
            int donationCount = 0;
            if (resultSet.next()) {
                donationCount = resultSet.getInt("donationCount");
            }
            resultSet.close();
            statement.close();

            return donationCount;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

