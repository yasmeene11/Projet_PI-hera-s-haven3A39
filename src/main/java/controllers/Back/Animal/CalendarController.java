package controllers.Back.Animal;



import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import entities.Adoption;
import entities.Boarding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import services.ServiceAdoption;
import services.ServiceBoarding;
import services.ServiceCategory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CalendarController {

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
    private Button btnaddanimal;

    @FXML
    private Button btnlistanimal;
    @FXML
    private CalendarView calendarView;

    private final ServiceAdoption adoptionService = new ServiceAdoption();
    private final ServiceBoarding boardingService = new ServiceBoarding();



    @FXML
    private Label infoTextArea;


    private Calendar adoptionCalendar;
    private Calendar boardingCalendar;

    @FXML
    private void initialize() throws SQLException {
        // Create a calendar source
        CalendarSource calendarSource = new CalendarSource("Pet Events");

        // Create a calendar for adoptions
        adoptionCalendar = new Calendar("Adoptions");
        adoptionCalendar.setStyle(Calendar.Style.STYLE1); // Set style for adoptions
        calendarSource.getCalendars().add(adoptionCalendar);

        // Create a calendar for pet boardings
        boardingCalendar = new Calendar("Boardings");
        boardingCalendar.setStyle(Calendar.Style.STYLE2); // Set style for boardings
        calendarSource.getCalendars().add(boardingCalendar);

        // Retrieve adoption records using AdoptionService
        List<Adoption> adoptions = adoptionService.Show();
        for (Adoption adoption : adoptions) {
            LocalDate adoptionDate = adoption.getAdoption_Date().toLocalDate();
            Entry<Calendar> adoptionEntry = new Entry<>(adoption.getAnimal_Key().getAnimal_Name());
            adoptionEntry.changeStartDate(adoptionDate);
            adoptionEntry.changeEndDate(adoptionDate.plusDays(1));
            adoptionCalendar.addEntry(adoptionEntry);
        }

        // Retrieve boarding records using BoardingService
        List<Boarding> boardings = boardingService.Show();
        for (Boarding boarding : boardings) {
            LocalDate startDate = boarding.getStart_Date().toLocalDate();
            LocalDate endDate = boarding.getEnd_Date().toLocalDate();
            while (!startDate.isAfter(endDate)) {
                Entry<Calendar> boardingEntry = new Entry<>(boarding.getAnimal_Key().getAnimal_Name());
                boardingEntry.changeStartDate(startDate);
                boardingEntry.changeEndDate(startDate.plusDays(1));
                boardingCalendar.addEntry(boardingEntry);
                startDate = startDate.plusDays(1);
            }
        }

        // Set up event handlers for calendar entries
        calendarView.setEntryDetailsPopOverContentCallback(param -> {
            Entry<?> entry = param.getEntry();
            if (entry != null) {
                // Retrieve additional information based on the entry type
                String info = "";
                if (entry.getCalendar().equals(adoptionCalendar)) {
                    // This is an adoption entry
                    Adoption adoption = null;
                    try {
                        adoption = adoptionService.getAdoptionByAnimalNameAndDate(entry.getTitle(), entry.getStartDate());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (adoption != null) {
                        info += "Adoption Details:\n";
                        info += "Animal Name: " + adoption.getAnimal_Key().getAnimal_Name() + "\n";
                        info += "Adoption Date: " + adoption.getAdoption_Date() + "\n";
                        info += "Adoption Fee: " + adoption.getAdoption_Fee() + "\n";
                        info += "Adoption Status: " + adoption.getAdoption_Status() + "\n";
                        info += "Owner: " + adoption.getAccount_Key().getName() + "\n";
                        // Add more information if needed
                    }
                } else if (entry.getCalendar().equals(boardingCalendar)) {
                    // This is a boarding entry
                    Boarding boarding = null;
                    try {
                        boarding = boardingService.getBoardingByAnimalNameAndDate(entry.getTitle(), entry.getStartDate());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (boarding != null) {
                        info += "Boarding Details:\n";
                        info += "Animal Name: " + boarding.getAnimal_Key().getAnimal_Name() + "\n";
                        info += "Start Date: " + boarding.getStart_Date() + "\n";
                        info += "End Date: " + boarding.getEnd_Date() + "\n";
                        info += "Pet Boarding Fee: " + boarding.getBoarding_Fee() + "\n";
                        info += "Pet Boarding Status: " + boarding.getBoarding_Status() + "\n";
                        info += "Owner: " + boarding.getAccount_Key().getName() + "\n";
                        // Add more information if needed
                    }
                }

                // Update the infoTextArea with the information
                infoTextArea.setText(info);
            }
            return infoTextArea;
        });

        // Add the calendar source to the calendar view
        calendarView.getCalendarSources().add(calendarSource);
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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

    @FXML
    public void NavigateToAddAnimal()throws IOException {
        loadFXML("/Back/Animal/AddAnimal.fxml");


    }
    public void NavigateToDisplayCalendar()throws IOException {
        loadFXML("/Back/Animal/Calendar.fxml");


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

}
