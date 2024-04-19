package controllers.Front.Appointment;
import controllers.Front.Appointment.TimeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class AddAppointmentF {

    @FXML
    private ComboBox<String> AppTimeId;

    public void initialize() {
        AppTimeId.setItems(TimeUtils.generateTimeSlots());
    }


}
