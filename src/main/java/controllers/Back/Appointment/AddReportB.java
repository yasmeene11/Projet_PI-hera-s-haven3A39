    package controllers.Back.Appointment;

    import entities.Appointment;
    import entities.Rapport;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.ComboBox;
    import javafx.scene.control.TextArea;
    import javafx.stage.Stage;
    import services.ServiceRapport;

    import java.io.IOException;
    import java.sql.SQLException;
    import java.util.List;

    public class AddReportB {

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
        private Button btnaddreport;

        @FXML
        private Button btnconfirmadd;

        @FXML
        private Button btnlistreport;

        @FXML
        private TextArea reportTextArea;

        @FXML
        private ComboBox<Appointment> appointmentComboBox;

        private ServiceRapport rapportService;


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
        public void NavigateToAddReport()throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/AddReport.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnaddreport.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("United Pets");

            stage.show();

        }


        @FXML
        private void addReport() {
            try {
                Appointment selectedAppointment = appointmentComboBox.getValue();
                if (selectedAppointment == null) {
                    // Show a warning message using an Alert dialog
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("No Appointment currently lacks a report! Add an Appoitnment and try again.");
                    alert.showAndWait();
                    return;
                }

                // Get the description of the report from the TextArea
                String description = reportTextArea.getText();

                // Validate the description length and content
                if (description.length() < 5) {
                    // Show a warning message using an Alert dialog
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("The report should be at least 5 characters long.");
                    alert.showAndWait();
                    return;
                }

                if (description.matches("\\d+")) {
                    // Show a warning message using an Alert dialog
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("The report should not consist of only numbers.");
                    alert.showAndWait();
                    return;
                }

                if (description.isEmpty()) {
                    // Show a warning message using an Alert dialog
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("You cannot add an empty report.");
                    alert.showAndWait();
                    return;
                }

                // Create a new Rapport object
                String vetName = null;
                String petName = null;
                Rapport rapport = new Rapport(0, description, vetName, petName, selectedAppointment);

                // Add the report using the rapportService
                rapportService.add(rapport);

                // Show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Report added successfully.");
                alert.showAndWait();

                // Clear the report TextArea
                reportTextArea.clear();

                // Refresh the ComboBox
                fillComboBox();
            } catch (SQLException e) {
                // Handle any SQL exception that occurs during adding the report
                e.printStackTrace();
                // Display an error message
                System.out.println("Failed to add report.");
            }
        }


    @FXML
    private void fillComboBox() {
        try {
            // Clear the current items in the comboBox
            appointmentComboBox.getItems().clear();

            // Get appointments without reports from the service
            List<Appointment> appointmentsWithoutReports = rapportService.getAppointmentsWithoutReports();

            // Add appointments to the comboBox
            appointmentComboBox.getItems().addAll(appointmentsWithoutReports);

            // Select the first item by default
            if (!appointmentsWithoutReports.isEmpty()) {
                appointmentComboBox.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            // Handle any SQL exception that occurs
            e.printStackTrace();
            // Display an error message
            System.out.println("Failed to fill the ComboBox: " + e.getMessage());
        }
    }





        public void initialize() {
            rapportService = new ServiceRapport();
            fillComboBox();
        }





    }
