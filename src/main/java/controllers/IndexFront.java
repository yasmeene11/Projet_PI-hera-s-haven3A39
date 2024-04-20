package controllers;

import com.jfoenix.controls.JFXButton;
import entities.Session;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class IndexFront {


    @FXML
    private StackPane avatarPane;
    @FXML
    private MenuItem btnanimal;

    @FXML
    private MenuItem btnboarding;

    @FXML
    private MenuItem btndonationm;

    @FXML
    private MenuItem btndonationp;

    @FXML
    private MenuItem btnproduct;


    @FXML
    private MenuItem btnindexb;

    @FXML
    private MenuBar menuBar;
    @FXML
    private void handleLogoutBack() throws IOException {
        // Clear the session
        Session.getInstance().clearSession();
        showAlert(Alert.AlertType.INFORMATION, "Logged Out", "You have been logged out successfully.");
        Stage stage = (Stage) menuBar.getScene().getWindow();

        // Load the user profile scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/login.fxml"));
        Parent root = loader.load();

        // Set the scene on the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
    public void initialize() {


        User currentUser = Session.getInstance().getCurrentUser();


        // Sample user data
        String username = currentUser.getName();
        String imageuser= "/UserImages/"+currentUser.getImage();
        System.out.println(imageuser);

        // Load image
        Image image = new Image(getClass().getResourceAsStream(imageuser));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);


        // Create a circular button for the avatar
        JFXButton avatarButton = new JFXButton();
        avatarButton.setGraphic(imageView);

        // Create context menu items
        MenuItem profileMenuItem = new MenuItem("Profile");
        MenuItem logoutMenuItem = new MenuItem("Logout");

        // Define event handlers for menu items
        profileMenuItem.setOnAction(e -> {
            // Handle profile menu item click
            try {
                NavigateToUserProfile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Profile clicked");
        });

        logoutMenuItem.setOnAction(e -> {
            // Handle logout menu item click
            try {
                handleLogoutBack();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Logout clicked");
        });

        // Create context menu and add menu items
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(profileMenuItem, logoutMenuItem);

        // Attach context menu to avatar button
        avatarButton.setOnMouseClicked(e -> {
            if (e.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                contextMenu.show(avatarButton, e.getScreenX(), e.getScreenY());
            }
        });

        // Add the avatar button to the avatar pane
        avatarPane.getChildren().add(avatarButton);
    }




    // Method to show alert messages
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to display user info
    public void displayUserInfoBack() {
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Display user information in an alert
            String userInfo = "Name: " + currentUser.getName() + "\n"
                    + "Surname: " + currentUser.getSurname() + "\n"
                    + "Email: " + currentUser.getEmail() + "\n"
                    + "Phone Number: " + currentUser.getPhoneNumber() + "\n"
                    + "Address: " + currentUser.getAddress() + "\n"
                    + "Gender: " + currentUser.getGender();
            showAlert(Alert.AlertType.INFORMATION, "User Information", userInfo);
        } else {
            showAlert(Alert.AlertType.WARNING, "User Not Logged In", "Please log in first.");
        }
    }

    @FXML
    public void NavigateToIndexBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/indexFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnindexb.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayAnimal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/DisplayAnimal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnanimal.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToAddBoarding() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/AddBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnboarding.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnproduct.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationM() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationM/DisplayDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationm.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationP() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationP/DisplayDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationp.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }


    @FXML
    public void NavigateToUserProfile() throws IOException {
        // Get the current stage from any control
        Stage stage = (Stage) menuBar.getScene().getWindow();

        // Load the user profile scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/User/ProfileUser.fxml"));
        Parent root = loader.load();

        // Set the scene on the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }



}
