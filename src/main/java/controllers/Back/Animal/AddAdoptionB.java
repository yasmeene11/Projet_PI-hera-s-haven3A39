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

            // Populate the ComboBox for animals with only names
            List<Animal> animals = animalService.Show();
            cmbanimalkey.setItems(FXCollections.observableArrayList(animals));
            cmbanimalkey.setConverter(new StringConverter<Animal>() {
                @Override
                public String toString(Animal object) {
                    return object.getAnimal_Name(); // Assuming 'getName()' returns the name of the animal
                }

                @Override
                public Animal fromString(String string) {
                    // If needed for conversion back, but not used in this case
                    return null;
                }
            });

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
        }


        @FXML
        public void AddAdoptionB() {
            LocalDate adoptionDateValue = adoptiondate.getValue();
            Date Adoption_Date = adoptionDateValue != null ? Date.valueOf(adoptionDateValue) : null;

            String Adoption_Status = cmbadoptionstatus.getValue(); // No need to call toString() here
            float Adoption_Fee = Float.parseFloat(txtadoptionfee.getText());
            Animal Animal_Key = cmbanimalkey.getValue();
            User User_Key = cmbuserkey.getValue();

            Adoption adoption = new Adoption(Adoption_Date, Adoption_Status, Adoption_Fee, User_Key, Animal_Key);

            try {
                adoptionService.add(adoption);

                // Display a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Adoption added successfully");
                alert.setHeaderText(null);
                alert.setContentText("Adoption added successfully!");
                alert.showAndWait();

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
