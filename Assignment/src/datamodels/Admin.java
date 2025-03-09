package datamodels;

public class Admin extends User {
    private String role;

    // Default Constructor
    public Admin() {
        super();
    }

    // Parameterized Constructor
    public Admin(String fullName, String gender, String phoneNumber, String userEmail, String password, String role) {
        super(fullName, gender, phoneNumber, userEmail, password);
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
