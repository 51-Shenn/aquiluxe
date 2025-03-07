package controllers;

import services.UserService;

public class UserController {

    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone);
    }

    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone, char[] password, char[] confirmPassword) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone, password, confirmPassword);
    }

    public static boolean passForgotPasswordDetails(String email, String phone) {
        return UserService.validateForgotPasswordDetails(email, phone);
    }

    public static boolean passForgotPasswordDetails(String email, String phone, char[] password, char[] confirmPassword) {
        return UserService.validateForgotPasswordDetails(email, phone, password, confirmPassword);
    }

    public static boolean passSignInDetails(String email, char[] password) {
        return UserService.validateSignInDetails(email, password);
    }

}
