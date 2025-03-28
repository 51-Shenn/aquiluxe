package gui;

import datamodels.User;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.File;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.w3c.dom.events.MouseEvent;

public class OverflowMenu extends JLayeredPane {

    private final JFrame frame;
    private final JPanel panel;
    private User user;
    public final int MENU_WIDTH;
    public final int MENU_HEIGHT;
    private boolean isExpanded = false;
    private JPanel themeButton;
    private JPanel switchAccountButton;
    private JPanel signOutButton;
    private JPanel deleteAccountButton;
    private JPanel closeButton;
    private JPanel editPanel;
    private JButton editButton;

    public OverflowMenu(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;
        MENU_WIDTH = 400;
        if(this.user.getFullName().equals("Guest") && this.user.getUsername().equals("guest"))
            MENU_HEIGHT = 550;
        else
            MENU_HEIGHT = 800;
        add(createOverflowMenu(), JLayeredPane.POPUP_LAYER);
    }

    private JPanel createOverflowMenu() {
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBackground(Color.BLACK);
        container.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        container.add(createMainCard());
        return container;
    }

    private JPanel createMainCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        panel.setBackground(new Color(0, 0, 0, 40));
        panel.setBorder(new LineBorder(new Color(0, 0, 0, 60), 1));

        File sunMoonFilePath = new File("images/icons/sun-moon.png");
        File switchFilePath = new File("images/icons/switch.png");
        File signInFilePath = new File("images/icons/sign-in.png");
        File signOutFilePath = new File("images/icons/sign-out.png");
        File roundCloseFilePath = new File("images/icons/round-close.png");
        if(!sunMoonFilePath.exists() || !switchFilePath.exists() || !signInFilePath.exists() || !signOutFilePath.exists() || !roundCloseFilePath.exists())
            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + sunMoonFilePath + "\n" + switchFilePath + "\n" + signOutFilePath + "\n" + roundCloseFilePath);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(1, 0, 0, 0);
        panel.add(createProfileCard(), gbc);

        gbc.weighty = 1;

        if (this.user.getFullName().equals("Guest") && this.user.getUsername().equals("guest")) {
            panel.add(createMenuCard("Sign Up", signInFilePath), gbc);
            panel.add(createMenuCard("Sign In", signInFilePath), gbc);
        } else {
            themeButton = createMenuCard("Theme", sunMoonFilePath);
            panel.add(themeButton, gbc);

            switchAccountButton = createSwitchAccountPanel(switchFilePath);
            panel.add(switchAccountButton, gbc);

            signOutButton = createMenuCard("Sign Out", signOutFilePath);
            panel.add(signOutButton, gbc);

            deleteAccountButton = createMenuCard("Delete Account", sunMoonFilePath);
            panel.add(deleteAccountButton, gbc);
        }

        closeButton = createMenuCard("Close / Exit", roundCloseFilePath);
        panel.add(closeButton, gbc);

        editPanel = new JPanel(new GridBagLayout());
        editPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        JTextField fullNameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField drivingLicenseField = new JTextField();

        JLabel fullNameValidationLabel = new JLabel("");
        JLabel usernameValidationLabel = new JLabel("");
        JLabel emailValidationLabel = new JLabel("");
        JLabel phoneNumberValidationLabel = new JLabel("");
        JLabel drivingLicenseValidationLabel = new JLabel("");

        JPanel fullNamePanel = createEditProfileInputField("Full Name", fullNameField, fullNameValidationLabel);
        JPanel usernamePanel = createEditProfileInputField("Username", usernameField, usernameValidationLabel);
        JPanel emailPanel = createEditProfileInputField("Email Address", emailField, emailValidationLabel);
        JPanel phoneNumberPanel = createEditProfileInputField("Phone Number", phoneNumberField, phoneNumberValidationLabel);
        JPanel drivingLicensePanel = createEditProfileInputField("Driving License", drivingLicenseField, drivingLicenseValidationLabel);

        // Add panels to the edit panel
        editPanel.add(fullNamePanel, gbc2);
        editPanel.add(usernamePanel, gbc2);
        editPanel.add(emailPanel, gbc2);
        editPanel.add(phoneNumberPanel, gbc2);
        editPanel.add(drivingLicensePanel, gbc2);

        JPanel editPageButtonPanel = new JPanel(new GridBagLayout());
        editPageButtonPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.anchor = GridBagConstraints.EAST;
        gbc3.weightx = 1;
        gbc3.insets = new Insets(5, 0, 5, 15);

        JButton updateButton = new JButton("Update");
        updateButton.setBackground(Color.GREEN);
        updateButton.setForeground(Color.BLACK);
        updateButton.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(18f));
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        updateButton.setPreferredSize(new Dimension(100, 50));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(18f));
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(100, 50));
        cancelButton.addActionListener(e -> {
            editPanel.setVisible(false);
            editButton.setVisible(true);
            themeButton.setVisible(true);
            switchAccountButton.setVisible(true);
            signOutButton.setVisible(true);
            deleteAccountButton.setVisible(true);
            closeButton.setVisible(true);

            this.revalidate();
            this.repaint();
        });

        editPageButtonPanel.add(cancelButton, gbc3);
        editPageButtonPanel.add(updateButton, gbc3);

        editPanel.add(editPageButtonPanel, gbc3);
        editPanel.setVisible(false);

        gbc2.weighty = 1;
        gbc2.anchor = GridBagConstraints.NORTH;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(1, 0, 0, 0);
        panel.add(editPanel, gbc2);

        return panel;
    }

    private JPanel createEditProfileInputField(String text, JTextField inputField, JLabel validationLabel) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel(text + ":");
        label.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));

        inputField.setPreferredSize(new Dimension(200, 40));
        inputField.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(17f));
        inputField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(5, 10, 5, 10)));

        switch (text) {
            case "Full Name" -> inputField.setText(this.user.getFullName());
            case "Username" -> inputField.setText(this.user.getUsername());
            case "Email Address" -> inputField.setText(this.user.getUserEmail());
            case "Phone Number" -> inputField.setText("+60" + this.user.getPhoneNumber());
            case "Driving License" -> inputField.setText("");
        }

        gbc.insets = new Insets(10, 30, 0, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(label, gbc);

        validationLabel.setText("hello");
        if(validationLabel.getText().isEmpty())
            gbc.insets = new Insets(0, 30, 5, 30);
        else
            gbc.insets = new Insets(0, 30, 0, 30);

        panel.add(inputField, gbc);

        validationLabel.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(validationLabel, gbc);

        return panel;
    }

    private JPanel createSwitchAccountPanel(File iconFilePath) {
        JPanel switchAccountPanel = new JPanel(new BorderLayout());
        switchAccountPanel.setBackground(Color.WHITE);

        ImageIcon switchIcon = new ImageIcon(iconFilePath.toString());
        JButton button = createMenuButton("Switch Account", switchIcon);

        button.addActionListener(e -> {
            if (!isExpanded) {
                isExpanded = true;
                switchAccountPanel.removeAll();
                button.setText("Back");
                button.setForeground(Color.BLUE);
                button.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(20f));
                button.setIcon(null);
                switchAccountPanel.add(button, BorderLayout.SOUTH);

                JPanel accountsPanel = new JPanel(new GridBagLayout());
                accountsPanel.setBackground(Color.WHITE);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.weightx = 1;
                gbc.insets = new Insets(20, 30, 5, 30);

                JLabel accountLabel = new JLabel("Accounts");
                accountLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(20f));
                accountLabel.setForeground(Color.BLACK);
                gbc.weighty = 0;
                accountsPanel.add(accountLabel, gbc);

                File plusFilePath = new File("images/icons/plus.png");
                if (!plusFilePath.exists())
                    JOptionPane.showMessageDialog(null, "Failed to load image:\n" + plusFilePath);

                ImageIcon plusIcon = new ImageIcon(plusFilePath.toString());
                JButton addAccountButton = new JButton("Add New Profile");
                addAccountButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(18f));
                addAccountButton.setIcon(plusIcon);
                addAccountButton.setFocusPainted(false);
                addAccountButton.setForeground(Color.BLACK);
                addAccountButton.setBackground(Color.WHITE);
                addAccountButton.setHorizontalAlignment(SwingConstants.LEFT);
                addAccountButton.setIconTextGap(30);
                addAccountButton.setBorder(new EmptyBorder(15, 25, 15, 25));
                addAccountButton.setPreferredSize(new Dimension(MENU_WIDTH, 40));
                addAccountButton.addActionListener(ee -> {
                    frame.getLayeredPane().remove(this);
                    frame.getContentPane().removeAll();
                    frame.add(new SignInPage(this.frame, this.panel, this.user));
                    frame.revalidate();
                    frame.repaint();
                });
                accountsPanel.add(addAccountButton, gbc);

                gbc.weighty = 1;
                for (User user : User.getUsers().values()) {
                    JButton accountButton = new JButton();
                    accountButton.setBackground(Color.WHITE);

                    File smallMaleFilePath = new File("images/icons/small-male.png");
                    File smallFemaleFilePath = new File("images/icons/small-female.png");
                    if (!smallMaleFilePath.exists() || !smallFemaleFilePath.exists()) {
                        JOptionPane.showMessageDialog(null, "Failed to load image:\n" + smallMaleFilePath + "\n" + smallFemaleFilePath);
                    }

                    ImageIcon genderIcon;
                    if(user.getGender().equals("Male"))
                        genderIcon = new ImageIcon(smallMaleFilePath.toString());
                    else
                        genderIcon = new ImageIcon(smallFemaleFilePath.toString());

                    accountButton.setFocusPainted(false);
                    accountButton.setBorderPainted(false);

                    accountButton.setLayout(new GridBagLayout());
                    GridBagConstraints gbcButton = new GridBagConstraints();
                    gbcButton.anchor = GridBagConstraints.WEST;
                    gbcButton.fill = GridBagConstraints.HORIZONTAL;
                    gbcButton.insets = new Insets(10, 0, 10, 25);

                    gbcButton.gridx = 0;
                    gbcButton.gridy = 0;
                    gbcButton.weightx = 0;
                    accountButton.add(new JLabel(genderIcon), gbcButton);

                    JPanel accountDetails = new JPanel(new GridBagLayout());
                    accountDetails.setBackground(new Color(255, 255, 255, 0));

                    JLabel fullNameLabel = new JLabel(user.getFullName());
                    fullNameLabel.setForeground(Color.BLACK);
                    fullNameLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(18f));
                    JLabel usernameLabel = new JLabel("@" + user.getUsername());
                    usernameLabel.setForeground(Color.GRAY);
                    usernameLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(14f));

                    GridBagConstraints gbcDetails = new GridBagConstraints();
                    gbcDetails.anchor = GridBagConstraints.WEST;
                    gbcDetails.fill = GridBagConstraints.HORIZONTAL;
                    gbcDetails.gridwidth = GridBagConstraints.REMAINDER;
                    gbcDetails.weightx = 1;
                    gbcDetails.insets = new Insets(0, 0, 0, 0);

                    accountDetails.add(fullNameLabel, gbcDetails);
                    accountDetails.add(usernameLabel, gbcDetails);

                    gbcButton.gridx = 1;
                    gbcButton.gridy = 0;
                    gbcButton.weightx = 1;
                    accountButton.add(accountDetails, gbcButton);

                    gbc.insets = new Insets(5, 30, 5, 30);
                    accountsPanel.add(accountButton, gbc);

                    themeButton.setVisible(false);
                    signOutButton.setVisible(false);
                    deleteAccountButton.setVisible(false);
                    closeButton.setVisible(false);
                }

                switchAccountPanel.add(accountsPanel, BorderLayout.CENTER);
            } else {
                isExpanded = false;
                switchAccountPanel.removeAll();
                button.setText("Switch Account");
                button.setForeground(Color.BLACK);
                button.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(20f));
                button.setIcon(switchIcon);
                switchAccountPanel.add(button, BorderLayout.CENTER);

                themeButton.setVisible(true);
                signOutButton.setVisible(true);
                closeButton.setVisible(true);
            }

            this.revalidate();
            this.repaint();

            switchAccountPanel.revalidate();
            switchAccountPanel.repaint();
        });

        switchAccountPanel.add(button, BorderLayout.CENTER);
        return switchAccountPanel;
    }

    private JPanel createProfileCard() {
        JPanel profileMainPanel = new JPanel(new GridBagLayout());
        profileMainPanel.setBackground(Color.WHITE);

        JPanel profileCard = new JPanel(new GridBagLayout());
        profileCard.setBackground(Color.WHITE);
        profileCard.setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        File maleFilePath = new File("images/icons/male.png");
        File femaleFilePath = new File("images/icons/female.png");
        File editFilePath = new File("images/icons/edit.png");
        if(!maleFilePath.exists() || !femaleFilePath.exists() || !editFilePath.exists())
            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + maleFilePath + "\n" + femaleFilePath + "\n" + editFilePath);

        ImageIcon genderIcon;
        if(this.user.getGender().equals("Male"))
            genderIcon = new ImageIcon(maleFilePath.toString());
        else
            genderIcon = new ImageIcon(femaleFilePath.toString());

        JLabel profileIcon = new JLabel();
        profileIcon.setIcon(genderIcon);

        JLabel nameLabel = new JLabel(this.user.getFullName());
        nameLabel.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(30f));
        nameLabel.setForeground(Color.BLACK);

        JLabel usernameLabel = new JLabel("@" + this.user.getUsername());
        usernameLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        usernameLabel.setForeground(Color.GRAY);

        JButton editButton = createEditButton(editFilePath);

        gbc.insets = new Insets(25, 0, 0, 0);
        profileCard.add(profileIcon, gbc);
        gbc.insets = new Insets(15, 0, 0, 0);
        profileCard.add(nameLabel, gbc);
        gbc.insets = new Insets(5, 0, 15, 0);
        profileCard.add(usernameLabel, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        if(!this.user.getFullName().equals("Guest") && !this.user.getUsername().equals("guest"))
            profileCard.add(editButton, gbc);

        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        profileMainPanel.add(profileCard, gbc);
        return profileMainPanel;
    }

    private JButton createEditButton(File editFilePath) {
        ImageIcon editIcon = new ImageIcon(editFilePath.toString());
        editButton = new JButton("Edit Profile");
        editButton.setIcon(editIcon);
        editButton.setIconTextGap(15);
        editButton.setHorizontalTextPosition(SwingConstants.LEFT);
        editButton.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(18f));
        editButton.setBackground(Color.WHITE);
        editButton.setForeground(Color.BLACK);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);
        editButton.addActionListener(e -> {
            editButton.setVisible(false);
            themeButton.setVisible(false);
            switchAccountButton.setVisible(false);
            signOutButton.setVisible(false);
            deleteAccountButton.setVisible(false);
            closeButton.setVisible(false);
            editPanel.setVisible(true);

            this.revalidate();
            this.repaint();
        });
        editButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                editButton.setBackground(Color.BLUE.darker());
            }
        
            public void mouseExited(MouseEvent evt) {
                editButton.setBackground(Color.BLUE);
            }
        
            public void mousePressed(MouseEvent evt) {
                editButton.setBackground(Color.CYAN);
            }
        
            public void mouseReleased(MouseEvent evt) {
                editButton.setBackground(Color.BLUE);
            }
        });

        return editButton;
    }

    private JPanel createMenuCard(String text, File iconFilePath) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon(iconFilePath.toString());

        JButton button = createMenuButton(text, icon);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;

        card.add(button, gbc);
        return card;
    }

    private JButton createMenuButton(String text, ImageIcon icon) {

        JButton button = new JButton(text);
        button.setIcon(icon);
        button.setIconTextGap(30);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(Color.WHITE);
        button.setBorder(new EmptyBorder(0, 50, 0, 0));
        button.setForeground(Color.BLACK);
        button.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(20f));
        button.setPreferredSize(new Dimension(MENU_WIDTH, 70));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        if(text.equals("Theme")) {
            File sunFilePath = new File("images/icons/sun.png");
            File moonFilePath = new File("images/icons/moon.png");
            if(!sunFilePath.exists() || !moonFilePath.exists())
                JOptionPane.showMessageDialog(null, "Failed to load image:\n" + sunFilePath + "\n" + moonFilePath);

            ImageIcon sunIcon = new ImageIcon(sunFilePath.toString());
            ImageIcon moonIcon = new ImageIcon(moonFilePath.toString());

            button.addActionListener(e -> {
                if(button.getIcon() == moonIcon) {
                    button.setIcon(sunIcon);
                    button.setText("Light Theme");
                }
                else {
                    button.setIcon(moonIcon);
                    button.setText("Dark Theme");
                }
                frame.revalidate();
                frame.repaint();
            });
        }
        if(text.equals("Sign Up")) {
            button.addActionListener(e -> {
                frame.getLayeredPane().remove(this);
                frame.getContentPane().removeAll();

                UIManager.getDefaults().clear();  // Clear all cached UI properties
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // Reset to default
                    for (Window window : Window.getWindows()) {
                        SwingUtilities.updateComponentTreeUI(window);
                        window.repaint();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.add(new SignUpPage(this.frame, this.panel, this.user));
                frame.revalidate();
                frame.repaint();
            });
        }
        if(text.equals("Sign In")) {
            button.addActionListener(e -> {
                frame.getLayeredPane().remove(this);
                frame.getContentPane().removeAll();

                UIManager.getDefaults().clear();  // Clear all cached UI properties
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // Reset to default
                    for (Window window : Window.getWindows()) {
                        SwingUtilities.updateComponentTreeUI(window);
                        window.repaint();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                frame.add(new SignInPage(this.frame, this.panel, this.user));
                frame.revalidate();
                frame.repaint();
            });
        }
        if(text.equals("Sign Out")) {
            button.addActionListener(e -> {
                frame.getLayeredPane().remove(this);
                this.frame.getContentPane().removeAll();
                this.frame.setLayout(new BorderLayout());
                GUIComponents.overflowMenu = null;
                this.user = new User();
                this.frame.add(new GUIComponents(this.frame, this.panel, this.user), BorderLayout.NORTH);
                this.frame.add(this.panel, BorderLayout.CENTER);
                this.panel.removeAll();
                this.frame.revalidate();
                this.frame.repaint();
            });
        }
        if(text.equals("Close / Exit")) {
            if(isExpanded) button.setVisible(false);
            button.addActionListener(e -> frame.dispose());
        }

        return button;
    }
}
