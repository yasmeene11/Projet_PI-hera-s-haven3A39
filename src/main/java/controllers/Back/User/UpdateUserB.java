package controllers.Back.User;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;


import java.nio.file.Path;
import java.nio.file.Paths;


public class UpdateUserB {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSurname;

    @FXML
    private ComboBox<String> comboGender;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtRole;

    @FXML
    private ComboBox<String> comboStatus;

    @FXML
    private TextField txtImage;

    private User user;


    private File selectedImageFile;


    private String imagefullpath;



    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        selectedImageFile = fileChooser.showOpenDialog(new Stage());
        if (selectedImageFile != null && selectedImageFile.exists()) {
            String imageFileName = selectedImageFile.getName(); // Get just the file name
            Path targetPath = Paths.get("UserImages", imageFileName); // Use only the file name when creating the target path
            String sourcePath = selectedImageFile.getAbsolutePath(); // Get the full path of the source image file
            try {
                Files.createDirectories(targetPath.getParent()); // Create parent directories if they don't exist
                Files.copy(selectedImageFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                txtImage.setText(imageFileName); // Set the image file name in the text field
                imagefullpath = sourcePath; // Set the full path of the image file
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to copy the image file.");
                alert.showAndWait();
            }
        } else {
            // Handle case where selected file does not exist
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selected image file does not exist.");
            alert.showAndWait();
        }
    }

    public void initData(User user) {
        // Initialize the user data
        this.user = user;
        if (user != null) {
            txtName.setText(user.getName());
            txtSurname.setText(user.getSurname());
            comboGender.setValue(user.getGender());
            txtPhone.setText(user.getPhoneNumber());
            txtAddress.setText(user.getAddress());
            txtEmail.setText(user.getEmail());
            txtRole.setText(user.getRole());
            comboStatus.setValue(user.getAccountStatus());
            txtImage.setText(user.getImage());
        }
    }

    @FXML
    private void updateUser() {
        if (user != null) {
            // Retrieve updated data from input fields
            String newName = txtName.getText();
            String newSurname = txtSurname.getText();
            String newGender = comboGender.getValue();
            String newPhone = txtPhone.getText();
            String newAddress = txtAddress.getText();
            String newEmail = txtEmail.getText();
            String newRole = txtRole.getText();
            String newStatus = comboStatus.getValue();


            // Copy the image to the target path
            Path targetPath = Paths.get("C:/Users/Adem/Downloads/UnitedPets/src/main/resources", "UserImages", txtImage.getText());

            try {
                Files.copy(Paths.get(imagefullpath), targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to copy the image file.");
                alert.showAndWait();
                return;
            }

            // Update user object
            user.setName(newName);
            user.setSurname(newSurname);
            user.setGender(newGender);
            user.setPhoneNumber(newPhone);
            user.setAddress(newAddress);
            user.setEmail(newEmail);
            user.setRole(newRole);
            user.setAccountStatus(newStatus);
            user.setImage(txtImage.getText());

            // Perform the update operation (e.g., call a service method)
            try {
                ServiceUser u = new ServiceUser();
                u.update(user);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the update stage (optional)
            txtName.getScene().getWindow().hide();
        }
    }

    public void HandleSelectImage() {
    }
}
