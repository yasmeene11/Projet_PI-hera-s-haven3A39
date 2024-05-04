package controllers.Back.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javafx.scene.layout.*;

import javafx.stage.Modality;
import java.awt.*;
import java.io.IOException;
import entities.Product;
import javafx.scene.image.Image;
import java.io.InputStream;
import java.sql.SQLException;
import javax.swing.*;

import services.ServicePD;
import services.ServiceProduct;
import java.util.List;

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

    @FXML
    private Button btnlistproduct;

    @FXML
    private ListView<Product> ProductListView;
    private final ServiceProduct serviceprod;
    @FXML
    AnchorPane chartAnchorPane;

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
            ServicePD pd = new ServicePD();
            List<Product> allProducts = serviceprod.Show();
            ProductListView.getItems().addAll(allProducts);
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
                                expirationDateLabel, categoryLabel, donationCountLabel, imageView, buttonBox);
                        setGraphic(container);
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static JFreeChart createChart(DefaultPieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart(
                title,  // chart title
                dataset,             // data
                true,               // include legend
                true,
                false);

        chart.setBackgroundPaint(Color.white);

        // Customize the plot
        chart.getPlot().setBackgroundPaint(Color.white);
        chart.getPlot().setOutlinePaint(null); // Remove plot border

        // Set custom colors for pie sections
        PiePlot plot = (PiePlot) chart.getPlot();
        ServiceProduct p = new ServiceProduct();
        plot.setSectionPaint("Product", Color.BLUE);
        plot.setSectionPaint("Rating 4", Color.GREEN);
        plot.setSectionPaint("Rating 3", Color.ORANGE);
        plot.setSectionPaint("Rating 2", Color.RED);
        plot.setSectionPaint("Rating 1", Color.YELLOW);

        // Customize the legend
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));


        return chart;
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
            serviceprod.deletePDbyProduct(product);

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
    public void generateProductRatingsPieChart(MouseEvent mouseEvent) throws SQLException {
        ServiceProduct p= new ServiceProduct();
        DefaultPieDataset dataset = p.createDataset();
        JFreeChart chart = createChart(dataset, "Product Ratings");

        // Create and configure a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        // Display the chart in a frame
        JFrame frame = new JFrame("Product Ratings Pie Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
