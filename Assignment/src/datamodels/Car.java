package datamodels;

import java.util.ArrayList;
import java.util.List;

public class Car extends Vehicle {
    private String carType;
    private static List<Car> allCars = new ArrayList<>();

    // Default Constructor
    public Car() {
        super();
        this.carType = "";
    }

    // Parameterized Constructor
    public Car(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String carType, int seatingCapacity,
            boolean availability, String features) {
        super(vehicleId, imagePath, brand, model, year, capacity, horsepower, color, mpg, vinNumber, registrationNumber,
                rentalPriceDay, transmission, fuelType, carType, seatingCapacity, availability, features);
        this.carType = carType;
        allCars.add(this);
    }

    // Getter and Setters
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public static List<Car> getCars() {
        return allCars;
    }
}
