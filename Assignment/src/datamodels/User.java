package datamodels;

import java.util.HashMap;
import java.util.Random;

public class User {
    private static int userIdCounter = 0;
    private int userId;
    private String fullName;
    private String gender;
    private String license;
    private String phoneNumber;
    private String userEmail;
    private String username;
    private String password;

    public static HashMap<Integer, User> users = new HashMap<>();

    // Default Constructor : for subclassing
    public User() {
    }

    // Parameterized Constructor
    public User(String fullName, String gender, String license, String userEmail, String phoneNumber, String password) {
        this.userId = userIdCounter++;
        this.fullName = fullName;
        this.username = generateUsername(fullName);
        this.gender = gender;
        this.license = license;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.password = password;

        users.put(userId, this);
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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
                ", license = '" + license + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'' +
                ", userEmail = '" + userEmail + '\'' +
                ", username = '" + username + '\'' +
                " }";
    }

    public void setUser(int userId, String fullName, String gender, String license, String phoneNumber,
            String userEmail, String username, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.license = license;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
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
                    ", Password: " + user.getPassword()
            );
        }
    }
}
