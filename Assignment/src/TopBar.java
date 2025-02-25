import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {

    public TopBar(JFrame frame) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(frame.getWidth(), 80));
        setLayout(new GridBagLayout());

        add(logoLabel());
        add(createButtonContainer());

    }

    private JLabel logoLabel() {
        JLabel logoLabel = new JLabel("AQUILUXE");
        logoLabel.setFont(CustomFonts.CINZEL_DECORATIVE_BOLD.deriveFont(30f));
        logoLabel.setForeground(Color.BLUE);

        return logoLabel;
    }

    private JPanel createButtonContainer() {
        JPanel topBarContainer = new JPanel();
        topBarContainer.setBackground(Color.WHITE);

        JButton[] buttons = createButtons();
        for (JButton button : buttons) {
            topBarContainer.add(button);
        }

        return topBarContainer;
    }

    private JButton[] createButtons() {
        JButton[] topBarButtons = new JButton[4];
        String[] topBarButtonsLabels = { "Home", "Vehicles", "About Us", "Contact Us" };

        for (int i = 0; i < topBarButtonsLabels.length; i++) {
            topBarButtons[i] = new JButton(topBarButtonsLabels[i]);
            topBarButtons[i].setFont(CustomFonts.ROBOTO_BOLD.deriveFont(15f));
            topBarButtons[i].setBorderPainted(false); // no border
            topBarButtons[i].setFocusPainted(false); // no highlight
            topBarButtons[i].setContentAreaFilled(false); // no fill
        }

        return topBarButtons;
    }
}