package controllers.Back.DonationM;

import entities.DonationM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceDonationM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class DisplayDonationMB {


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
           List<DonationM> donations = donationService.Show();
           listDonations.getItems().addAll(donations);
           // Set cell value factories to populate ListView items
           listDonations.setCellFactory(param -> new ListCell<DonationM>() {
               @Override
               protected void updateItem(DonationM donation, boolean empty) {
                   super.updateItem(donation, empty);
                   if (empty || donation == null) {
                       setText(null);
                   } else {
                       /*setText("Id: " + donation.getDonationMId() +
                               "Amount: " + donation.getDonationAmount() + ", Date: " + donation.getDonationMDate() + ", Account Key: " + donation.getAccountKey());*/
                       VBox container = new VBox(5);
                       container.getStyleClass().add("user-card");
                       Label donationMAmount = new Label("Donation Amount: " + donation.getDonationAmount());
                       Label donationMDate = new Label("Donation Date : " + donation.getDonationMDate());
                       Label userNameLabel = new Label("User Name: " + donation.getAccountKey());
                     //  Label userSurnameLabel = new Label("User Surname: " + donation.getAccountKey());






                       HBox buttonBox = new HBox(10);
                       buttonBox.setAlignment(Pos.CENTER);

                       Button updateButton = new Button("Update");
                       updateButton.getStyleClass().add("user-button");
                       updateButton.setOnAction(event -> handleUpdate(donation));

                       Button deleteButton = new Button("Delete");
                       deleteButton.getStyleClass().add("user-button");
                       deleteButton.setOnAction(delete -> handleDelete(donation));

                       buttonBox.getChildren().addAll(updateButton, deleteButton);

                       container.getChildren().addAll(donationMAmount, donationMDate, userNameLabel,buttonBox);
                       setGraphic(container);

                   }
               }
           });
           listDonations.getSelectionModel().selectedItemProperty().addListener((obs, oldDonation, newDonation) -> {
               if (newDonation != null) {
                   // Handle user selection here
               }
           });



           // Call the Show method to retrieve donation data and populate the ListView

       } catch (SQLException e) {
           e.printStackTrace();
           // Handle SQLException
       }
   }
    @FXML
    private void handleDelete(DonationM donationM) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Donation");
        alert.setContentText("Are you sure you want to delete this donation?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                donationService.delete(donationM); // Assuming donationPService is the correct service to use for deleting donations
                // Deletion successful, now refresh the display
                listDonations.getItems().remove(donationM); // Remove the deleted donation from the list
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception appropriately
            }
        }
    }
    private void handleUpdate(DonationM donationM) {
        if (donationM != null) {
            try {
                // Load the FXML for the update animal page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationM/UpdateDonationM.fxml"));
                Parent root = loader.load();

                // Get the controller for the update animal page
                UpdateDonationMB controller = loader.getController();

                // Pass the selected animal to the controller
                controller.initData(donationM);

                // Create a new stage for the update animal page
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update Donation");

                // Show the update stage without closing the main stage
                updateStage.initOwner(btnAdoption.getScene().getWindow());
                updateStage.initModality(Modality.WINDOW_MODAL);
                updateStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(); // Handle IOException
            }
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
