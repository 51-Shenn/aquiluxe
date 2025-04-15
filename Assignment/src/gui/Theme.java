package gui;

import controllers.UserController;
import java.awt.Color;
import java.io.File;

public class Theme {
    public static Color LIGHT_BLUE = new Color(0x306CFE);
    public static Color OFF_WHITE = new Color(0xF4F7FF);
    public static Color BLACK = new Color(0x0A0908);
    
    private static final Color SUCCESS = new Color(0x28A745);
    private static final Color ERROR = new Color(0xDC3545);
    
    private final static File THEME_FILE = new File("files/settings/theme.txt");

    public static Color getTransparencyColor() {
        return isDarkMode() ? new Color(255, 255, 255, 20) : new Color(0, 0, 0, 20);
    }

    public static Color getBackground() {
        return isDarkMode() ? new Color(0x121212) : new Color(0xFFFFFF);
    }
    
    public static Color getHoverBackground() {
        return isDarkMode() ? new Color(0x252525) : new Color(0XF0F0F0);
    }

    public static Color getPressedBackground() {
        return isDarkMode() ? new Color(0x383838) : new Color(0xE0E7FF);
    }

    public static Color getForeground() {
        return isDarkMode() ? new Color(0xFFFFFF) : new Color(0x000000);
    }

    public static Color getSecondaryForeground() {
        return isDarkMode() ? new Color(0xB0B0B0) : new Color(0x555555);
    }

    public static Color getSpecial() {
        return LIGHT_BLUE;
    }

    public static Color getHoverSpecial() {
        return isDarkMode() ? new Color(0x3A7BFF) : new Color(0x1A5BEE);
    }

    public static Color getPressedSpecial() {
        return new Color(0x1A5BEE);
    }

    public static Color getSpecialForeground() {
        return new Color(0xFFFFFF);
    }

    public static Color getSuccess() {
        return SUCCESS;
    }

    public static Color getHoverSuccess() {
        return isDarkMode() ? new Color(0x34CE57) : new Color(0x218838);
    }

    public static Color getSuccessForeground() {
        return new Color(0xFFFFFF);
    }

    public static Color getError() {
        return ERROR;
    }

    public static Color getHoverError() {
        return isDarkMode() ? new Color(0xE04B5A) : new Color(0xC82333);
    }

    public static Color getPressedError() {
        return isDarkMode() ? new Color(0x8E1926) : new Color(0xA71D2D);
    }

    public static Color getErrorForeground() {
        return new Color(0xFFFFFF);
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