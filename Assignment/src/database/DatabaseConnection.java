package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/vehicle_rental";
    private static final String USER = "root";
    private static final String PASSWORD = "3ykjdL6j0cnUn0"; // [m

    public static Connection getConnection() {
        try {
            System.out.println("\nCONNECTION SUCCESS\n");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO CONNECT\n");
        }
    }
}
