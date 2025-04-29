package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import datamodels.*;

public class RentalPage extends JPanel {

    private final JFrame frame;
    private final JPanel panel;
    private JScrollPane mainScrollPane;
    private JLabel totalPriceLabel;
    private final Map<String, JComponent> inputFieldsMap = new HashMap<>();

    private final float TITLE_TEXT_SIZE = 20f;
    private final float NORMAL_TEXT_SIZE = 28f;
    private final float BUTTON_TEXT_SIZE = 30f;
    private final Border BORDER = new LineBorder(Color.BLACK, 2);
    private final Border PADDING = new EmptyBorder(10, 15, 10, 15);
    private final Font TITLE_FONT = CustomFonts.ROBOTO_SEMI_BOLD;
    private final Font INPUT_FONT = CustomFonts.OPEN_SANS_REGULAR;

    private GridBagConstraints gbc = new GridBagConstraints();

    private static Vehicle vehicleSelected;

    public RentalPage(JFrame frame, JPanel panel, Vehicle selectedVehicle) {
        this.frame = frame;
        this.panel = panel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        vehicleSelected = selectedVehicle;

        // scrollable rental page container
        JSplitPane rentalPage = createRentalPage();
        this.add(rentalPage, BorderLayout.CENTER);
    }

    public static void setVehicleSelected(Vehicle vehicleSelected) {
        RentalPage.vehicleSelected = vehicleSelected;
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

        // check border size
        // splitPane.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        // leftContainer.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        // rightContainer.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));

        return splitPane;
    }

