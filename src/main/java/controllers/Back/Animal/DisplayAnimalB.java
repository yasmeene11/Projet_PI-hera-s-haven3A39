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
import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceBoarding;
import services.ServiceUser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.InputStream;
import java.net.URL;



import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

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

    @FXML
    private CheckBox chkName;

    @FXML
    private CheckBox chkBreed;

    @FXML
    private CheckBox chkType;

    @FXML
    private CheckBox chkAge;

    @FXML
    private TextField fieldSearch;

    private final ServiceAnimal animalService;
    private final ServiceAdoption adoptionService;
    private final ServiceBoarding boardingService;

    public DisplayAnimalB() {
        animalService = new ServiceAnimal();
        adoptionService = new ServiceAdoption();
        boardingService = new ServiceBoarding();
    }


    @FXML
    private void initialize() {
        try {
            List<Animal> animals = animalService.Show();
            ListAnimals.getItems().clear(); // Clear the ListView before adding items

            // Create a Set to store unique animals
            Set<Animal> uniqueAnimals = new HashSet<>(animals);

            ListAnimals.getItems().addAll(uniqueAnimals);

            ListAnimals.setCellFactory(param -> new ListCell<Animal>() {
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
                        Label animaldescriptionLabel = new Label("Animal Description: " + animal.getAnimal_Description());

                        // Use an ImageView for the animal image
                        ImageView imageView = new ImageView();

                        // Check if the image file exists in the animal_images directory
                        InputStream imageStream = getClass().getResourceAsStream("/animal_images/" + animal.getAnimal_Image());
                        if (imageStream != null) {
                            imageView.setImage(new Image(imageStream)); // Load image from animal_images directory
                        } else {
                            // Load image directly from its original location
                            String originalImagePath = "file:///" + animal.getAnimal_Image();
                            imageView.setImage(new Image(originalImagePath));
                        }

                        imageView.setFitWidth(100); // Set image width
                        imageView.setFitHeight(100); // Set image height

                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button updateButton = new Button("Update");
                        updateButton.getStyleClass().add("user-button");
                        updateButton.setOnAction(event -> handleUpdate(animal));

                        Button deleteButton = new Button("Delete");
                        deleteButton.getStyleClass().add("user-button");
                        deleteButton.setOnAction(delete -> handleDelete(animal));

                        buttonBox.getChildren().addAll(updateButton, deleteButton);

                        container.getChildren().addAll(animalnameLabel, animalbreedLabel, animalstatusLabel, animaltypeLabel, ageLabel, enrollementdatelabel, animaldescriptionLabel, imageView, buttonBox);
                        setGraphic(container);
                    }
                }
            });

            ListAnimals.getSelectionModel().selectedItemProperty().addListener((obs, oldAnimal, newAnimal) -> {
                if (newAnimal != null) {
                    // Handle user selection here
                }
            });

            // Add listener to the search field
            fieldSearch.textProperty().addListener((obs, oldValue, newValue) -> handleSearch(newValue.trim()));

            // Add listeners to the checkboxes
            chkName.setOnAction(event -> handleSort());
            chkBreed.setOnAction(event -> handleSort());
            chkType.setOnAction(event -> handleSort());
            chkAge.setOnAction(event -> handleSort());

            // Initially, sort by name
            handleSort();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Animal> filterAnimals(String searchText, List<Animal> animals) {
        List<Animal> filteredAnimals = new ArrayList<>();
        String searchTextLowerCase = searchText.toLowerCase();

        for (Animal animal : animals) {
            String animalName = animal.getAnimal_Name().toLowerCase();
            String animalBreed = animal.getAnimal_Breed().toLowerCase();
            String animalType = animal.getAnimal_Type().toLowerCase();
            String ageString = Integer.toString(animal.getAge()).toLowerCase(); // Convert int to String

            if (animalName.contains(searchTextLowerCase) ||
                    animalBreed.contains(searchTextLowerCase) ||
                    animalType.contains(searchTextLowerCase) ||
                    ageString.contains(searchTextLowerCase)) {
                filteredAnimals.add(animal);
            }
        }

        return filteredAnimals;
    }

    @FXML
    private void handleSearch(String searchText) {
        List<Animal> animals;

        try {
            animals = animalService.Show();
            List<Animal> filteredAnimals = filterAnimals(searchText, animals);
            ListAnimals.getItems().clear();
            ListAnimals.getItems().addAll(filteredAnimals);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSort() {
        // Retrieve animals from the ListView
        List<Animal> animals = new ArrayList<>(ListAnimals.getItems());

        // Apply sorting based on checked checkboxes
        if (chkName.isSelected()) {
            Collections.sort(animals, Comparator.comparing(Animal::getAnimal_Name));
        }
        if (chkBreed.isSelected()) {
            Collections.sort(animals, Comparator.comparing(Animal::getAnimal_Breed));
        }
        if (chkType.isSelected()) {
            Collections.sort(animals, Comparator.comparing(Animal::getAnimal_Type));
        }
        if (chkAge.isSelected()) {
            Collections.sort(animals, Comparator.comparingInt(Animal::getAge));
        }

        // Update the ListView with sorted animals
        ListAnimals.getItems().setAll(animals);
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
            // Delete related adoption records
            adoptionService.deleteByAnimalId(animal.getAnimalId());

            // Delete related pet boarding records
            boardingService.deleteByAnimalId(animal.getAnimalId());
            // Delete the animal
            animalService.delete(animal);

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

    @FXML
    public void NavigateToDisplayCalendar() throws IOException {
        loadFXML("/Back/Animal/Calendar.fxml");
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