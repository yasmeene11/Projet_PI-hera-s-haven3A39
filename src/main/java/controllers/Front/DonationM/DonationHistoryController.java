package controllers.Front.DonationM;

import entities.DonationM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceDonationM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DonationHistoryController implements Initializable {

    @FXML
    private MenuItem btnanimal;

    @FXML
    private MenuItem btnboarding;

    @FXML
    private MenuItem btndonationm;

    @FXML
    private MenuItem btndonationp;

    @FXML
    private MenuItem btnproduct;
    @FXML
    private MenuItem btnShowHistory;

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem btnindexb;


    @FXML
    private ListView<DonationM> listDonationHistory;

    private ServiceDonationM serviceDonationM;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceDonationM = new ServiceDonationM();
        loadDonationHistory();
    }

    private void loadDonationHistory() {
        try {
            List<DonationM> donationHistory = serviceDonationM.getDonationHistoryForCurrentUser();
            listDonationHistory.getItems().addAll(donationHistory);
            // Set cell value factories to populate ListView items
            listDonationHistory.setCellFactory(param -> new ListCell<DonationM>() {
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
                        donationMAmount.setStyle("-fx-font-weight: bold;-fx-text-fill: red;"); // Appliquer le style en gras

                        Label donationMDate = new Label("Donation Date : " + donation.getDonationMDate());
                        donationMDate.setStyle("-fx-font-weight: bold;"); // Appliquer le style en gras

                        //Label userNameLabel = new Label("User Name: " + donation.getAccountKey());
                        //  Label userSurnameLabel = new Label("User Surname: " + donation.getAccountKey());








                        container.getChildren().addAll(donationMAmount, donationMDate);
                        setGraphic(container);

                    }
                }
            });
            listDonationHistory.getSelectionModel().selectedItemProperty().addListener((obs, oldDonation, newDonation) -> {
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
    private void showDonationHistory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationM/HistoryDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnShowHistory.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
    @FXML
    public void NavigateToIndexBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/indexFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnindexb.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayAnimal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/DisplayAnimal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnanimal.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }


    @FXML
    public void NavigateToAddBoarding() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/AddBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnboarding.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnproduct.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationM() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationM/AddDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationm.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationP() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationP/AddDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationp.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
}
