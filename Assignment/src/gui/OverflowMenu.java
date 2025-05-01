package gui;

import controllers.UserController;
import datamodels.Admin;
import datamodels.Customer;
import datamodels.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class OverflowMenu extends JLayeredPane {

    private JFrame frame;
    private JPanel panel;
    private User user;
    private static GUIComponents guiComponents;
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
    private static int invokeAdminPortalCounter = 0;
    private static String generatedUUID;
    private static String position;

    public OverflowMenu() {
        this(new JFrame(), new JPanel(), new User());
    }

    public OverflowMenu(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;
        MENU_WIDTH = 400;
        if (this.user.getUserId() == 0)
            MENU_HEIGHT = 550;
        else
            MENU_HEIGHT = 800;
        setBackground(Theme.getBackground());
        add(createOverflowMenu(), JLayeredPane.POPUP_LAYER);
    }

    public static String getPosition() {
        return position;
    }

    public static void setPosition(String position) {
        OverflowMenu.position = position;
    }

    public static int getInvokeAdminPortalCounter() {
        return invokeAdminPortalCounter;
    }

    public static void setInvokeAdminPortalCounter(int invokeAdminPortalCounter) {
        OverflowMenu.invokeAdminPortalCounter = invokeAdminPortalCounter;
    }

    public static String getGeneratedUUID() {
        return generatedUUID;
    }

    public static void setGeneratedUUID(String generatedUUID) {
        OverflowMenu.generatedUUID = generatedUUID;
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

    public static GUIComponents getGuiComponents() {
        return guiComponents;
    }

    public static void setGuiComponents(GUIComponents guiComponents) {
        OverflowMenu.guiComponents = guiComponents;
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
        mainCardPanel.setBackground(Theme.getTransparencyColor());
        mainCardPanel.setBorder(new LineBorder(Theme.getTransparencyColor(), 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(1, 0, 0, 0);
        mainCardPanel.add(createProfileCard(), gbc);

        gbc.weighty = 1;

        if (this.user.getUserId() == 0) {
            mainCardPanel.add(createMenuCard("Sign Up", IconLoader.getSignInIcon()), gbc);
            mainCardPanel.add(createMenuCard("Sign In", IconLoader.getSignInIcon()), gbc);
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(THEME_FILE))) {
                String line = reader.readLine();
                themeButton = line.equals("Dark") ? createMenuCard("Dark Theme", IconLoader.getMoonIcon())
                        : createMenuCard("Light Theme", IconLoader.getSunIcon());
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null, "Could not locate file location: " + THEME_FILE);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "Could not write file: " + THEME_FILE);
            }

            mainCardPanel.add(themeButton, gbc);

            switchAccountButton = createSwitchAccountPanel(IconLoader.getSwitchIcon());
            mainCardPanel.add(switchAccountButton, gbc);

            signOutButton = createMenuCard("Sign Out", IconLoader.getSignOutIcon());
            mainCardPanel.add(signOutButton, gbc);

            deleteAccountButton = createMenuCard("Delete Account", IconLoader.getTrashIcon());
            mainCardPanel.add(deleteAccountButton, gbc);
        }

        closeButton = createMenuCard("Close / Exit", IconLoader.getCloseIcon());
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
        JTextField identityCardField = new JTextField();

        JLabel fullNameValidationLabel = new JLabel("");
        JLabel usernameValidationLabel = new JLabel("");
        JLabel emailValidationLabel = new JLabel("");
        JLabel phoneNumberValidationLabel = new JLabel("");
        JLabel icValidationLabel = new JLabel("");

        JPanel fullNamePanel = createEditProfileInputField("Full Name", fullNameField, fullNameValidationLabel);
        JPanel usernamePanel = createEditProfileInputField("Username", usernameField, usernameValidationLabel);
        JPanel emailPanel = createEditProfileInputField("Email Address", emailField, emailValidationLabel);
        JPanel phoneNumberPanel = createEditProfileInputField("Phone Number (+60)", phoneNumberField,
                phoneNumberValidationLabel);
        JPanel identityCardPanel = createEditProfileInputField("Identity Card", identityCardField, icValidationLabel);

        // Add panels to the edit panel
        editPanel.add(fullNamePanel, gbc2);
        editPanel.add(usernamePanel, gbc2);
        editPanel.add(emailPanel, gbc2);
        editPanel.add(phoneNumberPanel, gbc2);
        editPanel.add(identityCardPanel, gbc2);

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

            try {
                Dialog.getAdminDialog().dispose();
            } catch (Exception exception) {
            }

            boolean proceed = dialog.showDialog(
                    "HAZARD",
                    "Confirm Update",
                    "Warning: Changes Ahead",
                    "Your existing information will be overwritten.",
                    true);

            if (proceed) {
                boolean isValidUpdateDetails = UserController.passUpdateProfileDetails(this.user,
                        fullNameField.getText(), usernameField.getText(), emailField.getText(),
                        phoneNumberField.getText(), identityCardField.getText(),
                        fullNameValidationLabel, usernameValidationLabel, emailValidationLabel,
                        phoneNumberValidationLabel, icValidationLabel);
                if (isValidUpdateDetails) {
                    this.user = UserController.getUserFromDatabase(this.user);

                    this.frame.getLayeredPane().remove(this);
                    GUIComponents.refreshHomePage(this.frame, this.panel, this.user, guiComponents);

                    dialog.showDialog(
                            "SUCCESS",
                            "Update Profile",
                            "Profile Updated",
                            "Your information has been saved.",
                            false);
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
        JPanel editPanel = new JPanel(new GridBagLayout());
        editPanel.setBackground(Theme.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel(text + ":");
        label.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));
        label.setBackground(Theme.getBackground());
        label.setForeground(Theme.getForeground());

        inputField.setPreferredSize(new Dimension(200, 40));
        inputField.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(17f));
        inputField.setBackground(Theme.getBackground());
        inputField.setForeground(Theme.getForeground());
        inputField.setCaretColor(Theme.getForeground());
        inputField
                .setBorder(new CompoundBorder(new LineBorder(Theme.getForeground(), 1), new EmptyBorder(5, 10, 5, 10)));

        switch (text) {
            case "Full Name" -> inputField.setText(this.user.getFullName());
            case "Username" -> inputField.setText(this.user.getUsername());
            case "Email Address" -> inputField.setText(this.user.getUserEmail());
            case "Phone Number (+60)" -> inputField.setText(this.user.getPhoneNumber());
            case "Identity Card" -> {
                if (this.user.getUserType().equals("Customer")) {
                    try {
                        Customer customer = UserController.getCustomerFromDatabase(this.user);
                        inputField.setText(customer.getLicense());
                        inputField.setEditable(false);
                    } catch (Exception e) {
                        inputField.setText("");
                    }
                } else if (this.user.getUserType().equals("Admin")) {
                    label.setText("Position:");
                    Admin admin = UserController.getAdminFromDatabase(this.user);
                    inputField.setText(admin.getAdminRole());
                    inputField.setEditable(false);
                }
            }
        }

        gbc.insets = new Insets(5, 30, 0, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        editPanel.add(label, gbc);

        validationLabel.setText("‎");
        gbc.insets = new Insets(2, 30, 2, 30);

        editPanel.add(inputField, gbc);

        validationLabel.setForeground(Theme.getError());
        validationLabel.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(12f));
        gbc.gridx = 1;
        gbc.gridy = 2;
        editPanel.add(validationLabel, gbc);

        return editPanel;
    }

    private JPanel createSwitchAccountPanel(ImageIcon icon) {
        JPanel switchAccountPanel = new JPanel(new BorderLayout());
        switchAccountPanel.setBackground(Theme.getBackground());

        JButton button = createMenuButton("Switch Account", icon);

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

                ImageIcon plusIcon = IconLoader.getPlusIcon();
                JButton addAccountButton = new JButton();
                addAccountButton.setText("Add New Profile");
                addAccountButton.setOpaque(true);
                addAccountButton.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(18f));
                addAccountButton.setIcon(plusIcon);
                addAccountButton.setFocusPainted(false);
                addAccountButton.setForeground(Theme.getForeground());
                addAccountButton.setBackground(Theme.getBackground());
                addAccountButton.setHorizontalAlignment(SwingConstants.LEFT);
                addAccountButton.setIconTextGap(30);
                addAccountButton.setMargin(new Insets(15, 25, 15, 25));
                addAccountButton.setBorderPainted(false);
                addAccountButton.setPreferredSize(new Dimension(MENU_WIDTH, 40));
                addAccountButton
                        .addActionListener(new Navigation().toSignInPage(this.frame, this.panel, this, this.user));
                addAccountButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent evt) {
                        addAccountButton.setBackground(Theme.getHoverBackground());
                        addAccountButton.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent evt) {
                        addAccountButton.setBackground(Theme.getBackground());
                        addAccountButton.repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent evt) {
                        addAccountButton.setBackground(Theme.getPressedBackground());
                        addAccountButton.repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent evt) {
                        addAccountButton.setBackground(Theme.getBackground());
                        addAccountButton.repaint();
                    }
                });
                accountsPanel.add(addAccountButton, gbc);

                gbc.weighty = 1;

                if (ACCOUNTS_FILE.exists()) {
                    userAccountsID = UserController.loadExistingUserInFile(userAccountsID, ACCOUNTS_FILE);
                }

                for (int userID : userAccountsID) {
                    if (userID != 0 && userID != this.user.getUserId()) {
                        User everyUser = UserController.getUserFromDatabase(userID);

                        RoundedButton accountButton = new RoundedButton(20, Theme.getBackground());
                        accountButton.setBackground(Theme.getBackground());
                        accountButton.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent evt) {
                                accountButton.setBackground(Theme.getHoverBackground());
                                accountButton.repaint();
                            }

                            @Override
                            public void mouseExited(MouseEvent evt) {
                                accountButton.setBackground(Theme.getBackground());
                                accountButton.repaint();
                            }

                            @Override
                            public void mousePressed(MouseEvent evt) {
                                accountButton.setBackground(Theme.getPressedBackground());
                                accountButton.repaint();
                            }

                            @Override
                            public void mouseReleased(MouseEvent evt) {
                                accountButton.setBackground(Theme.getBackground());
                                accountButton.repaint();
                            }
                        });

                        ImageIcon genderIcon;
                        if (everyUser.getGender().equals("Male"))
                            genderIcon = IconLoader.getSmallMaleProfileIcon();
                        else
                            genderIcon = IconLoader.getSmallFemaleProfileIcon();

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

                        JLabel profilePicture = new JLabel(genderIcon);

                        accountButton.add(profilePicture, gbcButton);

                        JPanel accountDetails = new JPanel(new GridBagLayout());
                        accountDetails.setBackground(new Color(255, 255, 255, 0));

                        JLabel fullNameLabel = new JLabel(everyUser.getFullName());
                        fullNameLabel.setForeground(Theme.getForeground());
                        fullNameLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(18f));

                        JLabel usernameLabel = new JLabel("@" + everyUser.getUsername());
                        usernameLabel.setForeground(Theme.getSecondaryForeground());
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
                            this.user = everyUser;

                            frame.getLayeredPane().remove(this);
                            GUIComponents.refreshHomePage(this.frame, this.panel, this.user, guiComponents);

                            Dialog dialog = new Dialog(this.frame);
                            dialog.showDialog(
                                    "SUCCESS",
                                    "Switch Account",
                                    "Switched to " + this.user.getUsername(),
                                    "All switched! You're logged in as " + this.user.getUsername(),
                                    false);

                            UserController.switchToAccount(this.user, ACCOUNTS_FILE);
                        });

                        gbc.insets = new Insets(5, 30, 5, 30);
                        gbc.weighty = 0;
                        accountsPanel.add(accountButton, gbc);
                    }
                    editButton.setVisible(false);
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
                button.setIcon(icon);
                switchAccountPanel.add(button, BorderLayout.CENTER);

                editButton.setVisible(true);
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

        ImageIcon genderIcon;
        if (this.user.getGender().equals("Male"))
            genderIcon = IconLoader.getBigMaleProfileIcon();
        else
            genderIcon = IconLoader.getBigFemaleProfileIcon();

        JLabel profileIcon = new JLabel();
        profileIcon.setIcon(genderIcon);

        float baseFontSize = 24f; // max size (shorter name)
        float minFontSize = 10f; // min size (longer name)
        float scaleFactor = 30f;

        float fontSize = baseFontSize + (scaleFactor / this.user.getFullName().length());
        fontSize = Math.max(fontSize, minFontSize);
        JLabel nameLabel = new JLabel(this.user.getFullName());
        nameLabel.setFont(CustomFonts.OPEN_SANS_EXTRA_BOLD.deriveFont(fontSize));
        nameLabel.setForeground(Theme.getForeground());

        JLabel usernameLabel = new JLabel("@" + this.user.getUsername());
        usernameLabel.setFont(CustomFonts.OPEN_SANS_BOLD.deriveFont(15f));
        usernameLabel.setForeground(Theme.getSecondaryForeground());
        if (this.user.getUserId() != 0) {
            if (this.user.getUserType().equals("Customer")) {
                usernameLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        invokeAdminPortalCounter++;
                        if (invokeAdminPortalCounter >= 7) {
                            Dialog dialog = new Dialog();
                            dialog.adminPortalDialog();
                            invokeAdminPortalCounter = 0;
                        }
                    }
                });
            }
        }

        editButton = createEditButton(IconLoader.getEditProfileIcon());

        gbc.insets = new Insets(25, 0, 0, 0);
        profileCard.add(profileIcon, gbc);
        gbc.insets = new Insets(15, 0, 0, 0);
        profileCard.add(nameLabel, gbc);
        gbc.insets = new Insets(5, 0, 15, 0);
        profileCard.add(usernameLabel, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        if (!this.user.getFullName().equals("Guest") && !this.user.getUsername().equals("guest"))
            profileCard.add(editButton, gbc);

        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        profileMainPanel.add(profileCard, gbc);
        return profileMainPanel;
    }

    private JButton createEditButton(ImageIcon icon) {
        editButton = new JButton("Edit Profile");
        editButton.setIcon(icon);
        editButton.setIconTextGap(15);
        editButton.setHorizontalTextPosition(SwingConstants.RIGHT);
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

    private JPanel createMenuCard(String text, ImageIcon icon) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.getBackground());

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
                button.setBackground(Theme.getHoverBackground());
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Theme.getBackground());
                button.repaint();
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                button.setBackground(Theme.getPressedBackground());
                button.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                button.setBackground(Theme.getBackground());
                button.repaint();
            }
        });

        if (text.equals("Theme") || text.equals("Dark Theme") || text.equals("Light Theme")) {
            button.addActionListener(e -> {
                String theme = UserController.loadTheme(THEME_FILE);
                String newTheme;

                if (theme.equals("Dark")) {
                    newTheme = "Light";
                    button.setIcon(IconLoader.getSunIcon());
                    button.setText("Light Theme");
                } else {
                    newTheme = "Dark";
                    button.setIcon(IconLoader.getMoonIcon());
                    button.setText("Dark Theme");
                }

                UserController.useTheme(newTheme, THEME_FILE);

                frame.getLayeredPane().remove(this);
                GUIComponents.refreshHomePage(this.frame, this.panel, this.user, guiComponents);
            });
        }
        if (text.equals("Sign Up")) {
            button.addActionListener(new Navigation().toSignUpPage(this.frame, this.panel, this, this.user));
        }
        if (text.equals("Sign In")) {
            button.addActionListener(new Navigation().toSignInPage(this.frame, this.panel, this, this.user));
        }
        if (text.equals("Sign Out")) {
            button.addActionListener(e -> {
                Dialog dialog = new Dialog(this.frame);
                boolean proceed = dialog.showDialog(
                        "HAZARD",
                        "Sign Out",
                        "Sign Out?",
                        "Logging out will end your session.",
                        true);

                if (proceed) {
                    frame.getLayeredPane().remove(this);
                    UserController.removeUserFromFile(this.user.getUserId(), ACCOUNTS_FILE);

                    this.user = new User();
                    GUIComponents.refreshHomePage(this.frame, this.panel, this.user, guiComponents);
                }
            });
        }
        if (text.equals("Delete Account")) {

            button.addActionListener(e -> {
                Dialog dialog = new Dialog(this.frame);
                boolean proceed = dialog.showDialog(
                        "HAZARD",
                        "Delete Account",
                        "Delete Your Account?",
                        "This action cannot be undone. Proceed with deletion?",
                        true);

                if (proceed) {
                    UserController.removeUserFromFile(this.user.getUserId(), ACCOUNTS_FILE);
                    UserController.removeUserFromDatabase(this.user);

                    this.user = new User();

                    frame.getLayeredPane().remove(this);
                    GUIComponents.refreshHomePage(this.frame, this.panel, this.user, guiComponents);
                }
            });
        }
        if (text.equals("Close / Exit")) {
            if (isExpanded)
                button.setVisible(false);
            button.addActionListener(e -> {
                Dialog dialog = new Dialog(this.frame);
                boolean proceed = dialog.showDialog(
                        "HAZARD",
                        "Exit",
                        "Close App?",
                        "Are you sure you want to exit?",
                        true);
                if (proceed)
                    this.frame.dispose();
            });
        }

        return button;
    }
}
