package controllers.Front.Animal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entities.Animal;
import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceBoarding;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DisplayAnimalF {

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
    private MenuBar menuBar;

    @FXML
    private Button btncat;

    @FXML
    private Button btndog;

    @FXML
    private MenuItem btnindexb;

    @FXML
    private ListView<Animal> ListAnimals;

    private final ServiceAnimal animalService;

    public DisplayAnimalF() {
        animalService = new ServiceAnimal();
    }

    @FXML
    private void initialize() {
        try {
            List<Animal> animals = animalService.Show();

            // Filter out only the animals with status "Available"
            List<Animal> availableAnimals = animals.stream()
                    .filter(animal -> animal.getAnimal_Status().equals("Available"))
                    .collect(Collectors.toList());

            // Add filtered animals to the ListView
            ListAnimals.setItems(FXCollections.observableArrayList(availableAnimals));

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
                        Label ageLabel = new Label("Age: " + animal.getAge());

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

                        // Buttons
                        Button adoptButton = new Button("Adopt Now");
                        Button descriptionButton = new Button("Description");

                        adoptButton.setOnAction(event -> {
                            // Get the selected animal's ID
                            int selectedAnimalId = getItem().getAnimalId();

                            // Open the AddAdoptionF page
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/AddAdoption.fxml"));
                                Parent root = loader.load();

                                // Get the controller of the AddAdoptionF page
                                AddAdoptionF controller = loader.getController();

                                // Pass the selected animal's ID to the controller
                                controller.setSelectedAnimalId(selectedAnimalId);

                                // Retrieve the stage from the event
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.setTitle("Add Adoption");
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        descriptionButton.setOnAction(event -> {
                            // Get the selected animal's ID
                            int selectedAnimalId = getItem().getAnimalId();

                            // Open the description pop-up window
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/DescriptionPage.fxml"));
                                Parent root = loader.load();

                                // Get the controller of the description pop-up window
                                DescriptionPage controller = loader.getController();

                                // Pass the selected animal's ID to the controller
                                controller.initData(selectedAnimalId);

                                // Create a new stage for the pop-up window
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.setTitle("Animal Description");
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);
                        buttonBox.getChildren().addAll(adoptButton, descriptionButton);

                        container.getChildren().addAll(animalnameLabel, animalbreedLabel, animalstatusLabel, ageLabel, imageView, buttonBox);
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
    private void onFilterDogsClicked(ActionEvent event) {
        filterByType("Dog");
    }

    @FXML
    private void onFilterCatsClicked(ActionEvent event) {
        filterByType("Cat");
    }

    private void filterByType(String type) {
        try {
            // Get all animals from the service
            List<Animal> animals = animalService.Show();

            // Filter out only the animals with status "Available" and the specified type
            List<Animal> filteredAnimals = animals.stream()
                    .filter(animal -> animal.getAnimal_Status().equals("Available") && animal.getAnimal_Type().equals(type))
                    .collect(Collectors.toList());

            // Clear the ListView
            ListAnimals.getItems().clear();

            // Add filtered animals to the ListView
            ListAnimals.setItems(FXCollections.observableArrayList(filteredAnimals));

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationM/DisplayDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationm.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationP() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationP/DisplayDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationp.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
}
