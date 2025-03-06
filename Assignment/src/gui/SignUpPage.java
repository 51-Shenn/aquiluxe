package gui;

import controllers.UserController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class SignUpPage extends LoginPage {

    private final JFrame frame;
    private JTextField fullNameInput;
    private String genderInput;
    private JTextField phoneInput;
    private JPasswordField passwordInput = new JPasswordField();
    private JPasswordField confirmPasswordInput = new JPasswordField();

    SignUpPage(JFrame frame) {
        this.frame = frame;
    }

    @Override
    protected JLabel createWallpaperLabel() {
        JLabel wallpaperLabel = new JLabel();
        wallpaperLabel.setOpaque(false);
        File imageFile = new File("images/icons/carBackground2.png");
        if (!imageFile.exists()) {
            JOptionPane.showMessageDialog(null, "Failed to load image");
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
        // title container to indicate sign up
        JPanel titleContainer = new JPanel();
        titleContainer.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(60f));
        titleLabel.setForeground(Color.BLACK);
        titleContainer.add(titleLabel);

        return titleContainer;
    }

    @Override
    protected JPanel createContentPanel() {
        // content panel that contains title and the required input for sign up
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
        gbc.insets = new Insets(0, 0, 200, 0);
        gbc.gridy = 1;
        gbc.weighty = 1;
        contentPanel.add(createInputContainer(), gbc);
        return contentPanel;
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

        gbc.insets = new Insets(0, 30, 30, 30);
        gbc.gridwidth = container.getWidth();
        container.add(fullNameAndGenderPanel, gbc);

        gbc.anchor = GridBagConstraints.WEST;

        container.add(createEmailContainer(), gbc);
        container.add(createPhoneContainer(), gbc);
        container.add(createProceedContainer(container), gbc);

        InputContainer.add(container);

        return InputContainer;
    }

    private JPanel createFullNameContainer() {
        // full name
        JPanel fullNamePanel = new JPanel(new BorderLayout());
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

        fullNamePanel.add(fullNameLabel, BorderLayout.NORTH);
        fullNamePanel.add(fullNameInput, BorderLayout.CENTER);

        return fullNamePanel;
    }

    private JPanel createGenderContainer() {
        // gender
        JPanel genderPanel = new JPanel(new BorderLayout());
        genderPanel.setBackground(Color.WHITE);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        genderLabel.setForeground(Color.BLACK);

        JPanel genderButtonsPanel = new JPanel(new BorderLayout());
        genderButtonsPanel.setBorder(new LineBorder(Color.BLACK, 1));

        String[] genderButtonsText = {"M", "F"};
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
                }
                else {
                    genderLabel.setText("Gender: (Female)");
                    genderInput = "Female";
                }
            });
            
            if (genderButtonsText[i].equals("M"))
                genderButtonsPanel.add(genderButton, BorderLayout.WEST);
            else
                genderButtonsPanel.add(genderButton, BorderLayout.EAST);
        }


        // add both components inside gender panel
        genderPanel.add(genderLabel, BorderLayout.NORTH);
        genderPanel.add(genderButtonsPanel, BorderLayout.CENTER);

        return genderPanel;
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
        phoneLabel.setFont(TITLE_FONT.deriveFont(TITLE_TEXT_SIZE));
        phoneLabel.setForeground(Color.BLACK);

        JPanel phoneInputPanel = new JPanel(new GridBagLayout());
        JButton countryCode = new JButton();
        countryCode.setText("+60");
        countryCode.setFont(TITLE_FONT.deriveFont(BUTTON_TEXT_SIZE));
        countryCode.setPreferredSize(new Dimension(100, HEIGHT));
        countryCode.setMinimumSize(new Dimension(100, HEIGHT));
        countryCode.setFocusPainted(false);
        countryCode.setBackground(Color.BLACK);
        countryCode.setForeground(Color.WHITE);
        countryCode.setBorder(BORDER);

        phoneInput = new JTextField();
        phoneInput.setFont(INPUT_FONT.deriveFont(NORMAL_TEXT_SIZE));
        phoneInput.setPreferredSize(new Dimension(600, HEIGHT));
        phoneInput.setMinimumSize(new Dimension(600, HEIGHT));
        phoneInput.setForeground(Color.BLACK);
        phoneInput.setBorder(new CompoundBorder(BORDER, PADDING));

        phoneInputPanel.add(countryCode);
        phoneInputPanel.add(phoneInput);

        phonePanel.add(phoneLabel, gbc);
        phonePanel.add(phoneInputPanel);

        return phonePanel;
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
            frame.setContentPane(new SignInPage(frame));
            frame.revalidate();
            frame.repaint();
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
            boolean isValidUserDetails = UserController.passNewUserDetails(fullNameInput.getText(), genderInput, emailInput.getText(), phoneInput.getText(), passwordInput.getPassword(), confirmPasswordInput.getPassword());
            if(isValidUserDetails) {
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                panel.add(createPasswordInputContainer());
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

        String[] texts = {"Rent a Car,", "Drive with Ease!"};
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

    // these methods will be called after validate the first phase of the sign up page
    private JPanel createPasswordInputContainer() {
        // create container that contains relevant info for sign in or sign up
        JPanel InputContainer = new JPanel(new GridBagLayout());

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 50, 0);
        gbc.gridwidth = container.getWidth();

        gbc.anchor = GridBagConstraints.WEST;

        container.add(createPasswordContainer(), gbc);
        container.add(createConfirmPasswordContainer(), gbc);
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
}
