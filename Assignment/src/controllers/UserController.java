package controllers;

import services.UserService;

public class UserController {

    public static boolean passNewUserDetails(String fullName, String gender, String email, String phone) {
        return UserService.validateNewUserDetails(fullName, gender, email, phone);
    }
}
