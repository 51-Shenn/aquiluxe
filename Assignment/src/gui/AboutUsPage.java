package gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutUsPage extends JPanel {

    private JFrame frame;
    private JPanel panel;

    AboutUsPage() {
        this.frame = new JFrame();
        this.panel = new JPanel();
    }

    AboutUsPage(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;

        setBackground(Theme.getBackground());
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        add(createAboutUsPage(), gbc);
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

    private JPanel createAboutUsPage() {
        JPanel aboutUsPanel = new JPanel(new GridBagLayout());
        aboutUsPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        aboutUsPanel.add(createHeader(), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        aboutUsPanel.add(createContentPanel(), gbc);

        return aboutUsPanel;
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Theme.getBackground());

        JLabel wallpaperLabel = new JLabel();
        wallpaperLabel.setIcon(ImageLoader.getKoenigseggImage());
        wallpaperLabel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHEAST;

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);

        String[] texts = {"About", "Us"};
        for (String text : texts) {
            JLabel lines = new JLabel(text);
            if (text.equals(texts[0]))
                lines.setForeground(Theme.getWhite());
            else
                lines.setForeground(Theme.getWhite());
            lines.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(90f));

            titlePanel.add(lines, gbc);
        }
        
        gbc.insets = new Insets(0, 1250, 150, 0);

        wallpaperLabel.add(titlePanel, gbc);
        headerPanel.add(wallpaperLabel);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(50, 50, 20, 50);

        contentPanel.add(createDescriptionPanel(), gbc);

        gbc.anchor = GridBagConstraints.NORTHEAST;
        contentPanel.add(createLocationPanel(), gbc);
        
        return contentPanel;
    }

    private RoundedPanel createDescriptionPanel() {
        RoundedPanel descriptionPanel = new RoundedPanel(10, Theme.getBackground());
        descriptionPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        String[] description = {
            "Welcome to Aquiluxe — The driving experience that combines luxury and class",
            "Aquiluxe provide a wide array of luxury vehicles which designed for those who demand quality, class, and performance.",
            "Our collection includes iconic models from automotive brands such as Porsche, Rolls-Royce, Koenigsegg, Lamborghini,",
            "and many others. Regardless of whether it is for a special occasion, or a business event, Aquiluxe provides a seamless",
            "rental experience. Every vehicle is carefully serviced to ensure perfect condition before every rent, delivering both",
            "comfort and performance to our customers. Elevate your journey by choosing Aquiluxe."
        };

        for(String text : description) {
            JLabel descriptionLabel = new JLabel(text);
            if(text.equals(description[0])) {
                descriptionLabel.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(30f));
                descriptionLabel.setForeground(Theme.getForeground());

                gbc.insets = new Insets(0, 0, 20, 0);
            }
            else {
                descriptionLabel.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(20f));
                descriptionLabel.setForeground(Theme.getSecondaryForeground());

                gbc.insets = new Insets(0, 0, 5, 0);
            }
            
            descriptionPanel.add(descriptionLabel, gbc);
        }


        return descriptionPanel;
    }

    private RoundedPanel createLocationPanel() {
        RoundedPanel locationPanel = new RoundedPanel(10, Theme.getBackground());
        locationPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0);

        JLabel locationTitle = new JLabel("We're located at: ");
        locationTitle.setIcon(IconLoader.getLocationIcon());
        locationTitle.setIconTextGap(20);
        locationTitle.setForeground(Theme.getForeground());
        locationTitle.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(25f));

        locationPanel.add(locationTitle, gbc);

        String[] address = {
            "AQUILUXE HQ",
            "Jalan Malinja, Taman Bunga Raya",
            "53000 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur",
            "Malaysia"
        };

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0);

        for(String text : address) {
            JLabel addressLabel = new JLabel(text);
            addressLabel.setFont(CustomFonts.INSTRUMENT_SANS_MEDIUM.deriveFont(20f));
            addressLabel.setForeground(Theme.getSpecial());
            addressLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addressLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(new URI("https://www.google.com.my/maps/place/Jalan+Malinja,+53100+Kuala+Lumpur,+Wilayah+Persekutuan+Kuala+Lumpur/@3.2148265,101.7257074,17z/data=!3m1!4b1!4m6!3m5!1s0x31cc386a13d21831:0x8a0555aa6aecf29e!8m2!3d3.2148211!4d101.7282823!16s%2Fg%2F1vhq16d3?entry=ttu&g_ep=EgoyMDI1MDQxNi4xIKXMDSoASAFQAw%3D%3D"));
                    } catch (URISyntaxException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            
            locationPanel.add(addressLabel, gbc);
            gbc.gridy++;
        }


        return locationPanel;
    }
}
