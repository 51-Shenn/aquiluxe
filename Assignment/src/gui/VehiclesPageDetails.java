package gui;

import javax.swing.*;
import javax.swing.border.Border;

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
        carMainPanel.setPreferredSize(new Dimension(1600,450));

        JPanel carMainContainer = new JPanel(new GridLayout(1,2,10,0));
        carMainContainer.setBackground(Color.BLUE);

        carMainContainer.add(carDetailsPanel());
        carMainContainer.add(technicalSpecsPanel());

        JPanel carFeaturesContainer = createCarFeaturesContainer();

        carMainPanel.add(carMainContainer, BorderLayout.CENTER);
        carMainPanel.add(sidePanel(), BorderLayout.EAST);
        carMainPanel.add(sidePanel(), BorderLayout.WEST);
        //carMainPanel.add(carFeaturesContainer,BorderLayout.SOUTH);
        
        return carMainPanel;
    }

    private JPanel carDetailsPanel() {
        JPanel carDetailsPanel = new JPanel(new BorderLayout());
        carDetailsPanel.setPreferredSize(new Dimension(600, 800));

        //sample
        ImageIcon image = new ImageIcon("images/cars/Supra.jpg");
        Image rImage = image.getImage().getScaledInstance(700,700,java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(rImage);

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
        brandModelYearPricePanel.setPreferredSize(new Dimension(500,75));
        brandModelYearPricePanel.add(brandModelYearLabel);
        brandModelYearPricePanel.add(pricePerDayLabel);

        carDetailsPanel.add(brandModelYearPricePanel, BorderLayout.NORTH);

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
        detailsLabelPanel.add(carCodesPanel());

        technicalSpecsPanel.add(detailsLabelPanel,BorderLayout.NORTH);

        //sample
        String transmission = "AUTO";
        String fuelType = "HYBRID";
        int seats = 2;
        String carType = "CONVERTIBLE";
        double topSpeed = 300;
        int capacity = 3000;
        double mpg = 12;
        int horsepower = 800;
        String color = "BLACK";

        // loop through every details of the car and show 
        ImageIcon[] detailsIcons = new ImageIcon[9];
        ImageIcon transmissionIcon = new ImageIcon("images/vehiclepageicons/manual-transmission.png");
        ImageIcon fuelIcon = new ImageIcon("images/vehiclepageicons/gas-station.png");
        ImageIcon seatsIcon = new ImageIcon("images/vehiclepageicons/car-seat.png");
        ImageIcon carTypeIcon = new ImageIcon("images/vehiclepageicons/chassis.png");
        ImageIcon topSpeedIcon = new ImageIcon("images/vehiclepageicons/speedometer.png");
        ImageIcon capacityIcon = new ImageIcon("images/vehiclepageicons/engine.png");
        ImageIcon mpgIcon = new ImageIcon("images/vehiclepageicons/fuel-gauge.png");
        ImageIcon horsepowerIcon = new ImageIcon("images/vehiclepageicons/horse.png");
        ImageIcon colorIcon = new ImageIcon("images/vehiclepageicons/circle.png");
        detailsIcons[0] = transmissionIcon;
        detailsIcons[1] = fuelIcon;
        detailsIcons[2] = seatsIcon;
        detailsIcons[3] = carTypeIcon;
        detailsIcons[4] = topSpeedIcon;
        detailsIcons[5] = capacityIcon;
        detailsIcons[6] = mpgIcon;
        detailsIcons[7] = horsepowerIcon;

        JPanel transmissionPanel = new JPanel(new BorderLayout());
        JLabel transmissionLabel = new JLabel(transmissionIcon);
        transmissionLabel.setVerticalTextPosition(JLabel.BOTTOM);
        transmissionLabel.setHorizontalTextPosition(JLabel.CENTER);
        transmissionLabel.setText(transmission);
        transmissionPanel.add(transmissionLabel, BorderLayout.CENTER);

        JPanel fuelPanel = new JPanel(new BorderLayout());
        JLabel fuelLabel = new JLabel(fuelIcon);
        fuelLabel.setVerticalTextPosition(JLabel.BOTTOM);
        fuelLabel.setHorizontalTextPosition(JLabel.CENTER);
        fuelLabel.setText(fuelType);
        fuelPanel.add(fuelLabel, BorderLayout.CENTER);

        JPanel seatsPanel = new JPanel(new BorderLayout());
        JLabel seatsLabel = new JLabel(seatsIcon);
        seatsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        seatsLabel.setHorizontalTextPosition(JLabel.CENTER);
        seatsLabel.setText(Integer.toString(seats));
        seatsPanel.add(seatsLabel, BorderLayout.CENTER);

        JPanel carTypePanel = new JPanel(new BorderLayout());
        JLabel carTypeLabel = new JLabel(carTypeIcon);
        carTypeLabel.setVerticalTextPosition(JLabel.BOTTOM);
        carTypeLabel.setHorizontalTextPosition(JLabel.CENTER);
        carTypeLabel.setText(carType);
        carTypePanel.add(carTypeLabel, BorderLayout.CENTER);

        JPanel topSpeedPanel = new JPanel(new BorderLayout());
        JLabel topSpeedLabel = new JLabel(topSpeedIcon);
        topSpeedLabel.setVerticalTextPosition(JLabel.BOTTOM);
        topSpeedLabel.setHorizontalTextPosition(JLabel.CENTER);
        topSpeedLabel.setText(Double.toString(topSpeed) + "km/h");
        topSpeedPanel.add(topSpeedLabel, BorderLayout.CENTER);

        JPanel capacityPanel = new JPanel(new BorderLayout());
        JLabel capacityLabel = new JLabel(capacityIcon);
        capacityLabel.setVerticalTextPosition(JLabel.BOTTOM);
        capacityLabel.setHorizontalTextPosition(JLabel.CENTER);
        capacityLabel.setText(Integer.toString(capacity));
        capacityPanel.add(capacityLabel, BorderLayout.CENTER);

        JPanel mpgPanel = new JPanel(new BorderLayout());
        JLabel mpgLabel = new JLabel(mpgIcon);
        mpgLabel.setVerticalTextPosition(JLabel.BOTTOM);
        mpgLabel.setHorizontalTextPosition(JLabel.CENTER);
        mpgLabel.setText(Double.toString(mpg));
        mpgPanel.add(mpgLabel, BorderLayout.CENTER);

        JPanel horsepowerPanel = new JPanel(new BorderLayout());
        JLabel horsepowerLabel = new JLabel(horsepowerIcon);
        horsepowerLabel.setVerticalTextPosition(JLabel.BOTTOM);
        horsepowerLabel.setHorizontalTextPosition(JLabel.CENTER);
        horsepowerLabel.setText(Integer.toString(horsepower));
        horsepowerPanel.add(horsepowerLabel, BorderLayout.CENTER);

        JPanel colorPanel = new JPanel(new BorderLayout());
        JLabel colorLabel = new JLabel(colorIcon);
        colorLabel.setText(color);
        colorLabel.setVerticalTextPosition(JLabel.BOTTOM);
        colorLabel.setHorizontalTextPosition(JLabel.CENTER);
        colorPanel.setBackground(Color.BLACK);
        colorPanel.add(colorLabel, BorderLayout.CENTER);

        JPanel[] detailsBox = {transmissionPanel,fuelPanel,seatsPanel,carTypePanel,topSpeedPanel,capacityPanel,mpgPanel,horsepowerPanel,colorPanel};

        JPanel specsContainer = new JPanel(new GridLayout(3, 3, 5, 5));

        for (int i = 0; i < 9; i++) {
            specsContainer.add(detailsBox[i]);
        }
        specsContainer.add(colorPanel);

        technicalSpecsPanel.add(specsContainer,BorderLayout.CENTER);
        
        //bottom panel that holds all the button
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setPreferredSize(new Dimension(1600,75));
        buttonsPanel.setBackground(Color.BLACK);

        //rent button on the right 
        JPanel rentPanel = new JPanel(null);
        rentPanel.setPreferredSize(new Dimension(250,100));
        JButton rentButton = new JButton("RENT");
        rentButton.setBounds(0,0,250,75);
        rentPanel.add(rentButton);
        buttonsPanel.add(rentPanel, BorderLayout.EAST);

        // show more on the left
        JPanel showMorePanel = new JPanel(null);
        showMorePanel.setPreferredSize(new Dimension(200,100));
        JButton showMoreButton = new JButton("Show More");
        showMoreButton.setBounds(0,25,200,50);
        showMorePanel.add(showMoreButton);
        buttonsPanel.add(showMorePanel, BorderLayout.WEST);

        technicalSpecsPanel.add(buttonsPanel,BorderLayout.SOUTH);

        return technicalSpecsPanel;
    }

    private JPanel carCodesPanel() {
        // additional not so important details for users like codes: ID,Numbers at the bottom bar
        int vehicleId = 1111;
        String vinNumber = "4Y1SL65848Z411439";
        String registrationNumber = "GOLD 1";

        JLabel vehilceIdLabel = new JLabel("Vehicle ID : " + vehicleId);
        vehilceIdLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(15f));

        JLabel vinNumberLabel = new JLabel("VIN Number : " + vinNumber);
        vinNumberLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(15f));

        JLabel registrationNumberLabel = new JLabel("Registration Number : " + registrationNumber);
        registrationNumberLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(15f));

        JPanel carCodesPanel = new JPanel(new GridLayout(3,1,5,5));
        carCodesPanel.setPreferredSize(new Dimension(500,75));
        carCodesPanel.add(vehilceIdLabel);
        carCodesPanel.add(vinNumberLabel);
        carCodesPanel.add(registrationNumberLabel);

        return carCodesPanel;
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
        Image rImage = image.getImage().getScaledInstance(400,400,java.awt.Image.SCALE_SMOOTH);
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
        otherCarsTitlePanel.setPreferredSize(new Dimension(800, 75));
        JLabel otherCarsLabel = new JLabel("Other Cars");
        otherCarsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(35f));
        JButton viewAllButton = new JButton("View All");
        viewAllButton.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(20f));
        viewAllButton.setPreferredSize(new Dimension(150,50));
        otherCarsTitlePanel.add(otherCarsLabel,BorderLayout.WEST);
        otherCarsTitlePanel.add(viewAllButton,BorderLayout.EAST);
        
        // contain cars
        JPanel carsContainer = new JPanel(new GridLayout(0,3,100,15));
        for (String car : cars){
            carsContainer.add(VehiclesPage.createCarCard(image, car, model, transmissions, fuelType, carType,
            seats, price, availability));
        }

        // container include cars and the labels and buttons
        JPanel emptyBottomPanel = new JPanel();
        emptyBottomPanel.setPreferredSize(new Dimension(800, 87));
        
        JPanel moreCarsContainer = new JPanel(new BorderLayout());
        moreCarsContainer.setPreferredSize(new Dimension(800, 400));
        moreCarsContainer.add(carsContainer, BorderLayout.CENTER);
        moreCarsContainer.add(otherCarsTitlePanel, BorderLayout.NORTH);
        moreCarsContainer.add(emptyBottomPanel, BorderLayout.SOUTH);

        JPanel emptyLeftPanel = new JPanel();
        emptyLeftPanel.setBackground(Color.BLACK);
        emptyLeftPanel.setPreferredSize(new Dimension(300, 1000));

        JPanel emptyRightPanel = new JPanel();
        emptyRightPanel.setBackground(Color.BLACK);
        emptyRightPanel.setPreferredSize(new Dimension(300, 1000));

        JPanel moreCarsPanel = new JPanel(new BorderLayout());
        moreCarsPanel.add(moreCarsContainer,BorderLayout.CENTER);
        moreCarsPanel.add(emptyRightPanel,BorderLayout.EAST);
        moreCarsPanel.add(emptyLeftPanel,BorderLayout.WEST);
        moreCarsPanel.setPreferredSize(new Dimension(1600,550));

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
        sidePanel.setPreferredSize(new Dimension(250, 1000));

        return sidePanel;
    }
}