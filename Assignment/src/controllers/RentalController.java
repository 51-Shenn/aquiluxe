package controllers;

import javax.swing.JFrame;
import javax.swing.JPanel;

import datamodels.Vehicle;
import gui.RentalPage;

public class RentalController {

    private final JFrame frame;
    private final JPanel panel;

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

    // called by gui to get rental details
    // called by gui to pass rental details to rental service
}
