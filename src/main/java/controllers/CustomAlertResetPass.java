package controllers;

import entities.Session;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import services.ServiceUser;

import java.sql.SQLException;
import java.util.function.Consumer;

public class CustomAlertResetPass {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField1;

    private ServiceUser userService = new ServiceUser();

    private Consumer<Boolean> confirmationCallback;

    private Stage stage;

    // Setter method for stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setConfirmationCallback(Consumer<Boolean> callback) {
        this.confirmationCallback = callback;
    }

    @FXML
    void cancelAction(ActionEvent event) {
        if (confirmationCallback != null) {
            confirmationCallback.accept(false); // Send false to indicate cancellation
            stage.close(); // Close the dialog window
        }
    }

    @FXML
    void confirmAction(ActionEvent event) {
        // Retrieve the current user's email from the session (assuming you have session management)
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser == null || currentUser.getEmail() == null) {
            System.out.println("Current user or email is null.");
            return;
        }

        String userEmail = currentUser.getEmail();
        System.out.println("User Email: " + userEmail); // Print userEmail to check its value

        // Get the old password from the password field
        String oldPassword = passwordField.getText();

        // Get the new password from the password field
        String newPassword = passwordField1.getText();

        // Check if the new password is empty
        if (newPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "New password field cannot be empty.");
            return;
        }

        // Check if password meets complexity requirements
        if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{6,}$")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must contain at least 6 characters including one uppercase letter, one lowercase letter, one digit, and one special character.");
            return;
        }

        try {
            // Verify if the old password matches the stored password
            if (userService.verifyOldPassword(userEmail, oldPassword)) {
                // If the old password is correct, update the password with the new one
                userService.updatePasswordByEmail1(userEmail, newPassword);

                // Display success message
                showAlert(Alert.AlertType.INFORMATION, "Password Updated", "Password successfully updated.");

                if (confirmationCallback != null) {
                    confirmationCallback.accept(true); // Send true to indicate confirmation
                }
            } else {
                // Display error message indicating incorrect old password
                showAlert(Alert.AlertType.ERROR, "Error", "Incorrect old password.");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            // Display error message
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password. Please try again later.");
        }
    }


    // Method to show an alert with the specified type, title, and content
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null); // Hide header text
        alert.showAndWait();
    }
}
