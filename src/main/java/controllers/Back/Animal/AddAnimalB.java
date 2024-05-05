package controllers.Back.Animal;

import entities.User;
import javafx.fxml.FXML;
import entities.Animal;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceAnimal;
import services.ServiceUser;
import java.sql.Date;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.io.IOException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddAnimalB {

    @FXML
    private Button btnAdoption;

    @FXML
    private Button btnAnimal;

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnBoarding;

    @FXML
    private Button btnCash;

    @FXML
    private Button btnCategory;

    @FXML
    private Button btnDonation;

    @FXML
    private Button btnIndex;

    @FXML
    private Button btnProduct;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnaddanimal;

    @FXML
    private Button btnconfirmadd;

    @FXML
    private Button btnlistanimal;

    @FXML
    private ComboBox<String> cmbBreed;

    @FXML
    private ComboBox<String> cmbstatus;

    @FXML
    private ComboBox<String> cmbtype;

    @FXML
    private DatePicker enrollementdate;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtAnimalName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtImage; // Assuming this is the TextField for displaying the image path

    private File selectedImageFile;
    private String imagefullpath;


    private final ServiceAnimal animalService; // Assuming you have a ServiceUser object

    public AddAnimalB() {
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
    public void addAnimalB() {
        // Get values from input fields
        String Animal_Name = txtAnimalName.getText();
        String Animal_Breed = cmbBreed.getValue() != null ? cmbBreed.getValue().toString() : null;
        String Animal_Status = cmbstatus.getValue() != null ? cmbstatus.getValue().toString() : null;
        String Animal_Type = cmbtype.getValue() != null ? cmbtype.getValue().toString() : null;
        String AgeText = txtAge.getText();
        LocalDate enrolmentDateValue = enrollementdate.getValue();
        String Animal_Description = txtDescription.getText();
        String ImageFileName = txtImage.getText();

        // Perform input validation
        if (Animal_Name.isEmpty() || Animal_Breed == null || Animal_Status == null || Animal_Type == null ||
                AgeText.isEmpty() || enrolmentDateValue == null || Animal_Description.isEmpty() || ImageFileName.isEmpty()) {
            // Display an error message if any field is empty
            showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Information", "Please fill in all fields.");
            return;
        }

        try {
            // Parse age
            int Age = Integer.parseInt(AgeText);
            // Check if age is positive
            if (Age <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Age", "Age must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            // Display an error message if age is not a valid integer
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Age", "Age must be a positive integer.");
            return;
        }

        // Check if enrolment date is in the past
        if (enrolmentDateValue.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Date", "Enrolment date cannot be in the past.");
            return;
        }

        // Perform other validations if needed (e.g., validate image file existence)

        // If all validations pass, proceed to add the animal
        Animal animal = new Animal(Animal_Name, Animal_Breed, Animal_Status, Animal_Type,
                Integer.parseInt(AgeText), Date.valueOf(enrolmentDateValue), ImageFileName, Animal_Description);

        try {
            // Call the add method in ServiceAnimal to add the animal
            animalService.add(animal);

            // Display a success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Animal Added", "Animal added successfully!");

            // Close the current window or scene
            Stage stage = (Stage) txtImage.getScene().getWindow();
            stage.close();

            // Open the DisplayAnimal page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAnimal.fxml"));
            Parent root = loader.load();
            DisplayAnimalB controller = loader.getController();
            // Initialize or update the DisplayAnimal page as needed

            // Show the DisplayAnimal page
            Stage displayStage = new Stage();
            displayStage.setScene(new Scene(root));
            displayStage.show();

        } catch (Exception e) {
            // Display an error message if adding fails
            showAlert(Alert.AlertType.ERROR, "Error", "Add Failed", "Failed to add animal.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }













    @FXML
    private void NavigateToDisplayUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/User/DisplayUser.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnUser.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    private void NavigateToDisplayAnimal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAnimal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAnimal.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    private void NavigateToDisplayAdoption() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAdoption.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAdoption.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();
    }
    @FXML
    private void NavigateToIndexBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/indexBack.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnIndex.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();
    }

    @FXML
    private void NavigateToDisplayAppointment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayAppointment.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAppointment.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }


    @FXML
    public void NavigateToDisplayBoarding()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBoarding.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCashRegister()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CashRegister/DisplayCashRegister.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCash.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCategory()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/DisplayCategory.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayDonation()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DisplayDonation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnDonation.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public  void NavigateToDisplayProduct()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnProduct.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayReport()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayReport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReport.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToAddAnimal()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/AddAnimal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddanimal.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

}
