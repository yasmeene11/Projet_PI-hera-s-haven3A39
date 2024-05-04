package controllers.Front.DonationM;

import com.stripe.model.PaymentMethod;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import entities.DonationM;
import entities.GoogleCalendarIntegration;
import entities.Payment;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import entities.PaymentDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceDonationM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AddDonationMF  {
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
    private MenuItem btnShowHistory;

    @FXML
    private MenuBar menuBar;
    @FXML
    private TextField donationAmount;

    @FXML
    private DatePicker donationDate;
    @FXML
    private MenuItem btnindexb;

    private ServiceDonationM serviceDonationM;

    public AddDonationMF() {
        serviceDonationM = new ServiceDonationM();
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
        return validateEmptyFields() && validateAmount() && validateDate();
    }

    private boolean validateEmptyFields() {
        if (donationAmount.getText().isEmpty() || donationDate.getValue() == null) {
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

        if (validateFields()) {
            float amount = Float.parseFloat(donationAmount.getText());
            Date date = java.sql.Date.valueOf(donationDate.getValue());

            DonationM donationM = new DonationM();
            donationM.setDonationAmount(amount);
            donationM.setDonationMDate(date);
            donationM.setAccountKey(1);

            try {
                ServiceDonationM donationMController = new ServiceDonationM();
                donationMController.add(donationM);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Donation Added");
                alert.setHeaderText(null);
                alert.setContentText("Donation added successfully!");
                alert.showAndWait();

                showChoiceDialog(donationM);

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void showChoiceDialog(DonationM donationM) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Choix de l'action");
        alert.setContentText("Voulez-vous ajouter l'événement à Google Calendar ou effectuer un paiement?");

        ButtonType buttonTypeCalendar = new ButtonType("Ajouter à Google Calendar");
        ButtonType buttonTypePayment = new ButtonType("Effectuer un paiement");
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeCalendar, buttonTypePayment, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeCalendar) {
            addEventToGoogleCalendar();
        } else if (result.get() == buttonTypePayment) {
            processPayment(donationM);
        }
    }

    private void addEventToGoogleCalendar() {
        // Code pour ouvrir la fenêtre de connexion Google et ajouter un événement
        try {
            GoogleCalendarIntegration.addEvent();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ajouter l'événement au calendrier : " + e.getMessage());
        }
    }
    private void processPayment(DonationM donationM) throws SQLException {

// Set your secret key here
            Stripe.apiKey = "sk_test_51OpuslIobZsNpOWZGRgL1kyHnKIfMNRKKWk3QgZuP2Ry48sU5UOlPGZnp8dFMJlkfoYvcDGwQxvpK0qDWgydmt6h00pIUO7trA";
            //String userName = serviceDonationM.getDonorNameById(donationM.getAccountKey());

// Create a PaymentIntent with other payment details
            int roundedAmount = (int) Math.floor(donationM.getDonationAmount());
            System.out.println(roundedAmount);
            String amount = String.valueOf(roundedAmount);
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://your-domain.com/success")
                    .setCancelUrl("https://your-domain.com/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount((long) roundedAmount)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Donation") // You can customize this to represent what the donation is for
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD) // Specify payment method types

                    .build();

           try{ Session session = Session.create(params);
            openStripePaymentWebView(session.getUrl());




//            PaymentMethodCreateParams.CardDetails cardDetails = PaymentMethodCreateParams.CardDetails.builder()
////                    .setCvc("0000")
////                    .setExpYear(Long.valueOf(12042026))
////                    .setNumber("14774161").setExpMonth(Long.valueOf(12)).build();
////
////            PaymentMethodCreateParams paymentMethodCreateParams = PaymentMethodCreateParams.builder().setCard(cardDetails).build();
////            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
////                    .setAmount(Long.parseLong(amount)) // Amount in cents (e.g., $10.00)
////                    .setCurrency("usd")
////                    .putMetadata("user_name", userName)
////                    .setPaymentMethod(paymentMethodCreateParams.getPaymentMethod())
////                    .build();
////
////            PaymentIntent intent = PaymentIntent.create(params);

// If the payment was successful, display a success message
            //System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
// If there was an error processing the payment, display the error message
           System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }

    private void openStripePaymentWebView(String url) {
        try {
            Stage stage = new Stage();
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load(url);

            StackPane root = new StackPane();
            root.getChildren().add(webView);

            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Stripe Payment Form");
            stage.initModality(Modality.APPLICATION_MODAL); // Rend la fenêtre modale par rapport à sa fenêtre parente
            stage.setMinWidth(500);
            stage.setMinHeight(400);
            stage.centerOnScreen(); // Centre la fenêtre sur l'écran
            stage.show();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre de paiement : " + e.getMessage());
        }
    }

   /* private void processPayment(DonationM donationM, String cardNumber, int expMonth, int expYear, String cvc) {
        try {
            // Initialize your Stripe API key
            //Stripe.apiKey = "sk_test_your_stripe_api_key";

            // Create a payment token (assuming your Payment class does this correctly)
            Payment paymentProcessor = new Payment();
            String token = paymentProcessor.createPaymentToken(cardNumber, expMonth, expYear, cvc);

            // Create a payment method using the token
            PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
                    .setType(PaymentMethodCreateParams.Type.CARD)
                    .setCard(new PaymentMethodCreateParams.Card(token)) // Correctly use the token
                    .build();
            PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

            // Convert the donation amount to cents and create a payment intent
            int roundedAmount = (int) Math.floor(donationM.getDonationAmount() * 100); // Convert to cents
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(Long.valueOf(roundedAmount))
                    .setCurrency("usd")
                    .setPaymentMethod(paymentMethod.getId())
                    .addPaymentMethodType("card")
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Check if the payment was successful
            if ("succeeded".equals(paymentIntent.getStatus())) {
                // Payment was successful
                serviceDonationM.update(donationM); // Update the donation status
                showAlert("Payment Successful", "Your donation has been processed successfully.");
            } else {
                // Payment was not successful
                showAlert("Payment Failed", "There was an error processing your payment. Please try again.");
            }
        } catch (StripeException | SQLException e) {
            showAlert("Error", "An error occurred while processing your payment: " + e.getMessage());
            e.printStackTrace();
        }
    }
*/

    //historique des donations
    @FXML
    private void showDonationHistory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationM/HistoryDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnShowHistory.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
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
