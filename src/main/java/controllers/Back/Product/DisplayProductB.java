package controllers.Back.Product;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import java.awt.Graphics;

import javafx.scene.layout.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;

import java.io.File;
import java.io.IOException;
import entities.Product;
import javafx.scene.image.Image;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.sql.SQLException;
import java.util.*;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javafx.scene.image.Image;
import services.ServicePD;
import services.ServiceProduct;
import utils.MyBD;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import static services.ServicePD.con;

public class DisplayProductB {

    @FXML
    private Button btnAdoption;

    @FXML
    private Button btnAnimal;

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnBoarding;
    @FXML
    private JFXTextField searchField;
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
    private Button btnaddproduct;
    @FXML
    private Button btnPD;
    private ObservableList<Product> allProducts;

    @FXML
    private Button btnlistproduct;

    @FXML
    private ListView<Product> ProductListView;
    private final ServiceProduct serviceprod;
    @FXML
    public void NavigateToAddProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/AddProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddproduct.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Product");
        stage.show();
    }

    public DisplayProductB() {
        serviceprod = new ServiceProduct();
    }



    @FXML
    public void initialize() throws SQLException {
        try {
            List<Product> products = serviceprod.Show();
            allProducts = FXCollections.observableArrayList(products);
            ProductListView.setItems(allProducts);
ServicePD pd= new ServicePD();
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
                        Label productQuantityLabel = new Label("Product Quantity: " + product.getProductQuantity());
                        Label expirationDateLabel = new Label("Expiration Date: " + product.getExpirationDate());
                        Label categoryLabel = new Label("Category: " + product.getCategoryKey().getProduct_Type());
                        int donationCount = pd.getDonationCount(product.getProductId());
                        Label donationCountLabel = new Label("Number of Donations: " + donationCount);
                        ImageView imageView = new ImageView();
                        InputStream imageStream = getClass().getResourceAsStream("/product_images/" + product.getProductImage());
                        if (imageStream != null) {
                            imageView.setImage(new Image(imageStream)); // Load image from product_images directory
                        } else {
                            String originalImagePath = "file:///" + product.getProductImage();
                            imageView.setImage(new Image(originalImagePath));
                        }
                        imageView.setFitWidth(100); // Set image width
                        imageView.setFitHeight(100); // Set image height

                        HBox buttonBox = new HBox(10);

                        Button updateButton = new Button("Update");
                        updateButton.setOnAction(event -> handleUpdate(product));

                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(event -> handleDelete(product));

                        buttonBox.getChildren().addAll(updateButton, deleteButton);

                        container.getChildren().addAll(productNameLabel, productLabelLabel, productQuantityLabel,
                                expirationDateLabel, categoryLabel,donationCountLabel,imageView, buttonBox);
                        setGraphic(container);
                    }
                }
            });
            searchField.textProperty().addListener((observable, oldValue, newValue) -> searchProduct(newValue));
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



    @FXML
    private void handleUpdate(Product product) {
        if (product != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Product/UpdateProduct.fxml"));
                Parent root = loader.load();
                UpdateProductB controller = loader.getController();
                controller.initData(product);
                Stage updateStage = new Stage();
                updateStage.setScene(new Scene(root));
                updateStage.setTitle("Update Product");
                updateStage.initOwner(btnProduct.getScene().getWindow());
                updateStage.initModality(Modality.WINDOW_MODAL);
                updateStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handleDelete(Product product) {
        try {
            serviceprod.delete(product);
            ProductListView.getItems().remove(product);
            NavigateToDisplayProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    private void NavigateToDisplayPD() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/ProductDonation/DisplayPD.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnPD.getScene().getWindow();
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


    public void generatePDF(MouseEvent mouseEvent) {
        String outputPath = "List_Product.pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.setLeading(20); // Set line spacing

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Product List");
                contentStream.newLine();
                contentStream.endText();

                List<Product> products = serviceprod.Show();
                float yPosition = 680;
                for (Product product : products) {
                    String imagePath = product.getProductImage();
                    Image image = loadImage(imagePath);
                    if (image == null) {
                        System.err.println("Error loading image: " + imagePath);
                        continue;
                    }
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(200, yPosition);
                    contentStream.showText("Product Name: " + product.getProductName());
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText("Product Label: " + product.getProductLabel());
                    contentStream.newLine();
                    contentStream.showText("Product Quantity: " + product.getProductQuantity());
                    contentStream.newLine();
                    contentStream.showText("Expiration: " + product.getExpirationDate());
                    contentStream.newLine();
                    contentStream.endText();

                    yPosition -= 100; // Adjust vertical position for the next product
                }
            } catch (SQLException e) {
                System.err.println("Error fetching products from database: " + e.getMessage());
                return; // Exit the method to avoid attempting to save the document without proper data
            }
            try {
                document.save(outputPath);
                System.out.println("PDF saved successfully.");
            } catch (IOException e) {
                System.err.println("Error saving PDF: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error creating PDF document: " + e.getMessage());
        }
    }

    private Image loadImage(String imagePath) {
        try {
            return new Image(new File(imagePath).toURI().toString());
        } catch (Exception e) {
            return null; // Return null if loading fails
        }
    }



}
