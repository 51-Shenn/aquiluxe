package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
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
