package gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class VehiclesPage extends JPanel implements ActionListener {

    private final JFrame frame;
    private final JPanel panel;

    public VehiclesPage(JFrame frame, JPanel panel) {

        this.frame = frame;
        this.panel = panel;
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        this.add(createCarCardsContainer(), BorderLayout.CENTER);
        // this.add(carLeftPanel(), BorderLayout.WEST);
        // this.add(carRightPanel(), BorderLayout.EAST);
        this.add(createCarTopBar(), BorderLayout.NORTH);

    }

    private JPanel createCarCards() {
        // images in the future will change this to loop to check all car images
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
        Image rImage = image.getImage().getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(rImage);

        JPanel carCards = new JPanel(new GridLayout(0, 3, 50, 30));
        carCards.setBackground(Color.WHITE);

        // sample details just to show output
        String[] cars = { "PORSCHE", "TOYOTA", "NISSAN",
                "HONDA", "FERRARI", "LAMBORGHINI",
                "PERODUA", "PROTON", "LEXUS",
                "BMW", "MERCEDES BENZ", "AUDI" };
        String model = "GT3 RS WEISSACH";
        String transmissions = "AUTO";
        String fuelType = "HYBRID";
        String carType = "CONVERTIBLE";
        int seats = 2;
        String price = "RM699 per day";
        String availability = "AVAILABLE";

        for (String car : cars) {
            carCards.add(createCarCard(image, car, model, transmissions, fuelType, carType,
                    seats, price, availability, frame, panel));
        }

        if (cars.length < 12) {
            for (int i = cars.length; i < 12; i++) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setPreferredSize(new Dimension(350, 400));
                carCards.add(emptyPanel);
            }
        }

        return carCards;
    }

    public static JPanel createCarCard(ImageIcon image, String brand, String model, String transmission,
            String fuelType,
            String carType, int seats, String price, String availability, JFrame frame, JPanel panel) {

        ImageIcon transmissionIcon = null;
        ImageIcon fuelIcon = null;
        ImageIcon seatsIcon = null;

        try {
            transmissionIcon = new ImageIcon("images/vehiclepageicons/manual-transmission.png");
            fuelIcon = new ImageIcon("images/vehiclepageicons/gas-station.png");
            seatsIcon = new ImageIcon("images/vehiclepageicons/car-seat.png");

            // Check if any image failed to load
            if (transmissionIcon.getIconWidth() == -1 ||
                    fuelIcon.getIconWidth() == -1 ||
                    seatsIcon.getIconWidth() == -1) {
                throw new Exception("One or more images failed to load.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading images: " + e.getMessage());
        }

        JPanel carCard = new JPanel();
        carCard.setLayout(new BorderLayout());
        carCard.setBackground(Color.WHITE);
        carCard.setPreferredSize(new Dimension(100, 400));
        carCard.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 35), 3));

        // create buttons on the bottom
        JButton carDetails = new JButton();
        carDetails.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        carDetails.setText("DETAILS");
        carDetails.setFocusable(false);
        carDetails.setBorderPainted(false);
        carDetails.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        carDetails.setBackground(Color.BLUE);
        carDetails.setForeground(Color.WHITE);
        carDetails.setOpaque(true);
        carDetails.addActionListener(e -> {
            if (e.getActionCommand().equals("DETAILS")) {
                panel.removeAll();
                panel.add(new VehiclesPageDetails(frame, panel), BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();
            }
        });

        JButton carRent = new JButton();
        carRent.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        carRent.setText("RENT");
        carRent.setFocusable(false);
        carRent.setBorderPainted(false);
        carRent.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        carRent.setBackground(Color.BLUE);
        carRent.setForeground(Color.WHITE);
        carRent.setOpaque(true);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 5));
        buttonPanel.setPreferredSize(new Dimension(350, 50));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(carRent);
        buttonPanel.add(carDetails);

        // car name model and car type on the left and price on the right
        JLabel carName = new JLabel(brand);
        carName.setHorizontalTextPosition(JLabel.LEFT);
        carName.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(20f));
        carName.setPreferredSize(new Dimension(50, 20));
        carName.setForeground(Color.BLACK);

        JLabel carModel = new JLabel(model);
        carModel.setHorizontalTextPosition(JLabel.LEFT);
        carModel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(17.5f));
        carModel.setPreferredSize(new Dimension(50, 10));
        carModel.setForeground(Color.BLACK);

        JLabel carRentPrice = new JLabel(price);
        carRentPrice.setHorizontalTextPosition(JLabel.RIGHT);
        carRentPrice.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(17.5f));
        carRentPrice.setForeground(Color.BLUE);

        JLabel carAvailability = new JLabel(availability);
        carAvailability.setOpaque(true);
        carAvailability.setHorizontalAlignment(JLabel.CENTER);
        carAvailability.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(15f));
        if (availability.equals("AVAILABLE")) {
            carAvailability.setBackground(Color.BLUE);
        } else {
            carAvailability.setBackground(Color.RED);
        }
        carAvailability.setForeground(Color.WHITE);
        carAvailability.setPreferredSize(new Dimension(200, 20));

        JLabel carTypeLabel = new JLabel(carType);
        carTypeLabel.setHorizontalTextPosition(JLabel.LEFT);
        carTypeLabel.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(12.5f));
        carTypeLabel.setPreferredSize(new Dimension(50, 10));
        carTypeLabel.setForeground(Color.GRAY);

        JPanel carNamePanel = new JPanel(new GridLayout(3, 1, 0, 0));
        carNamePanel.add(carName);
        carNamePanel.add(carModel);
        carNamePanel.add(carTypeLabel);
        carNamePanel.setPreferredSize(new Dimension(50, 150));
        carNamePanel.setBackground(Color.WHITE);
        JPanel carRentPriceAvailabilityPanel = new JPanel();
        carRentPriceAvailabilityPanel.add(carRentPrice, BorderLayout.NORTH);
        carRentPriceAvailabilityPanel.add(carAvailability, BorderLayout.SOUTH);
        carRentPriceAvailabilityPanel.setPreferredSize(new Dimension(50, 150));
        carRentPriceAvailabilityPanel.setBackground(Color.WHITE);

        // container for both details at the center
        JPanel carInfoNameRentPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        carInfoNameRentPanel.setBackground(Color.WHITE);
        carInfoNameRentPanel.add(carNamePanel);
        carInfoNameRentPanel.add(carRentPriceAvailabilityPanel);

        // container for picture at the top
        JLabel carPicture = new JLabel(image);
        carPicture.setPreferredSize(new Dimension(225, 225));
        JPanel carPicturePanel = new JPanel(new BorderLayout());
        carPicturePanel.add(carPicture);

        // minor details of the car at the bottom but the top of the buttons
        JLabel seatsLabel = new JLabel(Integer.toString(seats));
        seatsLabel.setIcon(seatsIcon);
        seatsLabel.setHorizontalTextPosition(JLabel.RIGHT);
        seatsLabel.setOpaque(true);
        seatsLabel.setBackground(Color.WHITE);
        // seatsLabel.setPreferredSize(new Dimension(0,35));
        seatsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        seatsLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel fuelTypeLabel = new JLabel(fuelType);
        fuelTypeLabel.setIcon(fuelIcon);
        fuelTypeLabel.setHorizontalTextPosition(JLabel.RIGHT);
        fuelTypeLabel.setBackground(Color.WHITE);
        fuelTypeLabel.setOpaque(true);
        // fuelTypeLabel.setPreferredSize(new Dimension(0,35));
        fuelTypeLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        fuelTypeLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel transLabel = new JLabel(transmission);
        transLabel.setIcon(transmissionIcon);
        transLabel.setHorizontalTextPosition(JLabel.RIGHT);
        transLabel.setBackground(Color.WHITE);
        transLabel.setOpaque(true);
        // transLabel.setPreferredSize(new Dimension(0,35));
        transLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        transLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel carInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        carInfoPanel.setBackground(Color.WHITE);
        carInfoPanel.add(transLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        carInfoPanel.add(fuelTypeLabel, gbc);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        carInfoPanel.add(seatsLabel, gbc);

        // a container to put every detail and infos
        JPanel carEverythingPanel = new JPanel(new BorderLayout(0, 2));
        carEverythingPanel.add(carPicturePanel, BorderLayout.NORTH);
        carEverythingPanel.add(carInfoNameRentPanel, BorderLayout.CENTER);
        carEverythingPanel.add(carInfoPanel, BorderLayout.SOUTH);
        carEverythingPanel.setBackground(Color.WHITE);

        // add the bottom buttons panel and the container for all specific car infos
        // into the main card container
        carCard.add(buttonPanel, BorderLayout.SOUTH);
        carCard.add(carEverythingPanel, BorderLayout.CENTER);

        return carCard;
    }

    private JScrollPane createCarCardsContainer() {

        // JPanel carCards = new JPanel(new GridLayout(0,3,20,15));

        JPanel carCardsPanel = new JPanel(new BorderLayout());
        carCardsPanel.add(createCarCards(), BorderLayout.CENTER);
        carCardsPanel.add(createCarRightPanel(), BorderLayout.EAST);
        carCardsPanel.add(createBottomBar(), BorderLayout.SOUTH);
        carCardsPanel.add(createCarLeftPanel(), BorderLayout.WEST);

        JScrollPane container = new JScrollPane(carCardsPanel);
        container.getVerticalScrollBar().setUnitIncrement(30);
        container.setBorder(BorderFactory.createEmptyBorder());
        container.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return container;
    }

    private JPanel createCarTopBar() {

        ImageIcon searchIcon = null;
        try {
            searchIcon = new ImageIcon("images/vehiclepageicons/search-interface-symbol.png");
            if (searchIcon.getIconWidth() == -1) {
                throw new Exception("Image file not found or invalid.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading image: " + e.getMessage());
        }

        JPanel topBar = new JPanel();
        topBar.setLayout(null);
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(1600, 100));

        JLabel filters = new JLabel("Filter");
        filters.setBounds(50, 25, 200, 50);
        filters.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));

        JTextField searchBar = new JTextField();
        searchBar.setText("Search for vehicles");
        searchBar.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));
        searchBar.setForeground(Color.GRAY);
        searchBar.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 15, 10, 5)));
        searchBar.setBounds(300, 25, 900, 50);
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search for vehicles")) {
                    searchBar.setText(""); // Clear text when clicked
                    searchBar.setForeground(Color.BLACK); // Set normal text color
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setText("Search for vehicles"); // Restore placeholder
                    searchBar.setForeground(Color.GRAY);
                }
            }
        });

        JButton searchButton = new JButton();
        searchButton.setIcon(searchIcon);
        searchButton.setBounds(1200, 25, 85, 50);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.WHITE);
        searchButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));
        searchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel sortByLabel = new JLabel("Sort By:");
        sortByLabel.setBounds(1350, 25, 75, 50);
        sortByLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(17.5f));
        sortByLabel.setHorizontalAlignment(JLabel.RIGHT);

        String[] sortOptions = { " Best Match ", " Lowest Price ", " Highest Price ", " Newest ", " Oldest " };
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setBounds(1450, 25, 150, 50);
        sortComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(17.5f));

        JPanel adminButtons = new JPanel(new GridLayout(1, 2, 0, 0));
        adminButtons.setBounds(1660, 25, 200, 50);
        JButton deleteButton = new JButton();
        deleteButton.setBackground(Color.RED);
        deleteButton.setOpaque(true);
        JButton addButton = new JButton();
        addButton.setBackground(Color.GREEN);
        addButton.setOpaque(true);

        adminButtons.add(deleteButton);
        adminButtons.add(addButton);

        topBar.add(searchBar);
        topBar.add(searchButton);
        topBar.add(filters);
        topBar.add(sortByLabel);
        topBar.add(sortComboBox);
        topBar.add(adminButtons);

        return topBar;
    }

    private JComboBox<String> brandComboBox;
    private JComboBox<String> modelComboBox;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> carTypeComboBox;
    private JComboBox<String> transTypeComboBox;
    private JComboBox<String> fuelTypeComboBox;
    private JComboBox<String> availabilityComboBox;
    private JTextField minPriceField;
    private JTextField maxPriceField;

    private JPanel createCarLeftPanel() {
        // sample data
        String[] brands = { "ALL", "NISSAN", "LAMBORGHINI", "FERRARI", "PORSCHE" };
        String[] models = { "ALL", "GTR R35", "M5 E90", "CLA45S" };
        String[] transType = { "ALL", "MANUAL", "AUTO" };
        String[] fuelType = { "ALL", "GAS", "HYBRID", "ELECTRIC" };
        String[] availability = { "ALL", "AVAILABLE", "UNAVAILABLE" };
        String[] carType = { "ALL", "SUV", "MPV", "SEDAN", "CONVERTIBLE", "COUPE", "PICKUP TRUCK", "HATCHBACK",
                "WAGON" };

        String[] year = new String[76];
        int newestYear = 2025;
        for (int i = 75; i >= 0; i--) {
            year[i] = Integer.toString(newestYear - i);
        }

        JLabel brandLabel = new JLabel("Select Brand");
        brandLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        brandComboBox = new JComboBox<>(brands);
        brandComboBox.addActionListener(this);
        brandComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        brandComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel brandFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        brandFilterPanel.setBackground(Color.WHITE);
        brandFilterPanel.add(brandLabel);
        brandFilterPanel.add(brandComboBox);

        JLabel modelLabel = new JLabel("Select Model");
        modelLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        modelComboBox = new JComboBox<>(models);
        modelComboBox.addActionListener(this);
        modelComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        modelComboBox.setPreferredSize(new Dimension(200, 30));
        modelComboBox.setEnabled(false);

        JPanel modelFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        modelFilterPanel.setBackground(Color.WHITE);
        modelFilterPanel.add(modelLabel);
        modelFilterPanel.add(modelComboBox);

        JLabel yearLabel = new JLabel("Select Year");
        yearLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        yearComboBox = new JComboBox<>(year);
        yearComboBox.insertItemAt("ALL", 0);
        yearComboBox.addActionListener(this);
        yearComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        yearComboBox.setPreferredSize(new Dimension(200, 30));
        yearComboBox.setSelectedIndex(0);

        JPanel yearFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        yearFilterPanel.setBackground(Color.WHITE);
        yearFilterPanel.add(yearLabel);
        yearFilterPanel.add(yearComboBox);

        JLabel transLabel = new JLabel("Select Transmission");
        transLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        transTypeComboBox = new JComboBox<>(transType);
        transTypeComboBox.addActionListener(this);
        transTypeComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        transTypeComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel transFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        transFilterPanel.setBackground(Color.WHITE);
        transFilterPanel.add(transLabel);
        transFilterPanel.add(transTypeComboBox);

        JLabel fuelLabel = new JLabel("Select Fuel Type");
        fuelLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        fuelTypeComboBox = new JComboBox<>(fuelType);
        fuelTypeComboBox.addActionListener(this);
        fuelTypeComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        fuelTypeComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel fuelFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        fuelFilterPanel.setBackground(Color.WHITE);
        fuelFilterPanel.add(fuelLabel);
        fuelFilterPanel.add(fuelTypeComboBox);

        JLabel availabilityLabel = new JLabel("Select Availability");
        availabilityLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        availabilityComboBox = new JComboBox<>(availability);
        availabilityComboBox.addActionListener(this);
        availabilityComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        availabilityComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel availabilityFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        availabilityFilterPanel.setBackground(Color.WHITE);
        availabilityFilterPanel.add(availabilityLabel);
        availabilityFilterPanel.add(availabilityComboBox);

        JLabel carTypeLabel = new JLabel("Select Car Type");
        carTypeLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        carTypeComboBox = new JComboBox<>(carType);
        carTypeComboBox.addActionListener(this);
        carTypeComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        carTypeComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel carTypeFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        carTypeFilterPanel.setBackground(Color.WHITE);
        carTypeFilterPanel.add(carTypeLabel);
        carTypeFilterPanel.add(carTypeComboBox);

        JLabel seatLabel = new JLabel("Seats: ALL");
        seatLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        JSlider seatSlider = new JSlider(0, 7, 0);
        seatSlider.setBackground(Color.WHITE);
        seatSlider.setMajorTickSpacing(1);
        seatSlider.setPaintTicks(true);
        seatSlider.setPaintLabels(true);
        seatSlider.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        seatSlider.addChangeListener(e -> {
            int value = seatSlider.getValue();
            if (value == 0) {
                seatLabel.setText("Seats: ALL");
            } else {
                seatLabel.setText("Seats: " + value);
            }
        });

        JPanel seatFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        seatFilterPanel.setBackground(Color.WHITE);
        seatFilterPanel.add(seatLabel);
        seatFilterPanel.add(seatSlider);

        JLabel priceLabel = new JLabel("Price (RM):");
        priceLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        minPriceField = new JTextField("Min", 4);
        minPriceField.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));
        minPriceField.setForeground(Color.GRAY);
        maxPriceField = new JTextField("Max", 4);
        maxPriceField.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));
        maxPriceField.setForeground(Color.GRAY);

        // Set the preferred size to control height
        minPriceField.setPreferredSize(new Dimension(50, 40));
        minPriceField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (minPriceField.getText().equals("Min")) {
                    minPriceField.setText(""); // Clear text when clicked
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (minPriceField.getText().isEmpty()) {
                    minPriceField.setText("Min"); // Restore placeholder
                }
            }
        });

        maxPriceField.setPreferredSize(new Dimension(50, 40));
        maxPriceField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (maxPriceField.getText().equals("Max")) {
                    maxPriceField.setText(""); // Clear text when clicked
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (maxPriceField.getText().isEmpty()) {
                    maxPriceField.setText("Max"); // Restore placeholder
                }
            }
        });

        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 7, 0));
        pricePanel.setBackground(Color.WHITE);
        pricePanel.add(minPriceField);
        pricePanel.add(new JLabel("-"));
        pricePanel.add(maxPriceField);
        pricePanel.setPreferredSize(new Dimension(200, 60));
        // filter name add label and suf to a panel then only format it to spacing down
        // the filters
        JPanel priceFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        priceFilterPanel.setBackground(Color.WHITE);
        priceFilterPanel.add(priceLabel);
        priceFilterPanel.add(pricePanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        leftPanel.setPreferredSize(new Dimension(300, 1600));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(brandFilterPanel);
        leftPanel.add(modelFilterPanel);
        leftPanel.add(yearFilterPanel);
        leftPanel.add(transFilterPanel);
        leftPanel.add(fuelFilterPanel);
        leftPanel.add(availabilityFilterPanel);
        leftPanel.add(carTypeFilterPanel);
        leftPanel.add(seatFilterPanel);
        leftPanel.add(priceFilterPanel);

        return leftPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == brandComboBox) {
            // brandComboBox.getSelectedItem();
            // yearComboBox.getSelectedItem();
            // transTypeComboBox.getSelectedItem();
            // fuelTypeComboBox.getSelectedItem();
            // availabilityComboBox.getSelectedItem();
            // carTypeComboBox.getSelectedItem();
            if (brandComboBox.getSelectedIndex() != 0) {
                modelComboBox.setEnabled(true);
                if (e.getSource() == modelComboBox) {
                    modelComboBox.getSelectedItem();
                }
            } else {
                modelComboBox.setSelectedIndex(0);
                modelComboBox.setEnabled(false);
            }
        }
    }

    private JPanel createCarRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 900));
        rightPanel.setBackground(Color.WHITE);

        return rightPanel;
    }

    private JPanel createBottomBar() {
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.BLACK);
        bottomBar.setPreferredSize(new Dimension(800, 500));

        return bottomBar;
    }
}
