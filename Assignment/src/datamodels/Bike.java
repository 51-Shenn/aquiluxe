package datamodels;

import java.util.ArrayList;
import java.util.List;;

public class Bike extends Vehicle {
    private String bikeType;
    private static List<Bike> allBikes = new ArrayList<>();

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

    @Override
    public String toString() {
        return " Bike { " +
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
                ", bikeType = '" + bikeType + '\'' +
                ", seatingCapacity = '" + seatingCapacity + '\'' +
                ", availability = '" + availability + '\'' +
                ", features = '" + features + '\'' +
                " }";
    }
}
