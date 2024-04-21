package controllers.Back.Appointment;

import entities.Appointment;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceAppointment;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class UpdateAppointmentB {
    @FXML
    private ComboBox<String> comboappointmentstatus;
    @FXML
    private DatePicker datepickerappointmentdate;
    @FXML
    private ComboBox<String> AptimeId;
    @FXML
    private ComboBox<String> combovet;
    @FXML
    private ComboBox<String> combopet;

    private Appointment appointment;

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        initData(appointment);
    }

    public void initData(Appointment appointment) {
        // Initialize the user data
        this.appointment = appointment;
        if (appointment != null) {
            System.out.println("Appointment Status from Appointment Object: " + appointment.getAppointmentStatus());
            comboappointmentstatus.setValue(appointment.getAppointmentStatus());
            datepickerappointmentdate.setValue(appointment.getAppointmentDate().toLocalDate());
            AptimeId.setValue(appointment.getAppointmentTime().toString()); //
            combovet.setValue(appointment.getUser().getName());
            combopet.setValue(appointment.getAnimal().getAnimal_Name());
        }
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

            combovet.setItems(vetNamesObservable);
            combopet.setItems(animalNamesObservable);
            ObservableList<String> appointmentStatusList = FXCollections.observableArrayList("Cancelled", "Confirmed", "Pending");
            comboappointmentstatus.setItems(appointmentStatusList);
            ObservableList<String> timeSlots = TimeUtils.generateTimeSlots();
            AptimeId.setItems(timeSlots);
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., show an error message
            e.printStackTrace();
        }
    }
    @FXML
    private void updateAppointment() {
        if (appointment != null) {
            try {
                // Retrieve updated data from input fields
                LocalDate newAppointmentDate = datepickerappointmentdate.getValue();
                LocalTime newAppointmentTime = LocalTime.parse(AptimeId.getValue());
                String newAppointmentStatus = comboappointmentstatus.getValue();
                String newPatientName = combovet.getValue();
                String newPetName = combopet.getValue();

                // Get the IDs of the selected user and animal
                Integer userId = ServiceAppointment.getUserIdByName(newPatientName);
                Integer animalId = ServiceAppointment.getAnimalIdByName(newPetName);

                // Check if user and animal IDs are valid
                if (userId == null || animalId == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "User or animal not found.");
                    return;
                }

                // Update appointment object with the new values
                appointment.setAppointmentDate(java.sql.Date.valueOf(newAppointmentDate));
                appointment.setAppointmentTime(java.sql.Time.valueOf(newAppointmentTime));
                appointment.setAppointmentStatus(newAppointmentStatus);
                appointment.getUser().setAccountId(userId);
                appointment.getAnimal().setAnimalId(animalId);

                // Perform the update operation
                ServiceAppointment a = new ServiceAppointment();
                a.update(appointment);

                // Show a success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment updated successfully.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update appointment: " + e.getMessage());
            }
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void initialize() {
        fillComboBoxes();

    }
}
