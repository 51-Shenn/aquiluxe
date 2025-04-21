package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        aboutUsPanel.add(createHeader(), gbc);

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
        gbc.insets = new Insets(0, 1450, 10, 0);

        String[] texts = {"About", "Us"};
        for (String text : texts) {
            JLabel lines = new JLabel(text);
            if (text.equals(texts[0]))
                lines.setForeground(Theme.getBlack());
            else
                lines.setForeground(Theme.getSpecial());
            lines.setFont(CustomFonts.INSTRUMENT_SANS_BOLD.deriveFont(90f));

            wallpaperLabel.add(lines, gbc);
        }

        headerPanel.add(wallpaperLabel);

        return headerPanel;
    }
}
