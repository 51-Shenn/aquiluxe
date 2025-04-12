package gui;

import java.awt.Color;
import java.io.File;

import controllers.UserController;

public class Theme {
    public static Color LIGHT_BLUE = new Color(0x306CFE);
    public static Color OFF_WHITE = new Color(0xF4F7FF);
    public static Color BLACK = new Color(0x0A0908);

    private final static File THEME_FILE = new File("files/settings/theme.txt");

    public static Color getBackground() {
        return isDarkMode() ? new Color(0x121212) : new Color(0xFFFFFF);
    }

    public static Color getForeground() {
        return isDarkMode() ? new Color(0xFFFFFF) : new Color(0x000000);
    }

    public static Color getSpecial() {
        return new Color(0x306CFE); // Always the same, but can be dynamic if needed
    }

    public static Color getSpecialForeground() {
        return new Color(0xFFFFFF); // Always the same, but can be dynamic if needed
    }

    public static Color getSuccess() {
        return new Color(0x00FF00); // Always the same, but can be dynamic if needed
    }

    public static Color getSuccessForeground() {
        return new Color(0x000000); // Always the same, but can be dynamic if needed
    }

    public static Color getError() {
        return new Color(0xFF0000); // Always the same, but can be dynamic if needed
    }

    public static Color getErrorForeground() {
        return new Color(0xFFFFFF); // Always the same, but can be dynamic if needed
    }

    public static File getThemeFile() {
        return THEME_FILE;
    }

    public static void setLightBlue(Color newColor) {
        LIGHT_BLUE = newColor;
    }

    public static void setOffWhite(Color newColor) {
        OFF_WHITE = newColor;
    }

    public static void setBlack(Color newColor) {
        BLACK = newColor;
    }

    public static boolean isDarkMode() {
        String theme = UserController.loadTheme(THEME_FILE);
        return theme.equals("Dark");
    }
}
