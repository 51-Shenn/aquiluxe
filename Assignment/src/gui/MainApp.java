package gui;

import database.UserDAO;
import datamodels.User;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

        UserDAO userDAO = new UserDAO();

        File accountsFile = new File("files/settings/accounts.txt");
        if (accountsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
                String line = reader.readLine();
                User loadUserFromFile = userDAO.getUserById(Integer.parseInt(line.trim()));
                add(new GUIComponents(this, contentPanel, loadUserFromFile), BorderLayout.NORTH);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "Error reading file: " + accountsFile);
            }
        } else
            add(new GUIComponents(this, contentPanel, null), BorderLayout.NORTH);

        add(contentPanel, BorderLayout.CENTER);

        Dialog dialog = new Dialog(this);
        dialog.showSuccessDialog();

        setVisible(true);
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User.setUsers(userDAO.getAllUsers());
        System.out.println(User.getUsers());

        new MainApp();
    }

}