package controllers.Back.Appointment;

import com.itextpdf.text.DocumentException;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import entities.Rapport;
import services.ServiceRapport;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
public class ReportGenerationJob implements Job {
    private static final Logger LOGGER = Logger.getLogger(ReportGenerationJob.class.getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Get the ServiceRapport instance from the job data map
        ServiceRapport serviceRapport = (ServiceRapport) context.getMergedJobDataMap().get("serviceRapport");

        try {
            // Generate the reports using the serviceRapport instance
            List<Rapport> reports = serviceRapport.Show();
            LOGGER.info("Found " + reports.size() + " reports to generate.");

            for (Rapport rapport : reports) {
                String petName = rapport.getPetName();
                String vetName = rapport.getVetName();
                LocalDateTime now = LocalDateTime.now();
                String datePart = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
                String timePart = now.format(DateTimeFormatter.ofPattern("HH_mm_ss"));
                String fileName = String.format("%s_%s_%s_%s.pdf", petName, vetName, datePart, timePart);
                String filePath = "C:\\Users\\asus\\Documents\\UnitedPets\\ReportsBackUp\\" + fileName;

                LOGGER.info("Generating report for pet: " + petName + " and vet: " + vetName);
                generatePdf(rapport, filePath);
                LOGGER.info("Report generated successfully: " + filePath);
            }
        } catch (SQLException e) {
            LOGGER.severe("Failed to generate reports: " + e.getMessage());
            e.printStackTrace();
        } catch (DocumentException | IOException e) {
            LOGGER.severe("Failed to generate reports: " + e.getMessage());
            throw new RuntimeException(e);
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
