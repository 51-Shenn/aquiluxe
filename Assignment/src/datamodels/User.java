package datamodels;

import java.util.HashMap;
import java.util.Random;

public class User {
    private int userId;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private String userEmail;
    private String username;
    private String password;

    public static HashMap<Integer, User> users = new HashMap<>();

    // Default Constructor : for subclassing
    public User() {
    }

    // Parameterized Constructor
    public User(int userId, String fullName, String gender, String userEmail, String phoneNumber, String username,
            String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.username = generateUsername(fullName); // create new username for every instance created?
        this.userEmail = userEmail;
        this.password = password;

        users.put(userId, this);
    }

    public User(String fullName, String gender, String userEmail, String phoneNumber, String password) {
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.password = password;
    }

    private static String generateUsername(String fullName) {
        Random random = new Random();
        String baseUsername = fullName.toLowerCase().replaceAll("\\s+", "");
        String newUsername;

        do {
            int randomNum = random.nextInt(9000) + 1000; // 1000 - 9999
            newUsername = baseUsername + randomNum;
        } while (isUsernameTaken(newUsername));

        return newUsername;
    }

    private static boolean isUsernameTaken(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Getters and Setters
    public void setUser(int userId, String fullName, String gender, String phoneNumber,
            String userEmail, String username, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
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

    // Debug
    @Override
    public String toString() {
        return "User { " +
                "userId = " + userId +
                ", fullName = '" + fullName + '\'' +
                ", gender = '" + gender + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'' +
                ", userEmail = '" + userEmail + '\'' +
                ", username = '" + username + '\'' +
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
