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

public class PaymentDAO {

    // add new payment
    public int addPayment(int rentalId, double amount, String paymentMethod, LocalDate paymentDate) {
        String sql = "INSERT INTO payments (rental_id, amount, payment_method, payment_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, rentalId);
            stmt.setDouble(2, amount);
            stmt.setString(3, paymentMethod);
            stmt.setDate(4, Date.valueOf(paymentDate));

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
    private Payment mapResultPayment(ResultSet rs) throws SQLException {
        RentalDAO rentalDAO = new RentalDAO();
        int rentalId = rs.getInt("rental_id");
        Rental rental = rentalDAO.getRentalById(rentalId);

        return new Payment(
                rs.getInt("payment_id"),
                rental,
                rs.getDouble("amount"),
                rs.getString("payment_method"),
                rs.getDate("payment_date").toLocalDate());
    }

    // get payment by ID
    public Payment getPaymentById(int paymentId) {
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

    // get payment by rental ID
    public Payment getPaymentByRentalId(int rentalId) {
        String sql = "SELECT * FROM payments WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rentalId);

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

    // get all payments
    public List<Payment> getAllPayments() {
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
    public List<Payment> getPaymentsByMethod(String paymentMethod) {
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
    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
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
    public boolean updatePaymentAmount(int paymentId, double amount) {
        String sql = "UPDATE payments SET amount = ? WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount);
            stmt.setInt(2, paymentId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE PAYMENT AMOUNT\n");
        }
    }

    // update payment method
    public boolean updatePaymentMethod(int paymentId, String paymentMethod) {
        String sql = "UPDATE payments SET payment_method = ? WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentMethod);
            stmt.setInt(2, paymentId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE PAYMENT METHOD\n");
        }
    }

    // delete payment
    public boolean deletePayment(int paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paymentId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO DELETE PAYMENT\n");
        }
    }

    // calculate total payments for a date range
    public double calculateTotalPayments(LocalDate startDate, LocalDate endDate) {
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