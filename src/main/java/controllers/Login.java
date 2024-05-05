package controllers;

import com.google.api.services.oauth2.model.Userinfoplus;
import entities.Session;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import services.GoogleSignInHelper;
import services.IService;
import services.ServiceUser;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;




import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Login {

    @FXML
    private Label btnForgot;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnSignup;

    @FXML
    private Label lblErrors;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    public void handleLogin() {
        String email = txtUsername.getText();
        String password = txtPassword.getText();

        // Check if email and password are empty
        if (email.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Failed", "Email and password cannot be empty.");
            return; // Exit the method early
        }

        // Check if email is in a valid format
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid email format.");
            return; // Exit the method early
        }

        // Check if password has at least 6 characters
        if (password.length() < 6) {
            showAlert(AlertType.ERROR, "Login Failed", "Password must be at least 6 characters long.");
            return; // Exit the method early
        }

        // Proceed with login
        IService<User> userService = new ServiceUser();

        try {
            User loggedInUser = ((ServiceUser) userService).login(email, password);
            if (loggedInUser != null) {
                // Check if the account status is active
                if (loggedInUser.getAccountStatus().equals("Active")) {
                    // Redirect based on user role
                    String userRole = loggedInUser.getRole();
                    if (userRole.equals("User")) {
                        // Redirect to user page
                        Session.getInstance().setCurrentUser(loggedInUser);
                        redirectToUserPage();
                    } else if (userRole.equals("Veterinary")) { // Correct typo to "Veterinary"
                        // Redirect to veterinary page
                        Session.getInstance().setCurrentUser(loggedInUser);
                        redirectToVeterinaryPage();
                    } else if (userRole.equals("Admin")) {
                        // Redirect to admin page
                        redirectToAdminPage();
                    } else {
                        showAlert(AlertType.ERROR, "Login Failed", "Unknown user role.");
                    }
                } else {
                    showAlert(AlertType.ERROR, "Login Failed", "Your account is not active. Please contact support.");
                }
            } else {
                showAlert(AlertType.ERROR, "Login Failed", "Invalid email or password.");
                // Additional actions for a failed login
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "An error occurred during login.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void redirectToUserPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/indexFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnSignin.getScene().getWindow(); // Get the current stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    private void redirectToVeterinaryPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/indexFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnSignin.getScene().getWindow(); // Get the current stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Unites Pets");
        stage.show();
    }

    private void redirectToAdminPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/indexBack.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnSignin.getScene().getWindow(); // Get the current stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin Page");
        stage.show();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void NavigateToRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Register.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnSignin.getScene().getWindow(); // Get the current stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }


    @FXML
    private void LoginGoogle()  {
        try {
            // Sign in with Google
            Userinfoplus userInfo = GoogleSignInHelper.signInWithGoogle();

            // Print user information
            System.out.println("User Info:");
            // System.out.println(userInfo.toPrettyString());
            System.out.println(userInfo.getEmail());



            // Check if the user exists in the database
            try {
                ServiceUser serviceUserr = new ServiceUser();
                String email = userInfo.getEmail();
                User user = serviceUserr.getUserByEmail(email);
                System.out.println(user);
                if (user != null) {
                    showAlert(AlertType.INFORMATION, "Success", "Welcome back "+user.getName()+".");
                    Session.getInstance().setCurrentUser(user);
                    redirectToUserPage();
                } else {
                    showAlert(AlertType.ERROR, "Failed", "User with email " + userInfo.getEmail() + " does not exist. Please sign up first.");

                }
            } catch (SQLException e) {

                showAlert(AlertType.ERROR, "Error", "An error occurred while attempting to log in: " + e.getMessage());
            }

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void NavigateToForgotMail() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/ForgotPassMail.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnSignin.getScene().getWindow(); // Get the current stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }









}