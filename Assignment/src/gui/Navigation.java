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
        // frame.getContentPane().removeAll();

        removeWindowsLookAndFeel();

        switch (pageToNavigate) {
            case "SIGN_IN" -> GUIComponents.mainCardLayout.show(GUIComponents.mainCardPanel, "SignInPage");
            case "SIGN_UP" -> GUIComponents.mainCardLayout.show(GUIComponents.mainCardPanel, "SignUpPage");
        }

        frame.revalidate();
        frame.repaint();
    }

    // from home page
    public ActionListener toSignInPage(JFrame frame, JPanel panel, User user) {
        return e -> {
            MainApp.getGuiComponents().setVisible(false);
            GUIComponents.topBarPanel.setVisible(false);
            authenticationPageNavigation(frame, panel, user, "SIGN_IN");
        };
    }

    // from overflow menu
    public ActionListener toSignInPage(JFrame frame, JPanel panel, JLayeredPane layeredPane, User user) {
        return e -> {
            frame.getLayeredPane().remove(layeredPane);
            MainApp.getGuiComponents().setVisible(false);
            GUIComponents.topBarPanel.setVisible(false);
            authenticationPageNavigation(frame, panel, user, "SIGN_IN");
        };
    }

    // from home page
    public ActionListener toSignUpPage(JFrame frame, JPanel panel, User user) {
        return e -> {
            MainApp.getGuiComponents().setVisible(false);
            GUIComponents.topBarPanel.setVisible(false);
            authenticationPageNavigation(frame, panel, user, "SIGN_UP");
        };
    }

    // from overflow menu
    public ActionListener toSignUpPage(JFrame frame, JPanel panel, JLayeredPane layeredPane, User user) {
        return e -> {
            frame.getLayeredPane().remove(layeredPane);
            MainApp.getGuiComponents().setVisible(false);
            GUIComponents.topBarPanel.setVisible(false);
            authenticationPageNavigation(frame, panel, user, "SIGN_UP");
        };
    }
}
