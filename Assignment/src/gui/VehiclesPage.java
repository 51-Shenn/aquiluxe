package gui;

import controllers.VehicleController;
import datamodels.Vehicle;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VehiclesPage extends JPanel implements ActionListener {

    private List<Vehicle> vehicles = VehicleController.passVehicles();
    private List<Vehicle> sortedVehicles = vehicles;
    public static final ImageIcon TRANSMISSION;
    public static final ImageIcon FUEL;
    public static final ImageIcon SEATS;

    static {
        TRANSMISSION = new ImageIcon("images/vehiclepageicons/manual-transmission.png");
        FUEL = new ImageIcon("images/vehiclepageicons/gas-station.png");
        SEATS = new ImageIcon("images/vehiclepageicons/car-seat.png");
    }

    private final JFrame frame;
    private final JPanel panel;
    private JPanel vehicleCards;

    public VehiclesPage(JFrame frame, JPanel panel) {

        this.frame = frame;
        this.panel = panel;
        this.sortedVehicles = this.vehicles;
        this.setLayout(new BorderLayout());

        this.add(createCarCardsContainer(this.vehicles), BorderLayout.CENTER);
        this.add(createCarTopBar(), BorderLayout.NORTH);

    }

    private JPanel createCarCards(List<Vehicle> vehicles) {
        // images in the future will change this to loop to check all car images

        vehicleCards = new JPanel(new GridLayout(0, 3, 50, 30));
        vehicleCards.setBackground(Theme.getBackground());


        for (Vehicle v : vehicles) {
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
            Image rImage = image.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            image = new ImageIcon(rImage);

            String availability = v.isAvailability() ? "AVAILABLE" : "UNAVAILABLE";
            String rentPrice = "RM" + v.getRentalPriceDay() + "/per day";

            vehicleCards.add(createCarCard(v, image, v.getBrand(), v.getModel(), v.getTransmission(), v.getFuelType(),
                    v.getVehicleType(),
                    v.getSeatingCapacity(), rentPrice, availability, frame, panel));
        }

        return vehicleCards;
    }

    public static JPanel createCarCard(Vehicle vehicle, ImageIcon image, String brand, String model, String transmission,
            String fuelType,
            String vehicleType, int seats, String price, String availability, JFrame frame, JPanel panel) {

        ImageIcon transmissionIcon = TRANSMISSION;
        ImageIcon fuelIcon = FUEL;
        ImageIcon seatsIcon = SEATS;

        JPanel carCard = new JPanel();
        carCard.setLayout(new BorderLayout());
        carCard.setBackground(Theme.getBackground());
        carCard.setPreferredSize(new Dimension(350, 450));
        carCard.setBorder(BorderFactory.createLineBorder(Theme.getBackground(), 3, true));

        // create buttons on the bottom
        RoundedButton carDetails = new RoundedButton(0, Theme.getBackground());
        carDetails.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        carDetails.setFocusable(false);
        carDetails.setBackground(Theme.getBackground());
        carDetails.setBorderPainted(false);
        carDetails.setContentAreaFilled(false);
        carDetails.setOpaque(true);

        RoundedButton carRent = new RoundedButton(10, Theme.getSpecial());
        carRent.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        carRent.setText("RENT");
        carRent.setFocusable(false);
        carRent.setBorderPainted(false);
        carRent.setContentAreaFilled(false);
        carRent.setBackground(Theme.getSpecial());
        carRent.setForeground(Theme.getSpecialForeground());
        carRent.setOpaque(true);

        carRent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                carRent.setBackground(Theme.getHoverSpecial());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                carRent.setBackground(Theme.getSpecial());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                carRent.setBackground(Theme.getPressedSpecial());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                carRent.setBackground(Theme.getSpecial());
                // pass selected car to rental controller
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 5));
        buttonPanel.setPreferredSize(new Dimension(350, 50));
        buttonPanel.setBackground(Theme.getBackground());
        buttonPanel.add(carDetails);
        buttonPanel.add(carRent);

        // car name model and car type on the left and price on the right
        JLabel carName = new JLabel(brand);
        carName.setHorizontalTextPosition(JLabel.LEFT);
        carName.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(20f));
        carName.setPreferredSize(new Dimension(50, 20));
        carName.setForeground(Theme.getForeground());

        JLabel carModel = new JLabel(model);
        carModel.setHorizontalTextPosition(JLabel.LEFT);
        carModel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(17.5f));
        carModel.setPreferredSize(new Dimension(50, 10));
        carModel.setForeground(Theme.getForeground());

        JLabel carRentPrice = new JLabel(price);
        carRentPrice.setHorizontalTextPosition(JLabel.RIGHT);
        carRentPrice.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(17.5f));
        carRentPrice.setForeground(Theme.getSpecial());

        JLabel carAvailability = new JLabel(availability);
        carAvailability.setOpaque(true);
        carAvailability.setHorizontalAlignment(JLabel.CENTER);
        carAvailability.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(15f));
        if (availability.equals("AVAILABLE")) {
            carAvailability.setBackground(Theme.getSpecial());
        } else {
            carAvailability.setBackground(Theme.getError());
        }
        carAvailability.setForeground(Theme.getSpecialForeground());
        carAvailability.setPreferredSize(new Dimension(200, 20));

        JLabel carTypeLabel = new JLabel(vehicleType);
        carTypeLabel.setHorizontalTextPosition(JLabel.LEFT);
        carTypeLabel.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(12.5f));
        carTypeLabel.setPreferredSize(new Dimension(50, 10));
        carTypeLabel.setForeground(Theme.getSecondaryForeground());

        JPanel carNamePanel = new JPanel(new GridLayout(3, 1, 0, 0));
        carNamePanel.add(carName);
        carNamePanel.add(carModel);
        carNamePanel.add(carTypeLabel);
        carNamePanel.setPreferredSize(new Dimension(50, 150));
        carNamePanel.setBackground(Theme.getBackground());
        JPanel carRentPriceAvailabilityPanel = new JPanel();
        carRentPriceAvailabilityPanel.add(carRentPrice, BorderLayout.NORTH);
        carRentPriceAvailabilityPanel.add(carAvailability, BorderLayout.SOUTH);
        carRentPriceAvailabilityPanel.setPreferredSize(new Dimension(50, 150));
        carRentPriceAvailabilityPanel.setBackground(Theme.getBackground());

        // container for both details at the center
        JPanel carInfoNameRentPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        carInfoNameRentPanel.setBackground(Theme.getBackground());
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
        seatsLabel.setBackground(Theme.getBackground());
        seatsLabel.setForeground(Theme.getForeground());
        // seatsLabel.setPreferredSize(new Dimension(0,35));
        seatsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        seatsLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel fuelTypeLabel = new JLabel(fuelType);
        fuelTypeLabel.setIcon(fuelIcon);
        fuelTypeLabel.setHorizontalTextPosition(JLabel.RIGHT);
        fuelTypeLabel.setBackground(Theme.getBackground());
        fuelTypeLabel.setOpaque(true);
        fuelTypeLabel.setForeground(Theme.getForeground());
        // fuelTypeLabel.setPreferredSize(new Dimension(0,35));
        fuelTypeLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        fuelTypeLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel transLabel = new JLabel(transmission);
        transLabel.setIcon(transmissionIcon);
        transLabel.setHorizontalTextPosition(JLabel.RIGHT);
        transLabel.setBackground(Theme.getBackground());
        transLabel.setOpaque(true);
        transLabel.setForeground(Theme.getForeground());
        // transLabel.setPreferredSize(new Dimension(0,35));
        transLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        transLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel carInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        carInfoPanel.setBackground(Theme.getBackground());
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
        carEverythingPanel.setBackground(Theme.getBackground());
        carEverythingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if the click is on the image
                if (SwingUtilities.isLeftMouseButton(e)) {
                    panel.removeAll();
                    panel.add(new VehiclesPageDetails(frame, panel, vehicle), BorderLayout.CENTER);
                    panel.revalidate();
                    panel.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    carPicture.setIcon(toGreyScale(image)); // Set greyscale image
                    carAvailability.setBackground(Theme.getPressedSpecial());
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    carPicture.setIcon(image); // Restore original image
                    carAvailability.setBackground(Theme.getPressedSpecial());
                    transLabel.setIcon(transmissionIcon);
                    fuelTypeLabel.setIcon(fuelIcon);
                    seatsLabel.setIcon(seatsIcon);
                }
            }
        });
        // if is customer
        carDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if the click is on the image
                if (SwingUtilities.isLeftMouseButton(e)) {
                    panel.removeAll();
                    panel.add(new VehiclesPageDetails(frame, panel, vehicle), BorderLayout.CENTER);
                    panel.revalidate();
                    panel.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    carPicture.setIcon(toGreyScale(image)); // Set greyscale image
                    carAvailability.setBackground(Theme.getPressedSpecial());
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    carPicture.setIcon(image); // Restore original image
                    carAvailability.setBackground(Theme.getPressedSpecial());
                    transLabel.setIcon(transmissionIcon);
                    fuelTypeLabel.setIcon(fuelIcon);
                    seatsLabel.setIcon(seatsIcon);
                }
            }
        });

        // if its admin
        // carDetails.setText("EDIT");
        // carDetails.setForeground(Theme.getBackground());
        // carDetails.setBackground(Theme.getError());
        // carDetails.setOpaque(true);

        // carDetails.addMouseListener(new MouseAdapter() {
        // @Override
        // public void mouseEntered(MouseEvent evt) {
        // carDetails.setBackground(Theme.getHoverError());
        // }

        // @Override
        // public void mouseExited(MouseEvent evt) {
        // carDetails.setBackground(Theme.getError());
        // }

        // @Override
        // public void mousePressed(MouseEvent evt) {
        // carDetails.setBackground(Theme.getPressedError());
        // }

        // @Override
        // public void mouseReleased(MouseEvent evt) {
        // carDetails.setBackground(Theme.getError());
        // }
        // });

        // add the bottom buttons panel and the container for all specific car infos
        // into the main card container
        carCard.add(buttonPanel, BorderLayout.SOUTH);
        carCard.add(carEverythingPanel, BorderLayout.CENTER);

        return carCard;
    }

    private JScrollPane createCarCardsContainer(List<Vehicle> vehicles) {

        // JPanel vehicleCards = new JPanel(new GridLayout(0,3,20,15));

        JPanel carCardsPanel = new JPanel(new BorderLayout());
        carCardsPanel.add(createCarCards(vehicles), BorderLayout.CENTER);
        carCardsPanel.add(createCarRightPanel(), BorderLayout.EAST);
        carCardsPanel.add(createBottomBar(), BorderLayout.SOUTH);
        carCardsPanel.add(createCarLeftPanel(), BorderLayout.WEST);

        JScrollPane container = new JScrollPane(carCardsPanel);
        container.getVerticalScrollBar().setUnitIncrement(30);
        container.setBorder(BorderFactory.createEmptyBorder());
        container.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return container;
    }

    private JComboBox<String> sortComboBox;

    private JPanel createCarTopBar() {

        ImageIcon searchIcon = null;
        ImageIcon carIcon = null;
        ImageIcon bikeIcon = null;
        try {
            searchIcon = new ImageIcon("images/vehiclepageicons/search-interface-symbol.png");
            carIcon = new ImageIcon("images/vehiclepageicons/car.png");
            bikeIcon = new ImageIcon("images/vehiclepageicons/bike.png");
            if (searchIcon.getIconWidth() == -1 || carIcon.getIconWidth() == -1 || bikeIcon.getIconWidth() == -1) {
                throw new Exception("Image file not found or invalid.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading image: " + e.getMessage());
        }

        JPanel topBar = new JPanel();
        topBar.setLayout(null);
        topBar.setBackground(Theme.getBackground());
        topBar.setPreferredSize(new Dimension(1600, 100));

        JLabel filters = new JLabel("Filter");
        filters.setForeground(Theme.getForeground());
        filters.setBounds(50, 25, 200, 50);
        filters.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));

        // if car then array of vehicles if bike then array of bikes else array of vehicles
        RoundedButton carButton = new RoundedButton(10, Theme.getSpecial());
        carButton.setIcon(carIcon);
        carButton.setBorderPainted(false);
        carButton.setBounds(375, 25, 65, 50);
        carButton.setFocusable(false);
        carButton.setBackground(Theme.getSpecial());
        carButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));
        carButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                carButton.setBackground(Theme.getHoverSpecial());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                carButton.setBackground(Theme.getSpecial());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                carButton.setBackground(Theme.getPressedSpecial());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                carButton.setBackground(Theme.getSpecial());
            }
        });

        RoundedButton bikeButton = new RoundedButton(10, Theme.getSpecial());
        bikeButton.setIcon(bikeIcon);
        bikeButton.setBorderPainted(false);
        bikeButton.setBounds(450, 25, 65, 50);
        bikeButton.setFocusable(false);
        bikeButton.setBackground(Theme.getSpecial());
        bikeButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));
        bikeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                bikeButton.setBackground(Theme.getHoverSpecial());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                bikeButton.setBackground(Theme.getSpecial());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                bikeButton.setBackground(Theme.getPressedSpecial());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                bikeButton.setBackground(Theme.getSpecial());
            }
        });

        RoundedButton allButton = new RoundedButton(10, Theme.getSpecial());
        allButton.setText("ALL");
        allButton.setOpaque(true);
        allButton.setBorderPainted(false);
        allButton.setBounds(300, 25, 65, 50);
        allButton.setFocusable(false);
        allButton.setBackground(Theme.getSpecial());
        allButton.setForeground(Color.BLACK);
        allButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));
        allButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                allButton.setBackground(Theme.getHoverSpecial());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                allButton.setBackground(Theme.getSpecial());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                allButton.setBackground(Theme.getPressedSpecial());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                allButton.setBackground(Theme.getSpecial());
            }
        });

        JTextField searchBar = new JTextField();
        searchBar.setText("Search for vehicles");
        searchBar.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));
        searchBar.setForeground(Theme.getSecondaryForeground());
        searchBar.setBorder(new CompoundBorder(new LineBorder(Theme.getForeground(), 1), new EmptyBorder(10, 15, 10, 5)));
        searchBar.setBounds(600, 25, 600, 50);
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
                    searchBar.setForeground(Theme.getSecondaryForeground());
                }
            }
        });

        JButton searchButton = new JButton();
        searchButton.setIcon(searchIcon);
        searchButton.setBounds(1200, 25, 85, 50);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));
        searchButton.setBorder(BorderFactory.createLineBorder(Theme.getTransparencyColor(), 1));

        JLabel sortByLabel = new JLabel("Sort By:");
        sortByLabel.setBounds(1350, 25, 75, 50);
        sortByLabel.setForeground(Theme.getForeground());
        sortByLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(17.5f));
        sortByLabel.setHorizontalAlignment(JLabel.RIGHT);

        String[] sortOptions = { " Best Match ", " Lowest Price ", " Highest Price ", " Newest ", " Oldest " };
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setForeground(Color.BLACK);
        sortComboBox.setFocusable(false);
        sortComboBox.setBounds(1450, 25, 150, 50);
        sortComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(17.5f));
        sortComboBox.addActionListener(this);
        ImageIcon addIcon = new ImageIcon("images/vehiclepageicons/add.png");

        RoundedButton addButton = new RoundedButton(10, Theme.getBackground());
        addButton.setIcon(addIcon);
        addButton.setBackground(Theme.getSuccess());
        addButton.setOpaque(true);
        addButton.setFocusable(false);
        addButton.setContentAreaFilled(true);
        addButton.setBorderPainted(false);
        addButton.setBounds(1660, 25, 200, 50);
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                addButton.setBackground(Theme.getHoverSuccess());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                addButton.setBackground(Theme.getSuccess());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                addButton.setBackground(Theme.getPressedSuccess());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                addButton.setBackground(Theme.getSuccess());
            }
        });
        addButton.addActionListener(e -> showAddCarPopup());

        topBar.add(allButton);
        topBar.add(carButton);
        topBar.add(bikeButton);
        topBar.add(searchBar);
        topBar.add(searchButton);
        topBar.add(filters);
        topBar.add(sortByLabel);
        topBar.add(sortComboBox);
        topBar.add(addButton);

        return topBar;
    }

    private JComboBox<String> brandComboBox;
    private JComboBox<String> modelComboBox;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> carTypeComboBox;
    private JComboBox<String> transTypeComboBox;
    private JComboBox<String> fuelTypeComboBox;
    private JComboBox<String> availabilityComboBox;
    private JSlider seatSlider;
    private JTextField minPriceField;
    private JTextField maxPriceField;

    private JPanel createCarLeftPanel() {
        // sample data
        // in future loop through every car brands and model and list them through the
        // filter + "ALL"
        // when user pick a brand filter model will update and only show few models
        // follow the brand same as cartype
        String[] brands = { "ALL", "NISSAN", "LAMBORGHINI", "FERRARI", "PORSCHE" };
        String[] models = { "ALL", "AVENTADOR SVJ", "GT3 RS WEISSACH", "SUPRA MK5" };
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
        brandLabel.setForeground(Theme.getForeground());

        UIManager.put("ComboBox.background", Theme.getBackground());
        UIManager.put("ComboBox.foreground", Theme.getForeground());
        UIManager.put("ComboBox.selectionBackground", Theme.getHoverBackground());
        UIManager.put("ComboBox.selectionForeground", Theme.getForeground());
        UIManager.put("ComboBox.buttonBackground", Theme.getBackground());
        UIManager.put("ComboBox.buttonShadow", Theme.getSpecial());

        brandComboBox = new JComboBox<>(brands);
        brandComboBox.setForeground(Color.BLACK);
        brandComboBox.setFocusable(false);
        brandComboBox.addActionListener(this);
        brandComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        brandComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel brandFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        brandFilterPanel.setBackground(Theme.getBackground());
        brandFilterPanel.add(brandLabel);
        brandFilterPanel.add(brandComboBox);

        JLabel modelLabel = new JLabel("Select Model");
        modelLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        modelLabel.setForeground(Theme.getForeground());

        modelComboBox = new JComboBox<>(models);
        modelComboBox.setForeground(Color.BLACK);
        modelComboBox.setFocusable(false);
        modelComboBox.addActionListener(this);
        modelComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        modelComboBox.setPreferredSize(new Dimension(200, 30));
        modelComboBox.setEnabled(false);

        JPanel modelFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        modelFilterPanel.setBackground(Theme.getBackground());
        modelFilterPanel.add(modelLabel);
        modelFilterPanel.add(modelComboBox);

        JLabel yearLabel = new JLabel("Select Year");
        yearLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        yearLabel.setForeground(Theme.getForeground());

        yearComboBox = new JComboBox<>(year);
        yearComboBox.setForeground(Color.BLACK);
        yearComboBox.setFocusable(false);
        yearComboBox.insertItemAt("ALL", 0);
        yearComboBox.addActionListener(this);
        yearComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        yearComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel yearFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        yearFilterPanel.setBackground(Theme.getBackground());
        yearFilterPanel.add(yearLabel);
        yearFilterPanel.add(yearComboBox);

        JLabel transLabel = new JLabel("Select Transmission");
        transLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        transLabel.setForeground(Theme.getForeground());

        transTypeComboBox = new JComboBox<>(transType);
        transTypeComboBox.setForeground(Color.BLACK);
        transTypeComboBox.setFocusable(false);
        transTypeComboBox.addActionListener(this);
        transTypeComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        transTypeComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel transFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        transFilterPanel.setBackground(Theme.getBackground());
        transFilterPanel.add(transLabel);
        transFilterPanel.add(transTypeComboBox);

        JLabel fuelLabel = new JLabel("Select Fuel Type");
        fuelLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        fuelLabel.setForeground(Theme.getForeground());

        fuelTypeComboBox = new JComboBox<>(fuelType);
        fuelTypeComboBox.setForeground(Color.BLACK);
        fuelTypeComboBox.setFocusable(false);
        fuelTypeComboBox.addActionListener(this);
        fuelTypeComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        fuelTypeComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel fuelFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        fuelFilterPanel.setBackground(Theme.getBackground());
        fuelFilterPanel.add(fuelLabel);
        fuelFilterPanel.add(fuelTypeComboBox);

        JLabel availabilityLabel = new JLabel("Select Availability");
        availabilityLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        availabilityLabel.setForeground(Theme.getForeground());

        availabilityComboBox = new JComboBox<>(availability);
        availabilityComboBox.setForeground(Color.BLACK);
        availabilityComboBox.setFocusable(false);
        availabilityComboBox.addActionListener(this);
        availabilityComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        availabilityComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel availabilityFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        availabilityFilterPanel.setBackground(Theme.getBackground());
        availabilityFilterPanel.add(availabilityLabel);
        availabilityFilterPanel.add(availabilityComboBox);

        JLabel carTypeLabel = new JLabel("Select Car Type");
        carTypeLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        carTypeLabel.setForeground(Theme.getForeground());

        carTypeComboBox = new JComboBox<>(carType);
        carTypeComboBox.setForeground(Color.BLACK);
        carTypeComboBox.setFocusable(false);
        carTypeComboBox.addActionListener(this);
        carTypeComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        carTypeComboBox.setPreferredSize(new Dimension(200, 30));

        JPanel carTypeFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        carTypeFilterPanel.setBackground(Theme.getBackground());
        carTypeFilterPanel.add(carTypeLabel);
        carTypeFilterPanel.add(carTypeComboBox);

        JLabel seatLabel = new JLabel("Seats: ALL");
        seatLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        seatLabel.setForeground(Theme.getForeground());

        seatSlider = new JSlider(0, 7, 0);
        seatSlider.setFocusable(false);
        seatSlider.setBackground(Theme.getBackground());
        seatSlider.setForeground(Theme.getForeground());
        seatSlider.setMajorTickSpacing(1);
        seatSlider.setPaintTicks(true);
        seatSlider.setPaintLabels(true);
        seatSlider.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        JPanel seatFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        seatFilterPanel.setBackground(Theme.getBackground());
        seatFilterPanel.add(seatLabel);
        seatFilterPanel.add(seatSlider);

        JLabel priceLabel = new JLabel("Price (RM):");
        priceLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        priceLabel.setForeground(Theme.getForeground());

        minPriceField = new JTextField("Min", 4);
        minPriceField.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));
        minPriceField.setForeground(Theme.getSecondaryForeground());
        maxPriceField = new JTextField("Max", 4);
        maxPriceField.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));
        maxPriceField.setForeground(Theme.getSecondaryForeground());

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
        minPriceField.addActionListener(e -> refreshCards(VehicleController.allFilterCombination(this.sortedVehicles,
                (String) brandComboBox.getSelectedItem(), (String) modelComboBox.getSelectedItem(),
                yearComboBox.getSelectedItem(), (String) fuelTypeComboBox.getSelectedItem(),
                (String) transTypeComboBox.getSelectedItem(),
                (String) availabilityComboBox.getSelectedItem(), (String) carTypeComboBox.getSelectedItem(),
                seatSlider.getValue(),
                (String) minPriceField.getText(), (String) maxPriceField.getText())));
        maxPriceField.addActionListener(e -> refreshCards(VehicleController.allFilterCombination(this.sortedVehicles,
                (String) brandComboBox.getSelectedItem(), (String) modelComboBox.getSelectedItem(),
                yearComboBox.getSelectedItem(), (String) fuelTypeComboBox.getSelectedItem(),
                (String) transTypeComboBox.getSelectedItem(),
                (String) availabilityComboBox.getSelectedItem(), (String) carTypeComboBox.getSelectedItem(),
                seatSlider.getValue(),
                (String) minPriceField.getText(), (String) maxPriceField.getText())));

        seatSlider.addChangeListener(e -> {
            int value = seatSlider.getValue();
            if (value == 0) {
                seatLabel.setText("Seats: ALL");
            } else {
                seatLabel.setText("Seats: " + value);
            }
            refreshCards(VehicleController.allFilterCombination(this.sortedVehicles,
                    (String) brandComboBox.getSelectedItem(), (String) modelComboBox.getSelectedItem(),
                    yearComboBox.getSelectedItem(), (String) fuelTypeComboBox.getSelectedItem(),
                    (String) transTypeComboBox.getSelectedItem(),
                    (String) availabilityComboBox.getSelectedItem(), (String) carTypeComboBox.getSelectedItem(),
                    seatSlider.getValue(),
                    (String) minPriceField.getText(), (String) maxPriceField.getText()));
        });

        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 7, 0));
        pricePanel.setBackground(Theme.getBackground());
        pricePanel.add(minPriceField);
        pricePanel.add(new JLabel("-"));
        pricePanel.add(maxPriceField);
        pricePanel.setPreferredSize(new Dimension(200, 60));
        // filter name add label and suf to a panel then only format it to spacing down
        // the filters
        JPanel priceFilterPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        priceFilterPanel.setBackground(Theme.getBackground());
        priceFilterPanel.add(priceLabel);
        priceFilterPanel.add(pricePanel);

        yearComboBox.setSelectedIndex(0);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        leftPanel.setPreferredSize(new Dimension(300, 1600));
        leftPanel.setBackground(Theme.getBackground());
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
        if (e.getSource() == sortComboBox) {
            if (sortComboBox.getSelectedItem() == " Best Match ") {
                this.sortedVehicles = this.vehicles;
            } else if (sortComboBox.getSelectedItem() == " Lowest Price ") {
                this.sortedVehicles = VehicleController.passSortedByPriceLowToHigh(this.vehicles);
            } else if (sortComboBox.getSelectedItem() == " Highest Price ") {
                this.sortedVehicles = VehicleController.passSortedByPriceHighToLow(this.vehicles);
            } else if (sortComboBox.getSelectedItem() == " Newest ") {
                this.sortedVehicles = VehicleController.passSortedByYearNewestFirst(this.vehicles);
            } else if (sortComboBox.getSelectedItem() == " Oldest ") {
                this.sortedVehicles = VehicleController.passSortedByYearOldestFirst(this.vehicles);
            }
            refreshCards(VehicleController.allFilterCombination(this.sortedVehicles,
                    (String) brandComboBox.getSelectedItem(), (String) modelComboBox.getSelectedItem(),
                    yearComboBox.getSelectedItem(), (String) fuelTypeComboBox.getSelectedItem(),
                    (String) transTypeComboBox.getSelectedItem(),
                    (String) availabilityComboBox.getSelectedItem(), (String) carTypeComboBox.getSelectedItem(),
                    seatSlider.getValue(),
                    (String) minPriceField.getText(), (String) maxPriceField.getText()));
        }
        if (e.getSource() == brandComboBox || e.getSource() == modelComboBox ||
                e.getSource() == yearComboBox ||
                e.getSource() == carTypeComboBox ||
                e.getSource() == transTypeComboBox ||
                e.getSource() == fuelTypeComboBox ||
                e.getSource() == availabilityComboBox ||
                e.getSource() == minPriceField ||
                e.getSource() == maxPriceField) {
            refreshCards(VehicleController.allFilterCombination(this.sortedVehicles,
                    (String) brandComboBox.getSelectedItem(), (String) modelComboBox.getSelectedItem(),
                    yearComboBox.getSelectedItem(), (String) fuelTypeComboBox.getSelectedItem(),
                    (String) transTypeComboBox.getSelectedItem(),
                    (String) availabilityComboBox.getSelectedItem(), (String) carTypeComboBox.getSelectedItem(),
                    seatSlider.getValue(),
                    (String) minPriceField.getText(), (String) maxPriceField.getText()));
            if (brandComboBox.getSelectedIndex() != 0) {
                modelComboBox.setEnabled(true);
            } else {
                modelComboBox.setSelectedIndex(0);
                modelComboBox.setEnabled(false);
            }
        }
    }

    private void refreshCards(List<Vehicle> filteredvehicles) {
        // Clear existing cards but preserve layout
        vehicleCards.removeAll();
        vehicleCards.setLayout(new GridLayout(0, 3, 50, 30));
        vehicleCards.setBackground(Theme.getBackground());

        // Add new cards
        for (Vehicle c : filteredvehicles) {
            ImageIcon image = new ImageIcon(c.getImagePath());
            Image rImage = image.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            image = new ImageIcon(rImage);

            String availability = c.isAvailability() ? "AVAILABLE" : "UNAVAILABLE";
            String rentPrice = "RM" + c.getRentalPriceDay() + "/per day";

            JPanel card = createCarCard(c, image, c.getBrand(), c.getModel(),
                    c.getTransmission(), c.getFuelType(), c.getVehicleType(),
                    c.getSeatingCapacity(), rentPrice, availability, frame, panel);

            card.setPreferredSize(new Dimension(350, 400));
            vehicleCards.add(card);
        }

        // Add empty panels if needed
        if (filteredvehicles.size() < 12) {
            for (int i = filteredvehicles.size(); i < 12; i++) {
                if (filteredvehicles.isEmpty() && i == 4) {
                    JPanel empty = new JPanel(new BorderLayout());
                    JLabel noResultsLabel = new JLabel("No results found!");
                    noResultsLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
                    noResultsLabel.setHorizontalAlignment(JLabel.CENTER);
                    empty.setPreferredSize(new Dimension(350, 400));
                    empty.setBackground(Theme.getBackground());
                    empty.add(noResultsLabel, BorderLayout.NORTH);
                    vehicleCards.add(empty);
                } else {
                    JPanel empty = new JPanel();
                    empty.setPreferredSize(new Dimension(350, 400));
                    empty.setBackground(Theme.getBackground());
                    vehicleCards.add(empty);
                }
            }
        }

        // Force layout update
        vehicleCards.revalidate();
        vehicleCards.repaint();
    }

    private JPanel createCarRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 900));
        rightPanel.setBackground(Theme.getBackground());

        return rightPanel;
    }

    private JPanel createBottomBar() {
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Theme.getForeground());
        bottomBar.setPreferredSize(new Dimension(800, 500));

        return bottomBar;
    }

    public static ImageIcon toGreyScale(ImageIcon icon) {
        if (icon == null) {
            throw new IllegalArgumentException("ImageIcon cannot be null");
        }

        // Get the image from the ImageIcon
        Image image = icon.getImage();

        // Convert the Image to a BufferedImage
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Convert the BufferedImage to greyscale
        BufferedImageOp op = new ColorConvertOp(
                ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        BufferedImage greyImage = op.filter(bufferedImage, null);

        RescaleOp rescaleOp = new RescaleOp(1.0f, 20, null); // Adjust brightness (offset = 20)
        BufferedImage brighterGreyImage = rescaleOp.filter(greyImage, null);

        // Return the greyscale image as an ImageIcon
        return new ImageIcon(brighterGreyImage);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getSortedVehicles() {
        return sortedVehicles;
    }

    public void setSortedVehicles(List<Vehicle> sortedVehicles) {
        this.sortedVehicles = sortedVehicles;
    }

    public JPanel getVehicleCards() {
        return vehicleCards;
    }

    public void setVehicleCards(JPanel vehicleCards) {
        this.vehicleCards = vehicleCards;
    }

    public JComboBox<String> getSortComboBox() {
        return sortComboBox;
    }

    public void setSortComboBox(JComboBox<String> sortComboBox) {
        this.sortComboBox = sortComboBox;
    }

    public JComboBox<String> getBrandComboBox() {
        return brandComboBox;
    }

    public void setBrandComboBox(JComboBox<String> brandComboBox) {
        this.brandComboBox = brandComboBox;
    }

    public JComboBox<String> getModelComboBox() {
        return modelComboBox;
    }

    public void setModelComboBox(JComboBox<String> modelComboBox) {
        this.modelComboBox = modelComboBox;
    }

    public JComboBox<String> getYearComboBox() {
        return yearComboBox;
    }

    public void setYearComboBox(JComboBox<String> yearComboBox) {
        this.yearComboBox = yearComboBox;
    }

    public JComboBox<String> getCarTypeComboBox() {
        return carTypeComboBox;
    }

    public void setCarTypeComboBox(JComboBox<String> carTypeComboBox) {
        this.carTypeComboBox = carTypeComboBox;
    }

    public JComboBox<String> getTransTypeComboBox() {
        return transTypeComboBox;
    }

    public void setTransTypeComboBox(JComboBox<String> transTypeComboBox) {
        this.transTypeComboBox = transTypeComboBox;
    }

    public JComboBox<String> getFuelTypeComboBox() {
        return fuelTypeComboBox;
    }

    public void setFuelTypeComboBox(JComboBox<String> fuelTypeComboBox) {
        this.fuelTypeComboBox = fuelTypeComboBox;
    }

    public JComboBox<String> getAvailabilityComboBox() {
        return availabilityComboBox;
    }

    public void setAvailabilityComboBox(JComboBox<String> availabilityComboBox) {
        this.availabilityComboBox = availabilityComboBox;
    }

    public JSlider getSeatSlider() {
        return seatSlider;
    }

    public void setSeatSlider(JSlider seatSlider) {
        this.seatSlider = seatSlider;
    }

    public JTextField getMinPriceField() {
        return minPriceField;
    }

    public void setMinPriceField(JTextField minPriceField) {
        this.minPriceField = minPriceField;
    }

    public JTextField getMaxPriceField() {
        return maxPriceField;
    }

    public void setMaxPriceField(JTextField maxPriceField) {
        this.maxPriceField = maxPriceField;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(JLabel imageLabel) {
        this.imageLabel = imageLabel;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public void setSelectButton(JButton selectButton) {
        this.selectButton = selectButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(JButton removeButton) {
        this.removeButton = removeButton;
    }

    public File getSelectedImageFile() {
        return selectedImageFile;
    }

    public void setSelectedImageFile(File selectedImageFile) {
        this.selectedImageFile = selectedImageFile;
    }

    private static class RoundedButton extends JButton {
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
            g2d.fillRoundRect(0, 0, width - 1, height - 1, arcSize, arcSize);

            // Draw the icon (if set)
            Icon icon = getIcon();
            if (icon != null) {
                int iconWidth = icon.getIconWidth();
                int iconHeight = icon.getIconHeight();
                int iconX = (width - iconWidth - getText().length() * 5) / 2; // Adjust spacing
                int iconY = (height - iconHeight) / 2;
                icon.paintIcon(this, g2d, iconX, iconY);
            }

            FontMetrics fm = g2d.getFontMetrics();
            int textX = (width - fm.stringWidth(getText())) / 2;
            int textY = (height + fm.getAscent()) / 2 - 2;

            // Fill rounded rectangle
            g2d.setColor(getForeground());
            g2d.drawString(getText(), textX, textY);

            g2d.dispose();
        }
    }

    private void showAddCarPopup() {
        // Create the dialog
        JDialog dialog = new JDialog(frame, "Add New Car", true); // true for modal
        dialog.setPreferredSize(new Dimension(1600, 900));
        dialog.setLayout(new BorderLayout());
        dialog.setBackground(Theme.getBackground());

        JLabel title = new JLabel("Add New Car", JLabel.CENTER);
        title.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        title.setBackground(Theme.getBackground());
        dialog.add(title, BorderLayout.NORTH);

        // Add form components
        // need to use jscrollpane
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 1, 1));
        formPanel.setBackground(Theme.getBackground());

        JPanel picturePanel = new JPanel(new BorderLayout());
        JLabel carUploadImage = new JLabel("Car Image: ");
        picturePanel.add(carUploadImage);
        picturePanel.add(initImageUploader());
        formPanel.add(picturePanel);

        JScrollPane informationPanel = new JScrollPane();
        informationPanel.setBackground(Theme.getBackground());
        informationPanel.getViewport().setBackground(Theme.getBackground());
        formPanel.add(informationPanel);
        // gbc.gridy = 0;
        // gbc.gridx = 1;
        // gbc.weightx = 2;
        // gbc.weighty = 1;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // formPanel.add(new JLabel("Brand:"), gbc);

        // gbc.gridy = 1;
        // gbc.weighty = 1;
        // gbc.weightx = 5;
        // JTextField brandInput = new JTextField();
        // formPanel.add(brandInput, gbc);

        // gbc.gridy = 2;
        // gbc.weightx = 2;
        // gbc.weighty = 1;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // formPanel.add(new JLabel("Model:"), gbc);

        // gbc.gridy = 3;
        // gbc.weighty = 1;
        // gbc.weightx = 5;
        // JTextField modelInput = new JTextField();
        // formPanel.add(modelInput, gbc);

        // gbc.gridy = 4;
        // gbc.weightx = 2;
        // gbc.weighty = 1;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // formPanel.add(new JLabel("Year:"), gbc);

        // gbc.gridy = 5;
        // gbc.weighty = 1;
        // gbc.weightx = 5;
        // JComboBox yearInput = new JComboBox<>();
        // formPanel.add(yearInput, gbc);

        // formPanel.add(new JLabel("Brand:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Model:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Year:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Engine Capacity:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Horsepower:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Color:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Fuel Consumption (mpg):"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("VIN Number:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Registration Number:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Rental Price Per Day:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Transmission:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Fuel Type:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Car Type:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Seating Capacity:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Availability:"));
        // formPanel.add(new JTextField());
        // formPanel.add(new JLabel("Features:"));
        // formPanel.add(new JTextField());

        dialog.add(formPanel, BorderLayout.CENTER);

        // Add submit button
        RoundedButton submitButton = new RoundedButton(20, Theme.getSpecial());
        submitButton.setText("Submit");
        submitButton.setForeground(Theme.getSpecialForeground());
        submitButton.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        submitButton.setPreferredSize(new Dimension(1600, 100));
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                submitButton.setBackground(Theme.getHoverSpecial());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                submitButton.setBackground(Theme.getSpecial());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                submitButton.setBackground(Theme.getPressedSpecial());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                submitButton.setBackground(Theme.getSpecial());
            }
        });
        submitButton.addActionListener(e -> {
            // Handle form submission here
            JOptionPane.showMessageDialog(dialog, "Car added successfully!");
            dialog.dispose();
        });
        dialog.add(submitButton, BorderLayout.SOUTH);

        // Finalize and show the dialog
        dialog.pack();
        dialog.setLocationRelativeTo(frame); // Center relative to parent frame
        dialog.setVisible(true);
    }

    private JLabel imageLabel;
    private JButton selectButton, removeButton;
    private File selectedImageFile;
    private final String IMAGE_DIR = "images/vehicles/";

    // In your form initialization method:
    private JPanel initImageUploader() {
        // Image Upload Components
        JPanel imageUploadPanel = new JPanel(new BorderLayout());

        // Label for image preview
        imageLabel = new JLabel("No Image Selected", JLabel.CENTER);
        imageLabel.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(15f));
        imageLabel.setBorder(BorderFactory.createLineBorder(Theme.getTransparencyColor()));
        imageLabel.setPreferredSize(new Dimension(500, 500));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Theme.getBackground());
        selectButton = new JButton("Select Image");
        selectButton.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(20f));
        selectButton.setPreferredSize(new Dimension(250, 100));
        selectButton.setFocusable(false);

        removeButton = new JButton("Remove");
        removeButton.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(20f));
        removeButton.setPreferredSize(new Dimension(250, 100));
        removeButton.setFocusable(false);
        removeButton.setEnabled(false);

        buttonPanel.add(selectButton);
        buttonPanel.add(removeButton);

        // Add to panel
        imageUploadPanel.add(imageLabel, BorderLayout.CENTER);
        imageUploadPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        selectButton.addActionListener(this::handleImageSelect);
        removeButton.addActionListener(this::handleImageRemove);

        return imageUploadPanel;
    }

    // Handle image selection
    private void handleImageSelect(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();

            try {
                BufferedImage img = ImageIO.read(selectedImageFile);
                if (img.getWidth() == 900 && img.getHeight() == 900) {
                    // Create target directory if needed
                    new File(IMAGE_DIR).mkdirs();

                    // Copy file
                    String filename = selectedImageFile.getName();
                    File targetFile = new File(IMAGE_DIR + filename);
                    Files.copy(selectedImageFile.toPath(), targetFile.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    // Display preview
                    ImageIcon icon = new ImageIcon(
                            new ImageIcon(targetFile.getAbsolutePath())
                                    .getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH));
                    imageLabel.setIcon(icon);
                    imageLabel.setText("");
                    removeButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Image must be 900×900 pixels!",
                            "Invalid Size", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error loading image",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Handle image removal
    private void handleImageRemove(ActionEvent e) {
        if (selectedImageFile != null) {
            String filename = selectedImageFile.getName();
            File targetFile = new File(IMAGE_DIR + filename);

            if (targetFile.exists() && targetFile.delete()) {
                imageLabel.setIcon(null);
                imageLabel.setText("No Image Selected");
                removeButton.setEnabled(false);
                selectedImageFile = null;
            }
        }
    }
}
