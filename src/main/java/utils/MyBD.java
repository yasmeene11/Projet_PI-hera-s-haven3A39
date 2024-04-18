package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MyBD {
    public final String USERNAME = "root";
    public final String PWD = "";
    public final String URL = "jdbc:mysql://localhost:3306/united_pets";

    private Connection con;
    public Connection getCon() {
        return con;
    }
    public static MyBD instance;
    private MyBD() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PWD);
            System.out.println("connected to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public static MyBD getInstance() {
        if (instance == null) {
            instance = new MyBD();
        }
        return instance;
    }
}
