package controllers.Back.User;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import services.ServiceUser;

import java.sql.SQLException;

public class UpdateBacktoBack {

    @FXML
    private ComboBox<String> comboRole;

    @FXML
    private ComboBox<String> comboStatus;

    private User user;

    // Method to initialize the input fields with user data
    @FXML
    public void initialize() {
        // Leave this method empty
    }

    // Method to initialize the input fields with user data
    public void initData() {
        if (user != null) {
            // Initialize the combo boxes with user data
            comboRole.setValue(user.getRole());
            comboStatus.setValue(user.getAccountStatus());
        }
    }

    @FXML
    private void updateUser() {
        // Check if user is initialized
        if (user == null) {
            // Handle the case where user is not initialized
            throw new IllegalStateException("User is not initialized");
        }

        // Check if combo boxes have selected values
        String role = comboRole.getValue();
        String status = comboStatus.getValue();

        if (role == null || status == null) {
            // Handle the case where combo boxes don't have selected values
            throw new IllegalStateException("Role and status must be selected");
        }

        // Update user object
        user.setRole(role);
        user.setAccountStatus(status);

        // Update the user in the database
        ServiceUser userService = new ServiceUser();
        try {
            userService.update(user);

            // Show success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "User Updated", "The user has been updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Close the window after updating
        comboRole.getScene().getWindow().hide();
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
