package services;
import entities.Animal;
import entities.Product;
import utils.MyBD;
import entities.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ServiceCategory implements IService<Category>{
    public Connection con;
    public Statement ste;


    public ServiceCategory(){
        con= MyBD.getInstance().getCon();
    }

    @Override
    public void add(Category category) throws SQLException {
        String req = "INSERT INTO category (product_type, product_source) " +
                "VALUES ( ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, category.getProduct_Type());
        pre.setString(2, category.getProduct_Source());

        pre.executeUpdate();
    }
    @Override
    public void update(Category category) throws SQLException {
        String req = "update category set product_type=?,product_source=? where categoryId=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,category.getProduct_Type());
        pre.setString(2,category.getProduct_Source());
        pre.setInt(3,category.getCategoryId());
        pre.executeUpdate();
    }

    @Override
    public void delete(Category category) throws SQLException {
        String req = "delete from category where categoryId=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, category.getCategoryId());
        pre.executeUpdate();
    }

    @Override
    public List<Category> Show() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String req = "select * from category";
        Statement ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        while (res.next()){
            int categoryId = res.getInt(1);
            String Product_Type =res.getString("product_type");
            String Product_Source =res.getString("product_source");

            Category category = new Category(categoryId,Product_Type,Product_Source);
            categories.add(category);
        }
        return categories;
    }
    public void deleteProductsByCategory(Category category) throws SQLException {
        String deleteProductsQuery = "DELETE FROM product WHERE Category_Key=?";
        try (PreparedStatement deleteProductsStatement = con.prepareStatement(deleteProductsQuery)) {
            deleteProductsStatement.setInt(1, category.getCategoryId());
            deleteProductsStatement.executeUpdate();
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
            // Optionally, throw an exception or handle the error gracefully
        }
    }
}
