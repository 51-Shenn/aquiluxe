package gui;

import java.awt.Color;

public class Colors {
    public static Color LIGHT_BLUE = new Color(0x306CFE);
    public static Color OFF_WHITE = new Color(0xF4F7FF);
    public static Color BLACK = new Color(0x0A0908);

    // setColor(new Color(0x000000));
    // setBackground(Colors.Color));

    public static void setLightBlue(Color newColor) {
        LIGHT_BLUE = newColor;
    }

    public static void setOffWhite(Color newColor) {
        OFF_WHITE = newColor;
    }

    public static void setBlack(Color newColor) {
        BLACK = newColor;
    }
}