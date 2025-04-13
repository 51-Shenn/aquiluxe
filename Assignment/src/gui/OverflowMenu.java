package gui;

import controllers.UserController;
import database.UserDAO;
import datamodels.Customer;
import datamodels.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class OverflowMenu extends JLayeredPane {

    private JFrame frame;
    private JPanel panel;
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
    private final File THEME_FILE = new File("files/settings/theme.txt");
    private final File ACCOUNTS_FILE = new File("files/settings/accounts.txt");
    private int[] userAccountsID = new int[4];

    public OverflowMenu() {
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.user = new User();
        this.MENU_WIDTH = 0;
        this.MENU_HEIGHT = 0;
    }

    public OverflowMenu(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;
        MENU_WIDTH = 400;
        if(this.user.getFullName().equals("Guest") && this.user.getUsername().equals("guest"))
            MENU_HEIGHT = 550;
        else
            MENU_HEIGHT = 800;
        setBackground(Theme.getBackground());
        add(createOverflowMenu(), JLayeredPane.POPUP_LAYER);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMENU_WIDTH() {
        return MENU_WIDTH;
    }

    public int getMENU_HEIGHT() {
        return MENU_HEIGHT;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public JPanel getThemeButton() {
        return themeButton;
    }

    public void setThemeButton(JPanel themeButton) {
        this.themeButton = themeButton;
    }

    public JPanel getSwitchAccountButton() {
        return switchAccountButton;
    }

    public void setSwitchAccountButton(JPanel switchAccountButton) {
        this.switchAccountButton = switchAccountButton;
    }

    public JPanel getSignOutButton() {
        return signOutButton;
    }

    public void setSignOutButton(JPanel signOutButton) {
        this.signOutButton = signOutButton;
    }

    public JPanel getDeleteAccountButton() {
        return deleteAccountButton;
    }

    public void setDeleteAccountButton(JPanel deleteAccountButton) {
        this.deleteAccountButton = deleteAccountButton;
    }

    public JPanel getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(JPanel closeButton) {
        this.closeButton = closeButton;
    }

    public JPanel getEditPanel() {
        return editPanel;
    }

    public void setEditPanel(JPanel editPanel) {
        this.editPanel = editPanel;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public void setEditButton(JButton editButton) {
        this.editButton = editButton;
    }

    public File getTHEME_FILE() {
        return THEME_FILE;
    }

    public File getACCOUNTS_FILE() {
        return ACCOUNTS_FILE;
    }

    public int[] getUserAccountsID() {
        return userAccountsID;
    }

    public void setUserAccountsID(int[] userAccountsID) {
        this.userAccountsID = userAccountsID;
    }

    private JPanel createOverflowMenu() {
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBackground(Theme.getBackground());
        container.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        container.add(createMainCard());
        return container;
    }

    private JPanel createMainCard() {
        JPanel mainCardPanel = new JPanel(new GridBagLayout());
        mainCardPanel.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        mainCardPanel.setBackground(new Color(0, 0, 0, 40));
        mainCardPanel.setBorder(new LineBorder(new Color(0, 0, 0, 60), 1));

        File sunMoonFilePath = new File("images/icons/sun-moon.png");
        File sunFilePath = new File("images/icons/sun.png");
        File moonFilePath = new File("images/icons/moon.png");
        File switchFilePath = new File("images/icons/switch.png");
        File signInFilePath = new File("images/icons/sign-in.png");
        File signOutFilePath = new File("images/icons/sign-out.png");
        File deleteFilePath = new File("images/icons/trash.png");
        File roundCloseFilePath = new File("images/icons/round-close.png");
        if(!sunMoonFilePath.exists() || !switchFilePath.exists() || !signInFilePath.exists() || !signOutFilePath.exists() || !roundCloseFilePath.exists() || !deleteFilePath.exists())
            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + sunMoonFilePath + "\n" + sunFilePath + "\n" + moonFilePath + "\n" + switchFilePath + "\n" + signOutFilePath + "\n" + roundCloseFilePath + "\n" + deleteFilePath);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(1, 0, 0, 0);
        mainCardPanel.add(createProfileCard(), gbc);

        gbc.weighty = 1;

        if (this.user.getFullName().equals("Guest") && this.user.getUsername().equals("guest")) {
            mainCardPanel.add(createMenuCard("Sign Up", signInFilePath), gbc);
            mainCardPanel.add(createMenuCard("Sign In", signInFilePath), gbc);
        } else {
            if(!THEME_FILE.exists()) {
                themeButton = createMenuCard("Theme", sunMoonFilePath);
            }
            else {
                try(BufferedReader reader = new BufferedReader(new FileReader(THEME_FILE))) {
                    String line = reader.readLine();
                    themeButton = line.equals("Dark") ? createMenuCard("Dark Theme", moonFilePath) : createMenuCard("Light Theme", sunFilePath);
                } catch(FileNotFoundException exception) {
                    JOptionPane.showMessageDialog(null, "Could not locate file location: " + THEME_FILE);
                } catch(IOException exception) {
                    JOptionPane.showMessageDialog(null, "Could not write file: " + THEME_FILE);
                }
            }
            mainCardPanel.add(themeButton, gbc);

            switchAccountButton = createSwitchAccountPanel(switchFilePath);
            mainCardPanel.add(switchAccountButton, gbc);

            signOutButton = createMenuCard("Sign Out", signOutFilePath);
            mainCardPanel.add(signOutButton, gbc);

            deleteAccountButton = createMenuCard("Delete Account", deleteFilePath);
            mainCardPanel.add(deleteAccountButton, gbc);
        }

        closeButton = createMenuCard("Close / Exit", roundCloseFilePath);
        mainCardPanel.add(closeButton, gbc);

        editPanel = new JPanel(new GridBagLayout());
        editPanel.setBackground(Theme.getBackground());
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
        JPanel phoneNumberPanel = createEditProfileInputField("Phone Number (+60)", phoneNumberField, phoneNumberValidationLabel);
        JPanel drivingLicensePanel = createEditProfileInputField("Driving License", drivingLicenseField, drivingLicenseValidationLabel);

        // Add panels to the edit panel
        editPanel.add(fullNamePanel, gbc2);
        editPanel.add(usernamePanel, gbc2);
        editPanel.add(emailPanel, gbc2);
        editPanel.add(phoneNumberPanel, gbc2);
        editPanel.add(drivingLicensePanel, gbc2);

        JPanel editPageButtonPanel = new JPanel(new GridBagLayout());
        editPageButtonPanel.setBackground(Theme.getBackground());
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.anchor = GridBagConstraints.EAST;
        gbc3.weightx = 1;
        gbc3.insets = new Insets(10, 0, 5, 15);

        RoundedButton updateButton = new RoundedButton(8, Theme.getSuccess());
        updateButton.setText("Update");
        updateButton.setBackground(Theme.getSuccess());
        updateButton.setForeground(Theme.getSuccessForeground());
        updateButton.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(18f));
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        updateButton.setPreferredSize(new Dimension(100, 50));
        updateButton.addActionListener(e -> {
            Dialog dialog = new Dialog(this.frame);
            boolean proceed = dialog.showDialog(
                "HAZARD",
                "Confirm Update",
                "Warning: Changes Ahead",
                "Your existing information will be overwritten.",
                true
            );
            
            if(proceed) {
                boolean isValidUpdateDetails = UserController.passUpdateProfileDetails(this.user, fullNameField.getText(), usernameField.getText(), emailField.getText(), phoneNumberField.getText(), drivingLicenseField.getText(),
                    fullNameValidationLabel, usernameValidationLabel, emailValidationLabel, phoneNumberValidationLabel, drivingLicenseValidationLabel);
                if(isValidUpdateDetails) {
                    this.frame.getLayeredPane().remove(this);
                    this.frame.getContentPane().removeAll();
                    this.frame.setLayout(new BorderLayout());
                    GUIComponents.overflowMenu = null;
                    
                    UserDAO userDAO = new UserDAO();
                    this.user = userDAO.getUserById(this.user.getUserId());

                    this.frame.add(new GUIComponents(this.frame, this.panel, this.user), BorderLayout.NORTH);
                    this.frame.add(this.panel, BorderLayout.CENTER);
                    this.panel.removeAll();
                    this.frame.revalidate();
                    this.frame.repaint();

                    dialog.showDialog(
                        "SUCCESS",
                        "Update Profile",
                        "Profile Updated",
                        "Your information has been saved.",
                        false
                    );
                }
            }
        });

        RoundedButton cancelButton = new RoundedButton(8, Theme.getError());
        cancelButton.setText("Cancel");
        cancelButton.setBackground(Theme.getError());
        cancelButton.setForeground(Theme.getErrorForeground());
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
        mainCardPanel.add(editPanel, gbc2);

        return mainCardPanel;
    }

    private JPanel createEditProfileInputField(String text, JTextField inputField, JLabel validationLabel) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel(text + ":");
        label.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));
        label.setBackground(Theme.getBackground());
        label.setForeground(Theme.getForeground());

        inputField.setPreferredSize(new Dimension(200, 40));
        inputField.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(17f));
        inputField.setBackground(Theme.getBackground());
        inputField.setForeground(Theme.getForeground());
        inputField.setBorder(new CompoundBorder(new LineBorder(Theme.getForeground(), 1), new EmptyBorder(5, 10, 5, 10)));

        switch (text) {
            case "Full Name" -> inputField.setText(this.user.getFullName());
            case "Username" -> inputField.setText(this.user.getUsername());
            case "Email Address" -> inputField.setText(this.user.getUserEmail());
            case "Phone Number (+60)" -> inputField.setText(this.user.getPhoneNumber());
            case "Driving License" -> {
                try {
                    Customer customer = new UserDAO().getCustomerById(this.user);
                    inputField.setText(customer.getLicense());
                } catch (Exception e) {
                    inputField.setText("");
                }
            }
        }

        gbc.insets = new Insets(5, 30, 0, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(label, gbc);

        validationLabel.setText("‎");
        gbc.insets = new Insets(2, 30, 2, 30);

        panel.add(inputField, gbc);

        validationLabel.setForeground(Theme.getError());
        validationLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(12f));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(validationLabel, gbc);

        return panel;
    }

    private JPanel createSwitchAccountPanel(File iconFilePath) {
        JPanel switchAccountPanel = new JPanel(new BorderLayout());
        switchAccountPanel.setBackground(Theme.getBackground());

        ImageIcon switchIcon = new ImageIcon(iconFilePath.toString());
        JButton button = createMenuButton("Switch Account", switchIcon);

        button.addActionListener(e -> {
            if (!isExpanded) {
                isExpanded = true;
                switchAccountPanel.removeAll();
                button.setText("Back");
                button.setForeground(Theme.getSpecial());
                button.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(20f));
                button.setIcon(null);
                switchAccountPanel.add(button, BorderLayout.SOUTH);

                JPanel accountsPanel = new JPanel(new GridBagLayout());
                accountsPanel.setBackground(Theme.getBackground());

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.weightx = 1;
                gbc.insets = new Insets(20, 30, 5, 30);

                JLabel accountLabel = new JLabel("Accounts");
                accountLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(20f));
                accountLabel.setForeground(Theme.getForeground());
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
                addAccountButton.setForeground(Theme.getForeground());
                addAccountButton.setBackground(Theme.getBackground());
                addAccountButton.setHorizontalAlignment(SwingConstants.LEFT);
                addAccountButton.setIconTextGap(30);
                addAccountButton.setBorder(new EmptyBorder(15, 25, 15, 25));
                addAccountButton.setPreferredSize(new Dimension(MENU_WIDTH, 40));
                addAccountButton.addActionListener(ee -> {
                    frame.getLayeredPane().remove(this);
                    frame.getContentPane().removeAll();

                    UIManager.getDefaults().clear();
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
                accountsPanel.add(addAccountButton, gbc);

                gbc.weighty = 1;

                if(ACCOUNTS_FILE.exists()) {
                    userAccountsID = UserController.loadExistingUserInFile(userAccountsID, ACCOUNTS_FILE);
                }

                for (int userID : userAccountsID) {
                    if(userID != 0 && userID != this.user.getUserId()) {
                        UserDAO userDAO = new UserDAO();
                        User everyUser = userDAO.getUserById(userID);

                        RoundedButton accountButton = new RoundedButton(20, Theme.getBackground());
                        accountButton.setBackground(Theme.getBackground());
                        accountButton.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent evt) {
                                accountButton.setBackground(new Color(223, 223, 223));
                                accountButton.repaint();
                            }
                
                            @Override
                            public void mouseExited(MouseEvent evt) {
                                accountButton.setBackground(Theme.getBackground());
                                accountButton.repaint();
                            }
                
                            @Override
                            public void mousePressed(MouseEvent evt) {
                                accountButton.setBackground(new Color(223, 223, 223));
                                accountButton.repaint();
                            }
                
                            @Override
                            public void mouseReleased(MouseEvent evt) {
                                accountButton.setBackground(Theme.getBackground());
                                accountButton.repaint();
                            }
                        });

                        File smallMaleFilePath = new File("images/icons/small-male.png");
                        File smallFemaleFilePath = new File("images/icons/small-female.png");
                        if (!smallMaleFilePath.exists() || !smallFemaleFilePath.exists()) {
                            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + smallMaleFilePath + "\n" + smallFemaleFilePath);
                        }

                        ImageIcon genderIcon;
                        if(everyUser.getGender().equals("Male"))
                            genderIcon = new ImageIcon(smallMaleFilePath.toString());
                        else
                            genderIcon = new ImageIcon(smallFemaleFilePath.toString());

                        accountButton.setFocusPainted(false);
                        accountButton.setBorderPainted(false);

                        accountButton.setLayout(new GridBagLayout());
                        GridBagConstraints gbcButton = new GridBagConstraints();
                        gbcButton.anchor = GridBagConstraints.NORTHWEST;
                        gbcButton.fill = GridBagConstraints.HORIZONTAL;
                        gbcButton.insets = new Insets(10, 0, 10, 25);

                        gbcButton.gridx = 0;
                        gbcButton.gridy = 0;
                        gbcButton.weightx = 0;
                        accountButton.add(new JLabel(genderIcon), gbcButton);

                        JPanel accountDetails = new JPanel(new GridBagLayout());
                        accountDetails.setBackground(new Color(255, 255, 255, 0));

                        JLabel fullNameLabel = new JLabel(everyUser.getFullName());
                        fullNameLabel.setForeground(Theme.getForeground());
                        fullNameLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(18f));
                        JLabel usernameLabel = new JLabel("@" + everyUser.getUsername());
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
                        accountButton.addActionListener(ex -> {
                            frame.getLayeredPane().remove(this);
                            this.frame.getContentPane().removeAll();
                            this.frame.setLayout(new BorderLayout());
                            GUIComponents.overflowMenu = null;
                            this.user = everyUser;
                            this.frame.add(new GUIComponents(this.frame, this.panel, this.user), BorderLayout.NORTH);
                            this.frame.add(this.panel, BorderLayout.CENTER);
                            this.panel.removeAll();
                            this.frame.revalidate();
                            this.frame.repaint();
                            
                            Dialog dialog = new Dialog(this.frame);
                            dialog.showDialog(
                                "SUCCESS",
                                "Switch Account",
                                "Switched to " + this.user.getUsername(),
                                "All switched! You're logged in as " + this.user.getUsername(),
                                false
                            );
                            
                            UserController.switchToAccount(this.user, ACCOUNTS_FILE);
                        });

                        gbc.insets = new Insets(5, 30, 5, 30);
                        gbc.weighty = 0;
                        accountsPanel.add(accountButton, gbc);                        
                    }
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
                button.setForeground(Theme.getForeground());
                button.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(20f));
                button.setIcon(switchIcon);
                switchAccountPanel.add(button, BorderLayout.CENTER);

                themeButton.setVisible(true);
                signOutButton.setVisible(true);
                deleteAccountButton.setVisible(true);
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
        profileMainPanel.setBackground(Theme.getBackground());

        JPanel profileCard = new JPanel(new GridBagLayout());
        profileCard.setBackground(Theme.getBackground());
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

        float baseFontSize = 24f; // max size (shorter name)
        float minFontSize = 10f;  // min size (longer name)
        float scaleFactor = 30f;

        float fontSize = baseFontSize + (scaleFactor / this.user.getFullName().length());
        fontSize = Math.max(fontSize, minFontSize);
        JLabel nameLabel = new JLabel(this.user.getFullName());
        nameLabel.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(fontSize));
        nameLabel.setForeground(Theme.getForeground());

        JLabel usernameLabel = new JLabel("@" + this.user.getUsername());
        usernameLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        usernameLabel.setForeground(Color.GRAY);

        editButton = createEditButton(editFilePath);

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
        editButton.setBackground(Theme.getBackground());
        editButton.setForeground(Theme.getForeground());
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

        return editButton;
    }

    private JPanel createMenuCard(String text, File iconFilePath) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.getBackground());
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
        button.setBackground(Theme.getBackground());
        button.setBorder(new EmptyBorder(0, 50, 0, 0));
        button.setForeground(Theme.getForeground());
        button.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(20f));
        button.setPreferredSize(new Dimension(MENU_WIDTH, 70));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(223, 223, 223));
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Theme.getBackground());
                button.repaint();
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                button.setBackground(new Color(223, 223, 223));
                button.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                button.setBackground(Theme.getBackground());
                button.repaint();
            }
        });

        if(text.equals("Theme") || text.equals("Dark Theme") || text.equals("Light Theme")) {
            File sunFilePath = new File("images/icons/sun.png");
            File moonFilePath = new File("images/icons/moon.png");
            if(!sunFilePath.exists() || !moonFilePath.exists())
                JOptionPane.showMessageDialog(null, "Failed to load image:\n" + sunFilePath + "\n" + moonFilePath);

            ImageIcon sunIcon = new ImageIcon(sunFilePath.toString());
            ImageIcon moonIcon = new ImageIcon(moonFilePath.toString());

            button.addActionListener(e -> {
                String theme = UserController.loadTheme(THEME_FILE);
                String newTheme;

                if(theme.equals("Dark")) {
                    newTheme = "Light";
                    button.setIcon(sunIcon);
                    button.setText("Light Theme");
                }
                else {
                    newTheme = "Dark";
                    button.setIcon(moonIcon);
                    button.setText("Dark Theme");
                }

                UserController.useTheme(newTheme, THEME_FILE);

                this.frame.getLayeredPane().remove(this);
                this.frame.getContentPane().removeAll();
                this.frame.setLayout(new BorderLayout());
                GUIComponents.overflowMenu = null;
                this.frame.add(new GUIComponents(this.frame, this.panel, this.user), BorderLayout.NORTH);
                this.frame.add(this.panel, BorderLayout.CENTER);
                this.panel.removeAll();
                this.frame.revalidate();
                this.frame.repaint();
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
                Dialog dialog = new Dialog(this.frame);
                boolean proceed = dialog.showDialog(
                    "HAZARD",
                    "Sign Out",
                    "Sign Out?",
                    "Logging out will end your session.",
                    true
                );  

                if(proceed) {
                    UserController.removeUserFromFile(this.user.getUserId(), ACCOUNTS_FILE);

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
                }
            });
        }
        if(text.equals("Delete Account")) {
            UserDAO userDAO = new UserDAO();
            
            button.addActionListener(e -> {
                Dialog dialog = new Dialog(this.frame);
                boolean proceed = dialog.showDialog(
                    "HAZARD",
                    "Delete Account",
                    "Delete Your Account?",
                    "This action cannot be undone. Proceed with deletion?",
                    true
                );                

                if(proceed) {
                    UserController.removeUserFromFile(this.user.getUserId(), ACCOUNTS_FILE);

                    userDAO.deleteUser(this.user.getUserId());

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
                }
            });
        }
        if(text.equals("Close / Exit")) {
            if(isExpanded) button.setVisible(false);
            button.addActionListener(e -> {
                Dialog dialog = new Dialog(this.frame);
                boolean proceed = dialog.showDialog(
                    "HAZARD",
                    "Exit",
                    "Close App?",
                    "Are you sure you want to exit?",
                    true
                );
                if(proceed) this.frame.dispose();
            });
        }

        return button;
    }
}
