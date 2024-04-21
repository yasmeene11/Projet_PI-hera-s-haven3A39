package controllers.Front.DonationM;

import entities.DonationM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceDonationM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class AddDonationMF {
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
    private TextField donationAmount;

    @FXML
    private DatePicker donationDate;
    @FXML
    private MenuItem btnindexb;

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
        if (donationAmount.getText().isEmpty() || donationDate.getValue() == null ) {
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

            // Créer un objet DonationM avec les valeurs récupérées
            DonationM donationM = new DonationM();
            donationM.setDonationAmount(amount);
            donationM.setDonationMDate(date);
            donationM.setAccountKey(1);

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
