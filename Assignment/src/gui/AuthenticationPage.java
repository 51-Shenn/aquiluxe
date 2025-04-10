package gui;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;

public abstract class AuthenticationPage extends JPanel {

    protected final float TITLE_TEXT_SIZE = 20f;
    protected final float NORMAL_TEXT_SIZE = 28f;
    protected final float BUTTON_TEXT_SIZE = 30f;
    protected final int HEIGHT = 60;
    protected final Border BORDER = new LineBorder(Color.BLACK, 2);
    protected final Border PADDING = new EmptyBorder(10, 15, 10, 15);
    protected final Font TITLE_FONT = CustomFonts.ROBOTO_SEMI_BOLD;
    protected final Font INPUT_FONT = CustomFonts.OPEN_SANS_REGULAR;

    protected JTextField emailInput;
    protected JTextField phoneInput;
    protected JLabel emailValidationLabel;
    protected JLabel phoneValidationLabel;

    public AuthenticationPage() {
        setLayout(new GridBagLayout());
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
    abstract JLabel createWallpaperLabel();
    abstract JPanel createInputContainer();

    protected JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = contentPanel.getWidth();
        gbc.insets = new Insets(100, 0, 0, 0);
        gbc.anchor = GridBagConstraints.NORTH;

        contentPanel.add(createTitle(), gbc);
        gbc.insets = new Insets(50, 0, 250, 0);
        gbc.gridy = 1;
        gbc.weighty = 1;
        contentPanel.add(createInputContainer(), gbc);
        return contentPanel;
    }

    protected JPanel createEmailContainer(String emailInputTitle) {
        // email address
        JPanel emailPanel = new JPanel(new GridBagLayout());
        emailPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = emailPanel.getWidth();
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel emailLabel = new JLabel(emailInputTitle);
        emailLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        emailLabel.setForeground(Color.BLACK);

        emailInput = new JTextField();
        emailInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        emailInput.setPreferredSize(new Dimension(700, HEIGHT));
        emailInput.setMinimumSize(new Dimension(700, HEIGHT));
        emailInput.setForeground(Color.BLACK);
        emailInput.setBorder(new CompoundBorder(BORDER, PADDING));
        // emailInput.addKeyListener(e -> {

        // });

        emailValidationLabel = new JLabel("");
        emailValidationLabel.setForeground(Color.RED);
        emailValidationLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));

        emailPanel.add(emailLabel, gbc);
        emailPanel.add(emailInput, gbc);
        emailPanel.add(emailValidationLabel, gbc);

        return emailPanel;
    }
    protected JPanel createPhoneContainer() {
        // phone number
        JPanel phonePanel = new JPanel(new GridBagLayout());
        phonePanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = phonePanel.getWidth();
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel phoneLabel = new JLabel("Phone Number: ");
        phoneLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        phoneLabel.setForeground(Color.BLACK);

        JPanel phoneInputPanel = new JPanel(new GridBagLayout());
        JButton countryCode = new JButton();
        countryCode.setText("+60");
        countryCode.setFont(TITLE_FONT.deriveFont(BUTTON_TEXT_SIZE));
        countryCode.setPreferredSize(new Dimension(100, HEIGHT));
        countryCode.setMinimumSize(new Dimension(100, HEIGHT));
        countryCode.setFocusPainted(false);
        countryCode.setBackground(Color.BLACK);
        countryCode.setForeground(Color.WHITE);
        countryCode.setBorder(BORDER);

        phoneInput = new JTextField();
        phoneInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        phoneInput.setPreferredSize(new Dimension(600, HEIGHT));
        phoneInput.setMinimumSize(new Dimension(600, HEIGHT));
        phoneInput.setForeground(Color.BLACK);
        phoneInput.setBorder(new CompoundBorder(BORDER, PADDING));

        phoneInputPanel.add(countryCode);
        phoneInputPanel.add(phoneInput);

        phoneValidationLabel = new JLabel("");
        phoneValidationLabel.setForeground(Color.RED);
        phoneValidationLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));

        phonePanel.add(phoneLabel, gbc);
        phonePanel.add(phoneInputPanel, gbc);
        phonePanel.add(phoneValidationLabel, gbc);

        return phonePanel;
    }
    protected JButton createEyeButton(JPasswordField passwordField) {
        JButton eyeButton = new JButton();
        File eyeOn = new File("images/icons/eye-on.png");
        File eyeOff = new File("images/icons/eye-off.png");

        if (!eyeOn.exists() || !eyeOff.exists())
            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + eyeOn + "\n" + eyeOff);
        else {
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
