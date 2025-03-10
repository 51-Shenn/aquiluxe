package gui;

import javax.swing.*;
import java.awt.*;

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
        add(createOverflowMenu(), 100);
    }

    private JPanel createOverflowMenu() {
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBounds(MENU_POSITION_X, MENU_POSITION_Y, MENU_WIDTH, MENU_HEIGHT);
        container.setBackground(Color.BLUE);
        container.add(createCard());
        container.add(new JButton("Another Button"));

        return container;
    }

    private JPanel createCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 0, MENU_WIDTH, 50);
        panel.add(createButton());
        panel.add(new JButton("jo"));
        return panel;
    }

    private JButton createButton() {
        JButton button = new JButton("TEST");
        button.setBackground(Color.RED);
        button.setBounds(0, 0, MENU_WIDTH- 100, 50);
        return button;
    }
}
