package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import datamodels.User;

public class UserDAO {

    // add user by parameter
    public int addUser(String fullName, String gender, String phoneNumber, String userEmail, String username,
            String password, String userType) {
        String sql = "INSERT INTO users (full_name, gender, phone_number, user_email, username, password, usertype) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Failed to connect to database.");
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, fullName);
            stmt.setString(2, gender);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, userEmail);
            stmt.setString(5, username);
            stmt.setString(6, password);
            stmt.setString(7, userType);

            stmt.executeUpdate();

            // get auto increment id
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO ADD USER\n");
        }
    }

    // add customer details
    public void addCustomerDetails(int userId, String address, String license) {
        String sql = "INSERT INTO customers (user_id, address, license) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, address);
            stmt.setString(3, license);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO ADD CUSTOMER DETAILS\n");
        }
    }

    // add admin position
    public void addAdminPosition(int userId, String position) {
        String sql = "INSERT INTO admins (user_id, position) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, position);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO ADD ADMIN POSITION\n");
        }
    }

    // Mapping Result of SQL to user object : reduce redundant code
    private User mapResultUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("user_id"),
                rs.getString("full_name"),
                rs.getString("gender"),
                rs.getString("phone_number"),
                rs.getString("user_email"),
                rs.getString("username"),
                rs.getString("password"));
    }

    // get all users
    public HashMap<Integer, User> getAllUsers() {
        String sql = "SELECT * FROM users";
        HashMap<Integer, User> users = new HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = mapResultUser(rs);
                users.put(user.getUserId(), user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET USERS\n");
        }
        return users;
    }

    // get user by specific id
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultUser(rs);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET USER BY ID\n");
        }
        return null;
    }

    // filter users with value of column
    public HashMap<Integer, User> filterUsersByColumn(String column, String value) {
        String sql = "SELECT * FROM users WHERE " + column + " = ?";
        HashMap<Integer, User> users = new HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = mapResultUser(rs);
                    users.put(user.getUserId(), user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO FILTER USERS\n");
        }
        return users;
    }

    // search user by keywords
    public HashMap<Integer, User> searchUsersByKeyword(String column, String keyword) {
        String sql = "SELECT * FROM users WHERE " + column + " LIKE ?";
        HashMap<Integer, User> users = new HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = mapResultUser(rs);
                    users.put(user.getUserId(), user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO SEARCH USERS\n");
        }
        return users;
    }

    // update user profile values
    public boolean updateUserColumnValue(int userId, String column, String value) {
        String sql = "UPDATE users SET " + column + " = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);
            stmt.setInt(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE USER\n");
        }
    }

    // delete user
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO DELETE USER\n");
        }
    }

    // get user by username and password
    public User authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultUser(rs);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nAUTHENTICATION FAILED\n");
        }
        return null;
    }

    // check if username exist : return boolean value
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO CHECK USERNAME\n");
        }
        return false;
    }

    // check if email exist : return boolean value
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO CHECK EMAIL\n");
        }
        return false;
    }
}