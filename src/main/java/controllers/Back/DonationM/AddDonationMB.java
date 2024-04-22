package controllers.Back.DonationM;


import entities.DonationM;
import entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceDonationM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class AddDonationMB {

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
    private Button btnadddonationm;

    @FXML
    private Button btnconfirmadd;

    @FXML
    private Button btnlistdonationm;
    @FXML
    private TextField donationAmount;

    @FXML
    private DatePicker donationDate;

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

    private boolean validateAmount() {
        try {
            float amount = Float.parseFloat(donationAmount.getText());
            if (amount < 10) {
                showAlert("Weak Amount", "You should donate at least 10dt!");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showAlert("Amount Error", "The amount should be a number!");
            return false;
        }
    }

    private boolean validateDate() {
        try {
            LocalDate selectedDate = donationDate.getValue();
            LocalDate currentDate = LocalDate.now();
            if (selectedDate == null || selectedDate.isAfter(currentDate)) {
                showAlert("Date Error", "The date cannot be later than the current date.");
                return false;
            }
            if (selectedDate.isBefore(currentDate)) {
                showAlert("Date Error", "The date cannot be less than the current date.");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            showAlert("Date Error", "You must select a valid date!");
            return false;
        }
    }

    private boolean validateFields() {
        return validateEmptyFields() && validateAmount() && validateDate() ;
    }

    private boolean validateEmptyFields() {
        if (donationAmount.getText().isEmpty() || donationDate.getValue() == null || accountComboBox.getValue() == null) {
            showAlert("Empty Fields", "Please complete all fields!");
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

    @FXML
    private void addDonationM() {

        if (validateFields())
        {// Récupérer les valeurs des champs de texte et de la date
        float amount = Float.parseFloat(donationAmount.getText());
        Date date = java.sql.Date.valueOf(donationDate.getValue());
        int accountId = accountComboBox.getValue().getAccountId();

        // Créer un objet DonationM avec les valeurs récupérées
        DonationM donationM = new DonationM();
        donationM.setDonationAmount(amount);
        donationM.setDonationMDate(date);
        donationM.setAccountKey(accountId);

        // Appeler la méthode add() pour insérer la donation dans la base de données
        try {
            ServiceDonationM donationMController = new ServiceDonationM();
            donationMController.add(donationM);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Donation Added");
            alert.setHeaderText(null);
            alert.setContentText("Donation added successfully!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs liées à la base de données
            // Vous pouvez afficher un message d'erreur à l'utilisateur ici
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
    public void NavigateToDisplayDonationM()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationM/DisplayDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnlistdonationm.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
    @FXML
    public void NavigateToAddDonationM()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationM/AddDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnadddonationm.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
}
