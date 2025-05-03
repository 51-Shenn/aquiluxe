package datamodels;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String address;
    private String license;
    private List<Rental> rentalHistory;

    // Default Constructor
    public Customer() {
        super();
        this.rentalHistory = new ArrayList<>(); // User Exclusive variable
    }

    // Parameter Constructor
    public Customer(int userId, String fullName, String gender, String phoneNumber, String userEmail,
            String username, String password, String address, String license) {
        super(userId, fullName, gender, phoneNumber, userEmail, username, password);
        this.address = address;
        this.license = license;
    }

    public Customer(int userId, String fullName, String gender, String phoneNumber, String userEmail,
            String username, String password, String address, String license, List<Rental> rentalHistory) {
        this(userId, fullName, gender, phoneNumber, userEmail, username, password, address, license);
        this.rentalHistory = rentalHistory; // No need pass to User class
    }

    // Getters and Setters
    public String getUserAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    // Add Rental to History
    public void addRental(Rental rental) {
        this.rentalHistory.add(rental);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Address: %s\n" + "License: %s\n", address, license);
    }
}
