package controllers.Back.Animal;

import entities.Adoption;
import entities.Animal;
import entities.Boarding;
import entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceBoarding;
import services.ServiceUser;
import services.ServiceBoarding;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddBoardingB {

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
    private Button btnaddboarding;

    @FXML
    private Button btnconfirmadd;

    @FXML
    private Button btnlistboarding;

    @FXML
    private ComboBox<Animal> cmbanimalkey;

    @FXML
    private ComboBox<String> cmbboardingstatus;

    @FXML
    private ComboBox<User> cmbuserkey;

    @FXML
    private DatePicker enddate;

    @FXML
    private DatePicker startdate;

    @FXML
    private TextField txtboardingfee;
    private final ServiceBoarding boardingService; // Assuming you have a ServiceUser object
    private final ServiceAnimal animalService;
    private final ServiceUser userService;

    public AddBoardingB() {
        this.boardingService = new ServiceBoarding(); // Initialize the ServiceUser
        this.animalService = new ServiceAnimal();
        this.userService = new ServiceUser();
    }

    @FXML
    public void initialize() throws SQLException {

        cmbboardingstatus.setItems(FXCollections.observableArrayList("Pending", "Cancelled", "Completed"));

        // Populate the ComboBox for users with only names
        List<User> users = userService.Show();
        cmbuserkey.setItems(FXCollections.observableArrayList(users));
        cmbuserkey.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object.getName();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });

        // Populate the ComboBox for animals with only names where the status is available
        List<Animal> availableAnimals = getAvailableAnimals();
        cmbanimalkey.setItems(FXCollections.observableArrayList(availableAnimals));
        cmbanimalkey.setConverter(new StringConverter<Animal>() {
            @Override
            public String toString(Animal object) {
                return object.getAnimal_Name(); // Assuming 'getAnimal_Name()' returns the name of the animal
            }

            @Override
            public Animal fromString(String string) {
                return null;
            }
        });
    }

    private List<Animal> getAvailableAnimals() throws SQLException {
        List<Animal> allAnimals = animalService.Show();
        List<Animal> availableAnimals = new ArrayList<>();

        for (Animal animal : allAnimals) {
            if (animal.getAnimal_Status().equals("Here for Boarding"))
            {
                availableAnimals.add(animal);
            }
        }

        return availableAnimals;
    }



    @FXML
    public void AddBoardingB() {
        // Get values from input fields
        LocalDate startDateValue = startdate.getValue();
        LocalDate endDateValue = enddate.getValue();
        String boardingFeeText = txtboardingfee.getText();
        Animal animalKey = cmbanimalkey.getValue();
        User userKey = cmbuserkey.getValue();

        // Perform input validation
        if (startDateValue == null || endDateValue == null || boardingFeeText.isEmpty() ||
                animalKey == null || userKey == null) {
            // Display an error message if any field is empty
            showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Information", "Please fill in all fields.");
            return;
        }

        Date startDate = Date.valueOf(startDateValue);
        Date endDate = Date.valueOf(endDateValue);

        if (startDate.after(endDate)) {
            // Display an error message if start date is after end date
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Dates", "Start date cannot be after end date.");
            return;
        }

        if (startDate.before(Date.valueOf(LocalDate.now())) || endDate.before(Date.valueOf(LocalDate.now()))) {
            // Display an error message if start date or end date is in the past
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Dates", "Start and end dates cannot be in the past.");
            return;
        }

        float boardingFee;
        try {
            boardingFee = Float.parseFloat(boardingFeeText);
            if (boardingFee < 0) {
                // Display an error message if boarding fee is negative
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Boarding Fee", "Boarding fee cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            // Display an error message if boarding fee is not a valid number
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Boarding Fee", "Boarding fee must be a valid number.");
            return;
        }

        // Create the Boarding object
        Boarding boarding = new Boarding(startDate, endDate, "Pending", boardingFee, userKey, animalKey);

        try {
            // Add the boarding
            boardingService.add(boarding);

            // Display a success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Boarding Added", "Boarding added successfully!");

            // Close the current window or scene
            Stage stage = (Stage) txtboardingfee.getScene().getWindow();
            stage.close();

            // Open the DisplayBoarding page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayBoarding.fxml"));
            Parent root = loader.load();
            DisplayBoardingB controller = loader.getController();
            // Initialize or update the DisplayBoarding page as needed

            // Show the DisplayBoarding page
            Stage displayStage = new Stage();
            displayStage.setScene(new Scene(root));
            displayStage.show();

        } catch (Exception e) {
            // Display an error message if adding fails
            showAlert(Alert.AlertType.ERROR, "Error", "Add Failed", "Failed to add boarding.");
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
    public void NavigateToAddBoarding()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/AddBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddboarding.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
}
