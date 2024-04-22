package services;

import entities.CashRegister;
import utils.MyBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceCashRegister implements IService<CashRegister> {
    public Connection con;
    public Statement ste;


    public ServiceCashRegister(){
        con= MyBD.getInstance().getCon();
    }

    @Override
    public void add(CashRegister cashRegister) throws SQLException {

    }

    @Override
    public void update(CashRegister cashRegister) throws SQLException {

    }

    @Override
    public void delete(CashRegister cashRegister) throws SQLException {

    }

    @Override
    public List<CashRegister> Show() throws SQLException {
        List<CashRegister> transactions = new ArrayList<>();
        String query = "SELECT * FROM cash_register";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("cashRegisterId");
                int input = resultSet.getInt("input");
                int output = resultSet.getInt("output");
                Date dateTransaction = resultSet.getDate("date_transaction");
                String type = resultSet.getString("type");
                float sum = resultSet.getFloat("somme");
                int idEntity = resultSet.getInt("id_entity");

                CashRegister transaction = new CashRegister(id, input, output, dateTransaction, type, sum, idEntity);
                transactions.add(transaction);
            }
        }

        return transactions;
    }
    public Map<String, Integer> getStatsByType() throws SQLException {
        String query = "SELECT type, COUNT(*) AS count FROM cash_register GROUP BY type";
        Map<String, Integer> stats = new HashMap<>();

        try (PreparedStatement statement = con.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                int count = resultSet.getInt("count");
                stats.put(type, count);
            }
        }
        return stats;
    }

}
