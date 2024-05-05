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
import java.sql.SQLException;

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

    @FXML
    private void initialize() {
        cmbGender.setPromptText("Choose a gender");
        cmbRole.setPromptText("Sign up as");
    }
    // Method to handle user registration
    @FXML
    public void registerUser() {
        // Get user input
        String name = txtName.getText();
        String surname = txtSurname.getText();
        String gender = cmbGender.getValue();
        String phoneNumber = txtPhone.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = cmbRole.getValue(); // Get the selected role from ComboBox
        String accountStatus;

        // Validate user input
        if (name.isEmpty() || surname.isEmpty() || gender == null || phoneNumber.isEmpty() ||
                address.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            // Show an error message if any field is empty
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "All fields are required.");
            return;
        }

        // Check if name and surname contain only letters
        if (!name.matches("[a-zA-Z]+") || !surname.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Name and surname must contain only letters.");
            return;
        }

        // Check if phone number contains only digits and has 8 digits
        if (!phoneNumber.matches("\\d{8}")) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Phone number must contain 8 digits.");
            return;
        }

        // Check if email is in a valid format
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Invalid email format.");
            return;
        }

        // Check if password meets complexity requirements
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{6,}$")) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Password must contain at least 6 characters including one uppercase letter, one lowercase letter, one digit, and one special character.");
            return;
        }

        // Check if the email already exists in the database
        try {
            if (userService.searchUserByEmail(email)) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "This email is already registered. Please use a different email.");
                return;
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "An error occurred while checking email availability. Please try again.");
            e.printStackTrace();
            return;
        }

        // Set account status based on role
        if (role.equals("User")) {
            accountStatus = "Active";
        } else if (role.equals("Veterinary")) {
            accountStatus = "Pending";
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Invalid role selected.");
            return;
        }

        // Proceed with user registration
        User user = new User(name, surname, gender, phoneNumber, address, email, password, role, accountStatus, null, null);
        if(user.getGender().equals("Female")) {
            user.setImage("FemaleUser.jpg");
        }
        if(user.getGender().equals("Male")) {
            user.setImage("MaleUser.jpg.");

        }

        try {
            // Call the add method in ServiceUser to register the user
            userService.add(user);

            // Display an info message indicating successful registration
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have been successfully registered!");
            NavigateToLogin();

        } catch (Exception e) {
            // Display an error message if registration fails
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "An error occurred while registering the user. Please try again.");
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
