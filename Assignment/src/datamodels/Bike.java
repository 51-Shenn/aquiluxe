package datamodels;

import java.util.List;

public class Bike extends Vehicle{
    // Default Constructor
    public Bike(){
        super();
    }

    // Parameterized Constructor
    public Bike(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, int seatingCapacity, boolean availability, List<String> features) {
        super(vehicleId,imagePath,brand,model,year,capacity,horsepower,color,mpg,vinNumber,registrationNumber,rentalPriceDay,transmission,fuelType,seatingCapacity,availability,features);
    }
}
