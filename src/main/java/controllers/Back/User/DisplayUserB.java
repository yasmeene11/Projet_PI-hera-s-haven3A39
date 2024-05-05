package controllers.Back.User;

import entities.User;
import javafx.scene.image.Image;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.security.core.userdetails.UserDetailsService;
import services.ServiceUser;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class DisplayUserB {

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
    private Button btnlistuser;

    @FXML
    private ListView<User> LISTUSERS;

    private final ServiceUser userService;

    public DisplayUserB() {
        userService  = new ServiceUser();
    }


    @FXML
    private void initialize() {
        try {
            List<User> users = userService.Show();
            LISTUSERS.getItems().addAll(users);

            LISTUSERS.setCellFactory(param -> new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (empty || user == null) {
                        setText(null);
                    } else {
                        VBox container = new VBox(5);
                        container.getStyleClass().add("user-card");

                        Label userNameLabel = new Label("User Name: " + (user.getName() != null ? user.getName() : ""));
                        Label userLastNameLabel = new Label("User Last Name: " + (user.getSurname() != null ? user.getSurname() : ""));
                        Label userEmailLabel = new Label("User Email: " + (user.getEmail() != null ? user.getEmail() : ""));
                        Label userPhoneLabel = new Label("User Phone Number: " + (user.getPhoneNumber() != null ? user.getPhoneNumber() : ""));
                        Label accountStatusLabel = new Label("Account Status: " + (user.getAccountStatus() != null ? user.getAccountStatus() : ""));
                        Label userRoleLabel = new Label("User Role: " + (user.getRole() != null ? user.getRole() : ""));

                        // Add image view
                        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
                        System.out.println(user.getImage());
                        String imagePath = "/UserImages/" + user.getImage();
                        System.out.println(imagePath);
                        InputStream imageStream = getClass().getResourceAsStream(imagePath);
                        if (imageStream != null) {
                            try {
                                Image image = new Image(imageStream);
                                imageView.setImage(image);
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);
                                container.getChildren().add(imageView);
                            } catch (Exception e) {
                                System.err.println("Error loading image: " + e.getMessage());
                            } finally {
                                try {
                                    imageStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.err.println("Image not found: " + imagePath);
                        }

                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button updateButton = new Button("Update");
                        updateButton.getStyleClass().add("user-button");
                        updateButton.setOnAction(event -> {
                            handleUpdate(user);
                            try {
                                NavigateToDisplayUser();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        Button deleteButton = new Button("Delete");
                        deleteButton.getStyleClass().add("user-button");
                        deleteButton.setOnAction(event -> handleDelete(user));

                        buttonBox.getChildren().addAll(updateButton, deleteButton);

                        // Add labels to the container
                        container.getChildren().addAll(
                                userNameLabel, userLastNameLabel, userEmailLabel, userPhoneLabel,
                                accountStatusLabel, userRoleLabel, buttonBox
                        );
                        setGraphic(container);
                    }
                }
            });

            LISTUSERS.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
                if (newUser != null) {
                    // Handle user selection here
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(User user) {
        if (user != null) {
            try {
                // Load the FXML for the update user page
                System.out.println(user);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/User/UpdateBacktoBack.fxml"));
                Parent root = loader.load();

                // Get the controller for the update user page
                UpdateBacktoBack controller = loader.getController();

                // Pass the selected user to the controller
                controller.setUser(user);
                controller.initData(); // Call this method to initialize input fields

                // Create a new stage for the update user page
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update User");

                // Show the update stage without closing the main stage
                updateStage.initOwner(btnUser.getScene().getWindow());
                updateStage.initModality(Modality.WINDOW_MODAL);
                updateStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(); // Handle IOException
            }
        }
    }

    @FXML
    private void handleDelete(User user) {
        // Implement delete action here
        ServiceUser u = new ServiceUser();
        try {
            u.delete(user);
            // Deletion successful, now navigate to the display user screen to refresh the display
            NavigateToDisplayUser();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
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
