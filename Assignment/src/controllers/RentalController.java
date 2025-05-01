package controllers;

import javax.swing.JFrame;
import javax.swing.JPanel;

import datamodels.Vehicle;
import gui.RentalPage;
import datamodels.Rental;
import services.RentalService;

public class RentalController {

    private final JFrame frame;
    private final JPanel panel;

    public RentalController() {
        this.frame = new JFrame();
        this.panel = new JPanel();
    }

    public RentalController(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;
    }

    public void gotoRentalPage(Vehicle selectedCar) {
        panel.removeAll();
        panel.add(new RentalPage(frame, panel, selectedCar)); // pass selected car
        panel.revalidate();
        panel.repaint();
    }

    public double processRentalTotalCost(Rental rental) {
        return RentalService.calculateTotalRentalCost(rental);
    }

    public double[] processRentalCosts(Rental rental) {
        double[] rentalCosts = { RentalService.calculateBaseRentalCost(rental),
                RentalService.calculateInsuranceCost(rental),
                RentalService.calculateDepositCost(rental),
                RentalService.calculateTaxCost(rental) };
        return rentalCosts;
    }

    // called by gui to get rental details
    // called by gui to pass rental details to rental service
}
