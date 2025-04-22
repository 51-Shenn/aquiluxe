package controllers;

import datamodels.Vehicle;
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

    public static List<Vehicle> getAllVehicles() {
        return VehicleService.getAllVehiclesfromDAO();
    }

    public static List<Vehicle> passFilteredCarBrand(List<Vehicle> car, String filterBrand) {
        return VehicleService.filterCarBrand(car, filterBrand);
    }

    public static List<Vehicle> passFilteredCarModel(List<Vehicle> car, String filterModel) {
        return VehicleService.filterCarModel(car, filterModel);
    }

    public static List<Vehicle> passFilteredCarYear(List<Vehicle> car, Object filterYear) {
        return VehicleService.filterCarYear(car, filterYear);
    }

    public static List<Vehicle> passFilteredCarTransmission(List<Vehicle> car, String filterTransmission) {
        return VehicleService.filterCarTransmission(car, filterTransmission);
    }

    public static List<Vehicle> passFilteredCarFuelType(List<Vehicle> car, String filterFuelType) {
        return VehicleService.filterCarFuelType(car, filterFuelType);
    }

    public static List<Vehicle> passFilteredCarAvailability(List<Vehicle> car, String filterAvailability) {
        return VehicleService.filterCarAvailability(car, filterAvailability);
    }

    public static List<Vehicle> passFilteredCarType(List<Vehicle> car, String filterCarType) {
        return VehicleService.filterCarType(car, filterCarType);
    }

    public static List<Vehicle> passFilteredCarSeats(List<Vehicle> car, int filterSeats) {
        return VehicleService.filterCarSeats(car, filterSeats);
    }

    public static List<Vehicle> passFilteredCarPrice(List<Vehicle> car, String filterMinPrice,
            String filterMaxPrice) {
        return VehicleService.filterCarPrice(car, filterMinPrice, filterMaxPrice);
    }

    public static List<Vehicle> passSortedByYearNewestFirst(List<Vehicle> car) {
        return VehicleService.sortByYearNewestFirst(car);
    }

    public static List<Vehicle> passSortedByYearOldestFirst(List<Vehicle> car) {
        return VehicleService.sortByYearOldestFirst(car);
    }

    public static List<Vehicle> passSortedByPriceLowToHigh(List<Vehicle> car) {
        return VehicleService.sortByPriceLowToHigh(car);
    }

    public static List<Vehicle> passSortedByPriceHighToLow(List<Vehicle> car) {
        return VehicleService.sortByPriceHighToLow(car);
    }

    public static List<String> passAllBrands(List<Vehicle> vehicles) {
        return VehicleService.getDistinctBrands(vehicles);
    }

    public static List<String> passAllModelsByBrand(List<Vehicle> vehicles, String brand) {
        return VehicleService.getDistinctModelsByBrand(vehicles, brand);
    }

    // and more

    // thoughts - when user use filter once then I NEED TO PASS THE Cars VALUE IN TO
    // THE ONE OF THE Filter Method
    // then if ANOTHER filter then i take the rest of th filtered once again (other
    // combobox is touched)
    // then if CHANGE filter such CHANGE Brand to ALL then i need to pass normal car
    // values then pass in to filter that werent removed (when The original combobox
    // is touched)

    public static List<Vehicle> allFilterCombination(List<Vehicle> cars, String filterBrand, String filterModel,
            Object filterYear, String filterFuelType, String filterTransmission, String filterAvailability,
            String filterCarType, int filterSeats, String filterMinPrice, String filterMaxPrice) {
        // , String filterTransmission, String filterFuelType, String
        // filterAvailability, String filterCarType, Object filterSeats, String
        // filterMinPrice, String filterMaxPrice
        List<Vehicle> filteredCars = new ArrayList<>(cars);
        filteredCars = passFilteredCarBrand(
                passFilteredCarModel(
                        passFilteredCarYear(passFilteredCarTransmission(
                                passFilteredCarFuelType(
                                        passFilteredCarAvailability(passFilteredCarType(
                                                passFilteredCarSeats(passFilteredCarPrice(filteredCars,
                                                        filterMinPrice.trim(), filterMaxPrice.trim()), filterSeats),
                                                filterCarType), filterAvailability),
                                        filterFuelType),
                                filterTransmission), filterYear),
                        filterModel),
                filterBrand);
        return filteredCars;
    }
}
