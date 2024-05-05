package controllers.Back.Animal;

import entities.Animal;
import entities.Boarding;
import entities.User;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceAnimal;
import services.ServiceBoarding;
import services.ServiceUser;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateBoardingB {
    @FXML
    private ComboBox<String> cmbAnimalName;

    @FXML
    private ComboBox<String> cmbUserName;

    @FXML
    private ComboBox<String> cmbboardingstatus;

    @FXML
    private DatePicker enddate;

    @FXML
    private DatePicker startdate;

    @FXML
    private TextField txtboardingFee;

    private Boarding boarding;

    public void initData(Boarding boarding) {
        this.boarding = boarding;
        if (boarding != null) {
            startdate.setValue(boarding.getStart_Date().toLocalDate());
            enddate.setValue(boarding.getEnd_Date().toLocalDate());
            cmbboardingstatus.setValue(boarding.getBoarding_Status());
            txtboardingFee.setText(String.valueOf(boarding.getBoarding_Fee()));

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
            cmbUserName.setValue(boarding.getAccount_Key().getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateAnimalComboBox() {
        try {
            ServiceAnimal animalService = new ServiceAnimal();
            // Fetch animals with status "Here for Boarding" from the database
            List<Animal> boardingAnimals = animalService.Show().stream()
                    .filter(animal -> animal.getAnimal_Status().equals("Here for Boarding"))
                    .collect(Collectors.toList());

            // Extract the names of animals here for boarding
            List<String> boardingAnimalNames = boardingAnimals.stream()
                    .map(Animal::getAnimal_Name)
                    .collect(Collectors.toList());

            // Set the ComboBox items with the names of animals here for boarding
            cmbAnimalName.setItems(FXCollections.observableArrayList(boardingAnimalNames));

            // Set the default value of the ComboBox to the boarding's animal name if available
            if (boarding != null && boarding.getAnimal_Key() != null) {
                cmbAnimalName.setValue(boarding.getAnimal_Key().getAnimal_Name());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void updateBoarding() {
        if (boarding != null) {
            // Retrieve updated data from input fields
            LocalDate startDateValue = startdate.getValue();
            LocalDate endDateValue = enddate.getValue();
            String newBoardingStatus = cmbboardingstatus.getValue();
            String boardingFeeText = txtboardingFee.getText();

            // Validate input fields
            if (startDateValue == null || endDateValue == null || newBoardingStatus == null || boardingFeeText.isEmpty()) {
                // Display an error message for empty fields
                showAlert("Error", "Missing Information", "Please fill in all fields.");
                return;
            }

            try {
                // Validate dates
                java.sql.Date newStartDate = java.sql.Date.valueOf(startDateValue);
                java.sql.Date newEndDate = java.sql.Date.valueOf(endDateValue);
                if (newStartDate.after(newEndDate)) {
                    // Display an error message for invalid dates
                    showAlert("Error", "Invalid Dates", "Start date must be before end date.");
                    return;
                }

                // Validate boarding fee
                float newBoardingFee = Float.parseFloat(boardingFeeText);
                if (newBoardingFee <= 0) {
                    // Display an error message for invalid fee
                    showAlert("Error", "Invalid Boarding Fee", "Boarding fee must be a positive number.");
                    return;
                }

                // Rest of the update logic
                boarding.setStart_Date(newStartDate);
                boarding.setEnd_Date(newEndDate);
                boarding.setBoarding_Status(newBoardingStatus);
                boarding.setBoarding_Fee(newBoardingFee);

                // Set new animal key if a different animal is selected
                String selectedAnimalName = cmbAnimalName.getValue();
                if (selectedAnimalName != null) {
                    ServiceAnimal animalService = new ServiceAnimal();
                    ServiceBoarding boardingService = new ServiceBoarding();
                    Animal selectedAnimal = boardingService.getAnimalByName(selectedAnimalName);
                    boarding.setAnimal_Key(selectedAnimal);
                }

                // Set new user key if a different user is selected
                String selectedUserName = cmbUserName.getValue();
                if (selectedUserName != null) {
                    ServiceUser userService = new ServiceUser();
                    ServiceBoarding boardingService = new ServiceBoarding();
                    User selectedUser = boardingService.getUserByName(selectedUserName);
                    boarding.setAccount_Key(selectedUser);
                }

                // Perform the update operation (e.g., call a service method)
                ServiceBoarding boardingService = new ServiceBoarding();
                boardingService.update(boarding);

                // Close the update stage (optional)
                startdate.getScene().getWindow().hide();
            } catch (NumberFormatException e) {
                // Display an error message for invalid boarding fee format
                showAlert("Error", "Invalid Boarding Fee", "Boarding fee must be a valid number.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // Helper method to display alert messages
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
