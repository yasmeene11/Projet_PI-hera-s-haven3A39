    package controllers.Back.Appointment;

    import entities.Rapport;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.layout.HBox;
    import javafx.stage.Stage;
    import javafx.util.Callback;
    import services.ServiceRapport;

    import java.sql.SQLException;
    import java.time.LocalDate;
    import java.util.List;

    public class UpdateReportB {
        @FXML
        private Button updateButton;
        @FXML
        public TextArea rapportTextArea;

        private ServiceRapport serviceRapport;
        private Rapport rapport;

        public UpdateReportB() {
            serviceRapport = new ServiceRapport();
        }


        public void setRapport(Rapport rapport) {
            this.rapport = rapport;
            System.out.println("Rapport ID: " + rapport.getRapportId());
            System.out.println("Appointment Key: " + rapport.getAppointmentKey());
            rapportTextArea.setText(rapport.getDescription()); // Set the text here
        }

        @FXML
        private void initialize() {
            // No need to set the text here
        }

        @FXML
        private void updateRapport() {
            try {
                System.out.println("Rapport ID: " + rapport.getRapportId());
                System.out.println("Description: " + rapport.getDescription());
                // Update the description with the current content of the TextArea
                rapport.setDescription(rapportTextArea.getText());
                rapport.setAppointmentKey(rapport.getAppointmentKey()); // Set the Appointment_Key

                // Update the Rapport in the database
                serviceRapport.update(rapport);

                // Show a success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "Report has been updated successfully.");

                // Close the window
                Stage stage = (Stage) rapportTextArea.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update rapport: " + e.getMessage());
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
