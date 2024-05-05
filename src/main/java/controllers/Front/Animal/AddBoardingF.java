package controllers.Front.Animal;

import entities.Animal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceAnimal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;

public class AddBoardingF {


    @FXML
    private MenuItem btnanimal;

    @FXML
    private MenuItem btnboarding;

    @FXML
    private Button btnconfirmadd;

    @FXML
    private MenuItem btndonationm;

    @FXML
    private MenuItem btndonationp;

    @FXML
    private MenuItem btnindexb;

    @FXML
    private MenuItem btnproduct;

    @FXML
    private ComboBox<String> cmbBreed;

    @FXML
    private ComboBox<String> cmbtype;

    @FXML
    private DatePicker enrollementdate;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtAnimalName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtImage;

    private File selectedImageFile;
    private String imagefullpath;


    private final ServiceAnimal animalService; // Assuming you have a ServiceUser object

    public AddBoardingF() {
        this.animalService = new ServiceAnimal(); // Initialize the ServiceUser
    }



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
            String sourcePath = selectedImageFile.getAbsolutePath(); // Get the full path of the source image file
            String targetPath = "C:/Users/perri/IdeaProjects/UnitedPets (1)/UnitedPets/src/main/resources/animal_images/" + imageFileName;
            try {
                Files.copy(selectedImageFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
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


    @FXML
    public void AddBoardingF() {
        String Animal_Name = txtAnimalName.getText();
        String Animal_Breed = cmbBreed.getValue().toString();
        String Animal_Type = cmbtype.getValue().toString();
        String Animal_Description = txtDescription.getText();

        if (Animal_Name.isEmpty() || Animal_Breed.isEmpty() || Animal_Type.isEmpty() || Animal_Description.isEmpty()) {
            showAlert("Error", "Missing Information", "Please fill in all required fields.");
            return;
        }

        // Validate age field
        int Age;
        try {
            Age = Integer.parseInt(txtAge.getText());
            if (Age < 0) {
                showAlert("Error", "Invalid Age", "Age cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Age", "Please enter a valid number for age.");
            return;
        }

        // Validate enrolment date
        LocalDate enrolmentDateValue = enrollementdate.getValue();
        if (enrolmentDateValue == null) {
            showAlert("Error", "Missing Date", "Please select enrolment date.");
            return;
        }

        LocalDate currentDate = LocalDate.now();
        if (enrolmentDateValue.isBefore(currentDate)) {
            showAlert("Error", "Invalid Date", "Enrolment date cannot be in the past.");
            return;
        }

        // Perform the rest of the operations (e.g., copying image, adding animal) here
        Path targetPath = Paths.get("C:/Users/perri/IdeaProjects/UnitedPets (1)/UnitedPets/src/main/resources", "animal_images", txtImage.getText());

        try {
            Files.copy(Paths.get(imagefullpath), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to Copy Image", "Failed to copy the image file.");
            return;
        }

        Animal animal = new Animal(Animal_Name, Animal_Breed, "Here For Boarding", Animal_Type, Age, Date.valueOf(enrolmentDateValue), txtImage.getText(), Animal_Description);

        try {
            // Call the add method in ServiceAnimal to add the animal
            int animalId = animalService.add2(animal);

            // Display a success message
            showAlert("Information", "Animal Added Successfully", "Animal added successfully!");

            // Pass the animal ID to the next page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/AddBoarding2.fxml"));
            Parent root = loader.load();
            AddBoarding2 controller = loader.getController();
            controller.initData(animalId);

            // Show the next page
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) txtAnimalName.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            // Display an error message if adding fails
            showAlert("Error", "Add Failed", "Failed to add animal");
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
}
