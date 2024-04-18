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


    private final ServiceAnimal animalService; // Assuming you have a ServiceUser object

    public AddAnimalB() {
        this.animalService = new ServiceAnimal(); // Initialize the ServiceUser
    }



    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        selectedImageFile = fileChooser.showOpenDialog(new Stage());
        if (selectedImageFile != null && selectedImageFile.exists()) {
            String imageFileName = selectedImageFile.getName(); // Get just the file name
            Path targetPath = Paths.get("/animal_images/", imageFileName); // Use only the file name when creating the target path
            try {
                Files.createDirectories(targetPath.getParent()); // Create parent directories if they don't exist
                Files.copy(selectedImageFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                txtImage.setText(imageFileName); // Set the image file name in the text field
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
        String Animal_Name = txtAnimalName.getText();
        String Animal_Breed = cmbBreed.getValue().toString(); // Use getValue() to get the selected item from ComboBox
        String Animal_Status = cmbstatus.getValue().toString(); // Same as above
        String Animal_Type = cmbtype.getValue().toString(); // Same as above
        int Age = Integer.parseInt(txtAge.getText());

        LocalDate enrolmentDateValue = enrollementdate.getValue();
        Date Enrollement_Date = enrolmentDateValue != null ? Date.valueOf(enrolmentDateValue) : null;
        String Animal_Description = txtDescription.getText(); // Use getText() for TextField

        Animal animal = new Animal(Animal_Name, Animal_Breed, Animal_Status, Animal_Type, Age, Enrollement_Date, txtImage.getText(), Animal_Description);

        try {
            // Call the add method in ServiceAnimal to add the animal
            animalService.add(animal);

            // Display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Animal added successfully");
            alert.setHeaderText(null);
            alert.setContentText("Animal added successfully!");
            alert.showAndWait();

        } catch (Exception e) {
            // Display an error message if adding fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add animal");
            alert.showAndWait();
            e.printStackTrace();
        }
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
