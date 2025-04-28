package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.Color;

import datamodels.Vehicle;
import database.VehicleDAO;

public class VehicleService {
    // filters
    // methods that pass in cars and return them with filtered cars
    // check car.getbrand
    private static final Map<String, Color> standardColors = new HashMap<>();

    static {
        // Add standard colors
        standardColors.put("Black", Color.BLACK);
        standardColors.put("Blue", Color.BLUE);
        standardColors.put("Cyan", Color.CYAN);
        standardColors.put("Dark Gray", Color.DARK_GRAY);
        standardColors.put("Gray", Color.GRAY);
        standardColors.put("Green", Color.GREEN);
        standardColors.put("Light Gray", Color.LIGHT_GRAY);
        standardColors.put("Magenta", Color.MAGENTA);
        standardColors.put("Orange", Color.ORANGE);
        standardColors.put("Pink", Color.PINK);
        standardColors.put("Red", Color.RED);
        standardColors.put("White", Color.WHITE);
        standardColors.put("Yellow", Color.YELLOW);
    }

    public static List<String> getDistinctBrands(List<Vehicle> vehicles) {
        Set<String> distinctBrands = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            distinctBrands.add(vehicle.getBrand());
        }

        result.addAll(distinctBrands); // Convert Set to List

        return result;
    }

    public static List<String> getDistinctModelsByBrand(List<Vehicle> vehicles, String brand) {
        Set<String> distinctModels = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getBrand().equalsIgnoreCase(brand)) {
                distinctModels.add(vehicle.getModel());
            }
        }

        result.addAll(distinctModels); // Convert Set to List

        return result;
    }

    public static List<Vehicle> getAllVehiclesfromDAO() {

        List<Vehicle> vehicles = new ArrayList<>(VehicleDAO.getAllVehicles());

        return vehicles;
    }

    public static List<Vehicle> getAllCarsfromDAO() {

        List<Vehicle> vehicles = new ArrayList<>(VehicleDAO.getAllCars());

        return vehicles;
    }

    public static List<Vehicle> getAllBikesfromDAO() {

        List<Vehicle> vehicles = new ArrayList<>(VehicleDAO.getAllBikes());

        return vehicles;
    }

    public static List<Vehicle> filterCarBrand(List<Vehicle> car, String filterBrand) {
        List<Vehicle> filteredCars = new ArrayList<>();

        if ("ALL".equals(filterBrand)) {
            return car;
        }
        for (Vehicle c : car) {
            if (c.getBrand().equals(filterBrand)) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    // check car.getmodel if got brand
    public static List<Vehicle> filterCarModel(List<Vehicle> car, String filterModel) {
        List<Vehicle> filteredCars = new ArrayList<>();

        if ("ALL".equals(filterModel)) {
            return car;
        }
        for (Vehicle c : car) {
            if (c.getModel().equals(filterModel)) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    // check car year
    public static List<Vehicle> filterCarYear(List<Vehicle> car, Object filterYear) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if ("ALL".equals(filterYear)) {
            return car;
        }
        for (Vehicle c : car) {
            if (c.getYear() == Integer.parseInt(filterYear.toString())) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    // check car transmission
    public static List<Vehicle> filterCarTransmission(List<Vehicle> car, String filterTransmission) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if ("ALL".equals(filterTransmission)) {
            return car;
        }
        for (Vehicle c : car) {
            if (c.getTransmission().toUpperCase().contains(filterTransmission)) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    // check car fuel type
    public static List<Vehicle> filterCarFuelType(List<Vehicle> car, String filterFuelType) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if ("ALL".equals(filterFuelType)) {
            return car;
        }
        for (Vehicle c : car) {
            if (c.getFuelType().toUpperCase().equals(filterFuelType)) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    // check availability
    public static List<Vehicle> filterCarAvailability(List<Vehicle> car, String filterAvailability) {
        List<Vehicle> filteredCars = new ArrayList<>();
        boolean availability;
        if (filterAvailability.equals("AVAILABLE")) {
            availability = true;
        } else if (filterAvailability.equals("UNAVAILABLE")) {
            availability = false;
        } else {
            return car;
        }
        for (Vehicle c : car) {
            if (c.isAvailability() == availability) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    // check car type
    public static List<Vehicle> filterCarType(List<Vehicle> car, String filterCarType) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if ("ALL".equals(filterCarType)) {
            return car;
        }
        for (Vehicle c : car) {
            if (c.getVehicleType().toUpperCase().equals(filterCarType)) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    // check car seats
    public static List<Vehicle> filterCarSeats(List<Vehicle> car, int filterSeats) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if (filterSeats == 0) {
            return car;
        }
        for (Vehicle c : car) {
            if (c.getSeatingCapacity() == filterSeats) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    // check price range
    public static List<Vehicle> filterCarPrice(List<Vehicle> car, String filterMinPrice, String filterMaxPrice) {
        List<Vehicle> filteredCars = new ArrayList<>();
        if (filterMaxPrice.equals("Max") && filterMinPrice.equals("Min")) {
            return car;
        }
        if (filterMaxPrice.equals("Max") || filterMaxPrice.equals("")) {
            filterMaxPrice = Double.toString(Double.MAX_VALUE);
        }
        if (filterMinPrice.equals("Min") || filterMinPrice.equals("")) {
            filterMinPrice = "0";
        }

        try {
            for (Vehicle c : car) {
                if (c.getRentalPriceDay() <= Double.parseDouble(filterMaxPrice)
                        && c.getRentalPriceDay() >= Double.parseDouble(filterMinPrice)) {
                    filteredCars.add(c);
                }
            }
        } catch (NumberFormatException e) {
            return filteredCars;
        }
        return filteredCars;
    }

    // check search bar
    public static List<Vehicle> searchCar(List<Vehicle> car, String searchInput) {
        List<Vehicle> filteredCars = new ArrayList<>();

        searchInput = searchInput.toUpperCase();

        if (searchInput.equals("SEARCH FOR VEHICLES") || searchInput.equals("")) {
            return car;
        }
        else {
            for (Vehicle c : car) {
                if (c.getBrand().toUpperCase().contains(searchInput) || c.getModel().toUpperCase().contains(searchInput) || Integer.toString(c.getYear()).contains(searchInput) || c.getTransmission().toUpperCase().contains(searchInput) || 
                c.getFuelType().toUpperCase().contains(searchInput) || c.getVehicleType().toUpperCase().contains(searchInput) || c.getFeatures().toUpperCase().contains(searchInput)) {
                        filteredCars.add(c);
                }
            }
            return filteredCars;
        }

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

    public static String getClosestColorName(Color color) {
        try {
            if (color.equals(Color.BLACK)) return "Black";
            if (color.equals(Color.BLUE)) return "Blue";
            if (color.equals(Color.CYAN)) return "Cyan";
            if (color.equals(Color.DARK_GRAY)) return "Dark Gray";
            if (color.equals(Color.GRAY)) return "Gray";
            if (color.equals(Color.GREEN)) return "Green";
            if (color.equals(Color.LIGHT_GRAY)) return "Light Gray";
            if (color.equals(Color.MAGENTA)) return "Magenta";
            if (color.equals(Color.ORANGE)) return "Orange";
            if (color.equals(Color.PINK)) return "Pink";
            if (color.equals(Color.RED)) return "Red";
            if (color.equals(Color.WHITE)) return "White";
            if (color.equals(Color.YELLOW)) return "Yellow";
            
            String customName = "Custom ";
            String colorName = "";
            double closestDistance = Double.MAX_VALUE;

            for (Map.Entry<String, Color> entry : standardColors.entrySet()) {
                double distance = colorDistance(color, entry.getValue());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    colorName = entry.getKey();
                }
            }

            return customName.concat(colorName);
        }
        catch(NullPointerException e) {
            return "Pick A Color";
        }
    }

    private static double colorDistance(Color c1, Color c2) {
        int rDiff = c1.getRed() - c2.getRed();
        int gDiff = c1.getGreen() - c2.getGreen();
        int bDiff = c1.getBlue() - c2.getBlue();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }

}
