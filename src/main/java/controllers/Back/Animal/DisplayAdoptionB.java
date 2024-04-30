package controllers.Back.Animal;

import entities.Adoption;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceAdoption;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DisplayAdoptionB {

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
    private Button btnaddadoption;

    @FXML
    private Button btnlistadoption;

    @FXML
    private ListView<Adoption> ListAdoptions;

    private final ServiceAdoption adoptionService;

    public DisplayAdoptionB() {
        adoptionService = new ServiceAdoption();
    }
    @FXML
    private GridPane statsGrid;




    @FXML
    private void initialize() {
        try {
            // Initialize statistics variables
            int totalAdoptions = 0;
            int completedAdoptions = 0;
            int cancelledAdoptions = 0;
            int pendingAdoptions = 0;

            for (int i = 0; i < 4; i++) {
                ColumnConstraints column = new ColumnConstraints();
                column.setPercentWidth(25); // Each column takes up 25% of the width
                statsGrid.getColumnConstraints().add(column);
            }

            List<Adoption> adoptions = adoptionService.Show();

            // Iterate through the adoptions to calculate statistics
            for (Adoption adoption : adoptions) {
                switch (adoption.getAdoption_Status()) {
                    case "Completed":
                        completedAdoptions++;
                        break;
                    case "Cancelled":
                        cancelledAdoptions++;
                        break;
                    case "Pending":
                        pendingAdoptions++;
                        break;
                }
                // Increment total adoptions
                totalAdoptions++;
            }

            // Display statistics in the grid pane
            Label totalAdoptionsLabel = new Label("Total Adoptions: " + totalAdoptions);
            Label completedAdoptionsLabel = new Label("Completed Adoptions: " + completedAdoptions);
            Label cancelledAdoptionsLabel = new Label("Cancelled Adoptions: " + cancelledAdoptions);
            Label pendingAdoptionsLabel = new Label("Pending Adoptions: " + pendingAdoptions);

            statsGrid.add(totalAdoptionsLabel, 0, 0);
            statsGrid.add(completedAdoptionsLabel, 1, 0);
            statsGrid.add(cancelledAdoptionsLabel, 2, 0);
            statsGrid.add(pendingAdoptionsLabel, 3, 0);


           // List<Adoption> adoptions = adoptionService.Show();
            ListAdoptions.getItems().addAll(adoptions);

            ListAdoptions.setCellFactory(param -> new ListCell<Adoption>() {
                @Override
                protected void updateItem(Adoption adoption, boolean empty) {
                    super.updateItem(adoption, empty);
                    if (empty || adoption == null) {
                        setText(null);
                    } else {
                        VBox container = new VBox(5);
                        container.getStyleClass().add("user-card");

                        Label adoptiondateLabel = new Label("Adoption Date : " + adoption.getAdoption_Date());
                        Label adoptionstatusLabel = new Label("Adoption Status: " + adoption.getAdoption_Status());
                        Label adoptionfeeLabel = new Label("Adoption Fee: " + adoption.getAdoption_Fee());
                        Label animalNameLabel = new Label("Animal Name: " + adoption.getAnimal_Key().getAnimal_Name());
                        // Assuming you have a method to get the animal image URL
                        String animalImageUrl = adoption.getAnimal_Key().getAnimal_Image();
                        // Create an ImageView to display the image
                        ImageView imageView = new ImageView();
                        String imagePath = getClass().getResource("/animal_images/" + adoption.getAnimal_Key().getAnimal_Image()).toExternalForm();
                        imageView.setImage(new Image(imagePath)); // Load image
                        imageView.setFitWidth(100); // Set image width
                        imageView.setFitHeight(100); // Set image height

                        // Display user information
                        Label userNameLabel = new Label("User Name: " + adoption.getAccount_Key().getName());
                        Label userSurnameLabel = new Label("User Surname: " + adoption.getAccount_Key().getSurname());


                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button updateButton = new Button("Update");
                        updateButton.getStyleClass().add("user-button");
                        updateButton.setOnAction(event -> handleUpdate(adoption));

                        Button deleteButton = new Button("Delete");
                        deleteButton.getStyleClass().add("user-button");
                        deleteButton.setOnAction(delete -> handleDelete(adoption));

                        buttonBox.getChildren().addAll(updateButton, deleteButton);

                        container.getChildren().addAll(adoptiondateLabel, adoptionstatusLabel, adoptionfeeLabel,animalNameLabel,imageView,userNameLabel,userSurnameLabel, buttonBox);
                        setGraphic(container);
                    }
                }
            });

            ListAdoptions.getSelectionModel().selectedItemProperty().addListener((obs, oldAdoption, newAdoption) -> {
                if (newAdoption != null) {
                    // Handle user selection here
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(Adoption adoption) {
        if (adoption != null) {
            try {
                // Load the FXML for the update animal page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/UpdateAdoption.fxml"));
                Parent root = loader.load();

                // Get the controller for the update animal page
                UpdateAdoptionB controller = loader.getController();

                // Pass the selected animal to the controller
                controller.initData(adoption);

                // Create a new stage for the update animal page
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update Adoption");

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
    private void handleDelete(Adoption adoption) {
        try {
            adoptionService.delete(adoption); // Assuming animalService is the correct service to use for deleting animals
            // Deletion successful, now refresh the display
            ListAdoptions.getItems().remove(adoption); // Remove the deleted animal from the list
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }


    @FXML
    private void NavigateToDisplayUser() throws IOException {
        loadFXML("/Back/User/DisplayUser.fxml");
    }

    @FXML
    private void NavigateToDisplayAnimal() throws IOException {
        loadFXML("/Back/Animal/DisplayAnimal.fxml");
    }

    @FXML
    private void NavigateToDisplayAdoption() throws IOException {
        loadFXML("/Back/Animal/DisplayAdoption.fxml");
    }

    @FXML
    private void NavigateToIndexBack() throws IOException {
        loadFXML("/Back/indexBack.fxml");
    }

    @FXML
    private void NavigateToDisplayAppointment() throws IOException {
        loadFXML("/Back/Appointment/DisplayAppointment.fxml");
    }

    @FXML
    public void NavigateToDisplayBoarding() throws IOException {
        loadFXML("/Back/Animal/DisplayBoarding.fxml");
    }

    @FXML
    public void NavigateToDisplayCashRegister() throws IOException {
        loadFXML("/Back/CashRegister/DisplayCashRegister.fxml");
    }

    @FXML
    public void NavigateToDisplayCategory() throws IOException {
        loadFXML("/Back/Category/DisplayCategory.fxml");
    }

    @FXML
    public void NavigateToDisplayDonation() throws IOException {
        loadFXML("/Back/DisplayDonation.fxml");
    }

    @FXML
    public void NavigateToDisplayProduct() throws IOException {
        loadFXML("/Back/Product/DisplayProduct.fxml");
    }

    @FXML
    public void NavigateToDisplayReport() throws IOException {
        loadFXML("/Back/Appointment/DisplayReport.fxml");
    }


    @FXML
    public void NavigateToAddAdoption()throws IOException {
        loadFXML("/Back/Animal/AddAdoption.fxml");


    }
    private void loadFXML(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = (Stage) btnUser.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }



}
