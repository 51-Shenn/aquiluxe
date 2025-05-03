package datamodels;

import java.util.ArrayList;
import java.util.List;;

public class Bike extends Vehicle {
    private static List<Bike> allBikes = new ArrayList<>();
    private static final String[] BIKE_TYPES = { "superbike", "touring", "cruiser" };

    // Default Constructor
    public Bike() {
        super();
    }

    // Parameterized Constructor
    public Bike(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String vehicleType, int seatingCapacity,
            boolean availability, String features) {
        super(vehicleId, imagePath, brand, model, year, capacity, horsepower, color, mpg, vinNumber, registrationNumber,
                rentalPriceDay, transmission, fuelType, vehicleType, seatingCapacity, availability, features);
        allBikes.add(this);
    }

    public Bike(String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String vehicleType, int seatingCapacity,
            boolean availability, String features) {
        super(imagePath, brand, model, year, capacity, horsepower, color, mpg, vinNumber, registrationNumber,
                rentalPriceDay, transmission, fuelType, vehicleType, seatingCapacity, availability, features);
        allBikes.add(this);
    }

    // Getter and Setters

    public static List<Bike> getBikes() {
        return allBikes;
    }

    public String getVehicleCategory() {
        return "Bike";
    }

    @Override
    public String toString() {
        return getVehicleCategory() + " { " +
                "vehicleId = " + vehicleId +
                ", imagePath = '" + imagePath + '\'' +
                ", brand = '" + brand + '\'' +
                ", model = '" + model + '\'' +
                ", year = '" + year + '\'' +
                ", capacity = '" + capacity + '\'' +
                ", horsepower = '" + horsepower + '\'' +
                ", color = '" + color + '\'' +
                ", mpg = '" + mpg + '\'' +
                ", vinNumber = '" + vinNumber + '\'' +
                ", registrationNumber = '" + registrationNumber + '\'' +
                ", rentalPriceDay = '" + rentalPriceDay + '\'' +
                ", transmission = '" + transmission + '\'' +
                ", fuelType = '" + fuelType + '\'' +
                ", bikeType = '" + vehicleType + '\'' +
                ", seatingCapacity = '" + seatingCapacity + '\'' +
                ", availability = '" + availability + '\'' +
                ", features = '" + features + '\'' +
                " }";
    }

    public static String[] getBikeTypes() {
        return BIKE_TYPES;
    }
}
