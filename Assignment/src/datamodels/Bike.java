package datamodels;

import java.util.ArrayList;
import java.util.List;;

public class Bike extends Vehicle {
    private static List<Bike> allBikes = new ArrayList<Bike>();
    private String bikeType;

    // Default Constructor
    public Bike() {
        super();
        this.bikeType = "";
    }

    // Parameterized Constructor
    public Bike(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String bikeType, int seatingCapacity,
            boolean availability, String features) {
        super(vehicleId, imagePath, brand, model, year, capacity, horsepower, color, mpg, vinNumber, registrationNumber,
                rentalPriceDay, transmission, fuelType, bikeType, seatingCapacity, availability, features);
        this.bikeType = bikeType;
        allBikes.add(this);
    }

    // Getter and Setters
    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }

    public static List<Bike> getBikes() {
        return allBikes;
    }
}
