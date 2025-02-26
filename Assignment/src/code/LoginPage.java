package code;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginPage extends JPanel {

    LoginPage() {
        setLayout(new GridLayout(1, 1));
        setBackground(Color.BLACK);
        add((createLoginPage()));
    }

    private JPanel createLoginPage() {
        // container that separate left and right panel
        JPanel container = new JPanel(new GridLayout(1, 2));

        // left panel for description or background image
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.BLUE);

        // right panel for sign in and sign up
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridwidth = rightPanel.getWidth();
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.anchor = GridBagConstraints.NORTH;

        rightPanel.add(createTitle(), gbc);

        gbc.weighty = 1;
        gbc.insets = new Insets(100, 0, 0, 0);
        rightPanel.add(createTextInputContainer(), gbc);

        container.add(leftPanel);
        container.add(rightPanel);
        return container;
    }

    private JPanel createTitle() {
        // title container to indicate sign in or sign up
        JPanel titleContainer = new JPanel();
        titleContainer.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(50f));
        titleLabel.setForeground(Color.BLACK);
        titleContainer.add(titleLabel);

        return titleContainer;
    }

    private JPanel createTextInputContainer() {
        // create container that contains relevant info for sign in or sign up
        JPanel textInputContainer = new JPanel(new BorderLayout());

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

        textInputContainer.add(container);

        return textInputContainer;
    }

    private JPanel createFullNameContainer() {
        // full name
        JPanel fullNamePanel = new JPanel(new BorderLayout());
        fullNamePanel.setBackground(Color.WHITE);

        JLabel fullNameLabel = new JLabel("Full Name: ");
        fullNameLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        fullNameLabel.setForeground(Color.BLACK);

        JTextField fullNameInput = new JTextField();
        fullNameInput.setPreferredSize(new Dimension(350, 50));
        fullNameInput.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        fullNameInput.setBorder(new LineBorder(Color.BLACK, 2));

        fullNamePanel.add(fullNameLabel, BorderLayout.NORTH);
        fullNamePanel.add(fullNameInput, BorderLayout.CENTER);

        return fullNamePanel;
    }
    private JPanel createGenderContainer() {
        // gender
        JPanel genderPanel = new JPanel(new BorderLayout());
        genderPanel.setBackground(Color.WHITE);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        genderLabel.setForeground(Color.BLACK);

        JPanel genderButtonsPanel = new JPanel(new BorderLayout());
        genderButtonsPanel.setBorder(new LineBorder(Color.BLACK, 3));

        // male button
        JButton maleButton = new JButton("M");
        maleButton.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(25f));
        maleButton.setPreferredSize(new Dimension(100, 50));
        maleButton.setFocusPainted(false);
        maleButton.setBackground(Color.GRAY);
        maleButton.setForeground(Color.WHITE);

        // female button
        JButton femaleButton = new JButton("F");
        femaleButton.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(25f));
        femaleButton.setPreferredSize(new Dimension(100, 50));
        femaleButton.setFocusPainted(false);
        femaleButton.setBackground(Color.GRAY);
        femaleButton.setForeground(Color.WHITE);

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
        emailLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        emailLabel.setForeground(Color.BLACK);

        JTextField emailInput = new JTextField();
        emailInput.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        emailInput.setPreferredSize(new Dimension(600, 50));
        emailInput.setBorder(new LineBorder(Color.BLACK, 2));

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
        phoneLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(20f));
        phoneLabel.setForeground(Color.BLACK);

        JPanel phoneInputPanel = new JPanel(new GridBagLayout());
        JButton countryCode = new JButton();
        countryCode.setText("+60");
        countryCode.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(25f));
        countryCode.setPreferredSize(new Dimension(100, 50));
        countryCode.setFocusPainted(false);
        countryCode.setBackground(Color.BLACK);
        countryCode.setForeground(Color.WHITE);
        countryCode.setBorder(new LineBorder(Color.BLACK, 2));

        JTextField phoneInput = new JTextField();
        phoneInput.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(30f));
        phoneInput.setPreferredSize(new Dimension(500, 50));
        phoneInput.setBorder(new LineBorder(Color.BLACK, 2));

        phoneInputPanel.add(countryCode);
        phoneInputPanel.add(phoneInput);

        phonePanel.add(phoneLabel, gbc);
        phonePanel.add(phoneInputPanel);

        return phonePanel;
    }
}
