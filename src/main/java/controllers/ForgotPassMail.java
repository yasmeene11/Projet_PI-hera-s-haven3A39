package controllers;

import entities.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.mail.EmailException;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotPassMail {

    @FXML
    private Button btnsendresetcode;

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    void TestMailandSendingcode(ActionEvent event) {
        String email = txtUsername.getText().trim(); // Get the email from the text field
        if (email.isEmpty()) {
            showAlert("Email", "Please enter an email.");
            return;
        }

        try {
            ServiceUser u = new ServiceUser();
            boolean emailExists = u.emailExists(email);
            if (emailExists) {
                // Send the reset code via email
                String randomCode = u.generateRandomNumber(6);
                u.generateResetToken(email, randomCode);
                u.sendEmail(randomCode, email);

                // Start the session and set the current user's email
                Session session = Session.getInstance();
                session.setCurrentUserEmail(email);

                showAlert("Email sent", "The code has been sent successfully check your email !");
                // Navigate to the page where the user enters the verification code
                NavigateToEnterCode();
            } else {
                showAlert("Email Not Found", "The email " + email + " does not exist in the database.");
            }
        } catch (SQLException | EmailException e) {
            // Handle database exception or email sending exception
            e.printStackTrace();
            showAlert("Error", "An error occurred while accessing the database or sending email.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to display alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void NavigateToEnterCode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/EnterCodeReset.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnsendresetcode.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }




}


