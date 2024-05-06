package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.IOException;

public class Register {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSignup;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private ComboBox<String> cmbRole; // Assuming you've corrected the type

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSurname;

    private final ServiceUser userService; // Assuming you have a ServiceUser object

    public Register() {
        this.userService = new ServiceUser(); // Initialize the ServiceUser
    }

    // Method to handle user registration
    @FXML
    public void registerUser() {
        String name = txtName.getText();
        String surname = txtSurname.getText();
        String gender = cmbGender.getValue();
        String phoneNumber = txtPhone.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = cmbRole.getValue(); // Get the selected role from ComboBox
        String accountStatus = "Active"; // Assuming default account status is "Active"

        User user = new User(name, surname, gender, phoneNumber, address, email, password, role, accountStatus, null, null);

        try {
            // Call the add method in ServiceUser to register the user
            userService.add(user);

            // Display an info message indicating successful registration
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("You have been successfully registered!");
            alert.showAndWait();

        } catch (Exception e) {
            // Display an error message if registration fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while registering the user. Please try again.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    @FXML
    private void NavigateToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCancel.getScene().getWindow(); // Get the current stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
}
