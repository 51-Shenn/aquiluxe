package gui;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {

        new MainWindow();
    }

    public static class MainWindow extends JFrame {

        public MainWindow() {
            // Initialize frame
            setTitle("AQUILUXE");
            setLayout(new BorderLayout());
            setMinimumSize(new Dimension(1280, 720));
            setMaximumSize(new Dimension(1920, 1080));
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ImageIcon carLogoIcon = new ImageIcon("images/icons/carLogo.png");
            setIconImage(carLogoIcon.getImage()); // Window Icon

            // add(new GUIComponents(this), BorderLayout.NORTH);
            add(new LoginPage());

            setVisible(true);
        }
    }
}