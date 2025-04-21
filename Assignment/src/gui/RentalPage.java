package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

import datamodels.*;

public class RentalPage extends JPanel {

    private final JFrame frame;
    private final JPanel panel;
    private JScrollPane mainScrollPane;
    private JLabel totalPriceLabel;

    protected final float TITLE_TEXT_SIZE = 20f;
    protected final float NORMAL_TEXT_SIZE = 28f;
    protected final float BUTTON_TEXT_SIZE = 30f;
    protected final Border BORDER = new LineBorder(Color.BLACK, 2);
    protected final Border PADDING = new EmptyBorder(10, 15, 10, 15);
    protected final Font TITLE_FONT = CustomFonts.ROBOTO_SEMI_BOLD;
    protected final Font INPUT_FONT = CustomFonts.OPEN_SANS_REGULAR;

    private GridBagConstraints gbc = new GridBagConstraints();

    private Car carSelected;

    public RentalPage(JFrame frame, JPanel panel, Car selectedCar) {
        this.frame = frame;
        this.panel = panel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        carSelected = selectedCar;

        // scrollable rental page container
        JSplitPane rentalPage = createRentalPage();
        this.add(rentalPage, BorderLayout.CENTER);
    }

    // create scrollable container (from VehiclesPage)
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
        splitPane.setDividerLocation(800);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setDividerSize(5);

        // check border size
        // splitPane.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        // leftContainer.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        // rightContainer.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));

