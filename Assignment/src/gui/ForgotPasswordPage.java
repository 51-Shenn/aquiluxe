package gui;

import controllers.UserController;
import datamodels.User;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class ForgotPasswordPage extends AuthenticationPage {

    private final JFrame frame;
    private final JPanel panel;
    private User user;
    private JPasswordField passwordInput;
    private JPasswordField confirmPasswordInput;
    private String currentPage = "USER";

    private JLabel passwordValidationLabel;
    private JLabel confirmPasswordValidationLabel;

    public ForgotPasswordPage(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;
    }

    @Override
    protected JLabel createWallpaperLabel() {
        JLabel wallpaperLabel = new JLabel();
        wallpaperLabel.setOpaque(false);
        File imageFile = new File("images/wallpapers/car-wallpaper-1.png");
        if (!imageFile.exists()) {
            JOptionPane.showMessageDialog(null, "Failed to load image: " + imageFile);
        } else {
            ImageIcon backgroundImage = new ImageIcon(imageFile.toString());
            Image image = backgroundImage.getImage().getScaledInstance(960, 1080, Image.SCALE_SMOOTH);
            wallpaperLabel.setIcon(new ImageIcon(image));
        }

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
        titleLabel.setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(55f));
        titleLabel.setForeground(Color.BLACK);

        JButton closeButton = new JButton();
        File closeImage = new File("images/icons/close.png");
        if (!closeImage.exists()) {
            JOptionPane.showMessageDialog(null, "Failed to load image:\n" + closeImage + "\n");
        } else {
            ImageIcon closeIcon = new ImageIcon(closeImage.toString());
            closeButton.setPreferredSize(new Dimension(50, 50));
            closeButton.setBackground(Color.WHITE);
            closeButton.setIcon(closeIcon);
            closeButton.setBorderPainted(false);
            closeButton.setContentAreaFilled(false);
            closeButton.setFocusPainted(false);
            closeButton.addActionListener(e -> {
                this.frame.getContentPane().removeAll();
                this.frame.add(new GUIComponents(this.frame, this.panel, this.user), BorderLayout.NORTH);
                this.frame.add(this.panel, BorderLayout.CENTER);
                this.panel.removeAll();
                this.panel.add(new VehiclesPage(this.frame, this.panel), BorderLayout.CENTER);
                this.frame.revalidate();
                this.frame.repaint();
            });
        }

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

        container.add(createEmailContainer("Username / Email Address: "), gbc);
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
            boolean isValidForgotPasswordDetails;
            if (currentPage.equals("USER")) {
                isValidForgotPasswordDetails = UserController.passForgotPasswordDetails(emailInput.getText(), phoneInput.getText(), emailValidationLabel, phoneValidationLabel);
                if(isValidForgotPasswordDetails) {
                    currentPage = "PASSWORD";
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    panel.add(createPasswordInputContainer());
                }
            }
            else {
                isValidForgotPasswordDetails = UserController.passForgotPasswordDetails(emailInput.getText(), phoneInput.getText(), passwordInput.getPassword(), confirmPasswordInput.getPassword(), passwordValidationLabel, confirmPasswordValidationLabel);
                if(isValidForgotPasswordDetails) {
                    currentPage = "USER";
                    JPanel newContentPane = new JPanel(new BorderLayout());
                    this.frame.setContentPane(newContentPane);
                    this.frame.add(new GUIComponents(this.frame, this.panel, this.user), BorderLayout.NORTH);
                    this.frame.validate();
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

        String[] texts = {"Drive in Style,", "Rent with Ease."};
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
