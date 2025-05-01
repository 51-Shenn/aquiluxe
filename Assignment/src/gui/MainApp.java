package gui;

import controllers.UserController;
import controllers.VehicleController;
import datamodels.User;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.util.List;
import datamodels.Vehicle;

public class MainApp extends JFrame {

    private List<Vehicle> vehicles;

    public MainApp() {
        Navigation.setWindowsLookAndFeel();

        loadDataAndCacheImages();

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
        } else {
            add(new GUIComponents(this, contentPanel, null), BorderLayout.NORTH);
        }
        add(contentPanel, BorderLayout.CENTER);

        setAlwaysOnTop(true);
        setVisible(true);
        setAlwaysOnTop(false);
    }

    private void loadDataAndCacheImages() {
        vehicles = VehicleController.processVehicles(); // Get all vehicles
        Vehicle.setVehicles(vehicles); // Store in Vehicle class
        ImageLoader.loadImages(vehicles); // Preload images
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp());
    }
}