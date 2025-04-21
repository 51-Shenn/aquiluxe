package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
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
        add(createAboutUsPage());
    }

    private JPanel createAboutUsPage() {
        JPanel aboutUsPanel = new JPanel();
        aboutUsPanel.setBackground(Theme.getBackground());

        return new JPanel();
    }
}
