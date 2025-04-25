package gui;

import controllers.UserController;
import datamodels.User;
import java.awt.*;
import java.io.File;
import javax.swing.*;

public class MainApp extends JFrame {

    public MainApp() {
        Navigation.setWindowsLookAndFeel();

        setTitle("AQUILUXE");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(1280, 720));
        setMaximumSize(new Dimension(1920, 1080));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(false);
        setIconImage(IconLoader.getAppIcon().getImage()); // Window Icon
        JPanel contentPanel = new JPanel(new BorderLayout());

        File accountsFile = new File("files/settings/accounts.txt");
        User currentUser = accountsFile.exists() ? UserController.loadCurrentUser(accountsFile) : null;
        add(new GUIComponents(this, contentPanel, currentUser), BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        GUIComponents.showPage("HomePage");

        setVisible(true);
        setAlwaysOnTop(false);

        GUIComponents.loadVehiclesPageAsync(this, contentPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}