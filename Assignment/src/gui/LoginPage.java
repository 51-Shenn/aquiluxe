package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;

public abstract class LoginPage extends JPanel {

    protected final float TITLE_TEXT_SIZE = 20f;
    protected final float NORMAL_TEXT_SIZE = 28f;
    protected final float BUTTON_TEXT_SIZE = 30f;
    protected final int HEIGHT = 60;
    protected final Border BORDER = new LineBorder(Color.BLACK, 2);
    protected final Border PADDING = new EmptyBorder(10, 15, 10, 15);
    protected final Font TITLE_FONT = CustomFonts.ROBOTO_SEMI_BOLD;
    protected final Font INPUT_FONT = CustomFonts.OPEN_SANS_REGULAR;

    public LoginPage() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        add((createLoginPage()));
    }

    protected JPanel createLoginPage() {
        // container that separate left and right panel
        JPanel container = new JPanel(new GridLayout(1, 1));
        container.setBorder(BORDER);

        // left panel for description or background image
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BORDER);
        leftPanel.add(createWallpaperLabel());

        // right panel for sign in and sign up
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BORDER);
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

    protected JPanel createEmailContainer() {
        // email address
        JPanel emailPanel = new JPanel(new GridBagLayout());
        emailPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = emailPanel.getWidth();
        gbc.insets = new Insets(10, 0, 0, 0);

        JLabel emailLabel = new JLabel("Email Address: ");
        emailLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        emailLabel.setForeground(Color.BLACK);

        JTextField emailInput = new JTextField();
        emailInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        emailInput.setPreferredSize(new Dimension(700, HEIGHT));
        emailInput.setMinimumSize(new Dimension(700, HEIGHT));
        emailInput.setForeground(Color.BLACK);
        emailInput.setBorder(new CompoundBorder(BORDER, PADDING));

        emailPanel.add(emailLabel, gbc);
        emailPanel.add(emailInput);

        return emailPanel;
    }
    protected JButton createEyeButton(JPasswordField passwordField) {
        JButton eyeButton = new JButton();
        File eyeOn = new File("images/icons/eyeOn.png");
        File eyeOff = new File("images/icons/eyeOff.png");

        if (!eyeOn.exists() || !eyeOff.exists()) {
            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + eyeOn + "\n" + eyeOff);
        } else {
            ImageIcon eyeOnIcon = new ImageIcon(eyeOn.toString());
            ImageIcon eyeOffIcon = new ImageIcon(eyeOff.toString());

            eyeButton.setIcon(eyeOffIcon);
            eyeButton.setBackground(Color.WHITE);
            eyeButton.setBorderPainted(false);
            eyeButton.setFocusPainted(false);
            eyeButton.setContentAreaFilled(false);
            eyeButton.setPreferredSize(new Dimension(100, HEIGHT));
            eyeButton.addActionListener(e -> {
                if(eyeButton.getIcon() == eyeOffIcon) {
                    eyeButton.setIcon(eyeOnIcon);
                    passwordField.setEchoChar((char) 0);
                }
                else {
                    eyeButton.setIcon(eyeOffIcon);
                    passwordField.setEchoChar('•');
                }
            });
        }

        return eyeButton;
    }
}