        return splitPane;
    }

    private JPanel createLeftContainer() {
        // create container for left panel
        RoundedPanel leftContainer = new RoundedPanel(30, Theme.getSpecial());
        leftContainer.setLayout(new BorderLayout(0, 5));
        leftContainer.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));

        leftContainer.add(createTitlePanel("Rental Summary"), BorderLayout.NORTH);
        leftContainer.add(createRentalSummary(), BorderLayout.CENTER);

        leftContainer.setPreferredSize(leftContainer.getPreferredSize());

        return leftContainer;
    }

    private JPanel createRentalSummary() {
        // rental car summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BorderLayout(0, 10));
        summaryPanel.setBackground(Theme.getSpecial());

        // rental car summary container
        RoundedPanel carPanel = new RoundedPanel(20, Color.WHITE);
        carPanel.setLayout(new BorderLayout());
        carPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // car image -> get image path from car object
        ImageIcon image = null;
        try {
            image = new ImageIcon(carSelected.getImagePath());

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
        JLabel carNameLabel = createLabel(carSelected.getBrand() + " " + carSelected.getModel(),
                40f, Color.BLACK, Color.WHITE, 50, -1);
        carNameLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carTransmissionLabel = createLabel(carSelected.getTransmission() + " Transmission",
                20f, Color.BLACK, Color.WHITE, 50, -1);
        carTransmissionLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carFuelLabel = createLabel(carSelected.getFuelType(),
                20f, Color.BLACK, Color.WHITE, 50, -1);
        carFuelLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carRentalPricLabel = createLabel("RM " + carSelected.getRentalPriceDay() + "/day",
                20f, Color.BLACK, Color.WHITE, 50, -1);
        carRentalPricLabel.setHorizontalAlignment(JLabel.LEFT);

        // gblayout gridy position
        gbc.gridy = 0;
        carDetailsPanel.add(carNameLabel, gbc);
        gbc.insets.left = 30; // indent for other labels
        gbc.gridy = 1;
        carDetailsPanel.add(carTransmissionLabel, gbc);
        gbc.gridy = 2;
        carDetailsPanel.add(carFuelLabel, gbc);
        gbc.gridy = 3;
        carDetailsPanel.add(carRentalPricLabel, gbc);
        gbc.insets.left = 0; // reset indent

        // add label in panel
        carPanel.add(carImageLabel, BorderLayout.WEST);
        carPanel.add(carDetailsPanel, BorderLayout.CENTER);

        summaryPanel.add(carPanel, BorderLayout.NORTH);

        // add pricing details
        RoundedPanel pricingPanel = new RoundedPanel(20, Color.WHITE);
        pricingPanel.setLayout(new GridBagLayout());
        pricingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // pricing details text labels
        JLabel rentalPriceTextLabel = createLabel("Rental Price : ", 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        rentalPriceTextLabel.setOpaque(false);
        rentalPriceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel rentalPriceLabel = createLabel("RM " + carSelected.getRentalPriceDay(), 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        rentalPriceLabel.setOpaque(false);
        rentalPriceLabel.setHorizontalAlignment(JLabel.RIGHT);

        JLabel insuranceTextLabel = createLabel("Insurance Price : ", 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        insuranceTextLabel.setOpaque(false);
        insuranceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel insurancePriceLabel = createLabel(
                "RM " + String.format("%.2f", (carSelected.getRentalPriceDay() * 0.1)), 20f,
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
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel totalPriceTextLabel = createLabel("Total Price : ", 20f,
                Color.BLACK, Color.WHITE, 30, -1);
        totalPriceTextLabel.setOpaque(false);
        totalPriceTextLabel.setHorizontalAlignment(JLabel.LEFT);

        totalPriceLabel = createLabel("RM " + carSelected.getRentalPriceDay(), 20f,
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
        RoundedPanel rightContainer = new RoundedPanel(30, Theme.getSpecial());

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

        JPanel billingContainer = createBillingInfoPanel();
        JPanel rentalContainer = createRentalDetailsPanel();
        JPanel paymentContainer = createPaymentMethodContainer();

        gbc.gridy = 0;
        rightContainer.add(billingContainer, gbc);
        gbc.gridy = 1;
        rightContainer.add(rentalContainer, gbc);
        gbc.gridy = 2;
        rightContainer.add(paymentContainer, gbc);

        return rightContainer;
    }

    // demo
    private JPanel createBillingInfoPanel() {
        JPanel billingContainer = new JPanel(new BorderLayout());
        billingContainer.setBackground(Theme.getSpecial());
        billingContainer.add(createTitlePanel("Billing Info"), BorderLayout.NORTH);

        RoundedPanel billingInfoPanel = new RoundedPanel(20, Color.WHITE);
        billingInfoPanel.setLayout(new GridBagLayout());
        billingInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        billingInfoPanel.add(createInputContainer("Billing Address: ", 4, true), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        billingInfoPanel.add(createInputContainer("Billing Name: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        billingInfoPanel.add(createInputContainer("Billing Contact: ", 1, false), gbc);

        billingContainer.add(billingInfoPanel, BorderLayout.CENTER);

        return billingContainer;
    }

    // demo
    private JPanel createRentalDetailsPanel() {
        JPanel rentalContainer = new JPanel(new BorderLayout());
        rentalContainer.setBackground(Theme.getSpecial());
        rentalContainer.add(createTitlePanel("Rental Details"), BorderLayout.NORTH);

        RoundedPanel rentalDetailsPanel = new RoundedPanel(20, Color.WHITE);
        rentalDetailsPanel.setLayout(new GridBagLayout());
        rentalDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        rentalDetailsPanel.add(createInputContainer("Rental Details 1: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        rentalDetailsPanel.add(createInputContainer("Rental Details 2: ", 2, true), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rentalDetailsPanel.add(createInputContainer("Rental Details 3: ", 4, true), gbc);

        rentalContainer.add(rentalDetailsPanel, BorderLayout.CENTER);

        return rentalContainer;
    }

    // demo
    private JPanel createPaymentMethodContainer() {
        JPanel paymentContainer = new JPanel(new BorderLayout());
        paymentContainer.setBackground(Theme.getSpecial());
        paymentContainer.add(createTitlePanel("Payment Method"), BorderLayout.NORTH);

        RoundedPanel paymentMethodPanel = new RoundedPanel(20, Color.WHITE);
        paymentMethodPanel.setLayout(new GridBagLayout());
        paymentMethodPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentMethodPanel.add(createInputContainer("Payment Method: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        paymentMethodPanel.add(createInputContainer("Credit / Debit Card: ", false), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        paymentMethodPanel.add(createInputContainer("Expiry Date: ", 1, true), gbc);

        paymentContainer.add(paymentMethodPanel, BorderLayout.CENTER);

        return paymentContainer;
    }

    // create input container (from AuthenticationPage)
    protected JPanel createInputContainer(String title, int initialRows, boolean scrollable) {
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
        } else {
            // use text field for single line input
            inputComponent = inputLineField;
        }

        inputPanel.add(inputComponent, gbc);
        return inputPanel;
    }

    protected JPanel createInputContainer(String title, boolean scrollable) {
        return createInputContainer(title, 1, scrollable);
    }

    // create title panel
    private JPanel createTitlePanel(String title) {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Theme.getSpecial());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 0));

        JLabel titleLabel = createLabel(title, 30f, Color.WHITE, Theme.getSpecial(), 50, -1);
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
}