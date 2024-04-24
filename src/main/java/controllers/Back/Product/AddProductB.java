package controllers.Back.Product;

import entities.Category;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import services.ServiceCategory;
import services.ServiceProduct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class AddProductB {

    @FXML
    private Button btnAdoption;

    @FXML
    private Button btnAnimal;

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnBoarding;

    @FXML
    private Button btnCash;

    @FXML
    private Button btnCategory;

    @FXML
    private Button btnDonation;

    @FXML
    private Button btnIndex;

    @FXML
    private Button btnProduct;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnaddproduct;

    @FXML
    private TextField ProductName;

    @FXML
    private TextField ProductLabel;

    @FXML
    private TextField ProductQuantity;
    @FXML
    private DatePicker ExpirationDate;
    @FXML
    private ComboBox<Category> CategoryKey;
    @FXML
    private TextField txtImage; // Assuming this is the TextField for displaying the image path

    private File selectedImageFile;
    private String imagefullpath;
    ServiceProduct ps = new ServiceProduct();
    @FXML
    private void NavigateToDisplayUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/User/DisplayUser.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnUser.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    private void NavigateToDisplayAnimal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAnimal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAnimal.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    private void NavigateToDisplayAdoption() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAdoption.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAdoption.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();
    }

    @FXML
    private void NavigateToIndexBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/indexBack.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnIndex.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();
    }

    @FXML
    private void NavigateToDisplayAppointment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayAppointment.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAppointment.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }


    @FXML
    public void NavigateToDisplayBoarding() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBoarding.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCashRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CashRegister/DisplayCashRegister.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCash.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCategory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/DisplayCategory.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayDonation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DisplayDonation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnDonation.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnProduct.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayReport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayReport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReport.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToAddProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/AddProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddproduct.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        selectedImageFile = fileChooser.showOpenDialog(new Stage());
        if (selectedImageFile != null && selectedImageFile.exists()) {
            String imageFileName = selectedImageFile.getName(); // Get just the file name
            String sourcePath = selectedImageFile.getAbsolutePath(); // Get the full path of the source image file
            String targetPath = "C:/Users/user/IdeaProjects/UnitedPets/UnitedPets/src/main/resources/product_images/" + imageFileName;
            try {
                Files.copy(selectedImageFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
                txtImage.setText(imageFileName); // Set the image file name in the text field
                imagefullpath = sourcePath; // Set the full path of the image file
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to copy the image file.");
                alert.showAndWait();
            }
        } else {
            // Handle case where selected file does not exist
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selected image file does not exist.");
            alert.showAndWait();
        }
    }
    @FXML
    public void initialize() {
        try {
            populateCategoryComboBox();
        } catch (SQLException e) {
            handleException("SQL Exception", e.getMessage());
        }
        ExpirationDate.setDayCellFactory(picker -> new DateCell() {
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
                try {
                    int newValue = Integer.parseInt(change.getControlNewText());
                    if (newValue >= 0) {
                        return change;
                    } else {
                        return null; // Reject negative values
                    }
                } catch (NumberFormatException e) {
                    return null; // Reject non-integer values
                }
            }
            return change;
        });

        ProductQuantity.setTextFormatter(quantityFormatter);

    }

    private void populateCategoryComboBox() throws SQLException {
        ServiceCategory sc = new ServiceCategory();
        List<Category> categories = sc.Show();
            CategoryKey.setItems(FXCollections.observableArrayList(categories));
            CategoryKey.setConverter(new StringConverter<Category>() {
                @Override
                public String toString(Category object) {
                    return object.getProduct_Type();
                }

                @Override
                public Category fromString(String string) {
                    return null;
                }
            });

    }

    @FXML
    void AddProduct(ActionEvent event) {
        if (ProductName.getText().isEmpty() || ProductLabel.getText().isEmpty() || ProductQuantity.getText().isEmpty() || ExpirationDate.getValue() == null || CategoryKey.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }
        try {
            LocalDate localDate = ExpirationDate.getValue();
            Date expirationDate = Date.valueOf(localDate);
            String ImageFileName = txtImage.getText();
            Category selectedCategory = CategoryKey.getValue();

            ps.add(new Product(ProductName.getText(), ProductLabel.getText(), Integer.parseInt(ProductQuantity.getText()), expirationDate, ImageFileName,selectedCategory));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add");
            alert.setContentText("Product Added");
            alert.show();
            LoadPage();

        } catch (SQLException e) {
            handleException("SQL Exception", e.getMessage());
        } catch (IOException e) {
            handleException("IO Exception", e.getMessage());
        } catch (NullPointerException e) {
            // Handle case when no date is selected
            handleException("Date Not Selected", "Please select a valid expiration date.");
        }
    }
    void handleException(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void NextPage(ActionEvent event) throws IOException {
        LoadPage();
    }


    public void LoadPage() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Back/Product/DisplayProduct.fxml"));
        Parent root=loader.load();
        ProductName.getScene().setRoot(root);

    }

}
