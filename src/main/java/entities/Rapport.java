package entities;

public class Rapport {
    private int rapportId;
    private String description;
    private String vetName;
    private String petName;
    private Appointment appointmentKey;

    // Constructor
    public Rapport(int rapportId, String description, String vetName, String petName, Appointment appointmentKey) {
        this.rapportId = rapportId;
        this.description = description;
        this.vetName = vetName;
        this.petName = petName;
        this.appointmentKey = appointmentKey;
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

    public Appointment getAppointmentKey() {
        return appointmentKey;
    }

    public void setAppointmentKey(Appointment appointmentKey) {
        this.appointmentKey = appointmentKey;
    }
}