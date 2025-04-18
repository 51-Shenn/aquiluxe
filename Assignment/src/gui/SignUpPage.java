package gui;

import controllers.UserController;
import datamodels.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SignUpPage extends AuthenticationPage {

    private JFrame frame;
    private JPanel panel;
    private User user;
    private JTextField fullNameInput;
    private String genderInput;
    private JPasswordField passwordInput;
    private JPasswordField confirmPasswordInput;
    private String currentPage = "USER";

    private JLabel fullNameValidationLabel;
    private JLabel genderValidationLabel;
    private JLabel passwordValidationLabel;
    private JLabel confirmPasswordValidationLabel;

    public SignUpPage() {
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.user = new User();
    }

    public SignUpPage(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;

        Navigation.removeWindowsLookAndFeel();
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

    public JTextField getFullNameInput() {
        return fullNameInput;
    }

    public void setFullNameInput(JTextField fullNameInput) {
        this.fullNameInput = fullNameInput;
    }

    public String getGenderInput() {
        return genderInput;
    }

    public void setGenderInput(String genderInput) {
        this.genderInput = genderInput;
    }

    public JPasswordField getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(JPasswordField passwordInput) {
        this.passwordInput = passwordInput;
    }

    public JPasswordField getConfirmPasswordInput() {
        return confirmPasswordInput;
    }

    public void setConfirmPasswordInput(JPasswordField confirmPasswordInput) {
        this.confirmPasswordInput = confirmPasswordInput;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public JLabel getFullNameValidationLabel() {
        return fullNameValidationLabel;
    }

    public void setFullNameValidationLabel(JLabel fullNameValidationLabel) {
        this.fullNameValidationLabel = fullNameValidationLabel;
    }

    public JLabel getGenderValidationLabel() {
        return genderValidationLabel;
    }

    public void setGenderValidationLabel(JLabel genderValidationLabel) {
        this.genderValidationLabel = genderValidationLabel;
    }

    public JLabel getPasswordValidationLabel() {
        return passwordValidationLabel;
    }

    public void setPasswordValidationLabel(JLabel passwordValidationLabel) {
        this.passwordValidationLabel = passwordValidationLabel;
    }

    public JLabel getConfirmPasswordValidationLabel() {
        return confirmPasswordValidationLabel;
    }

    public void setConfirmPasswordValidationLabel(JLabel confirmPasswordValidationLabel) {
        this.confirmPasswordValidationLabel = confirmPasswordValidationLabel;
    }

    @Override
    protected JLabel createWallpaperLabel() {
        JLabel wallpaperLabel = new JLabel();
        wallpaperLabel.setOpaque(false);
        wallpaperLabel.setIcon(ImageLoader.getSignUpWallpaper());

        wallpaperLabel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(70, 150, 0, 0);
        gbc.weighty = 1;

        wallpaperLabel.add(createTaglineContainer(), gbc);
        return wallpaperLabel;
    }

    @Override
    protected JPanel createTitle() {
        // title container to indicate sign in
        JPanel titleContainer = new JPanel(new GridBagLayout());
        titleContainer.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(60f));
        titleLabel.setForeground(Color.BLACK);

        JButton closeButton = new JButton();
        closeButton.setPreferredSize(new Dimension(50, 50));
        closeButton.setBackground(Color.WHITE);
        closeButton.setIcon(IconLoader.getXIcon());
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            new Navigation().homePageNavigation(this.frame, this.panel, this.user);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 250, 0, 0);
        titleContainer.add(titleLabel, gbc);
        gbc.insets = new Insets(0, 200, 0, 0);
        titleContainer.add(closeButton, gbc);

        return titleContainer;
    }

    @Override
    protected JPanel createInputContainer() {
        // create container that contains relevant input for sign up
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

        gbc.insets = new Insets(0, 30, 20, 30);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        container.add(fullNameAndGenderPanel, gbc);

        gbc.anchor = GridBagConstraints.WEST;

        container.add(createEmailContainer("Email Address: "), gbc);
        container.add(createPhoneContainer(), gbc);
        gbc.insets = new Insets(40, 30, 0, 30);
        container.add(createProceedContainer(container), gbc);

        InputContainer.add(container);

        return InputContainer;
    }

    private JPanel createFullNameContainer() {
        // full name
        JPanel fullNamePanel = new JPanel(new GridBagLayout());
        fullNamePanel.setBackground(Color.WHITE);

        JLabel fullNameLabel = new JLabel("Full Name: ");
        fullNameLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        fullNameLabel.setForeground(Color.BLACK);

        fullNameInput = new JTextField();
        fullNameInput.setPreferredSize(new Dimension(440, HEIGHT));
        fullNameInput.setMinimumSize(new Dimension(440, HEIGHT));
        fullNameInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        fullNameInput.setForeground(Color.BLACK);
        fullNameInput.setBorder(new CompoundBorder(BORDER, PADDING));

        fullNameValidationLabel = new JLabel("");
        fullNameValidationLabel.setForeground(Color.RED);
        fullNameValidationLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 0, 0);

        fullNamePanel.add(fullNameLabel, gbc);
        fullNamePanel.add(fullNameInput, gbc);
        fullNamePanel.add(fullNameValidationLabel, gbc);

        return fullNamePanel;
    }

    private JPanel createGenderContainer() {
        // gender
        JPanel genderPanel = new JPanel(new GridBagLayout());
        genderPanel.setBackground(Color.WHITE);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        genderLabel.setForeground(Color.BLACK);

        JPanel genderButtonsPanel = new JPanel(new BorderLayout());
        genderButtonsPanel.setBorder(new LineBorder(Color.BLACK, 1));

        String[] genderButtonsText = { "M", "F" };
        JButton[] genderJButtons = new JButton[2];
        for (int i = 0; i < genderButtonsText.length; i++) {
            JButton genderButton = new JButton();
            genderJButtons[i] = genderButton;
            genderButton.setText(genderButtonsText[i]);
            genderButton.setFont(TITLE_FONT.deriveFont(BUTTON_TEXT_SIZE));
            genderButton.setPreferredSize(new Dimension(100, HEIGHT));
            genderButton.setMinimumSize(new Dimension(100, HEIGHT));
            genderButton.setBorder(new LineBorder(Color.BLACK, 1));
            genderButton.setFocusPainted(false);
            genderButton.setBackground(Color.WHITE);
            genderButton.setForeground(Color.BLACK);
            genderButton.addActionListener(e -> {
                for (JButton button : genderJButtons) {
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                }

                genderButton.setBackground(Color.BLUE);
                genderButton.setForeground(Color.WHITE);

                if (genderButton.getText().equals("M")) {
                    genderLabel.setText("Gender: (Male)");
                    genderInput = "Male";
                } else {
                    genderLabel.setText("Gender: (Female)");
                    genderInput = "Female";
                }
            });

            if (genderButtonsText[i].equals("M"))
                genderButtonsPanel.add(genderButton, BorderLayout.WEST);
            else
                genderButtonsPanel.add(genderButton, BorderLayout.EAST);
        }

        genderValidationLabel = new JLabel("");
        genderValidationLabel.setForeground(Color.RED);
        genderValidationLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 0, 0);
        // add both components inside gender panel
        genderPanel.add(genderLabel, gbc);
        genderPanel.add(genderButtonsPanel, gbc);
        genderPanel.add(genderValidationLabel, gbc);

        return genderPanel;
    }

    private JPanel createProceedContainer(JPanel panel) {
        // 2 buttons that let user change to sign in page or continue
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 0, 0, 350);

        container.add(createLinkButton(), gbc);
        gbc.insets = new Insets(50, 0, 0, 0);
        container.add(createNextButton(panel), gbc);
        return container;
    }

    private JButton createLinkButton() {
        JButton linkButton = new JButton("Already have an account?");
        linkButton.setBackground(Color.WHITE);
        linkButton.setForeground(Color.BLUE);
        linkButton.setFont(TITLE_FONT.deriveFont(17f));
        linkButton.setBorderPainted(false);
        linkButton.setFocusPainted(false);
        linkButton.setContentAreaFilled(false);
        linkButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        linkButton.setHorizontalAlignment(SwingConstants.LEFT);
        linkButton.addActionListener(e -> {
            this.frame.setContentPane(new SignInPage(this.frame, this.panel, this.user));
            this.frame.revalidate();
            this.frame.repaint();
        });

        return linkButton;
    }

    private JButton createNextButton(JPanel panel) {
        JButton nextButton = new JButton("Next");
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(TITLE_FONT.deriveFont(BUTTON_TEXT_SIZE));
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setPreferredSize(new Dimension(150, 70));
        nextButton.setMinimumSize(new Dimension(150, HEIGHT));
        nextButton.addActionListener(e -> {
            boolean isValidUserDetails;
            if (currentPage.equals("USER")) {
                isValidUserDetails = UserController.passNewUserDetails(fullNameInput.getText(), genderInput,
                        emailInput.getText(), phoneInput.getText(), fullNameValidationLabel, genderValidationLabel,
                        emailValidationLabel, phoneValidationLabel);
                if (isValidUserDetails) {
                    currentPage = "PASSWORD";
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    panel.add(createPasswordInputContainer());
                }
            } else {
                isValidUserDetails = UserController.passNewUserDetails(fullNameInput.getText(), genderInput,
                        emailInput.getText(), phoneInput.getText(), passwordInput.getPassword(),
                        confirmPasswordInput.getPassword(), fullNameValidationLabel, genderValidationLabel,
                        emailValidationLabel, phoneValidationLabel, passwordValidationLabel,
                        confirmPasswordValidationLabel);
                if (isValidUserDetails) {
                    currentPage = "USER";
                    this.frame.setContentPane(new SignInPage(this.frame, this.panel, this.user));
                    this.frame.validate();

                    Dialog dialog = new Dialog(this.frame);
                    dialog.showDialog(
                        "SUCCESS",
                        "Sign Up",
                        "Account Created Successfully",
                        "Awesome! Your account is ready — let's get you signed in!",
                        false
                    );
                }
            }
        });

        return nextButton;
    }

    private JPanel createTaglineContainer() {
        // contains description or quote that will display above the wallpaper
        JPanel taglineContainer = new JPanel(new GridBagLayout());
        taglineContainer.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHEAST;

        String logoText = "- AQUILUXE";
        JLabel logo = new JLabel(logoText);
        logo.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(30f));
        logo.setForeground(Color.DARK_GRAY);

        String[] texts = { "Rent a Car,", "Drive with Ease!" };
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

    // these methods will be called after validate the first phase of the sign up
    // page
    private JPanel createPasswordInputContainer() {
        // create container that contains relevant info for sign in or sign up
        JPanel InputContainer = new JPanel(new GridBagLayout());

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);

        passwordValidationLabel = new JLabel("");
        passwordValidationLabel.setForeground(Color.RED);
        passwordValidationLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));

        confirmPasswordValidationLabel = new JLabel("");
        confirmPasswordValidationLabel.setForeground(Color.RED);
        confirmPasswordValidationLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = container.getWidth();
        gbc.anchor = GridBagConstraints.WEST;

        container.add(createPasswordContainer(), gbc);
        gbc.insets = new Insets(5, 0, 50, 0);
        container.add(passwordValidationLabel, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        container.add(createConfirmPasswordContainer(), gbc);
        gbc.insets = new Insets(5, 0, 50, 0);
        container.add(confirmPasswordValidationLabel, gbc);
        gbc.insets = new Insets(30, 0, 0, 0);
        container.add(createProceedContainer(container), gbc);

        InputContainer.add(container);

        return InputContainer;
    }

    private JPanel createPasswordContainer() {
        // password
        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = passwordPanel.getWidth();
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        passwordLabel.setForeground(Color.BLACK);

        passwordInput = new JPasswordField();
        passwordInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        passwordInput.setPreferredSize(new Dimension(600, HEIGHT));
        passwordInput.setMinimumSize(new Dimension(600, HEIGHT));
        passwordInput.setForeground(Color.BLACK);
        passwordInput.setBorder(new CompoundBorder(BORDER, PADDING));

        passwordPanel.add(passwordLabel, gbc);
        passwordPanel.add(passwordInput);
        passwordPanel.add(createEyeButton(passwordInput));

        return passwordPanel;
    }

    private JPanel createConfirmPasswordContainer() {
        // password
        JPanel confirmPasswordPanel = new JPanel(new GridBagLayout());
        confirmPasswordPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = confirmPasswordPanel.getWidth();
        gbc.insets = new Insets(5, 0, 0, 0);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password: ");
        confirmPasswordLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        confirmPasswordLabel.setForeground(Color.BLACK);

        confirmPasswordInput = new JPasswordField();
        confirmPasswordInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        confirmPasswordInput.setPreferredSize(new Dimension(600, HEIGHT));
        confirmPasswordInput.setMinimumSize(new Dimension(600, HEIGHT));
        confirmPasswordInput.setForeground(Color.BLACK);
        confirmPasswordInput.setBorder(new CompoundBorder(BORDER, PADDING));

        confirmPasswordPanel.add(confirmPasswordLabel, gbc);
        confirmPasswordPanel.add(confirmPasswordInput);
        confirmPasswordPanel.add(createEyeButton(confirmPasswordInput));

        return confirmPasswordPanel;
    }
}
