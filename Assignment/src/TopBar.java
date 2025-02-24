import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {

    public TopBar(JFrame frame) {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        add(logo());
        setPreferredSize(new Dimension(frame.getWidth(), 80));
    }

    private JPanel logo() {
        JPanel container = new JPanel();
        container.setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel("AQUILUXE");
        logoLabel.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(30f));
        logoLabel.setForeground(Color.BLUE);
        container.add(logoLabel);

        JButton[] topBarButtons = new JButton[5];
        String[] topBarButtonsLabels = {"Home", "Vehicles", "Details", "About Us", "Contact Us"};

        for(int i = 0; i < topBarButtonsLabels.length; i++) {
            topBarButtons[i] = new JButton(topBarButtonsLabels[i]);
            topBarButtons[i].setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));
            topBarButtons[i].setBorderPainted(false); // no border
            topBarButtons[i].setFocusPainted(false); // no highlight
            topBarButtons[i].setContentAreaFilled(false); // no fill
            container.add(topBarButtons[i]);
        }

        return container;
    }
}