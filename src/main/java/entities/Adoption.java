package entities;

import java.util.Date;

public class Adoption {




        int adoptionId;
        Date Adoption_Date;
        String Adoption_Status;
        float Adoption_Fee;
        User Account_Key;
        Animal Animal_Key;

        public Adoption()
        {

        }
        public Adoption(int adoptionId, Date Adoption_Date,String Adoption_Status,float Adoption_Fee,User Account_Key,Animal Animal_Key)
        {
                this.adoptionId=adoptionId;
                this.Adoption_Date=Adoption_Date;
                this.Adoption_Status=Adoption_Status;
                this.Adoption_Fee=Adoption_Fee;
                this.Account_Key=Account_Key;
                this.Animal_Key=Animal_Key;


        }

        public int getAdoptionId() {
                return adoptionId;
        }

        public void setAdoptionId(int adoptionId) {
                this.adoptionId = adoptionId;
        }

        public java.sql.Date getAdoption_Date() {
                return (java.sql.Date) Adoption_Date;
        }

        public void setAdoption_Date(Date adoption_Date) {
                Adoption_Date = adoption_Date;
        }

        public String getAdoption_Status() {
                return Adoption_Status;
        }

        public void setAdoption_Status(String adoption_Status) {
                Adoption_Status = adoption_Status;
        }

        public float getAdoption_Fee() {
                return Adoption_Fee;
        }

        public void setAdoption_Fee(float adoption_Fee) {
                Adoption_Fee = adoption_Fee;
        }

        public User getAccount_Key() {
                return Account_Key;
        }

        public void setAccount_Key(User account_Key) {
                Account_Key = account_Key;
        }

        public Animal getAnimal_Key() {
                return Animal_Key;
        }

        public void setAnimal_Key(Animal animal_Key) {
                Animal_Key = animal_Key;
        }

        @Override
        public String toString() {
                return "Adoption{" +
                        "adoptionId=" + adoptionId +
                        ", Adoption_Date=" + Adoption_Date +
                        ", Adoption_Status='" + Adoption_Status + '\'' +
                        ", Adoption_Fee=" + Adoption_Fee +
                        ", Account_Key=" + Account_Key +
                        ", Animal_Key=" + Animal_Key +
                        '}';
        }
}
