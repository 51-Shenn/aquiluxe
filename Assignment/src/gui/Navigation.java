package gui;

import datamodels.User;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Navigation {

    public static void removeWindowsLookAndFeel() {
        UIManager.getDefaults().clear();
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
                window.repaint();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setWindowsLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void authenticationPageNavigation(JFrame frame, JPanel panel, User user, String pageToNavigate) {
        frame.getContentPane().removeAll();

        removeWindowsLookAndFeel();

        switch (pageToNavigate) {
            case "SIGN_IN" -> frame.add(new SignInPage(frame, panel, user));
            case "SIGN_UP" -> frame.add(new SignUpPage(frame, panel, user));
        }

        frame.revalidate();
        frame.repaint();
    }

    // use this if already inside the lambda
    public void homePageNavigation(JFrame frame, JPanel panel, User user) {
        frame.getContentPane().removeAll();
        panel.removeAll();
        frame.setLayout(new BorderLayout());
        GUIComponents.overflowMenu = null;
        GUIComponents newGuiComponents = new GUIComponents(frame, panel, user);
        frame.add(newGuiComponents, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        GUIComponents.cardPanel.remove(GUIComponents.homePanel);
        GUIComponents.homePanel = new HomePage(frame, panel, user, newGuiComponents);
        GUIComponents.cardPanel.add(GUIComponents.homePanel, "HomePage");
        GUIComponents.cardLayout.show(GUIComponents.cardPanel, "HomePage");
        // panel.removeAll();
        // panel.add(new HomePage(frame, panel, user, newGuiComponents),
        // BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    // from home page
    public ActionListener toSignInPage(JFrame frame, JPanel panel, User user) {
        return e -> {
            authenticationPageNavigation(frame, panel, user, "SIGN_IN");
        };
    }

    // from overflow menu
    public ActionListener toSignInPage(JFrame frame, JPanel panel, JLayeredPane layeredPane, User user) {
        return e -> {
            frame.getLayeredPane().remove(layeredPane);
            authenticationPageNavigation(frame, panel, user, "SIGN_IN");
        };
    }

    // from home page
    public ActionListener toSignUpPage(JFrame frame, JPanel panel, User user) {
        return e -> {
            authenticationPageNavigation(frame, panel, user, "SIGN_UP");
        };
    }

    // from overflow menu
    public ActionListener toSignUpPage(JFrame frame, JPanel panel, JLayeredPane layeredPane, User user) {
        return e -> {
            frame.getLayeredPane().remove(layeredPane);
            authenticationPageNavigation(frame, panel, user, "SIGN_UP");
        };
    }
}
