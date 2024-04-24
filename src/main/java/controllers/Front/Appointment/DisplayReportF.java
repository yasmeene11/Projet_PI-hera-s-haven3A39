package controllers.Front.Appointment;

import controllers.Back.Appointment.UpdateReportB;
import entities.Rapport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceRapport;

import java.io.IOException;
import java.sql.SQLException;

public class DisplayReportF {
    @FXML
    private MenuItem btnanimal;

    @FXML
    private MenuItem btnappointment;

    @FXML
    private MenuItem btnboarding;

    @FXML
    private MenuItem btndonationm;

    @FXML
    private MenuItem btndonationp;

    @FXML
    private MenuItem btnindexb;

    @FXML
    private MenuItem btnproduct;

    @FXML
    private MenuItem btnreport;

    @FXML
    private MenuBar menuBar;




    @FXML
    private TableView<Rapport> rapportTableView;


    @FXML
    private TableColumn<Rapport, String> descriptionColumn;


    @FXML
    private TableColumn<Rapport, String> vetNameColumn;

    @FXML
    private TableColumn<Rapport, String> petNameColumn;


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
    public void NavigateToDisplayAppointment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Appointment/DisplayAppointment.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnappointment.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayReport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Appointment/DisplayReport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnreport.getParentPopup().getOwnerWindow();
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
        vetNameColumn.setCellValueFactory(new PropertyValueFactory<>("vetName"));
        petNameColumn.setCellValueFactory(new PropertyValueFactory<>("petName"));

        TableColumn<Rapport, Void> updateColumn = new TableColumn<>("Update");
        updateColumn.setCellFactory(param -> new TableCell<Rapport, Void>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Rapport rapport = getTableView().getItems().get(getIndex());
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
                    try {
                        // Perform the delete operation
                        serviceRapport.delete(rapport);

                        // Show a success message
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Rapport deleted successfully.");

                        // Refresh the table data after deletion
                        rapportData.remove(rapport);
                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete rapport: " + e.getMessage());
                    }
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


