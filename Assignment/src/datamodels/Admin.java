package datamodels;

public class Admin extends User {
    private String position;

    // Default Constructor
    public Admin() {
        super();
    }

    // Parameterized Constructor
    public Admin(int userId, String fullName, String gender, String phoneNumber, String userEmail, String username,
            String password, String position) {
        super(userId, fullName, gender, phoneNumber, userEmail, username, password);
        this.position = position;
    }

    // Getters & Setters...
    public String getAdminRole() {
        return position;
    }

    public void setAdminRole(String position) {
        this.position = position;
    }
}
