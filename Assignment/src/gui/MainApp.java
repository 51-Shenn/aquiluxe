package gui;

import controllers.UserController;
import database.UserDAO;
import datamodels.User;
import java.awt.*;
import java.io.File;
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

        ImageIcon carLogoIcon = new ImageIcon("images/icons/car-logo.png");
        setIconImage(carLogoIcon.getImage()); // Window Icon

        JPanel contentPanel = new JPanel(new BorderLayout());

        File accountsFile = new File("files/settings/accounts.txt");
        if (accountsFile.exists()) {
            User currentUser = UserController.loadCurrentUser(accountsFile);
            add(new GUIComponents(this, contentPanel, currentUser), BorderLayout.NORTH);
        }
        else add(new GUIComponents(this, contentPanel, null), BorderLayout.NORTH);
        
        add(contentPanel, BorderLayout.CENTER);

        // Dialog dialog = new Dialog(this);
        // dialog.showSuccessDialog();

        setVisible(true);
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User.setUsers(userDAO.getAllUsers());
        System.out.println(User.getUsers());

        new MainApp();
    }
}