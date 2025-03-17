package controllers;

import gui.RentalPage;
import datamodels.Car;

import javax.swing.*;

public class VehicleController {

    private final JFrame frame;
    private final JPanel panel;

    public VehicleController(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;
    }

    public void gotoRentalPage(Car selectedCar) {
        panel.removeAll();
        panel.add(new RentalPage(frame, panel, selectedCar)); // pass selected car
        panel.revalidate();
        panel.repaint();
    }
}
