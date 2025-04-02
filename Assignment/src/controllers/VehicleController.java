package controllers;

import java.util.ArrayList;

import datamodels.Car;
import services.VehicleService;

public class VehicleController {

    public static ArrayList<Car> passFilteredCarBrand(ArrayList<Car> car, String filterBrand) {
        return VehicleService.filterCarBrand(car, filterBrand);
    }

    public static ArrayList<Car> passFilteredCarModel(ArrayList<Car> car, String filterModel) {
        return VehicleService.filterCarModel(car, filterModel);
    }

    public static ArrayList<Car> passFilteredCarYear(ArrayList<Car> car, Object filterYear) {
        return VehicleService.filterCarYear(car, filterYear);
    }

    public static ArrayList<Car> passFilteredCarTransmission(ArrayList<Car> car, String filterTransmission) {
        return VehicleService.filterCarTransmission(car, filterTransmission);
    }

    public static ArrayList<Car> passFilteredCarFuelType(ArrayList<Car> car, String filterFuelType) {
        return VehicleService.filterCarFuelType(car, filterFuelType);
    }
    
    public static ArrayList<Car> passFilteredCarAvailability(ArrayList<Car> car, String filterAvailability) {
        return VehicleService.filterCarAvailability(car, filterAvailability);
    }

    public static ArrayList<Car> passFilteredCarType(ArrayList<Car> car, String filterCarType) {
        return VehicleService.filterCarType(car, filterCarType);
    }

    public static ArrayList<Car> passFilteredCarSeats(ArrayList<Car> car, int filterSeats) {
        return VehicleService.filterCarSeats(car, filterSeats);
    }

    public static ArrayList<Car> passFilteredCarPrice(ArrayList<Car> car, String filterMinPrice, String filterMaxPrice) {
        return VehicleService.filterCarPrice(car, filterMinPrice, filterMaxPrice);
    }

    public static ArrayList<Car> passSortedByYearNewestFirst(ArrayList<Car> car){
        return VehicleService.sortByYearNewestFirst(car);
    }

    public static ArrayList<Car> passSortedByYearOldestFirst(ArrayList<Car> car){
        return VehicleService.sortByYearOldestFirst(car);
    }

    public static ArrayList<Car> passSortedByPriceLowToHigh(ArrayList<Car> car){
        return VehicleService.sortByPriceLowToHigh(car);
    }

    public static ArrayList<Car> passSortedByPriceHighToLow(ArrayList<Car> car){
        return VehicleService.sortByPriceHighToLow(car);
    }

    //and more

    //thoughts - when user use filter once then I NEED TO PASS THE Cars VALUE IN TO THE ONE OF THE Filter Method
    //          then if ANOTHER filter then i take the rest of th filtered once again (other combobox is touched)
    //          then if CHANGE filter such CHANGE Brand to ALL then i need to pass normal car values then pass in to filter that werent removed (when The original combobox is touched)

    public static ArrayList<Car> allFilterCombination(ArrayList<Car> cars, String filterBrand, String filterModel, Object filterYear, String filterFuelType, String filterTransmission, String filterAvailability, String filterCarType, int filterSeats, String filterMinPrice, String filterMaxPrice) {
        // , String filterTransmission, String filterFuelType, String filterAvailability, String filterCarType, Object filterSeats, String filterMinPrice, String filterMaxPrice
        ArrayList<Car> filteredCars = new ArrayList<>(cars);
        filteredCars = passFilteredCarBrand
                        (passFilteredCarModel
                        (passFilteredCarYear
                        (passFilteredCarTransmission
                        (passFilteredCarFuelType
                        (passFilteredCarAvailability
                        (passFilteredCarType
                        (passFilteredCarSeats
                        (passFilteredCarPrice(filteredCars, filterMinPrice.trim(), filterMaxPrice.trim()), filterSeats), filterCarType), filterAvailability), filterFuelType), filterTransmission)
                        , filterYear), filterModel), filterBrand);
        return filteredCars;
    }
}
