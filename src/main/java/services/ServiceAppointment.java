package services;

import entities.Animal;
import entities.Appointment;
import entities.Rapport;
import entities.User;
import utils.MyBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAppointment implements IService<Appointment> {

    private Connection con;
    private Statement ste;

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
        pre.setString(3, appointment.getAppointmentStatus());
        pre.setInt(4, appointment.getUser().getAccountId()); // Assuming Account_Key is the foreign key for the User entity
        pre.setInt(5, appointment.getAnimal().getAnimalId()); // Assuming Animal_Key is the foreign key for the Animal entity
        pre.executeUpdate();
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
        String req = "SELECT * FROM appointment";
            ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            int appointmentId = res.getInt(1);
            Date appointmentDate = res.getDate("appointment_date");
            Time appointmentTime = res.getTime("appointment_time");
            String appointmentStatus = res.getString("appointment_status");
            int userId = res.getInt("Account_Key"); // Assuming Account_Key is the foreign key in appointment for User
            int animalId = res.getInt("Animal_Key"); // Assuming Animal_Key is the foreign key in appointment for Animal
            // Fetch User and Animal objects using userId and animalId respectively
            User user = fetchUserById(userId);
            Animal animal = fetchAnimalById(animalId);
            Appointment a = new Appointment(appointmentId, appointmentDate, appointmentTime, appointmentStatus, null, user, animal);
            appointments.add(a);
        }
        return appointments;
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
        Animal animal = null; // Initialize animal variable with fetched Animal object
        return animal;
    }

}
