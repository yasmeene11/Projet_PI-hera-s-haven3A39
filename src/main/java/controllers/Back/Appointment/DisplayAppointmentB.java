package controllers.Back.Appointment;

import controllers.Back.User.UpdateUserB;
import entities.Appointment;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceAppointment;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import services.ServiceRapport;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DisplayAppointmentB {
    @FXML
    private ListView<Appointment> listAppointments;
    @FXML
    private TableColumn<?, ?> ColumnAppointmentDate;

    @FXML
    private TableColumn<?, ?> ColumnAppointmentTime;

    @FXML
    private TableColumn<?, ?> Columnanimal;

    @FXML
    private TableColumn<?, ?> ColumnappointmentStatus;

    @FXML
    private TableColumn<?, ?> Columnrapport;

    @FXML
    private TableColumn<?, ?> Columnuser;
    @FXML
    private TableColumn<?, ?> ColumnappointmentId;

    @FXML
    private TableView<Appointment> TableAppointments;

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
    private Button btnaddappointment;

    @FXML
    private Button btnlistappointment;


    @FXML
    private Button btnAppStats;




    private final ServiceAppointment appointmentservice;


    public DisplayAppointmentB() {
        appointmentservice = new ServiceAppointment();
    }
    @FXML
    private void initialize() {
        try {
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            List<Appointment> appointments = appointmentservice.Show();
            appointmentList.addAll(appointments);


            listAppointments.setItems(appointmentList);

            listAppointments.setCellFactory(param -> new ListCell<Appointment>() {
                @Override
                protected void updateItem(Appointment item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText("Date: " + item.getAppointmentDate() +
                                "\nTime: " + item.getAppointmentTime() +
                                "\nStatus: " + item.getAppointmentStatus() +
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

                        Button remindButton = new Button("Send Reminder");

                        remindButton.setOnAction(event -> {
                            int accountId = item.getUser().getAccountId();
                            String appointmentDate = item.getAppointmentDate().toString();
                            String appointmentTime = item.getAppointmentTime().toString();
                            System.out.println("Doctor account ID: " + accountId);
                            try {
                                String doctorPhoneNumber = appointmentservice.getDoctorPhoneNumber(accountId);
                                if (doctorPhoneNumber != null) {
                                    System.out.println("Doctor's phone number: " + doctorPhoneNumber);
                                    appointmentservice.sendReminder(doctorPhoneNumber, appointmentDate, appointmentTime);
                                } else {
                                    System.out.println("Doctor's phone number not found.");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        HBox buttonsBox = new HBox(10, updateButton, deleteButton, remindButton);
                        setGraphic(buttonsBox);
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void NavigateToDisplayUser() throws IOException {
        loadFXML("/Back/User/DisplayUser.fxml");
    }

    @FXML
    private void NavigateToDisplayAnimal() throws IOException {
        loadFXML("/Back/Animal/DisplayAnimal.fxml");
    }

    @FXML
    private void NavigateToDisplayAdoption() throws IOException {
        loadFXML("/Back/Animal/DisplayAdoption.fxml");
    }

    @FXML
    private void NavigateToIndexBack() throws IOException {
        loadFXML("/Back/indexBack.fxml");
    }

    @FXML
    private void NavigateToDisplayAppointment() throws IOException {
        loadFXML("/Back/Appointment/DisplayAppointment.fxml");
    }

    @FXML
    public void NavigateToDisplayBoarding() throws IOException {
        loadFXML("/Back/Animal/DisplayBoarding.fxml");
    }

    @FXML
    public void NavigateToDisplayCashRegister() throws IOException {
        loadFXML("/Back/CashRegister/DisplayCashRegister.fxml");
    }

    @FXML
    public void NavigateToDisplayCategory() throws IOException {
        loadFXML("/Back/Category/DisplayCategory.fxml");
    }

    @FXML
    public void NavigateToDisplayDonation() throws IOException {
        loadFXML("/Back/DisplayDonation.fxml");
    }

    @FXML
    public void NavigateToDisplayProduct() throws IOException {
        loadFXML("/Back/Product/DisplayProduct.fxml");
    }

    @FXML
    public void NavigateToDisplayReport() throws IOException {
        loadFXML("/Back/Appointment/DisplayReport.fxml");
    }

    private void loadFXML(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = (Stage) btnUser.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }


    @FXML
    public void NavigateToAddAppointment()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/AddAppointment.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnaddappointment.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }
    @FXML
    public void NavigateToDisplayStat()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Appointment/DisplayAppStats.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnAppStats.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");

        stage.show();

    }


}


