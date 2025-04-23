package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import datamodels.Bike;
import datamodels.Car;
import datamodels.Vehicle;

public class VehicleDAO {

    // add vehicle by parameter
    public static int addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (image_path, brand, model, year, capacity, horsepower, color, mpg, vin_number, registration_number, rental_price_day, transmission, fuel_type, vehicle_type, seating_capacity, availability, features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vehicle.getImagePath());
            stmt.setString(2, vehicle.getBrand());
            stmt.setString(3, vehicle.getModel());
            stmt.setInt(4, vehicle.getYear());
            stmt.setInt(5, vehicle.getCapacity());
            stmt.setInt(6, vehicle.getHorsepower());
            stmt.setString(7, vehicle.getColor());
            stmt.setDouble(8, vehicle.getMpg());
            stmt.setString(9, vehicle.getVinNumber());
            stmt.setString(10, vehicle.getRegistrationNumber());
            stmt.setDouble(11, vehicle.getRentalPriceDay());
            stmt.setString(12, vehicle.getTransmission());
            stmt.setString(13, vehicle.getFuelType());
            stmt.setString(14, vehicle.getVehicleType());
            stmt.setInt(15, vehicle.getSeatingCapacity());
            stmt.setBoolean(16, vehicle.isAvailability());
            stmt.setString(17, vehicle.getFeatures());

            stmt.executeUpdate();

            // get auto increment id
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
    private static final String[] CAR_TYPES = { "mpv", "suv", "sedan", "coupe", "convertible", "hatchback", "wagon", "pickup truck" };

    private static final String[] BIKE_TYPES = { "superbike", "touring", "cruiser" };

    private static Vehicle mapResultVehicle(ResultSet rs) throws SQLException {
        // Common vehicle fields
        int vehicleId = rs.getInt("vehicle_id");
        String imagePath = rs.getString("image_path");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        int year = rs.getInt("year");
        int capacity = rs.getInt("capacity");
        int horsepower = rs.getInt("horsepower");
        String color = rs.getString("color");
        double mpg = rs.getDouble("mpg");
        String vin = rs.getString("vin_number");
        String regNumber = rs.getString("registration_number");
        double rentalPrice = rs.getDouble("rental_price_day");
        String transmission = rs.getString("transmission");
        String fuelType = rs.getString("fuel_type");
        String vehicleType = rs.getString("vehicle_type").toLowerCase();
        int seatingCapacity = rs.getInt("seating_capacity");
        boolean available = rs.getBoolean("availability");
        String features = rs.getString("features");

        if (Arrays.asList(CAR_TYPES).contains(vehicleType)) {
            return new Car(vehicleId, imagePath, brand, model, year, capacity, horsepower,
                    color, mpg, vin, regNumber, rentalPrice, transmission,
                    fuelType, vehicleType, seatingCapacity, available, features);
        } else if (Arrays.asList(BIKE_TYPES).contains(vehicleType)) {
            return new Bike(vehicleId, imagePath, brand, model, year, capacity, horsepower,
                    color, mpg, vin, regNumber, rentalPrice, transmission,
                    fuelType, vehicleType, seatingCapacity, available, features);
        } else {
            return new Vehicle();
        }
    }

    // get all vehicles
    public static List<Vehicle> getAllVehicles() {
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
            throw new RuntimeException("\nFAILED TO GET VEHICLES\n");
        }
        return vehicles;
    }

    // get all vehicles
    public static List<Car> getAllCars() {
        String sql = "SELECT * FROM Vehicles WHERE vehicle_type IN ('suv', 'mpv', 'sedan', 'coupe', 'convertible', 'hatchback', 'wagon', 'pickup truck')";
        List<Car> cars = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = (Car) mapResultVehicle(rs);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET CARS\n");
        }
        return cars;
    }

    // get all bikes
    public static List<Bike> getAllBikes() {
        String sql = "SELECT * FROM Vehicles WHERE vehicle_type IN ('touring', 'cruiser', 'superbike')";
        List<Bike> bikes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bike bike = (Bike) mapResultVehicle(rs);
                bikes.add(bike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET BIKES\n");
        }
        return bikes;
    }

    // get vehicle by specific id
    public static Vehicle getVehicleById(int vehicleId) {
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
    public static List<Vehicle> filterVehiclesByColumn(String column, String value) {
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

    // return distinct values of a column
    public static List<String> getFiltersValues(String column) {
        String sql = "SELECT DISTINCT " + column + " FROM vehicles";
        List<String> filters = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    filters.add(rs.getString(column));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO FILTER VEHICLES\n");
        }
        return filters;
    }

    public static List<String> getModelsByBrand(String brand) {
        String sql = "SELECT DISTINCT model FROM vehicles WHERE brand = ?";
        List<String> models = new ArrayList<>();
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, brand);
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    models.add(rs.getString("model"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET MODELS BY BRAND\n");
        }
    
        return models;
    }

    // search vehicle by keywords
    public static List<Vehicle> searchVehiclesByKeyword(String column, String keyword) {
        String sql = "SELECT * FROM vehicles WHERE " + column + " ILIKE ?";
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
    public static boolean updateVehicleColumnValue(Vehicle vehicle, String column, String value) {
        String sql = "UPDATE vehicles SET " + column + " = ? WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);
            stmt.setInt(2, vehicle.getVehicleId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE VEHICLE\n");
        }
    }

    // update vehicle availability
    public static void updateVehicleAvailability(Vehicle vehicle, boolean availability) {
        String sql = "UPDATE vehicles SET availability = ? WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, availability);
            stmt.setInt(2, vehicle.getVehicleId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO UPDATE VEHICLE AVAILABILITY\n");
        }
    }

    // delete vehicle
    public static boolean deleteVehicle(Vehicle vehicle) {
        String sql = "DELETE FROM vehicles WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicle.getVehicleId());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO DELETE VEHICLE\n");
        }
    }

    // get available vehicles
    public static List<Vehicle> getAvailableVehicles() {
        String sql = "SELECT * FROM vehicles WHERE availability = TRUE";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicle vehicle = mapResultVehicle(rs);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\nFAILED TO GET AVAILABLE VEHICLES\n");
        }
        return vehicles;
    }
}
