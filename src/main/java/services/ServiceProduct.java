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
        String sql = "INSERT INTO product (product_name, product_quantity, product_label, expiration_date, Category_Key, product_image) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getProductQuantity());
            statement.setString(3, product.getProductLabel());
            statement.setDate(4, new java.sql.Date(product.getExpirationDate().getTime()));
            statement.setInt(5, product.getCategoryKey().getCategoryId());

            if (product.getProductImage() != null) {
                statement.setString(6, product.getProductImage());
            } else {
                statement.setString(6, "default_image.jpg");
            }

            statement.executeUpdate();
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        String req = "update product set product_name=?,product_quantity=?,product_label=?,expiration_date=?,product_image=?,Category_Key=?, where productId=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,Product.getProductName());
        pre.setInt(2,Product.getProductQuantity());
        pre.setString(3,Product.getProductLabel());
        pre.setDate(5,Product.getExpirationDate());
        pre.setString(6,Product.getProductImage());
        Category category = Product.getCategoryKey();
        pre.setInt(7, Product.getCategoryKey().getCategoryId());
        pre.setInt(8,Product.getProductId());
        pre.executeUpdate();
    }

    @Override
    public void delete(Product product) throws SQLException {
        String req = "delete from product where productId=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,Product.getProductId());
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
               int categoryKey= res.getInt("Category_Key");
                Category category=Product.getCategoryKey();
                Product p = new Product(id, productname,productlabel,productquantity,expirationdate,category);
                products.add(p);
            }
        }
        return products;   }


}
