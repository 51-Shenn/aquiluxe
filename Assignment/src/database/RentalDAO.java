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
import datamodels.User;
import datamodels.Vehicle;
import datamodels.Rental.PaymentStatus;
import datamodels.Rental.RentalStatus;

public class RentalDAO {

    // add new rental
    public static int addRental(Rental rental) {
        String sql = "INSERT INTO rentals (customer_id, vehicle_id, start_date, end_date, pickup_time, dropoff_time, total_cost, rental_status, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, getCustomerIdbyUserId(rental));
                stmt.setInt(2, rental.getRentVehicle().getVehicleId());
                stmt.setDate(3, Date.valueOf(rental.getRentStartDate()));
                stmt.setDate(4, Date.valueOf(rental.getRentEndDate()));
                stmt.setTime(5, Time.valueOf(rental.getPickUpTime()));
                stmt.setTime(6, Time.valueOf(rental.getDropoffTime()));
                stmt.setDouble(7, rental.getRentTotalCost());
                stmt.setString(8, rental.getRentalStatus().toString());
                stmt.setString(9, rental.getPaymentStatus().toString());

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int rentalId = generatedKeys.getInt(1);
                        updateVehicleAvailability(rental.getRentVehicle(), false);
                        conn.commit();
                        return rentalId;
                    } else {
                        throw new SQLException("Creating rental failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Failed to add rental", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB connection failed", e);
        }
    }

    public static int getCustomerIdbyUserId(Rental rental) {
        String sql = "SELECT customer_id FROM customers WHERE user_id = ? ORDER BY created_at DESC LIMIT 1;";
        int customerId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rental.getRentCustomer().getUserId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customerId = rs.getInt("customer_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET CUSTOMER ID\n");
        }
        return customerId;
    }

    // overloaded method to get customer ID by user ID
    private static int getCustomerIdbyUserId(User user) {
        String sql = "SELECT customer_id FROM customers WHERE user_id = ? ORDER BY created_at DESC LIMIT 1;";
        int customerId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getUserId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customerId = rs.getInt("customer_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET CUSTOMER ID\n");
        }
        return customerId;
    }

    // get user ID by rental
    public static int getUserbyRental(Rental rental) {
        String sql = "SELECT user_id FROM customers WHERE customer_id = ? ORDER BY created_at DESC LIMIT 1;";
        int userId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rental.getRentCustomer().getUserId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET USER ID\n");
        }
        return userId;
    }

    // update vehicle availability
    private static void updateVehicleAvailability(Vehicle vehicle, boolean availability) {
        VehicleDAO.updateVehicleAvailability(vehicle, availability);
    }

    // Mapping Result of SQL to rental object : reduce redundant code
    private static Rental mapResultRental(ResultSet rs) throws SQLException {
        // get the customer and vehicle
        int customerId = rs.getInt("customer_id");
        int vehicleId = rs.getInt("vehicle_id");

        User user = UserDAO.getUserById(getUserIdbyCustomerId(customerId));
        Customer customer = (Customer) UserDAO.getCustomerById(user);
        Vehicle vehicle = VehicleDAO.getVehicleById(vehicleId);

        // convert string status to enum
        RentalStatus rentalStatus = RentalStatus.valueOf(rs.getString("rental_status").toUpperCase());
        PaymentStatus paymentStatus = PaymentStatus.valueOf(rs.getString("payment_status").toUpperCase());

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

    // get user id by customer id
    private static int getUserIdbyCustomerId(int customerId) {
        String sql = "SELECT user_id FROM customers WHERE customer_id = ? LIMIT 1;";
        int userId = 0;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET USER ID\n");
        }
        return userId;
    }

    // get rental by ID
    public static Rental getRentalById(int rentalId) {
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
    public static List<Rental> getAllRentals() {
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

    // get rentals by user ID
    public static List<Rental> getRentalsByUser(User user) {
        String sql = "SELECT * FROM rentals WHERE customer_id = ?";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, getCustomerIdbyUserId(user));

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
    public static List<Rental> getRentalsByVehicle(Vehicle vehicle) {
        String sql = "SELECT * FROM rentals WHERE vehicle_id = ?";
        List<Rental> rentals = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicle.getVehicleId());

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
    public static List<Rental> getRentalsByStatus(RentalStatus status) {
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
    public static List<Rental> getRentalsByPaymentStatus(PaymentStatus status) {
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
    public static List<Rental> getRentalsByDateRange(LocalDate startDate, LocalDate endDate) {
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

    // update rental status
    public static boolean updateRentalStatus(Rental rental, RentalStatus status) {
        String sql = "UPDATE rentals SET rental_status = ? WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());
            stmt.setInt(2, rental.getRentalId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // update the rental object status
                rental.setRentalStatus(status);

                // If rental is completed, make vehicle available again
                if (status == RentalStatus.COMPLETED) {
                    updateVehicleAvailability(rental.getRentVehicle(), true);
                }

                if (status == RentalStatus.CANCELLED) {
                    updateVehicleAvailability(rental.getRentVehicle(), true);
                }
            }

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE RENTAL STATUS\n");
        }
    }

    // update payment status
    public static boolean updatePaymentStatus(Rental rental, PaymentStatus status) {
        String sql = "UPDATE rentals SET payment_status = ? WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());
            stmt.setInt(2, rental.getRentalId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE PAYMENT STATUS\n");
        }
    }

    // update rental total cost
    public static boolean updateRentalTotalCost(Rental rental, double totalCost) {
        String sql = "UPDATE rentals SET total_cost = ? WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, totalCost);
            stmt.setInt(2, rental.getRentalId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE RENTAL TOTAL COST\n");
        }
    }

    // update rental date values
    public static boolean updateRentalDates(Rental rental, String column, LocalDate date) {
        String sql = "UPDATE rentals SET " + column + " = ? WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));
            stmt.setInt(2, rental.getRentalId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE RENTAL DATES\n");
        }
    }

    // update rental time values
    public static boolean updateRentalTimes(Rental rental, String column, LocalTime time) {
        String sql = "UPDATE rentals SET " + column + " = ? WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTime(1, Time.valueOf(time));
            stmt.setInt(2, rental.getRentalId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE RENTAL TIMES\n");
        }
    }

    // delete rental
    public static boolean deleteRental(Rental rental) {
        // get rental to know which vehicle to update
        if (rental == null) {
            return false;
        }

        String sql = "DELETE FROM rentals WHERE rental_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rental.getRentalId());

            int rowsDeleted = stmt.executeUpdate();

            // Make the vehicle available again if rental is deleted
            if (rowsDeleted > 0) {
                updateVehicleAvailability(rental.getRentVehicle(), true);
            }

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO DELETE RENTAL\n");
        }
    }

    // check for overdue rentals and update their status
    public static void checkForOverdueRentals() {
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