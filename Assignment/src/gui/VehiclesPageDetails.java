package gui;

import javax.swing.*;
import java.awt.*;

public class VehiclesPageDetails extends JPanel {

    public VehiclesPageDetails(JFrame frame) {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        this.add(MainContainer(), BorderLayout.CENTER);
    }

    private JScrollPane MainContainer() {
        JPanel carMainPanel = new JPanel(new BorderLayout());
        carMainPanel.add(bottomBar(), BorderLayout.SOUTH);
        carMainPanel.add(moreCarsPanel(), BorderLayout.CENTER);
        carMainPanel.add(carMainPanel(), BorderLayout.NORTH);
        
        JScrollPane carMainContainer = new JScrollPane(carMainPanel);
        carMainContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        carMainContainer.getVerticalScrollBar().setUnitIncrement(30);

        return carMainContainer;
    }

    // Top panel
    private JPanel carMainPanel() {
        /*  Sample data
        String brand = "Porsche";
        String model = "GT3 RS";
        int year = 2023, pricePerDay = 0;

        /*  Images
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
        gbc.insets = new Insets(10,10,10,10);
        carDetailsPanel.setPreferredSize(new Dimension(800, 1000));
        carDetailsPanel.setBackground(Color.BLUE);
        */
        JPanel carMainPanel = new JPanel(new BorderLayout());

        JPanel carMainContainer = new JPanel(new GridBagLayout());
        carMainContainer.setBackground(Color.BLUE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Add car details panel (Left Top)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        carMainContainer.add(carDetailsPanel(), gbc);

        // Add technical specification panel (Right Top)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        carMainContainer.add(technicalSpecsPanel(), gbc);

        carMainPanel.add(carMainContainer, BorderLayout.CENTER);
        carMainPanel.add(sidePanel(), BorderLayout.EAST);
        carMainPanel.add(sidePanel(), BorderLayout.WEST);

        
        return carMainPanel;
    }

    private JPanel carDetailsPanel() {
        //sample
        JPanel carDetailsPanel = new JPanel(new BorderLayout());
        carDetailsPanel.setPreferredSize(new Dimension(600, 800));

        ImageIcon image = new ImageIcon("images/cars/Supra.jpg");
        JLabel carPicture = new JLabel(image);
        carDetailsPanel.add(carPicture, BorderLayout.CENTER);

        String brand = "Porsche";
        String model = "GT3 RS";
        int year = 2023, pricePerDay = 0;

        JLabel brandModelYearLabel = new JLabel(brand + " " + model + " " + year);
        brandModelYearLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));

        JLabel pricePerDayLabel = new JLabel("RM" + pricePerDay + " / day");
        pricePerDayLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JPanel brandModelYearPricePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        brandModelYearPricePanel.add(brandModelYearLabel);
        brandModelYearPricePanel.add(pricePerDayLabel);

        carDetailsPanel.add(brandModelYearPricePanel, BorderLayout.NORTH);

        return carDetailsPanel;
    }

    private JPanel technicalSpecsPanel() {
        //sample
        JPanel technicalSpecsPanel = new JPanel(new GridBagLayout());
        technicalSpecsPanel.setPreferredSize(new Dimension(600, 800));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        ImageIcon[] detailsIcons = new ImageIcon[6];
        ImageIcon transmissionIcon = new ImageIcon("images/vehiclepageicons/manual-transmission.png");
        ImageIcon fuelIcon = new ImageIcon("images/vehiclepageicons/gas-station.png");
        ImageIcon seatsIcon = new ImageIcon("images/vehiclepageicons/car-seat.png");
        ImageIcon carTypeIcon = new ImageIcon("images/vehiclepageicons/chassis.png");
        ImageIcon topSpeedIcon = new ImageIcon("images/vehiclepageicons/speedometer.png");
        ImageIcon capacityIcon = new ImageIcon("images/vehiclepageicons/engine.png");
        detailsIcons[0] = transmissionIcon;
        detailsIcons[1] = fuelIcon;
        detailsIcons[2] = seatsIcon;
        detailsIcons[3] = carTypeIcon;
        detailsIcons[4] = topSpeedIcon;
        detailsIcons[5] = capacityIcon;

        for (int i = 0; i < 3; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            technicalSpecsPanel.add(new JLabel(detailsIcons[i]), gbc);

            gbc.gridx = 1;
            technicalSpecsPanel.add(new JLabel(detailsIcons[i + 3]), gbc);
        }
        
        JButton rentButton = new JButton("RENT");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        technicalSpecsPanel.add(rentButton, gbc);

        return technicalSpecsPanel;
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

    // side panel
    private JPanel sidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.BLACK);
        sidePanel.setPreferredSize(new Dimension(200, 1000));

        return sidePanel;
    }
}