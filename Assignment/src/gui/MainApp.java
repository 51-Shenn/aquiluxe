package gui;

import controllers.UserController;
import database.VehicleDAO;
import datamodels.User;
import datamodels.Vehicle;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MainApp extends JFrame {

    public MainApp() {
        try {
            // Set the Windows Look and Feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("AQUILUXE");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(1280, 720));
        setMaximumSize(new Dimension(1920, 1080));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(IconLoader.getAppIcon().getImage()); // Window Icon

        JPanel contentPanel;
        contentPanel = new JPanel(new BorderLayout());

        File accountsFile = new File("files/settings/accounts.txt");
        if (accountsFile.exists()) {
            User currentUser = UserController.loadCurrentUser(accountsFile);
            add(new GUIComponents(this, contentPanel, currentUser), BorderLayout.NORTH);
        } else
            add(new GUIComponents(this, contentPanel, null), BorderLayout.NORTH);

        add(contentPanel, BorderLayout.CENTER);

        // Testing VehicleDAO
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles = VehicleDAO.getAllVehicles();
        System.out.println("Available Vehicles: " + vehicles);
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.getClass() + " " + vehicle.getBrand() + " " +
                    vehicle.getModel() + " " + vehicle.getVehicleType());
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainApp();
    }
}