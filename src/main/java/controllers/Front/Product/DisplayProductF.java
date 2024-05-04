package controllers.Front.Product;

import com.jfoenix.controls.JFXTextField;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import services.ServiceProduct;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

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
    private Button Food;
    @FXML
    private Button Medical;
    @FXML
    private Button Hygienic;
    @FXML
    private JFXTextField searchField;

    @FXML
    private ListView<Product> ProductListView;
    private ObservableList<Product> allProducts;

    private final ServiceProduct serviceprod;

    public DisplayProductF() {
        serviceprod = new ServiceProduct();
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

    @FXML
    public void initialize() {
        try {
            allProducts = FXCollections.observableArrayList(serviceprod.Show());
            ProductListView.getItems().addAll(allProducts);
            //List<Product> allProducts = serviceprod.Show();
            //ProductListView.getItems().addAll(allProducts);

            // Set cell factory for list view
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
                        Label categoryLabel = new Label("Category: " + product.getCategoryKey().getProduct_Type());

                        Rating ratingControl = new Rating();
                        ratingControl.setRating(product.getRating());
                        ratingControl.setMax(5); // Maximum rating
                        ratingControl.setDisable(false); // Enable rating control

                        ratingControl.ratingProperty().addListener((observable, oldValue, newValue) -> {
                            // Update the product's rating when the user changes the rating
                            product.setRating(newValue.intValue());
                            try {
                                // Update the rating in the database
                                serviceprod.updateProductRating(product.getProductId(), newValue.intValue());
                            } catch (SQLException e) {
                                e.printStackTrace();
                                // Handle the exception appropriately
                            }
                        });

                        ImageView imageView = new ImageView();
                        InputStream imageStream = getClass().getResourceAsStream("/product_images/" + product.getProductImage());
                        if (imageStream != null) {
                            imageView.setImage(new javafx.scene.image.Image(imageStream)); // Load image from animal_images directory
                        } else {
                            String originalImagePath = "file:///" + product.getProductImage();
                            imageView.setImage(new Image(originalImagePath));
                        }

                        imageView.setFitWidth(100); // Set image width
                        imageView.setFitHeight(100); // Set image heightt
                        HBox buttonBox = new HBox(10);
                        buttonBox.setAlignment(Pos.CENTER);

                        Button seeButton = new Button("See");
                        Button mapbutton= new Button("See on Map");

                        seeButton.getStyleClass().add("Category-button");
                        seeButton.setOnAction(event -> handleSee(product));

                        mapbutton.getStyleClass().add("Category-button");
                        mapbutton.setOnAction(event -> seeOnMapButtonClicked());
                        buttonBox.getChildren().addAll(seeButton,mapbutton);

                        container.getChildren().addAll(productNameLabel, productLabelLabel, imageView, categoryLabel, ratingControl, buttonBox);
                        setGraphic(container);
                        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchProduct(newValue));

                    }

                }
            });

            // Event handlers for filter buttons
            Food.setOnAction(event -> filterByCategory("Food Supplies"));
            Medical.setOnAction(event -> filterByCategory("Medical"));
            Hygienic.setOnAction(event -> filterByCategory("Hygienic"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchProduct(String keyword) {
        ProductListView.getItems().clear();

        // Check if there are products to search through
        if (allProducts == null || allProducts.isEmpty()) {
            System.out.println("No products found. allProducts is empty or null.");
            return;
        }

        // If the keyword is empty, add all products back to the list view
        if (keyword == null || keyword.isEmpty()) {
            ProductListView.getItems().addAll(allProducts);
        } else {
            // If there is a keyword, filter products based on it
            for (Product product : allProducts) {
                if (product != null && product.getProductName() != null &&
                        product.getProductName().toLowerCase().contains(keyword.toLowerCase())) {
                    ProductListView.getItems().add(product);
                }
            }
        }
    }
    private void filterByCategory(String category) {
        try {
            // Clear existing items from the list view
            ProductListView.getItems().clear();

            // Retrieve products based on the selected category
            List<Product> filteredProducts = serviceprod.filterByCategory(category);

            // Add filtered products to the list view
            ProductListView.getItems().addAll(filteredProducts);
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
            case "Hygienic":
                link = "https://zanimo.tn/category/33/product";
                break;
            default:
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

    public void openGoogleMaps(List<String> locations) {
        StringBuilder urlBuilder = new StringBuilder("https://www.google.com/maps/search/?api=1");

        // Append each location to the URL
        for (String location : locations) {
            // Replace any spaces in the location with '+' for the query parameter
            String encodedLocation = location.replace(" ", "+");
            urlBuilder.append("&query=").append(encodedLocation);
        }

        try {
            // Open the URL in the default web browser
            Desktop.getDesktop().browse(new URI(urlBuilder.toString()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
    @FXML
    private void seeOnMapButtonClicked() {
        // List of locations to display on Google Maps
        List<String> locations = List.of(
                "Zanimo,Tunis"

        );

        // Open Google Maps with the specified locations
        openGoogleMaps(locations);
    }

    }
