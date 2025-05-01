package controllers;

import javax.swing.JFrame;
import javax.swing.JPanel;

import datamodels.Vehicle;
import gui.RentalPage;
import datamodels.Rental;
import datamodels.Customer;
import datamodels.Payment;
import services.PaymentService;
import services.RentalService;
import services.UserService;

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

    public void processCustomerAddress(Customer customer) {
        // call service save to db
        UserService.saveCustomerAddress(customer);
    }

    public int processRental(Rental rental) {
        // call service save to db
        int rentalId = RentalService.addRental(rental);
        return rentalId;
    }

    public void processPayment(Payment payment) {
        // generate token
        // call service save to db
        PaymentService.assignPaymentToken(payment, PaymentService.generateRandomToken());
        PaymentService.addPayment(payment);
    }

    // called by gui to get rental details
    // called by gui to pass rental details to rental service
}
