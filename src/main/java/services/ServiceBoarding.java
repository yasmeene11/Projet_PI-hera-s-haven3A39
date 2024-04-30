package services;
import entities.Adoption;
import utils.MyBD;
import entities.Boarding;
import entities.Animal;
import entities.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceBoarding implements IService<Boarding>{
    public Connection con;
    public Statement ste;

    public ServiceBoarding(){
        con= MyBD.getInstance().getCon();
    }
    @Override
    public void add(Boarding boarding) throws SQLException {
        String req = "INSERT INTO boarding (Start_Date, End_Date, Boarding_Status,Boarding_Fee, Animal_Key ,Account_Key) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setDate(1, boarding.getStart_Date());
        pre.setDate(2, boarding.getEnd_Date());
        pre.setString(3, boarding.getBoarding_Status());
        pre.setFloat(4, boarding.getBoarding_Fee());
        pre.setInt(5, boarding.getAnimal_Key().getAnimalId());
        pre.setInt(6, boarding.getAccount_Key().getAccountId());


        pre.executeUpdate();
    }
    @Override
    public void update(Boarding boarding) throws SQLException {

        String req = "UPDATE boarding SET Start_Date=?, End_Date=?, Boarding_Status=?, Boarding_Fee=?, Animal_Key=?,Account_Key=? WHERE boardingId=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setDate(1, boarding.getStart_Date());
        pre.setDate(2, boarding.getEnd_Date());
        pre.setString(3, boarding.getBoarding_Status());
        pre.setFloat(4, boarding.getBoarding_Fee());
        pre.setInt(5, boarding.getAnimal_Key().getAnimalId());
        pre.setInt(6, boarding.getAccount_Key().getAccountId());

        pre.setInt(7, boarding.getBoardingId());

        pre.executeUpdate();
    }
    @Override
    public void delete(Boarding boarding) throws SQLException {
        String req = "delete from boarding where boardingId=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,boarding.getBoardingId());
        pre.executeUpdate();
    }



    public List<Boarding> Show() throws SQLException {
        List<Boarding> boardings = new ArrayList<>();
        String req = "SELECT * FROM Boarding";
        try (Statement ste = con.createStatement();
             ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                int boardingId = res.getInt("boardingId");
                Date Start_Date = res.getDate("Start_Date");
                Date End_Date = res.getDate("End_Date");
                String Boarding_Status = res.getString("Boarding_Status");
                float Boarding_Fee = res.getFloat("Boarding_Fee");
                int animalId = res.getInt("Animal_Key");
                int accountId = res.getInt("Account_Key");

                // Fetch User and Animal objects based on their IDs
                User accountKey = fetchUserById(accountId);
                Animal animalKey = fetchAnimalById(animalId);

                // Assuming you have a constructor for Adoption class that takes all these parameters
                Boarding b = new Boarding(boardingId, Start_Date, End_Date, Boarding_Status,Boarding_Fee, animalKey, accountKey);
                boardings.add(b);
            }
        }
        return boardings;
    }
    public Animal fetchAnimalById(int animalId) throws SQLException {
        Animal animal = null;
        String query = "SELECT * FROM Animal WHERE animalId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, animalId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have an Animal constructor that takes parameters accordingly
                    animal = new Animal(
                            resultSet.getInt("animalId"),
                            resultSet.getString("Animal_Name"),
                            resultSet.getString("Animal_Breed"),
                            resultSet.getString("Animal_Status"),
                            resultSet.getString("Animal_Type"),
                            resultSet.getInt("Age"),
                            resultSet.getDate("Enrollement_Date"),
                            resultSet.getString("Animal_Image"),
                            resultSet.getString("Animal_description")
                    );
                }
            }
        }
        return animal;
    }


    public User fetchUserById(int accountId) throws SQLException {
        User user = null;
        String query = "SELECT * FROM Account WHERE accountId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have a User constructor that takes parameters accordingly
                    user = new User(
                            resultSet.getInt("accountId"), // Assuming this is the column name for account ID
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("gender"),
                            resultSet.getString("phone_Number"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role"),
                            resultSet.getString("account_Status"),
                            resultSet.getString("reset_Token"),
                            resultSet.getDate("reset_Token_Requested_At")
                    );
                }
            }
        }
        return user;
    }




    public  void deleteByAnimalId(int animalId) throws SQLException {
        String req = "DELETE FROM boarding WHERE Animal_Key=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, animalId);
            pre.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception appropriately
            throw e;
        }
    }

    public Animal getAnimalByName(String name) throws SQLException {
        Animal animal = null;
        String query = "SELECT * FROM Animal WHERE animal_name = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have a Animal constructor that takes parameters accordingly
                    animal = new Animal(
                            resultSet.getInt("animalId"), // Assuming this is the column name for animal ID
                            resultSet.getString("animal_name"),
                            resultSet.getString("animal_status")
                    );
                }
            }
        }
        return animal;
    }
    public User getUserByName(String name) throws SQLException {
        User user = null;
        String query = "SELECT * FROM Account WHERE name = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have a User constructor that takes parameters accordingly
                    user = new User(
                            resultSet.getInt("accountId"), // Assuming this is the column name for account ID
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("gender"),
                            resultSet.getString("phone_Number"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role"),
                            resultSet.getString("account_Status"),
                            resultSet.getString("reset_Token"),
                            resultSet.getDate("reset_Token_Requested_At")
                    );
                }
            }
        }
        return user;
    }
    public Boarding getBoardingByAnimalNameAndDate(String animalName, LocalDate startDate) throws SQLException {
        List<Boarding> allBoardings = Show();
        for (Boarding boarding : allBoardings) {
            if (boarding.getAnimal_Key().getAnimal_Name().equals(animalName) &&
                    boarding.getStart_Date().toLocalDate().isEqual(startDate)) {
                return boarding;
            }
        }
        return null; // Return null if no matching boarding is found
    }
}
