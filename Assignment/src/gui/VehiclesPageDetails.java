package gui;

import javax.swing.*;
import java.awt.*;

public class VehiclesPageDetails extends JPanel {

    public VehiclesPageDetails(JFrame frame) {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        this.add(carMainContainer(), BorderLayout.CENTER);
    }

    private JScrollPane carMainContainer() {
        JPanel carMainPanel = new JPanel(new BorderLayout());
        carMainPanel.add(bottomBar(), BorderLayout.SOUTH);
        carMainPanel.add(moreCarsPanel(), BorderLayout.CENTER);
        carMainPanel.add(carDetailsPanel(), BorderLayout.NORTH);

        JScrollPane carMainContainer = new JScrollPane(carMainPanel);
        carMainContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        carMainContainer.getVerticalScrollBar().setUnitIncrement(30);

        return carMainContainer;
    }

    // Helper method to add components to the grid
    private void addToGrid(JPanel panel, Component component, GridBagConstraints gbc,
                          int gridx, int gridy, int gridwidth, int gridheight,
                          double weightx, double weighty, int insetsTop, int insetsLeft,
                          int insetsBottom, int insetsRight, int fill, int anchor) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.insets = new Insets(insetsTop, insetsLeft, insetsBottom, insetsRight);
        gbc.fill = fill;
        gbc.anchor = anchor;
        panel.add(component, gbc);
    }

    // Top panel
    private JPanel carDetailsPanel() {
        // Sample data
        String brand = "Porsche";
        String model = "GT3 RS";
        int year = 2023, pricePerDay = 0;

        // Images
        ImageIcon image = new ImageIcon("images/cars/Supra.jpg");
        JLabel carPicture = new JLabel(image);
        ImageIcon transmissionIcon = new ImageIcon("images/vehiclepageicons/manual-transmission.png");
        ImageIcon fuelIcon = new ImageIcon("images/vehiclepageicons/gas-station.png");
        ImageIcon seatsIcon = new ImageIcon("images/vehiclepageicons/car-seat.png");
        ImageIcon carTypeIcon = new ImageIcon("images/vehiclepageicons/chassis.png");
        ImageIcon topSpeedIcon = new ImageIcon("images/vehiclepageicons/speedometer.png");
        ImageIcon capacityIcon = new ImageIcon("images/vehiclepageicons/engine.png");

        JLabel brandModelYearLabel = new JLabel(brand + " " + model + " " + year);
        brandModelYearLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));

        JLabel pricePerDayLabel = new JLabel("RM" + pricePerDay + " / day");
        pricePerDayLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JLabel featuresLabel = new JLabel("Features");
        featuresLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JLabel detailsLabel = new JLabel("Details");
        detailsLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JPanel brandModelYearPricePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        brandModelYearPricePanel.add(brandModelYearLabel);
        brandModelYearPricePanel.add(pricePerDayLabel);

        JPanel detailsLabelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        detailsLabelPanel.add(detailsLabel);
        detailsLabelPanel.setPreferredSize(new Dimension(600, 75));

        JPanel picturePanel = new JPanel(new BorderLayout());
        picturePanel.add(carPicture, BorderLayout.CENTER);

        JPanel featuresPanel = new JPanel(new BorderLayout());
        featuresPanel.add(featuresLabel,BorderLayout.CENTER);

        JPanel textContainer = new JPanel(new BorderLayout());
        textContainer.setPreferredSize(new Dimension(600, 75));
        textContainer.add(brandModelYearPricePanel, BorderLayout.CENTER);

        JPanel carPictureContainer = new JPanel(new BorderLayout());
        carPictureContainer.setPreferredSize(new Dimension(600, 600)); // Same size as car image
        carPictureContainer.add(carPicture, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new GridLayout(3, 3, 2, 2));
        detailsPanel.add(new JLabel(transmissionIcon));
        detailsPanel.add(new JLabel(fuelIcon));
        detailsPanel.add(new JLabel(seatsIcon));
        detailsPanel.add(new JLabel(carTypeIcon));
        detailsPanel.add(new JLabel(topSpeedIcon));
        detailsPanel.add(new JLabel(capacityIcon));

        JButton rentButton = new JButton("RENT");

        JPanel carDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        carDetailsPanel.setPreferredSize(new Dimension(800, 1000));
        carDetailsPanel.setBackground(Color.BLUE);

        // Add components to the grid using the helper method
        // Text container on top of the picture (aligned with the top of the picture)
        addToGrid(carDetailsPanel, textContainer, gbc,
        0, 0, 5, 1, 0.5, 0, 10, 50, 10, 20, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST);

        // Details label on top of the details panel
        addToGrid(carDetailsPanel, detailsLabelPanel, gbc,
        5, 0, 5, 1, 0.5, 0, 10, 20, 10, 50, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);

        // Picture takes 5x5 grid (below the text container)
        addToGrid(carDetailsPanel, carPictureContainer, gbc,
        0, 1, 5, 5, 0.5, 1.0, 0, 50, 10, 20, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        addToGrid(carDetailsPanel, featuresPanel, gbc,
        0, 6, 5, 5, 0.5, 1.0, 0, 50, 10, 20, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        // Details take 5x4 grid on the right (below the details label)
        addToGrid(carDetailsPanel, detailsPanel, gbc,
        5, 1, 4, 4, 0.5, 1.0, 0, 20, 10, 50, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        // Rent button takes 5x1 grid below the details
        addToGrid(carDetailsPanel, rentButton, gbc,
        5, 5, 5, 1, 0.5, 0, 0, 20, 0, 50, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
        return carDetailsPanel;
    }

    // Center panel
    private JPanel moreCarsPanel() {
        JPanel moreCarsPanel = new JPanel(new GridLayout());
        moreCarsPanel.setPreferredSize(new Dimension(800, 500));

        return moreCarsPanel;
    }

    // Bottom panel
    private JPanel bottomBar() {
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.BLACK);
        bottomBar.setPreferredSize(new Dimension(800, 500));

        return bottomBar;
    }
}