package controllers.Back.User;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.IOException;
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
    private TableColumn<User, String> ColumnAddress;

    @FXML
    private TableColumn<User, String> ColumnEmail;

    @FXML
    private TableColumn<User, String> ColumnGender;

    @FXML
    private TableColumn<User, String> ColumnName;

    @FXML
    private TableColumn<User, String> ColumnPhone;

    @FXML
    private TableColumn<User, String> ColumnRole;

    @FXML
    private TableColumn<User, String> ColumnStatus;

    @FXML
    private TableColumn<User, String> ColumnSurname;

    @FXML
    private TableColumn<User, Integer> ColumnId;

    @FXML
    private TableView<User> TableUsers;

    private final ServiceUser userService;

    public DisplayUserB() {
        userService = new ServiceUser();
    }



    @FXML
    private void initialize() {
        try {
            // Set cell value factories to populate table columns
            ColumnId.setCellValueFactory(new PropertyValueFactory<>("accountId"));
            ColumnId.setVisible(false); // Hide the ID column
            ColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            ColumnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            ColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            ColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            ColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            ColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            ColumnRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            ColumnStatus.setCellValueFactory(new PropertyValueFactory<>("accountStatus"));

            // Call the Show method to retrieve user data and populate the TableView
            List<User> users = userService.Show();
            TableUsers.getItems().addAll(users);

            // Add the action column
            TableColumn<User, Void> actionColumn = new TableColumn<>("Action");
            actionColumn.setPrefWidth(200);
            actionColumn.setCellFactory(param -> new TableCell<User, Void>() {
                private final Button deleteButton = new Button("Delete");
                private final Button updateButton = new Button("Update");

                {
                    deleteButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        try {
                            userService.delete(user);
                            TableUsers.getItems().remove(user);
                        } catch (SQLException e) {
                            e.printStackTrace(); // Handle SQLException
                        }
                    });

                    updateButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        try {
                            // Load the FXML for the update user page
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/User/UpdateUser.fxml"));
                            Parent root = loader.load();

                            // Get the controller for the update user page
                            UpdateUserB controller = loader.getController();

                            // Pass the selected user to the controller
                            controller.initData(user);

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
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new HBox(5, deleteButton, updateButton));
                    }
                }
            });

            TableUsers.getColumns().add(actionColumn);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
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


    // Method to refresh TableView data

}
