package gui;

import controllers.UserController;
import datamodels.User;
import datamodels.Vehicle;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
<<<<<<< HEAD
import java.util.List;

=======
import java.io.File;
>>>>>>> 7289e0cfaa9c713c5fb38bbb3466d5dddf5e5ea5
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import controllers.VehicleController;

import java.awt.Color;

public class GUIComponents extends JPanel {

    private JFrame frame;
    private JPanel panel;
    private User user;
    private static JButton[] buttons;
    public static OverflowMenu overflowMenu;
<<<<<<< HEAD
    private static JButton[] topBarButtons = new JButton[4];
    private String[] topBarButtonsLabels = { "Home", "Vehicles", "About", "Contact" };
=======
    private JButton[] topBarButtons = new JButton[5];
    private String[] topBarButtonsLabels = { "Home", "Vehicles", "About", "Contact", "Rentals" };
>>>>>>> 7289e0cfaa9c713c5fb38bbb3466d5dddf5e5ea5

    public static JPanel homePanel;
    public static JPanel vehiclesPanel;
    public static JPanel aboutUsPanel;
    public static JPanel contactUsPanel;
    public static JPanel rentalHistoryPanel;
    public static CardLayout cardLayout;
    public static JPanel cardPanel;
    public static boolean vehiclesPageLoaded = false;

    public GUIComponents() {
        this(new JFrame(), new JPanel(), new User());
    }

    public GUIComponents(JFrame frame, JPanel panel, User user) {
        Navigation.setWindowsLookAndFeel();

        this.frame = frame;
        this.panel = panel;
        this.user = user;
        setBackground(Theme.getBackground());
        setPreferredSize(new Dimension(frame.getWidth(), 80));
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Theme.getTransparencyColor(), 1));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Theme.getBackground());
        cardPanel.setBorder(new LineBorder(Color.RED, 3));

        setBorder(new LineBorder(Color.YELLOW, 3));

        homePanel = new HomePage(this.frame, this.panel, this.user, this);
<<<<<<< HEAD
        homePanel.setBorder(new LineBorder(Color.GREEN, 3));
        vehiclesPanel = new VehiclesPage(this.frame, this.panel);
=======
        vehiclesPanel = new VehiclesPage(this.frame, this.panel, this.user);
