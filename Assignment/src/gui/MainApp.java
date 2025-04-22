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

        setIconImage(IconLoader.getAppIcon().getImage()); // Window Icon

        // get vehicle list from database
        // load imageicons list from vehicle list
        // pass to vehicle controller
        // pass to vehicle page
        // iterate vehicle list and imageicons list

        JPanel contentPanel;
        contentPanel = new JPanel(new BorderLayout());

        File accountsFile = new File("files/settings/accounts.txt");
        if (accountsFile.exists()) {
            User currentUser = UserController.loadCurrentUser(accountsFile);
            add(new GUIComponents(this, contentPanel, currentUser), BorderLayout.NORTH);
        } else
            add(new GUIComponents(this, contentPanel, null), BorderLayout.NORTH);

        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainApp();
    }
}