package services;

import entities.User;
import org.apache.commons.mail.*;
import utils.MyBD;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.mail.AuthenticationFailedException;

public class ServiceUser implements IService<User> {
    public Connection con;

    public ServiceUser() {
        con = MyBD.getInstance().getCon();
    }

    @Override
    public void add(User user) throws SQLException {
        String req = "INSERT INTO account (name, surname, gender, phone_number, address, email, password, role, account_status, user_image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        pre.setString(10, user.getImage()); // Set the image value

        pre.executeUpdate();
    }

    @Override
    public void update(User user) throws SQLException {
        String req = "UPDATE account SET name=?, surname=?, gender=?, phone_number=?, address=?, email=?, role=?, account_status=?, user_image=? WHERE accountId=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, user.getName());
        pre.setString(2, user.getSurname());
        pre.setString(3, user.getGender());
        pre.setString(4, user.getPhoneNumber());
        pre.setString(5, user.getAddress());
        pre.setString(6, user.getEmail());
        pre.setString(7, user.getRole());
        pre.setString(8, user.getAccountStatus());
        // Assuming user.getImageData() returns a byte array representing the image
        pre.setString(9, user.getImage());
        pre.setInt(10, user.getAccountId());
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
            Timestamp resetTokenRequestedAt = res.getTimestamp("reset_token_requested_at");
            String image=res.getString("user_image");
            User user = new User(accountId, name, surname, gender, phoneNumber, address, email, password, role, accountStatus, resetToken, resetTokenRequestedAt,image);
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
                Timestamp resetTokenRequestedAt = res.getTimestamp("reset_token_requested_at");
                String image=res.getString("user_image");;
                User user = new User(accountId, name, surname, gender, phoneNumber, address, email, password, role, accountStatus, resetToken, resetTokenRequestedAt,image);
                return user;
            }
        }

        return null; // Return null if login fails
    }


    public User getUserByEmail(String email) throws SQLException {
        String req = "SELECT * FROM account WHERE LOWER(email) = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, email.toLowerCase()); // Convert the email to lowercase for comparison
        ResultSet res = pre.executeQuery();

        if (res.next()) {
            // Retrieve user information
            int accountId = res.getInt(1);
            String name = res.getString("name");
            String surname = res.getString("surname");
            String gender = res.getString("gender");
            String phoneNumber = res.getString("phone_number");
            String address = res.getString("address");
            String role = res.getString("role");
            String accountStatus = res.getString("account_status");
            String resetToken = res.getString("reset_token");
            Timestamp resetTokenRequestedAt = res.getTimestamp("reset_token_requested_at");
            String image = res.getString("user_image");

            // Create and return the User object
            return new User(accountId, name, surname, gender, phoneNumber, address, email, null, role, accountStatus, resetToken, resetTokenRequestedAt, image);
        }

        // Return null if user with the given email is not found
        return null;
    }

   // Method to check if an email exists in the database
   public boolean emailExists(String email) throws SQLException {
       String req = "SELECT * FROM account WHERE email = ?";
       PreparedStatement pre = con.prepareStatement(req);
       pre.setString(1, email);
       ResultSet res = pre.executeQuery();
       return res.next();
   }

    public void generateResetToken(String email, String randomCode) throws SQLException {
        // Generate a random reset code


        // Update the user's account with the new reset code and timestamp
        String req = "UPDATE account SET reset_token = ?, reset_token_requested_at = ? WHERE email = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, randomCode);
        pre.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        pre.setString(3, email);
        pre.executeUpdate();
    }

    // Helper method to generate a random string
    public String generateRandomNumber(int length) {
        Random random = new Random();
        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }


    public void sendEmail(String code, String recipientEmail) throws EmailException, IOException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("adembg0@gmail.com", "lzak khcb otod ujkr"));
        email.setSSLOnConnect(true);
        email.setFrom("adembg0@gmail.com", "Your Name");
        email.setSubject("Your verification code");

        // Read HTML template file
        String htmlTemplatePath = "C:/Users/Adem/Downloads/uu/UnitedPets/src/main/resources/TemplateMail/index.html";
        String htmlTemplate = new String(Files.readAllBytes(Paths.get(htmlTemplatePath)));

        // Replace placeholders in the template with actual values
        String htmlContent = htmlTemplate.replace("${code}", code);

        email.setHtmlMsg(htmlContent);

        // Optionally, you can also set a plain text version of the email
        email.setTextMsg("Your email client does not support HTML messages");

        try {
            email.addTo(recipientEmail);
            email.send();
        } catch (EmailException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }


    public void updatePasswordByEmail(String email, String newPassword) throws SQLException {
        // Encode the new password before updating
        String encodedPassword = encodePassword(newPassword);

        // Update the user's password in the database
        String req = "UPDATE account SET password = ? WHERE LOWER(email) = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, encodedPassword);
        pre.setString(2, email.toLowerCase()); // Convert the email to lowercase for comparison
        pre.executeUpdate();
    }


    public void clearResetToken(String email) throws SQLException {
        String req = "UPDATE account SET reset_token = NULL, reset_token_requested_at = NULL WHERE LOWER(email) = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, email.toLowerCase()); // Convert the email to lowercase for comparison
        pre.executeUpdate();
    }

    public boolean searchUserByEmail(String email) throws SQLException {
        String req = "SELECT COUNT(*) FROM account WHERE LOWER(email) = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, email.toLowerCase()); // Convert the email to lowercase for comparison
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            int count = res.getInt(1);
            return count > 0; // Return true if count > 0 (user exists), otherwise false
        }
        return false; // Return false if no user found
    }


    public void updatePasswordByEmail1(String email, String newPassword) throws SQLException {
        // Encode the new password before updating
        String encodedPassword = encodePassword(newPassword);

        // Update the user's password in the database
        String req = "UPDATE account SET password = ? WHERE LOWER(email) = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, encodedPassword);
        pre.setString(2, email.toLowerCase()); // Convert the email to lowercase for comparison
        pre.executeUpdate();
    }

    // Method to verify if the provided old password matches the stored password
    public boolean verifyOldPassword(String email, String oldPassword) throws SQLException {
        // Retrieve user information by email
        User user = getUserByEmail(email);

        if (user != null) {
            // Check if the provided old password matches the stored password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(oldPassword, user.getPassword());
        }

        return false; // User not found or incorrect email
    }

}