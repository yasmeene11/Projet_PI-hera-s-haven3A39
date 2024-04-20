package controllers;

import entities.Session;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Consumer;



public class CustomAlertController {

    @FXML
    private PasswordField passwordField;

    private Consumer<Boolean> deletionCallback;

    private Stage stage;

    // Setter method for stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDeletionCallback(Consumer<Boolean> deletionCallback) {
        this.deletionCallback = deletionCallback;
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        String enteredPassword = passwordField.getText();
        User currentUser = Session.getInstance().getCurrentUser();
        String currentPassword = currentUser.getPassword();

        if (enteredPassword.equals(currentPassword)) {
            ServiceUser userService = new ServiceUser();
            try {
                showAlert(Alert.AlertType.INFORMATION, "Account Deleted", "Your account has been deleted successfully.");
                userService.delete(currentUser);

                if (deletionCallback != null) {
                    deletionCallback.accept(true); // Signal successful deletion
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting your account.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Incorrect Password", "The password you entered is incorrect.");
        }
        stage.close();
    }

    // Method to show error message in the custom alert dialog
    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }






}
