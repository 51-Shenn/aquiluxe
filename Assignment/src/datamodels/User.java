package datamodels;

public class User {
    protected int userId;
    protected String fullName;
    protected String gender;
    protected String phoneNumber;
    protected String userEmail;
    protected String username;
    protected String password;
    protected String userType;

    // private static HashMap<Integer, User> users = new HashMap<>();

    // Default Constructor : for subclassing
    public User() {
        this(0, "Guest", "male", "", "", "guest", "");
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

    // public User(String fullName, String gender, String phoneNumber, String
    // userEmail, String username,
    // String password, String usertype) {
    // this.fullName = fullName;
    // this.gender = gender;
    // this.phoneNumber = phoneNumber;
    // this.username = username;
    // this.userEmail = userEmail;
    // this.password = password;
    // this.userType = usertype;

    // users.put(userId, this);
    // }

    public User(int userId, String fullName, String gender, String phoneNumber, String userEmail, String username,
            String password, String userType) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
        this.userType = userType;
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
}
