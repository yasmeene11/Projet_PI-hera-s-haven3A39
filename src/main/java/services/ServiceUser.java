package services;

import entities.User;
import utils.MyBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ServiceUser implements IService<User> {
    public Connection con;

    public ServiceUser() {
        con = MyBD.getInstance().getCon();
    }

    @Override
    public void add(User user) throws SQLException {
        String req = "INSERT INTO account (name, surname, gender, phone_number, address, email, password, role, account_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, user.getName());
        pre.setString(2, user.getSurname());
        pre.setString(3, user.getGender());
        pre.setString(4, user.getPhoneNumber());
        pre.setString(5, user.getAddress());
        pre.setString(6, user.getEmail());

        // Encode the password before inserting
        String encodedPassword = encodePassword(user.getPassword());
        pre.setString(7, encodedPassword);

        pre.setString(8, user.getRole());
        pre.setString(9, user.getAccountStatus());
        pre.executeUpdate();
    }

    @Override
    public void update(User user) throws SQLException {
        String req = "UPDATE account SET name=?, surname=?, gender=?, phone_number=?, address=?, email=?, role=?, account_status=? WHERE accountId=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, user.getName());
        pre.setString(2, user.getSurname());
        pre.setString(3, user.getGender());
        pre.setString(4, user.getPhoneNumber());
        pre.setString(5, user.getAddress());
        pre.setString(6, user.getEmail());
        pre.setString(7, user.getRole());
        pre.setString(8, user.getAccountStatus());
        pre.setInt(9, user.getAccountId());
        pre.executeUpdate();
    }

    @Override
    public void delete(User user) throws SQLException {
        String req = "DELETE FROM account WHERE accountId=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, user.getAccountId());
        pre.executeUpdate();
    }

    @Override
    public List<User> Show() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM account";
        Statement ste = con.createStatement();
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            int accountId = res.getInt(1);
            String name = res.getString("name");
            String surname = res.getString("surname");
            String gender = res.getString("gender");
            String phoneNumber = res.getString("phone_number");
            String address = res.getString("address");
            String email = res.getString("email");
            String password = res.getString("password");
            String role = res.getString("role");
            String accountStatus = res.getString("account_status");
            String resetToken = res.getString("reset_token");
            Date resetTokenRequestedAt = res.getDate("reset_token_requested_at");
            User user = new User(accountId, name, surname, gender, phoneNumber, address, email, password, role, accountStatus, resetToken, resetTokenRequestedAt);
            users.add(user);
        }
        return users;
    }

    // Method to encode password using BCryptPasswordEncoder
    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    // Login function
    public User login(String email, String password) throws SQLException {
        String req = "SELECT * FROM account WHERE email=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, email);
        ResultSet res = pre.executeQuery();

        if (res.next()) {
            String storedPassword = res.getString("password");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, storedPassword)) {
                int accountId = res.getInt(1);
                String name = res.getString("name");
                String surname = res.getString("surname");
                String gender = res.getString("gender");
                String phoneNumber = res.getString("phone_number");
                String address = res.getString("address");
                String role = res.getString("role");
                String accountStatus = res.getString("account_status");
                String resetToken = res.getString("reset_token");
                Date resetTokenRequestedAt = res.getDate("reset_token_requested_at");
                User user = new User(accountId, name, surname, gender, phoneNumber, address, email, password, role, accountStatus, resetToken, resetTokenRequestedAt);
                return user;
            }
        }

        return null; // Return null if login fails
    }
}