package services;

import entities.Category;
import entities.Product;
import org.springframework.security.core.parameters.P;
import utils.MyBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduct implements IService<Product>{
    public static Connection con;
    public static Statement ste;


    public ServiceProduct(){
        con= MyBD.getInstance().getCon();
    }


    public void add(Product product) throws SQLException {
        String req = "INSERT INTO product (product_name, product_quantity, product_label, expiration_date, Category_Key, product_image)"+ "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, product.getProductName());
            pre.setInt(2, product.getProductQuantity());
            pre.setString(3, product.getProductLabel());
            pre.setDate(4, new java.sql.Date(product.getExpirationDate().getTime()));
            pre.setInt(5, product.getCategoryKey().getCategoryId());
            if (product.getProductImage() != null) {
                pre.setString(6, product.getProductImage());
            } else {
                pre.setString(6, "default_image.jpg");
            }

            pre.executeUpdate();
        }


    @Override
    public void update(Product product) throws SQLException {
        String req = "UPDATE product SET product_name=?, product_quantity=?, product_label=?, expiration_date=?, product_image=?, Category_Key=? WHERE productId=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, product.getProductName());
        pre.setInt(2, product.getProductQuantity());
        pre.setString(3, product.getProductLabel());
        pre.setDate(4, product.getExpirationDate());
       // pre.setString(5, product.getProductImage());
        Category category = product.getCategoryKey();
        pre.setInt(6, product.getCategoryKey().getCategoryId());
        pre.setInt(7, product.getProductId());
        if (product.getProductImage() != null) {
            pre.setString(5, product.getProductImage());
        } else {
            pre.setString(5, "default_image.jpg");
        }
        pre.executeUpdate();
    }


    @Override
    public void delete(Product product) throws SQLException {
      String req = "delete from product where productId=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,product.getProductId());
        pre.executeUpdate();
    }
    @Override
    public List<Product> Show() throws SQLException {
       List<Product> products = new ArrayList<>();
        String req = "SELECT * FROM product ";
        try (Statement ste = con.createStatement();
             ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                int id = res.getInt(1);
                String productname= res.getString("product_name");
                String productlabel= res.getString("product_label");
                int productquantity= res.getInt("product_quantity");
                Date expirationdate= res.getDate("expiration_date");
               int categoryId= res.getInt("Category_Key");
                Category categoryKey = fetchCategoryById(categoryId);
                Product p = new Product(id, productname,productlabel,productquantity,expirationdate,categoryKey);
                products.add(p);
            }
        }
        return products;   }
    public Category fetchCategoryById(int categoryId) throws SQLException {
        Category category = null;
        String query = "SELECT * FROM category WHERE categoryId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have an Animal constructor that takes parameters accordingly
                    category = new Category(
                            resultSet.getInt("categoryId"),
                            resultSet.getString("product_type"),
                            resultSet.getString("product_source")
                    );
                }
            }
        }
        return category;
    }

}
