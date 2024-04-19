package controllers.Back.Appointment;

import entities.Appointment;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import services.ServiceAppointment;
import services.ServiceUser;

import java.sql.SQLException;

public class UpdateAppointmentB {
    @FXML
    private ComboBox<String> comboappointmentstatus;

    private Appointment appointment;

    public void initData(Appointment appointment) {
        // Initialize the user data
        this.appointment = appointment;
        if (appointment != null) {
            comboappointmentstatus.setValue(appointment.getAppointmentStatus());
        }
    }

    @FXML
    private void updateAppointment() {
        if (appointment != null) {
            // Retrieve updated data from input fields
            String newAppointmentStatus = comboappointmentstatus.getValue();

            // Update user object
            appointment.setAppointmentStatus(newAppointmentStatus);

            // Perform the update operation (e.g., call a service method)
            try {
                ServiceAppointment a = new ServiceAppointment();
                a.update(appointment);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the update stage (optional)
            comboappointmentstatus.getScene().getWindow().hide();
        }
    }
}
