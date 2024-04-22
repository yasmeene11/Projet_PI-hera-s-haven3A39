package entities;

public class Rapport {
    private String vetName;
    private String petName;
    private int rapportId;
    private String description;
    private Appointment appointmentKey;

    // Constructor
    public Rapport(int rapportId, String description, Appointment appointmentKey) {
        this.rapportId = rapportId;
        this.description = description;
        this.appointmentKey = appointmentKey;
    }

    public Rapport(String description, String vetName, String petName) {
        this.description = description;
        this.vetName = vetName;
        this.petName = petName;
    }

    // Getters and Setters
    public int getRapportId() {
        return rapportId;
    }

    public void setRapportId(int rapportId) {
        this.rapportId = rapportId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Appointment getAppointmentKey() {
        return appointmentKey;
    }

    public void setAppointmentKey(Appointment appointmentKey) {
        this.appointmentKey = appointmentKey;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    
    
}
