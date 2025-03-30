package datamodels;

import java.util.HashMap;

public class User {
    private int userId;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private String userEmail;
    private String username;
    private String password;
    private String userType;

    private static HashMap<Integer, User> users = new HashMap<>();

    // Default Constructor : for subclassing
    public User() {
        this.fullName = "Guest";
        this.username = "guest";
        this.gender = "Male";
    }

    // Parameterized Constructor
    public User(int userId, String fullName, String gender, String phoneNumber, String userEmail, String username,
            String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
    }

    public User(String fullName, String gender, String phoneNumber, String userEmail, String username,
            String password) {
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;

        users.put(userId, this);
    }

    public User(int userId, String fullName, String gender, String phoneNumber, String userEmail, String username,
            String password, String userType) {
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
        this.userType = userType;

        users.put(userId, this);
    }

    // Getters and Setters
    public void setUser(int userId, String fullName, String gender, String phoneNumber,
            String userEmail, String username, String password, String userType) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static HashMap<Integer, User> getUsers() {
        return users;
    }

    public static void setUsers(HashMap<Integer, User> setUser) {
        users = setUser;
    }

    // Debug
    @Override
    public String toString() {
        return " User { " +
                "userId = " + userId +
                ", fullName = '" + fullName + '\'' +
                ", gender = '" + gender + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'' +
                ", userEmail = '" + userEmail + '\'' +
                ", username = '" + username + '\'' +
                ", usertype = '" + userType + '\'' +
                " }";
    }

    public static void displayUsers() {
        for (User user : users.values()) {
            System.out.println(
                    "UserID: " + user.getUserId() +
                            ", Name: " + user.getFullName() +
                            ", Username: " + user.getUsername() +
                            ", Gender: " + user.getGender() +
                            ", Email: " + user.getUserEmail() +
                            ", Phone Number: " + user.getPhoneNumber() +
                            ", Password: " + user.getPassword());
        }
    }
}
