package controllers.Back.Appointment;
import com.itextpdf.text.Image;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import entities.Rapport;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceRapport;

import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DisplayReportB {

    @FXML
    private Button btnAdoption;
    @FXML
    private Button btnGeneratePdf;
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
    private Button btnaddreport;

    @FXML
    private Button btnlistreport;
    @FXML
    private TableView<Rapport> rapportTableView;


    @FXML
    private TableColumn<Rapport, String> descriptionColumn;

    @FXML
    private TableColumn<Rapport, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Rapport, String> vetNameColumn;

    @FXML
    private TableColumn<Rapport, String> petNameColumn;


    public DisplayReportB(ServiceRapport serviceRapport) {
    }

    public DisplayReportB() {
        // Default constructor
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
    public void NavigateToAddReport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/AddReport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddreport.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }

    @FXML
    private void initialize() {
        try {
            ServiceRapport serviceRapport = new ServiceRapport();
            ObservableList<Rapport> rapportData = FXCollections.observableArrayList(serviceRapport.Show());

            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            vetNameColumn.setCellValueFactory(cellData -> {
                Rapport rapport = cellData.getValue();
                String vetName = (rapport.getVetName()!= null)? rapport.getVetName() : "";
                return new SimpleStringProperty(vetName);
            });
            petNameColumn.setCellValueFactory(cellData -> {
                Rapport rapport = cellData.getValue();
                String petName = (rapport.getPetName()!= null)? rapport.getPetName() : "";
                return new SimpleStringProperty(petName);
            });
            TableColumn<Rapport, Void> updateColumn = new TableColumn<>("Update");
            updateColumn.setCellFactory(param -> new TableCell<Rapport, Void>() {
                private final Button updateButton = new Button("Update");

                {
                    updateButton.setOnAction(event -> {
                        Rapport rapport = getTableView().getItems().get(getIndex());
                        handleUpdate(rapport);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(updateButton);
                    }
                }
            });

            TableColumn<Rapport, Void> deleteColumn = new TableColumn<>("Delete");
            deleteColumn.setCellFactory(param -> new TableCell<Rapport, Void>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        Rapport rapport = getTableView().getItems().get(getIndex());
                        handleDelete(rapport);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            });

            rapportTableView.getColumns().addAll(updateColumn, deleteColumn);
            rapportTableView.setItems(rapportData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleUpdate(Rapport rapport) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/UpdateReport.fxml"));
            Parent root = loader.load();
            UpdateReportB controller = loader.getController();
            controller.setRapport(rapport);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Rapport");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Rapport rapport) {
        try {
            // Perform the delete operation
            ServiceRapport serviceRapport = new ServiceRapport();
            serviceRapport.delete(rapport);

            // Show a success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Rapport deleted successfully.");

            // Refresh the table data after deletion
            rapportTableView.getItems().remove(rapport);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete rapport: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleGeneratePdf(javafx.event.ActionEvent actionEvent) {
        Rapport selectedRapport = rapportTableView.getSelectionModel().getSelectedItem();
        if (selectedRapport!= null) {
            try {
                generatePdf(selectedRapport, "rapport_table.pdf");
                showAlert(Alert.AlertType.INFORMATION, "Success", "PDF file generated successfully.");
            } catch (IOException | DocumentException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate PDF file: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please Select a Report first!");
        }
    }

    private void generatePdf(Rapport rapport, String filePath) throws IOException, DocumentException {
        String petName = rapport.getPetName();
        String vetName = rapport.getVetName();
        LocalDateTime now = LocalDateTime.now();
        String datePart = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        String timePart = now.format(DateTimeFormatter.ofPattern("HH_mm_ss"));
        String fileName = String.format("%s_%s_%s_%s.pdf", petName, vetName, datePart, timePart);
        String newFilePath = filePath.replace("rapport_table.pdf", fileName);

        // Create a HTML string with CSS styles
        String html = "<html><head><style>" +
                "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f9f9f9; color: #333; margin: 0; padding: 0; }" +
                ".content { border: 10px solid #38B6FF; padding: 20px; box-sizing: border-box; }" +
                "table { border-collapse: collapse; width: 100%; background-color: #fff; border-radius: 30px; }" +
                "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
                "th { background-color: #38B6FF; }" +
                ".logo { text-align: center; margin-bottom: 20px; }" +
                "h1 { color: #333; border-bottom: 2px solid #38B6FF; padding-bottom: 5px; }" +
                "</style></head><body>" +
                "<div class='content'>" +
                "<div class='logo'>" +
                "<img src='C:/Users/asus/Documents/UnitedPets/src/main/resources/images/loga.png' alt='Logo' style='width: 100px; height: 100px; border-radius: 50%;' />" +
                "</div>" +
                "<h1>Report Details</h1>" +
                "<table>" +
                "<tr><th>Description</th><th>Vet Name</th><th>Pet Name</th></tr>" +
                "<tr><td>" + rapport.getDescription() + "</td><td>" + rapport.getVetName() + "</td><td>" + rapport.getPetName() + "</td></tr>" +
                "</table>" +
                "</div>" +
                "</body></html>";




        // Create a PDF document
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(newFilePath));
        document.open();

        // Parse the HTML and add it to the PDF document
        InputStream is = new ByteArrayInputStream(html.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);

        document.close();
    }







}
