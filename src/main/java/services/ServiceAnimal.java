package services;

import entities.Animal;
import utils.MyBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ServiceAnimal implements IService<Animal> {

    public Connection con;
    public Statement ste;

    public ServiceAnimal(){
        con= MyBD.getInstance().getCon();
    }
    @Override
    public void add(Animal animal) throws SQLException {
        String req = "INSERT INTO animal (Animal_Name, Animal_Breed, Animal_Status, Animal_Type, Age, Enrollement_Date, Animal_Image, Animal_Description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, animal.getAnimal_Name());
        pre.setString(2, animal.getAnimal_Breed());
        pre.setString(3, animal.getAnimal_Status());
        pre.setString(4, animal.getAnimal_Type());
        pre.setInt(5, animal.getAge());
        pre.setDate(6,animal.getEnrollement_Date());
        pre.setString(7, animal.getAnimal_Image());
        pre.setString(8, animal.getAnimal_description());
        pre.executeUpdate();
    }




    @Override
    public void update(Animal animal) throws SQLException {

        String req = "UPDATE animal SET Animal_Name=?, Animal_Breed=?, Animal_Status=?, Animal_Type=?, Age=?, Enrollement_Date=?, Animal_Image=?, Animal_Description=? WHERE animalId=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, animal.getAnimal_Name());
        pre.setString(2, animal.getAnimal_Breed());
        pre.setString(3, animal.getAnimal_Status());
        pre.setString(4, animal.getAnimal_Type());
        pre.setInt(5, animal.getAge());
        pre.setDate(6, animal.getEnrollement_Date());
        pre.setString(7, animal.getAnimal_Image());
        pre.setString(8, animal.getAnimal_description());
        pre.setInt(9, animal.getAnimalId()); // Setting animalId
        pre.executeUpdate();
    }


    @Override
    public void delete(Animal animal) throws SQLException {
        String req = "delete from animal where animalId=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,animal.getAnimalId());
        pre.executeUpdate();
    }

    @Override
    public List<Animal> Show() throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String req = "select * from animal";
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        while (res.next()){
            int animalId = res.getInt(1);
            String Animal_Name =res.getString("Animal_Name");
            String Animal_Breed =res.getString("Animal_Breed");
            String Animal_Status =res.getString("Animal_Status");
            String Animal_Type =res.getString("Animal_Type");
            int Age =res.getInt("Age");
            Date Enrollement_Date =res.getDate("Enrollement_Date");
            String Animal_Image =res.getString("Animal_Image");
            String Animal_Description =res.getString("Animal_Description");
            Animal a = new Animal(animalId,Animal_Name,Animal_Breed,Animal_Status,Animal_Type,Age,Enrollement_Date,Animal_Image,Animal_Description);
            animals.add(a);
        }
        return animals;
    }


}
