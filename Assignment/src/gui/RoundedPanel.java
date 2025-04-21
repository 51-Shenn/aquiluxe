package gui;

import java.awt.*;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius;

    public RoundedPanel(int radius, Color bgColor) {
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
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
