package datamodels;

import java.util.List;

public class Car extends Vehicle {
    // Default Constructor
    public Car(){
        super();
    }

    // Parameterized Constructor
    public Car(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String carType, int seatingCapacity, boolean availability, List<String> features) {
        super(vehicleId,imagePath,brand,model,year,capacity,horsepower,color,mpg,vinNumber,registrationNumber,rentalPriceDay,transmission,fuelType,carType,seatingCapacity,availability,features);
    }
}
