package gui;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {

    public MainApp() {
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
        contentPanel.add(new VehiclesPageDetails(this));

        add(new GUIComponents(this, contentPanel), BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainApp();
    }

    // try {
    // Desktop desktop = Desktop.getDesktop();
    // desktop.browse(new
    // URI("https://www.youtube.com/watch?v=Kmgo00avvEw&t=2835s"));
    // } catch (URISyntaxException | IOException e) {
    // throw new RuntimeException(e);
    // }
}