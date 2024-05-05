package controllers.Front.Animal;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Adoption;
import entities.Animal;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceUser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Optional;

public class AddAdoptionF {
    @FXML
    private MenuItem btnanimal;

    @FXML
    private MenuItem btnboarding;

    @FXML
    private Button btnconfirmadd;

    @FXML
    private ImageView qrCodeImage;

    @FXML
    private MenuItem btndonationm;

    @FXML
    private MenuItem btndonationp;

    @FXML
    private MenuItem btnindexb;

    @FXML
    private MenuItem btnproduct;

    @FXML
    private MenuBar menuBar;

    @FXML
    private DatePicker adoptiondate;



    private final ServiceAdoption adoptionService;
    private final ServiceAnimal animalService;
    private final ServiceUser userService;
    private int selectedAnimalId;




    public AddAdoptionF() throws GeneralSecurityException, IOException {
        this.adoptionService = new ServiceAdoption();
        this.animalService = new ServiceAnimal();
        this.userService = new ServiceUser();



    }

    // Method to receive the selected animal's ID
    public void setSelectedAnimalId(int animalId) {
        this.selectedAnimalId = animalId;
    }

    private BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix;
        try {
            bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            throw new WriterException(e.getMessage());
        }
        int matrixWidth = bitMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        java.awt.Graphics2D graphics = (java.awt.Graphics2D) image.getGraphics();
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }
    @FXML
    public void AddAdoptionF() throws SQLException, WriterException, GeneralSecurityException {
        LocalDate adoptionDateValue = adoptiondate.getValue();
        if (adoptionDateValue == null || adoptionDateValue.isBefore(LocalDate.now())) {
            showAlert("Error", "Invalid Adoption Date", "Please select a valid future date for adoption.");
            return;
        }

        Date adoptionDate = Date.valueOf(adoptionDateValue);
        String adoptionStatus = "Pending";
        Animal selectedAnimal = adoptionService.fetchAnimalById(selectedAnimalId);
        User user = adoptionService.fetchUserById(6); // Assuming user ID 6 exists
        float adoptionFee = 200;

        Adoption adoption = new Adoption(adoptionDate, adoptionStatus, adoptionFee, user, selectedAnimal);

        try {
            // Add the adoption
            adoptionService.add(adoption);

            // Update the status of the selected animal to "Pending"
            adoptionService.updateAnimalStatus(selectedAnimal.getAnimalId(), "Pending");

            // Generate QR code with adoption information
            String adoptionInfo = "Adoption Date: " + adoptionDateValue.toString() +
                    "    Animal Name: " + selectedAnimal.getAnimal_Name() +
                    "    Adoption Fee: TD " + adoptionFee +
                    "    Adoption Status: " + adoptionStatus;

            // Create a temporary file to save the QR code
            File qrCodeFile = File.createTempFile("adoption_qr_code", ".png");
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(adoptionInfo, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", qrCodeFile.toPath());

            // Create a PDF document
            try (PDDocument document = new PDDocument();
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream.setLeading(20f);
                    contentStream.newLineAtOffset(50, 700);

                    // Title
                    contentStream.setNonStrokingColor(Color.BLUE);
                    contentStream.showText("Adoption Information:");
                    contentStream.newLine();
                    contentStream.newLine();

                    // Adoption Date
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.showText("Adoption Date: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText(adoptionDateValue.toString());
                    contentStream.newLine();

                    // Animal Name
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.showText("Animal Name: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText(selectedAnimal.getAnimal_Name());
                    contentStream.newLine();

                    // Adoption Fee
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.showText("Adoption Fee: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText("TD " + adoptionFee);
                    contentStream.newLine();

                    // Adoption Status
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.showText("Adoption Status: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText(adoptionStatus);
                    contentStream.newLine();
                    contentStream.newLine();

                    // QR Code
                    contentStream.setNonStrokingColor(Color.RED);
                    contentStream.showText("QR Code:");
                    contentStream.newLine();
                    contentStream.endText();

                    // Embed QR code into PDF
                    //PDImageXObject qrCodeImage = PDImageXObject.createFromFileByContent(qrCodeFile, document);
                    //contentStream.drawImage(qrCodeImage, 50, 400);
                }

                // Save the PDF content to ByteArrayOutputStream
                document.save(byteArrayOutputStream);

                // Convert the ByteArrayOutputStream to a byte array
                byte[] pdfContent = byteArrayOutputStream.toByteArray();

                // Call the uploadPDFAndGetFileId method from GoogleDriveService class, passing the pdfContent byte array and get the file ID
                String pdfFileName = "Adoption_Details.pdf"; // Provide a file name for the PDF
                String pdfFileId = GoogleDriveService.uploadPDFAndGetFileId(pdfContent, pdfFileName);

                String pdfUrl = "https://drive.google.com/uc?id=" + pdfFileId;

                // Generate QR code with the URL of the PDF file in Google Drive
                BufferedImage qrCodeImage = generateQRCodeImage(pdfUrl, 200, 200);

                // Convert BufferedImage to Image
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(); // Rename byteArrayOutputStream to byteArrayOutputStream2
                ImageIO.write(qrCodeImage, "png", byteArrayOutputStream2);
                byteArrayOutputStream2.flush();
                byte[] imageInByte = byteArrayOutputStream2.toByteArray();
                byteArrayOutputStream2.close();
                javafx.scene.image.Image qrCodeFXImage = new javafx.scene.image.Image(new ByteArrayInputStream(imageInByte));

                // Create ImageView with the Image
                javafx.scene.image.ImageView qrCodeView = new javafx.scene.image.ImageView(qrCodeFXImage);

                // Create a VBox to hold the QR code image
                javafx.scene.layout.VBox qrCodeBox = new javafx.scene.layout.VBox();
                qrCodeBox.getChildren().addAll(qrCodeView);

                // Create a confirmation dialog with the QR code
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Adoption Completed");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText("Adoption process completed. QR code with PDF URL:");

                // Set the dialog's graphic to display the QR code
                confirmationDialog.getDialogPane().setContent(qrCodeBox);

                // Add "Download" and "Cancel" buttons
                ButtonType downloadButton = new ButtonType("Download");
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirmationDialog.getButtonTypes().setAll(downloadButton, cancelButton);

                // Show the dialog and wait for user input
                Optional<ButtonType> result = confirmationDialog.showAndWait();
                if (result.isPresent() && result.get() == downloadButton) {
                    // If "Download" button is clicked, prompt user to choose download location
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save QR Code Image");
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
                    File file = fileChooser.showSaveDialog(new Stage());

                    // Save the QR code image to the selected location
                    if (file != null) {
                        ImageIO.write(qrCodeImage, "PNG", file);
                    }
                }

                // Your existing code for closing the stage and loading another FXML
            } catch (IOException | WriterException | GeneralSecurityException e) {
                showAlert("Error", null, "Failed to complete adoption process");
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to display alert messages
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlertS(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
}
