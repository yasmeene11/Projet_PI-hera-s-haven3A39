package controllers.Back.Category;

import controllers.Back.Animal.UpdateAnimalB;
import entities.Category;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceCategory;
import services.ServiceProduct;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayCategoryB {

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
    private Button btnaddcategory;

    @FXML
    private Button btnlistcategory;

    @FXML
    private ListView<Category> categoryListView;

    private final ServiceCategory servicecat;

    public DisplayCategoryB(){servicecat = new ServiceCategory();}
    @FXML
    private void initialize() {
        try {
            List<Category> categories = servicecat.Show();
            categoryListView.getItems().addAll(categories);

            categoryListView.setCellFactory(param -> new ListCell<Category>() {
                @Override
                protected void updateItem(Category category, boolean empty) {
                    super.updateItem(category, empty);
                    if (empty || category == null) {
                        setText(null);
                    } else {
                        VBox container = new VBox(5);
                        container.getStyleClass().add("Category-card");

                        Label productTypeLabel = new Label("Product Type: " + category.getProduct_Type());
                        Label productSourceLabel = new Label("Product Source: " + category.getProduct_Source());

                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button updateButton = new Button("Update");
                        updateButton.getStyleClass().add("Category-button");
                        updateButton.setOnAction(event -> handleUpdate(category));

                        Button deleteButton = new Button("Delete");
                        deleteButton.getStyleClass().add("Category-button");
                        deleteButton.setOnAction(event -> handleDelete(category));

                        buttonBox.getChildren().addAll(updateButton, deleteButton);

                        container.getChildren().addAll(productTypeLabel, productSourceLabel, buttonBox);
                        setGraphic(container);
                    }
                }
            });

            categoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldCategory, newCategory) -> {
                if (newCategory != null) {
                    // Handle user selection here
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(Category category) {
        if (category != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/UpdateCategory.fxml"));
                Parent root = loader.load();
                UpdateCategoryB controller = loader.getController();
                controller.initData(category);
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update Category");
                updateStage.initOwner(btnCategory.getScene().getWindow());
                updateStage.initModality(Modality.WINDOW_MODAL);
                updateStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace(); // Handle IOException
            }
        }
    }
    @FXML
    private void handleDelete(Category category) {
        try {
            servicecat.deleteProductsByCategory(category);
            servicecat.delete(category); // Assuming animalService is the correct service to use for deleting animals
            categoryListView.getItems().remove(category); // Remove the deleted animal from the list
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
    public void NavigateToDisplayBoarding() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Animal/DisplayBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBoarding.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCashRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CashRegister/DisplayCashRegister.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCash.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayCategory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/DisplayCategory.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayDonation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DisplayDonation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnDonation.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnProduct.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    public void NavigateToDisplayReport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayReport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReport.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
    @FXML
    public void NavigateToAddCategory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/AddCategory.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddcategory.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }


}
