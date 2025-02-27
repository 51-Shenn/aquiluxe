package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginPage extends JPanel {

    private final float TITLE_TEXT_SIZE = 22.5f;
    private final float NORMAL_TEXT_SIZE = 30f;
    private final float BUTTON_TEXT_SIZE = 30f;
    private final int HEIGHT = 60;
    private final Border BORDER = new LineBorder(Color.BLACK, 2);
    private final Border PADDING = new EmptyBorder(10, 15, 10, 15);
    private final Font TITLE_FONT = CustomFonts.ROBOTO_BOLD;
    private final Font INPUT_FONT = CustomFonts.OPEN_SANS_BOLD;

    public LoginPage() {
        setLayout(new GridLayout(1, 1));
        setBackground(Color.BLACK);
        add((createLoginPage()));
    }

    private JPanel createLoginPage() {
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
        rightPanel.add(createSignUpPanel());

        container.add(leftPanel);
        container.add(rightPanel, BorderLayout.NORTH);
        return container;
    }

    private JPanel createTitle() {
        // title container to indicate sign in or sign up
        JPanel titleContainer = new JPanel();
        titleContainer.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(60f));
        titleLabel.setForeground(Color.BLACK);
        titleContainer.add(titleLabel);

        return titleContainer;
    }

    private JPanel createInputContainer() {
        // create container that contains relevant info for sign in or sign up
        JPanel InputContainer = new JPanel(new BorderLayout());

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 50);

        // both full name and gender will be displayed in the same row
        JPanel fullNameAndGenderPanel = new JPanel(new GridBagLayout());
        fullNameAndGenderPanel.setBackground(Color.WHITE);
        fullNameAndGenderPanel.add(createFullNameContainer(), gbc);
        fullNameAndGenderPanel.add(createGenderContainer());

        gbc.insets = new Insets(0, 30, 30, 30);
        gbc.gridwidth = container.getWidth();
        container.add(fullNameAndGenderPanel, gbc);

        gbc.anchor = GridBagConstraints.WEST;

        container.add(createEmailContainer(), gbc);
        container.add(createPhoneContainer(), gbc);
