package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public abstract class LoginPage extends JPanel {

    protected final float TITLE_TEXT_SIZE = 20f;
    protected final float NORMAL_TEXT_SIZE = 28f;
    protected final float BUTTON_TEXT_SIZE = 30f;
    protected final int HEIGHT = 60;
    protected final Border BORDER = new LineBorder(Color.BLACK, 2);
    protected final Border PADDING = new EmptyBorder(10, 15, 10, 15);
    protected final Font TITLE_FONT = CustomFonts.ROBOTO_BOLD;
    protected final Font INPUT_FONT = CustomFonts.OPEN_SANS_BOLD;

    public LoginPage() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        add((createLoginPage()));
    }

    protected JPanel createLoginPage() {
        // container that separate left and right panel
        JPanel container = new JPanel(new GridLayout(1, 2));

        // left panel for description or background image
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new LineBorder(Color.BLACK, 1));
        leftPanel.add(createWallpaperLabel());

        // right panel for sign in and sign up
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new LineBorder(Color.BLACK, 1));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(createContentPanel());

        container.add(leftPanel);
        container.add(rightPanel, BorderLayout.NORTH);
        return container;
    }

    abstract JPanel createTitle();
    abstract JPanel createContentPanel();
    abstract JLabel createWallpaperLabel();
    abstract JPanel createInputContainer();
}
