package controllers.Back.DonationP;

import entities.DonationP;
import entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import services.ServiceDonationM;
import services.ServiceDonationP;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AddDonationPB {

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
    private Button btnadddonationp;

    @FXML
    private Button btnconfirmadd;

    @FXML
    private Button btnlistdonationp;
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
    @FXML
    private ComboBox<User> accountComboBox;

    @FXML
    public void initialize() {
        try {
            ServiceDonationM donationMService = new ServiceDonationM();
            List<User> accounts = donationMService.getAllAccounts();
            accountComboBox.setItems(FXCollections.observableArrayList(accounts));
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs
        }
    }
    public AddDonationPB() {
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
                accountComboBox.getValue() == null ||
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
    public void addDonationPB() {
        if(validateFields())
        {
        String productName= txtProductName.getText();
        String productLabel= txtProductLabel.getText();
        String productType= cmbProductType.getValue().toString();
        int accountId = accountComboBox.getValue().getAccountId();
        int productQuantity= Integer.parseInt(txtProductQuantity.getText());

        LocalDate donationPDate= datePickerDonationP.getValue();
        Date donationPDatee= donationPDate!= null ? Date.valueOf(donationPDate) : null;

        LocalDate donationPExpirationDate= datePickerExpirationDate.getValue();
        Date donationPExpirationDatee= donationPExpirationDate!= null ? Date.valueOf(donationPExpirationDate) : null;
        DonationP donationP = new DonationP(productName,productQuantity, productLabel,donationPExpirationDatee,donationPDatee,productType,accountId);

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
    public void NavigateToDisplayBoarding()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBoarding.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCashRegister()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CashRegister/DisplayCashRegister.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCash.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCategory()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/DisplayCategory.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayDonation()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DisplayDonation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnDonation.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public  void NavigateToDisplayProduct()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnProduct.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayReport()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayReport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReport.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayDonationP()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationP/DisplayDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnlistdonationp.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToAddDonationP()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationP/AddDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnadddonationp.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
}
