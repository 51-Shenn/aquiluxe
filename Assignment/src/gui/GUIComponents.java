package gui;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class GUIComponents extends JPanel {

    private final JFrame frame;
    private final JPanel panel;
    private OverflowMenu overflowMenu;

    public GUIComponents(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(frame.getWidth(), 80));
        setLayout(new BorderLayout());

        add(createTopBar(), BorderLayout.WEST);
        add(menuButton(), BorderLayout.EAST);
    }

    private JButton logo() {
        JButton logo = new JButton("AQUILUXE");
        logo.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(30f));
        logo.setForeground(Color.BLUE);
        logo.setBorderPainted(false); // no border
        logo.setFocusPainted(false); // no highlight
        logo.setContentAreaFilled(false); // no fill

        return logo;
    }

    private JPanel createTopBar() {
        JPanel topBarContainer = new JPanel();
        topBarContainer.setLayout(new GridBagLayout());
        topBarContainer.setBackground(Color.WHITE);

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
        JButton[] topBarButtons = new JButton[4];
        String[] topBarButtonsLabels = { "Home", "Vehicles", "About", "Contact" };

        for (int i = 0; i < topBarButtonsLabels.length; i++) {
            topBarButtons[i] = new JButton(topBarButtonsLabels[i]);
            topBarButtons[i].setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
            topBarButtons[i].setBackground(Color.WHITE);
            topBarButtons[i].setForeground(Color.BLACK);
            topBarButtons[i].setPreferredSize(new Dimension(150, 50));
            topBarButtons[i].setBorderPainted(false); // no border
            topBarButtons[i].setFocusPainted(false); // no highlight
        }

        topBarButtons[0].addActionListener(e -> {
            this.panel.removeAll();
            this.panel.revalidate();
            this.panel.repaint();
        });

        topBarButtons[1].addActionListener(e -> {
            this.panel.removeAll();
            this.panel.add(new VehiclesPage(this.frame), BorderLayout.CENTER);
            this.panel.revalidate();
            this.panel.repaint();
        });

        topBarButtons[2].addActionListener(e -> {
            this.panel.removeAll();
            JPanel panel = new JPanel();

            //test
            panel.setBackground(Color.BLACK);
            this.panel.add(panel);

            this.panel.revalidate();
            this.panel.repaint();
        });

        topBarButtons[3].addActionListener(e -> {
            this.panel.removeAll();
            this.panel.revalidate();
            this.panel.repaint();
        });

        return topBarButtons;
    }

    private JButton menuButton() {
        File kebabMenu = new File("images/icons/kebab-menu.png");

        if (!kebabMenu.exists())
            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + kebabMenu);
        else {
            ImageIcon kebabMenuIcon = new ImageIcon(kebabMenu.toString());
            JButton menu = new JButton();
            menu.setIcon(kebabMenuIcon);
            menu.setPreferredSize(new Dimension(100, 50));
            menu.setBorderPainted(false); // no border
            menu.setFocusPainted(false);
            menu.setBackground(Color.WHITE);
            menu.addActionListener(e -> {
//            frame.getContentPane().removeAll();
//            frame.add(new SignInPage(this.frame));
//            frame.validate();

                if (overflowMenu == null) {
                    overflowMenu = new OverflowMenu(this.frame);
                    this.frame.getLayeredPane().add(overflowMenu, JLayeredPane.POPUP_LAYER);
                    overflowMenu.setBounds(this.frame.getWidth() - (overflowMenu.MENU_WIDTH + 20), 85, overflowMenu.MENU_WIDTH, overflowMenu.MENU_HEIGHT);
                } else {
                    this.frame.getLayeredPane().remove(overflowMenu);
                    overflowMenu = null;
                }
                this.frame.revalidate();
                this.frame.repaint();
            });

            return menu;
        }
        return new JButton("ERROR");
    }
}