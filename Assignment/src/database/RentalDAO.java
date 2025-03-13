package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import datamodels.Customer;
import datamodels.Rental;
import datamodels.Vehicle;
import datamodels.Rental.PaymentStatus;
import datamodels.Rental.RentalStatus;

public class RentalDAO {

    // add new rental
    public int addRental(int customerId, int vehicleId, LocalDate startDate, LocalDate endDate,
            LocalTime pickupTime, LocalTime dropoffTime, double totalCost, RentalStatus rentalStatus,
            PaymentStatus paymentStatus) {
        String sql = "INSERT INTO rentals (customer_id, vehicle_id, start_date, end_date, pickup_time, dropoff_time, total_cost, rental_status, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        conn = DatabaseConnection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, vehicleId);
            stmt.setDate(3, Date.valueOf(startDate));
            stmt.setDate(4, Date.valueOf(endDate));
            stmt.setTime(5, Time.valueOf(pickupTime));
            stmt.setTime(6, Time.valueOf(dropoffTime));
            stmt.setDouble(7, totalCost);
            stmt.setString(8, rentalStatus.toString());
            stmt.setString(9, paymentStatus.toString());

            stmt.executeUpdate();

            // get auto increment id
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // update vehicle availability if rented
                    updateVehicleAvailability(vehicleId, false);
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating rental failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO ADD RENTAL\n");
        }
    }

    // update vehicle availability
    private void updateVehicleAvailability(int vehicleId, boolean availability) {
        VehicleDAO vehicleDAO = new VehicleDAO();
        vehicleDAO.updateVehicleColumnValue(vehicleId, "availability", String.valueOf(availability));
    }

    // Mapping Result of SQL to rental object : reduce redundant code
    private Rental mapResultRental(ResultSet rs) throws SQLException {
        UserDAO userDAO = new UserDAO();
        VehicleDAO vehicleDAO = new VehicleDAO();

        // get the customer and vehicle
        int customerId = rs.getInt("customer_id");
        int vehicleId = rs.getInt("vehicle_id");

        Customer customer = (Customer) userDAO.getUserById(customerId);
        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

        // convert string status to enum
        RentalStatus rentalStatus = RentalStatus.valueOf(rs.getString("rental_status"));
        PaymentStatus paymentStatus = PaymentStatus.valueOf(rs.getString("payment_status"));

        // seperate because getting class instead of normal data type
        return new Rental(
                rs.getInt("rental_id"),
                customer,
                vehicle,
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getTime("pickup_time").toLocalTime(),
                rs.getTime("dropoff_time").toLocalTime(),
                rs.getDouble("total_cost"),
                rentalStatus,
                paymentStatus);
    }

    // get rental by ID
    public Rental getRentalById(int rentalId) {
        String sql = "SELECT * FROM rentals WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rentalId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultRental(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET RENTAL BY ID\n");
        }
        return null;
    }

    // get all rentals
    public List<Rental> getAllRentals() {
        String sql = "SELECT * FROM rentals";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rental rental = mapResultRental(rs);
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET ALL RENTALS\n");
        }
        return rentals;
    }

    // get rentals by customer ID
    public List<Rental> getRentalsByCustomerId(int customerId) {
        String sql = "SELECT * FROM rentals WHERE customer_id = ?";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rental rental = mapResultRental(rs);
                    rentals.add(rental);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET RENTALS BY CUSTOMER ID\n");
        }
        return rentals;
    }

    // get rentals by vehicle ID
    public List<Rental> getRentalsByVehicleId(int vehicleId) {
        String sql = "SELECT * FROM rentals WHERE vehicle_id = ?";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rental rental = mapResultRental(rs);
                    rentals.add(rental);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET RENTALS BY VEHICLE ID\n");
        }
        return rentals;
    }

    // get rentals by status
    public List<Rental> getRentalsByStatus(RentalStatus status) {
        String sql = "SELECT * FROM rentals WHERE rental_status = ?";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rental rental = mapResultRental(rs);
                    rentals.add(rental);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET RENTALS BY STATUS\n");
        }
        return rentals;
    }

    // get rentals by payment status
    public List<Rental> getRentalsByPaymentStatus(PaymentStatus status) {
        String sql = "SELECT * FROM rentals WHERE payment_status = ?";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rental rental = mapResultRental(rs);
                    rentals.add(rental);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET RENTALS BY PAYMENT STATUS\n");
        }
        return rentals;
    }

    // get rentals by date range
    public List<Rental> getRentalsByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM rentals WHERE start_date >= ? AND end_date <= ?";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rental rental = mapResultRental(rs);
                    rentals.add(rental);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET RENTALS BY DATE RANGE\n");
        }
        return rentals;
    }

    // get active rentals (PENDING / OVERDUE)
    public List<Rental> getActiveRentals() {
        String sql = "SELECT * FROM rentals WHERE rental_status = 'PENDING' OR rental_status = 'OVERDUE'";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rental rental = mapResultRental(rs);
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET ACTIVE RENTALS\n");
        }
        return rentals;
    }

    // update rental status
    public boolean updateRentalStatus(int rentalId, RentalStatus status) {
        String sql = "UPDATE rentals SET rental_status = ? WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());
            stmt.setInt(2, rentalId);

            int rowsUpdated = stmt.executeUpdate();

            // if rental is completed, make the vehicle available again
            if (status == RentalStatus.COMPLETED) {
                Rental rental = getRentalById(rentalId);
                if (rental != null) {
                    updateVehicleAvailability(rental.getRentVehicle().getVehicleId(), true);
                }
            }

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE RENTAL STATUS\n");
        }
    }

    // update payment status
    public boolean updatePaymentStatus(int rentalId, PaymentStatus status) {
        String sql = "UPDATE rentals SET payment_status = ? WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());
            stmt.setInt(2, rentalId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE PAYMENT STATUS\n");
        }
    }

    // update rental total cost
    public boolean updateRentalTotalCost(int rentalId, double totalCost) {
        String sql = "UPDATE rentals SET total_cost = ? WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, totalCost);
            stmt.setInt(2, rentalId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE RENTAL TOTAL COST\n");
        }
    }

    // delete rental
    public boolean deleteRental(int rentalId) {
        // get rental to know which vehicle to update
        Rental rental = getRentalById(rentalId);
        if (rental == null) {
            return false;
        }

        String sql = "DELETE FROM rentals WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rentalId);

            int rowsDeleted = stmt.executeUpdate();

            // Make the vehicle available again if rental is deleted
            if (rowsDeleted > 0) {
                updateVehicleAvailability(rental.getRentVehicle().getVehicleId(), true);
            }

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO DELETE RENTAL\n");
        }
    }

    // check for overdue rentals and update their status
    public void checkForOverdueRentals() {
        String sql = "UPDATE rentals SET rental_status = 'OVERDUE' WHERE end_date < ? AND rental_status = 'PENDING'";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set current date
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO CHECK FOR OVERDUE RENTALS\n");
        }
    }
}