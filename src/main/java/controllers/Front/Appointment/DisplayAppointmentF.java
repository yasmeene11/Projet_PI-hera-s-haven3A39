package controllers.Front.Appointment;

import controllers.Back.Appointment.UpdateAppointmentB;
import entities.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceAppointment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DisplayAppointmentF {

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
    private ListView<Appointment> listUsers;
    private final ServiceAppointment appointmentservice;

    public DisplayAppointmentF() {
        this.appointmentservice = new ServiceAppointment(); // or initialize it with appropriate parameters
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
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

            List<Appointment> appointments = appointmentservice.Show();
            appointmentList.addAll(appointments);

            listUsers.setItems(appointmentList);

            listUsers.setCellFactory(param -> new ListCell<Appointment>() {
                @Override
                protected void updateItem(Appointment item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText("Date: " + item.getAppointmentDate() +
                                "\nTime: " + item.getAppointmentTime() +
                                "\nAssociated Vet: " + item.getUser().getName() +
                                "\nAnimal: " + item.getAnimal().getAnimal_Name());

                        Button updateButton = new Button("Update");
                        updateButton.setOnAction(event -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/UpdateAppointment.fxml"));
                                Parent root = loader.load();
                                UpdateAppointmentB controller = loader.getController();
                                controller.setAppointment(item);
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.setTitle("Update Appointment");
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.showAndWait();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(event -> {
                            try {
                                appointmentservice.delete(item);
                                appointmentList.remove(item);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                        HBox buttonsBox = new HBox(10, updateButton, deleteButton);
                        setGraphic(buttonsBox);
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
