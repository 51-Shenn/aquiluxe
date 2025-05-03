package gui;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controllers.RentalController;
import controllers.UserController;
import database.UserDAO;
import datamodels.User;
import datamodels.Customer;
import datamodels.Admin;
import datamodels.Rental;
import datamodels.Vehicle;
import datamodels.Rental.PaymentStatus;
import datamodels.Rental.RentalStatus;
import services.RentalService.FilterType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalHistory extends JPanel {

    private JFrame frame;
    private JPanel panel;
    private User user;
    private Customer customer = null;
    private Admin admin = null;
    // private Rental rental;
    private List<Rental> customerRentalRecords = new ArrayList<>();
    private Map<Rental, JPanel> rentalPanelMap = new HashMap<>();
    private RentalController rentalController = new RentalController();

    private GridBagConstraints gbc = new GridBagConstraints();

    public RentalHistory(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        setLayout(new BorderLayout());

        try {
            // set customer
            File accountsFile = new File("files/settings/accounts.txt");
            this.user = UserController.loadCurrentUser(accountsFile);
            System.out.println(this.user.getFullName() + " loaded user");

            if (this.user.getUserType().equals("Customer")) {
                System.out.println(this.user.getFullName() + "C1");
                this.customer = UserDAO.getCustomerById(this.user);
                System.out.println(this.customer.getFullName() + "C1");
            } else if (this.user.getUserType().equals("Admin")) {
                System.out.println(this.user.getFullName() + "A1");
                this.admin = UserDAO.getAdminById(this.user);
                System.out.println(this.admin.getFullName() + "A1");
            } else {
                System.out.println(this.user.getFullName() + "G1");
                this.customer = UserDAO.getCustomerById(this.user);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Dialog dialogError = new Dialog();
            dialogError.showDialog("ERROR",
                    "Account",
                    "No Account Signed In",
                    "Please Sign In or Create an account",
                    false);
            return;
        }

        // get rental history of user
        RentalController rentalController = new RentalController();
        if (this.customer != null) {
            this.customerRentalRecords = rentalController.getRentalHistoryOfUser(FilterType.USER, this.user);
        } else if (this.admin != null) {
            this.customerRentalRecords = rentalController.getRentalHistoryOfUser(FilterType.RENTAL_STATUS,
                    RentalStatus.PENDING, RentalStatus.APPROVED, RentalStatus.ACTIVE);
        }

        JLabel title = createTitlePanel(); // title
        title.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        JScrollPane rentalHistoryPage = createRentalHistoryPage(); // content below title

        this.add(title, BorderLayout.NORTH);
        this.add(rentalHistoryPage, BorderLayout.CENTER);
    }

    private JLabel createTitlePanel() {
        // Title
        JLabel titleLabel = new JLabel("Rental History", SwingConstants.CENTER);
        titleLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(40f));
        titleLabel.setForeground(Theme.getSpecial());
        titleLabel.setBackground(Theme.getBackground());
        titleLabel.setOpaque(true);

        return titleLabel;
    }

    private JScrollPane createRentalHistoryPage() {

        JPanel rentalHistoryContainer = new JPanel();
        rentalHistoryContainer.setBackground(Theme.getBackground());
        rentalHistoryContainer.setLayout(new GridBagLayout());
        rentalHistoryContainer.setBorder(BorderFactory.createEmptyBorder(50, 150, 100, 150));

        // create multiple vertical rows of rental panels
        // gbc gridy ++
        int row = 0;
        for (Rental rental : customerRentalRecords) {
            if (row > 0) {
                JPanel spacing = new JPanel();
                spacing.setPreferredSize(new Dimension(1000, 5));
                spacing.setOpaque(true);
                spacing.setBackground(Theme.getSecondaryForeground());

                gbc.gridx = 0;
                gbc.gridy = row++;
                rentalHistoryContainer.add(spacing, gbc);
            }

            JPanel rentalPanel = createRentalPanel(rental);
            rentalPanelMap.put(rental, rentalPanel);

            gbc.gridx = 0;
            gbc.gridy = row++;
            rentalHistoryContainer.add(rentalPanel, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(rentalHistoryContainer);
        scrollPane.setBackground(Theme.getError());
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setMinimumSize(new Dimension(900, 900));

        return scrollPane;
    }

    private JPanel createRentalPanel(Rental rental) {
        // add components in this container
        JPanel rentalPanelContainer = new JPanel();
        rentalPanelContainer.setLayout(new GridBagLayout());
        rentalPanelContainer.setBorder(new EmptyBorder(10, 0, 10, 0));
        rentalPanelContainer.setBackground(Theme.getBackground());

        // rental car summary container
        RoundedPanel carPanel = new RoundedPanel(20, Theme.getBackground());
        carPanel.setLayout(new BorderLayout());
        carPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // car image -> get image path from car object
        ImageIcon image = null;
        try {
            image = new ImageIcon(rental.getRentVehicle().getImagePath());

            // Check if any image failed to load
            if (image.getIconWidth() == -1) {
                throw new Exception("One or more images failed to load.");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
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
        JLabel carNameLabel = createLabel(
                rental.getRentVehicle().getBrand() + " "
                        + rental.getRentVehicle().getModel(),
                30f, Theme.getForeground(), Theme.getBackground(), 50, -1);
        carNameLabel.setHorizontalAlignment(JLabel.LEFT);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = rental.getRentStartDate();
        String startDateText = "Start: " + startDate.format(dateFormatter);
        JLabel carTransmissionLabel = createLabel(
                startDateText,
                25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
        carTransmissionLabel.setHorizontalAlignment(JLabel.LEFT);

        LocalDate endDate = rental.getRentEndDate();
        String endDateText = "End: " + endDate.format(dateFormatter);
        JLabel carFuelLabel = createLabel(
                endDateText,
                25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
        carFuelLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel carRentalPriceLabel = createLabel(
                "RM " + String.format("%,.2f", rental.getRentTotalCost()),
                25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
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

        // rental details : rental status, payment status, payment date
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(Theme.getBackground());
        detailsPanel.setLayout(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (this.customer != null) {
            // rental status
            JLabel rentalStatusLabel = createLabel(
                    "Rental Status: " + rental.getRentalStatus().toString(),
                    25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
            rentalStatusLabel.setHorizontalAlignment(JLabel.LEFT);
            rentalStatusLabel.setName("rentalStatus");

            // payment status
            JLabel paymentStatusLabel = createLabel(
                    "Payment Status: " + rental.getPaymentStatus().toString(),
                    25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
            paymentStatusLabel.setHorizontalAlignment(JLabel.LEFT);
            paymentStatusLabel.setName("paymentStatus");

            // payment date
            LocalDate paymentDate = rental.getRentStartDate();
            String paymentDateText = "Payment Date: " + paymentDate.format(dateFormatter);
            JLabel paymentDateLabel = createLabel(
                    paymentDateText,
                    25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
            paymentDateLabel.setHorizontalAlignment(JLabel.LEFT);
            paymentDateLabel.setName("paymentDate");

            gbc.gridx = 0;
            gbc.gridy = 0;
            detailsPanel.add(rentalStatusLabel, gbc);
            gbc.gridy = 1;
            detailsPanel.add(paymentStatusLabel, gbc);
            gbc.gridy = 2;
            detailsPanel.add(paymentDateLabel, gbc);
        }

        if (this.admin != null) {
            // customer name
            JLabel customerNameLabel = createLabel(
                    rental.getRentCustomer().getFullName(),
                    25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
            customerNameLabel.setHorizontalAlignment(JLabel.LEFT);
            customerNameLabel.setName("customerName");

            // customer ic
            JLabel customerICLabel = createLabel(
                    rental.getRentCustomer().getLicense(),
                    25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
            customerICLabel.setHorizontalAlignment(JLabel.LEFT);
            customerICLabel.setName("customerIC");

            // rental status
            JLabel rentalStatusLabel = createLabel(
                    "Rental Status: " + rental.getRentalStatus().toString(),
                    25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
            rentalStatusLabel.setHorizontalAlignment(JLabel.LEFT);
            rentalStatusLabel.setName("rentalStatus");

            // payment status
            JLabel paymentStatusLabel = createLabel(
                    "Payment Status: " + rental.getPaymentStatus().toString(),
                    25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
            paymentStatusLabel.setHorizontalAlignment(JLabel.LEFT);
            paymentStatusLabel.setName("paymentStatus");

            // payment date
            LocalDate paymentDate = rental.getRentStartDate();
            String paymentDateText = "Payment Date: " + paymentDate.format(dateFormatter);
            JLabel paymentDateLabel = createLabel(
                    paymentDateText,
                    25f, Theme.getForeground(), Theme.getBackground(), 50, -1);
            paymentDateLabel.setHorizontalAlignment(JLabel.LEFT);
            paymentDateLabel.setName("paymentDate");

            gbc.gridx = 0;
            gbc.gridy = 0;
            detailsPanel.add(customerNameLabel, gbc);
            gbc.gridy = 1;
            detailsPanel.add(customerICLabel, gbc);
            gbc.gridy = 2;
            gbc.insets.left = 30;
            detailsPanel.add(rentalStatusLabel, gbc);
            gbc.gridy = 3;
            detailsPanel.add(paymentStatusLabel, gbc);
            gbc.gridy = 4;
            detailsPanel.add(paymentDateLabel, gbc);
            gbc.insets.left = 0;
        }

        // rental operations : cancel, view vehicle details
        JPanel operationsPanel = new JPanel();
        operationsPanel.setBackground(Theme.getBackground());
        operationsPanel.setLayout(new GridBagLayout());
        operationsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // check usertype if admin - approve, reject, pick up
        // if customer - cancel, view details, etc
        // cancel rental button
        if (this.customer != null) {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(30f));
            cancelButton.setBackground(Theme.getError());
            cancelButton.setForeground(Theme.getErrorForeground());
            cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            cancelButton.setPreferredSize(new Dimension(100, 75));
            cancelButton.setBorderPainted(false);
            cancelButton.setFocusPainted(false);
            cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cancelButton.addActionListener(e -> {
                cancelButton.setVisible(false);
                rental.setRentalStatus(RentalStatus.CANCELLED);

                if (rental.getPaymentStatus() == PaymentStatus.PAID) {
                    rental.setPaymentStatus(PaymentStatus.REFUNDED);
                    Dialog dialog = new Dialog(this.frame);
                    dialog.showDialog("SUCCESS",
                            "Refund",
                            "Refund On the Way",
                            "Please check your email inbox.",
                            false);
                }

                updateRentalPanel(rental);
                rentalController.updateRental(rental, rental.getRentalStatus());
                GUIComponents.rentalHistoryPanel.revalidate();
                GUIComponents.rentalHistoryPanel.repaint();

                System.out.println(rental.getRentVehicle().getModel() +
                        rental.getRentalStatus().toString() + rental.getPaymentStatus().toString());
            });

            if (!rental.getRentalStatus().equals(RentalStatus.PENDING)) {
                cancelButton.setVisible(false);
            }

            // view vehicle details button
            JButton viewVehicleButton = new JButton(
                    "Vehicle Details >");
            viewVehicleButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));
            viewVehicleButton.setBackground(Theme.getBackground());
            viewVehicleButton.setForeground(Theme.getForeground());
            viewVehicleButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            viewVehicleButton.setPreferredSize(new Dimension(250, 50));
            viewVehicleButton.setBorderPainted(false);
            viewVehicleButton.setFocusPainted(false);
            viewVehicleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            viewVehicleButton.addActionListener(e -> {
                System.out.println(GUIComponents.cardPanel.getComponents().length);
                JPanel vehicleDetailsPanel = new VehiclesPageDetails(frame, panel, rental.getRentVehicle(),
                        Vehicle.getVehicles());
                GUIComponents.cardPanel.add(vehicleDetailsPanel, "VehicleDetailsPage");
                vehicleDetailsPanel.revalidate();
                vehicleDetailsPanel.repaint();
                GUIComponents.cardLayout.show(GUIComponents.cardPanel, "VehicleDetailsPage");
                GUIComponents.cardPanel.revalidate();
                GUIComponents.cardPanel.repaint();
                System.out.println(GUIComponents.cardPanel.getComponents().length);
            });

            JPanel spacing = new JPanel();
            spacing.setPreferredSize(new Dimension(250, 100));
            spacing.setOpaque(true);
            spacing.setBackground(Theme.getBackground());

            gbc.gridx = 0;
            gbc.gridy = 0;
            operationsPanel.add(cancelButton, gbc);
            gbc.gridy = 1;
            operationsPanel.add(spacing, gbc);
            gbc.gridy = 2;
            operationsPanel.add(viewVehicleButton, gbc);
        }

        if (this.admin != null) {
            JButton approveButton = new JButton("Approve");
            approveButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(30f));
            approveButton.setBackground(Theme.getSuccess());
            approveButton.setForeground(Theme.getSuccessForeground());
            approveButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            approveButton.setPreferredSize(new Dimension(100, 50));
            approveButton.setBorderPainted(false);
            approveButton.setFocusPainted(false);
            approveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            approveButton.addActionListener(e -> {
                approveButton.setVisible(false);
                rental.setRentalStatus(RentalStatus.APPROVED);
                updateRentalPanel(rental);
                rentalController.updateRental(rental, rental.getRentalStatus());
                GUIComponents.rentalHistoryPanel.revalidate();
                GUIComponents.rentalHistoryPanel.repaint();

                System.out.println(rental.getRentVehicle().getModel() +
                        rental.getRentalStatus().toString() + rental.getPaymentStatus().toString());
            });

            JButton rejectButton = new JButton("Reject");
            rejectButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(30f));
            rejectButton.setBackground(Theme.getError());
            rejectButton.setForeground(Theme.getErrorForeground());
            rejectButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            rejectButton.setPreferredSize(new Dimension(100, 50));
            rejectButton.setBorderPainted(false);
            rejectButton.setFocusPainted(false);
            rejectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            rejectButton.addActionListener(e -> {
                rejectButton.setVisible(false);
                rental.setRentalStatus(RentalStatus.CANCELLED);
                updateRentalPanel(rental);
                rentalController.updateRental(rental, rental.getRentalStatus());
                GUIComponents.rentalHistoryPanel.revalidate();
                GUIComponents.rentalHistoryPanel.repaint();

                System.out.println(rental.getRentVehicle().getModel() +
                        rental.getRentalStatus().toString() + rental.getPaymentStatus().toString());
            });

            JButton pickUpButton = new JButton("Pick Up");
            pickUpButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(30f));
            pickUpButton.setBackground(Theme.getSpecial());
            pickUpButton.setForeground(Theme.getSpecialForeground());
            pickUpButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            pickUpButton.setPreferredSize(new Dimension(100, 50));
            pickUpButton.setBorderPainted(false);
            pickUpButton.setFocusPainted(false);
            pickUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pickUpButton.addActionListener(e -> {
                pickUpButton.setVisible(false);
                rental.setRentalStatus(RentalStatus.ACTIVE);
                updateRentalPanel(rental);
                rentalController.updateRental(rental, rental.getRentalStatus());
                GUIComponents.rentalHistoryPanel.revalidate();
                GUIComponents.rentalHistoryPanel.repaint();

                System.out.println(rental.getRentVehicle().getModel() +
                        rental.getRentalStatus().toString() + rental.getPaymentStatus().toString());
            });

            JButton dropOffButton = new JButton("Drop Off");
            dropOffButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(30f));
            dropOffButton.setBackground(Theme.getSpecial());
            dropOffButton.setForeground(Theme.getSpecialForeground());
            dropOffButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            dropOffButton.setPreferredSize(new Dimension(100, 50));
            dropOffButton.setBorderPainted(false);
            dropOffButton.setFocusPainted(false);
            dropOffButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            dropOffButton.addActionListener(e -> {
                dropOffButton.setVisible(false);
                rental.setRentalStatus(RentalStatus.COMPLETED);
                updateRentalPanel(rental);
                rentalController.updateRental(rental, rental.getRentalStatus());
                GUIComponents.rentalHistoryPanel.revalidate();
                GUIComponents.rentalHistoryPanel.repaint();

                System.out.println(rental.getRentVehicle().getModel() +
                        rental.getRentalStatus().toString() + rental.getPaymentStatus().toString());
            });

            // view vehicle details button
            JButton viewVehicleButton = new JButton(
                    "Vehicle Details >");
            viewVehicleButton.setFont(CustomFonts.OPEN_SANS_REGULAR.deriveFont(25f));
            viewVehicleButton.setBackground(Theme.getBackground());
            viewVehicleButton.setForeground(Theme.getForeground());
            viewVehicleButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            viewVehicleButton.setPreferredSize(new Dimension(250, 50));
            viewVehicleButton.setBorderPainted(false);
            viewVehicleButton.setFocusPainted(false);
            viewVehicleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            viewVehicleButton.addActionListener(e -> {
                System.out.println(GUIComponents.cardPanel.getComponents().length);
                JPanel vehicleDetailsPanel = new VehiclesPageDetails(frame, panel, rental.getRentVehicle(),
                        Vehicle.getVehicles());
                GUIComponents.cardPanel.add(vehicleDetailsPanel, "VehicleDetailsPage");
                vehicleDetailsPanel.revalidate();
                vehicleDetailsPanel.repaint();
                GUIComponents.cardLayout.show(GUIComponents.cardPanel, "VehicleDetailsPage");
                GUIComponents.cardPanel.revalidate();
                GUIComponents.cardPanel.repaint();
                System.out.println(GUIComponents.cardPanel.getComponents().length);
            });

            // JPanel spacing = new JPanel();
            // spacing.setPreferredSize(new Dimension(250, 100));
            // spacing.setOpaque(true);
            // spacing.setBackground(Theme.getBackground());

            gbc.gridx = 0;
            gbc.gridy = 0;
            operationsPanel.add(approveButton, gbc);
            gbc.gridy = 1;
            operationsPanel.add(rejectButton, gbc);
            gbc.gridy = 2;
            operationsPanel.add(pickUpButton, gbc);
            gbc.gridy = 3;
            operationsPanel.add(dropOffButton, gbc);
            gbc.gridy = 4;
            operationsPanel.add(viewVehicleButton, gbc);
        }

        gbc.gridy = 0;
        gbc.gridx = 0;
        rentalPanelContainer.add(carPanel, gbc);
        gbc.gridx = 1;
        rentalPanelContainer.add(detailsPanel, gbc);
        gbc.gridx = 2;
        rentalPanelContainer.add(operationsPanel, gbc);
        gbc.gridx = 0;

        return rentalPanelContainer;
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

    private void updateRentalPanel(Rental rental) {
        JPanel panel = rentalPanelMap.get(rental);
        if (panel != null) {
            for (JLabel label : getAllJLabels(panel)) {
                if ("rentalStatus".equals(label.getName())) {
                    label.setText("Rental Status: " + rental.getRentalStatus().toString());
                    label.revalidate();
                    label.repaint();
                }

                if ("paymentStatus".equals(label.getName())) {
                    label.setText("Payment Status: " + rental.getPaymentStatus().toString());
                    label.revalidate();
                    label.repaint();
                }
            }
            panel.revalidate();
            panel.repaint();
        }
    }

    private static List<JLabel> getAllJLabels(Container container) {
        List<JLabel> labels = new ArrayList<>();
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel) {
                labels.add((JLabel) comp);
            } else if (comp instanceof Container) {
                labels.addAll(getAllJLabels((Container) comp));
            }
        }
        return labels;
    }
}
