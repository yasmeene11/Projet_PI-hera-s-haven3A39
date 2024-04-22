package controllers.Back.Animal;

import entities.Adoption;
import entities.Boarding;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceAdoption;
import services.ServiceBoarding;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class DisplayBoardingB {

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
    private Button btnaddboarding;

    @FXML
    private Button btnlistboarding;

    @FXML
    private ListView<Boarding> ListBoardings;

    private final ServiceBoarding boardingService;

    public DisplayBoardingB() {
        boardingService = new ServiceBoarding();
    }

    @FXML
    private void initialize() {
        try {
            List<Boarding> boardings = boardingService.Show();
            ListBoardings.getItems().addAll(boardings);

            ListBoardings.setCellFactory(param -> new ListCell<Boarding>() {
                @Override
                protected void updateItem(Boarding boarding, boolean empty) {
                    super.updateItem(boarding, empty);
                    if (empty || boarding == null) {
                        setText(null);
                    } else {
                        VBox container = new VBox(5);
                        container.getStyleClass().add("user-card");

                        Label startdateLabel = new Label("Start Date : " + boarding.getStart_Date());
                        Label enddateLabel = new Label("End Date : " + boarding.getEnd_Date());
                        Label boardingstatusLabel = new Label("Pet Boarding Status: " + boarding.getBoarding_Status());
                        Label boardingfeeLabel = new Label("Pet Boarding Fee: " + boarding.getBoarding_Fee());
                        Label animalNameLabel = new Label("Animal Name: " + boarding.getAnimal_Key().getAnimal_Name());
                        // Assuming you have a method to get the animal image URL
                        String animalImageUrl = boarding.getAnimal_Key().getAnimal_Image();
                        // Create an ImageView to display the image
                        ImageView imageView = new ImageView();
                        String imagePath = getClass().getResource("/animal_images/" + boarding.getAnimal_Key().getAnimal_Image()).toExternalForm();
                        imageView.setImage(new Image(imagePath)); // Load image
                        imageView.setFitWidth(100); // Set image width
                        imageView.setFitHeight(100); // Set image height

                        // Display user information
                        Label userNameLabel = new Label("User Name: " + boarding.getAccount_Key().getName());
                        Label userSurnameLabel = new Label("User Surname: " + boarding.getAccount_Key().getSurname());


                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button updateButton = new Button("Update");
                        updateButton.getStyleClass().add("user-button");
                        updateButton.setOnAction(event -> handleUpdate(boarding));

                        Button deleteButton = new Button("Delete");
                        deleteButton.getStyleClass().add("user-button");
                        deleteButton.setOnAction(delete -> handleDelete(boarding));

                        buttonBox.getChildren().addAll(updateButton, deleteButton);

                        container.getChildren().addAll(startdateLabel,enddateLabel, boardingstatusLabel, boardingfeeLabel,animalNameLabel,imageView,userNameLabel,userSurnameLabel, buttonBox);
                        setGraphic(container);
                    }
                }
            });

            ListBoardings.getSelectionModel().selectedItemProperty().addListener((obs, oldBoarding, newBoarding) -> {
                if (newBoarding != null) {
                    // Handle user selection here
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(Boarding boarding) {
        if (boarding != null) {
            try {
                // Load the FXML for the update animal page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/UpdateBoarding.fxml"));
                Parent root = loader.load();

                // Get the controller for the update animal page
                UpdateBoardingB controller = loader.getController();

                // Pass the selected animal to the controller
                controller.initData(boarding);

                // Create a new stage for the update animal page
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update boarding");

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
    private void handleDelete(Boarding boarding) {
        try {
            boardingService.delete(boarding); // Assuming animalService is the correct service to use for deleting animals
            // Deletion successful, now refresh the display
            ListBoardings.getItems().remove(boarding); // Remove the deleted animal from the list
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
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
    public void NavigateToAddBoarding()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/AddBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddboarding.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
}
