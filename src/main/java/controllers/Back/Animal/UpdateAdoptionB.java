package controllers.Back.Animal;
import entities.Adoption;
import entities.Animal;
import entities.User;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceUser;
import java.sql.SQLException;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateAdoptionB {
    @FXML
    private DatePicker adoptiondate;

    @FXML
    private ComboBox<Animal> cmbAnimalName;

    @FXML
    private ComboBox<String> cmbUserName;

    @FXML
    private ComboBox<String> cmbadoptionstatus;

    @FXML
    private TextField txtAdoptionFee;

    private Adoption adoption;

    public void initData(Adoption adoption) {
        this.adoption = adoption;
        if (adoption != null) {
            adoptiondate.setValue(adoption.getAdoption_Date().toLocalDate());
            cmbadoptionstatus.setValue(adoption.getAdoption_Status());
            txtAdoptionFee.setText(String.valueOf(adoption.getAdoption_Fee()));

            // Populate the ComboBox for users
            populateUserComboBox();

            // Populate the ComboBox for animals
            populateAnimalComboBox();
        }
    }

    private void populateUserComboBox() {
        try {
            ServiceUser userService = new ServiceUser();
            List<User> users = userService.Show();
            List<String> userNames = users.stream().map(User::getName).collect(Collectors.toList());
            cmbUserName.setItems(FXCollections.observableArrayList(userNames));
            cmbUserName.setValue(adoption.getAccount_Key().getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateAnimalComboBox() {
        try {
            ServiceAnimal animalService = new ServiceAnimal();
            // Fetch animals with status "Available" from the database
            List<Animal> availableAnimals = animalService.Show().stream()
                    .filter(animal -> animal.getAnimal_Status().equals("Available"))
                    .collect(Collectors.toList());

            // Set the ComboBox items with the available animals
            cmbAnimalName.setItems(FXCollections.observableArrayList(availableAnimals));

            // Set the default value of the ComboBox to the adoption's animal if available
            if (adoption != null && adoption.getAnimal_Key() != null) {
                cmbAnimalName.setValue(adoption.getAnimal_Key());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    private void updateAdoption() {
        if (adoption != null) {
            // Validate date
            LocalDate selectedDate = adoptiondate.getValue();
            if (selectedDate == null || selectedDate.isBefore(LocalDate.now())) {
                // Display an error message or handle the invalid date scenario
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date");
                alert.setHeaderText("Please select a valid future date.");
                alert.showAndWait();
                return; // Exit the method if the date is invalid
            }

            // Validate adoption fee
            String adoptionFeeText = txtAdoptionFee.getText();
            float newAdoptionFee;
            try {
                newAdoptionFee = Float.parseFloat(adoptionFeeText);
                if (newAdoptionFee < 0) {
                    // Adoption fee can't be negative
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Adoption Fee");
                    alert.setHeaderText("Adoption fee cannot be negative.");
                    alert.showAndWait();
                    return; // Exit the method if the fee is invalid
                }
            } catch (NumberFormatException e) {
                // Adoption fee contains characters or is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Adoption Fee");
                alert.setHeaderText("Please enter a valid number for adoption fee.");
                alert.showAndWait();
                return; // Exit the method if the fee is invalid
            }

            // Rest of the update logic
            java.sql.Date newAdoptionDate = java.sql.Date.valueOf(selectedDate);
            String newAdoptionStatus = cmbadoptionstatus.getValue();
            Animal newAnimalName = cmbAnimalName.getValue();
            String newUserName = cmbUserName.getValue();

            if (newAnimalName == null || newUserName == null) {
                // Display an error message if any of the combo boxes are left empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Selection");
                alert.setHeaderText("Please select values for all fields.");
                alert.showAndWait();
                return; // Exit the method if any combo box is left empty
            }

            // Update adoption details
            adoption.setAdoption_Date(newAdoptionDate);
            adoption.setAdoption_Status(newAdoptionStatus);
            adoption.setAdoption_Fee(newAdoptionFee);
            adoption.setAnimal_Key(newAnimalName);
            adoption.getAccount_Key().setName(newUserName);

            try {
                ServiceAdoption adoptionService = new ServiceAdoption();
                adoptionService.update(adoption);

                // If adoption status is "Cancelled", update the related animal's status to "Available"
                if ("Cancelled".equals(newAdoptionStatus)) {
                    int animalId = adoption.getAnimal_Key().getAnimalId();
                    adoptionService.updateAnimalStatus(animalId, "Available");
                }
                if ("Pending".equals(newAdoptionStatus)) {
                    int animalId = adoption.getAnimal_Key().getAnimalId();
                    adoptionService.updateAnimalStatus(animalId, "Pending");
                }

                // Close the window after successful update
                adoptiondate.getScene().getWindow().hide();

                // Update the ComboBox values
                populateUserComboBox();
                populateAnimalComboBox();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
