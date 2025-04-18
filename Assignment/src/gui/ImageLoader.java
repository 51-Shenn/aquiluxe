package gui;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class ImageLoader {

    private static final File whitePorsche;
    private static final File signInWallpaper;
    private static final File signUpWallpaper;
    private static final File forgotPasswordWallpaper;

    static {
        whitePorsche = new File("images/wallpapers/porsche-white.png");
        signInWallpaper = new File("images/wallpapers/car-wallpaper-3.png");
        signUpWallpaper = new File("images/wallpapers/car-wallpaper-2.png");
        forgotPasswordWallpaper = new File("images/wallpapers/car-wallpaper-1.png");
    }

    public static ImageIcon getWhitePorscheImage() {
        return scaleImage(whitePorsche, 1100, 609);
    }

    public static ImageIcon getSignInWallpaper() {
        return scaleImage(signInWallpaper, 960, 1080);
    }

    public static ImageIcon getSignUpWallpaper() {
        return scaleImage(signUpWallpaper, 960, 1080);
    }

    public static ImageIcon getForgotPasswordWallpaper() {
        return scaleImage(forgotPasswordWallpaper, 960, 1080);
    }

    private static ImageIcon scaleImage(File imageFile, int width, int height) {
        Image image = new ImageIcon(imageFile.toString()).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(image);
    }
}
