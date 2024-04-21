package controllers.Back.Animal;

import entities.Animal;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceAnimal;
import services.ServiceUser;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
            cmbBreed.setValue(String.valueOf(animal.getAnimal_Breed())); // Cast to String
            cmbstatus.setValue(String.valueOf(animal.getAnimal_Status())); // Cast to String
            cmbtype.setValue(String.valueOf(animal.getAnimal_Type())); // Cast to String

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
            String newAnimalName = txtAnimalName.getText();
            String newAnimalBreed = (String) cmbBreed.getValue(); // Cast to String
            String newAnimalStatus = (String) cmbstatus.getValue(); // Cast to String
            String newAnimalType = (String) cmbtype.getValue(); // Cast to String
            java.sql.Date newEnrollementDate = java.sql.Date.valueOf(enrollementdate.getValue()); // Convert LocalDate to java.sql.Date
            int newAge = Integer.parseInt(txtAge.getText()); // Parse to int

            String newAnimalDescription = txtAnimalDescription.getText();

            Path targetPath = Paths.get("C:/Users/perri/IdeaProjects/UnitedPets (1)/UnitedPets/src/main/resources", "animal_images", txtImage.getText());

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


            // Update animal object
            animal.setAnimal_Name(newAnimalName);
            animal.setAnimal_Breed(newAnimalBreed);
            animal.setAnimal_Status(newAnimalStatus);
            animal.setAnimal_Type(newAnimalType);
            animal.setEnrollement_Date(newEnrollementDate);
            animal.setAge(newAge);
            animal.setAnimal_Image(txtImage.getText());
            animal.setAnimal_Description(newAnimalDescription);

            // Perform the update operation (e.g., call a service method)
            try {
                ServiceAnimal animalService = new ServiceAnimal(); // Rename the variable to avoid conflict
                animalService.update(animal);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the update stage (optional)
            txtAnimalName.getScene().getWindow().hide();
        }
    }

}
