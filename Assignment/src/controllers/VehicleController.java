package controllers;

import datamodels.Vehicle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import services.VehicleService;

public class VehicleController {

    @SuppressWarnings("unused")
    private final JFrame frame;
    @SuppressWarnings("unused")
    private final JPanel panel;

    public VehicleController(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;
    }

    public static List<Vehicle> processVehicles() {
        return VehicleService.getAllVehiclesfromDAO();
    }

    public static List<Vehicle> processCars() {
        return VehicleService.getAllCarsfromDAO();
    }

    public static List<Vehicle> processBikes() {
        return VehicleService.getAllBikesfromDAO();
    }

    public static List<Vehicle> processFilteredCarBrand(List<Vehicle> car, String filterBrand) {
        return VehicleService.filterCarBrand(car, filterBrand);
    }

    public static List<Vehicle> processFilteredCarModel(List<Vehicle> car, String filterModel) {
        return VehicleService.filterCarModel(car, filterModel);
    }

    public static List<Vehicle> processFilteredCarYear(List<Vehicle> car, Object filterYear) {
        return VehicleService.filterCarYear(car, filterYear);
    }

    public static List<Vehicle> processFilteredCarTransmission(List<Vehicle> car, String filterTransmission) {
        return VehicleService.filterCarTransmission(car, filterTransmission);
    }

    public static List<Vehicle> processFilteredCarFuelType(List<Vehicle> car, String filterFuelType) {
        return VehicleService.filterCarFuelType(car, filterFuelType);
    }

    public static List<Vehicle> processFilteredCarAvailability(List<Vehicle> car, String filterAvailability) {
        return VehicleService.filterCarAvailability(car, filterAvailability);
    }

    public static List<Vehicle> processFilteredCarType(List<Vehicle> car, String filterCarType) {
        return VehicleService.filterCarType(car, filterCarType);
    }

    public static List<Vehicle> processFilteredCarSeats(List<Vehicle> car, int filterSeats) {
        return VehicleService.filterCarSeats(car, filterSeats);
    }

    public static List<Vehicle> processFilteredCarPrice(List<Vehicle> car, String filterMinPrice,
            String filterMaxPrice) {
        return VehicleService.filterCarPrice(car, filterMinPrice, filterMaxPrice);
    }

    public static List<Vehicle> processSortedByYearNewestFirst(List<Vehicle> car) {
        return VehicleService.sortByYearNewestFirst(car);
    }

    public static List<Vehicle> processSortedByYearOldestFirst(List<Vehicle> car) {
        return VehicleService.sortByYearOldestFirst(car);
    }

    public static List<Vehicle> processSortedByPriceLowToHigh(List<Vehicle> car) {
        return VehicleService.sortByPriceLowToHigh(car);
    }

    public static List<Vehicle> processSortedByPriceHighToLow(List<Vehicle> car) {
        return VehicleService.sortByPriceHighToLow(car);
    }

    public static List<Vehicle> processSearchCars(List<Vehicle> vehicles, String searchInput) {
        return VehicleService.searchCar(vehicles, searchInput);
    }

    public static List<String> processAllBrands(List<Vehicle> vehicles) {
        return VehicleService.getDistinctBrands(vehicles);
    }

    public static List<String> processAllModelsByBrand(List<Vehicle> vehicles, String brand) {
        return VehicleService.getDistinctModelsByBrand(vehicles, brand);
    }

    public static List<Vehicle> processAllFilterCombination(List<Vehicle> cars, String filterBrand, String filterModel,
            Object filterYear, String filterFuelType, String filterTransmission, String filterAvailability,
            String filterCarType, int filterSeats, String filterMinPrice, String filterMaxPrice, String searchInput) {
        List<Vehicle> filteredCars = new ArrayList<>(cars);
        filteredCars = processFilteredCarBrand(
                processFilteredCarModel(
                        processFilteredCarYear(processFilteredCarTransmission(
                                processFilteredCarFuelType(
                                        processFilteredCarAvailability(processFilteredCarType(
                                                processFilteredCarSeats(processFilteredCarPrice(
                                                        processSearchCars(filteredCars, searchInput),
                                                        filterMinPrice.trim(), filterMaxPrice.trim()), filterSeats),
                                                filterCarType), filterAvailability),
                                        filterFuelType),
                                filterTransmission), filterYear),
                        filterModel),
                filterBrand);
        return filteredCars;
    }

