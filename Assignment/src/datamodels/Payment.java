package datamodels;

import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private Rental rental;
    private double amount;
    private String paymentMethod;
    private String paymentToken;
    private LocalDate paymentDate;

    // Default Constructor
    public Payment() {
        this(0, new Rental(), 0.0, "", "", LocalDate.now()); // Default values
    }

    // Constructor
    public Payment(int paymentId, Rental rental, double amount, String paymentMethod,
            String paymentToken, LocalDate paymentDate) {
        this.paymentId = paymentId;
        this.rental = rental;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentToken = paymentToken;
        this.paymentDate = paymentDate;
    }

    // Getters & Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}