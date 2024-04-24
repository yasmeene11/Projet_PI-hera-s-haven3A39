package services;

import entities.*;

import java.sql.SQLException;
import java.util.List;
import utils.MyBD;
import org.springframework.security.core.parameters.P;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;
public class ServiceDonationP implements IService<DonationP>{
    public Connection con;
    public Statement ste;


    public ServiceDonationP(){
        con= MyBD.getInstance().getCon();
    }
    @Override
    public void add(DonationP donationP) throws SQLException {
        String req = "INSERT INTO donation_p (donation_product_name, donation_product_quantity, donation_product_label, donation_product_expiration_date, donation_p_date, donation_p_type, Account_Key) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setString(1, donationP.getDonation_product_name());
            preparedStatement.setInt(2, donationP.getDonation_product_quantity());
            preparedStatement.setString(3, donationP.getDonation_product_label());
            preparedStatement.setDate(4,new java.sql.Date(donationP.getDonation_product_expiration_date().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(donationP.getDonation_p_date().getTime()));
            preparedStatement.setString(6, donationP.getDonation_p_type());
            preparedStatement.setInt(7, donationP.getAccount_Key());

            preparedStatement.executeUpdate();
        }

    }

    @Override
    public void update(DonationP donationP) throws SQLException {
        String query = "UPDATE donation_p SET  donation_product_name = ?, donation_product_quantity = ?, donation_product_label = ?, donation_product_expiration_date = ?, donation_p_date = ?, donation_p_type = ?, Account_Key = ? WHERE donationPId = ?";
        try(PreparedStatement pstmt = con.prepareStatement(query))
        {
        pstmt.setString(1, donationP.getDonation_product_name());
        pstmt.setInt(2, donationP.getDonation_product_quantity());
        pstmt.setString(3, donationP.getDonation_product_label());
        pstmt.setDate(4, new java.sql.Date(donationP.getDonation_product_expiration_date().getTime()));
        pstmt.setDate(5, new java.sql.Date(donationP.getDonation_p_date().getTime()));
        pstmt.setString(6, donationP.getDonation_p_type());
        pstmt.setInt(7, donationP.getAccount_Key());
        pstmt.setInt(8, donationP.getDonationPId());

        pstmt.executeUpdate();
        System.out.println("DonationP updated successfully.");
        }

    }

    @Override
    public void delete(DonationP donationP) throws SQLException {
        String req = "delete from donation_p where donationPId=? ";
        try (PreparedStatement preparedStatement = con.prepareStatement(req))
        {
            preparedStatement.setInt(1, donationP.getDonationPId());
            preparedStatement.executeUpdate();}

    }

    @Override
    public List<DonationP> Show() throws SQLException {
        List<DonationP> donations = new ArrayList<>();
        String query = "SELECT * FROM donation_p";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                DonationP donation = new DonationP();
                donation.setDonationPId(rs.getInt("donationPId"));
                donation.setDonation_product_name(rs.getString("donation_product_name"));
                donation.setDonation_product_quantity(rs.getInt("donation_product_quantity"));
                donation.setDonation_product_label(rs.getString("donation_product_label"));
                donation.setDonation_product_expiration_date(rs.getDate("donation_product_expiration_date"));
                donation.setDonation_p_date(rs.getDate("donation_p_date"));
                donation.setDonation_p_type(rs.getString("donation_p_type"));
                donation.setAccount_Key(rs.getInt("Account_Key"));
                donations.add(donation);
            }
        }
        return donations;
    }
}
