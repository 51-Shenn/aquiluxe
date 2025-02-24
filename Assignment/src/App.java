import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) throws Exception {

        new MainWindow();
    }

    public static class MainWindow extends JFrame {

        public MainWindow() {
            setTitle("AQUILUXE");
            setLayout(new BorderLayout());
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            add(new TopBar(this), BorderLayout.NORTH);

            setVisible(true);
        }
    }
}