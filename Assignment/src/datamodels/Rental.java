package datamodels;

import java.time.LocalDate;

public class Rental {
    private int rentalId;
    private User customer;
    private Vehicle vehicle;
    // private Payment payment;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;
    private RentalStatus rentalStatus;
    private PaymentStatus paymentStatus;

    // Constructor
    public Rental(int rentalId, User customer, Vehicle vehicle, LocalDate startDate, LocalDate endDate,
            double totalCost, RentalStatus rentalStatus, PaymentStatus paymentStatus) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.rentalStatus = rentalStatus;
        this.paymentStatus = paymentStatus;
    }

    // Restrict Status Variations
    public enum RentalStatus {
        PENDING, COMPLETED, CANCELLED, OVERDUE
    }

    public enum PaymentStatus {
        PAID, FAILED
    }

    // Getters and Setters
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public User getRentCustomer() {
        return customer;
    }

    public void setRentCustomer(User customer) {
        this.customer = customer;
    }

    public Vehicle getRentVehicle() {
        return vehicle;
    }

    public void setRentVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getRentStartDate() {
        return startDate;
    }

    public void setRentStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getRentEndDate() {
        return endDate;
    }

    public void setRentEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getRentTotalCost() {
        return totalCost;
    }

    public void setRentTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
