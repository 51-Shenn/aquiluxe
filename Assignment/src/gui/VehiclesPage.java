package gui;

import controllers.UserController;
import controllers.VehicleController;
import datamodels.Admin;
import datamodels.Bike;
import datamodels.Car;
import datamodels.User;
import datamodels.Vehicle;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VehiclesPage extends JPanel implements ActionListener {

    private List<Vehicle> vehicles;
    private List<Vehicle> sortedVehicles;
    private Vehicle[] vehicless = new Vehicle[4];

    protected final Border BORDER = new LineBorder(Color.BLACK, 2);
    protected final Border PADDING = new EmptyBorder(10, 15, 10, 15);

    private final JFrame frame;
    private final JPanel panel;
    private JPanel vehicleCards;
    private static User user;
    private Dialog dialog = new Dialog();

    public VehiclesPage(JFrame frame, JPanel panel, User user) {

        this.frame = frame;
        this.panel = panel;
        this.vehicles = new ArrayList<>(VehicleController.processVehicles());
        this.sortedVehicles = this.vehicles;
        for (int i = 0; i < 4; i++) {
            vehicless[i] = vehicles.get(i);
        }
        VehiclesPage.user = user;
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
            HashMap<String, ImageIcon> imageMap = ImageLoader.getImageCache();
            try {
                for (ImageIcon imageicon : imageMap.values()) {
                    image = imageicon;
                    break;
                }

                // Check if any image failed to load
                if (image == null || image.getIconWidth() == -1) {
                    throw new Exception("One or more images failed to load.");
                }

            } catch (Exception e) {
                dialog.showDialog("ERROR", "Input Error", "Image Input Error", e.getMessage(), true);
            }
            Image rImage = image.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            image = new ImageIcon(rImage);

            String availability = v.getAvailability() ? "AVAILABLE" : "UNAVAILABLE";
            String rentPrice = "RM" + v.getRentalPriceDay() + "/per day";

            vehicleCards.add(createCarCard(v, image, v.getBrand(), v.getModel(), v.getTransmission(), v.getFuelType(),
                    v.getVehicleType(),
                    v.getSeatingCapacity(), rentPrice, availability, frame, panel));
        }

        return vehicleCards;
    }

    public JPanel createCarCard(Vehicle vehicle, ImageIcon image, String brand, String model,
            String transmission,
            String fuelType,
            String vehicleType, int seats, String price, String availability, JFrame frame, JPanel panel) {

        JPanel carCard = new JPanel();
        carCard.setLayout(new BorderLayout());
        carCard.setBackground(Theme.getBackground());
        carCard.setPreferredSize(new Dimension(350, 450));
        carCard.setBorder(BorderFactory.createLineBorder(Theme.getBackground(), 3, true));

        // create buttons on the bottom
        RoundedButton carDetails = new RoundedButton(10, Theme.getBackground());
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
        // if is admin
        if (user.getUserType().equals("Admin")) {
            Admin admin = UserController.getAdminFromDatabase(user);
            if (admin.getAdminRole().equals("Manager")) {
                carRent.setEnabled(true);
                carRent.setText("EDIT");
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
                        showEditCarPopup(vehicle);
                    }
                });
            }
            else {
                carRent.setEnabled(vehicle.getAvailability());
                if(vehicle.getAvailability()) {
                    carRent.setBackground(Theme.getSpecial());
                    carRent.setForeground(Theme.getSpecialForeground());
                    
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
                            JPanel rentalPanel = new RentalPage(frame, panel, vehicle);
                            GUIComponents.cardPanel.add(rentalPanel, "RentalPage");
                            GUIComponents.cardLayout.show(GUIComponents.cardPanel, "RentalPage");
                        }
                    });
                }
                else {
                    carRent.setBackground(Color.GRAY);
                    carRent.setForeground(Theme.getSpecialForeground());
                }
                
            }
        } else {
            carRent.setEnabled(vehicle.getAvailability());
            if (vehicle.getAvailability() && user != null) {
                carRent.setBackground(Theme.getSpecial());
                carRent.setForeground(Theme.getSpecialForeground());

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
                        File accountsFile = new File("files/settings/accounts.txt");
                        user = UserController.loadCurrentUser(accountsFile);
                        if (user.getUserType().equals("Guest") || user.getUserType().equals("Admin")) {
                            // Display dialog
                            System.err.println("Please Sign In");
                            return;
                        }
                        JPanel rentalPanel = new RentalPage(frame, panel, vehicle);
                        GUIComponents.cardPanel.add(rentalPanel, "RentalPage");
                        GUIComponents.cardLayout.show(GUIComponents.cardPanel, "RentalPage");
                    }
                });
            } else {
                carRent.setBackground(Color.GRAY);
                carRent.setForeground(Theme.getSpecialForeground());
            }
        }

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

        JLabel carTypeLabel = new JLabel(vehicleType.substring(0,1).toUpperCase() + vehicleType.substring(1));
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
        seatsLabel.setIcon(IconLoader.getSeatIcon());
        seatsLabel.setHorizontalTextPosition(JLabel.RIGHT);
        seatsLabel.setOpaque(true);
        seatsLabel.setBackground(Theme.getBackground());
        seatsLabel.setForeground(Theme.getForeground());
        // seatsLabel.setPreferredSize(new Dimension(0,35));
        seatsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        seatsLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel fuelTypeLabel = new JLabel(fuelType);
        fuelTypeLabel.setIcon(IconLoader.getGasIcon());
        fuelTypeLabel.setHorizontalTextPosition(JLabel.RIGHT);
        fuelTypeLabel.setBackground(Theme.getBackground());
        fuelTypeLabel.setOpaque(true);
        fuelTypeLabel.setForeground(Theme.getForeground());
        // fuelTypeLabel.setPreferredSize(new Dimension(0,35));
        fuelTypeLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        fuelTypeLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel transLabel = new JLabel(transmission);
        transLabel.setIcon(IconLoader.getTransmissionIcon());
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
                    JPanel detailsPanel = new VehiclesPageDetails(frame, panel, vehicle, vehicles);
                    GUIComponents.cardPanel.add(detailsPanel, "VehicleDetailsPage");
                    GUIComponents.cardLayout.show(GUIComponents.cardPanel, "VehicleDetailsPage");
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    carPicture.setIcon(toGreyScale(image)); // Set greyscale image
                    if (availability.equals("AVAILABLE")) {
                        carAvailability.setBackground(Theme.getPressedSpecial());
                    } else {
                        carAvailability.setBackground(Theme.getError());
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    carPicture.setIcon(image); // Restore original image
                    if (availability.equals("AVAILABLE")) {
                        carAvailability.setBackground(Theme.getSpecial());
                    } else {
                        carAvailability.setBackground(Theme.getError());
                    }
                    transLabel.setIcon(IconLoader.getTransmissionIcon());
                    fuelTypeLabel.setIcon(IconLoader.getGasIcon());
                    seatsLabel.setIcon(IconLoader.getSeatIcon());
                }
            }
        });
        // if is admin
        if (user.getUserType().equals("Admin")) {
            Admin admin = UserController.getAdminFromDatabase(user);
            if (admin.getAdminRole().equals("Manager")) {
                carDetails.setText("DELETE");
                carDetails.setForeground(Theme.getErrorForeground());
                carDetails.setBackground(Theme.getError());
                carDetails.setOpaque(true);

                carDetails.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent evt) {
                        carDetails.setBackground(Theme.getHoverError());
                    }

                    @Override
                    public void mouseExited(MouseEvent evt) {
                        carDetails.setBackground(Theme.getError());
                    }

                    @Override
                    public void mousePressed(MouseEvent evt) {
                        carDetails.setBackground(Theme.getPressedError());
                    }

                    @Override
                    public void mouseReleased(MouseEvent evt) {
                        carDetails.setBackground(Theme.getError());
                        if (dialog.showDialog("HAZARD", "Vehicle Deletion", "Delete Vehicle",
                                "Are you sure you want to delete this vehicle?", true)) {
                            VehicleController.processDeleteVehiclefromDAO(vehicle);
                            File imageFile = new File(vehicle.getImagePath());
                            imageFile.delete();
                            vehicles = new ArrayList<>(VehicleController.processVehicles());
                            sortedVehicles = vehicles;
                            for (int i = 0; i < 4; i++) {
                                vehicless[i] = vehicles.get(i);
                            }
                            goBackToVehiclePage();
                        }
                    }
                });
            }
            else {
                carDetails.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Check if the click is on the image
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            JPanel detailsPanel = new VehiclesPageDetails(frame, panel, vehicle, vehicles);
                            GUIComponents.cardPanel.add(detailsPanel, "VehicleDetailsPage");
                            GUIComponents.cardLayout.show(GUIComponents.cardPanel, "VehicleDetailsPage");
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
                            if (availability.equals("AVAILABLE")) {
                                carAvailability.setBackground(Theme.getSpecial());
                            } else {
                                carAvailability.setBackground(Theme.getError());
                            }
                            transLabel.setIcon(IconLoader.getTransmissionIcon());
                            fuelTypeLabel.setIcon(IconLoader.getGasIcon());
                            seatsLabel.setIcon(IconLoader.getSeatIcon());
                        }
                    }
                });
            }
        } else {
            carDetails.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Check if the click is on the image
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        JPanel detailsPanel = new VehiclesPageDetails(frame, panel, vehicle, vehicles);
                        GUIComponents.cardPanel.add(detailsPanel, "VehicleDetailsPage");
                        GUIComponents.cardLayout.show(GUIComponents.cardPanel, "VehicleDetailsPage");
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
                        if (availability.equals("AVAILABLE")) {
                            carAvailability.setBackground(Theme.getSpecial());
                        } else {
                            carAvailability.setBackground(Theme.getError());
                        }
                        transLabel.setIcon(IconLoader.getTransmissionIcon());
                        fuelTypeLabel.setIcon(IconLoader.getGasIcon());
                        seatsLabel.setIcon(IconLoader.getSeatIcon());
                    }
                }
            });
        }
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
    private RoundedButton carButton;
    private RoundedButton bikeButton;
    private RoundedButton allButton;
    private JTextField searchBar = new JTextField("Search for vehicles");

    private JPanel createCarTopBar() {

        JPanel topBar = new JPanel();
        topBar.setLayout(null);
        topBar.setBackground(Theme.getBackground());
        topBar.setPreferredSize(new Dimension(1600, 100));

        JLabel filters = new JLabel("Filter");
        filters.setForeground(Theme.getForeground());
        filters.setBounds(50, 25, 200, 50);
        filters.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));

        // if car then array of vehicles if bike then array of bikes else array of
        // vehicles
        carButton = new RoundedButton(10, Theme.getHoverBackground());
        carButton.setIcon(IconLoader.getCarIcon());
        carButton.setBorderPainted(false);
        carButton.setBounds(375, 25, 65, 50);
        carButton.setFocusable(false);
        carButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));
        carButton.addActionListener(this);
        carButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                carButton.setBackground(Theme.getPressedBackground());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                carButton.setBackground(Theme.getHoverBackground());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                carButton.setBackground(Theme.getBackground());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                carButton.setBackground(Theme.getHoverBackground());
            }
        });

        bikeButton = new RoundedButton(10, Theme.getHoverBackground());
        bikeButton.setIcon(IconLoader.getBikeIcon());
        bikeButton.setBorderPainted(false);
        bikeButton.setBounds(450, 25, 65, 50);
        bikeButton.setFocusable(false);
        bikeButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));
        bikeButton.addActionListener(this);
        bikeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                bikeButton.setBackground(Theme.getPressedBackground());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                bikeButton.setBackground(Theme.getHoverBackground());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                bikeButton.setBackground(Theme.getBackground());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                bikeButton.setBackground(Theme.getHoverBackground());
            }
        });

        allButton = new RoundedButton(10, Theme.getHoverBackground());
        allButton.setText("ALL");
        allButton.setOpaque(true);
        allButton.setBorderPainted(false);
        allButton.setBounds(300, 25, 65, 50);
        allButton.setFocusable(false);
        allButton.setForeground(Theme.getForeground());
        allButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));
        allButton.addActionListener(this);
        allButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                allButton.setBackground(Theme.getPressedBackground());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                allButton.setBackground(Theme.getHoverBackground());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                allButton.setBackground(Theme.getBackground());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                allButton.setBackground(Theme.getHoverBackground());
            }
        });

        searchBar.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));
        searchBar.setForeground(Theme.getSecondaryForeground());
        searchBar.setBorder(
                new CompoundBorder(new LineBorder(Theme.getForeground(), 1), new EmptyBorder(10, 15, 10, 5)));
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
        searchBar.addActionListener(e -> applyFilters());

        JButton searchButton = new JButton();
        searchButton.setIcon(IconLoader.getSearchIcon());
        searchButton.setBounds(1200, 25, 85, 50);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));
        searchButton.setBorder(BorderFactory.createLineBorder(Theme.getTransparencyColor(), 1));
        searchButton.addActionListener(e -> applyFilters());

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
        ImageIcon addIcon = IconLoader.getAddIcon();

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
        if (user.getUserType().equals("Admin")) {
            Admin admin = UserController.getAdminFromDatabase(user);
            if (admin.getAdminRole().equals("Manager")) {
                addButton.setVisible(true);
            }
            else {
                addButton.setVisible(false);
            }
        } else {
            addButton.setVisible(false);
        }

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
    private List<String> brandsList = new ArrayList<>();;
    private String[] brands;
    private JComboBox<String> modelComboBox;
    private String[] models;
    private String[] year;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> carTypeComboBox;
    private JLabel carTypeLabel;
    private String[] vehicleType = { "ALL", "suv", "mpv", "sedan", "convertible", "coupe", "pickup truck", "hatchback",
            "wagon", "touring", "cruiser", "superbike" };
    private JComboBox<String> transTypeComboBox;
    private JComboBox<String> fuelTypeComboBox;
    private JComboBox<String> availabilityComboBox;
    String[] transType = { "ALL", "MANUAL", "AUTO" };
    String[] fuelType = { "ALL", "PETROL", "HYBRID", "ELECTRIC" };
    String[] availability = { "ALL", "AVAILABLE", "UNAVAILABLE" };
    private JSlider seatSlider;
    private JTextField minPriceField;
    private JTextField maxPriceField;

    private JPanel createCarLeftPanel() {
        // sample data
        // in future loop through every car brands and model and list them through the
        // filter + "ALL"
        // when user pick a brand filter model will update and only show few models
        // follow the brand same as cartype
        brandsList.add("ALL");
        brandsList.addAll(VehicleController.processAllBrands(vehicles));

        List<String> modelsList = new ArrayList<>();
        modelsList.add("ALL");

        brands = brandsList.toArray(new String[0]);
        models = modelsList.toArray(new String[0]);

        year = new String[76];
        int newestYear = 2025;
        for (int i = 75; i >= 0; i--) {
            year[i] = Integer.toString(newestYear - i);
        }

        JLabel brandLabel = new JLabel("Select Brand");
        brandLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        brandLabel.setForeground(Theme.getForeground());

        // UIManager.put("ComboBox.background", Theme.getBackground());
        // UIManager.put("ComboBox.foreground", Theme.getForeground());
        // UIManager.put("ComboBox.selectionBackground", Theme.getHoverBackground());
        // UIManager.put("ComboBox.selectionForeground", Theme.getForeground());
        // UIManager.put("ComboBox.buttonBackground", Theme.getBackground());
        // UIManager.put("ComboBox.buttonShadow", Theme.getSpecial());

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

        carTypeLabel = new JLabel("Select Car or Bike Type");
        carTypeLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        carTypeLabel.setForeground(Theme.getForeground());

        carTypeComboBox = new JComboBox<>(vehicleType);
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
        minPriceField.addActionListener(e -> applyFilters());
        maxPriceField.addActionListener(e -> applyFilters());

        seatSlider.addChangeListener(e -> {
            int value = seatSlider.getValue();
            if (value == 0) {
                seatLabel.setText("Seats: ALL");
            } else {
                seatLabel.setText("Seats: " + value);
            }
            applyFilters();
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
        if (e.getSource() == bikeButton) {
            this.vehicles = new ArrayList<>(VehicleController.processBikes());
            this.sortedVehicles = this.vehicles;
            carTypeLabel.setText("Select A Bike Type: ");
            vehicleType = new String[] { "ALL", "touring", "cruiser", "superbike" };
            carTypeComboBox.removeAllItems();
            for (String type : vehicleType) {
                carTypeComboBox.addItem(type);
            }
        } else if (e.getSource() == carButton) {
            this.vehicles = new ArrayList<>(VehicleController.processCars());
            this.sortedVehicles = this.vehicles;
            carTypeLabel.setText("Select A Car Type: ");
            vehicleType = new String[] { "ALL", "suv", "mpv", "sedan", "convertible", "coupe", "pickup truck",
                    "hatchback", "wagon" };
            carTypeComboBox.removeAllItems();
            for (String type : vehicleType) {
                carTypeComboBox.addItem(type);
            }
        } else if (e.getSource() == allButton) {
            this.vehicles = new ArrayList<>(VehicleController.processVehicles());
            this.sortedVehicles = this.vehicles;
            carTypeLabel.setText("Select A Car or Bike Type: ");
            vehicleType = new String[] { "ALL", "suv", "mpv", "sedan", "comvertible", "coupe", "pickup truck",
                    "hatchback", "wagon", "touring", "cruiser", "superbike" };
            carTypeComboBox.removeAllItems();
            for (String type : vehicleType) {
                carTypeComboBox.addItem(type);
            }
        }
        if (e.getSource() == sortComboBox) {
            handleSorting();
            applyFilters();
        } else if (e.getSource() == brandComboBox) {
            handleBrandChange();
            applyFilters();
        } else if (e.getSource() == modelComboBox ||
                e.getSource() == yearComboBox ||
                e.getSource() == carTypeComboBox ||
                e.getSource() == transTypeComboBox ||
                e.getSource() == fuelTypeComboBox ||
                e.getSource() == availabilityComboBox ||
                e.getSource() == minPriceField ||
                e.getSource() == maxPriceField ||
                e.getSource() == searchBar) {
            applyFilters();
        }
    }

    private void handleSorting() {
        if (" Best Match ".equals(sortComboBox.getSelectedItem())) {
            this.sortedVehicles = this.vehicles;
        } else if (" Lowest Price ".equals(sortComboBox.getSelectedItem())) {
            this.sortedVehicles = VehicleController.processSortedByPriceLowToHigh(this.vehicles);
        } else if (" Highest Price ".equals(sortComboBox.getSelectedItem())) {
            this.sortedVehicles = VehicleController.processSortedByPriceHighToLow(this.vehicles);
        } else if (" Newest ".equals(sortComboBox.getSelectedItem())) {
            this.sortedVehicles = VehicleController.processSortedByYearNewestFirst(this.vehicles);
        } else if (" Oldest ".equals(sortComboBox.getSelectedItem())) {
            this.sortedVehicles = VehicleController.processSortedByYearOldestFirst(this.vehicles);
        }
    }

    private void handleBrandChange() {
        if (brandComboBox.getSelectedIndex() != 0) {
            List<String> modelsList = new ArrayList<>();
            modelsList.add("ALL");
            modelsList.addAll(
                    VehicleController.processAllModelsByBrand(vehicles, brandComboBox.getSelectedItem().toString()));
            String[] models = modelsList.toArray(new String[0]);

            // Store current selection if any
            String previouslySelectedModel = modelComboBox.isEnabled() ? (String) modelComboBox.getSelectedItem()
                    : null;

            modelComboBox.setModel(new DefaultComboBoxModel<>(models));
            modelComboBox.setEnabled(true);

            // Restore previous selection if it exists in the new model list
            if (previouslySelectedModel != null && modelsList.contains(previouslySelectedModel)) {
                modelComboBox.setSelectedItem(previouslySelectedModel);
            }
        } else {
            modelComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "ALL" }));
            modelComboBox.setEnabled(false);
        }
    }

    private void applyFilters() {
        refreshCards(VehicleController.processAllFilterCombination(
                this.sortedVehicles,
                (String) brandComboBox.getSelectedItem(),
                (String) modelComboBox.getSelectedItem(),
                yearComboBox.getSelectedItem(),
                (String) fuelTypeComboBox.getSelectedItem(),
                (String) transTypeComboBox.getSelectedItem(),
                (String) availabilityComboBox.getSelectedItem(),
                (String) carTypeComboBox.getSelectedItem(),
                seatSlider.getValue(),
                minPriceField.getText(),
                maxPriceField.getText(),
                (String) searchBar.getText()));
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

            String availability = c.getAvailability() ? "AVAILABLE" : "UNAVAILABLE";
            String rentPrice = "RM" + c.getRentalPriceDay() + "/per day";

            vehicleCards.add(createCarCard(c, image, c.getBrand(), c.getModel(),
                    c.getTransmission(), c.getFuelType(), c.getVehicleType(),
                    c.getSeatingCapacity(), rentPrice, availability, frame, panel));
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

    private void updateFilterDropdowns() {
        // Update Brands dropdown
        List<String> updatedBrands = new ArrayList<>();
        updatedBrands.add("ALL");
        updatedBrands.addAll(VehicleController.processAllBrands(vehicles));
        brandComboBox.setModel(new DefaultComboBoxModel<>(updatedBrands.toArray(new String[0])));

        // Reset Models dropdown based on selected brand
        if (brandComboBox.getSelectedIndex() > 0) {
            List<String> modelsList = new ArrayList<>();
            modelsList.add("ALL");
            modelsList.addAll(VehicleController.processAllModelsByBrand(
                    vehicles,
                    brandComboBox.getSelectedItem().toString()));
            modelComboBox.setModel(new DefaultComboBoxModel<>(modelsList.toArray(new String[0])));
            modelComboBox.setEnabled(true);
        } else {
            modelComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "ALL" }));
            modelComboBox.setEnabled(false);
        }
        applyFilters();
    }

    private JPanel createCarRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 900));
        rightPanel.setBackground(Theme.getBackground());

        return rightPanel;
    }

    private JPanel createBottomBar() {
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Theme.getBackground());
        bottomBar.setPreferredSize(new Dimension(800, 100));

        return bottomBar;
    }

    public static ImageIcon toGreyScale(ImageIcon icon) {
        if (icon == null) {
            throw new IllegalArgumentException("ImageIcon cannot be null");
        }

        // Get the image from the ImageIcon
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid image dimensions: " + width + "x" + height);
        }

        // Convert the Image to a BufferedImage
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Convert the BufferedImage to greyscale
        BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        BufferedImage greyImage = op.filter(bufferedImage, null);

        RescaleOp rescaleOp = new RescaleOp(1.0f, 20, null); // Adjust brightness (offset = 20)
        BufferedImage brighterGreyImage = rescaleOp.filter(greyImage, null);

        // Return the greyscale image as an ImageIcon
        return new ImageIcon(brighterGreyImage);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public static User getUser() {
        return user;
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

    private JScrollPane informationPanel;

    private void showAddCarPopup() {
        // Create the dialog
        JDialog addCar = new JDialog(frame, "Add New Vehicle", true); // true for modal
        addCar.setPreferredSize(new Dimension(1600, 900));
        addCar.setLayout(new BorderLayout());
        addCar.setBackground(Theme.getBackground());

        JLabel title = new JLabel("Add New Vehicle", JLabel.CENTER);
        title.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        title.setBackground(Theme.getBackground());
        title.setForeground(Theme.getForeground());
        title.setOpaque(true);
        addCar.add(title, BorderLayout.NORTH);

        // Add form components
        // need to use jscrollpane
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 1, 1));
        formPanel.setBackground(Theme.getBackground());

        // left side
        JPanel picturePanel = new JPanel(new BorderLayout());
        JLabel carUploadImage = new JLabel("Vehicle Image: ");
        picturePanel.add(carUploadImage);
        picturePanel.add(initImageUploader(""));
        formPanel.add(picturePanel);

        // right side
        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setPreferredSize(new Dimension(600, 900));

        informationPanel = new JScrollPane(rightContainer);
        informationPanel.setBorder(BorderFactory.createEmptyBorder());
        informationPanel.getVerticalScrollBar().setUnitIncrement(30);
        informationPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        informationPanel.setBackground(Theme.getBackground());
        informationPanel.getViewport().setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = rightContainer.getWidth();
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 5, 10, 5);

        rightContainer.setBackground(Theme.getBackground());
        rightContainer.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));

        rightContainer.setPreferredSize(null);
        rightContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        rightContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        rightContainer.add(createVehicleSelectionInputContainer("CAR"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightContainer.add(createAvailableInputContainer(true), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        rightContainer.add(createVinNumberInputContainer(""), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        rightContainer.add(createRegistrationNumberInputContainer(""), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        rightContainer.add(createBrandInputContainer(""), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        rightContainer.add(createModelInputContainer(""), gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        rightContainer.add(createYearInputContainer("Select Year"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightContainer.add(createPriceInputContainer(""), gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        rightContainer.add(createColorInputContainer(Color.WHITE, "Pick A Color"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        rightContainer.add(createCapacityInputContainer(""), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        rightContainer.add(createHorsepowerInputContainer(""), gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        rightContainer.add(createMpgInputContainer(""), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        rightContainer.add(createTransmissionInputContainer("Select Transmission"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        rightContainer.add(createFuelTypeInputContainer("Select Fuel Type"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        rightContainer.add(createVehicleTypeInputContainer("Select Vehicle Type"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        rightContainer.add(createSeatInputContainer(1), gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        rightContainer.add(createInputContainer("Features", 3, ""), gbc);

        formPanel.add(informationPanel);

        addCar.add(formPanel, BorderLayout.CENTER);

        // Add submit button
        RoundedButton submitButton = new RoundedButton(20, Theme.getSpecial());
        submitButton.setText("Submit");
        submitButton.setForeground(Theme.getSpecialForeground());
        submitButton.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        submitButton.setFocusable(false);
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
        submitButton.addActionListener(this::handleSubmit);
        addCar.add(submitButton, BorderLayout.SOUTH);

        // Finalize and show the dialog
        addCar.pack();
        addCar.setLocationRelativeTo(frame); // Center relative to parent frame
        addCar.setVisible(true);
    }

    private void showEditCarPopup(Vehicle vehicle) {
        // Create the dialog
        JDialog editCar = new JDialog(frame, "Edit Vehicle", true); // true for modal
        editCar.setPreferredSize(new Dimension(1600, 900));
        editCar.setLayout(new BorderLayout());
        editCar.setBackground(Theme.getBackground());

        JLabel title = new JLabel("Edit Vehicle", JLabel.CENTER);
        title.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        title.setBackground(Theme.getBackground());
        title.setForeground(Theme.getForeground());
        title.setOpaque(true);
        editCar.add(title, BorderLayout.NORTH);

        // Add form components
        // need to use jscrollpane
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 1, 1));
        formPanel.setBackground(Theme.getBackground());

        // left side
        JPanel picturePanel = new JPanel(new BorderLayout());
        JLabel carUploadImage = new JLabel("Vehicle Image: ");
        picturePanel.add(carUploadImage);
        picturePanel.add(initImageUploader(vehicle.getImagePath()));
        formPanel.add(picturePanel);

        // right side
        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setPreferredSize(new Dimension(600, 900));

        informationPanel = new JScrollPane(rightContainer);
        informationPanel.setBorder(BorderFactory.createEmptyBorder());
        informationPanel.getVerticalScrollBar().setUnitIncrement(30);
        informationPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        informationPanel.setBackground(Theme.getBackground());
        informationPanel.getViewport().setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = rightContainer.getWidth();
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 5, 10, 5);

        rightContainer.setBackground(Theme.getBackground());
        rightContainer.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));

        rightContainer.setPreferredSize(null);
        rightContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        rightContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        String vehicleType = "";

        for (String cartype : Car.getCarTypes()) {
            if (vehicle.getVehicleType().equalsIgnoreCase(cartype)) {
                vehicleType = "CAR";
                vehicleTypes = Car.getCarTypes();
                break;
            }
        }

        for (String biketype : Bike.getBikeTypes()) {
            if (vehicle.getVehicleType().equalsIgnoreCase(biketype)) {
                vehicleType = "BIKE";
                vehicleTypes = Bike.getBikeTypes();
                break;
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        rightContainer.add(createVehicleSelectionInputContainer(vehicleType), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightContainer.add(createAvailableInputContainer(vehicle.getAvailability()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        rightContainer.add(createVinNumberInputContainer(vehicle.getVinNumber()), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        rightContainer.add(createRegistrationNumberInputContainer(vehicle.getRegistrationNumber()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        rightContainer.add(createBrandInputContainer(vehicle.getBrand()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        rightContainer.add(createModelInputContainer(vehicle.getModel()), gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        rightContainer.add(createYearInputContainer(Integer.toString(vehicle.getYear())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightContainer.add(createPriceInputContainer(Double.toString(vehicle.getRentalPriceDay())), gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        rightContainer.add(createColorInputContainer(Color.decode(vehicle.getColor()),
                VehicleController.processClosestColorName(Color.decode(vehicle.getColor()))), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        rightContainer.add(createCapacityInputContainer(Integer.toString(vehicle.getCapacity())), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        rightContainer.add(createHorsepowerInputContainer(Integer.toString(vehicle.getHorsepower())), gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        rightContainer.add(createMpgInputContainer(Double.toString(vehicle.getMpg())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        rightContainer.add(createTransmissionInputContainer(vehicle.getTransmission().toUpperCase()), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        rightContainer.add(createFuelTypeInputContainer(vehicle.getFuelType().toUpperCase()), gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        rightContainer.add(createVehicleTypeInputContainer(vehicle.getVehicleType()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        rightContainer.add(createSeatInputContainer(vehicle.getSeatingCapacity()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        rightContainer.add(createInputContainer("Features", 3, vehicle.getFeatures()), gbc);

        formPanel.add(informationPanel);

        editCar.add(formPanel, BorderLayout.CENTER);

        // Add submit button
        RoundedButton submitButton = new RoundedButton(20, Theme.getSpecial());
        submitButton.setText("Submit");
        submitButton.setForeground(Theme.getSpecialForeground());
        submitButton.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        submitButton.setFocusable(false);
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
        submitButton.addActionListener(e -> handleEdit(vehicle));
        editCar.add(submitButton, BorderLayout.SOUTH);

        // Finalize and show the dialog
        editCar.pack();
        editCar.setLocationRelativeTo(frame); // Center relative to parent frame
        editCar.setVisible(true);
    }

    private RoundedButton selectCarButton, selectBikeButton;
    private RoundedButton availableButton, unavailableButton;
    private JTextField vinNumberInput, registrationNumberInput, brandInput, modelInput, priceInput, capacityInput,
            horsepowerInput, mpgInput;
    private JComboBox<String> yearInput, transmissionInput, fuelTypeInput, vehicleTypeInput;
    private JSlider seatInput;
    private JTextArea inputField;
    private RoundedButton colorInput;
    private Color selectedColor;
    private String selectedVehicle;
    private boolean availabilitySelection;
    private String[] vehicleTypes = vehicleType;

    private JPanel createVehicleSelectionInputContainer(String input) {
        JPanel vehicleSelectionInputPanel = new JPanel(new GridBagLayout());
        vehicleSelectionInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel vehicleSelectionLabel = new JLabel("Vehicle");
        vehicleSelectionLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        vehicleSelectionLabel.setForeground(Theme.getForeground());

        selectCarButton = new RoundedButton(10, Theme.getHoverBackground());
        selectCarButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        selectCarButton.setPreferredSize(new Dimension(100, 50));
        selectCarButton.setMinimumSize(new Dimension(100, 50));
        selectCarButton.setForeground(Theme.getForeground());
        selectCarButton.setIcon(IconLoader.getCarIcon());
        selectCarButton.setBorderPainted(false);
        selectCarButton.setFocusable(false);
        selectCarButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent evt) {
                selectCarButton.setBackground(Theme.getBackground());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                selectCarButton.setBackground(Theme.getHoverBackground());
                selectBikeButton.setBackground(Theme.getBackground());
                selectedVehicle = "CAR";
                updateVehicleTypeDropdown(Car.getCarTypes());
            }
        });

        selectBikeButton = new RoundedButton(10, Theme.getHoverBackground());
        selectBikeButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        selectBikeButton.setPreferredSize(new Dimension(100, 50));
        selectBikeButton.setMinimumSize(new Dimension(100, 50));
        selectBikeButton.setForeground(Theme.getForeground());
        selectBikeButton.setIcon(IconLoader.getBikeIcon());
        selectBikeButton.setBorderPainted(false);
        selectBikeButton.setFocusable(false);
        selectBikeButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent evt) {
                selectBikeButton.setBackground(Theme.getBackground());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                selectBikeButton.setBackground(Theme.getHoverBackground());
                selectCarButton.setBackground(Theme.getBackground());
                selectedVehicle = "BIKE";
                updateVehicleTypeDropdown(Bike.getBikeTypes());
            }
        });

        if (input.equals("CAR")) {
            selectBikeButton.setBackgroundColor(Theme.getBackground());
            selectCarButton.setBackground(Theme.getHoverBackground());
            selectedVehicle = "CAR";
        } else if (input.equals("BIKE")) {
            selectBikeButton.setBackgroundColor(Theme.getHoverBackground());
            selectCarButton.setBackground(Theme.getBackground());
            selectedVehicle = "BIKE";
        } else {
            selectBikeButton.setBackgroundColor(Theme.getBackground());
            selectCarButton.setBackground(Theme.getHoverBackground());
            selectedVehicle = "CAR";
        }

        vehicleSelectionInputPanel.add(vehicleSelectionLabel, gbc);
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        vehicleSelectionInputPanel.add(selectCarButton, gbc);
        gbc.gridx++;
        vehicleSelectionInputPanel.add(selectBikeButton, gbc);

        return vehicleSelectionInputPanel;
    }

    private void updateVehicleTypeDropdown(String[] types) {
        vehicleTypes = types;
        if (vehicleTypeInput != null) {
            vehicleTypeInput.setModel(new DefaultComboBoxModel<>(types));
            vehicleTypeInput.insertItemAt("Select Vehicle Type", 0);
            vehicleTypeInput.setSelectedIndex(0);
        }
    }

    private JPanel createAvailableInputContainer(boolean availability) {
        JPanel availabilityInputPanel = new JPanel(new GridBagLayout());
        availabilityInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel availabilityLabel = new JLabel("Availability");
        availabilityLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        availabilityLabel.setForeground(Theme.getForeground());

        availableButton = new RoundedButton(10, Theme.getHoverBackground());
        availableButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        availableButton.setPreferredSize(new Dimension(200, 50));
        availableButton.setMinimumSize(new Dimension(200, 50));
        availableButton.setForeground(Theme.getForeground());
        availableButton.setText("AVAILABLE");
        availableButton.setBorderPainted(false);
        availableButton.setFocusable(false);
        availableButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent evt) {
                availableButton.setBackground(Theme.getHoverBackground());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                availableButton.setBackground(Theme.getHoverBackground());
                unavailableButton.setBackground(Theme.getBackground());
                availabilitySelection = true;
            }
        });

        unavailableButton = new RoundedButton(10, Theme.getHoverBackground());
        unavailableButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        unavailableButton.setPreferredSize(new Dimension(200, 50));
        unavailableButton.setMinimumSize(new Dimension(200, 50));
        unavailableButton.setForeground(Theme.getForeground());
        unavailableButton.setText("UNAVAILABLE");
        unavailableButton.setBorderPainted(false);
        unavailableButton.setFocusable(false);
        unavailableButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent evt) {
                unavailableButton.setBackground(Theme.getHoverBackground());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                unavailableButton.setBackground(Theme.getHoverBackground());
                availableButton.setBackground(Theme.getBackground());
                availabilitySelection = false;
            }
        });

        if (availability) {
            unavailableButton.setBackgroundColor(Theme.getBackground());
            availableButton.setBackground(Theme.getHoverBackground());
            availabilitySelection = true;
        } else {
            unavailableButton.setBackgroundColor(Theme.getHoverBackground());
            availableButton.setBackground(Theme.getBackground());
            availabilitySelection = false;
        }

        availabilityInputPanel.add(availabilityLabel, gbc);
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        availabilityInputPanel.add(availableButton, gbc);
        gbc.gridx++;
        availabilityInputPanel.add(unavailableButton, gbc);

        return availabilityInputPanel;
    }

    private JPanel createVinNumberInputContainer(String input) {
        JPanel vinNumberInputPanel = new JPanel(new GridBagLayout());
        vinNumberInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = vinNumberInputPanel.getWidth();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel vinNumberLabel = new JLabel("VIN Number");
        vinNumberLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        vinNumberLabel.setForeground(Theme.getForeground());

        vinNumberInput = new JTextField(input);
        vinNumberInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        vinNumberInput.setPreferredSize(new Dimension(400, 50));
        vinNumberInput.setMinimumSize(new Dimension(400, 50));
        vinNumberInput.setForeground(Color.BLACK);
        vinNumberInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));

        vinNumberInputPanel.add(vinNumberLabel, gbc);
        vinNumberInputPanel.add(vinNumberInput, gbc);

        return vinNumberInputPanel;
    }

    private JPanel createRegistrationNumberInputContainer(String input) {
        JPanel registrationNumberInputPanel = new JPanel(new GridBagLayout());
        registrationNumberInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = registrationNumberInputPanel.getWidth();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel registrationNumberLabel = new JLabel("Registration Number");
        registrationNumberLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        registrationNumberLabel.setForeground(Theme.getForeground());

        registrationNumberInput = new JTextField(input);
        registrationNumberInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        registrationNumberInput.setPreferredSize(new Dimension(200, 50));
        registrationNumberInput.setMinimumSize(new Dimension(200, 50));
        registrationNumberInput.setForeground(Color.BLACK);
        registrationNumberInput
                .setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));

        registrationNumberInputPanel.add(registrationNumberLabel, gbc);
        registrationNumberInputPanel.add(registrationNumberInput, gbc);

        return registrationNumberInputPanel;
    }

    private JPanel createBrandInputContainer(String input) {
        JPanel brandInputPanel = new JPanel(new GridBagLayout());
        brandInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = brandInputPanel.getWidth();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel brandLabel = new JLabel("Brand");
        brandLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        brandLabel.setForeground(Theme.getForeground());

        brandInput = new JTextField(input);
        brandInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        brandInput.setPreferredSize(new Dimension(600, 50));
        brandInput.setMinimumSize(new Dimension(600, 50));
        brandInput.setForeground(Color.BLACK);
        brandInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));

        brandInputPanel.add(brandLabel, gbc);
        brandInputPanel.add(brandInput, gbc);

        return brandInputPanel;
    }

    private JPanel createModelInputContainer(String input) {
        JPanel modelInputPanel = new JPanel(new GridBagLayout());
        modelInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = modelInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel modelLabel = new JLabel("Model");
        modelLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        modelLabel.setForeground(Theme.getForeground());

        modelInput = new JTextField(input);
        modelInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        modelInput.setPreferredSize(new Dimension(400, 50));
        modelInput.setMinimumSize(new Dimension(400, 50));
        modelInput.setForeground(Color.BLACK);
        modelInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));

        modelInputPanel.add(modelLabel, gbc);
        modelInputPanel.add(modelInput, gbc);

        return modelInputPanel;
    }

    private JPanel createYearInputContainer(String selectItem) {
        JPanel yearInputPanel = new JPanel(new GridBagLayout());
        yearInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = yearInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel yearLabel = new JLabel("Year");
        yearLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        yearLabel.setForeground(Theme.getForeground());

        yearInput = new JComboBox<String>(year);
        yearInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        yearInput.insertItemAt("Select Year", 0);
        yearInput.setPreferredSize(new Dimension(200, 50));
        yearInput.setMinimumSize(new Dimension(200, 50));
        yearInput.setForeground(Color.BLACK);
        yearInput.setBorder(new LineBorder(Color.BLACK, 2));
        yearInput.setSelectedItem(selectItem);

        yearInputPanel.add(yearLabel, gbc);
        yearInputPanel.add(yearInput, gbc);

        return yearInputPanel;
    }

    private JPanel createPriceInputContainer(String input) {
        JPanel priceInputPanel = new JPanel(new GridBagLayout());
        priceInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = priceInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel priceLabel = new JLabel("Price");
        priceLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        priceLabel.setForeground(Theme.getForeground());

        priceInput = new JTextField(input);
        priceInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        priceInput.setPreferredSize(new Dimension(400, 50));
        priceInput.setMinimumSize(new Dimension(400, 50));
        priceInput.setForeground(Color.BLACK);
        priceInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));

        priceInputPanel.add(priceLabel, gbc);
        priceInputPanel.add(priceInput, gbc);

        return priceInputPanel;
    }

    private JPanel createColorInputContainer(Color color, String input) {
        JPanel colorInputPanel = new JPanel(new GridBagLayout());
        colorInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = colorInputPanel.getWidth();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel colorLabel = new JLabel("Color");
        colorLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        colorLabel.setForeground(Theme.getForeground());

        colorInput = new RoundedButton(0, color);
        colorInput.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        colorInput.setPreferredSize(new Dimension(200, 50));
        colorInput.setMinimumSize(new Dimension(200, 50));
        colorInput.setForeground(Color.BLACK);
        colorInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));
        colorInput.setText(input);
        colorInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open color picker
                if (e.getSource() == colorInput) {

                    selectedColor = JColorChooser.showDialog(null, "Choose A Color", color);
                    colorInput.setBackground(selectedColor);
                    colorInput.setText(VehicleController.processClosestColorName(selectedColor));
                }
            }
        });

        colorInputPanel.add(colorLabel, gbc);
        colorInputPanel.add(colorInput, gbc);

        return colorInputPanel;
    }

    private JPanel createTransmissionInputContainer(String selectedItem) {
        JPanel transmissionInputPanel = new JPanel(new GridBagLayout());
        transmissionInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = transmissionInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel transmissionLabel = new JLabel("Transmission");
        transmissionLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        transmissionLabel.setForeground(Theme.getForeground());

        transmissionInput = new JComboBox<String>(transType);
        transmissionInput.removeItem("ALL");
        transmissionInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        transmissionInput.insertItemAt("Select Transmission", 0);
        transmissionInput.setPreferredSize(new Dimension(200, 50));
        transmissionInput.setMinimumSize(new Dimension(200, 50));
        transmissionInput.setForeground(Color.BLACK);
        transmissionInput.setBorder(new LineBorder(Color.BLACK, 2));
        if (selectedItem.contains("AUTO")) {
            selectedItem = "AUTO";
        } else if (selectedItem.contains("MANUAL")) {
            selectedItem = "MANUAL";
        }
        transmissionInput.setSelectedItem(selectedItem);

        transmissionInputPanel.add(transmissionLabel, gbc);
        transmissionInputPanel.add(transmissionInput, gbc);

        return transmissionInputPanel;
    }

    private JPanel createFuelTypeInputContainer(String selectedItem) {
        JPanel fuelTypeInputPanel = new JPanel(new GridBagLayout());
        fuelTypeInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = fuelTypeInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel fuelTypeLabel = new JLabel("Fuel Type");
        fuelTypeLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        fuelTypeLabel.setForeground(Theme.getForeground());

        fuelTypeInput = new JComboBox<String>(fuelType);
        fuelTypeInput.removeItem("ALL");
        fuelTypeInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        fuelTypeInput.insertItemAt("Select Fuel Type", 0);
        fuelTypeInput.setPreferredSize(new Dimension(200, 50));
        fuelTypeInput.setMinimumSize(new Dimension(200, 50));
        fuelTypeInput.setForeground(Color.BLACK);
        fuelTypeInput.setBorder(new LineBorder(Color.BLACK, 2));
        fuelTypeInput.setSelectedItem(selectedItem);

        fuelTypeInputPanel.add(fuelTypeLabel, gbc);
        fuelTypeInputPanel.add(fuelTypeInput, gbc);

        return fuelTypeInputPanel;
    }

    private JPanel createVehicleTypeInputContainer(String selectedItem) {
        JPanel vehicleTypeInputPanel = new JPanel(new GridBagLayout());
        vehicleTypeInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = vehicleTypeInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
        vehicleTypeLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        vehicleTypeLabel.setForeground(Theme.getForeground());

        vehicleTypeInput = new JComboBox<String>(vehicleTypes);
        vehicleTypeInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        vehicleTypeInput.insertItemAt("Select Vehicle Type", 0);
        vehicleTypeInput.setPreferredSize(new Dimension(200, 50));
        vehicleTypeInput.setMinimumSize(new Dimension(200, 50));
        vehicleTypeInput.setForeground(Color.BLACK);
        vehicleTypeInput.setBorder(new LineBorder(Color.BLACK, 2));
        vehicleTypeInput.setSelectedItem(selectedItem);

        vehicleTypeInputPanel.add(vehicleTypeLabel, gbc);
        vehicleTypeInputPanel.add(vehicleTypeInput, gbc);

        return vehicleTypeInputPanel;
    }

    private JPanel createCapacityInputContainer(String input) {
        JPanel capacityInputPanel = new JPanel(new GridBagLayout());
        capacityInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = capacityInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel capacityLabel = new JLabel("Capacity");
        capacityLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        capacityLabel.setForeground(Theme.getForeground());

        capacityInput = new JTextField(input);
        capacityInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        capacityInput.setPreferredSize(new Dimension(200, 50));
        capacityInput.setMinimumSize(new Dimension(200, 50));
        capacityInput.setForeground(Color.BLACK);
        capacityInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));

        capacityInputPanel.add(capacityLabel, gbc);
        capacityInputPanel.add(capacityInput, gbc);

        return capacityInputPanel;
    }

    private JPanel createHorsepowerInputContainer(String input) {
        JPanel horsepowerInputPanel = new JPanel(new GridBagLayout());
        horsepowerInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = horsepowerInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel horsepowerLabel = new JLabel("Horsepower");
        horsepowerLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        horsepowerLabel.setForeground(Theme.getForeground());

        horsepowerInput = new JTextField(input);
        horsepowerInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        horsepowerInput.setPreferredSize(new Dimension(200, 50));
        horsepowerInput.setMinimumSize(new Dimension(200, 50));
        horsepowerInput.setForeground(Color.BLACK);
        horsepowerInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));

        horsepowerInputPanel.add(horsepowerLabel, gbc);
        horsepowerInputPanel.add(horsepowerInput, gbc);

        return horsepowerInputPanel;
    }

    private JPanel createMpgInputContainer(String input) {
        JPanel mpgInputPanel = new JPanel(new GridBagLayout());
        mpgInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = mpgInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel mpgLabel = new JLabel("Mpg");
        mpgLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        mpgLabel.setForeground(Theme.getForeground());

        mpgInput = new JTextField(input);
        mpgInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        mpgInput.setPreferredSize(new Dimension(200, 50));
        mpgInput.setMinimumSize(new Dimension(200, 50));
        mpgInput.setForeground(Color.BLACK);
        mpgInput.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 15, 10, 15)));

        mpgInputPanel.add(mpgLabel, gbc);
        mpgInputPanel.add(mpgInput, gbc);

        return mpgInputPanel;
    }

    private JPanel createSeatInputContainer(int setValue) {
        JPanel seatInputPanel = new JPanel(new GridBagLayout());
        seatInputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = seatInputPanel.getWidth();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel seatLabel = new JLabel("Seating Capacity");
        seatLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        seatLabel.setForeground(Theme.getForeground());

        seatInput = new JSlider(1, 7, setValue);
        seatInput.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        seatInput.setPreferredSize(new Dimension(600, 100));
        seatInput.setMinimumSize(new Dimension(600, 100));
        seatInput.setForeground(Theme.getForeground());
        seatInput.setBackground(Theme.getBackground());
        seatInput.setMajorTickSpacing(1);
        seatInput.setPaintTicks(true);
        seatInput.setPaintLabels(true);
        seatInput.setFocusable(false);

        seatInputPanel.add(seatLabel, gbc);
        seatInputPanel.add(seatInput, gbc);

        return seatInputPanel;
    }

    // create input container (from AuthenticationPage / RentalPage)
    protected JPanel createInputContainer(String title, int initialRows, String input) {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = inputPanel.getWidth();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // input title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(15f));
        titleLabel.setForeground(Theme.getForeground());
        inputPanel.add(titleLabel, gbc);

        gbc.gridy++;

        // input text area
        inputField = new JTextArea(initialRows, 20);
        inputField.setText(input);
        inputField.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(14f));
        inputField.setForeground(Color.BLACK);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // create scroll pane for text area
        JScrollPane scrollPane = new JScrollPane(inputField);
        scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // calculate height
        int rowHeight = inputField.getFontMetrics(inputField.getFont()).getHeight();
        int borderHeight = 4;
        int paddingHeight = 20;

        int fixedHeight = (initialRows * rowHeight) + borderHeight + paddingHeight;
        scrollPane.setPreferredSize(new Dimension(0, fixedHeight));

        // mouse wheel scroll listen
        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // dispatch the event to the main scroll pane
                informationPanel.dispatchEvent(e);
            }
        });

        // enable vertical scroll bar on focus, lose focus to hide
        inputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            }

            @Override
            public void focusLost(FocusEvent e) {
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            }
        });
        JComponent inputComponent = scrollPane;

        inputPanel.add(inputComponent, gbc);
        return inputPanel;
    }

    private JLabel imageLabel;
    private JButton selectButton, removeButton;
    private File selectedImageFile;
    private BufferedImage selectedImagePreview = null; // Buffered image for preview

    // In your form initialization method:
    private JPanel initImageUploader(String existingImagePath) {
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

        if (existingImagePath != null && !existingImagePath.isEmpty()) {
            try {
                selectedImageFile = new File(existingImagePath);
                selectedImagePreview = ImageIO.read(selectedImageFile);
                ImageIcon icon = new ImageIcon(
                        selectedImagePreview.getScaledInstance(500, 500, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
                imageLabel.setText("");
                removeButton.setEnabled(true);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

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
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();

            try {

                selectedImagePreview = ImageIO.read(selectedImageFile);
                if (selectedImagePreview == null) {
                    dialog.showDialog("ERROR", "Input Error", "Image Selection Error",
                            "Please select a valid image for the vehicle", true);
                    return;
                } else if (selectedImagePreview.getWidth() != selectedImagePreview.getHeight()) {
                    dialog.showDialog("ERROR", "Input Error", "Image Selection Error", "Image must be square", true);
                    return;
                }
                ImageIcon icon = new ImageIcon(
                        selectedImagePreview.getScaledInstance(500, 500, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
                imageLabel.setText("");
                removeButton.setEnabled(true);
            } catch (IOException ex) {
                dialog.showDialog("ERROR", "Input Error", "Image Loading Error", "Failed to load image", true);
            }
        }
    }

    // Handle image removal
    private void handleImageRemove(ActionEvent e) {
        selectedImageFile = null;
        selectedImagePreview = null;
        imageLabel.setIcon(null);
        removeButton.setEnabled(false);
    }

    private void handleSubmit(ActionEvent e) {
        // 1. Validate form fields here
        if (!validateFormFields()) {
            return;
        }
        boolean imageSaved = VehicleController.processImageSaving(selectedImageFile, selectedImagePreview);
        if (!imageSaved) {
            dialog.showDialog("ERROR", "Image Saving Error", "Image Saving Error", "Failed to save image", true);
            return;
        }
        if (selectedVehicle.equals("CAR")) {
            Vehicle newVehicle = new Car("images/cars/" + VehicleController.processGetImagePath(selectedImageFile),
                    brandInput.getText(), modelInput.getText(), Integer.parseInt((String) yearInput.getSelectedItem()),
                    Integer.parseInt(capacityInput.getText()), Integer.parseInt(horsepowerInput.getText()),
                    String.format("#%02x%02x%02x", selectedColor.getRed(), selectedColor.getGreen(),
                            selectedColor.getBlue()),
                    Double.parseDouble(mpgInput.getText()), vinNumberInput.getText(), registrationNumberInput.getText(),
                    Double.parseDouble(priceInput.getText()),
                    ((String) transmissionInput.getSelectedItem()).substring(0, 1)
                            + ((String) transmissionInput.getSelectedItem()).substring(1).toLowerCase(),
                    ((String) fuelTypeInput.getSelectedItem()).substring(0, 1)
                            + ((String) fuelTypeInput.getSelectedItem()).substring(1).toLowerCase(),
                    ((String) vehicleTypeInput.getSelectedItem()).toLowerCase(), (int) seatInput.getValue(),
                    availabilitySelection, inputField.getText());
            VehicleController.processAddVehiclestoDAO(newVehicle);
            dialog.showDialog("SUCCESS", "Submission Successful", "Added Successfully", "Successfully added a vehicle",
                    false);
        } else {
            Vehicle newVehicle = new Bike("images/cars/" + VehicleController.processGetImagePath(selectedImageFile),
                    brandInput.getText(), modelInput.getText(), Integer.parseInt((String) yearInput.getSelectedItem()),
                    Integer.parseInt(capacityInput.getText()), Integer.parseInt(horsepowerInput.getText()),
                    String.format("#%02x%02x%02x", selectedColor.getRed(), selectedColor.getGreen(),
                            selectedColor.getBlue()),
                    Double.parseDouble(mpgInput.getText()), vinNumberInput.getText(), registrationNumberInput.getText(),
                    Double.parseDouble(priceInput.getText()),
                    ((String) transmissionInput.getSelectedItem()).substring(0, 1)
                            + ((String) transmissionInput.getSelectedItem()).substring(1).toLowerCase(),
                    ((String) fuelTypeInput.getSelectedItem()).substring(0, 1)
                            + ((String) fuelTypeInput.getSelectedItem()).substring(1).toLowerCase(),
                    ((String) vehicleTypeInput.getSelectedItem()).toLowerCase(), (int) seatInput.getValue(),
                    availabilitySelection, inputField.getText());
            VehicleController.processAddVehiclestoDAO(newVehicle);
            dialog.showDialog("SUCCESS", "Submission Successful", "Added Successfully", "Successfully added a vehicle",
                    false);
        }

        // 2. Reset form
        resetForm();
        vehicles = new ArrayList<>(VehicleController.processVehicles());
        sortedVehicles = vehicles;
        for (int i = 0; i < 4; i++) {
            vehicless[i] = vehicles.get(i);
        }
        updateFilterDropdowns();
    }

    private void handleEdit(Vehicle vehicle) {
        // 1. Validate form fields here
        selectedColor = Color.decode(vehicle.getColor());
        if (!validateFormFields()) {
            return;
        }

        Vehicle newVehicle;

        if (selectedVehicle.equals("CAR")) {
            newVehicle = new Car(vehicle.getVehicleId(), vehicle.getImagePath(), brandInput.getText(),
                    modelInput.getText(), Integer.parseInt((String) yearInput.getSelectedItem()),
                    Integer.parseInt(capacityInput.getText()), Integer.parseInt(horsepowerInput.getText()),
                    String.format("#%02x%02x%02x", selectedColor.getRed(), selectedColor.getGreen(),
                            selectedColor.getBlue()),
                    Double.parseDouble(mpgInput.getText()), vinNumberInput.getText(), registrationNumberInput.getText(),
                    Double.parseDouble(priceInput.getText()),
                    ((String) transmissionInput.getSelectedItem()).substring(0, 1)
                            + ((String) transmissionInput.getSelectedItem()).substring(1).toLowerCase(),
                    ((String) fuelTypeInput.getSelectedItem()).substring(0, 1)
                            + ((String) fuelTypeInput.getSelectedItem()).substring(1).toLowerCase(),
                    ((String) vehicleTypeInput.getSelectedItem()).toLowerCase(), (int) seatInput.getValue(),
                    availabilitySelection, inputField.getText());
        } else {
            newVehicle = new Bike(vehicle.getVehicleId(), vehicle.getImagePath(), brandInput.getText(),
                    modelInput.getText(), Integer.parseInt((String) yearInput.getSelectedItem()),
                    Integer.parseInt(capacityInput.getText()), Integer.parseInt(horsepowerInput.getText()),
                    String.format("#%02x%02x%02x", selectedColor.getRed(), selectedColor.getGreen(),
                            selectedColor.getBlue()),
                    Double.parseDouble(mpgInput.getText()), vinNumberInput.getText(), registrationNumberInput.getText(),
                    Double.parseDouble(priceInput.getText()),
                    ((String) transmissionInput.getSelectedItem()).substring(0, 1)
                            + ((String) transmissionInput.getSelectedItem()).substring(1).toLowerCase(),
                    ((String) fuelTypeInput.getSelectedItem()).substring(0, 1)
                            + ((String) fuelTypeInput.getSelectedItem()).substring(1).toLowerCase(),
                    ((String) vehicleTypeInput.getSelectedItem()).toLowerCase(), (int) seatInput.getValue(),
                    availabilitySelection, inputField.getText());
        }

        if (selectedImageFile != null
                && !selectedImageFile.getName().equals(new File(vehicle.getImagePath()).getName())) {
            boolean imageSaved = VehicleController.processImageSaving(selectedImageFile, selectedImagePreview);
            if (!imageSaved) {
                dialog.showDialog("ERROR", "Image Saving Error", "Image Saving Error", "Failed to save image", true);
                return;
            }
            newVehicle.setImagePath("images/cars/" + VehicleController.processGetImagePath(selectedImageFile));
        }
        VehicleController.processUpdateVehiclefromDAO(newVehicle);
        dialog.showDialog("SUCCESS", "Editing Successful", "Updated Successfully", "Successfully updated the vehicle",
                false);

        vehicles = new ArrayList<>(VehicleController.processVehicles());
        sortedVehicles = vehicles;
        for (int i = 0; i < 4; i++) {
            vehicless[i] = vehicles.get(i);
        }
        goBackToVehiclePage();
    }

    private void goBackToVehiclePage() {
        // Create completely fresh VehiclesPage instance
        JPanel refreshedPage = new VehiclesPage(frame, panel, user);
        // Replace current view in card layout
        GUIComponents.cardPanel.add(refreshedPage, "VehiclesPage");
        GUIComponents.cardLayout.show(GUIComponents.cardPanel, "VehiclesPage");
    }

    private boolean validateFormFields() {
        if (!VehicleController.processImageValidation(selectedImageFile, selectedImagePreview)) {
            dialog.showDialog("ERROR", "Input Error", "Image Input Error", "Please select an image for the vehicle",
                    true);
            return false;
        }
        if (!VehicleController.processColorValidation(selectedColor)) {
            dialog.showDialog("ERROR", "Input Error", "Color Input Error", "Please select a color for the vehicle",
                    true);
            return false;
        }
        if (!VehicleController.processVinNumberValidation(vinNumberInput.getText())) {
            dialog.showDialog("ERROR", "Input Error", "VIN Number Input Error",
                    "VIN number must be 17 alphanumeric characters", true);
            return false;
        }
        if (!VehicleController.processStringsValidation(registrationNumberInput.getText(), brandInput.getText(),
                modelInput.getText())) {
            dialog.showDialog("ERROR", "Input Error", "Information Input Error",
                    "Please enter all information (except features)", true);
            return false;
        }

        if (!VehicleController.processRentalPriceDayValidation(priceInput.getText())) {
            dialog.showDialog("ERROR", "Input Error", "Price Input Error", "Please enter a valid price number", true);
            return false;
        }

        if (!VehicleController.processYearValidation((String) yearInput.getSelectedItem())) {
            dialog.showDialog("ERROR", "Input Error", "Year Input Error", "Please select a valid year", true);
            return false;
        }

        if (!VehicleController.processMpgValidation(mpgInput.getText())) {
            dialog.showDialog("ERROR", "Input Error", "MPG Input Error", "Please enter a valid mpg number", true);
            return false;
        }

        if (!VehicleController.processCapacityValidation(capacityInput.getText())) {
            dialog.showDialog("ERROR", "Input Error", "Capacity Input Error", "Please enter a valid capacity number",
                    true);
            return false;
        }

        if (!VehicleController.processHorsepowerValidation(horsepowerInput.getText())) {
            dialog.showDialog("ERROR", "Input Error", "Horsepower Input Error",
                    "Please enter a valid horsepower number", true);
            return false;
        }

        if (!VehicleController.processTransmissionValidation((String) transmissionInput.getSelectedItem())) {
            dialog.showDialog("ERROR", "Input Error", "Transmission Input Error", "Please select a transmission type",
                    true);
            return false;
        }

        if (!VehicleController.processFuelTypeValidation((String) fuelTypeInput.getSelectedItem())) {
            dialog.showDialog("ERROR", "Input Error", "Fuel Type Input Error", "Please select a fuel type", true);

            return false;
        }

        if (!VehicleController.processVehicleTypeValidation((String) vehicleTypeInput.getSelectedItem())) {
            dialog.showDialog("ERROR", "Input Error", "Vehicle Type Input Error", "Please select a vehicle type", true);

            return false;
        }

        return true;
    }

    private void resetForm() {
        vinNumberInput.setText("");
        registrationNumberInput.setText("");
        brandInput.setText("");
        modelInput.setText("");
        yearInput.setSelectedIndex(0);
        priceInput.setText("");
        colorInput.setBackground(Color.WHITE);
        colorInput.setText("Pick A Color");
        selectedColor = null;
        capacityInput.setText("");
        horsepowerInput.setText("");
        mpgInput.setText("");
        transmissionInput.setSelectedIndex(0);
        fuelTypeInput.setSelectedIndex(0);
        vehicleTypeInput.setSelectedIndex(0);
        seatInput.setValue(1);
        inputField.setText("");
        selectedImageFile = null;
        selectedImagePreview = null;
        imageLabel.setIcon(null);
        removeButton.setEnabled(false);
    }
}
