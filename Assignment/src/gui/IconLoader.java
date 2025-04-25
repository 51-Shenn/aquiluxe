package gui;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class IconLoader extends Theme {

    // shared icons
    // window-icon
    private static final File APP_ICON;
    // authentication-page
    private static final File X_ICON;
    private static final File EYE_ON_ICON;
    private static final File EYE_OFF_ICON;
    // dialog
    private static final File DIALOG_SUCCESS;
    private static final File DIALOG_ERROR;
    private static final File DIALOG_HAZARD;
    // profile
    private static final File BIG_MALE_PROFILE_ICON;
    private static final File BIG_FEMALE_PROFILE_ICON;
    private static final File SMALL_MALE_PROFILE_ICON;
    private static final File SMALL_FEMALE_PROFILE_ICON;
    private static final File AVATAR_1_ICON;
    private static final File AVATAR_2_ICON;
    private static final File AVATAR_3_ICON;
    private static final File INSTAGRAM_ICON;
    private static final File GITHUB_ICON;
    private static final File CONTACT_ICON;
    private static final File EMAIL_ICON;
    private static final File LOCATION_ICON;
    // vehicle-page
    private static final File SEARCH_ICON;
    private static final File ADD_ICON;
    private static final File DELETE_ICON;
    
    // light theme icons
    // overflow-menu
    private static final File BLACK_CLOSE_ICON;
    private static final File BLACK_EDIT_PROFILE_ICON;
    private static final File BLACK_SIGN_IN_ICON;
    private static final File BLACK_SIGN_OUT_ICON;
    private static final File BLACK_MENU_ICON;
    private static final File BLACK_SUN_MOON_ICON;
    private static final File BLACK_SUN_ICON;
    private static final File BLACK_MOON_ICON;
    private static final File BLACK_PLUS_ICON;
    private static final File BLACK_SWITCH_ICON;
    private static final File BLACK_TRASH_ICON;
    // vehicle-details-page
    private static final File BLACK_UP_ICON;
    private static final File BLACK_DOWN_ICON;
    private static final File BLACK_RIGHT_ICON;
    // vehicle-page
    private static final File BLACK_CAR_ICON;
    private static final File BLACK_BIKE_ICON;
    private static final File BLACK_MANUAL_TRANSMISSION_ICON;
    private static final File BLACK_SPEEDOMETER_ICON;
    private static final File BLACK_CAR_SEAT_ICON;
    private static final File BLACK_CHASSIS_ICON;
    private static final File BLACK_ENGINE_ICON;
    private static final File BLACK_FUEL_GAUGE_ICON;
    private static final File BLACK_GAS_ICON;
    private static final File BLACK_HORSE_ICON;
    
    // dark theme icons
    // overflow-menu
    private static final File WHITE_CLOSE_ICON;
    private static final File WHITE_EDIT_PROFILE_ICON;
    private static final File WHITE_SIGN_IN_ICON;
    private static final File WHITE_SIGN_OUT_ICON;
    private static final File WHITE_MENU_ICON;
    private static final File WHITE_SUN_MOON_ICON;
    private static final File WHITE_SUN_ICON;
    private static final File WHITE_MOON_ICON;
    private static final File WHITE_PLUS_ICON;
    private static final File WHITE_SWITCH_ICON;
    private static final File WHITE_TRASH_ICON;
    // vehicle-details-page
    private static final File WHITE_UP_ICON;
    private static final File WHITE_DOWN_ICON;
    private static final File WHITE_RIGHT_ICON;
    // vehicle-page
    private static final File WHITE_CAR_ICON;
    private static final File WHITE_BIKE_ICON;
    private static final File WHITE_MANUAL_TRANSMISSION_ICON;
    private static final File WHITE_SPEEDOMETER_ICON;
    private static final File WHITE_CAR_SEAT_ICON;
    private static final File WHITE_CHASSIS_ICON;
    private static final File WHITE_ENGINE_ICON;
    private static final File WHITE_FUEL_GAUGE_ICON;
    private static final File WHITE_GAS_ICON;
    private static final File WHITE_HORSE_ICON;
    
    static {
        String mainPath = "images/icons/";
        String lightThemePath = "images/icons/light-theme/";
        String darkThemePath = "images/icons/dark-theme/";
        String dialogPath = "images/icons/dialog/";
        String profilePath = "images/icons/profile/";

        String vehiclePath = "images/icons/vehicle/";
        String vehicleLightThemePath = "images/icons/vehicle/light-theme/";
        String vehicleDarkThemePath = "images/icons/vehicle/dark-theme/";

        APP_ICON = new File(String.format("%s" + "car-logo.png", mainPath));

        X_ICON = new File(String.format("%s" + "x.png", lightThemePath));
        EYE_ON_ICON = new File(String.format("%s" + "eye-on.png", lightThemePath));
        EYE_OFF_ICON = new File(String.format("%s" + "eye-off.png", lightThemePath));

        DIALOG_SUCCESS = new File(String.format("%s" + "success.png", dialogPath));
        DIALOG_ERROR = new File(String.format("%s" + "error.png", dialogPath));
        DIALOG_HAZARD = new File(String.format("%s" + "hazard.png", dialogPath));

        BIG_MALE_PROFILE_ICON = new File(String.format("%s" + "male.png", profilePath));
        BIG_FEMALE_PROFILE_ICON = new File(String.format("%s" + "female.png", profilePath));
        SMALL_MALE_PROFILE_ICON = new File(String.format("%s" + "small-male.png", profilePath));
        SMALL_FEMALE_PROFILE_ICON = new File(String.format("%s" + "small-female.png", profilePath));
        AVATAR_1_ICON = new File(String.format("%s" + "avatar-1.png", profilePath));
        AVATAR_2_ICON = new File(String.format("%s" + "avatar-2.png", profilePath));
        AVATAR_3_ICON = new File(String.format("%s" + "avatar-3.png", profilePath));
        INSTAGRAM_ICON = new File(String.format("%s" + "instagram.png", profilePath));
        GITHUB_ICON = new File(String.format("%s" + "github1.png", profilePath));
        CONTACT_ICON = new File(String.format("%s" + "contact.png", profilePath));
        EMAIL_ICON = new File(String.format("%s" + "email.png", profilePath));
        LOCATION_ICON = new File(String.format("%s" + "location.png", profilePath));

        SEARCH_ICON = new File(String.format("%s" + "search.png", vehiclePath));
        ADD_ICON = new File(String.format("%s" + "add.png", vehiclePath));
        DELETE_ICON = new File(String.format("%s" + "delete.png", vehiclePath));

        File[] generalIcons = {
            X_ICON,
            EYE_ON_ICON,
            EYE_OFF_ICON,
            DIALOG_SUCCESS,
            DIALOG_ERROR,
            DIALOG_HAZARD,
            BIG_MALE_PROFILE_ICON,
            BIG_FEMALE_PROFILE_ICON,
            SMALL_MALE_PROFILE_ICON,
            SMALL_FEMALE_PROFILE_ICON,
            AVATAR_1_ICON,
            AVATAR_2_ICON,
            AVATAR_3_ICON,
            INSTAGRAM_ICON,
            GITHUB_ICON,
            CONTACT_ICON,
            EMAIL_ICON,
            LOCATION_ICON,
            SEARCH_ICON,
            ADD_ICON,
            DELETE_ICON
        };

        // light theme icons
        BLACK_CLOSE_ICON = new File(String.format("%s" + "circle-x.png", lightThemePath));
        BLACK_EDIT_PROFILE_ICON = new File(String.format("%s" + "edit-profile.png", lightThemePath));
        BLACK_SIGN_IN_ICON = new File(String.format("%s" + "log-in.png", lightThemePath));
        BLACK_SIGN_OUT_ICON = new File(String.format("%s" + "log-out.png", lightThemePath));
        BLACK_MENU_ICON = new File(String.format("%s" + "menu.png", lightThemePath));
        BLACK_SUN_MOON_ICON = new File(String.format("%s" + "sun-moon.png", lightThemePath));
        BLACK_SUN_ICON = new File(String.format("%s" + "sun.png", lightThemePath));
        BLACK_MOON_ICON = new File(String.format("%s" + "moon.png", lightThemePath));
        BLACK_PLUS_ICON = new File(String.format("%s" + "plus.png", lightThemePath));
        BLACK_SWITCH_ICON = new File(String.format("%s" + "switch.png", lightThemePath));
        BLACK_TRASH_ICON = new File(String.format("%s" + "trash.png", lightThemePath));

        BLACK_UP_ICON = new File(String.format("%s" + "chevron-up.png", vehicleLightThemePath));
        BLACK_DOWN_ICON = new File(String.format("%s" + "chevron-down.png", vehicleLightThemePath));
        BLACK_RIGHT_ICON = new File(String.format("%s" + "chevron-right.png", vehicleLightThemePath));
        BLACK_CAR_ICON = new File(String.format("%s" + "car.png", vehicleLightThemePath));
        BLACK_BIKE_ICON = new File(String.format("%s" + "bike.png", vehicleLightThemePath));
        BLACK_MANUAL_TRANSMISSION_ICON = new File(String.format("%s" + "manual-transmission.png", vehicleLightThemePath));
        BLACK_SPEEDOMETER_ICON = new File(String.format("%s" + "speedometer.png", vehicleLightThemePath));
        BLACK_CAR_SEAT_ICON = new File(String.format("%s" + "car-seat.png", vehicleLightThemePath));
        BLACK_CHASSIS_ICON = new File(String.format("%s" + "chassis.png", vehicleLightThemePath));
        BLACK_ENGINE_ICON = new File(String.format("%s" + "engine.png", vehicleLightThemePath));
        BLACK_FUEL_GAUGE_ICON = new File(String.format("%s" + "fuel-gauge.png", vehicleLightThemePath));
        BLACK_GAS_ICON = new File(String.format("%s" + "gas-station.png", vehicleLightThemePath));
        BLACK_HORSE_ICON = new File(String.format("%s" + "horse.png", vehicleLightThemePath));

        File[] blackIcons = {
            BLACK_CLOSE_ICON,
            BLACK_EDIT_PROFILE_ICON,
            BLACK_SIGN_IN_ICON,
            BLACK_SIGN_OUT_ICON,
            BLACK_MENU_ICON,
            BLACK_SUN_MOON_ICON,
            BLACK_SUN_ICON,
            BLACK_MOON_ICON,
            BLACK_PLUS_ICON,
            BLACK_SWITCH_ICON,
            BLACK_TRASH_ICON,
            BLACK_UP_ICON,
            BLACK_DOWN_ICON,
            BLACK_RIGHT_ICON,
            BLACK_CAR_ICON,
            BLACK_BIKE_ICON,
            BLACK_MANUAL_TRANSMISSION_ICON,
            BLACK_SPEEDOMETER_ICON,
            BLACK_CAR_SEAT_ICON,
            BLACK_CHASSIS_ICON,
            BLACK_ENGINE_ICON,
            BLACK_FUEL_GAUGE_ICON,
            BLACK_GAS_ICON,
            BLACK_HORSE_ICON
        };
        
        // dark theme icons
        WHITE_CLOSE_ICON = new File(String.format("%s"+"circle-x.png", darkThemePath));
        WHITE_EDIT_PROFILE_ICON = new File(String.format("%s" + "edit-profile.png", darkThemePath));
        WHITE_SIGN_IN_ICON = new File(String.format("%s" + "log-in.png", darkThemePath));
        WHITE_SIGN_OUT_ICON = new File(String.format("%s" + "log-out.png", darkThemePath));
        WHITE_MENU_ICON = new File(String.format("%s" + "menu.png", darkThemePath));
        WHITE_SUN_MOON_ICON = new File(String.format("%s" + "sun-moon.png", darkThemePath));
        WHITE_SUN_ICON = new File(String.format("%s" + "sun.png", darkThemePath));
        WHITE_MOON_ICON = new File(String.format("%s" + "moon.png", darkThemePath));
        WHITE_PLUS_ICON = new File(String.format("%s" + "plus.png", darkThemePath));
        WHITE_SWITCH_ICON = new File(String.format("%s" + "switch.png", darkThemePath));
        WHITE_TRASH_ICON = new File(String.format("%s" + "trash.png", darkThemePath));

        WHITE_UP_ICON = new File(String.format("%s" + "chevron-up.png", vehicleDarkThemePath));
        WHITE_DOWN_ICON = new File(String.format("%s" + "chevron-down.png", vehicleDarkThemePath));
        WHITE_RIGHT_ICON = new File(String.format("%s" + "chevron-right.png", vehicleDarkThemePath));
        WHITE_CAR_ICON = new File(String.format("%s" + "car.png", vehicleDarkThemePath));
        WHITE_BIKE_ICON = new File(String.format("%s" + "bike.png", vehicleDarkThemePath));
        WHITE_MANUAL_TRANSMISSION_ICON = new File(String.format("%s" + "manual-transmission.png", vehicleDarkThemePath));
        WHITE_SPEEDOMETER_ICON = new File(String.format("%s" + "speedometer.png", vehicleDarkThemePath));
        WHITE_CAR_SEAT_ICON = new File(String.format("%s" + "car-seat.png", vehicleDarkThemePath));
        WHITE_CHASSIS_ICON = new File(String.format("%s" + "chassis.png", vehicleDarkThemePath));
        WHITE_ENGINE_ICON = new File(String.format("%s" + "engine.png", vehicleDarkThemePath));
        WHITE_FUEL_GAUGE_ICON = new File(String.format("%s" + "fuel-gauge.png", vehicleDarkThemePath));
        WHITE_GAS_ICON = new File(String.format("%s" + "gas-station.png", vehicleDarkThemePath));
        WHITE_HORSE_ICON = new File(String.format("%s" + "horse.png", vehicleDarkThemePath));

        File[] whiteIcons = {
            WHITE_CLOSE_ICON,
            WHITE_EDIT_PROFILE_ICON,
            WHITE_SIGN_IN_ICON,
            WHITE_SIGN_OUT_ICON,
            WHITE_MENU_ICON,
            WHITE_SUN_MOON_ICON,
            WHITE_SUN_ICON,
            WHITE_MOON_ICON,
            WHITE_PLUS_ICON,
            WHITE_SWITCH_ICON,
            WHITE_TRASH_ICON,
            WHITE_UP_ICON,
            WHITE_DOWN_ICON,
            WHITE_RIGHT_ICON,
            WHITE_CAR_ICON,
            WHITE_BIKE_ICON,
            WHITE_MANUAL_TRANSMISSION_ICON,
            WHITE_SPEEDOMETER_ICON,
            WHITE_CAR_SEAT_ICON,
            WHITE_CHASSIS_ICON,
            WHITE_ENGINE_ICON,
            WHITE_FUEL_GAUGE_ICON,
            WHITE_GAS_ICON,
            WHITE_HORSE_ICON
        };

        // check if every file exists
        checkEachIconExistence(generalIcons);
        checkEachIconExistence(blackIcons);
        checkEachIconExistence(whiteIcons);
    }

    private static void checkEachIconExistence(File[] icons) {
        for(File icon : icons) {
            if (!icon.exists()) {
                JOptionPane.showMessageDialog(null, "Failed to load image:\n" + icon + "\n");
                break;
            }
        }
    }

    public static ImageIcon getAppIcon() {
        return new ImageIcon(APP_ICON.toString());
    }

    public static ImageIcon getInstagramIcon() {
        return new ImageIcon(INSTAGRAM_ICON.toString());
    }

    public static ImageIcon getGitHubIcon() {
        return new ImageIcon(GITHUB_ICON.toString());
    }

    public static ImageIcon getXIcon() {
        return new ImageIcon(X_ICON.toString());
    }

    public static ImageIcon getEyeOnIcon() {
        return new ImageIcon(EYE_ON_ICON.toString());
    }

    public static ImageIcon getEyeOffIcon() {
        return new ImageIcon(EYE_OFF_ICON.toString());
    }

    public static ImageIcon getDialogSuccessIcon() {
        return new ImageIcon(DIALOG_SUCCESS.toString());
    }

    public static ImageIcon getDialogErrorIcon() {
        return new ImageIcon(DIALOG_ERROR.toString());
    }
    public static ImageIcon getDialogHazardIcon() {
        return new ImageIcon(DIALOG_HAZARD.toString());
    }

    public static ImageIcon getBigMaleProfileIcon() {
        return new ImageIcon(BIG_MALE_PROFILE_ICON.toString());
    }

    public static ImageIcon getBigFemaleProfileIcon() {
        return new ImageIcon(BIG_FEMALE_PROFILE_ICON.toString());
    }

    public static ImageIcon getSmallMaleProfileIcon() {
        return new ImageIcon(SMALL_MALE_PROFILE_ICON.toString());
    }

    public static ImageIcon getSmallFemaleProfileIcon() {
        return new ImageIcon(SMALL_FEMALE_PROFILE_ICON.toString());
    }

    public static ImageIcon getAvatar1Icon() {
        return new ImageIcon(AVATAR_1_ICON.toString());
    }

    public static ImageIcon getAvatar2Icon() {
        return new ImageIcon(AVATAR_2_ICON.toString());
    }

    public static ImageIcon getAvatar3Icon() {
        return new ImageIcon(AVATAR_3_ICON.toString());
    }

    public static ImageIcon getContactIcon() {
        return new ImageIcon(CONTACT_ICON.toString());
    }

    public static ImageIcon getEmailIcon() {
        return new ImageIcon(EMAIL_ICON.toString());
    }

    public static ImageIcon getLocationIcon() {
        return new ImageIcon(LOCATION_ICON.toString());
    }

    public static ImageIcon getSearchIcon() {
        return new ImageIcon(SEARCH_ICON.toString());
    }

    public static ImageIcon getAddIcon() {
        return new ImageIcon(ADD_ICON.toString());
    }

    public static ImageIcon getDeleteIcon() {
        return new ImageIcon(DELETE_ICON.toString());
    }

    public static ImageIcon getCloseIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_CLOSE_ICON.toString()) : new ImageIcon(BLACK_CLOSE_ICON.toString());
    }

    public static ImageIcon getEditProfileIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_EDIT_PROFILE_ICON.toString()) : new ImageIcon(BLACK_EDIT_PROFILE_ICON.toString());
    }

    public static ImageIcon getSignInIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_SIGN_IN_ICON.toString()) : new ImageIcon(BLACK_SIGN_IN_ICON.toString());
    }

    public static ImageIcon getSignOutIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_SIGN_OUT_ICON.toString()) : new ImageIcon(BLACK_SIGN_OUT_ICON.toString());
    }

    public static ImageIcon getMenuIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_MENU_ICON.toString()) : new ImageIcon(BLACK_MENU_ICON.toString());
    }

    public static ImageIcon getSunMoonIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_SUN_MOON_ICON.toString()) : new ImageIcon(BLACK_SUN_MOON_ICON.toString());
    }

    public static ImageIcon getSunIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_SUN_ICON.toString()) : new ImageIcon(BLACK_SUN_ICON.toString());
    }

    public static ImageIcon getMoonIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_MOON_ICON.toString()) : new ImageIcon(BLACK_SUN_ICON.toString());
    }

    public static ImageIcon getPlusIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_PLUS_ICON.toString()) : new ImageIcon(BLACK_PLUS_ICON.toString());
    }

    public static ImageIcon getSwitchIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_SWITCH_ICON.toString()) : new ImageIcon(BLACK_SWITCH_ICON.toString());
    }

    public static ImageIcon getTrashIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_TRASH_ICON.toString()) : new ImageIcon(BLACK_TRASH_ICON.toString());
    }

    public static ImageIcon getUpIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_UP_ICON.toString()) : new ImageIcon(BLACK_UP_ICON.toString());
    }

    public static ImageIcon getDownIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_DOWN_ICON.toString()) : new ImageIcon(BLACK_DOWN_ICON.toString());
    }

    public static ImageIcon getRightIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_RIGHT_ICON.toString()) : new ImageIcon(BLACK_RIGHT_ICON.toString());
    }

    public static ImageIcon getCarIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_CAR_ICON.toString()) : new ImageIcon(BLACK_CAR_ICON.toString());
    }

    public static ImageIcon getBikeIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_BIKE_ICON.toString()) : new ImageIcon(BLACK_BIKE_ICON.toString());
    }

    public static ImageIcon getTransmissionIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_MANUAL_TRANSMISSION_ICON.toString()) : new ImageIcon(BLACK_MANUAL_TRANSMISSION_ICON.toString());
    }

    public static ImageIcon getSpeedometerIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_SPEEDOMETER_ICON.toString()) : new ImageIcon(BLACK_SPEEDOMETER_ICON.toString());
    }

    public static ImageIcon getSeatIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_CAR_SEAT_ICON.toString()) : new ImageIcon(BLACK_CAR_SEAT_ICON.toString());
    }

    public static ImageIcon getChassisIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_CHASSIS_ICON.toString()) : new ImageIcon(BLACK_CHASSIS_ICON.toString());
    }

    public static ImageIcon getEngineIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_ENGINE_ICON.toString()) : new ImageIcon(BLACK_ENGINE_ICON.toString());
    }

    public static ImageIcon getFuelGaugeIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_FUEL_GAUGE_ICON.toString()) : new ImageIcon(BLACK_FUEL_GAUGE_ICON.toString());
    }

    public static ImageIcon getGasIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_GAS_ICON.toString()) : new ImageIcon(BLACK_GAS_ICON.toString());
    }

    public static ImageIcon getHorseIcon() {
        return Theme.isDarkMode()? new ImageIcon(WHITE_HORSE_ICON.toString()) : new ImageIcon(BLACK_HORSE_ICON.toString());
    }
}
