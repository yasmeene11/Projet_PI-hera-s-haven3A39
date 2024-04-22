package services;

import entities.Animal;
import entities.Appointment;
import entities.Rapport;
import entities.User;
import utils.MyBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRapport implements IService<Rapport> {
    private static Connection con;
    private Statement ste;

    public ServiceRapport() {
        con = MyBD.getInstance().getCon();
    }

    @Override
    public void add(Rapport rapport) throws SQLException {
        String req = "INSERT INTO rapport (description, Appointment_Key) VALUES (?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, rapport.getDescription());
        pre.setInt(2, rapport.getAppointmentKey().getAppointmentId());
        pre.executeUpdate();
    }

    @Override
    public void update(Rapport rapport) throws SQLException {
        con.setAutoCommit(false); // Set autoCommit to false
        try {
            String req = "UPDATE rapport SET description =? WHERE rapportId =?";
            System.out.println("SQL Query: " + req);
            System.out.println("Description: " + rapport.getDescription());
            System.out.println("Rapport ID: " + rapport.getRapportId());
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, rapport.getDescription());
            pre.setInt(2, rapport.getRapportId());
            pre.executeUpdate();
            con.commit(); // Commit the changes
        } catch (SQLException e) {
            con.rollback(); // Roll back the changes if an exception occurs
            throw e; // Rethrow the exception
        } finally {
            con.setAutoCommit(true); // Set autoCommit back to true
        }
    }

    @Override
    public void delete(Rapport rapport) throws SQLException {
        String req = "DELETE FROM rapport WHERE rapportId = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, rapport.getRapportId());
        pre.executeUpdate();
    }

    public List<Rapport> Show() throws SQLException {
        List<Rapport> rapports = new ArrayList<>();
        String query = "SELECT r.description, a.name AS vetName, an.animal_name AS petName " +
                "FROM rapport r " +
                "INNER JOIN appointment ap ON r.Appointment_Key = ap.appointmentId " +
                "INNER JOIN account a ON ap.Account_Key = a.accountId " +
                "INNER JOIN animal an ON ap.Animal_Key = an.animalId";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String description = resultSet.getString("description");
                String vetName = resultSet.getString("vetName");
                String petName = resultSet.getString("petName");
                System.out.println("Description: " + description + ", Vet Name: " + vetName + ", Pet Name: " + petName);
                Rapport rapport = new Rapport(description, vetName, petName);
                rapports.add(rapport);
            }
        }
        return rapports;
    }



    public List<Appointment> getAppointmentsWithoutReports() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT appointment.*, account.name AS vetName, animal.animal_name AS petName " +
                "FROM appointment " +
                "LEFT JOIN account ON appointment.Account_Key = account.accountId " +
                "LEFT JOIN animal ON appointment.Animal_Key = animal.animalId " +
                "WHERE appointment.appointmentId NOT IN (SELECT Appointment_Key FROM rapport)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointmentId"));
                appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                appointment.setAppointmentStatus(resultSet.getString("appointment_status"));

                // Set the Vet name
                String vetName = resultSet.getString("vetName");
                appointment.setUser(new User());
                appointment.getUser().setName(vetName != null ? vetName : "No Vet Assigned");

                // Set the Pet name
                String petName = resultSet.getString("petName");
                appointment.setAnimal(new Animal());
                appointment.getAnimal().setAnimal_Name(petName != null ? petName : "No Pet Assigned");

                appointments.add(appointment);
            }
        }
        return appointments;
    }




}
