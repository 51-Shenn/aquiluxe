package gui;

import java.awt.*;
import javax.swing.*;

public class GUIComponents extends JPanel {

    JFrame frame;
    private OverflowMenu overflowMenu;

    public GUIComponents(JFrame frame) {
        this.frame = frame;
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

        topBarButtons[1].addActionListener(e -> {
            this.frame.add(new VehiclesPage(this.frame), BorderLayout.CENTER);
            this.frame.revalidate();
            this.frame.repaint();
        });

        return topBarButtons;
    }

    private JButton menuButton() {
        ImageIcon kebabMenuIcon = new ImageIcon("images/icons/kebab.png");
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
                menu.setBackground(Color.LIGHT_GRAY);
                frame.add(overflowMenu);
            }
            else {
                menu.setBackground(Color.WHITE);
                frame.remove(overflowMenu);
                overflowMenu = null;
            }

            frame.revalidate();
            frame.repaint();
        });

        return menu;
    }
}