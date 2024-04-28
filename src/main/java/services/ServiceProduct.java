package services;

import entities.Category;
import entities.Product;
import org.springframework.security.core.parameters.P;
import utils.MyBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class ServiceProduct implements IService<Product>{
    public static Connection con;
    public static Statement ste;


    public ServiceProduct(){
        con= MyBD.getInstance().getCon();
    }


    public void add(Product product) throws SQLException {
        String req = "INSERT INTO product (product_name, product_quantity, product_label, expiration_date, product_image,rating, Category_Key)"+ "VALUES (?, ?, ?, ?, ?, ?,?)";

        PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, product.getProductName());
            pre.setInt(2, product.getProductQuantity());
            pre.setString(3, product.getProductLabel());
            pre.setDate(4, new java.sql.Date(product.getExpirationDate().getTime()));
            pre.setString(5, product.getProductImage());
            pre.setDouble(6,product.getRating());
            pre.setInt(7, product.getCategoryKey().getCategoryId());
            if (product.getProductImage() != null) {
                pre.setString(5, product.getProductImage());
            } else {
                pre.setString(5, "default_image.jpg");
            }

            pre.executeUpdate();
        }


    @Override
    public void update(Product product) throws SQLException {
        String req = "UPDATE product SET product_name=?, product_quantity=?, product_label=?, expiration_date=?, product_image=?,rating=?, Category_Key=? WHERE productId=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, product.getProductName());
        pre.setInt(2, product.getProductQuantity());
        pre.setString(3, product.getProductLabel());
        pre.setDate(4, product.getExpirationDate());
       pre.setString(5, product.getProductImage());
       pre.setDouble(6,product.getRating());
        Category category = product.getCategoryKey();
        pre.setInt(7, product.getCategoryKey().getCategoryId());
        pre.setInt(8, product.getProductId());
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
                String productImage=res.getString("product_image");
                Double rating= res.getDouble("rating");
               int categoryId= res.getInt("Category_Key");
                Category categoryKey = fetchCategoryById(categoryId);
                Product p = new Product(id, productname,productlabel,productquantity,expirationdate,productImage,rating, categoryKey);
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
    public List<Product> filterByCategory(String category) throws SQLException {
        // Implementation to retrieve products filtered by category from the database
        List<Product> filteredProducts = new ArrayList<>();
        // Example implementation: filtering products from a list based on category
        List<Product> allProducts = Show(); // Get all products
        for (Product product : allProducts) {
            if (product.getCategoryKey().getProduct_Type().equals(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public void updateProductRating(int productId, int newRating) throws SQLException {
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        try {
            // Select the existing rating
            String selectSql = "SELECT rating FROM product WHERE ProductId = ?";
            selectStatement = con.prepareStatement(selectSql);
            selectStatement.setInt(1, productId);
            ResultSet resultSet = selectStatement.executeQuery();

            double currentRating = 0;
            if (resultSet.next()) {
                currentRating = resultSet.getDouble("Rating");
            }

            // Update the product with the new rating
            double newRatingValue = newRating;
            if (currentRating != 0) {
                // If there's already a rating, calculate the average
                newRatingValue = (currentRating + newRating) / 2.0;
            }

            String updateSql = "UPDATE product SET rating = ? WHERE ProductId = ?";
            updateStatement = con.prepareStatement(updateSql);
            updateStatement.setDouble(1, newRatingValue);
            updateStatement.setInt(2, productId);
            updateStatement.executeUpdate();
        } finally {
            // Close resources
            if (selectStatement != null) {
                selectStatement.close();
            }
            if (updateStatement != null) {
                updateStatement.close();
            }
        }
    }
}
