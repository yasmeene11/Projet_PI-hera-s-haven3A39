package controllers.Back.User;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.ServiceUser;

import java.sql.SQLException;

public class UpdateUserB {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSurname;

    @FXML
    private ComboBox<String> comboGender;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtRole;

    @FXML
    private ComboBox<String> comboStatus;

    private User user;

    public void initData(User user) {
        // Initialize the user data
        this.user = user;
        if (user != null) {
            txtName.setText(user.getName());
            txtSurname.setText(user.getSurname());
            comboGender.setValue(user.getGender());
            txtPhone.setText(user.getPhoneNumber());
            txtAddress.setText(user.getAddress());
            txtEmail.setText(user.getEmail());
            txtRole.setText(user.getRole());
            comboStatus.setValue(user.getAccountStatus());
        }
    }

    @FXML
    private void updateUser() {
        if (user != null) {
            // Retrieve updated data from input fields
            String newName = txtName.getText();
            String newSurname = txtSurname.getText();
            String newGender = comboGender.getValue();
            String newPhone = txtPhone.getText();
            String newAddress = txtAddress.getText();
            String newEmail = txtEmail.getText();
            String newRole = txtRole.getText();
            String newStatus = comboStatus.getValue();

            // Update user object
            user.setName(newName);
            user.setSurname(newSurname);
            user.setGender(newGender);
            user.setPhoneNumber(newPhone);
            user.setAddress(newAddress);
            user.setEmail(newEmail);
            user.setRole(newRole);
            user.setAccountStatus(newStatus);

            // Perform the update operation (e.g., call a service method)
            try {
                ServiceUser u = new ServiceUser();
                u.update(user);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the update stage (optional)
            txtName.getScene().getWindow().hide();
        }
    }

}
