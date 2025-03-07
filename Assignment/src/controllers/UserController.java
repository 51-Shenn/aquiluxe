package controllers;

import services.UserService;

public class UserController {

    // overloaded method for SignUpPage
    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone);
    }
    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone, char[] password, char[] confirmPassword) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone, password, confirmPassword);
    }

    // overloaded methods for ForgotPasswordPage
    public static boolean passForgotPasswordDetails(String email, String phone) {
        return UserService.validateForgotPasswordDetails(email, phone);
    }
    public static boolean passForgotPasswordDetails(char[] password, char[] confirmPassword) {
        return UserService.validateForgotPasswordDetails(password, confirmPassword);
    }

    // method calling for SignInPage
    public static boolean passSignInDetails(String email, char[] password) {
        return UserService.validateSignInDetails(email, password);
    }

}
