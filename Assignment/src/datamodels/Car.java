package datamodels;

import java.util.ArrayList;
import java.util.List;

public class Car extends Vehicle {
    private static List<Car> allCars = new ArrayList<>();
    private static final String[] CAR_TYPES = { "mpv", "suv", "sedan", "coupe", "convertible", "hatchback", "wagon",
            "pickup truck" };

    // Default Constructor
    public Car() {
        super();
    }

    // Parameterized Constructor
    public Car(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String vehicleType, int seatingCapacity,
            boolean availability, String features) {
        super(vehicleId, imagePath, brand, model, year, capacity, horsepower, color, mpg, vinNumber, registrationNumber,
                rentalPriceDay, transmission, fuelType, vehicleType, seatingCapacity, availability, features);
        allCars.add(this);
    }

    public Car(String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String vehicleType, int seatingCapacity,
            boolean availability, String features) {
        super(imagePath, brand, model, year, capacity, horsepower, color, mpg, vinNumber, registrationNumber,
                rentalPriceDay, transmission, fuelType, vehicleType, seatingCapacity, availability, features);
        allCars.add(this);
    }

    // Getter and Setters

    public static List<Car> getCars() {
        return allCars;
    }

    public String getVehicleCategory() {
        return "Car";
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
                ", carType = '" + vehicleType + '\'' +
                ", seatingCapacity = '" + seatingCapacity + '\'' +
                ", availability = '" + availability + '\'' +
                ", features = '" + features + '\'' +
                " }";
    }

    public static String[] getCarTypes() {
        return CAR_TYPES;
    }
}
