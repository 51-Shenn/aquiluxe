package gui;

import controllers.UserController;
import datamodels.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends JPanel {

    private JFrame frame;
    private JPanel panel;
    private User user;
    private GUIComponents guiComponents;
    private final File ACCOUNTS_FILE = new File("files/settings/accounts.txt");

    HomePage() {
        this(new JFrame(), new JPanel(), new User());
    }

    HomePage(JFrame frame, JPanel panel, User user, GUIComponents guiComponents) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;
        this.guiComponents = guiComponents;

        setBackground(Theme.getBackground());
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        add(createHomePage(), gbc);
    }

    HomePage(JFrame frame, JPanel panel, User user) {
        this.frame = frame;
        this.panel = panel;
        this.user = user;

        setBackground(Theme.getBackground());
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        add(createHomePage(), gbc);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GUIComponents getGuiComponents() {
        return guiComponents;
    }

    public void setGuiComponents(GUIComponents guiComponents) {
        this.guiComponents = guiComponents;
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

    private JPanel createHomePage() {
        JPanel homePagePanel = new JPanel(new GridBagLayout());
        homePagePanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(70, 80, 0, 0);
        gbc.anchor = GridBagConstraints.NORTH;
        homePagePanel.add(createTitlePanel(), gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;

        homePagePanel.add(createWallpaperPanel(), gbc);

        return homePagePanel;
    }

    private JPanel createWallpaperPanel() {
        JPanel wallpaperPanel = new JPanel(new GridBagLayout());
        wallpaperPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, 20, 0);

        wallpaperPanel.add(createButtonPanel(), gbc);

        JLabel wallpaper = new JLabel(ImageLoader.getWhitePorscheImage());
        wallpaperPanel.add(wallpaper, gbc);

        return wallpaperPanel;
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] title = { "Every Drive Should Feel", "Like a Privilege." };
        for (int i = 0; i < title.length; i++) {
            JLabel titleLabel = new JLabel(title[i]);
            titleLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(70f));
            titleLabel.setForeground(Theme.getForeground());

            if (i == title.length - 1) {
                gbc.insets = new Insets(0, 0, 50, 0);
            }
            titlePanel.add(titleLabel, gbc);
        }

        String[] description = {
                "Discover a seamless car rental experience designed for discerning drivers.",
                "Choose from our exclusive fleet of luxury vehicles - Each meticulously",
                "maintained for comfort, performance, and style. Whether for business,",
                "leisure, or a special occasion, we deliver the keys to excellence.",
        };

        gbc.insets = new Insets(0, 0, 5, 0);
        for (String text : description) {
            JLabel descriptionLabel = new JLabel(text);
            descriptionLabel.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(22f));
            descriptionLabel.setForeground(Theme.getSecondaryForeground());

            titlePanel.add(descriptionLabel, gbc);
        }

        RoundedButton startButton = new RoundedButton(20, Theme.getSpecial());
        startButton.setText("Start Browsing");
        startButton.setBackground(Theme.getSpecial());
        startButton.setForeground(Theme.getSpecialForeground());
        startButton.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(24f));
        startButton.setPreferredSize(new Dimension(250, 80));
        startButton.setMinimumSize(new Dimension(250, 80));
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                startButton.setBackground(Theme.getHoverSpecial());
                startButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                startButton.setBackground(Theme.getSpecial());
                startButton.repaint();
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                startButton.setBackground(Theme.getPressedSpecial());
                startButton.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                startButton.setBackground(Theme.getSpecial());
                startButton.repaint();
            }
        });
        startButton.addActionListener(e -> {
            JButton[] topBarButtons = this.guiComponents.getTopBarButtons();
            for (JButton button : topBarButtons) {
                button.setForeground(Theme.getForeground());
                button.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(18f));
            }
            topBarButtons[1].setForeground(Theme.getSpecial());
            topBarButtons[1].setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(20f));

            GUIComponents.cardLayout.show(GUIComponents.cardPanel, "VehiclesPage");
        });

        gbc.insets = new Insets(70, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        titlePanel.add(startButton, gbc);

        return titlePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 0, 100, 80);
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;

        JPanel packButtonPanel = new JPanel();
        packButtonPanel.setBackground(Theme.getBackground());

        if (this.user.getUserId() == 0) {
            RoundedButton signInButton = createButton("Sign In", Theme.getBackground(), Theme.getForeground());
            signInButton.addActionListener(new Navigation().toSignInPage(this.frame, this.panel, this.user));
            signInButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    signInButton.setBackground(Theme.getHoverBackground());
                    signInButton.repaint();
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    signInButton.setBackground(Theme.getBackground());
                    signInButton.repaint();
                }

                @Override
                public void mousePressed(MouseEvent evt) {
                    signInButton.setBackground(Theme.getPressedBackground());
                    signInButton.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent evt) {
                    signInButton.setBackground(Theme.getBackground());
                    signInButton.repaint();
                }
            });

            RoundedButton signUpButton = createButton("Sign Up", Theme.getSpecial(), Theme.getSpecialForeground());
            signUpButton.addActionListener(new Navigation().toSignUpPage(this.frame, this.panel, this.user));
            signUpButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    signUpButton.setBackground(Theme.getHoverSpecial());
                    signUpButton.repaint();
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    signUpButton.setBackground(Theme.getSpecial());
                    signUpButton.repaint();
                }

                @Override
                public void mousePressed(MouseEvent evt) {
                    signUpButton.setBackground(Theme.getPressedSpecial());
                    signUpButton.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent evt) {
                    signUpButton.setBackground(Theme.getSpecial());
                    signUpButton.repaint();
                }
            });

            packButtonPanel.add(signInButton);
            packButtonPanel.add(signUpButton);
        } else {
            RoundedButton signOutButton = createButton("Sign Out", Theme.getError(), Theme.getErrorForeground());
            signOutButton.addActionListener(e -> {
                Dialog dialog = new Dialog(this.frame);
                boolean proceed = dialog.showDialog(
                        "HAZARD",
                        "Sign Out",
                        "Sign Out?",
                        "Logging out will end your session.",
                        true);

                if (proceed) {
                    UserController.removeUserFromFile(this.user.getUserId(), ACCOUNTS_FILE);

                    this.user = UserController.loadCurrentUser(ACCOUNTS_FILE);
                    GUIComponents.refreshPages(this.frame, this.panel, this.user, guiComponents);
                    GUIComponents.cardLayout.show(GUIComponents.cardPanel, "HomePage");
                }
            });
            signOutButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    signOutButton.setBackground(Theme.getHoverError());
                    signOutButton.repaint();
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    signOutButton.setBackground(Theme.getError());
                    signOutButton.repaint();
                }

                @Override
                public void mousePressed(MouseEvent evt) {
                    signOutButton.setBackground(Theme.getPressedError());
                    signOutButton.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent evt) {
                    signOutButton.setBackground(Theme.getError());
                    signOutButton.repaint();
                }
            });

            packButtonPanel.add(signOutButton);
        }

        buttonPanel.add(packButtonPanel, gbc);

        return buttonPanel;
    }

    private RoundedButton createButton(String text, Color background, Color foreground) {
        RoundedButton button = new RoundedButton(15, background);
        button.setText(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(CustomFonts.ROBOTO_SEMI_BOLD.deriveFont(22f));
        button.setPreferredSize(new Dimension(150, 70));
        button.setMinimumSize(new Dimension(150, 70));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }

    public File getACCOUNTS_FILE() {
        return ACCOUNTS_FILE;
    }
}
