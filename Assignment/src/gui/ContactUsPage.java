package gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContactUsPage extends JPanel {

    @SuppressWarnings("unused")
    private JFrame frame;
    @SuppressWarnings("unused")
    private JPanel panel;

    ContactUsPage() {
        this(new JFrame(), new JPanel());
    }

    ContactUsPage(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;

        setBackground(Theme.getBackground());
        add(createContactUsPage());
    }

    private JPanel createContactUsPage() {
        JPanel contactUsPanel = new JPanel(new GridBagLayout());
        contactUsPanel.setBackground(Theme.getBackground());

        JLabel titleLabel = new JLabel("Keep in Touch");
        titleLabel.setForeground(Theme.getForeground());
        titleLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(45f));

        JLabel label = new JLabel(
                "Have a question, feedback, or just want to say hello? We're always happy to hear from you.");
        label.setForeground(Theme.getSecondaryForeground());
        label.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(20f));

        JPanel gridPanel = new JPanel(new GridLayout(1, 3, 70, 0));
        gridPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(60, 0, 0, 0);

        contactUsPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(20, 0, 50, 0);
        contactUsPanel.add(label, gbc);

        gridPanel.add(createProfileCard("Eason", "_51shenn_", "51-Shenn", "yshen0505@gmail.com", "+60 11-7315 2116",
                IconLoader.getAvatar1Icon()));
        gridPanel.add(createProfileCard("Brian", "briankam_", "briankdxtarumt06", "brian060623@gmail.com",
                "+60 12-913 8362", IconLoader.getAvatar2Icon()));
        gridPanel.add(createProfileCard("Jason", "jason___178", "season1ng", "jasontankseng@gmail.com",
                "+60 18-221 2339", IconLoader.getAvatar3Icon()));

        contactUsPanel.add(gridPanel);

        return contactUsPanel;
    }

    private RoundedPanel createProfileCard(String name, String igUsername, String githubUsername, String emailAddress,
            String phoneNumber, ImageIcon icon) {
        RoundedPanel profileCard = new RoundedPanel(20, Theme.getHoverBackground());
        profileCard.setLayout(new GridBagLayout());
        profileCard.setPreferredSize(new Dimension(400, 550));

        JLabel profileIcon = new JLabel(icon);

        JLabel profileName = new JLabel(name);
        profileName.setForeground(Theme.getForeground());
        profileName.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(35f));

        JPanel linkPanel = new JPanel(new GridBagLayout());
        linkPanel.setBackground(Theme.getHoverBackground());

        JLabel label = new JLabel("Where to Find Me:");
        label.setForeground(Theme.getForeground());
        label.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(18f));

        String instagramLink;
        String githubLink;

        switch (name) {
            case "Eason" -> {
                instagramLink = "https://www.instagram.com/_51shenn_/";
                githubLink = "https://github.com/51-Shenn";
            }
            case "Brian" -> {
                instagramLink = "https://www.instagram.com/briankam_/";
                githubLink = "https://github.com/briankdxtarumt06";
            }
            case "Jason" -> {
                instagramLink = "https://www.instagram.com/jason___178/";
                githubLink = "https://github.com/season1ng";
            }
            default -> {
                instagramLink = "";
                githubLink = "";
            }
        }

        JLabel instragramLinkLabel = createLink(igUsername, IconLoader.getInstagramIcon(), instagramLink);
        JLabel githubLinkLabel = createLink(githubUsername, IconLoader.getGitHubIcon(), githubLink);
        JLabel emailLinkLabel = createLink(emailAddress, IconLoader.getEmailIcon());
        JLabel contactLinkLabel = createLink(phoneNumber, IconLoader.getContactIcon());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(50, 0, 0, 0);

        profileCard.add(profileIcon, gbc);

        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(30, 0, 0, 0);

        profileCard.add(profileName, gbc);

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(15, 0, 0, 0);

        linkPanel.add(label, gbc);
        linkPanel.add(instragramLinkLabel, gbc);
        linkPanel.add(githubLinkLabel, gbc);
        linkPanel.add(emailLinkLabel, gbc);
        linkPanel.add(contactLinkLabel, gbc);

        gbc.insets = new Insets(10, 50, 30, 0);
        profileCard.add(linkPanel, gbc);

        return profileCard;
    }

    private JLabel createLink(String string, ImageIcon icon, String link) {
        JLabel linkLabel = new JLabel();
        linkLabel.setIcon(icon);
        linkLabel.setText(string);
        linkLabel.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(20f));
        linkLabel.setIconTextGap(30);
        linkLabel.setForeground(Theme.getSpecial());
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openLink(link);
            }
        });

        return linkLabel;
    }

    private JLabel createLink(String string, ImageIcon icon) {
        JLabel linkLabel = new JLabel();
        linkLabel.setIcon(icon);
        linkLabel.setText(string);
        linkLabel.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(20f));
        linkLabel.setIconTextGap(30);
        linkLabel.setForeground(Theme.getSecondaryForeground());

        return linkLabel;
    }

    private void openLink(String link) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(link));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}