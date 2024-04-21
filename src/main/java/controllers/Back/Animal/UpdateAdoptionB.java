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

import java.util.List;

public class UpdateAdoptionB {
    @FXML
    private DatePicker adoptiondate;

    @FXML
    private ComboBox<Animal> cmbAnimalName;

    @FXML
    private ComboBox<User> cmbUserName;

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
            cmbUserName.setItems(FXCollections.observableArrayList(users));
            cmbUserName.setValue(adoption.getAccount_Key());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateAnimalComboBox() {
        try {
            ServiceAnimal animalService = new ServiceAnimal();
            List<Animal> animals = animalService.Show();
            cmbAnimalName.setItems(FXCollections.observableArrayList(animals));
            cmbAnimalName.setValue(adoption.getAnimal_Key());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void updateAdoption() {
        if (adoption != null) {
            java.sql.Date newAdoptionDate = java.sql.Date.valueOf(adoptiondate.getValue());
            String newAdoptionStatus = cmbadoptionstatus.getValue();
            float newAdoptionFee = Float.parseFloat(txtAdoptionFee.getText());
            Animal newAnimalName = cmbAnimalName.getValue();
            User newUserName = cmbUserName.getValue();

            adoption.setAdoption_Date(newAdoptionDate);
            adoption.setAdoption_Status(newAdoptionStatus);
            adoption.setAdoption_Fee(newAdoptionFee);
            adoption.setAnimal_Key(newAnimalName);
            adoption.setAccount_Key(newUserName);

            try {
                ServiceAdoption adoptionService = new ServiceAdoption();
                adoptionService.update(adoption);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            adoptiondate.getScene().getWindow().hide();
        }
    }
}