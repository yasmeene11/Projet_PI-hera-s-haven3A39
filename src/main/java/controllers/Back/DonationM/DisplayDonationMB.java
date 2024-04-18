package controllers.Back.DonationM;

import entities.DonationM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServiceDonationM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class DisplayDonationMB {

    @FXML
    private TableView<DonationM> TableDonations;
    @FXML

    private ListView<DonationM> listDonations;

    @FXML

    private TableColumn<DonationM, Integer> ColumnId;

    @FXML
    private TableColumn<DonationM, Double> ColumnAmount;

    @FXML
    private TableColumn<DonationM, String> ColumnDate;

    @FXML
    private TableColumn<DonationM, Integer> ColumnAccountKey;

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
    private Button btnadddonationm;

    @FXML
    private Button btnlistdonationm;

    private final ServiceDonationM donationService;

    public DisplayDonationMB() {
        donationService = new ServiceDonationM();
    }

   /* @FXML
    private void initialize() {
        try {
            ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
            ColumnId.setVisible(false); // Hide the ID column
            // Set cell value factories to populate table columns
            ColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            ColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            ColumnAccountKey.setCellValueFactory(new PropertyValueFactory<>("accountKey"));

            // Call the Show method to retrieve donation data and populate the TableView
            List<DonationM> donations = donationService.Show();
            TableDonations.getItems().addAll(donations);





        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }
    }
*/
   @FXML
   private void initialize() {
       try {
           // Set cell value factories to populate ListView items
           listDonations.setCellFactory(param -> new ListCell<DonationM>() {
               @Override
               protected void updateItem(DonationM donation, boolean empty) {
                   super.updateItem(donation, empty);
                   if (empty || donation == null) {
                       setText(null);
                   } else {
                       setText("Id: " + donation.getDonationMId() + ", Amount: " + donation.getDonationAmount() + ", Date: " + donation.getDonationMDate() + ", Account Key: " + donation.getAccountKey());
                   }
               }
           });

           // Call the Show method to retrieve donation data and populate the ListView
           List<DonationM> donations = donationService.Show();
           listDonations.getItems().addAll(donations);
       } catch (SQLException e) {
           e.printStackTrace();
           // Handle SQLException
       }
   }
    @FXML
    private void NavigateToDisplayUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/User/DisplayUser.fxml"));
        navigate(loader, btnUser);
    }

    @FXML
    private void NavigateToDisplayAnimal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAnimal.fxml"));
        navigate(loader, btnAnimal);
    }

    @FXML
    private void NavigateToDisplayAdoption() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayAdoption.fxml"));
        navigate(loader, btnAdoption);
    }

    @FXML
    private void NavigateToIndexBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/indexBack.fxml"));
        navigate(loader, btnIndex);
    }

    @FXML
    private void NavigateToDisplayAppointment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayAppointment.fxml"));
        navigate(loader, btnAppointment);
    }

    @FXML
    private void NavigateToDisplayBoarding() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayBoarding.fxml"));
        navigate(loader, btnBoarding);
    }

    @FXML
    private void NavigateToDisplayCashRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CashRegister/DisplayCashRegister.fxml"));
        navigate(loader, btnCash);
    }

    @FXML
    private void NavigateToDisplayCategory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/DisplayCategory.fxml"));
        navigate(loader, btnCategory);
    }

    @FXML
    private void NavigateToDisplayDonation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Donation/DonationDisplay.fxml"));
        navigate(loader, btnDonation);
    }

    @FXML
    private void NavigateToDisplayProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/DisplayProduct.fxml"));
        navigate(loader, btnProduct);
    }

    @FXML
    private void NavigateToDisplayReport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayReport.fxml"));
        navigate(loader, btnReport);
    }

    @FXML
    private void NavigateToDisplayDonationM() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationM/DisplayDonationM.fxml"));
        navigate(loader, btnlistdonationm);
    }

    @FXML
    private void NavigateToAddDonationM() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationM/AddDonationM.fxml"));
        navigate(loader, btnadddonationm);
    }

    private void navigate(FXMLLoader loader, Button button) throws IOException {
        Parent root = loader.load();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
}
