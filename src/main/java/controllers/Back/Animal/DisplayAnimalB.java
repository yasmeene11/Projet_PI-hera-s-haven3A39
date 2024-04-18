package controllers.Back.Animal;

import entities.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import services.ServiceAnimal;
import services.ServiceUser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DisplayAnimalB {

    @FXML
    private ListView<Animal> ListAnimals;


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
    private Button btnaddanimal;

    @FXML
    private Button btnlistanimal;

    private final ServiceAnimal animalService;

    public DisplayAnimalB() {
        animalService = new ServiceAnimal();
    }




    @FXML
    private void initialize() {
        try {
            List<Animal> animals = animalService.Show();
            ListAnimals.getItems().addAll(animals);

            ListAnimals.setCellFactory(param -> new ListCell<Animal>() {
                @Override
                protected void updateItem(Animal animal, boolean empty) {
                    super.updateItem(animal, empty);
                    if (empty || animal == null) {
                        setText(null);
                    } else {
                        VBox container = new VBox(5);
                        container.getStyleClass().add("user-card");

                        Label animalnameLabel = new Label("Animal Name: " + animal.getAnimal_Name());
                        Label animalbreedLabel = new Label("Animal Breed: " + animal.getAnimal_Breed());
                        Label animalstatusLabel = new Label("Animal Status: " + animal.getAnimal_Status());
                        Label animaltypeLabel = new Label("Animal Type: " + animal.getAnimal_Type());
                        Label ageLabel = new Label("Age: " + animal.getAge());
                        Label enrollementdatelabel = new Label("Enrollment Date: " + animal.getEnrollement_Date());

                        // Use an ImageView for the animal image
                        ImageView imageView = new ImageView();
                        String imagePath = getClass().getResource("/animal_images/" + animal.getAnimal_Image()).toExternalForm();
                        imageView.setImage(new Image(imagePath)); // Load image
                        imageView.setFitWidth(100); // Set image width
                        imageView.setFitHeight(100); // Set image height

                        Label animaldescriptionLabel = new Label("Animal Description: " + animal.getAnimal_Description());

                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button updateButton = new Button("Update");
                        updateButton.getStyleClass().add("user-button");
                        updateButton.setOnAction(event -> handleUpdate(animal));

                        Button deleteButton = new Button("Delete");
                        deleteButton.getStyleClass().add("user-button");
                        deleteButton.setOnAction(delete -> handleDelete(animal));

                        buttonBox.getChildren().addAll(updateButton, deleteButton);

                        container.getChildren().addAll(animalnameLabel, animalbreedLabel, animalstatusLabel, animaldescriptionLabel, imageView, buttonBox);
                        setGraphic(container);
                    }
                }
            });

            ListAnimals.getSelectionModel().selectedItemProperty().addListener((obs, oldAnimal, newAnimal) -> {
                if (newAnimal != null) {
                    // Handle user selection here
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(Animal animal) {
        if (animal != null) {
            try {
                // Load the FXML for the update animal page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/UpdateAnimal.fxml"));
                Parent root = loader.load();

                // Get the controller for the update animal page
                UpdateAnimalB controller = loader.getController();

                // Pass the selected animal to the controller
                controller.initData(animal);

                // Create a new stage for the update animal page
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update Animal");

                // Show the update stage without closing the main stage
                updateStage.initOwner(btnAnimal.getScene().getWindow());
                updateStage.initModality(Modality.WINDOW_MODAL);
                updateStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(); // Handle IOException
            }
        }
    }


    @FXML
    private void handleDelete(Animal animal) {
        try {
            animalService.delete(animal); // Assuming animalService is the correct service to use for deleting animals
            // Deletion successful, now refresh the display
            ListAnimals.getItems().remove(animal); // Remove the deleted animal from the list
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
    public void NavigateToAddAnimal()throws IOException {
        loadFXML("/Back/Animal/AddAnimal.fxml");


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
