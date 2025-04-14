package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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

    public float getTITLE_TEXT_SIZE() {
        return TITLE_TEXT_SIZE;
    }

    public float getNORMAL_TEXT_SIZE() {
        return NORMAL_TEXT_SIZE;
    }

    public float getBUTTON_TEXT_SIZE() {
        return BUTTON_TEXT_SIZE;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public Border getBORDER() {
        return BORDER;
    }

    public Border getPADDING() {
        return PADDING;
    }

    public Font getTITLE_FONT() {
        return TITLE_FONT;
    }

    public Font getINPUT_FONT() {
        return INPUT_FONT;
    }

    public JTextField getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(JTextField emailInput) {
        this.emailInput = emailInput;
    }

    public JTextField getPhoneInput() {
        return phoneInput;
    }

    public void setPhoneInput(JTextField phoneInput) {
        this.phoneInput = phoneInput;
    }

    public JLabel getEmailValidationLabel() {
        return emailValidationLabel;
    }

    public void setEmailValidationLabel(JLabel emailValidationLabel) {
        this.emailValidationLabel = emailValidationLabel;
    }

    public JLabel getPhoneValidationLabel() {
        return phoneValidationLabel;
    }

    public void setPhoneValidationLabel(JLabel phoneValidationLabel) {
        this.phoneValidationLabel = phoneValidationLabel;
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
        
        ImageIcon eyeOnIcon = IconLoader.getEyeOnIcon();
        ImageIcon eyeOffIcon = IconLoader.getEyeOffIcon();

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

        return eyeButton;
    }
}
