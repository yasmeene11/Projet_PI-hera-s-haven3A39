    package entities;

    public class Rapport {
        private int rapportId;
        private String description;
        private Appointment appointmentKey;

        // Constructor
        public Rapport(int rapportId, String description, Appointment appointmentKey) {
            this.rapportId = rapportId;
            this.description = description;
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

        public Appointment getAppointmentKey() {
            return appointmentKey;
        }

        public void setAppointmentKey(Appointment appointmentKey) {
            this.appointmentKey = appointmentKey;
        }



    }
