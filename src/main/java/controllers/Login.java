package controllers;

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
import services.IService;
import services.ServiceUser;


import java.io.IOException;
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

        IService<User> userService = new ServiceUser();

        try {
            User loggedInUser = ((ServiceUser) userService).login(email, password);
            if (loggedInUser != null) {
                Session.getInstance().setCurrentUser(loggedInUser);
                showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + loggedInUser.getName() + "!");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/indexBack.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) btnSignin.getScene().getWindow(); // Get the current stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("United Pets");
                stage.show();
                // Additional actions for a successful login
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





}