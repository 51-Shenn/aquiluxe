package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controllers.RentalController;
import controllers.UserController;
import database.UserDAO;
import datamodels.*;

public class RentalPage extends JPanel {

    private JFrame frame;
    private JPanel panel;
    private JScrollPane mainScrollPane;
    private JLabel totalPriceLabel;
    private Map<String, JComponent> inputFieldsMap = new HashMap<>();
    private Map<String, JComboBox<String>> comboBoxMap = new HashMap<>();

    private final float NORMAL_TEXT_SIZE = 28f;
    private final float BUTTON_TEXT_SIZE = 30f;
    private final Border BORDER = new LineBorder(Color.BLACK, 2);
    private final Border PADDING = new EmptyBorder(10, 15, 10, 15);
    private final Font INPUT_FONT = CustomFonts.OPEN_SANS_REGULAR;

    private GridBagConstraints gbc = new GridBagConstraints();

    private RentalController rentalController = new RentalController(frame, panel);
    private static Vehicle vehicleSelected;
    private Rental rental = new Rental(); // default rental object
    private Payment payment = new Payment();
    private User user = new User();

    private boolean validDetails = false;

    private JLabel rentalPriceLabel;
    private JLabel insurancePriceLabel;
    private JLabel depositPriceLabel;
    private JLabel taxPriceLabel;
    private JLabel discountPriceLabel;

    public RentalPage(JFrame frame, JPanel panel, Vehicle selectedVehicle) {
        this.frame = frame;
        this.panel = panel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // set vehicle
        vehicleSelected = selectedVehicle;
        rental.setRentVehicle(vehicleSelected);

        try {
            // set customer
            File accountsFile = new File("files/settings/accounts.txt");
            user = UserController.loadCurrentUser(accountsFile);
            Customer currentCustomer = UserDAO.getCustomerById(user);
            rental.setRentCustomer(currentCustomer);
        } catch (Exception e) {
            Dialog dialogError = new Dialog();
            dialogError.showDialog("ERROR",
                    "Account",
                    "No Account Signed In",
                    "Please Sign In or Create an account",
                    false);
            return;
        }

        // scrollable rental page container
        JSplitPane rentalPage = createRentalPage();
        this.add(rentalPage, BorderLayout.CENTER);

        addUpdateListeners();
    }

    private JSplitPane createRentalPage() {
        // create left and right containers
        JPanel leftContainer = createLeftContainer();
        JPanel rightContainer = createRightContainer();

        leftContainer.setMinimumSize(new Dimension(700, 0));
        rightContainer.setPreferredSize(rightContainer.getPreferredSize());

        // create right scroll pane
        mainScrollPane = new JScrollPane(rightContainer);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        mainScrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setMinimumSize(new Dimension(900, 0));

        // create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftContainer, mainScrollPane);
        splitPane.setDividerLocation(960);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerSize(5);

        return splitPane;
    }

