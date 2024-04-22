package controllers.Back.Animal;

import entities.Animal;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceAnimal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.io.File;
import java.time.LocalDate;

public class UpdateAnimalB {

    @FXML
    private ComboBox<String> cmbBreed;

    @FXML
    private ComboBox<String> cmbstatus;

    @FXML
    private ComboBox<String> cmbtype;


    @FXML
    private DatePicker enrollementdate;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtAnimalDescription;

    @FXML
    private TextField txtAnimalName;

    @FXML
    private TextField txtImage;

    private Animal animal;
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
            Path targetPath = Paths.get("animal_images", imageFileName); // Use only the file name when creating the target path
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

    public void initData(Animal animal) {
        this.animal = animal;
        if (animal != null) {
            txtAnimalName.setText(animal.getAnimal_Name());
            cmbBreed.setValue(animal.getAnimal_Breed()); // Set value directly
            cmbstatus.setValue(animal.getAnimal_Status()); // Set value directly
            cmbtype.setValue(animal.getAnimal_Type()); // Set value directly

            // For DatePicker, set the value to a LocalDate
            enrollementdate.setValue(animal.getEnrollement_Date().toLocalDate());

            // Convert int Age to String
            txtAge.setText(String.valueOf(animal.getAge()));

            txtImage.setText(animal.getAnimal_Image());
            txtAnimalDescription.setText(animal.getAnimal_Description());
        }
    }

    @FXML
    private void updateAnimal() {
        if (animal != null) {
            // Retrieve updated data from input fields
            String newAnimalName = txtAnimalName.getText().trim();
            String newAnimalBreed = cmbBreed.getValue();
            String newAnimalStatus = cmbstatus.getValue();
            String newAnimalType = cmbtype.getValue();
            LocalDate newEnrollementDate = enrollementdate.getValue();
            int newAge;
            String newAnimalDescription = txtAnimalDescription.getText();

            // Validate input fields
            if (newAnimalName.isEmpty() || newAnimalBreed == null || newAnimalStatus == null ||
                    newAnimalType == null || newEnrollementDate == null || newAnimalDescription.isEmpty()) {
                // Display an error message for empty fields
                showAlert("Error", "Missing Information", "Please fill in all fields.");
                return;
            }

            try {
                // Validate age
                newAge = Integer.parseInt(txtAge.getText());
                if (newAge <= 0) {
                    // Display an error message for invalid age
                    showAlert("Error", "Invalid Age", "Age must be a positive integer.");
                    return;
                }
            } catch (NumberFormatException e) {
                // Display an error message for non-integer age
                showAlert("Error", "Invalid Age", "Age must be a positive integer.");
                return;
            }

            // Check if an image file is selected
            if (selectedImageFile != null && selectedImageFile.exists()) {
                // Perform file copying only if an image file is selected
                Path targetPath = Paths.get("C:/Users/perri/IdeaProjects/UnitedPets (1)/UnitedPets/src/main/resources/animal_images", txtImage.getText());

                try {
                    Files.copy(selectedImageFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "File Copy Failed", "Failed to copy the image file.");
                    return;
                }
            }

            // Update animal object
            animal.setAnimal_Name(newAnimalName);
            animal.setAnimal_Breed(newAnimalBreed);
            animal.setAnimal_Status(newAnimalStatus);
            animal.setAnimal_Type(newAnimalType);
            animal.setEnrollement_Date(Date.valueOf(newEnrollementDate));
            animal.setAge(newAge);
            animal.setAnimal_Description(newAnimalDescription);
            animal.setAnimal_Image(newAnimalDescription);
            animal.setAnimal_Image(txtImage.getText());

            // Perform the update operation (e.g., call a service method)
            try {
                ServiceAnimal animalService = new ServiceAnimal(); // Initialize service properly
                animalService.update(animal);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the update stage (optional)
            txtAnimalName.getScene().getWindow().hide();
        }
    }

    // Helper method to display alert messages
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
