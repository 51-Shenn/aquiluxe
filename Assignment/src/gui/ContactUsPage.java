package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContactUsPage extends JPanel {

    private JFrame frame;
    private JPanel panel;

    ContactUsPage() {
        this.frame = new JFrame();
        this.panel = new JPanel();
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

        JLabel label = new JLabel("Have a question, feedback, or just want to say hello? We're always happy to hear from you.");
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

        gridPanel.add(createProfileCard("Eason", "_51shenn_", "51-Shenn", "yshen0505@gmail.com", "+60 11-7315 2116", IconLoader.getAvatar1Icon()));
        gridPanel.add(createProfileCard("Brian", "briankam_", "briankdxtarumt06", "brian060623@gmail.com", "+60 12-913 8362", IconLoader.getAvatar2Icon()));
        gridPanel.add(createProfileCard("Jason", "jason___178", "season1ng", "jason@gmail.com", "+60 18-221 2339", IconLoader.getAvatar3Icon()));

        contactUsPanel.add(gridPanel);

        return contactUsPanel;
    }
 
    private RoundedPanel createProfileCard(String name, String igUsername, String githubUsername, String emailAddress, String phoneNumber, ImageIcon icon) {
        RoundedPanel profileCard = new RoundedPanel(20, Theme.getHoverBackground());
        profileCard.setLayout(new GridBagLayout());
        profileCard.setPreferredSize(new Dimension(400, 550));

        JLabel profileIcon = new JLabel(icon);

        JLabel profileName = new JLabel(name);
        profileName.setForeground(Theme.getForeground());
        profileName.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(35f));

        JPanel linkPanel = new JPanel(new GridBagLayout());
        linkPanel.setBackground(Theme.getHoverBackground());

        JLabel instragramLink = createLink(igUsername, IconLoader.getInstagramIcon());
        JLabel githubLink = createLink(githubUsername, IconLoader.getGitHubIcon());
        JLabel emailLink = createLink(emailAddress, IconLoader.getEmailIcon());
        JLabel contactLink = createLink(phoneNumber, IconLoader.getContactIcon());

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

        // gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(15, 0, 0, 0);

        linkPanel.add(instragramLink, gbc);
        linkPanel.add(githubLink, gbc);
        linkPanel.add(emailLink, gbc);
        linkPanel.add(contactLink, gbc);

        gbc.insets = new Insets(10, 50, 0, 0);
        profileCard.add(linkPanel, gbc);

        return profileCard;
    }

    private JLabel createLink(String string, ImageIcon icon) {
        JLabel linkLabel = new JLabel();
        linkLabel.setIcon(icon);
        linkLabel.setText(string);
        linkLabel.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(20f));
        linkLabel.setIconTextGap(30);
        linkLabel.setForeground(Theme.getSpecial());

        return linkLabel;
    }

    // Custom JPanel with rounded corners
    private class RoundedPanel extends JPanel {
        private Color backgroundColor;
        private int cornerRadius;

        public RoundedPanel(int radius, Color bgColor) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // Enable anti-aliasing for smooth rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int arcSize = cornerRadius * 2;

            // Make panel transparent
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2d.setColor(backgroundColor != null ? backgroundColor : getBackground());

            // Fill rounded rectangle
            g2d.fillRoundRect(0, 0, width - 1, height - 1, arcSize, arcSize);

            g2d.dispose();
        }
    }
}