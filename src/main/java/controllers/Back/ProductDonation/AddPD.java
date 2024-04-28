package controllers.Back.ProductDonation;

import entities.DonationP;
import entities.Product;
import entities.ProductDonation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import services.ServiceDonationP;
import services.ServicePD;
import services.ServiceProduct;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddPD {

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
    private Button btnaddpd;
    @FXML
    private Button btnlistpd;
    @FXML
    private ComboBox<DonationP> donationp;
    @FXML
    private ComboBox<Product> product;
    @FXML
    private Button btnPD;
    ServicePD pd= new ServicePD();

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
    public void NavigateToAddCategory()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Category/AddCategory.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddpd.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    public void AddPD(ActionEvent actionEvent) {
        try {
            DonationP selectedDonation = donationp.getValue();
            Product selectedProduct = product.getValue();
            pd.add(new ProductDonation(selectedDonation,selectedProduct));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add");
            alert.setContentText("Category Added");
            alert.show();
            LoadPage();

        }catch (SQLException | IOException e){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sql exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }
    void handleException(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void initialize() {
        try {
            donationp = new ComboBox<>();
            product = new ComboBox<>();
            populateDonationP();
            populateProduct();
        } catch (SQLException e) {
            handleException("SQL Exception", e.getMessage());
        }}
@FXML
    private void populateProduct() throws SQLException {
        ServiceProduct p= new ServiceProduct();
        List<Product> products= p.Show();
        product.setItems(FXCollections.observableArrayList(products));
        product.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product object) {
                return object.getProductName();
            }

            @Override
            public Product fromString(String string) {
                return null;
            }
        });

    }
@FXML
    private void populateDonationP() throws SQLException {
        ServiceDonationP d= new ServiceDonationP();
        List<DonationP> donations= d.Show();
        donationp.setItems(FXCollections.observableArrayList(donations));
        donationp.setConverter(new StringConverter<DonationP>() {
            @Override
            public String toString(DonationP object) {
                return object.getDonation_product_name();
            }

            @Override
            public DonationP fromString(String string) {
                return null;
            }
        });

    }
    @FXML
    void NextPage(ActionEvent event) throws IOException {
        LoadPage();
    }
    @FXML
    private void LoadPage() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Back/ProductDonation/DisplayPD.fxml"));
        Parent root=loader.load();
        product.getScene().setRoot(root);
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
}