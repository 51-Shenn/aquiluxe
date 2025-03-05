package datamodels;

public class User {
    private int userId;
    private String fullName;
    private String gender;
    private String license;
    private String phoneNumber;
    private String userEmail;
    private String username;
    private String password;

    // Default Constructor : for subclassing
    public User() {
    }

    // Constructor
    public User(int userId, String fullName, String gender, String license, String phoneNumber, String userEmail,
            String username, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.license = license;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
