package controllers.Back.Appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimeUtils {

    public static ObservableList<String> generateTimeSlots() {
        ObservableList<String> timeSlots = FXCollections.observableArrayList();

        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                String timeSlot = String.format("%02d:%02d", hour, minute);
                timeSlots.add(timeSlot);
            }
        }

        return timeSlots;
    }
}