package controllers.Back.Category;
import entities.Animal;
import entities.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceAnimal;
import services.ServiceCategory;

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

            // Convert int Age to String
            txtProductSource.setText(category.getProduct_Source());



        }
    }



    @FXML
    private void updateCategory() {
        if (category != null) {
            // Retrieve updated data from input fields
            String newProductType = txtProductType.getText();
            String newProductSource = txtProductSource.getText(); // Cast to String


            // Update animal object
            category.setProduct_Type(newProductType);
            category.setProduct_Source(newProductSource);


            // Perform the update operation (e.g., call a service method)
            try {
                ServiceCategory servicecat = new ServiceCategory(); // Rename the variable to avoid conflict
                servicecat.update(category);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the update stage (optional)
            txtProductType.getScene().getWindow().hide();
        }
    }





}
