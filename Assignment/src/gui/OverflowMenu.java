package gui;

import datamodels.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class OverflowMenu extends JLayeredPane {

    private final JFrame frame;
    private final JPanel panel;
    private User user;
    public final int MENU_WIDTH;
    public final int MENU_HEIGHT;

    public OverflowMenu(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;
        MENU_WIDTH = 400;
        MENU_HEIGHT = 600;
        add(createOverflowMenu(), JLayeredPane.POPUP_LAYER);
    }

    private JPanel createOverflowMenu() {
        JPanel container = new JPanel();
        container.setLayout(null);
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
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(1, 0, 0, 0);

        panel.add(createProfileCard(), gbc);

        if (this.user.getFullName().equals("Guest") && this.user.getUsername().equals("guest")) {
            panel.add(createMenuCard("Sign Up", signInFilePath), gbc);
            panel.add(createMenuCard("Sign In", signInFilePath), gbc);

        }
        else {
            panel.add(createMenuCard("Theme", sunMoonFilePath), gbc);
            panel.add(createMenuCard("Switch Account", switchFilePath), gbc);
            panel.add(createMenuCard("Sign Out", signOutFilePath), gbc);
        }

        panel.add(createMenuCard("Close / Exit", roundCloseFilePath), gbc);

        return panel;
    }

    private JPanel createProfileCard() {
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

        gbc.insets = new Insets(20, 0, 0, 0);
        profileCard.add(profileIcon, gbc);
        gbc.insets = new Insets(5, 0, 0, 0);
        profileCard.add(nameLabel, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        profileCard.add(usernameLabel, gbc);

        if(!this.user.getFullName().equals("Guest") && !this.user.getUsername().equals("guest"))
            profileCard.add(editButton, gbc);

        return profileCard;
    }

    private JButton createEditButton(File editFilePath) {
        ImageIcon editIcon = new ImageIcon(editFilePath.toString());
        JButton editButton = new JButton("Edit Profile");
        editButton.setIcon(editIcon);
        editButton.setIconTextGap(15);
        editButton.setHorizontalTextPosition(SwingConstants.LEFT);
        editButton.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(18f));
        editButton.setBackground(Color.WHITE);
        editButton.setForeground(Color.BLACK);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);

        return editButton;
    }

    private JPanel createMenuCard(String text, File iconFilePath) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon(iconFilePath.toString());

        JPanel button = createMenuButton(text, icon);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;

        card.add(button, gbc);
        return card;
    }

    private JPanel createMenuButton(String text, ImageIcon icon) {
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 100;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

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
                frame.add(new SignUpPage(this.frame, this.panel, this.user));
                frame.revalidate();
                frame.repaint();
            });
        }
        if(text.equals("Sign In") || text.equals("Switch Account")) {
            button.addActionListener(e -> {
                frame.getLayeredPane().remove(this);
                frame.getContentPane().removeAll();
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
            button.addActionListener(e -> {
                frame.dispose();
            });
        }

        menuPanel.add(button, gbc);
        return menuPanel;
    }
}
