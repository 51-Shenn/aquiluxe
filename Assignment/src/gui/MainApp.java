package gui;

import controllers.UserController;
import controllers.VehicleController;
import datamodels.User;
import datamodels.Vehicle;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;

public class MainApp extends JFrame {

    private List<Vehicle> vehicles;
    private static GUIComponents guiComponents;

    public static GUIComponents getGuiComponents() {
        return guiComponents;
    }
    public static void setGuiComponents(GUIComponents components) {
        guiComponents = components;
    }

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
            guiComponents = new GUIComponents(this, contentPanel, currentUser);
            add(guiComponents, BorderLayout.NORTH);
        } else {
            guiComponents = new GUIComponents(this, contentPanel, null);
            add(guiComponents, BorderLayout.NORTH);
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