package controllers.Front.DonationM;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import entities.DonationM;
import entities.PaymentDialog;
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

private ServiceDonationM serviceDonationM;
public AddDonationMF(){
    serviceDonationM=new ServiceDonationM();
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
                PaymentDialog paymentDialog = new PaymentDialog();
                paymentDialog.showAndWait().ifPresent(paymentInfo -> {
                    // Appeler la méthode processPayment avec les informations de paiement
                    processPayment(donationM, paymentInfo);
                });

            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer les erreurs liées à la base de données
                // Vous pouvez afficher un message d'erreur à l'utilisateur ici
            }}
    }
    private void processPayment(DonationM donationM,String paymentInfo) {
        try {
// Set your secret key here
            Stripe.apiKey = "sk_test_51OpuslIobZsNpOWZGRgL1kyHnKIfMNRKKWk3QgZuP2Ry48sU5UOlPGZnp8dFMJlkfoYvcDGwQxvpK0qDWgydmt6h00pIUO7trA";
            String userName= serviceDonationM.getDonorNameById(donationM.getAccountKey());

// Create a PaymentIntent with other payment details
            int roundedAmount = (int) Math.floor(donationM.getDonationAmount());
            System.out.println(roundedAmount);
            String amount= String.valueOf(roundedAmount);
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(Long.parseLong(amount)) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .putMetadata("user_name", userName)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

// If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
// If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
