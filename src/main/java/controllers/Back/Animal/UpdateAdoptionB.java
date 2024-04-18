package controllers.Back.Animal;
import entities.Adoption;
import entities.Animal;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceUser;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
            cmbadoptionstatus.setValue(String.valueOf(adoption.getAdoption_Status()));
            txtAdoptionFee.setText(String.valueOf(adoption.getAdoption_Fee()));

            cmbAnimalName.setValue(adoption.getAnimal_Key()); // Cast to String
            cmbUserName.setValue(adoption.getAccount_Key()); // Cast to String

        }
    }



    @FXML
    private void updateAdoption() {
        if (adoption != null) {
            // Retrieve updated data from input fields
            java.sql.Date newAdoptionDate = java.sql.Date.valueOf(adoptiondate.getValue()); // Convert LocalDate to java.sql.Date
            String newAdoptionStatus = (String) cmbadoptionstatus.getValue(); // Cast to String

            float newAdoptionFee = Integer.parseInt(txtAdoptionFee.getText()); // Parse to int
            Animal newAnimalName =  cmbAnimalName.getValue(); // Cast to String
            User newUserName =  cmbUserName.getValue(); /// Cast to String

            // Update animal object
            adoption.setAdoption_Date(newAdoptionDate);
            adoption.setAdoption_Status(newAdoptionStatus);
            adoption.setAdoption_Fee(newAdoptionFee);
            adoption.setAnimal_Key(newAnimalName);
            adoption.setAccount_Key(newUserName);


            // Perform the update operation (e.g., call a service method)
            try {
                ServiceAdoption adoptionService = new ServiceAdoption(); // Rename the variable to avoid conflict
                adoptionService.update(adoption);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the update stage (optional)
            adoptiondate.getScene().getWindow().hide();
        }
    }



}
