package services;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.time.LocalTime;
import java.util.stream.IntStream;

public class TimePicker extends HBox {

    private final ComboBox<Integer> hourComboBox;
    private final ComboBox<Integer> minuteComboBox;

    private final ObjectProperty<LocalTime> time;

    public TimePicker() {
        this.hourComboBox = new ComboBox<>();
        this.minuteComboBox = new ComboBox<>();
        this.time = new SimpleObjectProperty<>(LocalTime.now());

        // Populate hour and minute ComboBoxes
        IntStream.rangeClosed(0, 23).forEach(hourComboBox.getItems()::add);
        IntStream.rangeClosed(0, 59).forEach(minuteComboBox.getItems()::add);

        // Set initial values
        hourComboBox.setValue(LocalTime.now().getHour());
        minuteComboBox.setValue(LocalTime.now().getMinute());

        // Update time property when ComboBox values change
        hourComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                time.set(LocalTime.of(hourComboBox.getValue(), minuteComboBox.getValue())));
        minuteComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                time.set(LocalTime.of(hourComboBox.getValue(), minuteComboBox.getValue())));

        getChildren().addAll(hourComboBox, minuteComboBox);
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public LocalTime getTime() {
        return time.get();
    }

    public void setTime(LocalTime time) {
        hourComboBox.setValue(time.getHour());
        minuteComboBox.setValue(time.getMinute());
        this.time.set(time);
    }
}
