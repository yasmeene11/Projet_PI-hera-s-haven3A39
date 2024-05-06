package controllers.Back.DonationP;

import entities.DonationP;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceDonationM;
import services.ServiceDonationP;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class UpdateDonationPB {
    @FXML
    private Button btnAdoption;

    @FXML
    private Button btnAnimal;

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnBoarding;

    @FXML
    private Button btnCash;

    @FXML
    private Button btnCategory;

    @FXML
    private Button btnDonation;

    @FXML
    private Button btnIndex;

    @FXML
    private Button btnProduct;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnadddonationp;

    @FXML
    private Button btnconfirmadd;

    @FXML
    private Button btnlistdonationp;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtProductQuantity;
    @FXML
    private TextField txtProductLabel;
    @FXML
    private ComboBox<String> cmbProductType;
    @FXML
    private DatePicker datePickerDonationP;
    @FXML
    private DatePicker datePickerExpirationDate;
    private  ServiceDonationP serviceDonationP;

    private  DonationP donationP;
    public UpdateDonationPB() {
        serviceDonationP =new ServiceDonationP();
    }
    public void initData(DonationP donationP)
    {
        this.donationP = donationP;
        if (donationP != null)
        {
            txtProductName.setText(donationP.getDonation_product_name());
            txtProductQuantity.setText(String.valueOf(donationP.getDonation_product_quantity()));
            txtProductLabel.setText(donationP.getDonation_product_label());

            // For DatePicker, set the value to a LocalDate
            datePickerExpirationDate.setValue(donationP.getDonation_product_expiration_date().toLocalDate());
            datePickerDonationP.setValue(donationP.getDonation_p_date().toLocalDate());

            // Set the donation type
            cmbProductType.setValue(donationP.getDonation_p_type());

            // Convert int Age to String

        }
    }
    @FXML
    private void updateDonationP() {
        if (donationP != null) {
            // Retrieve updated data from input fields
            String newProductName = txtProductName.getText();
            int newProductQuantity = Integer.parseInt(txtProductQuantity.getText());
            String newProductLabel = txtProductLabel.getText();
            java.sql.Date newDonationPDate = java.sql.Date.valueOf(datePickerDonationP.getValue()); // Convert LocalDate to java.sql.Date
            java.sql.Date newExpirationDate = java.sql.Date.valueOf(datePickerExpirationDate.getValue()); // Convert LocalDate to java.sql.Date
            String newProductType = cmbProductType.getValue();

            // Update the DonationP object with the new data
            donationP.setDonation_product_name(newProductName);
            donationP.setDonation_product_quantity(newProductQuantity);
            donationP.setDonation_product_label(newProductLabel);
            donationP.setDonation_p_date(newDonationPDate);
            donationP.setDonation_product_expiration_date(newExpirationDate);
            donationP.setDonation_p_type(newProductType);

            // Perform the update operation by calling the service method
            try {
                serviceDonationP.update(donationP);
                Stage stage = (Stage) txtProductName.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception appropriately
            }
        }
    }

}
