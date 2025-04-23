package controllers;

import datamodels.Vehicle;
import datamodels.Car;
import datamodels.Bike;
import gui.RentalPage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import services.VehicleService;

public class VehicleController {

    private final JFrame frame;
    private final JPanel panel;

    public VehicleController(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;
    }

    public void gotoRentalPage(Vehicle selectedVehicle) {
        panel.removeAll();
        panel.add(new RentalPage(frame, panel, selectedVehicle)); // pass selected car
        panel.revalidate();
        panel.repaint();
    }

    public static List<Vehicle> processVehicles() {
        return VehicleService.passVehicles();
    }

    public static List<Car> processCars() {
        return VehicleService.passCars();
    }

    public static List<Bike> processBikes() {
        return VehicleService.passBikes();
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

    public static List<String> processAllBrands() {
        return VehicleService.getBrands();
    }

    public static List<String> processAllCarBrands() {
        return VehicleService.getCarBrands();
    }

    public static List<String> processAllBikeBrands() {
        return VehicleService.getBikeBrands();
    }

    public static List<String> processAllModelsByBrand(String brand) {
        return VehicleService.getModels(brand);
    }

    // and more

    // thoughts - when user use filter once then I NEED TO PASS THE Cars VALUE IN TO
    // THE ONE OF THE Filter Method
    // then if ANOTHER filter then i take the rest of th filtered once again (other
    // combobox is touched)
    // then if CHANGE filter such CHANGE Brand to ALL then i need to pass normal car
    // values then pass in to filter that werent removed (when The original combobox
    // is touched)

    public static List<Vehicle> processAllFilterCombination(List<Vehicle> cars, String filterBrand, String filterModel,
            Object filterYear, String filterFuelType, String filterTransmission, String filterAvailability,
            String filterCarType, int filterSeats, String filterMinPrice, String filterMaxPrice) {
        // , String filterTransmission, String filterFuelType, String
        // filterAvailability, String filterCarType, Object filterSeats, String
        // filterMinPrice, String filterMaxPrice
        List<Vehicle> filteredCars = new ArrayList<>(cars);
        filteredCars = processFilteredCarBrand(
            processFilteredCarModel(
                processFilteredCarYear(
                    processFilteredCarTransmission(
                        processFilteredCarFuelType(
                            processFilteredCarAvailability(
                                processFilteredCarType(
                                    processFilteredCarSeats(
                                        processFilteredCarPrice(filteredCars,
                                                        filterMinPrice.trim(), filterMaxPrice.trim()), filterSeats),
                                                filterCarType), filterAvailability),
                                        filterFuelType),
                                filterTransmission), filterYear),
                        filterModel),
                filterBrand);
        return filteredCars;
    }
}
