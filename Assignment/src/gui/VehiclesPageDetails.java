package gui;

import datamodels.Car;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VehiclesPageDetails extends JPanel {

    private final JFrame frame;
    private final JPanel panel;
    private Car car;
    private JPanel carMainPanel;
    private JPanel carFeaturesContainer;
    private boolean isFeaturesVisible = false;

    public VehiclesPageDetails(JFrame frame, JPanel panel, Car car) {
        this.frame = frame;
        this.panel = panel;
        this.car = car;
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
        carMainPanel = new JPanel(new BorderLayout());
        carMainPanel.setPreferredSize(new Dimension(1600,450));

        JPanel carMainContainer = new JPanel(new GridLayout(1,2,5,0));
        carMainContainer.setBorder(BorderFactory.createLineBorder(new Color(238,238,238),5,true));
        carMainContainer.add(carDetailsPanel());
        carMainContainer.add(technicalSpecsPanel());

        carFeaturesContainer = createCarFeaturesContainer();

        carMainPanel.add(carMainContainer, BorderLayout.CENTER);
        carMainPanel.add(sidePanel(), BorderLayout.EAST);
        carMainPanel.add(sidePanel(), BorderLayout.WEST);
        //carMainPanel.add(carFeaturesContainer,BorderLayout.SOUTH);
        
        return carMainPanel;
    }

    private JPanel carDetailsPanel() {
        JPanel carDetailsPanel = new JPanel(new BorderLayout(0,5));
        carDetailsPanel.setPreferredSize(new Dimension(600, 800));

        //sample
        ImageIcon image = new ImageIcon(car.getImagePath());
        Image rImage = image.getImage().getScaledInstance(700,700,java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(rImage);

        // car picutre at the center
        JLabel carPicture = new JLabel(image);
        carDetailsPanel.add(carPicture, BorderLayout.CENTER);

        // Panel on top to hold the name and price of car
        JLabel brandModelYearLabel = new JLabel(car.getBrand() + " " + car.getModel() + " " + car.getYear());
        brandModelYearLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));

        JLabel pricePerDayLabel = new JLabel("RM" + car.getRentalPriceDay() + " / day");
        pricePerDayLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JPanel brandModelYearPricePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        brandModelYearPricePanel.setBackground(Color.WHITE);
        brandModelYearPricePanel.setPreferredSize(new Dimension(500,75));
        brandModelYearPricePanel.add(brandModelYearLabel);
        brandModelYearPricePanel.add(pricePerDayLabel);

        carDetailsPanel.add(brandModelYearPricePanel, BorderLayout.NORTH);

        return carDetailsPanel;
    }

    private JPanel technicalSpecsPanel() {
        ImageIcon downIcon = new ImageIcon("images/vehiclepageicons/down.png");
        ImageIcon upIcon = new ImageIcon("images/vehiclepageicons/up.png");
        //sample
        JPanel technicalSpecsPanel = new JPanel(new BorderLayout(5,5));
        technicalSpecsPanel.setPreferredSize(new Dimension(600, 800));

        // just a "Details" label
        JLabel detailsLabel = new JLabel("Details");
        detailsLabel.setBackground(Color.WHITE);
        detailsLabel.setOpaque(true);
        detailsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));

        JPanel detailsLabelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        detailsLabelPanel.add(detailsLabel);
        detailsLabelPanel.add(carCodesPanel());

        technicalSpecsPanel.add(detailsLabelPanel,BorderLayout.NORTH);

        //get pass through car
        String transmission = car.getTransmission();
        String fuelType = car.getFuelType();
        int seats = car.getSeatingCapacity();
        String carType = car.getCarType();
        int capacity = car.getCapacity();
        double mpg = car.getMpg();
        int horsepower = car.getHorsepower();
        String color = car.getColor();

        // loop through every details of the car and show 
        ImageIcon[] detailsIcons = new ImageIcon[8];
        ImageIcon transmissionIcon = new ImageIcon("images/vehiclepageicons/manual-transmission.png");
        ImageIcon fuelIcon = new ImageIcon("images/vehiclepageicons/gas-station.png");
        ImageIcon seatsIcon = new ImageIcon("images/vehiclepageicons/car-seat.png");
        ImageIcon carTypeIcon = new ImageIcon("images/vehiclepageicons/chassis.png");
        ImageIcon capacityIcon = new ImageIcon("images/vehiclepageicons/engine.png");
        ImageIcon mpgIcon = new ImageIcon("images/vehiclepageicons/fuel-gauge.png");
        ImageIcon horsepowerIcon = new ImageIcon("images/vehiclepageicons/horse.png");
        ImageIcon colorIcon = new ImageIcon("images/vehiclepageicons/circle.png");
        detailsIcons[0] = transmissionIcon;
        detailsIcons[1] = fuelIcon;
        detailsIcons[2] = seatsIcon;
        detailsIcons[3] = carTypeIcon;
        detailsIcons[4] = capacityIcon;
        detailsIcons[5] = mpgIcon;
        detailsIcons[6] = horsepowerIcon;

        JPanel transmissionPanel = new JPanel(new BorderLayout());
        JLabel transmissionLabel = new JLabel(transmissionIcon);
        transmissionLabel.setVerticalTextPosition(JLabel.BOTTOM);
        transmissionLabel.setHorizontalTextPosition(JLabel.CENTER);
        transmissionLabel.setText(transmission);
        transmissionLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        transmissionPanel.add(transmissionLabel, BorderLayout.CENTER);
        transmissionPanel.setBackground(Color.WHITE);

        JPanel fuelPanel = new JPanel(new BorderLayout());
        JLabel fuelLabel = new JLabel(fuelIcon);
        fuelLabel.setVerticalTextPosition(JLabel.BOTTOM);
        fuelLabel.setHorizontalTextPosition(JLabel.CENTER);
        fuelLabel.setText(fuelType);
        fuelLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        fuelPanel.add(fuelLabel, BorderLayout.CENTER);
        fuelPanel.setBackground(Color.WHITE);

        JPanel seatsPanel = new JPanel(new BorderLayout());
        JLabel seatsLabel = new JLabel(seatsIcon);
        seatsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        seatsLabel.setHorizontalTextPosition(JLabel.CENTER);
        seatsLabel.setText(Integer.toString(seats));
        seatsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        seatsPanel.add(seatsLabel, BorderLayout.CENTER);
        seatsPanel.setBackground(Color.WHITE);

        JPanel carTypePanel = new JPanel(new BorderLayout());
        JLabel carTypeLabel = new JLabel(carTypeIcon);
        carTypeLabel.setVerticalTextPosition(JLabel.BOTTOM);
        carTypeLabel.setHorizontalTextPosition(JLabel.CENTER);
        carTypeLabel.setText(carType);
        carTypeLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        carTypePanel.add(carTypeLabel, BorderLayout.CENTER);
        carTypePanel.setBackground(Color.WHITE);

        JPanel capacityPanel = new JPanel(new BorderLayout());
        JLabel capacityLabel = new JLabel(capacityIcon);
        capacityLabel.setVerticalTextPosition(JLabel.BOTTOM);
        capacityLabel.setHorizontalTextPosition(JLabel.CENTER);
        capacityLabel.setText(Integer.toString(capacity) + " cc");
        capacityLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        capacityPanel.add(capacityLabel, BorderLayout.CENTER);
        capacityPanel.setBackground(Color.WHITE);

        JPanel mpgPanel = new JPanel(new BorderLayout());
        JLabel mpgLabel = new JLabel(mpgIcon);
        mpgLabel.setVerticalTextPosition(JLabel.BOTTOM);
        mpgLabel.setHorizontalTextPosition(JLabel.CENTER);
        mpgLabel.setText(Double.toString(mpg) + " mpg");
        mpgLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        mpgPanel.add(mpgLabel, BorderLayout.CENTER);
        mpgPanel.setBackground(Color.WHITE);

        JPanel horsepowerPanel = new JPanel(new BorderLayout());
        JLabel horsepowerLabel = new JLabel(horsepowerIcon);
        horsepowerLabel.setVerticalTextPosition(JLabel.BOTTOM);
        horsepowerLabel.setHorizontalTextPosition(JLabel.CENTER);
        horsepowerLabel.setText(Integer.toString(horsepower) + " hp");
        horsepowerLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        horsepowerPanel.add(horsepowerLabel, BorderLayout.CENTER);
        horsepowerPanel.setBackground(Color.WHITE);

        JPanel colorPanel = new JPanel(new BorderLayout());
        JLabel colorIconLabel = new JLabel(colorIcon);
        JPanel colorIconPanel = new JPanel(new BorderLayout());
        colorIconPanel.setBackground(Color.BLACK);
        colorIconPanel.add(colorIconLabel, BorderLayout.CENTER);
        JLabel colorLabel = new JLabel(color);
        colorLabel.setPreferredSize(new Dimension(0,25));
        colorLabel.setHorizontalAlignment(JLabel.CENTER);
        colorLabel.setVerticalAlignment(JLabel.NORTH);
        colorLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        colorPanel.add(colorIconPanel, BorderLayout.CENTER);
        colorPanel.add(colorLabel, BorderLayout.SOUTH);
        colorPanel.setBackground(Color.WHITE);

        JPanel[] detailsBox = {transmissionPanel,fuelPanel,seatsPanel,carTypePanel,capacityPanel,mpgPanel,horsepowerPanel,colorPanel};

        JPanel specsContainer = new JPanel(new GridLayout(3, 3, 5, 5));

        for (int i = 0; i < 8; i++) {
            specsContainer.add(detailsBox[i]);
        }
        specsContainer.add(colorPanel);

        technicalSpecsPanel.add(specsContainer,BorderLayout.CENTER);
        
        //bottom panel that holds all the button
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setPreferredSize(new Dimension(1600,75));
        //buttonsPanel.setBackground(Color.BLACK);

        //rent button on the right 
        JPanel rentPanel = new JPanel(null);
        rentPanel.setPreferredSize(new Dimension(250,100));
        RoundedButton rentButton = new RoundedButton(10,Color.BLUE);
        rentButton.setText("RENT");
        rentButton.setBounds(0,0,250,75);
        rentButton.setBackground(Color.BLUE);
        rentButton.setForeground(Color.WHITE);
        rentButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        rentButton.setFocusable(false);
        rentButton.setContentAreaFilled(false);
        rentButton.setBorderPainted(false);
        rentButton.setOpaque(true);
        rentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                rentButton.setBackground(Color.BLUE.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                rentButton.setBackground(Color.BLUE);
            }
            
            @Override
            public void mousePressed(MouseEvent evt) {
                rentButton.setBackground(Color.CYAN);
            }
            
            @Override
            public void mouseReleased(MouseEvent evt) {
                rentButton.setBackground(Color.BLUE);
            }
        });
        rentPanel.add(rentButton);
        buttonsPanel.add(rentPanel, BorderLayout.EAST);

        // show more on the left
        JPanel showMorePanel = new JPanel(null);
        showMorePanel.setPreferredSize(new Dimension(200,100));
        JButton showMoreButton = new JButton("Show More");
        showMoreButton.setBounds(0,35,175,40);
        showMoreButton.setBackground(Color.WHITE);
        showMoreButton.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(20f));
        showMoreButton.setIcon(downIcon);
        showMoreButton.setHorizontalAlignment(JButton.LEFT);
        showMoreButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Show More") || e.getActionCommand().equals("Hide")) {
                if (isFeaturesVisible) {
                    carMainPanel.remove(carFeaturesContainer);
                    carMainPanel.setPreferredSize(new Dimension(1600,450));
                    showMoreButton.setText("Show More");
                    showMoreButton.setIcon(downIcon);
                    isFeaturesVisible = false;
                } else {
                    carMainPanel.add(carFeaturesContainer, BorderLayout.SOUTH);
                    carMainPanel.setPreferredSize(new Dimension(1600,900));
                    showMoreButton.setText("Hide");
                    showMoreButton.setIcon(upIcon);
                    isFeaturesVisible = true;
                }
                carMainPanel.revalidate();
                carMainPanel.repaint();
            }
        });
        showMorePanel.add(showMoreButton);
        buttonsPanel.add(showMorePanel, BorderLayout.WEST);

        technicalSpecsPanel.add(buttonsPanel,BorderLayout.SOUTH);

        return technicalSpecsPanel;
    }

    private JPanel carCodesPanel() {
        // additional not so important details for users like codes: ID,Numbers at the bottom bar

        JLabel vehilceIdLabel = new JLabel("Vehicle ID : " + car.getVehicleId());
        vehilceIdLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(12.5f));

        JLabel vinNumberLabel = new JLabel("VIN Number : " + car.getVinNumber());
        vinNumberLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(12.5f));

        JLabel registrationNumberLabel = new JLabel("Registration Number : " + car.getRegistrationNumber());
        registrationNumberLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(12.5f));

        JPanel carCodesPanel = new JPanel(new GridLayout(3,1,5,5));
        carCodesPanel.setBackground(Color.WHITE);
        carCodesPanel.setPreferredSize(new Dimension(500,75));
        carCodesPanel.add(vehilceIdLabel);
        carCodesPanel.add(vinNumberLabel);
        carCodesPanel.add(registrationNumberLabel);

        return carCodesPanel;
    }


    // Center panel
    private JPanel moreCarsPanel() {
        
        ImageIcon rightArrowIcon = new ImageIcon("images/vehiclepageicons/right-arrow.png");
        Car[] carss = new Car[4];
        carss[0] = new Car(1,"images/cars/PorscheGT3.jpg","PORSCHE","GT3 RS WEISSACH",2024,3996,518,"black",16.0,"WP0ZZZ99ZTS392124","GT3",699,"AUTO","GAS","COUPE",2,true,"weissach package :)");
        carss[1] = new Car(1,"images/cars/Supra.jpg","TOYOTA","SUPRA MK5",2024,2998,382,"red",16.0,"WP0ZZZ99ZTS392124","VMK 5",499,"AUTO","GAS","COUPE",2,true,"it's that a supra?");
        carss[2] = new Car(1,"images/cars/F8.jpg","FERRARI","F8",2023,3996,518,"black",16.0,"WP0ZZZ99ZTS392124","GT3",699,"AUTO","GAS","COUPE",2,true,"car");
        carss[3] = new Car(1,"images/cars/SVJ.jpg","LAMBORGHINI","AVENTADOR SVJ",2024,3996,518,"black",16.0,"WP0ZZZ99ZTS392124","GT3",699,"AUTO","GAS","COUPE",2,true,"v12");

        // title for Other Cars and a button to view more cars
        JPanel otherCarsTitlePanel = new JPanel(new BorderLayout());
        otherCarsTitlePanel.setPreferredSize(new Dimension(800, 75));
        otherCarsTitlePanel.setBackground(Color.WHITE);
        JLabel otherCarsLabel = new JLabel("Other Cars");
        otherCarsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(35f));
        JButton viewAllButton = new JButton("View All");
        viewAllButton.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(25f));
        viewAllButton.setPreferredSize(new Dimension(165,40));
        viewAllButton.setBackground(Color.WHITE);
        viewAllButton.setIcon(rightArrowIcon);
        viewAllButton.setHorizontalTextPosition(JButton.LEFT);
        viewAllButton.setVerticalAlignment(JButton.BOTTOM);
        viewAllButton.addActionListener(e -> {
            if (e.getActionCommand().equals("View All")){
                panel.removeAll();
                panel.add(new VehiclesPage(frame, panel), BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();
            }
        });

        JPanel viewAllButtonPanel = new JPanel(new BorderLayout());
        viewAllButtonPanel.setBackground(Color.WHITE);
        viewAllButtonPanel.add(viewAllButton,BorderLayout.SOUTH);
        otherCarsTitlePanel.add(otherCarsLabel,BorderLayout.WEST);
        otherCarsTitlePanel.add(viewAllButtonPanel,BorderLayout.EAST);
        
        // contain cars
        JPanel carsContainer = new JPanel(new GridLayout(0,3,50,30));
        carsContainer.setBackground(Color.WHITE);
        int count = 0;
        for (Car car : carss){
            if (this.car.getBrand() == car.getBrand() && this.car.getModel() == car.getModel()){
                continue;
            }
            else if (count == 3){
                break;
            }
            ImageIcon image = null;
            try {
                image = new ImageIcon(car.getImagePath());

                // Check if any image failed to load
                if (image.getIconWidth() == -1) {
                    throw new Exception("One or more images failed to load.");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error loading images: " + e.getMessage());
            }
            Image rImage = image.getImage().getScaledInstance(400,400,java.awt.Image.SCALE_SMOOTH);
            image = new ImageIcon(rImage);

            String availability = car.isAvailability() ? "AVAILABLE" : "UNAVAILABLE";
            String rentPrice = "RM" + car.getRentalPriceDay() + "/per day";

            carsContainer.add(VehiclesPage.createCarCard(car, image, car.getBrand(), car.getModel(), car.getTransmission(), car.getFuelType(), car.getCarType(),
            car.getSeatingCapacity(), rentPrice, availability, frame, panel));
            count++;
        }

        // container include cars and the labels and buttons
        JPanel emptyBottomPanel = new JPanel();
        emptyBottomPanel.setPreferredSize(new Dimension(800, 75));
        emptyBottomPanel.setBackground(Color.WHITE);
        
        JPanel moreCarsContainer = new JPanel(new BorderLayout());
        moreCarsContainer.setPreferredSize(new Dimension(800, 400));
        moreCarsContainer.add(carsContainer, BorderLayout.CENTER);
        moreCarsContainer.add(otherCarsTitlePanel, BorderLayout.NORTH);
        moreCarsContainer.add(emptyBottomPanel, BorderLayout.SOUTH);

        JPanel emptyLeftPanel = new JPanel();
        emptyLeftPanel.setBackground(Color.WHITE);
        emptyLeftPanel.setPreferredSize(new Dimension(300, 1000));

        JPanel emptyRightPanel = new JPanel();
        emptyRightPanel.setBackground(Color.WHITE);
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
        carMainFeaturesContainer.setPreferredSize(new Dimension(1600,450));

        JPanel featuresContainer = new JPanel(new BorderLayout());
        featuresContainer.setPreferredSize(new Dimension(1600,300));
        featuresContainer.setBackground(Color.WHITE);

        JLabel featuresLabel = new JLabel("Features");
        featuresLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));

        JLabel featuresContent = new JLabel(this.car.getFeatures());
        featuresContent.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(20f));

        featuresContainer.add(featuresLabel,BorderLayout.NORTH);
        featuresContainer.add(featuresContent,BorderLayout.CENTER);

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
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setPreferredSize(new Dimension(250, 1000));

        return sidePanel;
    }

    private class RoundedButton extends JButton {
        private Color backgroundColor;
        private final int cornerRadius;

        public RoundedButton(int radius, Color bgColor) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        public void setBackground(Color bgColor) {
            this.backgroundColor = bgColor;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // Enable anti-aliasing for smooth rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int arcSize = cornerRadius * 2;

            // Make panel transparent
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2d.setColor(backgroundColor != null ? backgroundColor : getBackground());

            FontMetrics fm = g2d.getFontMetrics();
            int textX = (width - fm.stringWidth(getText())) / 2;
            int textY = (height + fm.getAscent()) / 2 - 2;

            // Fill rounded rectangle
            g2d.fillRoundRect(0, 0, width - 1, height - 1, arcSize, arcSize);
            g2d.setColor(getForeground());
            g2d.drawString(getText(), textX, textY);

            g2d.dispose();
        }
    }

}