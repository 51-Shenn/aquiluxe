package controllers;

import java.io.File;

import javax.swing.JLabel;

import datamodels.User;
import services.UserService;

public class UserController {

    // method overloading for SignUpPage
    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone,
        JLabel fullNameValidationLabel, JLabel genderValidationLabel, JLabel emailValidationLabel, JLabel phoneValidationLabel) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone, fullNameValidationLabel, genderValidationLabel, emailValidationLabel, phoneValidationLabel);
    }
    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone, char[] password, char[] confirmPassword,
        JLabel fullNameValidationLabel, JLabel genderValidationLabel, JLabel emailValidationLabel, JLabel phoneValidationLabel, JLabel passwordValidationLabel, JLabel confirmPasswordValidationLabel) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone, password, confirmPassword, fullNameValidationLabel, genderValidationLabel, emailValidationLabel, phoneValidationLabel, passwordValidationLabel, confirmPasswordValidationLabel);
    }

    // method overloading for ForgotPasswordPage
    public static boolean passForgotPasswordDetails(String email, String phone, JLabel emailValidationLabel, JLabel phoneValidationLabel) {
        return UserService.validateForgotPasswordDetails(email, phone, emailValidationLabel, phoneValidationLabel);
    }
    public static boolean passForgotPasswordDetails(String email, String phone, char[] password, char[] confirmPassword, JLabel passwordValidationLabel, JLabel confirmPasswordValidationLabel) {
        return UserService.validateForgotPasswordDetails(email, phone, password, confirmPassword, passwordValidationLabel, confirmPasswordValidationLabel);
    }

    // method overloading for SignInPage
    public static boolean passSignInDetails(String email, char[] password, JLabel emailValidationLabel, JLabel passwordValidationLabel) {
        return UserService.validateSignInDetails(email, password, emailValidationLabel, passwordValidationLabel);
    }
    public static User passSignInDetails(String email, char[] password) {
        return UserService.signInUser(email, password);
    }

    public static boolean passUpdateProfileDetails(User user, String fullName, String username, String  email, String  phoneNumber, String  drivingLicense, JLabel fullNameValidationLabel, JLabel usernameValidationLabel, JLabel emailValidationLabel, JLabel phoneNumberValidationLabel, JLabel drivingLicenseValidationLabel) {
        return UserService.validateUpdateProfileDetails(user, fullName, username, email, phoneNumber, drivingLicense, fullNameValidationLabel, usernameValidationLabel, emailValidationLabel, phoneNumberValidationLabel, drivingLicenseValidationLabel);
    }

    public static User loadCurrentUser(File accountsFile) {
        return UserService.loadCurrentUserFromFile(accountsFile);
    }

    public static int[] loadExistingUserInFile(int[] userIDArr, File accountsFile) {
        return UserService.loadUserIDFromFile(userIDArr, accountsFile);
    }

    public static void switchToAccount(User user, File accountsFile) {
        UserService.updateUserAccountsFile(user, accountsFile);
    }

    public static void removeUserFromFile(int id, File accountsFile) {
        UserService.removeUserFromAccountsFile(id, accountsFile);
    }

    public static String loadTheme(File themeFile) {
        return UserService.loadThemeFromFile(themeFile);
    }

    public static void useTheme(String newTheme, File themFile) {
        UserService.applyNewTheme(newTheme, themFile);
    }
}
