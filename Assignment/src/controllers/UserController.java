package controllers;

import services.UserService;

public class UserController {

    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone, char[] password, char[] confirmPassword) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone, password, confirmPassword);
    }
}