>>>>>>> 7289e0cfaa9c713c5fb38bbb3466d5dddf5e5ea5
        aboutUsPanel = new AboutUsPage(this.frame, this.panel);
        contactUsPanel = new ContactUsPage(this.frame, this.panel);
        rentalHistoryPanel = new RentalHistory(this.frame, this.panel, this.user);

        cardPanel.add(homePanel, "HomePage");
        cardPanel.add(vehiclesPanel, "VehiclesPage");
        cardPanel.add(aboutUsPanel, "AboutUsPage");
        cardPanel.add(contactUsPanel, "ContactUsPage");
        cardPanel.add(rentalHistoryPanel, "RentalHistoryPage");
        // switch user, sign out - remove rentalhistory - add back with current user

        add(createTopBar(), BorderLayout.WEST);
        add(menuButton(), BorderLayout.EAST);
        this.panel.add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "HomePage");
    }

    public static void showPage(String pageName) {
        if (cardLayout != null && cardPanel != null) {
            cardLayout.show(cardPanel, pageName);
            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }

    public static void loadVehiclesPageAsync(JFrame frame, JPanel contentPanel) {
        new SwingWorker<List<Vehicle>, Void>() {
            private List<Vehicle> vehicles;

            @Override
            protected List<Vehicle> doInBackground() {
                vehicles = VehicleController.getAllVehicles();
                Vehicle.setVehicles(vehicles);
                ImageLoader.loadImages(vehicles);

                return vehicles;
            }

            @Override
            protected void done() {
                try {
                    get();

                    System.out.println("Loading vehicles: " + vehicles.size() + " vehicles loaded.");

                    // SwingUtilities.invokeLater(() -> {
                    ((VehiclesPage) vehiclesPanel).refreshCards(Vehicle.getVehicles());
                    // vehiclesPanel.revalidate();
                    // vehiclesPanel.repaint();
                    // cardLayout.show(cardPanel, "VehiclesPage");

                    // });
                } catch (Exception e) {
                    System.out.println("Error loading vehicles: " + e.getMessage());
                    // loadVehiclesPageAsync(frame, cardPanel);
                }
            }
        }.execute();
    }

    // not using
    public static void updateTheme() {
        // Update the background of all buttons
        if (buttons != null) {
            for (JButton button : buttons) {
                button.setBackground(Theme.getBackground());
                button.setForeground(Theme.getForeground());
                button.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
            }
        }

        // Update the background of the top bar buttons
        if (topBarButtons != null) {
            for (JButton button : topBarButtons) {
                button.setBackground(Theme.getBackground());
                button.setForeground(Theme.getForeground());
                if (button.getForeground().equals(Theme.getSpecial())) {
                    button.setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(20f));
                } else
                    button.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
            }
        }

        // Create a data structure array to hold all panels
        JPanel[] panels = { homePanel, vehiclesPanel, aboutUsPanel, contactUsPanel, cardPanel };

        // Iterate through the array and update backgrounds, revalidate, and repaint
        for (JPanel panel : panels) {
            if (panel != null) {
                updatePanelComponents(panel);
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    // Refreshes the panels and updates the theme
    public static void refreshPanels(JFrame frame, JPanel panel, User user) {
        // updateTheme();
        createNewThemedPanels(frame, (JPanel) panel.getParent(), user);
        GUIComponents.overflowMenu = null;

        frame.revalidate();
        frame.repaint();
    }

    private static void createNewThemedPanels(JFrame frame, JPanel panel, User user) {
        overflowMenu = null;

        panel.removeAll();
        panel.add(new GUIComponents(frame, panel, user), BorderLayout.NORTH);
        panel.revalidate();
        panel.repaint();
    }

    // not using
    private static void updatePanelComponents(java.awt.Container container) {

        // check which panel is being updated and update the theme accordingly
        // create a method for each panel to update the theme

        for (java.awt.Component component : container.getComponents()) {
            component.setBackground(Theme.getBackground());
            component.setForeground(Theme.getForeground());

            if (component instanceof JLabel) {
                component.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(22f));
            } else if (component instanceof JButton) {
                component.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(24f));
            } else if (component instanceof java.awt.Container) {
                updatePanelComponents((java.awt.Container) component);
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static OverflowMenu getOverflowMenu() {
        return overflowMenu;
    }

    public static void setOverflowMenu(OverflowMenu overflowMenu) {
        GUIComponents.overflowMenu = overflowMenu;
    }

    public JButton[] getTopBarButtons() {
        return topBarButtons;
    }

    public void setTopBarButtons(JButton[] topBarButtons) {
        GUIComponents.topBarButtons = topBarButtons;
    }

    public String[] getTopBarButtonsLabels() {
        return topBarButtonsLabels;
    }

    public void setTopBarButtonsLabels(String[] topBarButtonsLabels) {
        this.topBarButtonsLabels = topBarButtonsLabels;
    }

    private JButton logo() {
        JButton logo = new JButton("AQUILUXE");
        logo.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(30f));
        logo.setForeground(Theme.getSpecial());
        logo.setBorderPainted(false); // no border
        logo.setFocusPainted(false); // no highlight
        logo.setContentAreaFilled(false); // no fill
        logo.addActionListener(e -> {
            for (JButton button : topBarButtons) {
                button.setForeground(Theme.getForeground());
                button.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
            }
            topBarButtons[0].setForeground(Theme.getSpecial());
            topBarButtons[0].setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(20f));
            showPage("HomePage");
        });

        return logo;
    }

    private JPanel createTopBar() {
        JPanel topBarContainer = new JPanel();
        topBarContainer.setLayout(new GridBagLayout());
        topBarContainer.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 30, 0, 50);

        topBarContainer.add(logo(), gbc);

        gbc.insets = new Insets(0, 5, 0, 0);
        buttons = createButtons();
        for (JButton button : buttons) {
            topBarContainer.add(button, gbc);
        }

        return topBarContainer;
    }

    private JButton[] createButtons() {

        for (int i = 0; i < topBarButtonsLabels.length; i++) {
            topBarButtons[i] = new JButton(topBarButtonsLabels[i]);
            if (i == 0) {
                topBarButtons[0].setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(20f));
                topBarButtons[0].setForeground(Theme.getSpecial());
            } else {
                topBarButtons[i].setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
                topBarButtons[i].setForeground(Theme.getForeground());
            }
            topBarButtons[i].setBackground(Theme.getBackground());
            topBarButtons[i].setPreferredSize(new Dimension(150, 50));
            topBarButtons[i].setBorderPainted(false); // no border
            topBarButtons[i].setFocusPainted(false); // no highlight
        }

        topBarButtons[0].addActionListener(e -> {
            pageIndicator(0);
            showPage("HomePage");
        });

        topBarButtons[1].addActionListener(e -> {
            // if (!vehiclesPageLoaded) {
            // JOptionPane.showMessageDialog(frame, "Vehicles are still loading, please
            // wait...");
            // return;
            // }
            pageIndicator(1);
            showPage("VehiclesPage");
        });

        topBarButtons[2].addActionListener(e -> {
            pageIndicator(2);
            showPage("AboutUsPage");
        });

        topBarButtons[3].addActionListener(e -> {
            pageIndicator(3);
            showPage("ContactUsPage");
        });

        topBarButtons[4].addActionListener(e -> {
            pageIndicator(4);
            cardLayout.show(cardPanel, "RentalHistoryPage");
        });

        return topBarButtons;
    }

    private void pageIndicator(int index) {
        for (JButton button : topBarButtons) {
            button.setForeground(Theme.getForeground());
            button.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
        }
        topBarButtons[index].setForeground(Theme.getSpecial());
        topBarButtons[index].setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(20f));
    }

    private JButton menuButton() {
        if (this.user == null)
            this.user = new User();

        JButton menu = new JButton();
        menu.setIcon(IconLoader.getMenuIcon());
        menu.setPreferredSize(new Dimension(100, 50));
        menu.setBorderPainted(false); // no border
        menu.setFocusPainted(false);
        menu.setBackground(Theme.getBackground());
        menu.addActionListener(e -> {
            File accountsFile = new File("files/settings/accounts.txt");

            if (overflowMenu == null) {
                User currentUser = UserController.loadCurrentUser(accountsFile);
                overflowMenu = new OverflowMenu(this.frame, this.panel, currentUser);
                OverflowMenu.setGuiComponents(this);
                this.frame.getLayeredPane().add(overflowMenu, JLayeredPane.POPUP_LAYER);
                overflowMenu.setBounds(this.frame.getWidth() - (overflowMenu.MENU_WIDTH + 35), 90,
                        overflowMenu.MENU_WIDTH, overflowMenu.MENU_HEIGHT);
            } else {
                this.frame.getLayeredPane().remove(overflowMenu);
                overflowMenu = null;
            }
            this.frame.revalidate();
            this.frame.repaint();
        });

        return menu;
    }

    public static void refreshHomePage(JFrame frame, JPanel panel, User user, GUIComponents guiComponents) {
        cardPanel.remove(homePanel);
        System.out.println(cardPanel.getComponents().length);
        homePanel = new HomePage(frame, panel, user, guiComponents);
        homePanel.revalidate();
        homePanel.repaint();
        cardPanel.add(homePanel, "HomePage");
        // cardLayout.show(cardPanel, "HomePage");
        System.out.println(cardPanel.getComponents().length);
        cardPanel.revalidate();
        cardPanel.repaint();

    }
}