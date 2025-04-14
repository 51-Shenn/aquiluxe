package gui;

import datamodels.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class GUIComponents extends JPanel {

    private JFrame frame;
    private JPanel panel;
    private User user;
    public static OverflowMenu overflowMenu;
    private JButton[] topBarButtons = new JButton[4];
    private String[] topBarButtonsLabels = { "Home", "Vehicles", "About", "Contact" };

    public GUIComponents() {
        this.frame = new JFrame();
        this.panel = new JPanel();
    }
    
    public GUIComponents(JFrame frame, JPanel panel, User user) {
        try {
            // Set the Windows Look and Feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.frame = frame;
        this.panel = panel;
        this.user = user;
        setBackground(Theme.getBackground());
        setPreferredSize(new Dimension(frame.getWidth(), 80));
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Theme.getTransparencyColor(), 1));

        add(createTopBar(), BorderLayout.WEST);
        add(menuButton(), BorderLayout.EAST);
        this.panel.add(new HomePage(this.frame, this.panel, this.user, this), BorderLayout.CENTER);
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
            for(JButton button : topBarButtons) {
                button.setForeground(Theme.getForeground());
                button.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
            }
            topBarButtons[0].setForeground(Theme.getSpecial());
            topBarButtons[0].setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(20f));
            this.panel.removeAll();
            this.panel.add(new HomePage(this.frame, this.panel, this.user, this), BorderLayout.CENTER);
            this.panel.revalidate();
            this.panel.repaint();
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
            if(i == 0) {
                topBarButtons[0].setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(20f));
                topBarButtons[0].setForeground(Theme.getSpecial());
            }
            else {
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
            this.panel.removeAll();
            this.panel.add(new HomePage(this.frame, this.panel, this.user, this), BorderLayout.CENTER);
            this.panel.revalidate();
            this.panel.repaint();
        });

        topBarButtons[1].addActionListener(e -> {
            pageIndicator(1);
            this.panel.removeAll();
            this.panel.add(new VehiclesPage(this.frame, this.panel), BorderLayout.CENTER);
            this.panel.revalidate();
            this.panel.repaint();
        });

        topBarButtons[2].addActionListener(e -> {
            pageIndicator(2);
            this.panel.removeAll();

            this.panel.revalidate();
            this.panel.repaint();
        });

        topBarButtons[3].addActionListener(e -> {
            pageIndicator(3);
            this.panel.removeAll();
            this.panel.revalidate();
            this.panel.repaint();
        });

        return topBarButtons;
    }

    private void pageIndicator(int index) {
        for(JButton button : topBarButtons) {
            button.setForeground(Theme.getForeground());
            button.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
        }
        topBarButtons[index].setForeground(Theme.getSpecial());
        topBarButtons[index].setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(20f));
    }

    private JButton menuButton() {
        if(this.user == null) this.user = new User();

        JButton menu = new JButton();
        menu.setIcon(IconLoader.getMenuIcon());
        menu.setPreferredSize(new Dimension(100, 50));
        menu.setBorderPainted(false); // no border
        menu.setFocusPainted(false);
        menu.setBackground(Theme.getBackground());
        menu.addActionListener(e -> {
            if (overflowMenu == null) {
                overflowMenu = new OverflowMenu(this.frame, this.panel, this.user);
                this.frame.getLayeredPane().add(overflowMenu, JLayeredPane.POPUP_LAYER);
                overflowMenu.setBounds(this.frame.getWidth() - (overflowMenu.MENU_WIDTH + 35), 90, overflowMenu.MENU_WIDTH, overflowMenu.MENU_HEIGHT);
            } else {
                this.frame.getLayeredPane().remove(overflowMenu);
                overflowMenu = null;
            }
            this.frame.revalidate();
            this.frame.repaint();
        });

        return menu;
    }
}