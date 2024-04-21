    package controllers.Back.Appointment;

    import entities.Animal;
    import entities.Appointment;
    import entities.User;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.stage.Stage;
    import services.ServiceAppointment;
    import services.ServiceUser;

    import java.io.IOException;
    import java.sql.Date;
    import java.sql.SQLException;
    import java.sql.Time;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class AddAppointmentB {
        @FXML
        private DatePicker ApDateID;

        @FXML
        private ComboBox<String> patientComboBox;

        @FXML
        private ComboBox<String> petComboBox;


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
        private Button btnaddappointment;

        @FXML
        private Button btnconfirmadd;

        @FXML
        private Button btnlistappointment;


        @FXML
        private ComboBox<String> AptimeId;

        private final ServiceAppointment appointmentService = new ServiceAppointment();
        private final ServiceUser userService = new ServiceUser();
        private User fetchUserByName(String userName) throws SQLException {
            // Implement logic to fetch User from the database using the userName
            User user = null; // Initialize user variable with fetched User object
            return user;
        }

        private Animal fetchAnimalByName(String animalName) throws SQLException {
            // Implement logic to fetch Animal from the database using the animalName
            Animal animal = null; // Initialize animal variable with fetched Animal object
            return animal;
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
        public void NavigateToDisplayBoarding() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayBoarding.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnBoarding.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("United Pets");

            stage.show();

        }

        @FXML
        public void NavigateToDisplayCashRegister() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CashRegister/DisplayCashRegister.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnCash.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("United Pets");

            stage.show();

        }

        @FXML
        public void NavigateToDisplayCategory() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/DisplayCategory.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnCategory.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("United Pets");

            stage.show();

        }

        @FXML
        public void NavigateToDisplayDonation() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DisplayDonation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnDonation.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("United Pets");

            stage.show();

        }

        @FXML
        public void NavigateToDisplayProduct() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/DisplayProduct.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnProduct.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("United Pets");

            stage.show();

        }

        @FXML
        public void NavigateToDisplayReport() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayReport.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnReport.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("United Pets");

            stage.show();

        }

        @FXML
        public void NavigateToAddAppointment() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/AddAppointment.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnaddappointment.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("United Pets");

            stage.show();

        }

        public void fillComboBoxes() {
            ServiceAppointment serviceAppointment = new ServiceAppointment();
            try {
                Map<Integer, String> vetNamesMap = serviceAppointment.getVetNames();
                Map<Integer, String> animalNamesMap = serviceAppointment.getAnimalNames();

                System.out.println("Vet Names Map: " + vetNamesMap);
                System.out.println("Animal Names Map: " + animalNamesMap);

                ObservableList<String> vetNamesObservable = FXCollections.observableArrayList(vetNamesMap.values());
                ObservableList<String> animalNamesObservable = FXCollections.observableArrayList(animalNamesMap.values());

                patientComboBox.setItems(vetNamesObservable);
                petComboBox.setItems(animalNamesObservable);

                ObservableList<String> timeSlots = TimeUtils.generateTimeSlots();
                AptimeId.setItems(timeSlots);
            } catch (SQLException e) {
                // Handle the exception appropriately, e.g., show an error message
                e.printStackTrace();
            }
        }
@FXML
        public void initialize() {
            fillComboBoxes();
        }
        private ServiceAppointment serviceAppointment = new ServiceAppointment();

        @FXML
        private void addAppointment() {
            try {
                // Get the selected values from the combo boxes
                String patientName = patientComboBox.getValue();
                String petName = petComboBox.getValue();
                String appointmentTime = AptimeId.getValue();

                // Check if appointmentTime is null or empty
                if (appointmentTime == null || appointmentTime.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Please select an appointment time.");
                    return;
                }

                // Validate the appointmentTime format (HH:mm)
                if (!appointmentTime.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid appointment time format. Please use HH:mm.");
                    return;
                }

                // Get the IDs of the selected user and animal
                Integer userId = serviceAppointment.getUserIdByName(patientName);
                Integer animalId = serviceAppointment.getAnimalIdByName(petName);

                // Check if user and animal IDs are valid
                if (userId == null || animalId == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "User or animal not found.");
                    return;
                }

                // Create a new Appointment object with the selected values
                Appointment appointment = new Appointment();
                appointment.setAppointmentDate(Date.valueOf(ApDateID.getValue()));
                appointment.setAppointmentTime(Time.valueOf(appointmentTime + ":00")); // Adding seconds to format correctly
                appointment.setAppointmentStatus("pending"); // Set status to "pending"
                User user = new User();
                user.setAccountId(userId);
                appointment.setUser(user);
                Animal animal = new Animal();
                animal.setAnimalId(animalId);
                appointment.setAnimal(animal);

                // Add the appointment to the database
                appointmentService.add(appointment);

                // Show a success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment added successfully.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add appointment: " + e.getMessage());
            }
        }

        private void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }


    }



