package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JButton;

public class RoundedButton extends JButton {
    private Color backgroundColor;
    private final int cornerRadius;

    public RoundedButton() {
        this.cornerRadius = 0;
    }

    public RoundedButton(int radius, Color bgColor) {
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

    @Override
    public void setBackground(Color bgColor) {
        this.backgroundColor = bgColor;
        repaint();
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
        g2d.fillRoundRect(0, 0, width - 1, height - 1, arcSize, arcSize);

        // Draw the icon (if set)
        Icon icon = getIcon();
        if (icon != null) {
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int iconX = (width - iconWidth - getText().length() * 5) / 2; // Adjust spacing
            int iconY = (height - iconHeight) / 2;
            icon.paintIcon(this, g2d, iconX, iconY);
        }

        FontMetrics fm = g2d.getFontMetrics();
        int textX = (width - fm.stringWidth(getText())) / 2;
        int textY = (height + fm.getAscent()) / 2 - 2;

        // Fill rounded rectangle
        g2d.setColor(getForeground());
        g2d.drawString(getText(), textX, textY);

        g2d.dispose();
    }
}
