package gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInPage extends LoginPage {

    private JFrame frame;

    SignInPage(JFrame frame) {
        this.frame = frame;
    }

    @Override
    protected JLabel createWallpaperLabel() {
        JLabel wallpaperLabel = new JLabel();
        wallpaperLabel.setOpaque(false);
        ImageIcon backgroundImage = new ImageIcon("images/icons/carBackground3.png");
        Image image = backgroundImage.getImage().getScaledInstance(960, 1080, Image.SCALE_SMOOTH);

        wallpaperLabel.setLayout(new GridBagLayout());
        wallpaperLabel.setIcon(new ImageIcon(image));

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
        // title container to indicate sign in or sign up
        JPanel titleContainer = new JPanel();
        titleContainer.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sign In");
        titleLabel.setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(60f));
        titleLabel.setForeground(Color.BLACK);
        titleContainer.add(titleLabel);

        return titleContainer;
    }

    @Override
    protected JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = contentPanel.getWidth();
        gbc.insets = new Insets(80, 0, 0, 0);
        gbc.anchor = GridBagConstraints.NORTH;

        contentPanel.add(createTitle(), gbc);
        gbc.insets = new Insets(0, 0, 300, 0);
        gbc.gridy = 1;
        gbc.weighty = 1;
        contentPanel.add(createInputContainer(), gbc);
        return contentPanel;
    }

    @Override
    protected JPanel createInputContainer() {
        // create container that contains relevant info for sign in or sign up
        JPanel InputContainer = new JPanel(new BorderLayout());

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 20, 30);
        gbc.gridwidth = container.getWidth();

        gbc.anchor = GridBagConstraints.WEST;

        container.add(createEmailContainer(), gbc);
        container.add(createPasswordContainer(), gbc);
        gbc.insets = new Insets(30, 20, 0, 0);
        container.add(createProceedContainer(), gbc);

        InputContainer.add(container);

        return InputContainer;
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
        emailLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        emailLabel.setForeground(Color.BLACK);

        JTextField emailInput = new JTextField();
        emailInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        emailInput.setPreferredSize(new Dimension(700, HEIGHT));
        emailInput.setMinimumSize(new Dimension(700, HEIGHT));
        emailInput.setForeground(Color.BLACK);
        emailInput.setBorder(new CompoundBorder(BORDER, PADDING));

        emailPanel.add(emailLabel, gbc);
        emailPanel.add(emailInput);

        return emailPanel;
    }

    private JPanel createPasswordContainer() {
        // password
        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = passwordPanel.getWidth();
        gbc.insets = new Insets(10, 0, 0, 0);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        passwordLabel.setForeground(Color.BLACK);

        JTextField passwordInput = new JTextField();
        passwordInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        passwordInput.setPreferredSize(new Dimension(700, HEIGHT));
        passwordInput.setMinimumSize(new Dimension(700, HEIGHT));
        passwordInput.setForeground(Color.BLACK);
        passwordInput.setBorder(new CompoundBorder(BORDER, PADDING));

        JButton forgotPasswordLink = new JButton("Forgot Password?");
        forgotPasswordLink.setForeground(Color.BLUE);
        forgotPasswordLink.setFont(TITLE_FONT.deriveFont(17f));
        forgotPasswordLink.setBorderPainted(false);
        forgotPasswordLink.setFocusPainted(false);
        forgotPasswordLink.setContentAreaFilled(false);
        forgotPasswordLink.setBorder(new EmptyBorder(0, 0, 0, 0));

        passwordPanel.add(passwordLabel, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        passwordPanel.add(passwordInput, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        passwordPanel.add(forgotPasswordLink, gbc);

        return passwordPanel;
    }

    private JPanel createProceedContainer() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 10, 0, 340);

        JButton linkButton = new JButton("Don't have an account yet?");
        linkButton.setBackground(Color.WHITE);
        linkButton.setForeground(Color.BLUE);
        linkButton.setFont(TITLE_FONT.deriveFont(17f));
        linkButton.setBorderPainted(false);
        linkButton.setFocusPainted(false);
        linkButton.setContentAreaFilled(false);
        linkButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        linkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new SignUpPage(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(TITLE_FONT.deriveFont(BUTTON_TEXT_SIZE));
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setPreferredSize(new Dimension(150, 70));
        nextButton.setMinimumSize(new Dimension(150, HEIGHT));

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
