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
    public void add(DonationM donationM) throws SQLException {
        String donationMReq = "INSERT INTO donation_m (donation_amount, donation_m_date, Account_Key) VALUES (?, ?, ?)";
        String cashRegisterReq = "INSERT INTO cash_register (input, output, date_transaction, type, somme, id_entity) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement donationMStatement = con.prepareStatement(donationMReq, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement cashRegisterStatement = con.prepareStatement(cashRegisterReq)) {
            con.setAutoCommit(false); // Début de la transaction

            // Insertion dans la table donation_m
            donationMStatement.setFloat(1, donationM.getDonationAmount());
            donationMStatement.setDate(2, new java.sql.Date(donationM.getDonationMDate().getTime()));
            donationMStatement.setInt(3, donationM.getAccountKey());
            donationMStatement.executeUpdate();

            // Récupération de l'id de la donation_m insérée
            ResultSet generatedKeys = donationMStatement.getGeneratedKeys();
            int donationMId = -1;
            if (generatedKeys.next()) {
                donationMId = generatedKeys.getInt(1);
            }

            // Insertion dans la table cash_register
            cashRegisterStatement.setInt(1, 1); // input par 1
            cashRegisterStatement.setInt(2, 0); // output par 0
            cashRegisterStatement.setDate(3, new java.sql.Date(donationM.getDonationMDate().getTime())); // date_transaction par la date de donationM
            cashRegisterStatement.setString(4, "donationM"); // type par 'donationM'
            cashRegisterStatement.setFloat(5, donationM.getDonationAmount()); // somme par donationAmount
            cashRegisterStatement.setInt(6, donationMId); // id_entity par l'id de la donationM
            cashRegisterStatement.executeUpdate();

            con.commit(); // Validation de la transaction
        } catch (SQLException e) {
            con.rollback(); // Annulation de la transaction en cas d'erreur
            throw e;
        } finally {
            con.setAutoCommit(true); // Rétablissement du mode de fonctionnement auto-commit par défaut
        }
    }


    public List<User> getAllAccounts() throws SQLException {
        List<User> accounts = new ArrayList<>();
        String req = "SELECT * FROM account";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User account = new User();
                account.setAccountId(resultSet.getInt("accountId"));
                account.setName(resultSet.getString("name"));
                // Ajouter d'autres attributs si nécessaire
                accounts.add(account);
            }
        }
        return accounts;
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
