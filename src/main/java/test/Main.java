package test;

import entities.User;
import services.IService;
import services.ServiceUser;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create an instance of ServiceUser
        IService<User> userService = new ServiceUser();

        try {
            User loggedInUser = ((ServiceUser) userService).login("john.doe@example.com", "password");
            if (loggedInUser != null) {
                System.out.println("Login successful!");
                System.out.println("Logged in user: " + loggedInUser.getName());
                // Additional actions for a successful login
            } else {
                System.out.println("Login failed. Invalid email or password.");
                // Additional actions for a failed login
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}