package entities;

import java.util.Date;

public class Animal {

    int animalId;
    String Animal_Name;
    String Animal_Breed;
    String Animal_Status;
    String Animal_Type;
    int Age;
    Date Enrollement_Date;
    String Animal_Image;
    String Animal_description;

    public Animal(){

    }
    public Animal(int animalId, String Animal_Name, String Animal_Breed, String Animal_Status, String Animal_Type, int Age, Date Enrollement_Date, String Animal_Image, String Animal_description)
    {
        this.animalId=animalId;
        this.Animal_Name=Animal_Name;
        this.Animal_Breed=Animal_Breed;
        this.Animal_Status=Animal_Status;
        this.Animal_Type=Animal_Type;
        this.Age=Age;
        this.Enrollement_Date=Enrollement_Date;
        this.Animal_Image=Animal_Image;
        this.Animal_description=Animal_description;

    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getAnimal_Name() {
        return Animal_Name;
    }

    public void setAnimal_Name(String animal_Name) {
        Animal_Name = animal_Name;
    }

    public String getAnimal_Breed() {
        return Animal_Breed;
    }

    public void setAnimal_Breed(String animal_Breed) {
        Animal_Breed = animal_Breed;
    }

    public String getAnimal_Status() {
        return Animal_Status;
    }

    public void setAnimal_Status(String animal_Status) {
        Animal_Status = animal_Status;
    }

    public String getAnimal_Type() {
        return Animal_Type;
    }

    public void setAnimal_Type(String animal_Type) {
        Animal_Type = animal_Type;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public java.sql.Date getEnrollement_Date() {
        // Convert java.util.Date to java.sql.Date
        return new java.sql.Date(Enrollement_Date.getTime());
    }


    public void setEnrollement_Date(Date enrollement_Date) {
        Enrollement_Date = enrollement_Date;
    }

    public String getAnimal_Image() {
        return Animal_Image;
    }

    public void setAnimal_Image(String animal_Image) {
        Animal_Image = animal_Image;
    }

    public String getAnimal_description() {
        return Animal_description;
    }

    public void setAnimal_description(String animal_description) {
        Animal_description = animal_description;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", Animal_Name='" + Animal_Name + '\'' +
                ", Animal_Breed='" + Animal_Breed + '\'' +
                ", Animal_Status='" + Animal_Status + '\'' +
                ", Animal_Type='" + Animal_Type + '\'' +
                ", Age=" + Age +
                ", Enrollement_Date=" + Enrollement_Date +
                ", Animal_Image='" + Animal_Image + '\'' +
                ", Animal_description='" + Animal_description + '\'' +
                '}';
    }
}
