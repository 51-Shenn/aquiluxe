package datamodels;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String address;
    private List<Rental> rentalHistory;

    // Default Constructor
    public Customer() {
        super();
        this.rentalHistory = new ArrayList<>(); // User Exclusive variable
    }

    // Parameter Constructor
    public Customer(int userId, String fullName, String gender, String license, String phoneNumber, String userEmail,
            String address, String username, String password, List<Rental> rentalHistory) {
        super(userId, fullName, gender, license, phoneNumber, userEmail, username, password);
        this.address = address;
        this.rentalHistory = rentalHistory; // No need pass to User class
    }

    // Getters and Setters
    public String getUserAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    // Add Rental to History
    public void addRental(Rental rental) {
        this.rentalHistory.add(rental);
    }
}
