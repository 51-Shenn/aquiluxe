package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import datamodels.Vehicle;

public class VehicleDAO {

    // add vehicle by parameter
    public int addVehicle(String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, int seatingCapacity, boolean availability,
            String features) {
        String sql = "INSERT INTO vehicles (image_path, brand, model, year, capacity, horsepower, color, mpg, vin_number, registration_number, rental_price_day, transmission, fuel_type, seating_capacity, availability, features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, imagePath);
            stmt.setString(2, brand);
            stmt.setString(3, model);
            stmt.setInt(4, year);
            stmt.setInt(5, capacity);
            stmt.setInt(6, horsepower);
            stmt.setString(7, color);
            stmt.setDouble(8, mpg);
            stmt.setString(9, vinNumber);
            stmt.setString(10, registrationNumber);
            stmt.setDouble(11, rentalPriceDay);
            stmt.setString(12, transmission);
            stmt.setString(13, fuelType);
            stmt.setInt(14, seatingCapacity);
            stmt.setBoolean(15, availability);
            stmt.setString(16, features);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating vehicle failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO ADD VEHICLE\n");
        }
    }

    // Mapping Result of SQL to vehicle object : reduce redundant code
    private Vehicle mapResultVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(rs.getInt("vehicle_id"),
                rs.getString("image_path"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getInt("capacity"),
                rs.getInt("horsepower"),
                rs.getString("color"),
                rs.getDouble("mpg"),
                rs.getString("vin_number"),
                rs.getString("registration_number"),
                rs.getDouble("rental_price_day"),
                rs.getString("transmission"),
                rs.getString("fuel_type"),
                rs.getInt("seating_capacity"),
                rs.getBoolean("availability"),
                rs.getString("features"));
    }

    // get all vehicles
    public List<Vehicle> getAllVehicles() {
        String sql = "SELECT * FROM Vehicles";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = mapResultVehicle(rs);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET USERS\n");
        }
        return vehicles;
    }

    // get vehicle by specific id
    public Vehicle getVehicleById(int vehicleId) {
        String sql = "SELECT * FROM vehicles WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vehicle vehicle = mapResultVehicle(rs);
                    return vehicle;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET VEHICLE BY ID\n");
        }
        return null;
    }

    // filter vehicles with value of column
    public List<Vehicle> filterVehiclesByColumn(String column, String value) {
        String sql = "SELECT * FROM vehicles WHERE " + column + " = ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicle vehicle = mapResultVehicle(rs);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO FILTER VEHICLES\n");
        }
        return vehicles;
    }

    // search vehicle by keywords
    public List<Vehicle> searchVehiclesByKeyword(String column, String keyword) {
        String sql = "SELECT * FROM vehicles WHERE " + column + " LIKE ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicle vehicle = mapResultVehicle(rs);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO SEARCH VEHICLES\n");
        }
        return vehicles;
    }

    // update vehicle values
    public boolean updateVehicleColumnValue(int vehicleId, String column, String value) {
        String sql = "UPDATE vehicles SET " + column + " = ? WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);
            stmt.setInt(2, vehicleId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE VEHICLE\n");
        }
    }

    // delete vehicle
    public boolean deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM vehicles WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO DELETE VEHICLE\n");
        }
    }

    // get available vehicles
    public List<Vehicle> getAvailableVehicles() {
        String sql = "SELECT * FROM vehicles WHERE availability = TRUE";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vehicle vehicle = mapResultVehicle(rs);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nAUTHENTICATION FAILED\n");
        }
        return vehicles;
    }
}
