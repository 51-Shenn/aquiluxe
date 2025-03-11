package gui;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {

        // testing haha
        // try {
        // Desktop desktop = Desktop.getDesktop();
        // desktop.browse(new
        // URI("https://www.youtube.com/watch?v=Kmgo00avvEw&t=2835s"));
        // } catch (URISyntaxException | IOException e) {
        // throw new RuntimeException(e);
        // }

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

            ImageIcon carLogoIcon = new ImageIcon("images/icons/car-logo.png");
            setIconImage(carLogoIcon.getImage()); // Window Icon

            add(new GUIComponents(this), BorderLayout.NORTH);

            setVisible(true);
        }
    }
}