    // rental page left container
    private JPanel createLeftContainer() {
        // create container for left panel
        RoundedPanel leftContainer = new RoundedPanel(30, Theme.getBackground());
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
        RoundedPanel carPanel = new RoundedPanel(20, Color.WHITE);
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
        Image rImage = image.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(rImage);
        JLabel carImageLabel = new JLabel(image);
        carImageLabel.setHorizontalAlignment(JLabel.CENTER);

        // rental car details summary text
        JPanel carDetailsPanel = new JPanel();
        carDetailsPanel.setLayout(new GridBagLayout());
        carDetailsPanel.setBackground(Color.WHITE);
        carDetailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // car details text labels
        JLabel carNameLabel = createLabel(vehicleSelected.getBrand() + " " + vehicleSelected.getModel(),
                30f, Color.BLACK, Color.WHITE, 50, -1);
        carNameLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carTransmissionLabel = createLabel(vehicleSelected.getTransmission() + " Transmission",
                20f, Color.BLACK, Color.WHITE, 50, -1);
        carTransmissionLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carFuelLabel = createLabel(vehicleSelected.getFuelType(),
                20f, Color.BLACK, Color.WHITE, 50, -1);
        carFuelLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carRentalPriceLabel = createLabel("RM " + vehicleSelected.getRentalPriceDay() + "/day",
                20f, Color.BLACK, Color.WHITE, 50, -1);
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
        RoundedPanel pricingPanel = new RoundedPanel(20, Color.WHITE);
        pricingPanel.setLayout(new GridBagLayout());
        pricingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 100));

        // pricing details text labels
        JLabel rentalPriceTextLabel = createLabel("Rental Price : ", 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        rentalPriceTextLabel.setOpaque(false);
        rentalPriceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel rentalPriceLabel = createLabel("RM " + vehicleSelected.getRentalPriceDay(), 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        rentalPriceLabel.setOpaque(false);
        rentalPriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        JLabel insuranceTextLabel = createLabel("Insurance Price : ", 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        insuranceTextLabel.setOpaque(false);
        insuranceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel insurancePriceLabel = createLabel(
                "RM " + String.format("%.2f", (vehicleSelected.getRentalPriceDay() * 0.1)), 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        insurancePriceLabel.setOpaque(false);
        insurancePriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        // rental price per day
        gbc.gridx = 0;
        gbc.gridy = 0;
        pricingPanel.add(rentalPriceTextLabel, gbc);
        gbc.gridx = 1;
        pricingPanel.add(rentalPriceLabel, gbc);

        // insurance price per day
        gbc.gridx = 0;
        gbc.gridy = 1;
        pricingPanel.add(insuranceTextLabel, gbc);
        gbc.gridx = 1;
        pricingPanel.add(insurancePriceLabel, gbc);

        summaryPanel.add(pricingPanel, BorderLayout.CENTER);

        summaryPanel.add(createTotalPricePanel(), BorderLayout.SOUTH);

        return summaryPanel;
    }

    private RoundedPanel createTotalPricePanel() {
        // total price
        RoundedPanel totalPanel = new RoundedPanel(20, Color.WHITE);
        totalPanel.setLayout(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 100));

        JLabel totalPriceTextLabel = createLabel("Total Price : ", 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        totalPriceTextLabel.setOpaque(false);
        totalPriceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        totalPriceLabel = createLabel("RM " + vehicleSelected.getRentalPriceDay(), 40f,
                Color.BLACK, Color.WHITE, 30, -1);
        totalPriceLabel.setOpaque(false);
        totalPriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        totalPanel.add(totalPriceTextLabel, BorderLayout.WEST);
        totalPanel.add(totalPriceLabel, BorderLayout.EAST);

        return totalPanel;
    }

    // rental page right container
    private JPanel createRightContainer() {
        // create container for inputs
        RoundedPanel rightContainer = new RoundedPanel(30, Theme.getBackground());

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
        JButton confirmButton = createButton("Confirm", Theme.getSpecial(), Theme.getForeground(), 200, 50);

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

    // demo
    private JPanel createBillingInfoPanel() {
        JPanel billingContainer = new JPanel(new BorderLayout());
        billingContainer.setBackground(Theme.getBackground());
        billingContainer.add(createTitlePanel("Billing Info"), BorderLayout.NORTH);

        RoundedPanel billingInfoPanel = new RoundedPanel(20, Color.WHITE);
        billingInfoPanel.setLayout(new GridBagLayout());
        billingInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        billingInfoPanel.add(createInputContainer("B_Address", "Billing Address: ", 4, true), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        billingInfoPanel.add(createInputContainer("B_Name", "Billing Name: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        billingInfoPanel.add(createInputContainer("B_Contact", "Billing Contact: ", 1, false), gbc);

        billingContainer.add(billingInfoPanel, BorderLayout.CENTER);

        return billingContainer;
    }

    // demo
    private JPanel createRentalDetailsPanel() {
        JPanel rentalContainer = new JPanel(new BorderLayout());
        rentalContainer.setBackground(Theme.getBackground());
        rentalContainer.add(createTitlePanel("Rental Details"), BorderLayout.NORTH);

        RoundedPanel rentalDetailsPanel = new RoundedPanel(20, Color.WHITE);
        rentalDetailsPanel.setLayout(new GridBagLayout());
        rentalDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        rentalDetailsPanel.add(createInputContainer("R_Details1", "Rental Details 1: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        rentalDetailsPanel.add(createInputContainer("R_Details2", "Rental Details 2: ", 2, true), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rentalDetailsPanel.add(createInputContainer("R_Details3", "Rental Details 3: ", 4, true), gbc);

        rentalContainer.add(rentalDetailsPanel, BorderLayout.CENTER);

        return rentalContainer;
    }

    // Payment Method
    private JPanel createPaymentMethodContainer() {
        JPanel paymentContainer = new JPanel(new BorderLayout());
        paymentContainer.setBackground(Theme.getBackground());
        paymentContainer.add(createTitlePanel("Payment Method"), BorderLayout.NORTH);

        RoundedPanel paymentMethodPanel = new RoundedPanel(20, Color.WHITE);
        paymentMethodPanel.setLayout(new GridBagLayout());
        paymentMethodPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentMethodPanel.add(createInputContainer("P_Method", "Payment Method: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        paymentMethodPanel.add(createInputContainer("C_D_Card", "Credit / Debit Card: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        paymentMethodPanel.add(createInputContainer("E_Date", "Expiry Date: ", 1, true), gbc);

        paymentContainer.add(paymentMethodPanel, BorderLayout.CENTER);

        return paymentContainer;
    }

    // create input container (from AuthenticationPage)
    private JPanel createInputContainer(String fieldname, String title, int initialRows, boolean scrollable) {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // input title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        titleLabel.setForeground(Color.BLACK);
        inputPanel.add(titleLabel, gbc);

        gbc.gridy++;

        // input text field
        JTextField inputLineField = new JTextField();
        inputLineField.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        inputLineField.setPreferredSize(new Dimension(700, 50));
        inputLineField.setMinimumSize(new Dimension(700, 50));
        inputLineField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        inputLineField.setBorder(new CompoundBorder(BORDER, PADDING));

        // input text area
        JTextArea inputField = new JTextArea(initialRows, 20);
        inputField.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        inputField.setForeground(Color.BLACK);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

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

        JLabel titleLabel = createLabel(title, 30f, Theme.getSpecial(), Theme.getBackground(), 50, -1);
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
        titleLabel.setHorizontalAlignment(JLabel.LEFT);

        titlePanel.add(titleLabel);

        return titlePanel;
    }

    // create label instance
    private JLabel createLabel(String text, Float fontSize, Color textColor, Color bgColor, Integer height,
            Integer width) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(fontSize));
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
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}