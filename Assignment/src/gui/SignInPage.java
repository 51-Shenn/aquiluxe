package gui;

import controllers.UserController;
import datamodels.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SignInPage extends AuthenticationPage {

    private JFrame frame;
    private JPanel panel;
    private User user;
    private JPasswordField passwordInput;
    private JLabel passwordValidationLabel;
    private final File ACCOUNTS_FILE = new File("files/settings/accounts.txt");

    public SignInPage() {
        this(new JFrame(), new JPanel(), new User());
    }

    public SignInPage(JFrame frame, JPanel panel, User user) {
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

    public JPasswordField getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(JPasswordField passwordInput) {
        this.passwordInput = passwordInput;
    }

    public JLabel getPasswordValidationLabel() {
        return passwordValidationLabel;
    }

    public void setPasswordValidationLabel(JLabel passwordValidationLabel) {
        this.passwordValidationLabel = passwordValidationLabel;
    }

    public File getACCOUNTS_FILE() {
        return ACCOUNTS_FILE;
    }

    @Override
    protected JLabel createWallpaperLabel() {
        JLabel wallpaperLabel = new JLabel();
        wallpaperLabel.setOpaque(false);
        wallpaperLabel.setIcon(ImageLoader.getSignInWallpaper());

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

        JLabel titleLabel = new JLabel("Sign In");
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
            Navigation.setWindowsLookAndFeel();
            MainApp.getGuiComponents().setVisible(true);
            GUIComponents.topBarPanel.setVisible(true);
            GUIComponents.mainCardLayout.show(GUIComponents.mainCardPanel, "MainPage");
            GUIComponents.subCardLayout.show(GUIComponents.subCardPanel, "HomePage");
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
        // create container that contains relevant input for sign in
        JPanel InputContainer = new JPanel(new BorderLayout());

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 30, 20, 30);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        gbc.anchor = GridBagConstraints.WEST;

        container.add(createEmailContainer("Email Address / Username: "), gbc);
        container.add(createPasswordContainer(), gbc);
        gbc.insets = new Insets(50, 20, 0, 0);
        container.add(createProceedContainer(), gbc);

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

        passwordValidationLabel = new JLabel("");
        passwordValidationLabel.setForeground(Color.RED);
        passwordValidationLabel.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));

        passwordPanel.add(passwordLabel, gbc);
        passwordPanel.add(passwordInput);
        passwordPanel.add(createEyeButton(passwordInput), gbc);
        gbc.insets = new Insets(5, 0, 0, 0);
        passwordPanel.add(passwordValidationLabel, gbc);
        gbc.insets = new Insets(10, 0, 0, 0);
        passwordPanel.add(createForgotPasswordLink(), gbc);

        return passwordPanel;
    }

    private JButton createForgotPasswordLink() {
        JButton forgotPasswordLink = new JButton("Forgot Password?");
        forgotPasswordLink.setForeground(Color.BLUE);
        forgotPasswordLink.setFont(TITLE_FONT.deriveFont(17f));
        forgotPasswordLink.setBorderPainted(false);
        forgotPasswordLink.setFocusPainted(false);
        forgotPasswordLink.setContentAreaFilled(false);
        forgotPasswordLink.setBorder(new EmptyBorder(0, 0, 0, 0));
        forgotPasswordLink.addActionListener(e -> {
            GUIComponents.mainCardLayout.show(GUIComponents.mainCardPanel, "ForgotPasswordPage");
            this.frame.revalidate();
            this.frame.repaint();
        });
        return forgotPasswordLink;
    }

    private JPanel createProceedContainer() {
        // 2 buttons that let user change to sign up page or continue
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 10, 0, 350);

        container.add(createLinkButton(), gbc);
        gbc.insets = new Insets(50, 0, 0, 0);
        container.add(createNextButton(), gbc);
        return container;
    }

    private JButton createLinkButton() {
        JButton linkButton = new JButton("Don't have an account yet?");
        linkButton.setBackground(Color.WHITE);
        linkButton.setForeground(Color.BLUE);
        linkButton.setFont(TITLE_FONT.deriveFont(17f));
        linkButton.setBorderPainted(false);
        linkButton.setFocusPainted(false);
        linkButton.setContentAreaFilled(false);
        linkButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        linkButton.addActionListener(e -> {
            GUIComponents.mainCardLayout.show(GUIComponents.mainCardPanel, "SignUpPage");
            this.frame.revalidate();
            this.frame.repaint();
        });

        return linkButton;
    }

    private JButton createNextButton() {
        JButton nextButton = new JButton("Next");
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(TITLE_FONT.deriveFont(BUTTON_TEXT_SIZE));
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setPreferredSize(new Dimension(150, 70));
        nextButton.setMinimumSize(new Dimension(150, HEIGHT));
        nextButton.addActionListener(e -> {
            boolean isValidSignInDetails = UserController.passSignInDetails(emailInput.getText(),
                    passwordInput.getPassword(), emailValidationLabel, passwordValidationLabel);
            if (isValidSignInDetails) {
                this.user = UserController.passSignInDetails(emailInput.getText(), passwordInput.getPassword());

                UserController.switchToAccount(this.user, ACCOUNTS_FILE);

                GUIComponents.refreshPages(frame, panel, user, OverflowMenu.getGuiComponents());
                MainApp.getGuiComponents().setVisible(true);
                GUIComponents.topBarPanel.setVisible(true);
                GUIComponents.mainCardLayout.show(GUIComponents.mainCardPanel, "MainPage");
                GUIComponents.subCardLayout.show(GUIComponents.subCardPanel, "HomePage");

                Dialog dialog = new Dialog(this.frame);
                dialog.showDialog(
                        "SUCCESS",
                        "Welcome",
                        "Login Successful",
                        "You're in! " + this.user.getUsername(),
                        false);
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

        String[] texts = { "Your Journey,", "Our Wheels." };
        for (String text : texts) {
            JLabel lines = new JLabel(text);
            if (text.equals(texts[0]))
                lines.setForeground(Color.WHITE);
            else
                lines.setForeground(Color.BLUE);
            lines.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(70f));

            taglineContainer.add(lines, gbc);
        }
        gbc.anchor = GridBagConstraints.EAST;
        taglineContainer.add(logo, gbc);

        return taglineContainer;
    }
}
