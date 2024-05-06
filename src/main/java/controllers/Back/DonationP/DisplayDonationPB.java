package controllers.Back.DonationP;

import entities.DonationM;
import entities.DonationP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceDonationM;
import services.ServiceDonationP;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DisplayDonationPB {

    @FXML

    private ListView<DonationP> listDonations;
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
    private Button btnlistdonationp;
    private final ServiceDonationP donationPService;

    public DisplayDonationPB() {
        donationPService = new ServiceDonationP();
    }
    @FXML
    private void initialize() {
        try {
            List<DonationP> donations = donationPService.Show();
            listDonations.getItems().addAll(donations);
            // Set cell value factories to populate ListView items
            listDonations.setCellFactory(param -> new ListCell<DonationP>() {
                @Override
                protected void updateItem(DonationP donation, boolean empty) {
                    super.updateItem(donation, empty);
                    if (empty || donation == null) {
                        setText(null);
                    } else {
                    /*setText("Id: " + donation.getDonationPId() +
                            "Name: " + donation.getDonation_product_name() + ", Quantity: " + donation.getDonation_product_quantity() +
                            ", Label: " + donation.getDonation_product_label() + ", Expiration Date: " + donation.getDonation_product_expiration_date() +
                            ", Date: " + donation.getDonation_p_date() + ", Type: " + donation.getDonation_p_type() +
                            ", Account Key: " + donation.getAccount_Key());*/
                        VBox container = new VBox(5);
                        container.getStyleClass().add("user-card");
                        Label productNameLabel = new Label("Product Name: " + donation.getDonation_product_name());
                        Label productQuantityLabel = new Label("Product Quantity: " + donation.getDonation_product_quantity());
                        Label productLabelLabel = new Label("Product Label: " + donation.getDonation_product_label());
                        Label productExpirationDateLabel = new Label("Expiration Date: " + donation.getDonation_product_expiration_date());
                        Label donationDateLabel = new Label("Donation Date: " + donation.getDonation_p_date());
                        Label donationTypeLabel = new Label("Product Type: " + donation.getDonation_p_type());
                        Label accountKeyLabel = new Label("Account Key: " + donation.getAccount_Key());

                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button updateButton = new Button("Update");
                        updateButton.getStyleClass().add("user-button");
                        updateButton.setOnAction(event -> handleUpdate(donation));

                        Button deleteButton = new Button("Delete");
                        deleteButton.getStyleClass().add("user-button");
                        deleteButton.setOnAction(delete -> handleDelete(donation));

                        buttonBox.getChildren().addAll(updateButton, deleteButton);

                        container.getChildren().addAll(productNameLabel, productQuantityLabel, productLabelLabel,
                                productExpirationDateLabel, donationDateLabel, donationTypeLabel, accountKeyLabel,
                                buttonBox);
                        setGraphic(container);
                    }
                }
            });
            listDonations.getSelectionModel().selectedItemProperty().addListener((obs, oldDonation, newDonation) -> {
                if (newDonation != null) {
                    // Handle donation selection here
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }
    }


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
    private void handleUpdate(DonationP donationP) {
        if (donationP != null) {
            try {
                // Charger le FXML pour la page de mise à jour de la donation
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationP/UpdateDonationP.fxml"));
                Parent root = loader.load();

                // Obtenir le contrôleur pour la page de mise à jour de la donation
                UpdateDonationPB controller = loader.getController();

                // Passer la donation sélectionnée au contrôleur
                controller.initData(donationP);

                // Créer une nouvelle scène pour la page de mise à jour de la donation
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Mise à jour de la donation");

                // Afficher la scène de mise à jour sans fermer la scène principale
                updateStage.initOwner(btnAdoption.getScene().getWindow());
                updateStage.initModality(Modality.WINDOW_MODAL);
                updateStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'IOException
            }
        }
    }

    @FXML
    private void handleDelete(DonationP donationP) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Donation");
        alert.setContentText("Are you sure you want to delete this donation?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                donationPService.delete(donationP); // Assuming donationPService is the correct service to use for deleting donations
                // Deletion successful, now refresh the display
                listDonations.getItems().remove(donationP); // Remove the deleted donation from the list
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception appropriately
            }
        }
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
    public void NavigateToDisplayDonationP()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationP/DisplayDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnlistdonationp.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToAddDonationP()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationP/AddDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnadddonationp.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
}
