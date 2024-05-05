package controllers.Front.User;

import com.jfoenix.controls.JFXButton;
import controllers.Back.User.UpdateUserB;
import controllers.CustomAlertController;
import controllers.CustomAlertResetPass;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ProfileUser {
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
    private MenuBar menuBar;

    @FXML
    private MenuItem btnindexb;

    @FXML
    private StackPane avatarPane;


    @FXML
    private Label surnameLabel;




    @FXML
    private ImageView profileImage;


    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label genderLabel;


    @FXML
    private Label addressLabel;

    @FXML
    private Button btnDeleteId;

    @FXML
    private Button btnReseeId;

    @FXML
    private Button btnUpdateId;





    public void initialize() {
        User currentUser = Session.getInstance().getCurrentUser();

        // Set profile information
        nameLabel.setText(currentUser.getName());
        surnameLabel.setText(currentUser.getSurname());
        genderLabel.setText(currentUser.getGender());
        phoneNumberLabel.setText(currentUser.getPhoneNumber());
        addressLabel.setText(currentUser.getAddress());
        emailLabel.setText(currentUser.getEmail());

        // Load user profile image
        String profileImageFile = "/UserImages/" + currentUser.getImage();
        Image profileImagev = new Image(getClass().getResourceAsStream(profileImageFile));
        profileImage.setImage(profileImagev);

        // Load user avatar
        String avatarImageFile = "/UserImages/" + currentUser.getImage();
        Image avatarImage = new Image(getClass().getResourceAsStream(avatarImageFile));
        ImageView avatarImageView = new ImageView(avatarImage);
        avatarImageView.setFitWidth(50);
        avatarImageView.setFitHeight(50);

        // Create a circular button for the avatar
        JFXButton avatarButton = new JFXButton();
        avatarButton.setGraphic(avatarImageView);

        // Create context menu items
        MenuItem profileMenuItem = new MenuItem("Profile");
        MenuItem logoutMenuItem = new MenuItem("Logout");

        // Define event handlers for menu items
        profileMenuItem.setOnAction(e -> {
            // Handle profile menu item click
            System.out.println(currentUser.getPassword());
            System.out.println("Profile clicked");
        });

        logoutMenuItem.setOnAction(e -> {

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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void DeleteAcc() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/User/CustomAlert.fxml"));
            Parent root = loader.load();
            CustomAlertController controller = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.setTitle("Confirm Account Deletion");

            // Set the deletion callback
            controller.setDeletionCallback((success) -> {
                if (success) {
                    try {

                        handleLogoutBackFordelete(); // Call your handleLogout method if deletion is successful
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the alert dialog.");
        }
    }


    @FXML
    private void handleLogoutBackFordelete() throws IOException {
        // Clear the session
        Session.getInstance().clearSession();
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

    @FXML
    private void handleUpdate() {

        User user = Session.getInstance().getCurrentUser();

        if (user != null) {
            try {
                // Load the FXML for the update animal page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/User/UpdateUser.fxml"));
                Parent root = loader.load();

                // Get the controller for the update animal page
                UpdateUserB controller = loader.getController();

                // Pass the selected animal to the controller
                controller.initData(user);

                // Create a new stage for the update animal page
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update User");

                // Show the update stage without closing the main stage
                updateStage.initOwner(btnUpdateId.getScene().getWindow());
                updateStage.initModality(Modality.WINDOW_MODAL);
                updateStage.showAndWait();
                reloadProfilePage();
            } catch (IOException e) {
                e.printStackTrace(); // Handle IOException
            }
        }
    }

    private void reloadProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/User/ProfileUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnUpdateId.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while reloading the profile page.");
        }
    }



    @FXML
    private void openResetPasswordDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/User/CustomAlertResetPass.fxml"));
            Parent root = loader.load();
            CustomAlertResetPass controller = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.setTitle("Reset Password");

            // Set the confirmation callback
            controller.setConfirmationCallback((success) -> {
                if (success) {
                    // Password reset was successful, handle any necessary actions here
                    // For example, you can close the dialog and show a success message
                    dialogStage.close();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Password reset successful.");
                }
            });

            // Set the stage for the controller
            controller.setStage(dialogStage);

            // Show the dialog
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while opening the dialog.");
        }
    }


}




