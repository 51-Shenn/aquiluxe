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
        carMainPanel.setPreferredSize(new Dimension(1600,1100));

        JPanel carMainContainer = new JPanel(new GridBagLayout());
        carMainContainer.setBackground(Color.BLUE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0); // Padding

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

        JPanel carFeaturesContainer = createCarFeaturesContainer();

        carMainPanel.add(carMainContainer, BorderLayout.CENTER);
        carMainPanel.add(sidePanel(), BorderLayout.EAST);
        carMainPanel.add(sidePanel(), BorderLayout.WEST);
        carMainPanel.add(carFeaturesContainer,BorderLayout.SOUTH);
        
        return carMainPanel;
    }

    private JPanel carDetailsPanel() {
        JPanel carDetailsPanel = new JPanel(new BorderLayout());
        carDetailsPanel.setPreferredSize(new Dimension(600, 800));

        //sample
        ImageIcon image = new ImageIcon("images/cars/Supra.jpg");

        String brand = "Porsche";
        String model = "GT3 RS";
        int year = 2023, pricePerDay = 0;

        // car picutre at the center
        JLabel carPicture = new JLabel(image);
        carDetailsPanel.add(carPicture, BorderLayout.CENTER);

        // Panel on top to hold the name and price of car
        JLabel brandModelYearLabel = new JLabel(brand + " " + model + " " + year);
        brandModelYearLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));

        JLabel pricePerDayLabel = new JLabel("RM" + pricePerDay + " / day");
        pricePerDayLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JPanel brandModelYearPricePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        brandModelYearPricePanel.add(brandModelYearLabel);
        brandModelYearPricePanel.add(pricePerDayLabel);

        carDetailsPanel.add(brandModelYearPricePanel, BorderLayout.NORTH);

        // additional not so important details for users like codes: ID,Numbers at the bottom bar
        int vehicleId = 1111;
        String vinNumber = "4Y1SL65848Z411439";
        String registrationNumber = "GOLD 1";

        JLabel vehilceIdLabel = new JLabel("Vehicle ID : " + vehicleId);
        vehilceIdLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(20f));

        JLabel vinNumberLabel = new JLabel("VIN Number : " + vinNumber);
        vinNumberLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(20f));

        JLabel registrationNumberLabel = new JLabel("Registration Number : " + registrationNumber);
        registrationNumberLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(20f));

        JPanel carCodesPanel = new JPanel(new GridLayout(3,1,5,5));
        carCodesPanel.add(vehilceIdLabel);
        carCodesPanel.add(vinNumberLabel);
        carCodesPanel.add(registrationNumberLabel);

        carDetailsPanel.add(carCodesPanel, BorderLayout.SOUTH);

        return carDetailsPanel;
    }

    private JPanel technicalSpecsPanel() {
        //sample
        JPanel technicalSpecsPanel = new JPanel(new BorderLayout());
        technicalSpecsPanel.setPreferredSize(new Dimension(600, 800));

        // just a "Details" label
        JLabel detailsLabel = new JLabel("Details");
        detailsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));

        JPanel detailsLabelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        detailsLabelPanel.add(detailsLabel);

        technicalSpecsPanel.add(detailsLabelPanel,BorderLayout.NORTH);

        // loop through every details of the car and show 
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

        JPanel specsContainer = new JPanel(new GridLayout(3, 3, 5, 5));

        for (int i = 0; i < 6; i++) {
            specsContainer.add(new JLabel(detailsIcons[i]));
        }

        technicalSpecsPanel.add(specsContainer,BorderLayout.CENTER);
        
        //bottom panel that holds all the button
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setPreferredSize(new Dimension(1600,100));
        buttonsPanel.setBackground(Color.BLACK);

        //rent button on the right 
        JPanel rentPanel = new JPanel(null);
        rentPanel.setPreferredSize(new Dimension(250,100));
        JButton rentButton = new JButton("RENT");
        rentButton.setBounds(0,25,250,75);
        rentPanel.add(rentButton);
        buttonsPanel.add(rentPanel, BorderLayout.EAST);

        // show more on the left
        JPanel showMorePanel = new JPanel(null);
        showMorePanel.setPreferredSize(new Dimension(200,100));
        JButton showMoreButton = new JButton("Show More");
        showMoreButton.setBounds(0,50,200,50);
        showMorePanel.add(showMoreButton);
        buttonsPanel.add(showMorePanel, BorderLayout.WEST);

        technicalSpecsPanel.add(buttonsPanel,BorderLayout.SOUTH);

        return technicalSpecsPanel;
    }


    // Center panel
    private JPanel moreCarsPanel() {
        //sample
        ImageIcon image = null;
        try {
            image = new ImageIcon("images/cars/Supra.jpg");

            // Check if any image failed to load
            if (image.getIconWidth() == -1) {
                throw new Exception("One or more images failed to load.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading images: " + e.getMessage());
        }
        Image rImage = image.getImage().getScaledInstance(500,500,java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(rImage);
        String[] cars ={"PORSCHE","TOYOTA","NISSAN"};
        String model = "GT3 RS WEISSACH";
        String transmissions = "AUTO";
        String fuelType = "HYBRID";
        String carType = "CONVERTIBLE";
        int seats = 2;
        String price = "RM699 per day";
        String availability = "AVAILABLE";

        // title for Other Cars and a button to view more cars
        JPanel otherCarsTitlePanel = new JPanel(new BorderLayout());
        otherCarsTitlePanel.setPreferredSize(new Dimension(800, 100));
        JLabel otherCarsLabel = new JLabel("Other Cars");
        otherCarsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(35f));
        JButton viewAllButton = new JButton("View All");
        viewAllButton.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(20f));
        viewAllButton.setPreferredSize(new Dimension(150,50));
        otherCarsTitlePanel.add(otherCarsLabel,BorderLayout.WEST);
        otherCarsTitlePanel.add(viewAllButton,BorderLayout.EAST);

        JPanel otherCarsBottomPanel = new JPanel();
        otherCarsBottomPanel.setPreferredSize(new Dimension(800, 100));
        
        // contain cars
        JPanel carsContainer = new JPanel(new GridLayout(0,3,20,15));
        carsContainer.setPreferredSize(new Dimension(800, 500));
        for (String car : cars){
            carsContainer.add(VehiclesPage.createCarCard(image, car, model, transmissions, fuelType, carType,
            seats, price, availability));
        }

        // container include cars and the labels and buttons
        JPanel moreCarsContainer = new JPanel(new BorderLayout());
        moreCarsContainer.setPreferredSize(new Dimension(800, 600));
        moreCarsContainer.add(carsContainer, BorderLayout.CENTER);
        moreCarsContainer.add(otherCarsTitlePanel, BorderLayout.NORTH);
        moreCarsContainer.add(otherCarsBottomPanel, BorderLayout.SOUTH);

        JPanel moreCarsPanel = new JPanel(new BorderLayout());
        moreCarsPanel.add(moreCarsContainer,BorderLayout.CENTER);
        moreCarsPanel.add(sidePanel(),BorderLayout.EAST);
        moreCarsPanel.add(sidePanel(),BorderLayout.WEST);
        moreCarsPanel.setPreferredSize(new Dimension(1600,700));

        return moreCarsPanel;
    }

    // create features when press show more then remove when unshow
    private JPanel createCarFeaturesContainer(){
        JPanel carMainFeaturesContainer = new JPanel(new BorderLayout());
        carMainFeaturesContainer.setPreferredSize(new Dimension(1600,300));

        JPanel featuresContainer = new JPanel(new BorderLayout());
        featuresContainer.setPreferredSize(new Dimension(1600,300));

        JLabel featuresLabel = new JLabel("Features");
        featuresLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));

        featuresContainer.add(featuresLabel,BorderLayout.NORTH);

        carMainFeaturesContainer.add(featuresContainer,BorderLayout.CENTER);

        carMainFeaturesContainer.add(sidePanel(),BorderLayout.EAST);
        carMainFeaturesContainer.add(sidePanel(),BorderLayout.WEST);

        return carMainFeaturesContainer;
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