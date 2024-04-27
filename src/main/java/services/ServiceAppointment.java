    package services;

    import entities.Animal;
    import entities.Appointment;
    import entities.Rapport;
    import entities.User;
    import utils.MyBD;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import com.twilio.Twilio;
    import com.twilio.rest.api.v2010.account.Message;
    import com.twilio.type.PhoneNumber;

    public class ServiceAppointment implements IService<Appointment> {

        private static Connection con;
        private Statement ste;
        private static final String ACCOUNT_SID = "*****HiddenOnpurpose";
        private static final String AUTH_TOKEN = "*****HiddenOnPurpose";
        private static final String TWILIO_PHONE_NUMBER = "+*****HiddenOnPurpose";
        public ServiceAppointment() {
            con = MyBD.getInstance().getCon();
        }

        @Override
        public void add(Appointment appointment) throws SQLException {
            String req = "INSERT INTO appointment (appointment_date, appointment_time, appointment_status, Account_Key, Animal_Key) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setDate(1, appointment.getAppointmentDate());
            pre.setTime(2, appointment.getAppointmentTime());
            pre.setString(3, "Pending"); // Set appointment status to "pending"
            pre.setInt(4, appointment.getUser().getAccountId()); // Assuming Account_Key is the foreign key for the User entity
            pre.setInt(5, appointment.getAnimal().getAnimalId()); // Assuming Animal_Key is the foreign key for the Animal entity
            pre.executeUpdate();
        }
        public void sendReminder(String doctorPhoneNumber, String appointmentDate, String appointmentTime) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                            new PhoneNumber("+216" + doctorPhoneNumber), // Added "+216" prefix
                            new PhoneNumber(TWILIO_PHONE_NUMBER),
                            "Reminder: You have an appointment on " + appointmentDate + " at " + appointmentTime)
                    .create();

            System.out.println("Reminder sent. Message SID: " + message.getSid());
        }

        @Override
        public void update(Appointment appointment) throws SQLException {
            String req = "UPDATE appointment SET appointment_date=?, appointment_time=?, appointment_status=?, Account_Key=?, Animal_Key=? " +
                    "WHERE appointmentId=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setDate(1, appointment.getAppointmentDate());
            pre.setTime(2, appointment.getAppointmentTime());
            pre.setString(3, appointment.getAppointmentStatus());
            pre.setInt(4, appointment.getUser().getAccountId()); // Assuming Account_Key is the foreign key for the User entity
            pre.setInt(5, appointment.getAnimal().getAnimalId()); // Assuming Animal_Key is the foreign key for the Animal entity
            pre.setInt(6, appointment.getAppointmentId());
            pre.executeUpdate();
        }

        @Override
        public List<Appointment> Show() throws SQLException {
            List<Appointment> appointments = new ArrayList<>();
            String query = "SELECT a.appointmentId, a.appointment_date, a.appointment_time, a.appointment_status, " +
                    "u.accountId AS vet_id, u.name AS vet_name, u.phone_number AS vet_phone, " +
                    "an.animal_name AS pet_name " +
                    "FROM appointment a " +
                    "JOIN account u ON a.Account_Key = u.accountId " +
                    "JOIN animal an ON a.Animal_Key = an.animalId " +
                    "ORDER BY a.appointment_date, a.appointment_time"; // Order appointments by date and time

            try (PreparedStatement preparedStatement = con.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(resultSet.getInt("appointmentId"));
                    appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                    appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                    appointment.setAppointmentStatus(resultSet.getString("appointment_status"));

                    // Set vet and pet names
                    User vet = new User();
                    vet.setAccountId(resultSet.getInt("vet_id"));
                    vet.setName(resultSet.getString("vet_name"));
                    vet.setPhoneNumber(resultSet.getString("vet_phone"));
                    appointment.setUser(vet);

                    Animal pet = new Animal();
                    pet.setAnimal_Name(resultSet.getString("pet_name"));
                    appointment.setAnimal(pet);

                    appointments.add(appointment);
                }
            }
            return appointments;
        }
        public String getDoctorPhoneNumber(int accountId) throws SQLException {
            String req = "SELECT phone_number FROM account WHERE accountId=?";
            try (PreparedStatement pre = con.prepareStatement(req)) {
                pre.setInt(1, accountId);
                try (ResultSet res = pre.executeQuery()) {
                    if (res.next()) {
                        return res.getString("phone_number");
                    }
                }
            }
            return null; // Return null if doctor's phone number is not found
        }

        public Appointment get(int appointmentId) throws SQLException {
            String req = "SELECT * FROM appointment WHERE appointmentId=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, appointmentId);
            ResultSet res = pre.executeQuery();
            if (res.next()) {
                Date appointmentDate = res.getDate("appointment_date");
                Time appointmentTime = res.getTime("appointment_time");
                String appointmentStatus = res.getString("appointment_status");
                int userId = res.getInt("Account_Key"); // Assuming Account_Key is the foreign key in appointment for User
                int animalId = res.getInt("Animal_Key"); // Assuming Animal_Key is the foreign key in appointment for Animal
                // Fetch User and Animal objects using userId and animalId respectively
                User user = fetchUserById(userId);
                Animal animal = fetchAnimalById(animalId);
                return new Appointment(appointmentId, appointmentDate, appointmentTime, appointmentStatus, null, user, animal);
            }
            return null;
        }


        public void delete(Appointment appointment) throws SQLException {
            String req = "DELETE FROM appointment WHERE appointmentId=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, appointment.getAppointmentId());
            pre.executeUpdate();
        }

        private Rapport fetchRapportById(int rapportId) throws SQLException {
            // Implement logic to fetch Rapport from the database
            Rapport rapport = null; // Initialize rapport variable with fetched Rapport object
            return rapport;
        }

        private User fetchUserById(int userId) throws SQLException {
            // Implement logic to fetch User from the database
            User user = null; // Initialize user variable with fetched User object
            return user;
        }

        private Animal fetchAnimalById(int animalId) throws SQLException {
            // Implement logic to fetch Animal from the database
            Animal animal = null; // Initzialize animal variable with fetched Animal object
            return animal;
        }

        public Map<Integer, String> getVetNames() throws SQLException {
            Map<Integer, String> vetNames = new HashMap<>();
            String req = "SELECT accountId, name FROM account WHERE role='veterinary'";
            try (Statement ste = con.createStatement();
                 ResultSet res = ste.executeQuery(req)) {
                while (res.next()) {
                    int vetId = res.getInt("accountId");
                    String vetName = res.getString("name");
                    vetNames.put(vetId, vetName);
                }
            }
            return vetNames;
        }

        public Map<Integer, String> getAnimalNames() throws SQLException {
            Map<Integer, String> animalNames = new HashMap<>();
            String req = "SELECT animalId, animal_name FROM animal";
            try (Statement ste = con.createStatement();
                 ResultSet res = ste.executeQuery(req)) {
                while (res.next()) {
                    int animalId = res.getInt("animalId");
                    String animalName = res.getString("animal_name");
                    animalNames.put(animalId, animalName);
                }
            }
            return animalNames;
        }

        public static Integer getUserIdByName(String userName) throws SQLException {
            String req = "SELECT accountId FROM account WHERE name = ?";
            try (PreparedStatement pre = con.prepareStatement(req)) {
                pre.setString(1, userName);
                try (ResultSet res = pre.executeQuery()) {
                    if (res.next()) {
                        return res.getInt("accountId");
                    }
                }
            }
            return null; // Return null if user is not found
        }


        /*public Map<String, Integer> getAppointmentStatistics() throws SQLException {
            Map<String, Integer> statistics = new HashMap<>();

            // Count appointments by status
            String statusQuery = "SELECT appointment_status, COUNT(*) AS count FROM appointment GROUP BY appointment_status";
            try (PreparedStatement statusStatement = con.prepareStatement(statusQuery);
                 ResultSet statusResultSet = statusStatement.executeQuery()) {
                while (statusResultSet.next()) {
                    String status = statusResultSet.getString("appointment_status");
                    int count = statusResultSet.getInt("count");
                    statistics.put(status, count);
                }
            }

            // Count appointments by time period (e.g., morning, afternoon, evening)
            String timeQuery = "SELECT " +
                    "CASE " +
                    "WHEN HOUR(appointment_time) >= 0 AND HOUR(appointment_time) < 12 THEN 'Morning' " +
                    "WHEN HOUR(appointment_time) >= 12 AND HOUR(appointment_time) < 18 THEN 'Afternoon' " +
                    "ELSE 'Evening' " +
                    "END AS time_period, " +
                    "COUNT(*) AS count " +
                    "FROM appointment " +
                    "GROUP BY time_period";
            try (PreparedStatement timeStatement = con.prepareStatement(timeQuery);
                 ResultSet timeResultSet = timeStatement.executeQuery()) {
                while (timeResultSet.next()) {
                    String timePeriod = timeResultSet.getString("time_period");
                    int count = timeResultSet.getInt("count");
                    statistics.put(timePeriod, count);
                }
            }

            return statistics;
        } */


        public Map<String, Integer> getAppointmentStatistics() throws SQLException {
            Map<String, Integer> statistics = new HashMap<>();

            // Count appointments by status
            String statusQuery = "SELECT appointment_status, COUNT(*) AS count FROM appointment WHERE appointment_status IN ('Pending', 'Confirmed', 'Cancelled') GROUP BY appointment_status";
            try (PreparedStatement statusStatement = con.prepareStatement(statusQuery);
                 ResultSet statusResultSet = statusStatement.executeQuery()) {
                while (statusResultSet.next()) {
                    String status = statusResultSet.getString("appointment_status");
                    int count = statusResultSet.getInt("count");
                    statistics.put(status, count);
                }
            }

            return statistics;
        }









        public static Integer getAnimalIdByName(String animalName) throws SQLException {
            String req = "SELECT animalId FROM animal WHERE animal_name = ?";
            try (PreparedStatement pre = con.prepareStatement(req)) {
                pre.setString(1, animalName);
                try (ResultSet res = pre.executeQuery()) {
                    if (res.next()) {
                        return res.getInt("animalId");
                    }
                }
            }
            return null; // Return null if animal is not found
        }

















    }