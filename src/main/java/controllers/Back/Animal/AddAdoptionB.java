package controllers.Back.Animal;

import entities.Adoption;
import entities.Animal;
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
import services.ServiceUser;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



    public class AddAdoptionB {

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
        private Button btnaddadoption;

        @FXML
        private Button btnconfirmadd;

        @FXML
        private Button btnlistadoption;

        @FXML
        private ComboBox<String> cmbadoptionstatus;

        @FXML
        private ComboBox<Animal> cmbanimalkey;

        @FXML
        private ComboBox<User> cmbuserkey;

        @FXML
        private DatePicker adoptiondate;

        @FXML
        private TextField txtadoptionfee;

        private final ServiceAdoption adoptionService; // Assuming you have a ServiceUser object
        private final ServiceAnimal animalService;
        private final ServiceUser userService;

        public AddAdoptionB() {
            this.adoptionService = new ServiceAdoption(); // Initialize the ServiceUser
            this.animalService = new ServiceAnimal();
            this.userService = new ServiceUser();
        }

        @FXML
        public void initialize() throws SQLException {
            // Populate the ComboBox for adoption status
            cmbadoptionstatus.setItems(FXCollections.observableArrayList("Pending", "Cancelled", "Adopted"));

            // Populate the ComboBox for users with only names
            List<User> users = userService.Show();
            cmbuserkey.setItems(FXCollections.observableArrayList(users));
            cmbuserkey.setConverter(new StringConverter<User>() {
                @Override
                public String toString(User object) {
                    return object.getName(); // Assuming 'getName()' returns the name of the user
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
                if (animal.getAnimal_Status().equals("Available")) { // Assuming 'getStatus()' returns the status of the animal
                    availableAnimals.add(animal);
                }
            }

            return availableAnimals;
        }



        @FXML
        public void AddAdoptionB() {
            LocalDate adoptionDateValue = adoptiondate.getValue();
            if (adoptionDateValue == null || adoptionDateValue.isBefore(LocalDate.now())) {
                // Display an error message if the date is null or a past date
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date");
                alert.setHeaderText("Please select a valid future date.");
                alert.showAndWait();
                return; // Exit the method if the date is invalid
            }

            String Adoption_Status = "Pending"; // Set adoption status to "Pending"

            // Validate combo boxes
            if (cmbanimalkey.getValue() == null || cmbuserkey.getValue() == null) {
                // Display an error message if any of the combo boxes are left empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Selection");
                alert.setHeaderText("Please select values for all fields.");
                alert.showAndWait();
                return; // Exit the method if any combo box is left empty
            }
            // Validate adoption fee
            String adoptionFeeText = txtadoptionfee.getText();
            float Adoption_Fee;
            try {
                Adoption_Fee = Float.parseFloat(adoptionFeeText);
                if (Adoption_Fee < 0) {
                    // Adoption fee can't be negative
                    // Display an error message or handle the invalid fee scenario
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Adoption Fee");
                    alert.setHeaderText("Adoption fee cannot be negative.");
                    alert.showAndWait();
                    return; // Exit the method if the fee is invalid
                }
            } catch (NumberFormatException e) {
                // Adoption fee contains characters or is empty
                // Display an error message or handle the invalid fee scenario
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Adoption Fee");
                alert.setHeaderText("Please enter a valid number for adoption fee.");
                alert.showAndWait();
                return; // Exit the method if the fee is invalid
            }

            Animal Animal_Key = cmbanimalkey.getValue();
            User User_Key = cmbuserkey.getValue();

            Adoption adoption = new Adoption(Date.valueOf(adoptionDateValue), Adoption_Status, Adoption_Fee, User_Key, Animal_Key);

            try {
                adoptionService.add(adoption);

                // Update the status of the associated animal to "Pending"
                Animal_Key.setAnimal_Status("Pending");
                animalService.update(Animal_Key);
// Display a success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "Adoption Added", "Animal added successfully!");

                // Close the current window or scene
                Stage stage = (Stage) adoptiondate.getScene().getWindow();
                stage.close();

                // Open the DisplayAnimal page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAdoption.fxml"));
                Parent root = loader.load();
                DisplayAdoptionB controller = loader.getController();
                // Initialize or update the DisplayAnimal page as needed

                // Show the DisplayAnimal page
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
    public void NavigateToAddAdoption()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/AddAdoption.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddadoption.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
}
