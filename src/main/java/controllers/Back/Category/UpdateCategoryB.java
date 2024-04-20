package controllers.Back.Category;
import entities.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
            txtProductSource.setText(category.getProduct_Source());
        }
    }
    @FXML
    private void updateCategory() {
        if (category != null) {
            String newProductType = txtProductType.getText();
            String newProductSource = txtProductSource.getText();

            category.setProduct_Type(newProductType);
            category.setProduct_Source(newProductSource);

            try {
                ServiceCategory servicecat = new ServiceCategory();
                servicecat.update(category);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            txtProductType.getScene().getWindow().hide();
        }
    }





}
