package controllers.Front.Animal;

import entities.Adoption;
import entities.Animal;
import entities.Boarding;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceBoarding;
import services.ServiceUser;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddBoarding2 {

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
    private DatePicker enddate;

    @FXML
    private MenuBar menuBar;

    @FXML
    private DatePicker startdate;

    private final ServiceBoarding boardingService;
    private final ServiceAnimal animalService;
    private final ServiceUser userService;
    private int selectedAnimalId;

    public AddBoarding2() {
        this.boardingService = new ServiceBoarding();
        this.animalService = new ServiceAnimal();
        this.userService = new ServiceUser();
    }

    // Method to initialize the controller with the selected animal ID
    public void initData(int animalId) {
        selectedAnimalId = animalId;
    }

    @FXML
    public void AddBoarding2() throws SQLException {
        LocalDate startDateValue = startdate.getValue();
        LocalDate endDateValue = enddate.getValue();

        if (startDateValue == null || endDateValue == null) {
            // Display an error message if either start date or end date is missing
            showAlert("Error", "Missing Date", "Please select both start and end dates.");
            return;
        }

        Date startDate = Date.valueOf(startDateValue);
        Date endDate = Date.valueOf(endDateValue);

        if (startDate.after(endDate)) {
            // Display an error message if start date is after end date
            showAlert("Error", "Invalid Dates", "Start date cannot be after end date.");
            return;
        }

        // Set boarding status to "Pending"
        String boardingStatus = "Pending";

        try {
            // Fetch the animal by ID
            Animal selectedAnimal = boardingService.fetchAnimalById(selectedAnimalId);

            // Set the user statically to ID 6 (assuming user ID 6 exists in your database)
            User user = boardingService.fetchUserById(6);

            // Set the boarding fee to 200
            float boardingFee = 200;

            // Create the Boarding object
            Boarding boarding = new Boarding(startDate, endDate, boardingStatus, boardingFee, user, selectedAnimal);

            // Add the boarding
            boardingService.add(boarding);

            // Display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Boarding added successfully");
            alert.setHeaderText(null);
            alert.setContentText("Boarding added successfully!");
            alert.showAndWait();

            // Close the current stage
            Stage stage = (Stage) startdate.getScene().getWindow();
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
            alert.setContentText("Failed to add boarding");
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
