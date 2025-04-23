package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import datamodels.Vehicle;
import datamodels.Bike;
import datamodels.Car;
import database.VehicleDAO;
public class VehicleService {
//filters
//methods that pass in cars and return them with filtered cars
//check car.getbrand
    public static List<String> getBrands() {

        List<String> brands = new ArrayList<>(VehicleDAO.getFiltersValues("brand"));

        return brands;
    }

    public static List<String> getCarBrands() {

        List<Car> cars = new ArrayList<>(VehicleDAO.getAllCars());
        List<String> brands = new ArrayList<>();

        for (Car car : cars){
            if (!(brands.contains(car.getBrand()))){
                brands.add(car.getBrand());
            }
        }

        return brands;
    }

    public static List<String> getBikeBrands() {

        List<Bike> bikes = new ArrayList<>(VehicleDAO.getAllBikes());
        List<String> brands = new ArrayList<>();

        for (Bike bike : bikes){
            if (!(brands.contains(bike.getBrand()))){
                brands.add(bike.getBrand());
            }
        }

        return brands;
    }

    public static List<String> getModels(String brand) {

        List<String> models = new ArrayList<>(VehicleDAO.getModelsByBrand(brand));

        return models;
    }

    public static List<Vehicle> passVehicles() {

        List<Vehicle> vehicles = new ArrayList<>(VehicleDAO.getAllVehicles());

        return vehicles;
    }

    public static List<Car> passCars() {

        List<Car> vehicles = new ArrayList<>(VehicleDAO.getAllCars());

        return vehicles;
    }

    public static List<Bike> passBikes() {

        List<Bike> vehicles = new ArrayList<>(VehicleDAO.getAllBikes());

        return vehicles;
    }

    public static List<Vehicle> filterCarBrand(List<Vehicle> car, String filterBrand) {
        List<Vehicle> filteredCars = new ArrayList<>();

        if("ALL".equals(filterBrand)){
            return car;
        }
        for (Vehicle c : car){
            if (c.getBrand().equals(filterBrand)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car.getmodel if got brand
    public static List<Vehicle> filterCarModel(List<Vehicle> car, String filterModel) {
        List<Vehicle> filteredCars = new ArrayList<>();

        if("ALL".equals(filterModel)){
            return car;
        }
        for (Vehicle c : car){
            if (c.getModel().equals(filterModel)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car year
    public static List<Vehicle> filterCarYear(List<Vehicle> car, Object filterYear) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if("ALL".equals(filterYear)){
            return car;
        }
        for (Vehicle c : car){
            if (c.getYear() == Integer.parseInt(filterYear.toString())){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car transmission
    public static List<Vehicle> filterCarTransmission(List<Vehicle> car, String filterTransmission) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if("ALL".equals(filterTransmission)){
            return car;
        }
        for (Vehicle c : car){
            if (c.getTransmission().toUpperCase().contains(filterTransmission)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car fuel type
    public static List<Vehicle> filterCarFuelType(List<Vehicle> car, String filterFuelType) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if("ALL".equals(filterFuelType)){
            return car;
        }
        for (Vehicle c : car){
            if (c.getFuelType().toUpperCase().equals(filterFuelType)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check availability
    public static List<Vehicle> filterCarAvailability(List<Vehicle> car, String filterAvailability) {
        List<Vehicle> filteredCars = new ArrayList<>();
        boolean availability;
        if(filterAvailability.equals("AVAILABLE")){
            availability = true;
        }
        else if (filterAvailability.equals("UNAVAILABLE")){
            availability = false;
        }
        else{
            return car;
        }
        for (Vehicle c : car){
            if (c.isAvailability() == availability){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
// check car type
    public static List<Vehicle> filterCarType(List<Vehicle> car, String filterCarType) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if("ALL".equals(filterCarType)){
            return car;
        }
        for (Vehicle c : car){
            if (c.getVehicleType().toUpperCase().equals(filterCarType)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car seats
    public static List<Vehicle> filterCarSeats(List<Vehicle> car, int filterSeats) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if(filterSeats == 0){
            return car;
        }
        for (Vehicle c : car){
            if (c.getSeatingCapacity() == filterSeats){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check price range
    public static List<Vehicle> filterCarPrice(List<Vehicle> car, String filterMinPrice, String filterMaxPrice) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if (filterMaxPrice.equals("Max") && filterMinPrice.equals("Min")){
            return car;
        }
        if (filterMaxPrice.equals("Max") || filterMaxPrice.equals("")){
            filterMaxPrice = Double.toString(Double.MAX_VALUE);
        }
        if (filterMinPrice.equals("Min") || filterMinPrice.equals("")){
            filterMinPrice = "0";
        }

        try{
            for (Vehicle c : car){
                if (c.getRentalPriceDay() <= Double.parseDouble(filterMaxPrice) && c.getRentalPriceDay() >= Double.parseDouble(filterMinPrice)){
                    filteredCars.add(c);
                }
            }
        }
        catch (NumberFormatException e){
            return filteredCars;
        }
        return filteredCars;
    }

    public static List<Vehicle> sortByYearNewestFirst(List<Vehicle> cars) {
        List<Vehicle> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle c1, Vehicle c2) {
                // Newest first (descending order)
                return c2.getYear() - c1.getYear();
            }
        });
        return sortedCars;
    }

    public static List<Vehicle> sortByYearOldestFirst(List<Vehicle> cars) {
        List<Vehicle> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle c1, Vehicle c2) {
                // Oldest first (ascending order)
                return c1.getYear() - c2.getYear();
            }
        });
        return sortedCars;
    }

    public static List<Vehicle> sortByPriceLowToHigh(List<Vehicle> cars) {
        List<Vehicle> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle c1, Vehicle c2) {
                // Proper double comparison
                return Double.compare(c1.getRentalPriceDay(), c2.getRentalPriceDay());
            }
        });
        return sortedCars;
    }

    public static List<Vehicle> sortByPriceHighToLow(List<Vehicle> cars) {
        List<Vehicle> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle c1, Vehicle c2) {
                // Reverse the comparison for descending order
                return Double.compare(c2.getRentalPriceDay(), c1.getRentalPriceDay());
            }
        });
        return sortedCars;
    }

}