//        container.add(createLicenseContainer(), gbc);
        container.add(createProceedContainer(), gbc);

        InputContainer.add(container);

        return InputContainer;
    }

    private JLabel createWallpaperLabel() {
        JLabel wallpaperLabel = new JLabel();
        wallpaperLabel.setOpaque(false);
        ImageIcon backgroundImage = new ImageIcon("images/icons/carBackground.png");
        Image image = backgroundImage.getImage().getScaledInstance(960, 1080, Image.SCALE_SMOOTH);

        wallpaperLabel.setLayout(new GridBagLayout());
        wallpaperLabel.setIcon(new ImageIcon(image));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(70, 150, 0, 0);
        gbc.weighty = 1;

        wallpaperLabel.add(createTaglineContainer(), gbc);
        return wallpaperLabel;
    }

    private JPanel createSignUpPanel() {
        JPanel signUpPanel = new JPanel(new GridBagLayout());
        signUpPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = signUpPanel.getWidth();
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.anchor = GridBagConstraints.NORTH;

        signUpPanel.add(createTitle(), gbc);
        gbc.insets = new Insets(100, 0, 250, 0);
        gbc.gridy = 1;
        gbc.weighty = 1;
        signUpPanel.add(createInputContainer(), gbc);
        return signUpPanel;
    }

    private JPanel createFullNameContainer() {
        // full name
        JPanel fullNamePanel = new JPanel(new BorderLayout());
        fullNamePanel.setBackground(Color.WHITE);

        JLabel fullNameLabel = new JLabel("Full Name: ");
        fullNameLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        fullNameLabel.setForeground(Color.BLACK);

        JTextField fullNameInput = new JTextField();
        fullNameInput.setPreferredSize(new Dimension(440, HEIGHT));
        fullNameInput.setMinimumSize(new Dimension(440, HEIGHT));
        fullNameInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        fullNameInput.setForeground(Color.BLACK);
        fullNameInput.setBorder(new CompoundBorder(BORDER, PADDING));

        fullNamePanel.add(fullNameLabel, BorderLayout.NORTH);
        fullNamePanel.add(fullNameInput, BorderLayout.CENTER);

        return fullNamePanel;
    }

    private JPanel createGenderContainer() {
        // gender
        JPanel genderPanel = new JPanel(new BorderLayout());
        genderPanel.setBackground(Color.WHITE);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        genderLabel.setForeground(Color.BLACK);

        JPanel genderButtonsPanel = new JPanel(new BorderLayout());
        genderButtonsPanel.setBorder(new LineBorder(Color.BLACK, 3));

        // male button
        JButton maleButton = new JButton("M");
        maleButton.setFont(INPUT_FONT.deriveFont(BUTTON_TEXT_SIZE));
        maleButton.setPreferredSize(new Dimension(100, HEIGHT));
        maleButton.setMinimumSize(new Dimension(100, HEIGHT));
        maleButton.setFocusPainted(false);
        maleButton.setBackground(Color.WHITE);
        maleButton.setForeground(Color.BLACK);

        // female button
        JButton femaleButton = new JButton("F");
        femaleButton.setFont(INPUT_FONT.deriveFont(BUTTON_TEXT_SIZE));
        femaleButton.setPreferredSize(new Dimension(100, HEIGHT));
        femaleButton.setMinimumSize(new Dimension(100, HEIGHT));
        femaleButton.setFocusPainted(false);
        femaleButton.setBackground(Color.WHITE);
        femaleButton.setForeground(Color.BLACK);

        // add both buttons to panel
        genderButtonsPanel.add(maleButton, BorderLayout.WEST);
        genderButtonsPanel.add(femaleButton, BorderLayout.EAST);

        // add both components inside gender panel
        genderPanel.add(genderLabel, BorderLayout.NORTH);
        genderPanel.add(genderButtonsPanel, BorderLayout.CENTER);

        return genderPanel;
    }

    private JPanel createEmailContainer() {
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

    private JPanel createPhoneContainer() {
        // phone number
        JPanel phonePanel = new JPanel(new GridBagLayout());
        phonePanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = phonePanel.getWidth();
        gbc.insets = new Insets(10, 0, 0, 0);

        JLabel phoneLabel = new JLabel("Phone Number: ");
        phoneLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        phoneLabel.setForeground(Color.BLACK);

        JPanel phoneInputPanel = new JPanel(new GridBagLayout());
        JButton countryCode = new JButton();
        countryCode.setText("+60");
        countryCode.setFont(INPUT_FONT.deriveFont(BUTTON_TEXT_SIZE));
        countryCode.setPreferredSize(new Dimension(100, HEIGHT));
        countryCode.setMinimumSize(new Dimension(100, HEIGHT));
        countryCode.setFocusPainted(false);
        countryCode.setBackground(Color.BLACK);
        countryCode.setForeground(Color.WHITE);
        countryCode.setBorder(new LineBorder(Color.BLACK, 2));

        JTextField phoneInput = new JTextField();
        phoneInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        phoneInput.setPreferredSize(new Dimension(600, HEIGHT));
        phoneInput.setMinimumSize(new Dimension(600, HEIGHT));
        phoneInput.setForeground(Color.BLACK);
        phoneInput.setBorder(new CompoundBorder(BORDER, PADDING));

        phoneInputPanel.add(countryCode);
        phoneInputPanel.add(phoneInput);

        phonePanel.add(phoneLabel, gbc);
        phonePanel.add(phoneInputPanel);

        return phonePanel;
    }

    private JPanel createLicenseContainer() {
        // driving license
        JPanel licenseContainer = new JPanel(new GridBagLayout());
        licenseContainer.setBackground(Color.WHITE);

        JLabel licenseLabel = new JLabel("Driving License: ");
        licenseLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        licenseLabel.setForeground(Color.BLACK);

        JFileChooser licenseInput = new JFileChooser("c:");

        licenseContainer.add(licenseLabel);
        licenseContainer.add(licenseInput);
        return licenseContainer;
    }

    private JPanel createProceedContainer() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 0, 0, 320);

        JButton linkButton = new JButton("Already have an account?");
        linkButton.setBackground(Color.WHITE);
        linkButton.setForeground(Color.BLUE);
        linkButton.setFont(TITLE_FONT.deriveFont(17f));
        linkButton.setBorderPainted(false);
        linkButton.setFocusPainted(false);

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(TITLE_FONT.deriveFont(BUTTON_TEXT_SIZE));
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setPreferredSize(new Dimension(150, 70));
        nextButton.setMinimumSize(new Dimension(150, HEIGHT));

        container.add(linkButton, gbc);
        gbc.insets = new Insets(50, 0, 0, 0);
        container.add(nextButton, gbc);
        return container;
    }

    private JPanel createTaglineContainer() {
        JPanel taglineContainer = new JPanel(new GridBagLayout());
        taglineContainer.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHEAST;

        String logoText = "- AQUILUXE";
        JLabel logo = new JLabel(logoText);
        logo.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(30f));

        String[] texts = {"Rent a Car,", "Drive with Ease!"};
        for (String text : texts) {
            JLabel lines = new JLabel(text);
            if (text.equals(texts[0]))
                lines.setForeground(Color.BLACK);
            else
                lines.setForeground(Color.BLUE);
            lines.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(70f));

            taglineContainer.add(lines, gbc);
        }
        taglineContainer.add(logo, gbc);

        return taglineContainer;
    }
}
