import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {

    public TopBar(JFrame frame) {
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(logo());
        setPreferredSize(new Dimension(frame.getWidth(), 80));
    }

    private JLabel logo() {
        JLabel logoLabel = new JLabel("AQUILUXE");
        logoLabel.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(30f));

        return logoLabel;
    }
}