    package entities;

    import java.sql.Date;
    import java.sql.Time;

    public class Appointment {
        private int appointmentId;
        private Date appointmentDate;
        private Time appointmentTime;
        private String appointmentStatus;
        private Rapport rapport;
        private User user;
        private Animal animal;

        // Constructor
        public Appointment(int appointmentId, Date appointmentDate, Time appointmentTime, String appointmentStatus, Rapport rapport, User user, Animal animal) {
            this.appointmentId = appointmentId;
            this.appointmentDate = appointmentDate;
            this.appointmentTime = appointmentTime;
            this.appointmentStatus = appointmentStatus;
            this.rapport = rapport;
            this.user = user;
            this.animal = animal;
        }
        public Appointment(Date appointmentDate, Time appointmentTime, String appointmentStatus, User user, Animal animal) {
            this.appointmentDate = appointmentDate;
            this.appointmentTime = appointmentTime;
            this.appointmentStatus = appointmentStatus;
            this.user = user;
            this.animal = animal;
        }

        public Appointment(Date appointmentDate, Time appointmentTime, String appointmentStatus, String userName, Animal animal) {
            // Your constructor implementation
        }

        public Appointment() {

        }

        // Getters and Setters
        public int getAppointmentId() {
            return appointmentId;
        }

        public void setAppointmentId(int appointmentId) {
            this.appointmentId = appointmentId;
        }

        public Date getAppointmentDate() {
            return appointmentDate;
        }



        public void setAppointmentDate(Date appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public Time getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(Time appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(String appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

        public Rapport getRapport() {
            return rapport;
        }

        public void setRapport(Rapport rapport) {
            this.rapport = rapport;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Animal getAnimal() {
            return animal;
        }

        public void setAnimal(Animal animal) {
            this.animal = animal;
        }
    }
