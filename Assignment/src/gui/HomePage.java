package gui;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private JFrame frame;
    private JPanel panel;

    HomePage() {
        this.frame = new JFrame();
        this.panel = new JPanel();
    }

    HomePage(JFrame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;

        setBackground(Color.BLACK);
        add(createHomePage(), BorderLayout.CENTER);
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

        JLabel label = new JLabel("AQUILUXE");
        label.setFont(CustomFonts.CINZEL_DECORATIVE_BLACK.deriveFont(80f));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 0));

        homePagePanel.add(label);
        return homePagePanel;
    }
}
