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
import services.ServiceUser;

import java.io.IOException;

public class NewPassword {

    @FXML
    private Button btnConfirmVerif;

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtConfirmNewPass;

    @FXML
    private TextField txtNewPass;

    @FXML
    void confirmPressNewwpass(ActionEvent event) {
        String newPassword = txtNewPass.getText();
        String confirmNewPassword = txtConfirmNewPass.getText();

        // Check if passwords match
        if (!newPassword.equals(confirmNewPassword)) {
            // Show error message if passwords don't match
            showAlert(Alert.AlertType.ERROR, "Error", "Password does not match.");
            return;
        }

        // Validate the new password
        if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{6,}$")) {
            showAlert(Alert.AlertType.ERROR, "Resetting password failed", "Password must contain at least 6 characters including one uppercase letter, one lowercase letter, one digit, and one special character.");
            return;
        }

        // Get the current user's email from the session
        String userEmail = Session.getInstance().getCurrentUserEmail();

        // Update the password
        ServiceUser userService = new ServiceUser();
        try {
            userService.updatePasswordByEmail(userEmail, newPassword);
            // Close session (assuming you have a session management system)
            userService.clearResetToken(userEmail);
            Session.getInstance().clearSession(); // Clear session after password update
            // Display success message
            showAlert(Alert.AlertType.INFORMATION, "Password Updated", "Password successfully updated.");
            NavigateToLogin();
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password. Please try again later.");
        }
    }
    // Method to display an alert
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void NavigateToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnConfirmVerif.getScene().getWindow(); // Get the current stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
}
