package controllers.Front.Animal;

import entities.Adoption;
import entities.Animal;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceUser;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddAdoptionF {
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
    private MenuBar menuBar;

    @FXML
    private DatePicker adoptiondate;



    private final ServiceAdoption adoptionService;
    private final ServiceAnimal animalService;
    private final ServiceUser userService;
    private int selectedAnimalId;

    public AddAdoptionF() {
        this.adoptionService = new ServiceAdoption();
        this.animalService = new ServiceAnimal();
        this.userService = new ServiceUser();
    }

    // Method to receive the selected animal's ID
    public void setSelectedAnimalId(int animalId) {
        this.selectedAnimalId = animalId;
    }



    @FXML
    public void AddAdoptionF() throws SQLException {
        LocalDate adoptionDateValue = adoptiondate.getValue();
        if (adoptionDateValue == null || adoptionDateValue.isBefore(LocalDate.now())) {
            // Display an error message for invalid or missing date
            showAlert("Error", "Invalid Adoption Date", "Please select a valid future date for adoption.");
            return;
        }

        Date adoptionDate = Date.valueOf(adoptionDateValue);
        String adoptionStatus = "Pending";
        Animal selectedAnimal = adoptionService.fetchAnimalById(selectedAnimalId);
        User user = adoptionService.fetchUserById(6); // Assuming user ID 6 exists
        float adoptionFee = 200;

        Adoption adoption = new Adoption(adoptionDate, adoptionStatus, adoptionFee, user, selectedAnimal);

        try {
            // Add the adoption
            adoptionService.add(adoption);

            // Update the status of the selected animal to "Pending"
            adoptionService.updateAnimalStatus(selectedAnimal.getAnimalId(), "Pending");

            // Display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adoption added successfully");
            alert.setHeaderText(null);
            alert.setContentText("Adoption added successfully!");
            alert.showAndWait();
            // Close the current stage
            Stage stage = (Stage) adoptiondate.getScene().getWindow();
            stage.close();

            // Load and display the indexBack page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/indexFront.fxml"));
            Parent root = loader.load();
            Stage displayStage = new Stage();
            displayStage.setScene(new Scene(root));
            displayStage.show();

        } catch (Exception e) {
            // Display an error message if adding fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add adoption");
            alert.showAndWait();
            e.printStackTrace();
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
