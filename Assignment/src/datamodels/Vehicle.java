package datamodels;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    protected int vehicleId;
    protected String imagePath;
    protected String brand;
    protected String model;
    protected int year;
    protected int capacity;
    protected int horsepower;
    protected String color;
    protected double mpg;
    protected String vinNumber;
    protected String registrationNumber;
    protected double rentalPriceDay;
    protected String transmission;
    protected String fuelType;
    protected String vehicleType;
    protected int seatingCapacity;
    protected boolean availability;
    protected String features;

    protected static List<Vehicle> vehicles = new ArrayList<Vehicle>();

    // Default Constructor
    public Vehicle() {
        this(0, null, null, null, 0, 0, 0, null, 0.0, null, null, 0.0, null, null, null, 0, false, null);
    }

    // Parameterized Constructor
    public Vehicle( String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String vehicleType, int seatingCapacity,
            boolean availability, String features) {
        this.imagePath = imagePath;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.capacity = capacity;
        this.horsepower = horsepower;
        this.color = color;
        this.mpg = mpg;
        this.vinNumber = vinNumber;
        this.registrationNumber = registrationNumber;
        this.rentalPriceDay = rentalPriceDay;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.seatingCapacity = seatingCapacity;
        this.availability = availability;
        this.features = features;
    }

    // Parameterized Constructor
    public Vehicle(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String vehicleType, int seatingCapacity,
            boolean availability, String features) {
        this.vehicleId = vehicleId;
        this.imagePath = imagePath;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.capacity = capacity;
        this.horsepower = horsepower;
        this.color = color;
        this.mpg = mpg;
        this.vinNumber = vinNumber;
        this.registrationNumber = registrationNumber;
        this.rentalPriceDay = rentalPriceDay;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.seatingCapacity = seatingCapacity;
        this.availability = availability;
        this.features = features;
    }

    // Getters and Setters
    public void setVehicle(int vehicleId, String imagePath, String brand, String model, int year, int capacity,
            int horsepower, String color, double mpg, String vinNumber, String registrationNumber,
            double rentalPriceDay, String transmission, String fuelType, String vehicleType, int seatingCapacity,
            boolean availability, String features) {
        this.vehicleId = vehicleId;
        this.imagePath = imagePath;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.capacity = capacity;
        this.horsepower = horsepower;
        this.color = color;
        this.mpg = mpg;
        this.vinNumber = vinNumber;
        this.registrationNumber = registrationNumber;
        this.rentalPriceDay = rentalPriceDay;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.seatingCapacity = seatingCapacity;
        this.availability = availability;
        this.features = features;
        this.vehicleType = vehicleType;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public double getRentalPriceDay() {
        return rentalPriceDay;
    }

    public void setRentalPriceDay(double rentalPriceDay) {
        this.rentalPriceDay = rentalPriceDay;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getMpg() {
        return mpg;
    }

    public void setMpg(double mpg) {
        this.mpg = mpg;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public static List<Vehicle> getVehicles() {
        return vehicles;
    }

    public static void setVehicles(List<Vehicle> setVehicle) {
        vehicles = setVehicle;
    }

    public String getVehicletype() {
        return vehicleType;
    }

    public void setVehicletype(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
