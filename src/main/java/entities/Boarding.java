package entities;
import java.util.Date;
public class Boarding {

    int boardingId;
    Date Start_Date;
    Date End_Date;
    String Boarding_Status;
    float Boarding_Fee;
    Animal Animal_Key;
    User Account_Key;

    public Boarding()
    {


    }
    public Boarding(int boardingId,Date Start_Date,Date End_Date,String Boarding_Status,float Boarding_Fee,Animal Animal_Key,User Account_Key)
    {
        this.boardingId=boardingId;
        this.Start_Date=Start_Date;
        this.End_Date=End_Date;
        this.Boarding_Status=Boarding_Status;
        this.Boarding_Fee=Boarding_Fee;
        this.Animal_Key=Animal_Key;
        this.Account_Key=Account_Key;

    }
    public Boarding(Date Start_Date,Date End_Date,String Boarding_Status,float Boarding_Fee,User Account_Key,Animal Animal_Key)
    {
        this.Start_Date=Start_Date;
        this.End_Date=End_Date;
        this.Boarding_Status=Boarding_Status;
        this.Boarding_Fee=Boarding_Fee;
        this.Animal_Key=Animal_Key;
        this.Account_Key=Account_Key;

    }

    public int getBoardingId() {
        return boardingId;
    }

    public void setBoardingId(int boardingId) {
        this.boardingId = boardingId;
    }

    public java.sql.Date getStart_Date() {
        return (java.sql.Date) Start_Date;
    }

    public void setStart_Date(Date start_Date) {
        Start_Date = start_Date;
    }

    public java.sql.Date getEnd_Date() {
        return (java.sql.Date) End_Date;
    }

    public void setEnd_Date(Date end_Date) {
        End_Date = end_Date;
    }

    public String getBoarding_Status() {
        return Boarding_Status;
    }

    public void setBoarding_Status(String boarding_Status) {
        Boarding_Status = boarding_Status;
    }

    public float getBoarding_Fee() {
        return Boarding_Fee;
    }

    public void setBoarding_Fee(float boarding_Fee) {
        Boarding_Fee = boarding_Fee;
    }

    public Animal getAnimal_Key() {
        return Animal_Key;
    }

    public void setAnimal_Key(Animal animal_Key) {
        Animal_Key = animal_Key;
    }

    public User getAccount_Key() {
        return Account_Key;
    }

    public void setAccount_Key(User account_Key) {
        Account_Key = account_Key;
    }

    @Override
    public String toString() {
        return "Boarding{" +
                "boardingId=" + boardingId +
                ", Start_Date=" + Start_Date +
                ", End_Date=" + End_Date +
                ", Boarding_Status='" + Boarding_Status + '\'' +
                ", Boarding_Fee=" + Boarding_Fee +
                ", Animal_Key=" + Animal_Key +
                ", Account_Key=" + Account_Key +
                '}';
    }
}
