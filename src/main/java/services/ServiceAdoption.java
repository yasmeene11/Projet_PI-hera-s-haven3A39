package services;

import utils.MyBD;
import entities.Adoption;
import entities.Animal;
import entities.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ServiceAdoption implements IService<Adoption> {
    public Connection con;
    public Statement ste;

    public ServiceAdoption(){
        con= MyBD.getInstance().getCon();
    }
    @Override
    public void add(Adoption adoption) throws SQLException {
        String req = "INSERT INTO adoption (Adoption_Date, Adoption_Status, Adoption_Fee, Account_Key, Animal_Key) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setDate(1, adoption.getAdoption_Date());
        pre.setString(2, adoption.getAdoption_Status());
        pre.setFloat(3, adoption.getAdoption_Fee());
        pre.setInt(4, adoption.getAccount_Key().getAccountId());
        pre.setInt(5, adoption.getAnimal_Key().getAnimalId());

        pre.executeUpdate();
    }
    @Override
    public void update(Adoption adoption) throws SQLException {

        String req = "UPDATE adoption SET Adoption_Date=?, Adoption_Status=?, Adoption_Fee=?, Account_Key=?, Animal_Key=? WHERE adoptionId=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setDate(1, adoption.getAdoption_Date());
        pre.setString(2, adoption.getAdoption_Status());
        pre.setFloat(3, adoption.getAdoption_Fee());
        pre.setInt(4, adoption.getAccount_Key().getAccountId());
        pre.setInt(5, adoption.getAnimal_Key().getAnimalId());
        pre.setInt(6, adoption.getAdoptionId());
        pre.executeUpdate();
    }
    @Override
    public void delete(Adoption Adoption) throws SQLException {
        String req = "delete from Adoption where adoptionId=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,Adoption.getAdoptionId());
        pre.executeUpdate();
    }



    public List<Adoption> Show() throws SQLException {
        List<Adoption> adoptions = new ArrayList<>();
        String req = "SELECT * FROM Adoption";
        try (Statement ste = con.createStatement();
             ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                int adoptionId = res.getInt("adoptionId");
                Date adoptionDate = res.getDate("Adoption_Date");
                String adoptionStatus = res.getString("Adoption_Status");
                float adoptionFee = res.getFloat("Adoption_Fee");
                int accountId = res.getInt("Account_Key");
                int animalId = res.getInt("Animal_Key");

                // Fetch User and Animal objects based on their IDs
                User accountKey = fetchUserById(accountId);
                Animal animalKey = fetchAnimalById(animalId);

                // Assuming you have a constructor for Adoption class that takes all these parameters
                Adoption ad = new Adoption(adoptionId, adoptionDate, adoptionStatus, adoptionFee, accountKey, animalKey);
                adoptions.add(ad);
            }
        }
        return adoptions;
    }

    public Animal fetchAnimalById(int animalId) throws SQLException {
        Animal animal = null;
        String query = "SELECT Animal_Name, Animal_Image FROM Animal WHERE animalId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, animalId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have an Animal constructor that takes parameters accordingly
                    animal = new Animal(
                            animalId,
                            resultSet.getString("Animal_Name"),

                            resultSet.getString("Animal_Image")

                    );
                }
            }
        }
        return animal;
    }

    public User fetchUserById(int accountId) throws SQLException {
        User user = null;
        String query = "SELECT name, surname FROM Account WHERE accountId = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have a User constructor that takes parameters accordingly
                    user = new User(
                            accountId,
                            resultSet.getString("name"),
                            resultSet.getString("surname")

                    );
                }
            }
        }
        return user;
    }



    public  void deleteByAnimalId(int animalId) throws SQLException {
        String req = "DELETE FROM Adoption WHERE Animal_Key=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, animalId);
            pre.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception appropriately
            throw e;
        }
    }


    public void updateAnimalStatus(int animalId, String Animal_Status) throws SQLException {

        String req = "UPDATE animal SET Animal_Status = ? WHERE animalId = ?";

        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, Animal_Status);
            pre.setInt(2, animalId);
            pre.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception appropriately
            throw e;
        }



    }


}
