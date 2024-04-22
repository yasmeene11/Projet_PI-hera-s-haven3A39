package controllers.Front.Product;

import entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceProduct;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class DisplayProductF {
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
    private MenuItem btnindexb;
    @FXML
    private ListView<Product> ProductListView;
    private final ServiceProduct serviceprod;
    public DisplayProductF(){serviceprod = new ServiceProduct();}
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

    @FXML
    public void initialize() {
        try {
            List<Product> products = serviceprod.Show();
            ProductListView.getItems().addAll(products);

            ProductListView.setCellFactory(param -> new ListCell<Product>() {
                @Override
                protected void updateItem(Product product, boolean empty) {
                    super.updateItem(product, empty);
                    if (empty || product == null) {
                        setText(null);
                    } else {
                        VBox container = new VBox(9);
                        container.getStyleClass().add("Product-card");

                        Label productNameLabel = new Label("Product Name: " + product.getProductName());
                        Label productLabelLabel = new Label("Product Label: " + product.getProductLabel());
                        Label CategoryLabel = new Label("Category: " + product.getCategoryKey());

                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button SeeButton = new Button("See");
                        SeeButton.getStyleClass().add("Category-button");
                        SeeButton.setOnAction(event -> handleSee(product));


                        buttonBox.getChildren().addAll(SeeButton);

                        container.getChildren().addAll(productNameLabel,productLabelLabel, buttonBox);
                        setGraphic(container);
                    }
                }
            });

            ProductListView.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
                if (newProduct != null) {
                    // Handle user selection here
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void handleSee(Product product) {
        String category = product.getCategoryKey().getProduct_Type();
        String link = "";

        // Determine the link based on the category
        switch (category) {
            case "Food Supplies":
                link = "https://zanimo.tn/category/1/products";
                break;
            case "Medical":
                link = "https://zanimo.tn/category/31/products";
                break;
            case "Hygienix":
                link = "https://zanimo.tn/category/33/product";
                break;
                default:
                // Default link if category not recognized
                link = "https://zanimo.tn";
                break;
        }

        // Open the link in a browser
        if (!link.isEmpty()) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
