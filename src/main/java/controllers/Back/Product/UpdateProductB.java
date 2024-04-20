package controllers.Back.Product;
import entities.Category;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import services.ServiceCategory;
import services.ServiceProduct;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UpdateProductB {

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtProductLabel;
    @FXML
    private TextField txtProductQuantity;
    @FXML
    private DatePicker txtExpirationDate;
    @FXML
    private ComboBox<Category> txtCategory;

    private Product product;
    private void populateCategoryComboBox() throws SQLException {
        ServiceCategory sc = new ServiceCategory();
        List<Category> categories = sc.Show();
        System.out.println(categories);
        for (Category category: categories){
            txtCategory.getItems().add(category);}

    }
    @FXML
    public void initialize() {
        try {
            populateCategoryComboBox();
        } catch (SQLException e) {
            handleException("SQL Exception", e.getMessage());
        }
        txtExpirationDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                // Vérifier si la date est dans le passé
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Colorier en rose les dates passées
                }

                // Vérifier si la date est dans les deux semaines à venir
                if (date.isBefore(LocalDate.now().plusWeeks(2))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ff7f50;"); // Colorier en orange les dates à moins de deux semaines
                }
            }
        });
        TextFormatter<Integer> quantityFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
            if (change.isContentChange()) {
                // Vérifier si la nouvelle valeur peut être convertie en un nombre
                try {
                    Integer.parseInt(change.getControlNewText());
                    return change;
                } catch (NumberFormatException e) {
                    // Ne pas accepter la modification si la nouvelle valeur n'est pas un nombre
                    return null;
                }
            }
            return change;
        });

        txtProductQuantity.setTextFormatter(quantityFormatter);
    }

    void handleException(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML

    public void initData(Product product) {
        this.product = product;
        if (product != null) {
            txtProductName.setText(product.getProductName());
            txtProductLabel.setText(product.getProductLabel());
            txtProductQuantity.setText(String.valueOf(product.getProductQuantity()));
            txtExpirationDate.setValue(product.getExpirationDate().toLocalDate());

            Category category = product.getCategoryKey();

            // Check if category is already in the ComboBox's items list
            if (!txtCategory.getItems().contains(category)) {
                // If not, add it to the ComboBox's items list
                txtCategory.getItems().add(category);
                System.out.println("Category added to ComboBox's items list.");
            }

            // Set the ComboBox's value to the category
            txtCategory.setValue(category);
        }
    }

    @FXML
    private void updateProduct() {
        if (product != null) {
            String newProductName = txtProductName.getText();
            String newProductLabel = txtProductLabel.getText();
            int newProductQuantity = Integer.parseInt(txtProductQuantity.getText());
            java.sql.Date newExpirationDate = java.sql.Date.valueOf(txtExpirationDate.getValue());
            Category newCategory = txtCategory.getValue();

            product.setProductName(newProductName);
            product.setProductLabel(newProductLabel);
            product.setProductQuantity(newProductQuantity);
            product.setExpirationDate(newExpirationDate);
            product.setCategoryKey(newCategory);

            try {
                // Update the product in the database
                ServiceProduct serviceprod = new ServiceProduct();
                serviceprod.update(product);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the current window
            txtProductName.getScene().getWindow().hide();
        }
    }





}
