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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ForgotPasswordPage extends AuthenticationPage {

    private JFrame frame;
    private JPanel panel;
    private User user;
    private JPasswordField passwordInput;
    private JPasswordField confirmPasswordInput;
    private String currentPage = "USER";

    private JLabel passwordValidationLabel;
    private JLabel confirmPasswordValidationLabel;

    public ForgotPasswordPage() {
        this(new JFrame(), new JPanel(), new User());
    }

    public ForgotPasswordPage(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;
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
        wallpaperLabel.setIcon(ImageLoader.getForgotPasswordWallpaper());

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
        // title container to indicate forgot password
        JPanel titleContainer = new JPanel(new GridBagLayout());
        titleContainer.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Forgot Password");
        titleLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(60f));
        titleLabel.setForeground(Color.BLACK);

        titleContainer.add(titleLabel);

        return titleContainer;
    }

    @Override
    protected JPanel createInputContainer() {
        // create container that contains relevant input for forgot password
        JPanel InputContainer = new JPanel(new BorderLayout());

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 30, 20, 30);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;

        container.add(createEmailContainer("Email Address / Username: "), gbc);
        container.add(createPhoneContainer(), gbc);
        gbc.insets = new Insets(50, 20, 0, 0);
        container.add(createProceedContainer(container), gbc);

        InputContainer.add(container);

        return InputContainer;
    }

    private JPanel createPasswordInputContainer() {
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
        gbc.insets = new Insets(10, 0, 0, 0);

        JLabel passwordLabel = new JLabel("New Password: ");
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
        gbc.insets = new Insets(10, 0, 0, 0);

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

    private JPanel createProceedContainer(JPanel panel) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 15, 0, 500);

        container.add(createLinkButton(), gbc);
        gbc.insets = new Insets(50, 0, 0, 0);
        container.add(createNextButton(panel), gbc);
        return container;
    }

    private JButton createLinkButton() {
        JButton linkButton = new JButton("Back");
        linkButton.setBackground(Color.WHITE);
        linkButton.setForeground(Color.BLUE);
        linkButton.setFont(TITLE_FONT.deriveFont(20f));
        linkButton.setBorderPainted(false);
        linkButton.setFocusPainted(false);
        linkButton.setContentAreaFilled(false);
        linkButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        linkButton.addActionListener(e -> {
            GUIComponents.mainCardLayout.show(GUIComponents.mainCardPanel, "SignInPage");
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
            boolean isValidForgotPasswordDetails;
            if (currentPage.equals("USER")) {
                isValidForgotPasswordDetails = UserController.passForgotPasswordDetails(emailInput.getText(),
                        phoneInput.getText(), emailValidationLabel, phoneValidationLabel);
                if (isValidForgotPasswordDetails) {
                    currentPage = "PASSWORD";
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    panel.add(createPasswordInputContainer());
                }
            } else {
                isValidForgotPasswordDetails = UserController.passForgotPasswordDetails(emailInput.getText(),
                        phoneInput.getText(), passwordInput.getPassword(), confirmPasswordInput.getPassword(),
                        passwordValidationLabel, confirmPasswordValidationLabel);
                if (isValidForgotPasswordDetails) {
                    currentPage = "USER";
                    // GUIComponents.refreshPages(frame, panel, user, OverflowMenu.getGuiComponents());
                    // MainApp.getGuiComponents().setVisible(true);
                    // GUIComponents.topBarPanel.setVisible(true);
                    GUIComponents.mainCardLayout.show(GUIComponents.mainCardPanel, "SignInPage");
                    GUIComponents.signInPanel.revalidate();
                    GUIComponents.signInPanel.repaint();
                    GUIComponents.mainCardPanel.revalidate();
                    GUIComponents.mainCardPanel.repaint();



                    Dialog dialog = new Dialog(this.frame);
                    dialog.showDialog(
                            "SUCCESS",
                            "Password Updated",
                            "Password Changed Successfully",
                            "You can now use your new password to log in.",
                            false);
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

        String[] texts = { "Drive in Style,", "Rent with Ease." };
        for (String text : texts) {
            JLabel lines = new JLabel(text);
            if (text.equals(texts[0]))
                lines.setForeground(Color.BLACK);
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
