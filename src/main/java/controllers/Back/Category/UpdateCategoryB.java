package controllers.Back.Category;
import entities.Category;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceCategory;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateCategoryB {

    @FXML
    private TextField txtProductSource;

    @FXML
    private TextField txtProductType;
    private Category category;


    public void initData(Category category) {
        this.category = category;
        if (category != null) {
            txtProductType.setText(category.getProduct_Type());
            txtProductSource.setText(category.getProduct_Source());
        }
    }
    @FXML
    private void updateCategory() {
        if (txtProductType.getText().isEmpty() || txtProductSource.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }
        if (category != null) {
            String newProductType = txtProductType.getText();
            String newProductSource = txtProductSource.getText();

            category.setProduct_Type(newProductType);
            category.setProduct_Source(newProductSource);

            try {
                ServiceCategory servicecat = new ServiceCategory();
                servicecat.update(category);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update");
                alert.setContentText("Category Updated");
                alert.show();
                //LoadPage();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } /*catch (IOException e) {
                throw new RuntimeException(e);
            }
            txtProductType.getScene().getWindow().hide();*/
        }
    }
   /* private void LoadPage() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Back/Category/DisplayCategory.fxml"));
        Parent root=loader.load();
        txtProductType.getScene().setRoot(root);
    }*/




}
