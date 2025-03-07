package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class VehiclesPage extends JPanel implements ActionListener{

    public VehiclesPage(JFrame frame) {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        this.add(createCarCardsContainer(), BorderLayout.CENTER);
        //this.add(carLeftPanel(), BorderLayout.WEST);
        //this.add(carRightPanel(), BorderLayout.EAST);
        this.add(createCarTopBar(),BorderLayout.NORTH);

    }

    private JPanel createCarCards(){
        //images
        ImageIcon image = new ImageIcon("C:\\Users\\User\\VSCode Projects\\Git\\Car Rental\\images\\Supra.jpg");
        Image rImage = image.getImage().getScaledInstance(400,400,java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(rImage);

        JPanel carCards = new JPanel(new GridLayout(0,3,20,15));

        //sample details just to show output
        String[] cars ={"PORSCHE","TOYOTA","NISSAN",
                        "HONDA","FERRARI","LAMBORGHINI",
                        "PERODUA","PROTON","LEXUS",
                    "BMW","MERCEDES BENZ","AUDI"};
        String model = "GT3 RS WEISSACH";
        String transmissions = "AUTO";
        String fuelType = "HYBRID";
        String carType = "CONVERTIBLE";
        int seats = 2;
        String price = "RM699 per day";
        String availability = "AVAILABLE";

        for (String car : cars){
            carCards.add(createCarCard(image, car, model, transmissions, fuelType, carType,
            seats, price, availability));
        }

        return carCards;
    }

    private JPanel createCarCard(ImageIcon image, String brand, String model, String transmission, String fuelType,
                                 String carType, int seats, String price, String availability) {

        ImageIcon transmissionIcon = new ImageIcon("C:\\Users\\User\\VSCode Projects\\Git\\Car Rental\\images\\manual-transmission.png");
        ImageIcon fuelIcon = new ImageIcon("C:\\Users\\User\\VSCode Projects\\Git\\Car Rental\\images\\gas-station.png");
        ImageIcon seatsIcon = new ImageIcon("C:\\Users\\User\\VSCode Projects\\Git\\Car Rental\\images\\car-seat.png");
        
        JPanel carCard = new JPanel();
        carCard.setLayout(new BorderLayout());
        carCard.setBackground(Color.WHITE);
        carCard.setPreferredSize(new Dimension(100, 400));
        carCard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

        //create buttons on the bottom
        JButton carDetails = new JButton();
        carDetails.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(10f));
        carDetails.setText("DETAILS");
        carDetails.setFocusable(false);
        carDetails.setBorderPainted(false);
        carDetails.setBackground(Color.BLUE);
        carDetails.setForeground(Color.WHITE);
        carDetails.setPreferredSize(new Dimension(100, 25));

        JButton carRent = new JButton();
        carRent.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(10f));
        carRent.setText("RENT");
        carRent.setFocusable(false);
        carRent.setBorderPainted(false);
        carRent.setBackground(Color.BLUE);
        carRent.setForeground(Color.WHITE);
        carRent.setPreferredSize(new Dimension(100, 25));

        JPanel buttonPanel = new JPanel(new BorderLayout(1,2));
        buttonPanel.add(carRent, BorderLayout.NORTH);
        buttonPanel.add(carDetails, BorderLayout.SOUTH);

        //car name model and carte on the left and price on the right
        JLabel carName = new JLabel(brand);
        carName.setHorizontalTextPosition(JLabel.LEFT);
        carName.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(20f));
        carName.setPreferredSize(new Dimension(50,20));
        carName.setForeground(Color.BLACK);

        JLabel carModel = new JLabel(model);
        carModel.setHorizontalTextPosition(JLabel.LEFT);
        carModel.setFont(CustomFonts.OPEN_SANS_SEMI_BOLD.deriveFont(17.5f));
        carModel.setPreferredSize(new Dimension(50,10));
        carModel.setForeground(Color.BLACK);

        JLabel carRentPrice = new JLabel(price);
        carRentPrice.setHorizontalTextPosition(JLabel.RIGHT);
        carRentPrice.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(17.5f));
        carRentPrice.setForeground(Color.BLUE);

        JLabel carAvailability = new JLabel(availability);
        carAvailability.setOpaque(true);
        carAvailability.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(15f));
        if(availability.equals("AVAILABLE")){
            carAvailability.setBackground(Color.BLUE);
        }
        else{
            carAvailability.setBackground(Color.RED);
        }
        carAvailability.setForeground(Color.WHITE);
        carAvailability.setPreferredSize(new Dimension(150,20));

        JLabel carTypeLabel = new JLabel(carType);
        carTypeLabel.setHorizontalTextPosition(JLabel.LEFT);
        carTypeLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(12.5f));
        carTypeLabel.setPreferredSize(new Dimension(50,10));
        carTypeLabel.setForeground(Color.BLACK);

        JPanel carNamePanel = new JPanel(new BorderLayout());
        carNamePanel.add(carName,BorderLayout.NORTH);
        carNamePanel.add(carModel,BorderLayout.CENTER);
        carNamePanel.add(carTypeLabel,BorderLayout.SOUTH);
        carNamePanel.setPreferredSize(new Dimension(50,150));
        JPanel carRentPriceAvailabilityPanel = new JPanel();
        carRentPriceAvailabilityPanel.add(carRentPrice,BorderLayout.NORTH);
        carRentPriceAvailabilityPanel.add(carAvailability,BorderLayout.SOUTH);
        carRentPriceAvailabilityPanel.setPreferredSize(new Dimension(50,150));
        carRentPriceAvailabilityPanel.setBackground(Color.CYAN);

        //container for both details at the center
        JPanel carInfoNameRentPanel = new JPanel(new GridLayout(1,2,5,5));
        carInfoNameRentPanel.add(carNamePanel);
        carInfoNameRentPanel.add(carRentPriceAvailabilityPanel);

        //container for picture at the top
        JLabel carPicture = new JLabel(image);
        carPicture.setPreferredSize(new Dimension(100,225));
        JPanel carPicturePanel = new JPanel(new BorderLayout());
        carPicturePanel.add(carPicture);

        //minor details of the car at the bottom but the top of the buttons
        JLabel seatsLabel = new JLabel(Integer.toString(seats));
        seatsLabel.setIcon(seatsIcon);
        seatsLabel.setHorizontalTextPosition(JLabel.RIGHT);
        seatsLabel.setOpaque(true);
        seatsLabel.setBackground(Color.WHITE);
        seatsLabel.setPreferredSize(new Dimension(95,35));
        seatsLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        JLabel fuelTypeLabel = new JLabel(fuelType);
        fuelTypeLabel.setIcon(fuelIcon);
        fuelTypeLabel.setHorizontalTextPosition(JLabel.RIGHT);
        fuelTypeLabel.setBackground(Color.WHITE);
        fuelTypeLabel.setOpaque(true);
        fuelTypeLabel.setPreferredSize(new Dimension(95,35));
        fuelTypeLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        JLabel transLabel = new JLabel(transmission);
        transLabel.setIcon(transmissionIcon);
        transLabel.setHorizontalTextPosition(JLabel.RIGHT);
        transLabel.setBackground(Color.WHITE);
        transLabel.setOpaque(true);
        transLabel.setPreferredSize(new Dimension(95,35));
        transLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(12.5f));
        JPanel carInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,2));
        carInfoPanel.add(transLabel);
        carInfoPanel.add(fuelTypeLabel);
        carInfoPanel.add(seatsLabel);

        // a container to put every detail and infos
        JPanel carEverythingPanel = new JPanel(new BorderLayout(0,2));
        carEverythingPanel.add(carPicturePanel, BorderLayout.NORTH);
        carEverythingPanel.add(carInfoNameRentPanel, BorderLayout.CENTER);
        carEverythingPanel.add(carInfoPanel,BorderLayout.SOUTH);

        //add the bottom buttons panel and the container for all specific car infos into the main card container
        carCard.add(buttonPanel, BorderLayout.SOUTH);
        carCard.add(carEverythingPanel,BorderLayout.CENTER);

        return carCard;
    }

    private JScrollPane createCarCardsContainer() {

        //JPanel carCards = new JPanel(new GridLayout(0,3,20,15));

        JPanel carCardsPanel = new JPanel(new BorderLayout());
        carCardsPanel.add(createCarCards(),BorderLayout.CENTER);
        carCardsPanel.add(createCarRightPanel(),BorderLayout.EAST);
        carCardsPanel.add(createBottomBar(),BorderLayout.SOUTH);
        carCardsPanel.add(createCarLeftPanel(),BorderLayout.WEST);

        JScrollPane container = new JScrollPane(carCardsPanel); 
        container.getVerticalScrollBar().setUnitIncrement(30);
        container.setBorder(BorderFactory.createEmptyBorder());
        container.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return container;
    }

    private JPanel createCarTopBar(){
        JPanel topBar = new JPanel();
        topBar.setLayout(null);
        topBar.setPreferredSize(new Dimension(1600,50));

        JLabel filters = new JLabel("Search Filter");
        filters.setBounds(50,15,200,25);
        filters.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));

        JTextField searchBar = new JTextField();
        searchBar.setText("Search for vehicles");
        searchBar.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));
        searchBar.setForeground(Color.GRAY);
        searchBar.setBorder(BorderFactory.createEmptyBorder());
        searchBar.setBounds(275,15,900,25);
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
        searchButton.setText("SEARCH");
        searchButton.setBounds(1175,15,85,25);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.CYAN);
        searchButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));

        JLabel sortByLabel = new JLabel("Sort By:");
        sortByLabel.setBounds(1275,15,50,25);
        sortByLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(12.5f));

        String[] sortOptions = {"Best Match", "Lowest Price", "Highest Price", "Newest", "Oldest"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setBounds(1325,15,125,25);
        sortComboBox.setEditable(true);
        sortComboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(15f));

        topBar.add(searchBar);
        topBar.add(searchButton);
        topBar.add(filters);
        topBar.add(sortByLabel);
        topBar.add(sortComboBox);

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
        //sample data
        String[] brands = {"ALL","NISSAN","LAMBORGHINI","FERRARI","PORSCHE"};
        String[] models = {"ALL","GTR R35","M5 E90","CLA45S"};
        String[] transType = {"ALL","MANUAL","AUTO"};
        String[] fuelType = {"ALL","GAS","HYBRID","ELECTRIC"};
        String[] availability = {"ALL","AVAILABLE","UNAVAILABLE"};
        String[] carType = {"ALL","SUV","MPV","SEDAN","CONVERTIBLE","COUPE","PICKUP TRUCK","HATCHBACK","WAGON"};

        String[] year = new String[76];
        int newestYear = 2025;
        for (int i = 75; i >= 0; i--){
            year[i] = Integer.toString(newestYear - i);
        }

        JLabel brandLabel = new JLabel("Select Brand");

        brandComboBox = new JComboBox<>(brands);
        brandComboBox.addActionListener(this);
        brandComboBox.setEditable(true);
        brandComboBox.setPreferredSize(new Dimension(200,25));

        JLabel modelLabel = new JLabel("Select Model");

        modelComboBox = new JComboBox<>(models);
        modelComboBox.addActionListener(this);
        modelComboBox.setEditable(true);
        modelComboBox.setPreferredSize(new Dimension(200,25));
        modelComboBox.setEnabled(false);

        JLabel yearLabel = new JLabel("Select Year");

        yearComboBox = new JComboBox<>(year);
        yearComboBox.insertItemAt("ALL", 0);
        yearComboBox.addActionListener(this);
        yearComboBox.setEditable(true);
        yearComboBox.setPreferredSize(new Dimension(200,25));
        yearComboBox.setSelectedIndex(0);

        JLabel transLabel = new JLabel("Select Transmission");

        transTypeComboBox = new JComboBox<>(transType);
        transTypeComboBox.addActionListener(this);
        transTypeComboBox.setEditable(true);
        transTypeComboBox.setPreferredSize(new Dimension(200,25));

        JLabel fuelLabel = new JLabel("Select Fuel Type");

        fuelTypeComboBox = new JComboBox<>(fuelType);
        fuelTypeComboBox.addActionListener(this);
        fuelTypeComboBox.setEditable(true);
        fuelTypeComboBox.setPreferredSize(new Dimension(200,25));

        JLabel availabilityLabel = new JLabel("Select Availability");

        availabilityComboBox = new JComboBox<>(availability);
        availabilityComboBox.addActionListener(this);
        availabilityComboBox.setEditable(true);
        availabilityComboBox.setPreferredSize(new Dimension(200,25));

        JLabel carTypeLabel = new JLabel("Select Car Type");

        carTypeComboBox = new JComboBox<>(carType);
        carTypeComboBox.addActionListener(this);
        carTypeComboBox.setEditable(true);
        carTypeComboBox.setPreferredSize(new Dimension(200,25));

        JLabel seatLabel = new JLabel("Seats: ALL");

        JSlider seatSlider = new JSlider(0,7,0);
        seatSlider.setMajorTickSpacing(1);
        seatSlider.setPaintTicks(true);
        seatSlider.setPaintLabels(true);

        seatSlider.addChangeListener(e -> {
            int value = seatSlider.getValue();
            if (value == 0) {
                seatLabel.setText("Seats: ALL");
            } else {
                seatLabel.setText("Seats: " + value);
            }
        });

        JLabel priceLabel = new JLabel("Price (RM):");

        minPriceField = new JTextField("Min", 7);
        maxPriceField = new JTextField("Max", 7);

        // Set the preferred size to control height
        minPriceField.setPreferredSize(new Dimension(80, 40));
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

        maxPriceField.setPreferredSize(new Dimension(80, 40));
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
        pricePanel.add(minPriceField);
        pricePanel.add(new JLabel("-"));
        pricePanel.add(maxPriceField);
        pricePanel.setPreferredSize(new Dimension(200,60));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.setPreferredSize(new Dimension(250,1600));
        leftPanel.add(brandLabel);
        leftPanel.add(brandComboBox);
        leftPanel.add(modelLabel);
        leftPanel.add(modelComboBox);
        leftPanel.add(yearLabel);
        leftPanel.add(yearComboBox);
        leftPanel.add(transLabel);
        leftPanel.add(transTypeComboBox);
        leftPanel.add(fuelLabel);
        leftPanel.add(fuelTypeComboBox);
        leftPanel.add(availabilityLabel);
        leftPanel.add(availabilityComboBox);
        leftPanel.add(carTypeLabel);
        leftPanel.add(carTypeComboBox);
        leftPanel.add(seatLabel);
        leftPanel.add(seatSlider);
        leftPanel.add(priceLabel);
        leftPanel.add(pricePanel);

        return leftPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == brandComboBox){
            //brandComboBox.getSelectedItem();
            //yearComboBox.getSelectedItem();
            //transTypeComboBox.getSelectedItem();
            //fuelTypeComboBox.getSelectedItem();
            //availabilityComboBox.getSelectedItem();
            //carTypeComboBox.getSelectedItem();
            if (brandComboBox.getSelectedIndex() != 0){
                modelComboBox.setEnabled(true);
                if(e.getSource() == modelComboBox){
                    modelComboBox.getSelectedItem();
                }
            }
            else{
                modelComboBox.setSelectedIndex(0);
                modelComboBox.setEnabled(false);
            }
        }
    }

    private JPanel createCarRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(250,900));

        return rightPanel;
    }

    private JPanel createBottomBar(){
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.BLACK);
        bottomBar.setPreferredSize(new Dimension(800,500));

        return bottomBar;
    }
}
