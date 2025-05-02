package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import datamodels.Payment;
import datamodels.Rental;
import datamodels.User;

public class PaymentDAO {

    // add new payment
    public static int addPayment(Payment payment) {
        String sql = "INSERT INTO payments (rental_id, amount, payment_method, payment_token, payment_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payment.getRental().getRentalId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setString(4, payment.getPaymentToken());
            stmt.setDate(5, Date.valueOf(payment.getPaymentDate()));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int paymentId = generatedKeys.getInt(1);

                    return paymentId;
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO ADD PAYMENT\n");
        }
    }

    // Mapping Result of SQL to payment object : reduce redundant code
    private static Payment mapResultPayment(ResultSet rs) throws SQLException {
        int rentalId = rs.getInt("rental_id");
        Rental rental = RentalDAO.getRentalById(rentalId);

        return new Payment(
                rs.getInt("payment_id"),
                rental,
                rs.getDouble("amount"),
                rs.getString("payment_method"),
                rs.getString("payment_token"),
                rs.getDate("payment_date").toLocalDate());
    }

    // get payment by ID
    public static Payment getPaymentById(int paymentId) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paymentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultPayment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET PAYMENT BY ID\n");
        }
        return null;
    }

    // get payment by rental
    public static Payment getPaymentByRental(Rental rental) {
        String sql = "SELECT * FROM payments WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rental.getRentalId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultPayment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET PAYMENT BY RENTAL ID\n");
        }
        return null;
    }

    // get payment by user
    public static List<Payment> getPaymentHistoryByUser(User user) {
        String sql = "SELECT * FROM payments WHERE rental_id IN (SELECT rental_id FROM rentals WHERE user_id = ?)";
        List<Payment> payments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getUserId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = mapResultPayment(rs);
                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET PAYMENT HISTORY BY USER\n");
        }
        return payments;
    }

    // get all payments
    public static List<Payment> getAllPayments() {
        String sql = "SELECT * FROM payments";
        List<Payment> payments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Payment payment = mapResultPayment(rs);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET ALL PAYMENTS\n");
        }
        return payments;
    }

    // get payments by payment method
    public static List<Payment> getPaymentsByMethod(String paymentMethod) {
        String sql = "SELECT * FROM payments WHERE payment_method = ?";
        List<Payment> payments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentMethod);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = mapResultPayment(rs);
                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET PAYMENTS BY METHOD\n");
        }
        return payments;
    }

    // get payments by date range
    public static List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM payments WHERE payment_date BETWEEN ? AND ?";
        List<Payment> payments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = mapResultPayment(rs);
                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET PAYMENTS BY DATE RANGE\n");
        }
        return payments;
    }

    // update payment amount
    public static boolean updatePaymentAmount(Payment payment, double amount) {
        String sql = "UPDATE payments SET amount = ? WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount);
            stmt.setInt(2, payment.getPaymentId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE PAYMENT AMOUNT\n");
        }
    }

    // update payment method
    public static boolean updatePaymentMethod(Payment payment, String paymentMethod) {
        String sql = "UPDATE payments SET payment_method = ? WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentMethod);
            stmt.setInt(2, payment.getPaymentId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE PAYMENT METHOD\n");
        }
    }

    // update payment date
    public static boolean updatePaymentDate(Payment payment, LocalDate paymentDate) {
        String sql = "UPDATE payments SET payment_date = ? WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(paymentDate));
            stmt.setInt(2, payment.getPaymentId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE PAYMENT DATE\n");
        }
    }

    // delete payment
    public static boolean deletePayment(Payment payment) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getPaymentId());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO DELETE PAYMENT\n");
        }
    }

    // calculate total payments for a date range
    public static double calculateTotalPayments(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT SUM(amount) AS total FROM payments WHERE payment_date BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO CALCULATE TOTAL PAYMENTS\n");
        }
        return 0.0;
    }
}