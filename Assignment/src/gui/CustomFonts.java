package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Objects;

import javax.swing.JOptionPane;

public class CustomFonts {

    // how to use the fonts?
    // <label>.setFont(CustomFonts.<fontChoice>.deriveFont(<size>f))

    // Example:
    // label.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(30f));

    public static final Font CINZEL_DECORATIVE_REGULAR;
    public static final Font CINZEL_DECORATIVE_BOLD;
    public static final Font CINZEL_DECORATIVE_BLACK;

    public static final Font OPEN_SANS_REGULAR;
    public static final Font OPEN_SANS_SEMI_BOLD;
    public static final Font OPEN_SANS_BOLD;
    public static final Font OPEN_SANS_EXTRA_BOLD;

    public static final Font ROBOTO_REGULAR;
    public static final Font ROBOTO_SEMI_BOLD;
    public static final Font ROBOTO_BOLD;
    public static final Font ROBOTO_BLACK;

    static {
        // more fonts types can be added here
        CINZEL_DECORATIVE_REGULAR = loadFont("resources/fonts/CinzelDecorative/CinzelDecorative-Regular.ttf");
        CINZEL_DECORATIVE_BOLD = loadFont("resources/fonts/CinzelDecorative/CinzelDecorative-Bold.ttf");
        CINZEL_DECORATIVE_BLACK = loadFont("resources/fonts/CinzelDecorative/CinzelDecorative-Black.ttf");

        OPEN_SANS_REGULAR = loadFont("resources/fonts/OpenSans/static/OpenSans-Regular.ttf");
        OPEN_SANS_SEMI_BOLD = loadFont("resources/fonts/OpenSans/static/OpenSans-SemiBold.ttf");
        OPEN_SANS_BOLD = loadFont("resources/fonts/OpenSans/static/OpenSans-Bold.ttf");
        OPEN_SANS_EXTRA_BOLD = loadFont("resources/fonts/OpenSans/static/OpenSans-ExtraBold.ttf");

        ROBOTO_REGULAR = loadFont("resources/fonts/Roboto/static/Roboto-Regular.ttf");
        ROBOTO_SEMI_BOLD = loadFont("resources/fonts/Roboto/static/Roboto-SemiBold.ttf");
        ROBOTO_BOLD = loadFont("resources/fonts/Roboto/static/Roboto-Bold.ttf");
        ROBOTO_BLACK = loadFont("resources/fonts/Roboto/static/Roboto-Black.ttf");
    }

    private static Font loadFont(String path) {
        try {
            // create font
            return Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(CustomFonts.class.getClassLoader().getResourceAsStream(path)));
        } catch (IOException | FontFormatException | ExceptionInInitializerError | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Failed to load font: " + path);
            return new Font("Sans Serif", Font.BOLD, 16);
        }
    }
}