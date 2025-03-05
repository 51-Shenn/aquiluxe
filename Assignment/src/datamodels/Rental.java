package datamodels;

import java.time.LocalTime;
import java.time.LocalDate;

public class Rental {
    private int rentalId;
    private User customer;
    private Vehicle vehicle;
    private Payment payment;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime pickupTime;
    private LocalTime dropoffTime;
    private double totalCost;
    private RentalStatus rentalStatus;
    private PaymentStatus paymentStatus;

    public Rental() {

    }

    // Constructor
    public Rental(int rentalId, User customer, Vehicle vehicle, Payment payment, LocalDate startDate, LocalDate endDate,
            LocalTime pickupTime, LocalTime dropoffTime, double totalCost, RentalStatus rentalStatus,
            PaymentStatus paymentStatus) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
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

    public Payment getRentPayment() {
        return payment;
    }

    public void setRentPayment(Payment payment) {
        this.payment = payment;
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

    public LocalTime getPickUpTime() {
        return pickupTime;
    }

    public void setPickUpTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalTime getDropoffTime() {
        return dropoffTime;
    }

    public void setDropoffTime(LocalTime dropoffTime) {
        this.dropoffTime = dropoffTime;
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

    // Debug
    public void setRental(int rentalId, User customer, Vehicle vehicle, Payment payment, LocalDate startDate,
            LocalDate endDate,
            LocalTime pickupTime, LocalTime dropoffTime, double totalCost, RentalStatus rentalStatus,
            PaymentStatus paymentStatus) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.totalCost = totalCost;
        this.rentalStatus = rentalStatus;
        this.paymentStatus = paymentStatus;
    }
}
