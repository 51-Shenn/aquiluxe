package services;

import datamodels.Rental;
import database.RentalDAO;

public class RentalDriver {
    public static void main(String[] args) {
        Rental rental = RentalDAO.getAllRentals().get(0);
        System.out.println(RentalService.calculateDepositCost(rental));
    }
}
