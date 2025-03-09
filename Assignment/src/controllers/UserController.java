package controllers;

import javax.swing.JLabel;

import services.UserService;

public class UserController {

    // overloaded method for SignUpPage
    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone,
        JLabel fullNameValidationLabel, JLabel genderValidationLabel, JLabel emailValidationLabel, JLabel phoneValidationLabel) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone, fullNameValidationLabel, genderValidationLabel, emailValidationLabel, phoneValidationLabel);
    }
    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone, char[] password, char[] confirmPassword,
        JLabel fullNameValidationLabel, JLabel genderValidationLabel, JLabel emailValidationLabel, JLabel phoneValidationLabel, JLabel passwordValidationLabel, JLabel confirmPasswordValidationLabel) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone, password, confirmPassword, fullNameValidationLabel, genderValidationLabel, emailValidationLabel, phoneValidationLabel, passwordValidationLabel, confirmPasswordValidationLabel);
    }

    // overloaded methods for ForgotPasswordPage
    public static boolean passForgotPasswordDetails(String email, String phone, JLabel emailValidationLabel, JLabel phoneValidationLabel) {
        return UserService.validateForgotPasswordDetails(email, phone, emailValidationLabel, phoneValidationLabel);
    }
    public static boolean passForgotPasswordDetails(String email, String phone, char[] password, char[] confirmPassword, JLabel passwordValidationLabel, JLabel confirmPasswordValidationLabel) {
        return UserService.validateForgotPasswordDetails(email, phone, password, confirmPassword, passwordValidationLabel, confirmPasswordValidationLabel);
    }

    // method calling for SignInPage
    public static boolean passSignInDetails(String email, char[] password, JLabel emailValidationLabel, JLabel passwordValidationLabel) {
        return UserService.validateSignInDetails(email, password, emailValidationLabel, passwordValidationLabel);
    }

}
