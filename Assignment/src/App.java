import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {

        new MainWindow();
    }

    public static class MainWindow extends JFrame {

        public MainWindow() {
            // initialize frame
            setTitle("AQUILUXE");
            setLayout(new BorderLayout());
            setMinimumSize(new Dimension(1280, 720));
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            add(new TopBar(this), BorderLayout.NORTH);

            setVisible(true);
        }
    }
}