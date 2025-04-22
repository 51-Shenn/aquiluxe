package gui;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.List;
import datamodels.Vehicle;

public class ImageLoader {

    private static final File WHITE_PORSCHE;
    private static final File KOENIGSEGG;
    private static final File SIGN_IN_WALLPAPER;
    private static final File SIGN_UP_WALLPAPER;
    private static final File FORGOT_PASSWORD_WALLPAPER;

    private static HashMap<String, ImageIcon> vehicleImageCache = new HashMap<>();

    static {
        WHITE_PORSCHE = new File("images/wallpapers/porsche-white.png");
        KOENIGSEGG = new File("images/wallpapers/koenigsegg.jpg");
        SIGN_IN_WALLPAPER = new File("images/wallpapers/car-wallpaper-3.png");
        SIGN_UP_WALLPAPER = new File("images/wallpapers/car-wallpaper-2.png");
        FORGOT_PASSWORD_WALLPAPER = new File("images/wallpapers/car-wallpaper-1.png");

        File[] images = {
                WHITE_PORSCHE,
                SIGN_IN_WALLPAPER,
                SIGN_UP_WALLPAPER,
                FORGOT_PASSWORD_WALLPAPER
        };

        checkEachImageExistence(images);
    }

    public static void loadImages(List<Vehicle> vehicles) {
        new Thread(() -> {
            for (Vehicle vehicle : vehicles) {
                ImageIcon image = new ImageIcon(vehicle.getImagePath());
                Image rImage = image.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                image = new ImageIcon(rImage);

                vehicleImageCache.put(vehicle.getImagePath(), image);
            }
        }).start();
    }

    public static HashMap<String, ImageIcon> getImageCache() {
        return vehicleImageCache;
    }

    private static void checkEachImageExistence(File[] images) {
        for (File image : images) {
            if (!image.exists()) {
                JOptionPane.showMessageDialog(null, "Failed to load image:\n" + image + "\n");
                break;
            }
        }
    }

    public static ImageIcon getWhitePorscheImage() {
        return scaleImage(WHITE_PORSCHE, 1100, 609);
    }

    public static ImageIcon getKoenigseggImage() {
        return scaleImage(KOENIGSEGG, 1920, 555);
    }

    public static ImageIcon getSignInWallpaper() {
        return scaleImage(SIGN_IN_WALLPAPER, 960, 1080);
    }

    public static ImageIcon getSignUpWallpaper() {
        return scaleImage(SIGN_UP_WALLPAPER, 960, 1080);
    }

    public static ImageIcon getForgotPasswordWallpaper() {
        return scaleImage(FORGOT_PASSWORD_WALLPAPER, 960, 1080);
    }

    private static ImageIcon scaleImage(File imageFile, int width, int height) {
        Image image = new ImageIcon(imageFile.toString()).getImage().getScaledInstance(width, height,
                Image.SCALE_SMOOTH);

        return new ImageIcon(image);
    }
}
