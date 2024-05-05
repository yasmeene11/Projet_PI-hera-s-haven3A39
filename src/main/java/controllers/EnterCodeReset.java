package controllers;

import entities.User;
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
import services.ServiceUser;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
import java.sql.SQLException;
import java.util.Date;
import  entities.Session;
import java.sql.Timestamp;

public class EnterCodeReset {

    @FXML
    private Button btnConfirmVerif;

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtCodeVerif;

    @FXML
    void TestMailandSendingcode(ActionEvent event) {

    }


    @FXML
    void buttonverifclicked() {
        String resetCode = txtCodeVerif.getText().trim(); // Get the reset code from the text field

        // Retrieve the email of the current user from the session
        Session session = Session.getInstance();
        String userEmail = session.getCurrentUserEmail(); // Get the email of the current user from the session

        try {
            // Verify the reset code
            boolean codeVerified = verifyResetCode(userEmail, resetCode);
            if (codeVerified) {
                showAlert(Alert.AlertType.INFORMATION, "Code verified", "Reset code verification successful!");
                NavigateToEnternewpass();
            } else {
                // Code is not verified, display an error message

                showAlert(Alert.AlertType.ERROR, "Invalid", "Invalid or expired code. Please try again.");

            }
        } catch (SQLException | IOException e) {
            // Handle SQL exception
            lblErrors.setText("Error verifying the code. Please try again.");
            e.printStackTrace();
        }
    }




    public boolean verifyResetCode(String email, String resetCode) throws SQLException, IOException {
        // Retrieve user information using email
        ServiceUser serviceUser = new ServiceUser();
        User user = serviceUser.getUserByEmail(email);

        // Check if user exists and if the reset code matches
        if (user != null && resetCode.equals(user.getResetToken())) {
            // Retrieve reset token requested timestamp
            Date resetTokenRequestedAt = user.getResetTokenRequestedAt();

            // Convert Timestamp to Instant
            Instant requestedTime = resetTokenRequestedAt.toInstant();

            // Calculate current time
            Instant currentTime = Instant.now();

            // Print retrieved data for verification
            System.out.println("User email: " + email);
            System.out.println("Reset code: " + resetCode);
            System.out.println("Reset token requested at: " + resetTokenRequestedAt);
            System.out.println("Current time: " + currentTime);

            // Check if the difference between current time and requested time is less than 15 minutes
            Duration duration = Duration.between(requestedTime, currentTime);
            long minutesDifference = duration.toMinutes();
            if (minutesDifference < 15) {
                System.out.println("Reset code verification successful!");
                return true; // Token is valid
            } else {
                System.out.println("Reset code verification failed: Code expired!");
            }
        } else {
            System.out.println("Reset code verification failed: Invalid code or user not found!");
        }

        return false; // Token is invalid or expired
    }


    @FXML
    private void NavigateToEnternewpass() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/NewPassword.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnConfirmVerif.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
