package gui;

import datamodels.Vehicle;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

import controllers.VehicleController;

public class VehiclesPageDetails extends JPanel {

    private final JFrame frame;
    private final JPanel panel;
    private Vehicle vehicle;
    private JPanel carMainPanel;
    private JPanel carFeaturesContainer;
    private boolean isFeaturesVisible = false;
    private JPanel emptyBottomPanel = new JPanel();

    public static JPanel rentalPanel;

    public VehiclesPageDetails(JFrame frame, JPanel panel, Vehicle vehicle) {
        this.frame = frame;
        this.panel = panel;
        this.vehicle = vehicle;
        this.setBackground(Theme.getBackground());
        this.setLayout(new BorderLayout());

        this.add(MainContainer(), BorderLayout.CENTER);

        // for spacing below the picture
        emptyBottomPanel.setPreferredSize(new Dimension(1600, 150));
        emptyBottomPanel.setBackground(Theme.getBackground());
    }

    private JScrollPane MainContainer() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Theme.getBackground());
        mainPanel.add(bottomBar(), BorderLayout.SOUTH);
        mainPanel.add(moreCarsPanel(), BorderLayout.CENTER);
        mainPanel.add(carMainPanel(), BorderLayout.NORTH);

        JScrollPane carMainContainer = new JScrollPane(mainPanel);
        carMainContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        carMainContainer.getVerticalScrollBar().setUnitIncrement(30);

        return carMainContainer;
    }

    // Top panel
    private JPanel carMainPanel() {
        JPanel emptyTopPanel = new JPanel();
        emptyTopPanel.setPreferredSize(new Dimension(1600, 100));
        emptyTopPanel.setBackground(Theme.getBackground());

        carMainPanel = new JPanel(new BorderLayout());
        carMainPanel.setPreferredSize(new Dimension(1600, 800));
        carMainPanel.setBackground(Theme.getBackground());

        JPanel carMainContainer = new JPanel(new GridLayout(1, 2, 60, 0));
        carMainContainer.add(carDetailsPanel());
        carMainContainer.setBackground(Theme.getBackground());
        carMainContainer.add(technicalSpecsPanel());

        carFeaturesContainer = createCarFeaturesContainer();

        carMainPanel.add(emptyTopPanel, BorderLayout.NORTH);
        carMainPanel.add(emptyBottomPanel, BorderLayout.SOUTH);

        carMainPanel.add(carMainContainer, BorderLayout.CENTER);
        carMainPanel.add(sidePanel(), BorderLayout.EAST);
        carMainPanel.add(sidePanel(), BorderLayout.WEST);
        // carMainPanel.add(carFeaturesContainer,BorderLayout.SOUTH);

        return carMainPanel;
    }

    private JPanel carDetailsPanel() {
        JPanel carDetailsPanel = new JPanel(new BorderLayout(0, 30));
        carDetailsPanel.setPreferredSize(new Dimension(600, 800));
        carDetailsPanel.setBackground(Theme.getBackground());

        // sample
        ImageIcon image = new ImageIcon(vehicle.getImagePath());
        Image rImage = image.getImage().getScaledInstance(700, 700, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(rImage);

        // vehicle picutre at the center
        JLabel carPicture = new JLabel(image);
        carDetailsPanel.add(carPicture, BorderLayout.CENTER);

        // Panel on top to hold the name and price of vehicle
        JLabel brandModelYearLabel = new JLabel(
                vehicle.getBrand() + " " + vehicle.getModel() + " " + vehicle.getYear());
        brandModelYearLabel.setForeground(Theme.getForeground());
        brandModelYearLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(35f));

        JLabel pricePerDayLabel = new JLabel("RM" + vehicle.getRentalPriceDay() + " / day");
        pricePerDayLabel.setForeground(Theme.getForeground());
        pricePerDayLabel.setFont(CustomFonts.INSTRUMENT_SANS_SEMI_BOLD.deriveFont(25f));

        JPanel brandModelYearPricePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        brandModelYearPricePanel.setBackground(Theme.getBackground());
        brandModelYearPricePanel.setPreferredSize(new Dimension(500, 75));
        brandModelYearPricePanel.add(brandModelYearLabel);
        brandModelYearPricePanel.add(pricePerDayLabel);

        carDetailsPanel.add(brandModelYearPricePanel, BorderLayout.NORTH);

        return carDetailsPanel;
    }

    private JPanel technicalSpecsPanel() {
        ImageIcon downIcon = IconLoader.getDownIcon();
        ImageIcon upIcon = IconLoader.getUpIcon();
        // sample
        JPanel technicalSpecsPanel = new JPanel(new BorderLayout(5, 5));
        technicalSpecsPanel.setPreferredSize(new Dimension(600, 800));
        technicalSpecsPanel.setBackground(Theme.getBackground());

        // just a "Details" label
        JLabel detailsLabel = new JLabel("Details");
        detailsLabel.setForeground(Theme.getForeground());
        detailsLabel.setBackground(Theme.getBackground());
        detailsLabel.setOpaque(true);
        detailsLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(30f));

        JPanel detailsLabelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        detailsLabelPanel.setBackground(Theme.getBackground());
        detailsLabelPanel.add(detailsLabel);
        detailsLabelPanel.add(carCodesPanel());

        technicalSpecsPanel.add(detailsLabelPanel, BorderLayout.NORTH);

        // get pass through vehicle
        String transmission = vehicle.getTransmission();
        String fuelType = vehicle.getFuelType();
        int seats = vehicle.getSeatingCapacity();
        String carType = vehicle.getVehicleType();
        int capacity = vehicle.getCapacity();
        double mpg = vehicle.getMpg();
        int horsepower = vehicle.getHorsepower();
        String color = vehicle.getColor();

        // loop through every details of the vehicle and show
        ImageIcon transmissionIcon = IconLoader.getTransmissionIcon();
        ImageIcon fuelIcon = IconLoader.getGasIcon();
        ImageIcon seatsIcon = IconLoader.getSeatIcon();
        ImageIcon carTypeIcon = IconLoader.getChassisIcon();
        ImageIcon capacityIcon = IconLoader.getEngineIcon();
        ImageIcon mpgIcon = IconLoader.getFuelGaugeIcon();
        ImageIcon horsepowerIcon = IconLoader.getHorseIcon();
        ImageIcon colorIcon = new ImageIcon("images/vehiclepageicons/circle.png");

        JPanel transmissionPanel = new JPanel(new BorderLayout());
        transmissionPanel.setBackground(Theme.getBackground());
        JLabel transmissionLabel = new JLabel(transmissionIcon);
        transmissionLabel.setForeground(Theme.getForeground());
        transmissionLabel.setVerticalTextPosition(JLabel.BOTTOM);
        transmissionLabel.setHorizontalTextPosition(JLabel.CENTER);
        transmissionLabel.setText(transmission);
        transmissionLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(15f));
        transmissionPanel.add(transmissionLabel, BorderLayout.CENTER);
        transmissionPanel.setBackground(Theme.getBackground());

        JPanel fuelPanel = new JPanel(new BorderLayout());
        fuelPanel.setBackground(Theme.getBackground());
        JLabel fuelLabel = new JLabel(fuelIcon);
        fuelLabel.setForeground(Theme.getForeground());
        fuelLabel.setVerticalTextPosition(JLabel.BOTTOM);
        fuelLabel.setHorizontalTextPosition(JLabel.CENTER);
        fuelLabel.setText(fuelType);
        fuelLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(15f));
        fuelPanel.add(fuelLabel, BorderLayout.CENTER);
        fuelPanel.setBackground(Theme.getBackground());

        JPanel seatsPanel = new JPanel(new BorderLayout());
        seatsPanel.setBackground(Theme.getBackground());
        JLabel seatsLabel = new JLabel(seatsIcon);
        seatsLabel.setForeground(Theme.getForeground());
        seatsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        seatsLabel.setHorizontalTextPosition(JLabel.CENTER);
        seatsLabel.setText(Integer.toString(seats));
        seatsLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(15f));
        seatsPanel.add(seatsLabel, BorderLayout.CENTER);
        seatsPanel.setBackground(Theme.getBackground());

        JPanel carTypePanel = new JPanel(new BorderLayout());
        carTypePanel.setBackground(Theme.getBackground());
        JLabel carTypeLabel = new JLabel(carTypeIcon);
        carTypeLabel.setForeground(Theme.getForeground());
        carTypeLabel.setVerticalTextPosition(JLabel.BOTTOM);
        carTypeLabel.setHorizontalTextPosition(JLabel.CENTER);
        carTypeLabel.setText(carType);
        carTypeLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(15f));
        carTypePanel.add(carTypeLabel, BorderLayout.CENTER);
        carTypePanel.setBackground(Theme.getBackground());

        JPanel capacityPanel = new JPanel(new BorderLayout());
        capacityPanel.setBackground(Theme.getBackground());
        JLabel capacityLabel = new JLabel(capacityIcon);
        capacityLabel.setVerticalTextPosition(JLabel.BOTTOM);
        capacityLabel.setForeground(Theme.getForeground());
        capacityLabel.setHorizontalTextPosition(JLabel.CENTER);
        capacityLabel.setText(Integer.toString(capacity) + " cc");
        capacityLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(15f));
        capacityPanel.add(capacityLabel, BorderLayout.CENTER);
        capacityPanel.setBackground(Theme.getBackground());

        JPanel mpgPanel = new JPanel(new BorderLayout());
        mpgPanel.setBackground(Theme.getBackground());
        JLabel mpgLabel = new JLabel(mpgIcon);
        mpgLabel.setForeground(Theme.getForeground());
        mpgLabel.setVerticalTextPosition(JLabel.BOTTOM);
        mpgLabel.setHorizontalTextPosition(JLabel.CENTER);
        mpgLabel.setText(Double.toString(mpg) + " mpg");
        mpgLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(15f));
        mpgPanel.add(mpgLabel, BorderLayout.CENTER);
        mpgPanel.setBackground(Theme.getBackground());

        JPanel horsepowerPanel = new JPanel(new BorderLayout());
        horsepowerPanel.setBackground(Theme.getBackground());
        JLabel horsepowerLabel = new JLabel(horsepowerIcon);
        horsepowerLabel.setForeground(Theme.getForeground());
        horsepowerLabel.setVerticalTextPosition(JLabel.BOTTOM);
        horsepowerLabel.setHorizontalTextPosition(JLabel.CENTER);
        horsepowerLabel.setText(Integer.toString(horsepower) + " hp");
        horsepowerLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(15f));
        horsepowerPanel.add(horsepowerLabel, BorderLayout.CENTER);
        horsepowerPanel.setBackground(Theme.getBackground());

        JPanel colorPanel = new JPanel(new BorderLayout());
        colorPanel.setBackground(Theme.getBackground());
        JLabel colorIconLabel = new JLabel(colorIcon);
        JPanel colorIconPanel = new JPanel(new BorderLayout());
        colorIconPanel.setBackground(Color.decode(color));
        colorIconPanel.add(colorIconLabel, BorderLayout.CENTER);
        JLabel colorLabel = new JLabel(VehicleController.processClosestColorName(Color.decode(color)));
        colorLabel.setBackground(Theme.getBackground());
        colorLabel.setForeground(Theme.getForeground());
        colorLabel.setPreferredSize(new Dimension(0, 25));
        colorLabel.setHorizontalAlignment(JLabel.CENTER);
        colorLabel.setVerticalAlignment(JLabel.NORTH);
        colorLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(15f));
        colorPanel.add(colorIconPanel, BorderLayout.CENTER);
        colorPanel.add(colorLabel, BorderLayout.SOUTH);

        JPanel[] detailsBox = { transmissionPanel, fuelPanel, seatsPanel, carTypePanel, capacityPanel, mpgPanel,
                horsepowerPanel, colorPanel };

        JPanel specsContainer = new JPanel(new GridLayout(3, 3, 5, 5));
        specsContainer.setBackground(Theme.getBackground());

        for (int i = 0; i < 8; i++) {
            specsContainer.add(detailsBox[i]);
        }
        specsContainer.add(colorPanel);

        technicalSpecsPanel.add(specsContainer, BorderLayout.CENTER);

        // bottom panel that holds all the button
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setPreferredSize(new Dimension(1600, 75));
        buttonsPanel.setBackground(Theme.getBackground());

        // rent button on the right
        JPanel rentPanel = new JPanel(null);
        rentPanel.setBackground(Theme.getBackground());
        rentPanel.setPreferredSize(new Dimension(250, 100));
        RoundedButton rentButton = new RoundedButton(10, Theme.getSpecial());
        rentButton.setText("RENT");
        rentButton.setBounds(0, 0, 200, 70);
        rentButton.setBackground(Theme.getSpecial());
        rentButton.setForeground(Theme.getSpecialForeground());
        rentButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        rentButton.setFocusable(false);
        rentButton.setContentAreaFilled(false);
        rentButton.setBorderPainted(false);
        rentButton.setOpaque(true);
        rentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                rentButton.setBackground(Theme.getHoverSpecial());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                rentButton.setBackground(Theme.getSpecial());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                rentButton.setBackground(Theme.getPressedSpecial());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                rentButton.setBackground(Theme.getSpecial());
                rentalPanel = new RentalPage(frame, panel, vehicle);
                GUIComponents.cardPanel.add(rentalPanel, "RentalPage");
                GUIComponents.cardLayout.show(GUIComponents.cardPanel, "RentalPage");
            }
        });
        rentPanel.add(rentButton);
        buttonsPanel.add(rentPanel, BorderLayout.EAST);

        // show more on the left
        JPanel showMorePanel = new JPanel(null);
        showMorePanel.setPreferredSize(new Dimension(200, 100));
        showMorePanel.setBackground(Theme.getBackground());
        JButton showMoreButton = new JButton("Show More");
        showMoreButton.setHorizontalTextPosition(SwingConstants.LEFT);
        showMoreButton.setBounds(0, 35, 175, 40);
        showMoreButton.setOpaque(true);
        showMoreButton.setBorderPainted(false);
        showMoreButton.setFocusable(false);
        showMoreButton.setBackground(Theme.getBackground());
        showMoreButton.setForeground(Theme.getForeground());
        showMoreButton.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(20f));
        showMoreButton.setIcon(downIcon);
        showMoreButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Show More") || e.getActionCommand().equals("Hide")) {
                if (isFeaturesVisible) {
                    carMainPanel.remove(carFeaturesContainer);
                    carMainPanel.add(emptyBottomPanel, BorderLayout.SOUTH);
                    carMainPanel.setPreferredSize(new Dimension(1600, 800));
                    showMoreButton.setText("Show More");
                    showMoreButton.setIcon(downIcon);
                    isFeaturesVisible = false;
                } else {
                    carMainPanel.remove(emptyBottomPanel);
                    carMainPanel.add(carFeaturesContainer, BorderLayout.SOUTH);
                    carMainPanel.setPreferredSize(new Dimension(1600, 1150));
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

        technicalSpecsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return technicalSpecsPanel;
    }

    private JPanel carCodesPanel() {
        // additional not so important details for users like codes: ID,Numbers at the
        // bottom bar

        JLabel vehilceIdLabel = new JLabel("Vehicle ID : " + vehicle.getVehicleId());
        vehilceIdLabel.setForeground(Theme.getForeground());
        vehilceIdLabel.setFont(CustomFonts.INSTRUMENT_SANS_SEMI_BOLD.deriveFont(15f));

        JLabel vinNumberLabel = new JLabel("VIN Number : " + vehicle.getVinNumber());
        vinNumberLabel.setForeground(Theme.getForeground());
        vinNumberLabel.setFont(CustomFonts.INSTRUMENT_SANS_SEMI_BOLD.deriveFont(15f));

        JLabel registrationNumberLabel = new JLabel("Registration Number : " + vehicle.getRegistrationNumber());
        registrationNumberLabel.setForeground(Theme.getForeground());
        registrationNumberLabel.setFont(CustomFonts.INSTRUMENT_SANS_SEMI_BOLD.deriveFont(15f));

        JPanel carCodesPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        carCodesPanel.setBackground(Theme.getBackground());
        carCodesPanel.setPreferredSize(new Dimension(500, 75));
        carCodesPanel.add(vehilceIdLabel);
        carCodesPanel.add(vinNumberLabel);
        carCodesPanel.add(registrationNumberLabel);

        return carCodesPanel;
    }

    // Center panel
    private JPanel moreCarsPanel() {

        ImageIcon rightArrowIcon = IconLoader.getRightIcon();
        Vehicle[] vehicless = new Vehicle[4];
        List<Vehicle> vehicles = VehicleController.processVehicles();

        for (int i = 0; i < 4; i++) {
            vehicless[i] = vehicles.get(i);
        }

        // title for Other Cars and a button to view more cars
        JPanel otherCarsTitlePanel = new JPanel(new BorderLayout());
        otherCarsTitlePanel.setPreferredSize(new Dimension(800, 100));
        otherCarsTitlePanel.setBackground(Theme.getBackground());
        JLabel otherCarsLabel = new JLabel("Other Vehicles");
        otherCarsLabel.setForeground(Theme.getForeground());
        otherCarsLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(35f));
        JButton viewAllButton = new JButton("View All");
        viewAllButton.setFont(CustomFonts.INSTRUMENT_SANS_SEMI_BOLD.deriveFont(20f));
        viewAllButton.setPreferredSize(new Dimension(165, 50));
        viewAllButton.setOpaque(true);
        viewAllButton.setBorderPainted(false);
        viewAllButton.setFocusable(false);
        viewAllButton.setBackground(Theme.getBackground());
        viewAllButton.setForeground(Theme.getForeground());
        viewAllButton.setIcon(rightArrowIcon);
        viewAllButton.setHorizontalTextPosition(JButton.LEFT);
        viewAllButton.setVerticalAlignment(JButton.CENTER);
        viewAllButton.addActionListener(e -> {
            if (e.getActionCommand().equals("View All")) {
                panel.removeAll();
                panel.add(new VehiclesPage(frame, panel), BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();
            }
        });

        otherCarsTitlePanel.add(otherCarsLabel, BorderLayout.WEST);
        otherCarsTitlePanel.add(viewAllButton, BorderLayout.EAST);

        // contain cars
        JPanel carsContainer = new JPanel(new GridLayout(0, 3, 50, 30));
        carsContainer.setBackground(Theme.getBackground());
        int count = 0;
        for (Vehicle v : vehicless) {
            if (this.vehicle.getBrand().equals(v.getBrand()) && this.vehicle.getModel().equals(v.getModel())) {
                continue;
            } else if (count == 3) {
                break;
            }
            ImageIcon image = null;
            try {
                image = new ImageIcon(v.getImagePath());

                // Check if any image failed to load
                if (image.getIconWidth() == -1) {
                    throw new Exception("One or more images failed to load.");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error loading images: " + e.getMessage());
            }
            Image rImage = image.getImage().getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
            image = new ImageIcon(rImage);

            String availability = v.isAvailability() ? "AVAILABLE" : "UNAVAILABLE";
            String rentPrice = "RM" + v.getRentalPriceDay() + "/per day";

            carsContainer.add(VehiclesPage.createCarCard(v, image, v.getBrand(), v.getModel(),
                    v.getTransmission(), v.getFuelType(), v.getVehicleType(),
                    v.getSeatingCapacity(), rentPrice, availability, frame, panel));
            count++;
        }

        // container include cars and the labels and buttons
        JPanel emptyBottomPanel = new JPanel();
        emptyBottomPanel.setPreferredSize(new Dimension(800, 75));
        emptyBottomPanel.setBackground(Theme.getBackground());

        JPanel moreCarsContainer = new JPanel(new BorderLayout());
        moreCarsContainer.setBackground(Theme.getBackground());
        moreCarsContainer.setPreferredSize(new Dimension(800, 400));
        moreCarsContainer.add(carsContainer, BorderLayout.CENTER);
        moreCarsContainer.add(otherCarsTitlePanel, BorderLayout.NORTH);
        moreCarsContainer.add(emptyBottomPanel, BorderLayout.SOUTH);

        JPanel emptyLeftPanel = new JPanel();
        emptyLeftPanel.setBackground(Theme.getBackground());
        emptyLeftPanel.setPreferredSize(new Dimension(300, 1000));

        JPanel emptyRightPanel = new JPanel();
        emptyRightPanel.setBackground(Theme.getBackground());
        emptyRightPanel.setPreferredSize(new Dimension(300, 1000));

        JPanel moreCarsPanel = new JPanel(new BorderLayout());
        moreCarsPanel.setPreferredSize(new Dimension(1600, 580));
        moreCarsPanel.add(moreCarsContainer, BorderLayout.CENTER);
        moreCarsPanel.add(emptyRightPanel, BorderLayout.EAST);
        moreCarsPanel.add(emptyLeftPanel, BorderLayout.WEST);

        return moreCarsPanel;
    }

    // create features when press show more then remove when unshow
    private JPanel createCarFeaturesContainer() {
        JPanel carMainFeaturesContainer = new JPanel(new BorderLayout());
        carMainFeaturesContainer.setBackground(Theme.getBackground());
        carMainFeaturesContainer.setPreferredSize(new Dimension(1600, 500));

        JPanel featuresContainer = new JPanel(new BorderLayout());
        featuresContainer.setPreferredSize(new Dimension(1600, 200));
        featuresContainer.setBackground(Theme.getBackground());

        JLabel featuresLabel = new JLabel("Features");
        featuresLabel.setForeground(Theme.getForeground());
        featuresLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(30f));

        String[] features = this.vehicle.getFeatures().split(",");
        StringBuilder result = new StringBuilder("<html>");
        for (String feature : features) {
            result.append("- ").append(feature).append("<br>");
        }
        result.append("<html>");
        JLabel featuresContent = new JLabel(result.toString());
        featuresContent.setForeground(Theme.getSecondaryForeground());
        featuresContent.setFont(CustomFonts.INSTRUMENT_SANS_SEMI_BOLD.deriveFont(20f));

        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Theme.getBackground());
        emptyPanel.setPreferredSize(new Dimension(1600, 30));
        JPanel featurePanel = new JPanel(new BorderLayout());
        featurePanel.setBackground(Theme.getBackground());
        featurePanel.add(emptyPanel, BorderLayout.NORTH);
        featurePanel.add(featuresLabel, BorderLayout.CENTER);

        featuresContainer.add(featurePanel, BorderLayout.NORTH);
        featuresContainer.add(featuresContent, BorderLayout.CENTER);

        carMainFeaturesContainer.add(featuresContainer, BorderLayout.CENTER);

        carMainFeaturesContainer.add(sidePanel(), BorderLayout.EAST);
        carMainFeaturesContainer.add(sidePanel(), BorderLayout.WEST);

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
        sidePanel.setBackground(Theme.getBackground());
        sidePanel.setPreferredSize(new Dimension(250, 1000));

        return sidePanel;
    }
}