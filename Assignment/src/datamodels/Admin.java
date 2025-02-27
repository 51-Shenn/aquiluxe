package datamodels;

public class Admin extends User {
    private String role;

    // Default Constructor
    public Admin() {
        super();
    }

    // Parameterized Constructor
    public Admin(int userId, String fullName, String gender, String license, String phoneNumber, String userEmail,
            String username, String password, String role) {
        super(userId, fullName, gender, license, phoneNumber, userEmail, username, password);
        this.role = role;
    }

    // Getters & Setters...
    public String getAdminRole() {
        return role;
    }

    public void setAdminRole(String role) {
        this.role = role;
    }
}
