package controllers.Back.DonationM;

import entities.DonationM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceDonationM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UpdateDonationMB {
    @FXML

    private ListView<DonationM> listDonations;
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
    private TextField txtDonationMAmount;
    @FXML
    private DatePicker datePickerDonationMDate;


    @FXML
    private Button btnadddonationm;

    @FXML
    private Button btnconfirmadd;

    @FXML
    private Button btnlistdonationm;
    private DonationM donationM;
   private ServiceDonationM donationService;

    public UpdateDonationMB() {
        donationService = new ServiceDonationM();
    }

    public void initData(DonationM donationM) {
        this.donationM = donationM;
        if (donationM != null) {
            txtDonationMAmount.setText(String.valueOf(donationM.getDonationAmount()));

            // For DatePicker, set the value to a LocalDate
            datePickerDonationMDate.setValue(donationM.getDonationMDate().toLocalDate());



        }
    }
    @FXML
    private void updateDonationM() {
        if (donationM != null) {
            // Retrieve updated data from input fields
            Float newDonationAmount = Float.parseFloat(txtDonationMAmount.getText());

            java.sql.Date newDonationDate = java.sql.Date.valueOf(datePickerDonationMDate.getValue()); // Convert LocalDate to java.sql.Date
            //System.out.println(newDonationAmount);
            donationM.setDonationAmount(newDonationAmount);
            donationM.setDonationMDate(newDonationDate);

            // Perform the update operation (e.g., call a service method)
            try {
                ServiceDonationM donationMService = new ServiceDonationM(); // Rename the variable to avoid conflict
                donationMService.update(donationM);
                Stage stage = (Stage) txtDonationMAmount.getScene().getWindow();
                stage.close();
              //  refreshDonationList();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    /*private void refreshDonationList() {
        try {
            // Clear the existing list
            listDonations.getItems().clear();

            // Call the Show method to retrieve updated donation data and populate the ListView
            List<DonationM> donations = donationService.Show();
            listDonations.getItems().addAll(donations);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }
    }*/
    @FXML
    private void NavigateToDisplayUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/User/DisplayUser.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnUser.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
    @FXML
    private void NavigateToDisplayAnimal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAnimal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAnimal.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    private void NavigateToDisplayAdoption() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAdoption.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAdoption.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();
    }
    @FXML
    private void NavigateToIndexBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/indexBack.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnIndex.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();
    }

    @FXML
    private void NavigateToDisplayAppointment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayAppointment.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAppointment.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }


    @FXML
    public void NavigateToDisplayBoarding()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBoarding.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCashRegister()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CashRegister/DisplayCashRegister.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCash.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCategory()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/DisplayCategory.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayDonation()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DisplayDonation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnDonation.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public  void NavigateToDisplayProduct()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnProduct.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayReport()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayReport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReport.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayDonationM()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationM/DisplayDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnlistdonationm.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

}
