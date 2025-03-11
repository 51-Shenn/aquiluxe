package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OverflowMenu extends JLayeredPane {

    private final JFrame frame;
    private final int MENU_WIDTH;
    private final int MENU_HEIGHT;
    private final int MENU_POSITION_X;
    private final int MENU_POSITION_Y;

    public OverflowMenu(JFrame frame) {
        this.frame = frame;

        MENU_WIDTH = 350;
        MENU_HEIGHT = 500;
        MENU_POSITION_X = frame.getWidth() - (MENU_WIDTH + 20);
        MENU_POSITION_Y = 0;

        setBounds(MENU_POSITION_X, MENU_POSITION_Y, MENU_WIDTH, MENU_HEIGHT);
        add(createOverflowMenu(), JLayeredPane.POPUP_LAYER);
    }

    private JPanel createOverflowMenu() {
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBounds(MENU_POSITION_X, MENU_POSITION_Y, MENU_WIDTH, MENU_HEIGHT);
        container.add(createMainCard());

        return container;
    }

    private JPanel createMainCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        panel.setBackground(Color.LIGHT_GRAY);
//        panel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 0, 0, 0);

        File sunFilePath = new File("images/icons/sun.png");
        File moonFilePath = new File("images/icons/moon.png");
        File sunMoonFilePath = new File("images/icons/sun-moon.png");
        File switchFilePath = new File("images/icons/switch.png");
        File signOutFilePath = new File("images/icons/sign-out.png");
        File roundCloseFilePath = new File("images/icons/round-close.png");

        if(!sunMoonFilePath.exists() || !switchFilePath.exists() || !signOutFilePath.exists() || !roundCloseFilePath.exists())
            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + sunMoonFilePath + "\n" + switchFilePath + "\n" + signOutFilePath + "\n" + roundCloseFilePath);

        panel.add(createMenuCard("Theme", sunMoonFilePath.toString()), gbc);
        panel.add(createMenuCard("Switch Account", switchFilePath.toString()), gbc);
        panel.add(createMenuCard("Sign Out", signOutFilePath.toString()), gbc);
        panel.add(createMenuCard("Close / Exit", roundCloseFilePath.toString()), gbc);

        return panel;
    }

    private JPanel createMenuCard(String text, String iconFilePath) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon(iconFilePath);

        JButton button = new JButton(text);
        button.setIcon(icon);
        button.setIconTextGap(20);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(20f));
        button.setPreferredSize(new Dimension(MENU_WIDTH, 70));
        button.setFocusPainted(false);
        card.add(button);

        return card;
    }
}
