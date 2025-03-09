package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import datamodels.User;

public class UserDAO {

    // add user by parameter
    public int addUser(String fullName, String gender, String phoneNumber, String userEmail, String username,
            String password) {
        String sql = "INSERT INTO Users (full_name, gender, phone_number, user_email, username, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, fullName);
            stmt.setString(2, gender);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, userEmail);
            stmt.setString(5, username);
            stmt.setString(6, password);

            stmt.executeUpdate();

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

    // get all users
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUser(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("gender"),
                        rs.getString("phone_number"),
                        rs.getString("user_email"),
                        rs.getString("username"),
                        rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET USERS\n");
        }
        return users;
    }

    // get user by specific id
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUser(
                            rs.getInt("user_id"),
                            rs.getString("full_name"),
                            rs.getString("gender"),
                            rs.getString("phone_number"),
                            rs.getString("user_email"),
                            rs.getString("username"),
                            rs.getString("password"));
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
    public List<User> filterUsersByColumn(String column, String value) {
        String sql = "SELECT * FROM Users WHERE " + column + " = ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUser(
                            rs.getInt("user_id"),
                            rs.getString("full_name"),
                            rs.getString("gender"),
                            rs.getString("phone_number"),
                            rs.getString("user_email"),
                            rs.getString("username"),
                            rs.getString("password"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO FILTER USERS\n");
        }
        return users;
    }

    // search user by keywords
    public List<User> searchUsersByKeyword(String column, String pattern) {
        String sql = "SELECT * FROM Users WHERE " + column + " LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + pattern + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUser(
                            rs.getInt("user_id"),
                            rs.getString("full_name"),
                            rs.getString("gender"),
                            rs.getString("phone_number"),
                            rs.getString("user_email"),
                            rs.getString("username"),
                            rs.getString("password"));
                    users.add(user);
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
        String sql = "UPDATE Users SET " + column + " = ? WHERE user_id = ?";

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
        String sql = "DELETE FROM Users WHERE user_id = ?";

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
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUser(
                            rs.getInt("user_id"),
                            rs.getString("full_name"),
                            rs.getString("gender"),
                            rs.getString("phone_number"),
                            rs.getString("user_email"),
                            rs.getString("username"),
                            rs.getString("password"));
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
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";

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
        String sql = "SELECT COUNT(*) FROM Users WHERE user_email = ?";

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