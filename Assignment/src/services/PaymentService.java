package services;

import java.util.List;
import java.util.Random;
import datamodels.Payment;
import database.PaymentDAO;
import java.time.LocalDate;
import datamodels.User;
import datamodels.Rental;

public class PaymentService {

    private static final int TOKEN_LENGTH = 10;
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // add Payment
    public static void addPayment(Payment payment) {
        PaymentDAO.addPayment(payment);
    }

    // update payment amount
    public static void updatePaymentAmount(Payment payment, double newAmount) {
        payment.setAmount(newAmount);
        PaymentDAO.updatePaymentAmount(payment, newAmount);
    }

    // update payment method
    public static void updatePaymentMethod(Payment payment, String newMethod) {
        payment.setPaymentMethod(newMethod);
        PaymentDAO.updatePaymentMethod(payment, newMethod);
    }

    // set payment token
    public static void assignPaymentToken(Payment payment, String token) {
        payment.setPaymentToken(token);
    }

    // update payment date
    public static void updatePaymentDate(Payment payment, LocalDate newDate) {
        payment.setPaymentDate(newDate);
        PaymentDAO.updatePaymentDate(payment, newDate);
    }

    // delete payment
    public static void deletePayment(Payment payment) {
        PaymentDAO.deletePayment(payment);
    }

    // generateRandomToken 10 characters
    public static String generateRandomToken() {
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        Random random = new java.util.Random();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }
        return token.toString();
    }

    // get payment history by user
    public static List<Payment> getPaymentHistoryByUser(User user) {
        return PaymentDAO.getPaymentHistoryByUser(user);
    }

    // get payment by rental
    public static Payment getPaymentByRental(Rental rental) {
        return PaymentDAO.getPaymentByRental(rental);
    }

    // get payments by payment method
    public static List<Payment> getPaymentsByMethod(String method) {
        return PaymentDAO.getPaymentsByMethod(method);
    }

    // get payments by date range
    public static List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return PaymentDAO.getPaymentsByDateRange(startDate, endDate);
    }
}
