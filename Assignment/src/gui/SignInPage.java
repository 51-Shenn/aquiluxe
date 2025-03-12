package gui;

import controllers.UserController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class SignInPage extends AuthenticationPage {

    private final JFrame frame;
    private final JPanel panel;
    private JPasswordField passwordInput;
    private JLabel passwordValidationLabel;

    public SignInPage(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;
    }

    @Override
    protected JLabel createWallpaperLabel() {
        JLabel wallpaperLabel = new JLabel();
        wallpaperLabel.setOpaque(false);
        File imageFile = new File("images/wallpapers/car-wallpaper-3.png");
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
        // title container to indicate sign in
        JPanel titleContainer = new JPanel(new GridBagLayout());
        titleContainer.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sign In");
        titleLabel.setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(60f));
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
                this.frame.setLayout(new BorderLayout());
                this.frame.add(new GUIComponents(this.frame, this.panel), BorderLayout.NORTH);
                this.frame.add(this.panel, BorderLayout.CENTER);
                this.panel.removeAll();
                this.panel.add(new VehiclesPage(this.frame, this.panel), BorderLayout.CENTER);
                this.frame.revalidate();
                this.frame.repaint();
            });
        }

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

        container.add(createEmailContainer("Username / Email Address: "), gbc);
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
            this.frame.setContentPane(new ForgotPasswordPage(this.frame, this.panel));
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
            this.frame.setContentPane(new SignUpPage(this.frame, this.panel));
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
            boolean isValidSignInDetails = UserController.passSignInDetails(emailInput.getText(), passwordInput.getPassword(), emailValidationLabel, passwordValidationLabel);
            if(isValidSignInDetails) {
                JPanel newContentPane = new JPanel(new BorderLayout());
                this.frame.setContentPane(newContentPane);
                this.frame.add(new GUIComponents(this.frame, this.panel), BorderLayout.NORTH);
                this.frame.revalidate();
                this.frame.repaint();
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

        String[] texts = {"Your Journey,", "Our Wheels."};
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
