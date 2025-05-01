package services;

import datamodels.Rental;
import datamodels.User;
import datamodels.Vehicle;
import database.RentalDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RentalService {
    // add Rental
    public static void addRental(Rental rental) {
        RentalDAO.addRental(rental);
    }

    // updateRentalStatus ( rental : Rental, status : RentalStatus )
    public static void updateRentalStatus(Rental rental, Rental.RentalStatus status) {
        rental.setRentalStatus(status);
        RentalDAO.updateRentalStatus(rental, status);
    }

    // updatePaymentStatus ( rental : Rental, status : PaymentStatus )
    public static void updatePaymentStatus(Rental rental, Rental.PaymentStatus status) {
        rental.setPaymentStatus(status);
        RentalDAO.updatePaymentStatus(rental, status);
    }

    // change start dates ( rental : Rental, newDate : LocalDate )
    public static void updateStartOrEndDate(Rental rental, LocalDate newDate) {
        if (newDate.isBefore(rental.getRentStartDate())) {
            rental.setRentStartDate(newDate);
            RentalDAO.updateRentalDates(rental, "start_date", newDate);
        } else {
            rental.setRentStartDate(newDate);
            RentalDAO.updateRentalDates(rental, "end_date", newDate);
        }
    }

    // change pickup/dropoff time ( rental : Rental, newTime : LocalTime )
    public static void updatePickupOrDropoffTime(Rental rental, LocalTime newTime) {
        if (newTime.isBefore(rental.getPickUpTime())) {
            rental.setPickUpTime(newTime);
            RentalDAO.updateRentalTimes(rental, "pickup_time", newTime);
        } else {
            rental.setDropoffTime(newTime);
            RentalDAO.updateRentalTimes(rental, "dropoff_time", newTime);
        }
    }

    // change total cost ( rental : Rental, newCost : double )
    public static void changeTotalCost(Rental rental, double newCost) {
        rental.setRentTotalCost(newCost);
        RentalDAO.updateRentalTotalCost(rental, newCost);
    }

    // deleteRental ( rental : Rental )
    public static void deleteRental(Rental rental) {
        RentalDAO.deleteRental(rental);
    }

    // getRentalHistory ( customer : Customer ) # overloaded
    public static enum FilterType {
        USER, VEHICLE, RENTAL_STATUS, PAYMENT_STATUS, DATE_RANGE
    }

    public static List<Rental> getRentalHistory(FilterType type, Object value) {
        switch (type) {
            case USER -> {
                return RentalDAO.getRentalsByUser((User) value);
            }
            case VEHICLE -> {
                return RentalDAO.getRentalsByVehicle((Vehicle) value);
            }
            case RENTAL_STATUS -> {
                return RentalDAO.getRentalsByStatus((Rental.RentalStatus) value);
            }
            case PAYMENT_STATUS -> {
                return RentalDAO.getRentalsByPaymentStatus((Rental.PaymentStatus) value);
            }
            default -> {
                throw new IllegalArgumentException("Unsupported filter type: " + type);
            }
        }
    }

    public static List<Rental> getRentalHistory(LocalDate startDate, LocalDate endDate) {
        return RentalDAO.getRentalsByDateRange(startDate, endDate);
    }

    // calculateBaseRentalCost : daysbetween * rentalpriceday
    public static double calculateBaseRentalCost(Rental rental) {
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(rental.getRentStartDate(),
                rental.getRentEndDate());

        if (daysBetween >= 0)
            daysBetween = daysBetween + 1;
        else
            daysBetween = 1;

        double rentalPricePerDay = rental.getRentVehicle().getRentalPriceDay();

        double baseRentalCost = daysBetween * rentalPricePerDay;

        return Math.round(baseRentalCost * 100.0) / 100.0;
    }

    // calculateDepositCost : 120% of base rental cost
    public static double calculateDepositCost(Rental rental) {
        double baseRentalCost = calculateBaseRentalCost(rental);

        double depositCost = baseRentalCost * 1.20;

        return Math.round(depositCost * 100.0) / 100.0;
    }

    // calculateInsuranceCost : 15% of base rental cost
    public static double calculateInsuranceCost(Rental rental) {
        double baseRentalCost = calculateBaseRentalCost(rental);

        double insuranceCost = baseRentalCost * 0.15;

        return Math.round(insuranceCost * 100.0) / 100.0;
    }

    // calculateTaxCost : 5% of base rental cost
    public static double calculateTaxCost(Rental rental) {
        double baseRentalCost = calculateBaseRentalCost(rental);

        double taxCost = baseRentalCost * 0.05;

        return Math.round(taxCost * 100.0) / 100.0;
    }

    // calculateSubtotalRentalCost without deposit cost
    public static double calculateSubtotalRentalCost(Rental rental) {
        double baseRentalCost = calculateBaseRentalCost(rental);
        double insuranceCost = calculateInsuranceCost(rental);
        double taxCost = calculateTaxCost(rental);

        double subtotalRentalCost = baseRentalCost + insuranceCost + taxCost;

        return Math.round(subtotalRentalCost * 100.0) / 100.0;
    }

    // calculateTotalRentalCost with deposit cost
    public static double calculateTotalRentalCost(Rental rental) {
        double subtotalRentalCost = calculateSubtotalRentalCost(rental);
        double depositCost = calculateDepositCost(rental);

        double totalRentalCost = subtotalRentalCost + depositCost;

        return Math.round(totalRentalCost * 100.0) / 100.0;
    }

}
