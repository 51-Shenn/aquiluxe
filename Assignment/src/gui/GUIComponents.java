package gui;

import controllers.UserController;
import datamodels.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GUIComponents extends JPanel {

    private JFrame frame;
    private JPanel panel;
    private User user;
    public static OverflowMenu overflowMenu;
    private JButton[] topBarButtons = new JButton[4];
    private String[] topBarButtonsLabels = { "Home", "Vehicles", "About", "Contact" };

    public static JPanel homePanel;
    public static JPanel vehiclesPanel;
    public static JPanel aboutUsPanel;
    public static JPanel contactUsPanel;
    public static CardLayout cardLayout;
    public static JPanel cardPanel;

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

        homePanel = new HomePage(this.frame, this.panel, this.user, this);
        vehiclesPanel = new VehiclesPage(this.frame, this.panel, this.user);
        aboutUsPanel = new AboutUsPage(this.frame, this.panel);
        contactUsPanel = new ContactUsPage(this.frame, this.panel);

        cardPanel.add(homePanel, "HomePage");
        cardPanel.add(vehiclesPanel, "VehiclesPage");
        cardPanel.add(aboutUsPanel, "AboutUsPage");
        cardPanel.add(contactUsPanel, "ContactUsPage");

        add(createTopBar(), BorderLayout.WEST);
        add(menuButton(), BorderLayout.EAST);
        this.panel.add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "HomePage");
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
        this.topBarButtons = topBarButtons;
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
            cardLayout.show(cardPanel, "HomePage");
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
        JButton[] buttons = createButtons();
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
            cardLayout.show(cardPanel, "HomePage");
        });

        topBarButtons[1].addActionListener(e -> {
            pageIndicator(1);
            cardLayout.show(cardPanel, "VehiclesPage");
        });

        topBarButtons[2].addActionListener(e -> {
            pageIndicator(2);
            cardLayout.show(cardPanel, "AboutUsPage");
        });

        topBarButtons[3].addActionListener(e -> {
            pageIndicator(3);
            cardLayout.show(cardPanel, "ContactUsPage");
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

        // refresh vehicle page 
        // cardPanel.remove(vehiclesPanel);
        // vehiclesPanel = new VehiclesPage(frame, panel, user);
        // vehiclesPanel.revalidate();
        // vehiclesPanel.repaint();
        // cardPanel.add(vehiclesPanel, "VehiclesPage");
        // cardLayout.show(cardPanel,"VehiclesPage");
        // cardPanel.revalidate();
        // cardPanel.repaint();
    }
}