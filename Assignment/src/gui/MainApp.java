package gui;

import datamodels.User;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {

    public MainApp() {
//         try {
//             // Set the Windows Look and Feel
//             UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
        // Initialize frame
        setTitle("AQUILUXE");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(1280, 720));
        setMaximumSize(new Dimension(1920, 1080));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon carLogoIcon = new ImageIcon("images/icons/car-logo.png");
        setIconImage(carLogoIcon.getImage()); // Window Icon

        JPanel contentPanel = new JPanel(new BorderLayout());

        add(new GUIComponents(this, contentPanel, null), BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new User("Tan Yit Shen", "Male", "user@gmail.com", "123456789", "user1", "user1");

        new MainApp();
    }

}