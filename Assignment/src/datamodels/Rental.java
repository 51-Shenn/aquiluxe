package datamodels;

import java.time.LocalDate;
import java.time.LocalTime;

public class Rental {
    private int rentalId;
    private Customer customer;
    private Vehicle vehicle;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime pickupTime;
    private LocalTime dropoffTime;
    private double totalCost;
    private RentalStatus rentalStatus;
    private PaymentStatus paymentStatus;

    // Default Constructor
    public Rental() {
        this(0, new Customer(),null, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 0.0,
                RentalStatus.PENDING, PaymentStatus.PENDING); // Default values
    }

    // Parameterized Constructor
    public Rental(Customer customer, Vehicle vehicle, LocalDate startDate, LocalDate endDate,
            LocalTime pickupTime, LocalTime dropoffTime, double totalCost, RentalStatus rentalStatus,
            PaymentStatus paymentStatus) {
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

    public Rental(int rentalId, Customer customer, Vehicle vehicle, LocalDate startDate, LocalDate endDate,
            LocalTime pickupTime, LocalTime dropoffTime, double totalCost, RentalStatus rentalStatus,
            PaymentStatus paymentStatus) {
        this(customer, vehicle, startDate, endDate, pickupTime, dropoffTime, totalCost, rentalStatus, paymentStatus);
        this.rentalId = rentalId;
    }

    // Restrict Status Variations
    public enum RentalStatus {
        PENDING, APPROVED, ACTIVE, COMPLETED, CANCELLED, OVERDUE
    }

    public enum PaymentStatus {
        PENDING, PAID, FAILED
    }

    // Getters and Setters
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public Customer getRentCustomer() {
        return customer;
    }

    public void setRentCustomer(Customer customer) {
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
    public void setRental(int rentalId, Customer customer, Vehicle vehicle, Payment payment, LocalDate startDate,
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

    @Override
    public String toString() {
        return "Rental {" +
                "rentalId=" + rentalId +
                ", customer=" + customer.getFullName() +
                ", vehicle=" + vehicle.getBrand() + vehicle.getModel() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickupTime=" + pickupTime +
                ", dropoffTime=" + dropoffTime +
                ", totalCost=" + totalCost +
                ", rentalStatus=" + rentalStatus +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
