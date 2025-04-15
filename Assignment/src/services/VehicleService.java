package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import datamodels.Vehicle;
import datamodels.Car;
import datamodels.Bike;
import datamodels.User;
import database.UserDAO;
import database.VehicleDAO;

public class VehicleService {
//filters
//methods that pass in cars and return them with filtered cars
//check car.getbrand 

    public static List<Vehicle> passAllVehicles() {
        VehicleDAO vehicleDAO = new VehicleDAO();

        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();

        return vehicles;
    }

    public static ArrayList<Car> filterCarBrand(ArrayList<Car> car, String filterBrand) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();

        if("ALL".equals(filterBrand)){
            return car;
        }
        for (Car c : car){
            if (c.getBrand().equals(filterBrand)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car.getmodel if got brand
    public static ArrayList<Car> filterCarModel(ArrayList<Car> car, String filterModel) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();

        if("ALL".equals(filterModel)){
            return car;
        }
        for (Car c : car){
            if (c.getModel().equals(filterModel)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car year
    public static ArrayList<Car> filterCarYear(ArrayList<Car> car, Object filterYear) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();
        if("ALL".equals(filterYear)){
            return car;
        }
        for (Car c : car){
            if (c.getYear() == Integer.parseInt(filterYear.toString())){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car transmission
    public static ArrayList<Car> filterCarTransmission(ArrayList<Car> car, String filterTransmission) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();
        if("ALL".equals(filterTransmission)){
            return car;
        }
        for (Car c : car){
            if (c.getTransmission().equals(filterTransmission)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car fuel type
    public static ArrayList<Car> filterCarFuelType(ArrayList<Car> car, String filterFuelType) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();
        if("ALL".equals(filterFuelType)){
            return car;
        }
        for (Car c : car){
            if (c.getFuelType().equals(filterFuelType)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check availability
    public static ArrayList<Car> filterCarAvailability(ArrayList<Car> car, String filterAvailability) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();
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
        for (Car c : car){
            if (c.isAvailability() == availability){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
// check car type
    public static ArrayList<Car> filterCarType(ArrayList<Car> car, String filterCarType) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();
        if("ALL".equals(filterCarType)){
            return car;
        }
        for (Car c : car){
            if (c.getCarType().equals(filterCarType)){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check car seats
    public static ArrayList<Car> filterCarSeats(ArrayList<Car> car, int filterSeats) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();
        if(filterSeats == 0){
            return car;
        }
        for (Car c : car){
            if (c.getSeatingCapacity() == filterSeats){
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
//check price range
    public static ArrayList<Car> filterCarPrice(ArrayList<Car> car, String filterMinPrice, String filterMaxPrice) {
        ArrayList<Car> filteredCars = new ArrayList<Car>();
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
            for (Car c : car){
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

    public static ArrayList<Car> sortByYearNewestFirst(ArrayList<Car> cars) {
        ArrayList<Car> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, new Comparator<Car>() {
            @Override
            public int compare(Car c1, Car c2) {
                // Newest first (descending order)
                return c2.getYear() - c1.getYear();
            }
        });
        return sortedCars;
    }

    public static ArrayList<Car> sortByYearOldestFirst(ArrayList<Car> cars) {
        ArrayList<Car> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, new Comparator<Car>() {
            @Override
            public int compare(Car c1, Car c2) {
                // Oldest first (ascending order)
                return c1.getYear() - c2.getYear();
            }
        });
        return sortedCars;
    }

    public static ArrayList<Car> sortByPriceLowToHigh(ArrayList<Car> cars) {
        ArrayList<Car> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, new Comparator<Car>() {
            @Override
            public int compare(Car c1, Car c2) {
                // Proper double comparison
                return Double.compare(c1.getRentalPriceDay(), c2.getRentalPriceDay());
            }
        });
        return sortedCars;
    }

    public static ArrayList<Car> sortByPriceHighToLow(ArrayList<Car> cars) {
        ArrayList<Car> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, new Comparator<Car>() {
            @Override
            public int compare(Car c1, Car c2) {
                // Reverse the comparison for descending order
                return Double.compare(c2.getRentalPriceDay(), c1.getRentalPriceDay());
            }
        });
        return sortedCars;
    }
}
