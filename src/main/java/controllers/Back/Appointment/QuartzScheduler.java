package controllers.Back.Appointment;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import services.ServiceRapport;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
public class QuartzScheduler {

    public static void scheduleReportGeneration(ServiceRapport serviceRapport) {
        try {
            // Create a scheduler instance
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            //instance mta3 job
            JobDetail jobDetail = JobBuilder.newJob(ReportGenerationJob.class)
                    .withIdentity("reportGenerationJob", "group1")
                    .build();

            jobDetail.getJobDataMap().put("serviceRapport", serviceRapport);

            // Create a trigger instance
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("reportGenerationTrigger", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMinutes(1) // Change this line
                            .repeatForever())
                    .build();

            // Schedule the job with the trigger
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
