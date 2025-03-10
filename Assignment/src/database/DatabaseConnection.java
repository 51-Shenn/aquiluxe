package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Amazon RDS PostgreSQL database details
    private static final String URL = "jdbc:postgresql://my-db-vehicle-rental.cp0ow2qeen52.ap-southeast-1.rds.amazonaws.com:5432/vehicle_rental";
    private static final String USER = "briankam";
    private static final String PASSWORD = "Kamaws4266!";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load PostgreSQL JDBC Driver (optional in newer versions)
            Class.forName("org.postgresql.Driver");

            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Amazon RDS PostgreSQL successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connected to Amazon RDS PostgreSQL failed!");
        }
        return connection;
    }
}
