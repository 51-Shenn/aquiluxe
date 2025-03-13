package datamodels;

public class Bike extends Vehicle {

    private String bikeType;
    // Default Constructor
    public Bike() {
        super();
    }

    // Parameterized Constructor
    public Bike(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String bikeType, int seatingCapacity,
            boolean availability, String features) {
        super(vehicleId, imagePath, brand, model, year, capacity, horsepower, color, mpg, vinNumber, registrationNumber,
                rentalPriceDay, transmission, fuelType, seatingCapacity, availability, features);
        this.bikeType = bikeType;
    }

    // Getter and Setters
    public String getCarType() {
        return bikeType;
    }

    public void setCarType(String bikeType) {
        this.bikeType = bikeType;
    }
}
