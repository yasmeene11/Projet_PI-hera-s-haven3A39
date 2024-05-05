package controllers.Back.User;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceUser;

import controllers.IndexFront;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;


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

            txtImage.setText(user.getImage());
        }
    }

    @FXML
    private void updateUser() {
        if (validateInputs()) {
            if (user != null) {
                String newName = txtName.getText();
                String newSurname = txtSurname.getText();
                String newGender = comboGender.getValue();
                String newPhone = txtPhone.getText();
                String newAddress = txtAddress.getText();
                String newEmail = txtEmail.getText();

                // Check if an image was selected
                if (imagefullpath != null) {
                    // Image was selected, update user with new image
                    user.setImage(txtImage.getText());
                } else {
                    // No image was selected, keep the old image
                    txtImage.setText(user.getImage());
                }

                user.setName(newName);
                user.setSurname(newSurname);
                user.setGender(newGender);
                user.setPhoneNumber(newPhone);
                user.setAddress(newAddress);
                user.setEmail(newEmail);

                // Update user object and handle exceptions
                try {
                    // If an image was selected, update the image file
                    if (imagefullpath != null) {
                        Path targetPath = Paths.get("C:/Users/Adem/Downloads/UnitedPets/src/main/resources", "UserImages", txtImage.getText());
                        if (Files.exists(targetPath.getParent())) {
                            Files.copy(Paths.get(imagefullpath), targetPath, StandardCopyOption.REPLACE_EXISTING);
                        } else {
                            showAlert("Error", "Target directory does not exist.", null);
                            return;
                        }
                    }

                    // Update the user in the database
                    ServiceUser u = new ServiceUser();
                    u.update(user);
                    txtName.getScene().getWindow().hide();

                    // Show success alert
                    showAlert("Success", "User Updated", "The user has been updated successfully.");


                    // Reload the display user page


                    // Close the update user window







                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to update user.", e.getMessage());
                }
            }
        }
    }

    private boolean validateInputs() {
        String name = txtName.getText();
        String surname = txtSurname.getText();
        String gender = comboGender.getValue();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();

        if (!Pattern.matches("[a-zA-Z]+", name) || !Pattern.matches("[a-zA-Z]+", surname)) {
            showAlert("Name and surname should contain only letters.");
            return false;
        }

        if (gender == null || gender.isEmpty()) {
            showAlert("Please select gender.");
            return false;
        }

        if (!Pattern.matches("\\d{8}", phone)) {
            showAlert("Phone number should contain 8 numbers.");
            return false;
        }

        if (address.isEmpty()) {
            showAlert("Address cannot be empty.");
            return false;
        }

        if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)) {
            showAlert("Invalid email format.");
            return false;
        }

        return true;
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }







}

