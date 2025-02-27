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

    public LoginPage() {
        setLayout(new GridLayout(1, 1));
        setBackground(Color.BLACK);
        add((createLoginPage()));
    }

    private JPanel createLoginPage() {
        // container that separate left and right panel
        JPanel container = new JPanel(new GridLayout(1, 2));

        // left panel for description or background image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setOpaque(false);
        ImageIcon backgroundImage = new ImageIcon("images/icons/carBackground.png");
        Image image = backgroundImage.getImage().getScaledInstance(960, 1080, Image.SCALE_SMOOTH);

        backgroundLabel.setLayout(new GridBagLayout());
        backgroundLabel.setIcon(new ImageIcon(image));
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(70, 100, 0, 0);
        gbc.weighty = 1;

        leftPanel.add(createTaglineContainer());
        backgroundLabel.add(leftPanel, gbc);

        // right panel for sign in and sign up
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridwidth = rightPanel.getWidth();
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.anchor = GridBagConstraints.NORTH;

        rightPanel.add(createTitle(), gbc);

        gbc.weighty = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        rightPanel.add(createInputContainer(), gbc);

        container.add(backgroundLabel);
        container.add(rightPanel);
        return container;
    }

    private JPanel createTitle() {
        // title container to indicate sign in or sign up
        JPanel titleContainer = new JPanel();
        titleContainer.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(60f));
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

    private JPanel createFullNameContainer() {
        // full name
        JPanel fullNamePanel = new JPanel(new BorderLayout());
        fullNamePanel.setBackground(Color.WHITE);

        JLabel fullNameLabel = new JLabel("Full Name: ");
        fullNameLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(TITLE_TEXT_SIZE));
        fullNameLabel.setForeground(Color.BLACK);

        JTextField fullNameInput = new JTextField();
        fullNameInput.setPreferredSize(new Dimension(440, HEIGHT));
        fullNameInput.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(NORMAL_TEXT_SIZE));
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
        genderLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(TITLE_TEXT_SIZE));
        genderLabel.setForeground(Color.BLACK);

        JPanel genderButtonsPanel = new JPanel(new BorderLayout());
        genderButtonsPanel.setBorder(new LineBorder(Color.BLACK, 3));

        // male button
        JButton maleButton = new JButton("M");
        maleButton.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(BUTTON_TEXT_SIZE));
        maleButton.setPreferredSize(new Dimension(100, HEIGHT));
        maleButton.setFocusPainted(false);
        maleButton.setBackground(Color.WHITE);
        maleButton.setForeground(Color.BLACK);

        // female button
        JButton femaleButton = new JButton("F");
        femaleButton.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(BUTTON_TEXT_SIZE));
        femaleButton.setPreferredSize(new Dimension(100, HEIGHT));
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
        emailLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(TITLE_TEXT_SIZE));
        emailLabel.setForeground(Color.BLACK);

        JTextField emailInput = new JTextField();
        emailInput.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(NORMAL_TEXT_SIZE));
        emailInput.setPreferredSize(new Dimension(700, HEIGHT));
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
        phoneLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(TITLE_TEXT_SIZE));
        phoneLabel.setForeground(Color.BLACK);

        JPanel phoneInputPanel = new JPanel(new GridBagLayout());
        JButton countryCode = new JButton();
        countryCode.setText("+60");
        countryCode.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(BUTTON_TEXT_SIZE));
        countryCode.setPreferredSize(new Dimension(100, HEIGHT));
        countryCode.setFocusPainted(false);
        countryCode.setBackground(Color.BLACK);
        countryCode.setForeground(Color.WHITE);
        countryCode.setBorder(new LineBorder(Color.BLACK, 2));

        JTextField phoneInput = new JTextField();
        phoneInput.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(NORMAL_TEXT_SIZE));
        phoneInput.setPreferredSize(new Dimension(600, HEIGHT));
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
        licenseLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(TITLE_TEXT_SIZE));
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
        linkButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(17f));
        linkButton.setBorderPainted(false);
        linkButton.setFocusPainted(false);

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(BUTTON_TEXT_SIZE));
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setPreferredSize(new Dimension(150, 70));

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

        String[] texts = {"Rent a Car,", "Drive with Ease!"};
        for (String text : texts) {
            JLabel lines = new JLabel(text);
            lines.setForeground(Color.BLACK);
            lines.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(70f));
            taglineContainer.add(lines, gbc);
        }

        return taglineContainer;
    }
}