    // rental page left container
    private JPanel createLeftContainer() {
        // create container for left panel
        JPanel leftContainer = new JPanel();
        leftContainer.setBackground(Theme.getBackground());
        leftContainer.setLayout(new BorderLayout(0, 5));
        leftContainer.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));

        leftContainer.add(createTitlePanel("Rental Summary", 50f), BorderLayout.NORTH);
        leftContainer.add(createRentalSummary(), BorderLayout.CENTER);

        leftContainer.setPreferredSize(leftContainer.getPreferredSize());

        return leftContainer;
    }

    private JPanel createRentalSummary() {
        // rental car summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BorderLayout(0, 10));
        summaryPanel.setBackground(Theme.getBackground());

        // rental car summary container
        RoundedPanel carPanel = new RoundedPanel(20, Theme.getBackground());
        carPanel.setLayout(new BorderLayout());
        carPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // car image -> get image path from car object
        ImageIcon image = null;
        try {
            image = new ImageIcon(vehicleSelected.getImagePath());

            // Check if any image failed to load
            if (image.getIconWidth() == -1) {
                throw new Exception("One or more images failed to load.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading images: " + e.getMessage());
        }
        Image rImage = image.getImage().getScaledInstance(225, 225, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(rImage);
        JLabel carImageLabel = new JLabel(image);
        carImageLabel.setHorizontalAlignment(JLabel.CENTER);

        // rental car details summary text
        JPanel carDetailsPanel = new JPanel();
        carDetailsPanel.setLayout(new GridBagLayout());
        carDetailsPanel.setBackground(Theme.getBackground());
        carDetailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // car details text labels
        JLabel carNameLabel = createLabel(vehicleSelected.getBrand() + " " + vehicleSelected.getModel(),
                30f, Theme.getForeground(), Theme.getBackground(), 50, -1);
        carNameLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carTransmissionLabel = createLabel(vehicleSelected.getTransmission() + " Transmission",
                20f, Theme.getForeground(), Theme.getBackground(), 50, -1);
        carTransmissionLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carFuelLabel = createLabel(vehicleSelected.getFuelType(),
                20f, Theme.getForeground(), Theme.getBackground(), 50, -1);
        carFuelLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carRentalPriceLabel = createLabel(
                "RM " + String.format("%.2f", vehicleSelected.getRentalPriceDay()) + "/day",
                20f, Theme.getForeground(), Theme.getBackground(), 50, -1);
        carRentalPriceLabel.setHorizontalAlignment(JLabel.LEFT);

        // gblayout gridy position
        gbc.gridy = 0;
        carDetailsPanel.add(carNameLabel, gbc);
        gbc.insets.left = 30; // indent for other labels
        gbc.gridy = 1;
        carDetailsPanel.add(carTransmissionLabel, gbc);
        gbc.gridy = 2;
        carDetailsPanel.add(carFuelLabel, gbc);
        gbc.gridy = 3;
        carDetailsPanel.add(carRentalPriceLabel, gbc);
        gbc.insets.left = 0; // reset indent

        // add label in panel
        carPanel.add(carImageLabel, BorderLayout.WEST);
        carPanel.add(carDetailsPanel, BorderLayout.CENTER);

        summaryPanel.add(carPanel, BorderLayout.NORTH);

        // add pricing details
        RoundedPanel pricingPanel = new RoundedPanel(20, Theme.getBackground());
        pricingPanel.setLayout(new GridBagLayout());
        pricingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 100));

        // get list of costs
        double[] rentalCosts = rentalController.processRentalCosts(rental);

        // pricing details text labels
        JLabel rentalPriceTextLabel = createLabel("Base Rental Price : ", 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        rentalPriceTextLabel.setOpaque(false);
        rentalPriceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        rentalPriceLabel = createLabel("RM " + String.format("%.2f", rentalCosts[0]), 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        rentalPriceLabel.setOpaque(false);
        rentalPriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        JLabel insuranceTextLabel = createLabel("Insurance Price : ", 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        insuranceTextLabel.setOpaque(false);
        insuranceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        insurancePriceLabel = createLabel("RM " + String.format("%.2f", rentalCosts[1]), 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        insurancePriceLabel.setOpaque(false);
        insurancePriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        JLabel depositTextLabel = createLabel("Deposit Price (Refundable) : ", 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        depositTextLabel.setOpaque(false);
        depositTextLabel.setHorizontalAlignment(JLabel.LEFT);

        depositPriceLabel = createLabel("RM " + String.format("%.2f", rentalCosts[2]), 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        depositPriceLabel.setOpaque(false);
        depositPriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        JLabel taxTextLabel = createLabel("Tax Charges (SST) : ", 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        taxTextLabel.setOpaque(false);
        taxTextLabel.setHorizontalAlignment(JLabel.LEFT);

        taxPriceLabel = createLabel("RM " + String.format("%.2f", rentalCosts[3]), 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        taxPriceLabel.setOpaque(false);
        taxPriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        JLabel discountTextLabel = createLabel("Discount (Rent days > 3) : ", 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        discountTextLabel.setOpaque(false);
        discountTextLabel.setHorizontalAlignment(JLabel.LEFT);

        discountPriceLabel = createLabel("RM " + String.format("%.2f", rentalCosts[4]), 20f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        discountPriceLabel.setOpaque(false);
        discountPriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        // rental price
        gbc.gridx = 0;
        gbc.gridy = 0;
        pricingPanel.add(rentalPriceTextLabel, gbc);
        gbc.gridx = 1;
        pricingPanel.add(rentalPriceLabel, gbc);

        // insurance price
        gbc.gridx = 0;
        gbc.gridy = 1;
        pricingPanel.add(insuranceTextLabel, gbc);
        gbc.gridx = 1;
        pricingPanel.add(insurancePriceLabel, gbc);

        // deposit price
        gbc.gridx = 0;
        gbc.gridy = 2;
        pricingPanel.add(taxTextLabel, gbc);
        gbc.gridx = 1;
        pricingPanel.add(taxPriceLabel, gbc);

        // tax charges
        gbc.gridx = 0;
        gbc.gridy = 3;
        pricingPanel.add(discountTextLabel, gbc);
        gbc.gridx = 1;
        pricingPanel.add(discountPriceLabel, gbc);

        // tax charges
        gbc.gridx = 0;
        gbc.gridy = 4;
        pricingPanel.add(depositTextLabel, gbc);
        gbc.gridx = 1;
        pricingPanel.add(depositPriceLabel, gbc);

        summaryPanel.add(pricingPanel, BorderLayout.CENTER);

        summaryPanel.add(createTotalPricePanel(), BorderLayout.SOUTH);

        return summaryPanel;
    }

    private RoundedPanel createTotalPricePanel() {
        // total price
        RoundedPanel totalPanel = new RoundedPanel(20, Theme.getBackground());
        totalPanel.setLayout(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 100));

        JLabel totalPriceTextLabel = createLabel("Total Price : ", 30f,
                Theme.getForeground(), Theme.getBackground(), 30, -1);
        totalPriceTextLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        totalPriceTextLabel.setOpaque(false);
        totalPriceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        totalPriceLabel = createLabel("RM " + String.format("%.2f", rentalController.processRentalTotalCost(rental)),
                30f, Theme.getForeground(), Theme.getBackground(), 30, -1);
        totalPriceLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        totalPriceLabel.setOpaque(false);
        totalPriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        totalPanel.add(totalPriceTextLabel, BorderLayout.WEST);
        totalPanel.add(totalPriceLabel, BorderLayout.EAST);

        return totalPanel;
    }

    // rental page right container
    private JPanel createRightContainer() {
        // create container for inputs
        JPanel rightContainer = new JPanel();
        rightContainer.setBackground(Theme.getBackground());

        rightContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 10, 0); // Adds spacing between components

        rightContainer.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));

        rightContainer.setPreferredSize(null);
        rightContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        rightContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        // input panel initialization
        JPanel billingContainer = createBillingInfoPanel();
        JPanel rentalContainer = createRentalDetailsPanel();
        JPanel paymentContainer = createPaymentMethodContainer();
        JButton confirmButton = createButton("Confirm", Theme.getSpecial(), Theme.getWhite(), 200, 50);
        confirmButton.addActionListener(e -> {
            try {
                if (getInputValue("PaymentMethod").equals("Online Banking")) {
                    Dialog dialog1 = new Dialog(this.frame);
                    dialog1.showDialog("SUCCESS",
                            "Payment",
                            "Payment Gateway",
                            "Proceeding to Payment Gateway of " + getInputValue("OnlineBanking") + " ... ",
                            false);

                    Dialog dialog2 = new Dialog(this.frame);
                    dialog2.showDialog("SUCCESS",
                            "Payment",
                            "Successful Payment",
                            "Payment through " + getInputValue("OnlineBanking") + " was successful!",
                            false);
                }

                // update customer address
                getBillingDetails();

                // set rental details
                getRentalDetails();
                // set total cost
                rental.setRentTotalCost(rentalController.processRentalTotalCost(rental));

                // set payment details
                getPaymentDetails();

                // process data
                if (validDetails) {
                    int rentalId = rentalController.processRental(rental);
                    rental.setRentalId(rentalId);
                    rentalController.processPayment(rental, payment);

                    Dialog dialog = new Dialog(this.frame);
                    dialog.showDialog("SUCCESS",
                            "Success",
                            "Payment Success",
                            "Rental has been booked",
                            false);

                    // update vehicle availability
                    System.out.println(GUIComponents.cardPanel.getComponents().length);
                    GUIComponents.cardPanel.remove(GUIComponents.vehiclesPanel);
                    System.out.println(GUIComponents.cardPanel.getComponents().length);
                    GUIComponents.vehiclesPanel = new VehiclesPage(frame, panel, user);
                    GUIComponents.cardPanel.add(GUIComponents.vehiclesPanel, "VehiclesPage");
                    GUIComponents.vehiclesPanel.revalidate();
                    GUIComponents.vehiclesPanel.repaint();
                    System.out.println(GUIComponents.cardPanel.getComponents().length);
                    GUIComponents.cardLayout.show(GUIComponents.cardPanel, "VehiclesPage");
                    GUIComponents.cardPanel.revalidate();
                    GUIComponents.cardPanel.repaint();

                    validDetails = false;
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                Dialog dialog = new Dialog(this.frame);
                dialog.showDialog("HAZARD",
                        "Warning",
                        "Incorrect inputs",
                        "Please input the correct information",
                        true);
            }
        });

        // add input panels to right container
        gbc.gridy = 0;
        rightContainer.add(billingContainer, gbc);
        gbc.gridy = 1;
        rightContainer.add(rentalContainer, gbc);
        gbc.gridy = 2;
        rightContainer.add(paymentContainer, gbc);
        gbc.gridy = 3;
        rightContainer.add(confirmButton, gbc);

        return rightContainer;
    }

    // Billing Info
    private JPanel createBillingInfoPanel() {
        JPanel billingContainer = new JPanel(new BorderLayout());
        billingContainer.setBackground(Theme.getBackground());
        billingContainer.add(createTitlePanel("Billing Info"), BorderLayout.NORTH);

        RoundedPanel billingInfoPanel = new RoundedPanel(20, Theme.getBackground());
        billingInfoPanel.setLayout(new GridBagLayout());
        billingInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        billingInfoPanel.add(createInputContainer("BillingAddress", "Billing Address: ", 4, true), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        billingInfoPanel.add(createInputContainer("BillingName", "Billing Name: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        billingInfoPanel.add(createInputContainer("BillingContact", "Billing Contact: ", false), gbc);

        billingContainer.add(billingInfoPanel, BorderLayout.CENTER);

        return billingContainer;
    }

    private void getBillingDetails() {

        if (getInputValue("BillingAddress").isBlank()
                || getInputValue("BillingAddress").isEmpty()) {
            Dialog dialog = new Dialog(this.frame);
            dialog.showDialog("HAZARD",
                    "Empty",
                    "Empty Address",
                    "Please input an billing address",
                    true);

            return;
        }

        if (getInputValue("BillingAddress").equals(rental.getRentCustomer().getUserAddress()))
            return;

        rental.getRentCustomer().setAddress(getInputValue("BillingAddress"));
        rentalController.processCustomerAddress(rental.getRentCustomer());
    }

    // Rental Details
    private JPanel createRentalDetailsPanel() {
        JPanel rentalContainer = new JPanel(new BorderLayout());
        rentalContainer.setBackground(Theme.getBackground());
        rentalContainer.add(createTitlePanel("Rental Details"), BorderLayout.NORTH);

        RoundedPanel rentalDetailsPanel = new RoundedPanel(20, Theme.getBackground());
        rentalDetailsPanel.setLayout(new GridBagLayout());
        rentalDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 0;
        rentalDetailsPanel.add(createDateSelector("Start Date", "StartDay", "StartMonth", "StartYear"), gbc);

        gbc.gridy = 1;
        rentalDetailsPanel.add(createTimeSelector("Pick Up Time", "PickUpHour", "PickUpMinute"), gbc);

        gbc.gridy = 2;
        rentalDetailsPanel.add(createDateSelector("End Date", "EndDay", "EndMonth", "EndYear"), gbc);

        gbc.gridy = 3;
        rentalDetailsPanel.add(createTimeSelector("Drop Off Time", "DropOffHour", "DropOffMinute"), gbc);

        rentalContainer.add(rentalDetailsPanel, BorderLayout.CENTER);

        return rentalContainer;
    }

    private void getRentalDetails() {
        String startDate = getInputValue("StartYear") + "-" +
                String.format("%02d", Integer.parseInt(getInputValue("StartMonth"))) + "-" +
                String.format("%02d", Integer.parseInt(getInputValue("StartDay")));

        String endDate = getInputValue("EndYear") + "-" +
                String.format("%02d", Integer.parseInt(getInputValue("EndMonth"))) + "-" +
                String.format("%02d", Integer.parseInt(getInputValue("EndDay")));

        String pickupTime = getInputValue("PickUpHour") + ":" + getInputValue("PickUpMinute") + ":00";
        String dropoffTime = getInputValue("DropOffHour") + ":" + getInputValue("DropOffMinute") + ":00";

        rental.setRentStartDate(Date.valueOf(startDate).toLocalDate());
        rental.setRentEndDate(Date.valueOf(endDate).toLocalDate());
        rental.setPickUpTime(Time.valueOf(pickupTime).toLocalTime());
        rental.setDropoffTime(Time.valueOf(dropoffTime).toLocalTime());

    }

    private JPanel createDateSelector(String title, String day, String month, String year) {
        JPanel dateContainer = new JPanel(new GridBagLayout());
        dateContainer.setBackground(Theme.getBackground());

        JLabel titleDateLabel = createLabel(title, 25f, Theme.getForeground(),
                Theme.getBackground(), 1, 1);
        titleDateLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(Theme.getBackground());
        wrapper.add(titleDateLabel);

        String[] yearsList = { "2025", "2026", "2027" };

        String[] monthsList = new String[12];
        for (int i = 0; i < monthsList.length; i++) {
            monthsList[i] = String.valueOf(i + 1);
        }

        String[] daysList = new String[31];
        for (int i = 0; i < daysList.length; i++) {
            daysList[i] = String.valueOf(i + 1);
        }

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dateContainer.add(wrapper, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        dateContainer.add(createComboBox(day, daysList), gbc);

        gbc.gridx = 1;
        dateContainer.add(createComboBox(month, monthsList), gbc);

        gbc.gridx = 2;
        dateContainer.add(createComboBox(year, yearsList), gbc);

        return dateContainer;
    }

    private JPanel createTimeSelector(String title, String hour, String minute) {
        JPanel dateContainer = new JPanel(new GridBagLayout());
        dateContainer.setBackground(Theme.getBackground());

        JLabel titleTimeLabel = createLabel(title, 25f, Theme.getForeground(),
                Theme.getBackground(), 1, 1);
        // titleTimeLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(Theme.getBackground());
        wrapper.add(titleTimeLabel);

        String[] hourList = { "08", "10", "12", "14", "16", "18", "20", "22" };

        String[] minuteList = { "00", "15", "30", "45" };

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dateContainer.add(wrapper, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        dateContainer.add(createComboBox(hour, hourList), gbc);

        gbc.gridx = 1;
        dateContainer.add(createComboBox(minute, minuteList), gbc);

        return dateContainer;
    }

    private JPanel cardNumberPanel;
    private JPanel expiryDatePanel;
    private JPanel cvvPanel;
    private JPanel onlineBankingPanel;

    // Payment Method
    private JPanel createPaymentMethodContainer() {
        JPanel paymentContainer = new JPanel(new BorderLayout());
        paymentContainer.setBackground(Theme.getBackground());
        paymentContainer.add(createTitlePanel("Payment"), BorderLayout.NORTH);

        RoundedPanel paymentMethodPanel = new RoundedPanel(20, Theme.getBackground());
        paymentMethodPanel.setLayout(new GridBagLayout());
        paymentMethodPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentMethodPanel.add(createPaymentMethodSelector("Payment Method:", "PaymentMethod"), gbc);
        // combobox select payment method : online banking or card

        gbc.gridx = 0;
        gbc.gridy = 1;
        cardNumberPanel = createInputContainer("CardNumber", "Card Number: ", false);
        paymentMethodPanel.add(cardNumberPanel, gbc);
        onlineBankingPanel = createBankSelector("Bank:", "OnlineBanking");
        onlineBankingPanel.setVisible(false);
        paymentMethodPanel.add(onlineBankingPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        expiryDatePanel = createInputContainer("ExpiryDate", "Expiry Date: ", false);
        paymentMethodPanel.add(expiryDatePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        cvvPanel = createInputContainer("CVV", "CVV: ", false);
        paymentMethodPanel.add(cvvPanel, gbc);

        paymentContainer.add(paymentMethodPanel, BorderLayout.CENTER);

        return paymentContainer;
    }

    private void getPaymentDetails() {
        if (getInputValue("PaymentMethod").equals("Credit/Debit Card")) {
            if (getInputValue("CardNumber").isBlank() || getInputValue("CardNumber").isEmpty() ||
                    getInputValue("ExpiryDate").isBlank() || getInputValue("ExpiryDate").isEmpty() ||
                    getInputValue("CVV").isBlank() || getInputValue("CVV").isEmpty()) {
                Dialog dialog = new Dialog(this.frame);
                dialog.showDialog("HAZARD",
                        "Empty",
                        "Empty Payment Details",
                        "Please input complete Payment Details",
                        true);

                return;
            }
            if (getInputValue("CardNumber").length() < 16 || getInputValue("ExpiryDate").length() < 5
                    || getInputValue("CVV").length() < 3) {
                Dialog dialog = new Dialog(this.frame);
                dialog.showDialog("HAZARD",
                        "Invalid",
                        "Incomplee Payment Details",
                        "16 Digit Card Number : Expiry Date MM/YY : 3 Digit CVV",
                        true);

                return;
            }
        }

        payment.setRental(rental);
        payment.setAmount(rental.getRentTotalCost());
        payment.setPaymentMethod(getInputValue("PaymentMethod"));
        payment.setPaymentDate(LocalDate.now());
        validDetails = true;
    }

    private JPanel createPaymentMethodSelector(String title, String paymentMethod) {
        JPanel paymentContainer = new JPanel(new GridBagLayout());
        paymentContainer.setBackground(Theme.getBackground());

        JLabel titlePaymentLabel = createLabel(title, 25f, Theme.getForeground(),
                Theme.getBackground(), 1, 1);
        titlePaymentLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(Theme.getBackground());
        wrapper.add(titlePaymentLabel);

        String[] paymentMethodList = { "Credit/Debit Card", "Online Banking" };

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentContainer.add(wrapper, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        JComboBox<String> comboBoxPayment = createComboBox(paymentMethod, paymentMethodList);
        comboBoxPayment.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selected = (String) e.getItem();
                boolean showCardInputs = selected.equals("Credit/Debit Card");

                if (cardNumberPanel != null)
                    cardNumberPanel.setVisible(showCardInputs);
                if (expiryDatePanel != null)
                    expiryDatePanel.setVisible(showCardInputs);
                if (cvvPanel != null)
                    cvvPanel.setVisible(showCardInputs);
                if (onlineBankingPanel != null)
                    onlineBankingPanel.setVisible(!showCardInputs);

                // Force re-layout
                cardNumberPanel.getParent().revalidate();
                cardNumberPanel.getParent().repaint();
            }
        });

        paymentContainer.add(comboBoxPayment, gbc);

        return paymentContainer;
    }

    private JPanel createBankSelector(String title, String bank) {
        JPanel bankContainer = new JPanel(new GridBagLayout());
        bankContainer.setBackground(Theme.getBackground());

        JLabel titleBankLabel = createLabel(title, 25f, Theme.getForeground(),
                Theme.getBackground(), 1, 1);
        titleBankLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(Theme.getBackground());
        wrapper.add(titleBankLabel);

        String[] bankList = { "Maybank2U", "CIMB", "Hong Leong", "AmBank", "OCBC", "RHB", "Public Bank" };

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        bankContainer.add(wrapper, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        JComboBox<String> comboBoxBank = createComboBox(bank, bankList);

        bankContainer.add(comboBoxBank, gbc);

        return bankContainer;
    }

    // create input container (from AuthenticationPage)
    private JPanel createInputContainer(String fieldname, String title, int initialRows, boolean scrollable) {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // input title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));
        titleLabel.setForeground(Theme.getForeground());
        inputPanel.add(titleLabel, gbc);

        gbc.gridy++;

        // input text field
        JTextField inputLineField = new JTextField();
        inputLineField.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        inputLineField.setPreferredSize(new Dimension(700, 70));
        inputLineField.setMinimumSize(new Dimension(700, 70));
        inputLineField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        inputLineField.setBorder(new CompoundBorder(BORDER, PADDING));

        if (fieldname == "BillingName") {
            inputLineField.setText(rental.getRentCustomer().getFullName());
            inputLineField.setEditable(false);
        }

        if (fieldname == "BillingContact") {
            inputLineField.setText("0" + rental.getRentCustomer().getPhoneNumber());
            inputLineField.setEditable(false);
        }

        // input text area
        JTextArea inputField = new JTextArea(initialRows, 20);
        inputField.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        inputField.setForeground(Theme.getBlack());
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        if (fieldname == "BillingAddress") {
            inputField.setText(rental.getRentCustomer().getUserAddress());
        }

        JComponent inputComponent;
        if (scrollable) {
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
                    mainScrollPane.dispatchEvent(e);
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

            inputComponent = scrollPane;
            inputFieldsMap.put(fieldname, inputField); // store input field in map
        } else {
            // use text field for single line input
            inputComponent = inputLineField;
            inputFieldsMap.put(fieldname, inputLineField); // store input field in map
        }

        inputPanel.add(inputComponent, gbc);
        return inputPanel;
    }

    private JPanel createInputContainer(String fieldname, String title, boolean scrollable) {
        return createInputContainer(fieldname, title, 1, scrollable);
    }

    // getter and setter for input fields
    public String getInputValue(String fieldName) {
        JComponent comp = inputFieldsMap.get(fieldName);
        if (comp instanceof JTextField) {
            return ((JTextField) comp).getText();
        } else if (comp instanceof JTextArea) {
            return ((JTextArea) comp).getText();
        }

        JComboBox<String> combobox = comboBoxMap.get(fieldName);
        if (combobox instanceof JComboBox<String>) {
            Object selectedItem = ((JComboBox<String>) combobox).getSelectedItem();
            return selectedItem != null ? selectedItem.toString() : null;
        }

        return null;
    }

    public void setInputValue(String fieldName, String value) {
        JComponent comp = inputFieldsMap.get(fieldName);
        if (comp instanceof JTextField) {
            ((JTextField) comp).setText(value);
        } else if (comp instanceof JTextArea) {
            ((JTextArea) comp).setText(value);
        }
    }

    // create title panel
    private JPanel createTitlePanel(String title) {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Theme.getBackground());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 0));

        JLabel titleLabel = createLabel(title, 35f, Theme.getSpecial(), Theme.getBackground(), 50, -1);
        titleLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(35f));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);

        titlePanel.add(titleLabel);

        return titlePanel;
    }

    private JPanel createTitlePanel(String title, float size) {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Theme.getBackground());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 0));

        JLabel titleLabel = createLabel(title, size, Theme.getSpecial(), Theme.getBackground(), 50, -1);
        titleLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(size));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);

        titlePanel.add(titleLabel);

        return titlePanel;
    }

    // create label instance
    private JLabel createLabel(String text, Float fontSize, Color textColor, Color bgColor, Integer height,
            Integer width) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(fontSize));
        label.setForeground(textColor);
        label.setBackground(bgColor);
        label.setOpaque(true);
        label.setMinimumSize(new Dimension(width, height));

        return label;
    }

    // create button instance
    private JButton createButton(String text, Color bgColor, Color textColor, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(BUTTON_TEXT_SIZE));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setPreferredSize(new Dimension(width, height));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JComboBox<String> createComboBox(String fieldname, String[] stringList) {
        JComboBox<String> comboBox = new JComboBox<>(stringList);
        comboBox.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(25f));
        comboBox.setBackground(Theme.getBackground());
        comboBox.setForeground(Theme.getBlack());
        comboBox.setFocusable(false);
        comboBox.setPreferredSize(new Dimension(250, 50));

        comboBoxMap.put(fieldname, comboBox);

        return comboBox;
    }

    // Method to add listeners to all date combo boxes
    private void addUpdateListeners() {
        initializeCurrentDate();

        // Add action listeners to date combo boxes
        JComboBox<String> startDayBox = (JComboBox<String>) comboBoxMap.get("StartDay");
        JComboBox<String> startMonthBox = (JComboBox<String>) comboBoxMap.get("StartMonth");
        JComboBox<String> startYearBox = (JComboBox<String>) comboBoxMap.get("StartYear");
        JComboBox<String> endDayBox = (JComboBox<String>) comboBoxMap.get("EndDay");
        JComboBox<String> endMonthBox = (JComboBox<String>) comboBoxMap.get("EndMonth");
        JComboBox<String> endYearBox = (JComboBox<String>) comboBoxMap.get("EndYear");

        // Add action listeners to update when any date component changes
        startDayBox.addActionListener(e -> updateRentalPrices());
        startMonthBox.addActionListener(e -> {
            updateDaysInMonth(startDayBox, startMonthBox, startYearBox);
            updateRentalPrices();
        });
        startYearBox.addActionListener(e -> {
            updateDaysInMonth(startDayBox, startMonthBox, startYearBox);
            updateRentalPrices();
        });

        endDayBox.addActionListener(e -> updateRentalPrices());
        endMonthBox.addActionListener(e -> {
            updateDaysInMonth(endDayBox, endMonthBox, endYearBox);
            updateRentalPrices();
        });
        endYearBox.addActionListener(e -> {
            updateDaysInMonth(endDayBox, endMonthBox, endYearBox);
            updateRentalPrices();
        });
    }

    private void initializeCurrentDate() {
        LocalDate today = LocalDate.now();

        // Set start date to current date
        JComboBox<String> startDayBox = (JComboBox<String>) comboBoxMap.get("StartDay");
        JComboBox<String> startMonthBox = (JComboBox<String>) comboBoxMap.get("StartMonth");
        JComboBox<String> startYearBox = (JComboBox<String>) comboBoxMap.get("StartYear");

        startYearBox.setSelectedItem(String.valueOf(today.getYear()));
        startMonthBox.setSelectedItem(String.valueOf(today.getMonthValue()));
        int daysInMonth = getDaysInMonth(today.getMonthValue(), today.getYear());
        startDayBox.removeAllItems();
        for (int i = 1; i <= daysInMonth; i++) {
            startDayBox.addItem(String.valueOf(i));
        }
        startDayBox.setSelectedItem(String.valueOf(today.getDayOfMonth()));

        // Set end date to current date + 1 day
        LocalDate tomorrow = today.plusDays(1);

        JComboBox<String> endDayBox = (JComboBox<String>) comboBoxMap.get("EndDay");
        JComboBox<String> endMonthBox = (JComboBox<String>) comboBoxMap.get("EndMonth");
        JComboBox<String> endYearBox = (JComboBox<String>) comboBoxMap.get("EndYear");

        // Set year and month first
        endYearBox.setSelectedItem(String.valueOf(tomorrow.getYear()));
        endMonthBox.setSelectedItem(String.valueOf(tomorrow.getMonthValue()));

        // Manually update days in month
        daysInMonth = getDaysInMonth(tomorrow.getMonthValue(), tomorrow.getYear());
        endDayBox.removeAllItems();
        for (int i = 1; i <= daysInMonth; i++) {
            endDayBox.addItem(String.valueOf(i));
        }
        endDayBox.setSelectedItem(String.valueOf(tomorrow.getDayOfMonth()));

        // Store formatted dates for processing
        String startDateStr = String.format("%d-%02d-%02d",
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        String endDateStr = String.format("%d-%02d-%02d",
                tomorrow.getYear(), tomorrow.getMonthValue(), tomorrow.getDayOfMonth());

        inputFieldsMap.put("StartDate", new JTextField(startDateStr));
        inputFieldsMap.put("EndDate", new JTextField(endDateStr));

        // Initialize rental dates
        rental.setRentStartDate(today);
        rental.setRentEndDate(tomorrow);

        // Update prices without relying on listeners
        double[] rentalCosts = rentalController.processRentalCosts(rental);
        rentalPriceLabel.setText("RM " + String.format("%.2f", rentalCosts[0]));
        insurancePriceLabel.setText("RM " + String.format("%.2f", rentalCosts[1]));
        depositPriceLabel.setText("RM " + String.format("%.2f", rentalCosts[2]));
        taxPriceLabel.setText("RM " + String.format("%.2f", rentalCosts[3]));
        discountPriceLabel.setText("- RM " + String.format("%.2f", rentalCosts[4]));
        totalPriceLabel.setText("RM " + String.format("%.2f", rentalController.processRentalTotalCost(rental)));
    }

    // Update the number of days in the day combo box based on month and year
    private void updateDaysInMonth(JComboBox<String> dayBox, JComboBox<String> monthBox, JComboBox<String> yearBox) {
        // Get the selected month and year
        int month = Integer.parseInt((String) monthBox.getSelectedItem());
        int year = Integer.parseInt((String) yearBox.getSelectedItem());

        // Get current selected day (to restore if possible)
        int currentDay = 1;
        if (dayBox.getSelectedItem() != null) {
            currentDay = Integer.parseInt((String) dayBox.getSelectedItem());
        }

        // Determine days in month
        int daysInMonth = getDaysInMonth(month, year);

        // Store selection state - only try to remove if there are listeners
        ActionListener[] listeners = dayBox.getActionListeners();
        if (listeners != null && listeners.length > 0) {
            dayBox.removeActionListener(listeners[0]);
        }

        // Update days in combo box
        dayBox.removeAllItems();
        for (int i = 1; i <= daysInMonth; i++) {
            dayBox.addItem(String.valueOf(i));
        }

        // Restore selection if valid, otherwise select the last day
        if (currentDay <= daysInMonth) {
            dayBox.setSelectedItem(String.valueOf(currentDay));
        } else {
            dayBox.setSelectedItem(String.valueOf(daysInMonth));
        }

        // Restore action listener - only add back if we removed one
        if (listeners != null && listeners.length > 0) {
            dayBox.addActionListener(e -> updateRentalPrices());
        }
    }

    // Get the number of days in a specific month and year
    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 2: // February
                return isLeapYear(year) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11: // April, June, September, November
                return 30;
            default: // All other months
                return 31;
        }
    }

    // Check if the year is a leap year
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Method to update rental prices based on selected dates
    private void updateRentalPrices() {
        try {
            // Get date components from combo boxes
            String startDay = getInputValue("StartDay");
            String startMonth = getInputValue("StartMonth");
            String startYear = getInputValue("StartYear");
            String endDay = getInputValue("EndDay");
            String endMonth = getInputValue("EndMonth");
            String endYear = getInputValue("EndYear");

            // Skip if any fields are null
            if (startDay == null || startMonth == null || startYear == null ||
                    endDay == null || endMonth == null || endYear == null) {
                return;
            }

            // Format dates into standard format (yyyy-MM-dd)
            String startDateStr = String.format("%s-%02d-%02d",
                    startYear, Integer.parseInt(startMonth), Integer.parseInt(startDay));
            String endDateStr = String.format("%s-%02d-%02d",
                    endYear, Integer.parseInt(endMonth), Integer.parseInt(endDay));

            // Store formatted dates for later use in processing
            inputFieldsMap.put("StartDate", new JTextField(startDateStr));
            inputFieldsMap.put("EndDate", new JTextField(endDateStr));

            // Parse dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            // Validate dates
            long days = ChronoUnit.DAYS.between(startDate, endDate);
            if (days < 0) {
                // If end date is before start date, set end date equal to start date
                endDate = startDate;

                // Update end date combo boxes to match start date (without triggering
                // listeners)
                removeActionListeners((JComboBox<String>) comboBoxMap.get("EndDay"));
                removeActionListeners((JComboBox<String>) comboBoxMap.get("EndMonth"));
                removeActionListeners((JComboBox<String>) comboBoxMap.get("EndYear"));

                ((JComboBox<String>) comboBoxMap.get("EndDay")).setSelectedItem(startDay);
                ((JComboBox<String>) comboBoxMap.get("EndMonth")).setSelectedItem(startMonth);
                ((JComboBox<String>) comboBoxMap.get("EndYear")).setSelectedItem(startYear);

                addActionListenersBack();

                days = 0;
            }

            // Update rental object with new dates
            rental.setRentStartDate(startDate);
            rental.setRentEndDate(endDate);

            // Process updated costs
            double[] rentalCosts = rentalController.processRentalCosts(rental);

            // Update labels
            rentalPriceLabel.setText("RM " + String.format("%.2f", rentalCosts[0]));
            insurancePriceLabel.setText("RM " + String.format("%.2f", rentalCosts[1]));
            depositPriceLabel.setText("RM " + String.format("%.2f", rentalCosts[2]));
            taxPriceLabel.setText("RM " + String.format("%.2f", rentalCosts[3]));
            discountPriceLabel.setText("- RM " + String.format("%.2f", rentalCosts[4]));
            totalPriceLabel.setText("RM " + String.format("%.2f", rentalController.processRentalTotalCost(rental)));

        } catch (DateTimeParseException e) {
            System.out.println("Date parsing error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error updating prices: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to remove action listeners from a combo box
    private void removeActionListeners(JComboBox<String> comboBox) {
        ActionListener[] listeners = comboBox.getActionListeners();
        for (ActionListener listener : listeners) {
            comboBox.removeActionListener(listener);
        }
    }

    // Helper method to add action listeners back to end date combo boxes
    private void addActionListenersBack() {
        JComboBox<String> endDayBox = (JComboBox<String>) comboBoxMap.get("EndDay");
        JComboBox<String> endMonthBox = (JComboBox<String>) comboBoxMap.get("EndMonth");
        JComboBox<String> endYearBox = (JComboBox<String>) comboBoxMap.get("EndYear");

        endDayBox.addActionListener(e -> updateRentalPrices());
        endMonthBox.addActionListener(e -> {
            updateDaysInMonth(endDayBox, endMonthBox, endYearBox);
            updateRentalPrices();
        });
        endYearBox.addActionListener(e -> {
            updateDaysInMonth(endDayBox, endMonthBox, endYearBox);
            updateRentalPrices();
        });
    }
}