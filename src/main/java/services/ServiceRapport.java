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

    }

    @Override
    public void delete(Rapport rapport) throws SQLException {

    }

    @Override
    public List<Rapport> Show() throws SQLException {
        return null;
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
