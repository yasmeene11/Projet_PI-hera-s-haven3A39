package controllers.Front.DonationP;

import entities.DonationP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceDonationP;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AddDonationPF {
    @FXML
    private MenuItem btnanimal;

    @FXML
    private MenuItem btnboarding;

    @FXML
    private MenuItem btndonationm;

    @FXML
    private MenuItem btndonationp;

    @FXML
    private MenuItem btnproduct;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem btnindexb;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtProductQuantity;
    @FXML
    private TextField txtProductLabel;
    @FXML
    private ComboBox<String> cmbProductType;
    @FXML
    private DatePicker datePickerDonationP;
    @FXML
    private DatePicker datePickerExpirationDate;
    private final ServiceDonationP serviceDonationP;
    public AddDonationPF() {
        this.serviceDonationP = new ServiceDonationP(); // Initialize the ServiceUser
    }

    private boolean validateQuantity() {
        try {
            int quantity = Integer.parseInt(txtProductQuantity.getText());
            if (quantity < 1) {
                showAlert("Weak Quantity", "You should donate at least one product!");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showAlert("Quantity Error", "The quantity should be a number!");
            return false;
        }
    }

    private boolean validateDonationDate() {
        LocalDate selectedDate = datePickerDonationP.getValue();
        if (selectedDate == null) {
            showAlert("Date Error", "Please select a donation date!");
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        if (selectedDate.isAfter(currentDate)) {
            showAlert("Date Error", "The date cannot be later than the current date.");
            return false;
        }

        if (selectedDate.isBefore(currentDate)) {
            showAlert("Date Error", "The date cannot be less than the current date.");
            return false;
        }

        return true;
    }

    private boolean validateExpirationDate() {
        LocalDate expirationDate = datePickerExpirationDate.getValue();
        LocalDate minExpirationDate = LocalDate.now().plusWeeks(2);

        if (expirationDate == null || expirationDate.isBefore(minExpirationDate)) {
            showAlert("Date Error", "The expiration date should be at least two weeks from now.");
            return false;
        }

        return true;
    }

    private boolean validateProductName() {
        String productName = txtProductName.getText().trim();
        if (!productName.matches("[a-zA-Z]+")) {
            showAlert("Product Name Error", "Product name should contain only letters.");
            return false;
        }
        return true;
    }

    private boolean validateProductLabel() {
        String productLabel = txtProductLabel.getText().trim();
        if (!productLabel.matches("[a-zA-Z]+")) {
            showAlert("Product Label Error", "Product label should contain only letters.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private boolean validateFields() {
        if (txtProductLabel.getText().isEmpty() ||
                datePickerDonationP.getValue() == null ||
                datePickerExpirationDate.getValue() == null ||
                cmbProductType.getValue() == null ||
                txtProductName.getText().isEmpty() ||
                txtProductQuantity.getText().isEmpty()) {
            showAlert("Empty Fields", "Please complete all fields!");
            return false;
        }

        return validateQuantity() &&
                validateDonationDate() &&
                validateExpirationDate() &&
                validateProductName() &&
                validateProductLabel();
    }

    @FXML
    public void addDonationPF() {
        if(validateFields())
        {
            String productName= txtProductName.getText();
            String productLabel= txtProductLabel.getText();
            String productType= cmbProductType.getValue().toString();
            int productQuantity= Integer.parseInt(txtProductQuantity.getText());

            LocalDate donationPDate= datePickerDonationP.getValue();
            Date donationPDatee= donationPDate!= null ? Date.valueOf(donationPDate) : null;

            LocalDate donationPExpirationDate= datePickerExpirationDate.getValue();
            Date donationPExpirationDatee= donationPExpirationDate!= null ? Date.valueOf(donationPExpirationDate) : null;
            DonationP donationP = new DonationP(productName,productQuantity, productLabel,donationPExpirationDatee,donationPDatee,productType,2);

            try {
                // Call the add method in ServiceAnimal to add the animal
                serviceDonationP.add(donationP);

                // Display a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Donation added successfully");
                alert.setHeaderText(null);
                alert.setContentText("Donation added successfully!");
                alert.showAndWait();

            } catch (Exception e) {
                // Display an error message if adding fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add failed");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add donation");
                alert.showAndWait();
                e.printStackTrace();
            }}
    }

    @FXML
    public void NavigateToIndexBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/indexFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnindexb.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
    @FXML
    public void NavigateToDisplayAnimal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/DisplayAnimal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnanimal.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToAddBoarding() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/AddBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnboarding.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnproduct.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationM() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationM/AddDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationm.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationP() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationP/AddDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationp.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
}