    public static String processClosestColorName(Color color) {

        return VehicleService.getClosestColorName(color);
    }

    public static boolean processColorValidation(Color color) {

        return VehicleService.validateColor(color);
    }

    public static boolean processVinNumberValidation(String vinNumber) {

        return VehicleService.validateVinNumber(vinNumber);
    }

    public static boolean processStringsValidation(String brand, String model) {

        return VehicleService.validateStrings( brand, model);
    }

    public static boolean processRegistrationNumberValidation(String registrationNumber) {

        return VehicleService.validateRegistrationNumber(registrationNumber);
    }

    public static boolean processRentalPriceDayValidation(String rentalPriceDay) {

        return VehicleService.validateRentalPriceDay(rentalPriceDay);
    }

    public static boolean processYearValidation(String year) {

        return VehicleService.validateYear(year);
    }

    public static boolean processMpgValidation(String mpg) {

        return VehicleService.validateMpg(mpg);
    }

    public static boolean processCapacityValidation(String capacity) {

        return VehicleService.validateCapacity(capacity);
    }

    public static boolean processHorsepowerValidation(String horsepower) {

        return VehicleService.validateHorsepower(horsepower);
    }

    public static boolean processTransmissionValidation(String transmission) {

        return VehicleService.validateTransmission(transmission);
    }

    public static boolean processFuelTypeValidation(String fuelType) {

        return VehicleService.validateFuelType(fuelType);
    }

    public static boolean processVehicleTypeValidation(String vehicleType) {

        return VehicleService.validateVehicleType(vehicleType);
    }

    public static boolean processImageValidation(File selectedImageFile, BufferedImage selectedImagePreview) {

        return VehicleService.validateImage(selectedImageFile, selectedImagePreview);
    }

    // submit add car (get all values)
    // public static String processAddCar(String vinNumber, String
    // registrationNumber, String brand, String model, String rentalPriceDay, String
    // transmission, String fueltype, String vehicleType, Color color, File
    // selectedImageFile, BufferedImage selectedImagePreview) {

    // else {
    // return "success";
    // }
    // }

    // validate vinNumber (18digit)

    // validate color (color cannot be null)

    // all cannot be null except features

    // validate picture (must be square and image naming must be distinct)
    public static boolean processImageSaving(File selectedImageFile, BufferedImage selectedImagePreview) {
        return VehicleService.imageSaving(selectedImageFile, selectedImagePreview);
    }

    public static String processGetImagePath(File selectedImageFile) {

        return VehicleService.getImage_Path(selectedImageFile);
    }

    public static void processAddVehiclestoDAO(Vehicle vehicle) {

        VehicleService.addVehiclestoDAO(vehicle);
    }

    public static void processDeleteVehiclefromDAO(Vehicle vehicle) {

        vehicle.setBrand("Deleted");
        vehicle.setModel("Deleted");
        vehicle.setYear(0);
        vehicle.setCapacity(0);
        vehicle.setHorsepower(0);
        vehicle.setColor("Deleted");
        vehicle.setMpg(0);
        vehicle.setVinNumber("Deleted");
        vehicle.setRegistrationNumber("Deleted");
        vehicle.setRentalPriceDay(0);
        vehicle.setTransmission("Deleted");
        vehicle.setFuelType("Deleted");
        vehicle.setVehicleType("Deleted");
        vehicle.setSeatingCapacity(0);
        vehicle.setAvailability(false);
        vehicle.setFeatures("Deleted");

        VehicleService.updateVehiclefromDAO(vehicle);
    }

    public static void processUpdateVehiclefromDAO(Vehicle updatedVehicle) {

        VehicleService.updateVehiclefromDAO(updatedVehicle);
    }
}
