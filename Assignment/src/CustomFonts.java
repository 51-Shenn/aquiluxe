import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class CustomFonts {

    // how to use the fonts?
    // <label>.setFont(CustomFonts.<fontChoice>.deriveFont(<size>f))

    // Example:
    // label.setFont(CustomFonts.ROBOTO_BOLD.deriveFont(30f));

    public static final Font CINZEL_DECORATIVE_BOLD;
    public static final Font OPEN_SANS_BOLD;
    public static final Font ROBOTO_BOLD;

    static {
        // more fonts types can be added here
        CINZEL_DECORATIVE_BOLD = loadFont("resources/fonts/CinzelDecorative/CinzelDecorative-Bold.ttf");
        OPEN_SANS_BOLD = loadFont("resources/fonts/OpenSans/static/OpenSans-Bold.ttf");
        ROBOTO_BOLD = loadFont("resources/fonts/Roboto/static/Roboto-Bold.ttf");
    }

    private static Font loadFont(String path) {
        try {
            // create font
            return Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(CustomFonts.class.getClassLoader().getResourceAsStream(path)));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException("Failed to load font: " + path, e);
        }
    }
}