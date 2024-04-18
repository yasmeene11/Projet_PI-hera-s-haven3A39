package services;
import entities.*;
import utils.MyBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;


public class ServiceDonationM implements IService<DonationM>{
    public Connection con;
    public Statement ste;


    public ServiceDonationM(){
        con= MyBD.getInstance().getCon();
    }
    @Override
    public void add(DonationM donationM) throws SQLException
    {
        String req = "INSERT INTO donation_m (donation_amount, donation_m_date, Account_Key) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setFloat(1, donationM.getDonationAmount());
            preparedStatement.setDate(2, new java.sql.Date(donationM.getDonationMDate().getTime()));
            preparedStatement.setInt(3, donationM.getAccountKey());

            preparedStatement.executeUpdate();
        }

    }

    @Override
    public void update(DonationM donationM) throws SQLException
    {
        String req = "update donation_m set donation_amount=?,donation_m_date=? where donationMId=? ";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setFloat(1, donationM.getDonationAmount());
            preparedStatement.setDate(2, new java.sql.Date(donationM.getDonationMDate().getTime()));
            preparedStatement.setInt(3, donationM.getDonationMId());

            preparedStatement.executeUpdate();
        }

    }

    @Override
    public void delete(DonationM donationM) throws SQLException {
        String req = "delete from donation_m where donation_amount=? ";
        try (PreparedStatement preparedStatement = con.prepareStatement(req))
        {
            preparedStatement.setFloat(1, donationM.getDonationAmount());
            preparedStatement.executeUpdate();}
    }

    @Override
    public List<DonationM> Show() throws SQLException {
        List<DonationM> dons = new ArrayList<>();
        String req = "SELECT * FROM donation_m ";


        try (Statement ste = con.createStatement();
             ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                int id = res.getInt("donationMId");
                float amount = res.getFloat("donation_amount");
                Date date = res.getDate("donation_m_date");//tostrng
                int accountKey = res.getInt("Account_Key");
                DonationM d = new DonationM(id, amount, date, accountKey);
                dons.add(d);
            }
        } // Les ressources sont automatiquement fermées après la fin du bloc try
        return dons;   }


}
