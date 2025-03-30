package controllers;

import java.util.ArrayList;

import datamodels.Car;
import services.VehicleService;

public class VehicleController {

    public static ArrayList<Car> passFilteredCarBrand(ArrayList<Car> car, String filterBrand) {
        return VehicleService.filterCarBrand(car, filterBrand);
    }

    public static ArrayList<Car> passFilteredCarModel(ArrayList<Car> car, String filterModel) {
        return VehicleService.filterCarBrand(car, filterModel);
    }

    public static ArrayList<Car> passFilteredCarYear(ArrayList<Car> car, int filterYear) {
        return VehicleService.filterCarYear(car, filterYear);
    }

    public static ArrayList<Car> passFilteredCarTransmission(ArrayList<Car> car, String filterTransmission) {
        return VehicleService.filterCarTransmission(car, filterTransmission);
    }

    public static ArrayList<Car> passFilteredCarFuelType(ArrayList<Car> car, String filterFuelType) {
        return VehicleService.filterCarFuelType(car, filterFuelType);
    }
    
    public static ArrayList<Car> passFilteredCarAvailability(ArrayList<Car> car, boolean filterAvailability) {
        return VehicleService.filterCarAvailability(car, filterAvailability);
    }

    public static ArrayList<Car> passFilteredCarType(ArrayList<Car> car, String filterCarType) {
        return VehicleService.filterCarType(car, filterCarType);
    }

    public static ArrayList<Car> passFilteredCarSeats(ArrayList<Car> car, int filterSeats) {
        return VehicleService.filterCarSeats(car, filterSeats);
    }

    public static ArrayList<Car> passFilteredCarPrice(ArrayList<Car> car, int filterMinPrice, int filterMaxPrice) {
        return VehicleService.filterCarPrice(car, filterMinPrice, filterMaxPrice);
    }

    //and more

    //thoughts - when user use filter once then I NEED TO PASS THE Cars VALUE IN TO THE ONE OF THE Filter Method
    //          then if ANOTHER filter then i take the rest of th filtered once again (other combobox is touched)
    //          then if CHANGE filter such CHANGE Brand to ALL then i need to pass normal car values then pass in to filter that werent removed (when The original combobox is touched)

}
