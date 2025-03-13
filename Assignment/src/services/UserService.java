package services;

import datamodels.User;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

public class UserService {

    public static boolean validateNewUserDetails(String fullName, String gender, String email, String phone,
            JLabel fullNameValidationLabel, JLabel genderValidationLabel, JLabel emailValidationLabel,
            JLabel phoneValidationLabel) {

        // full name validation
        boolean isValidFullName = fullNameValidator(fullName, fullNameValidationLabel);

        // gender validation
        boolean isValidGender = genderValidator(gender, genderValidationLabel);

        // email validation
        boolean isValidEmailAddress = emailAddressValidator(email, emailValidationLabel);

        // phone number validation
        boolean isValidPhoneNumber = phoneNumberValidator(phone, phoneValidationLabel);

        return isValidFullName && isValidGender && isValidEmailAddress && isValidPhoneNumber;
    }

    public static boolean validateNewUserDetails(String fullName, String gender, String email, String phone,
            char[] password, char[] confirmPassword,
            JLabel fullNameValidationLabel, JLabel genderValidationLabel, JLabel emailValidationLabel,
            JLabel phoneValidationLabel, JLabel passwordValidationLabel, JLabel confirmPasswordValidationLabel) {

        // full name validation
        boolean isValidFullName = fullNameValidator(fullName, fullNameValidationLabel);

        // gender validation
        boolean isValidGender = genderValidator(gender, genderValidationLabel);

        // email validation
        boolean isValidEmailAddress = emailAddressValidator(email, emailValidationLabel);

        // phone number validation
        boolean isValidPhoneNumber = phoneNumberValidator(phone, phoneValidationLabel);

        // password validation
        boolean isValidPassword = passwordValidation(password, confirmPassword, passwordValidationLabel,
                confirmPasswordValidationLabel);

        if (isValidFullName && isValidGender && isValidEmailAddress && isValidPhoneNumber && isValidPassword) {
            fullName = capitalizeFullName(fullName);
            createNewUserAccount(fullName, gender, email, phone, password);
        }

        return isValidFullName && isValidGender && isValidEmailAddress && isValidPhoneNumber && isValidPassword;
    }

    public static boolean validateForgotPasswordDetails(String email, String phone, JLabel emailValidationLabel,
            JLabel phoneValidationLabel) {
        if (email.isEmpty()) {
            emailValidationLabel.setText("Username / Email Address cannot be blank");
            return false;
        } else if (phone.isEmpty()) {
            phoneValidationLabel.setText("Please enter your phone number.");
            return false;
        } else {
            for (User user : User.users.values()) {
                if ((user.getUserEmail().equals(email) || user.getUsername().equals(email))
                        && user.getPhoneNumber().equals(phone))
                    return true;
            }
        }

        emailValidationLabel.setText("Wrong email address or phone number.");
        phoneValidationLabel.setText("Wrong email address or phone number.");
        return false;
    }

    public static boolean validateForgotPasswordDetails(String email, String phone, char[] password,
            char[] confirmPassword, JLabel passwordValidationLabel, JLabel confirmPasswordValidationLabel) {
        boolean isValidPassword = passwordValidation(password, confirmPassword, passwordValidationLabel,
                confirmPasswordValidationLabel);
        String userPassword = new String(password);
        if (isValidPassword) {
            for (User user : User.users.values()) {
                if ((user.getUserEmail().equals(email)
                        || user.getUsername().equals(email) && user.getPhoneNumber().equals(phone))) {
                    user.setPassword(userPassword);
                    User.displayUsers();
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validateSignInDetails(String email, char[] password, JLabel emailValidationLabel,
            JLabel passwordValidationLabel) {
        String userPassword = new String(password);

        if (email.isEmpty()) {
            emailValidationLabel.setText("Username / Email Address cannot be blank");
            return false;
        } else {
            emailValidationLabel.setText("");
        }

        if (userPassword.isEmpty()) {
            passwordValidationLabel.setText("Please enter your password.");
            return false;
        } else {
            passwordValidationLabel.setText("");
        }

        for (User user : User.users.values()) {
            if ((user.getUserEmail().equals(email) || user.getUsername().equals(email))
                    && user.getPassword().equals(userPassword)) {
                System.out.println("Login successful!");
                return true;
            }
        }

        emailValidationLabel.setText("Wrong email address or password.");
        passwordValidationLabel.setText("Wrong email address or password.");
        return false;
    }

    private static String capitalizeFullName(String fullName) {
        StringBuilder capitalizedName = new StringBuilder();
        String[] name = fullName.trim().split("\\s+");

        for (String word : name) {
            char firstLetter = word.toUpperCase().charAt(0);
            String restOfName = word.substring(1).toLowerCase();
            String capitalizedWord = (firstLetter + restOfName).trim();
            capitalizedName.append(capitalizedWord).append(" ");
        }

        return capitalizedName.toString().trim();
    }

    private static boolean fullNameValidator(String fullName, JLabel label) {
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            label.setText("Please enter your full name");
            return false;
        }
        if (!fullName.matches("[a-zA-Z ]+")) {
            label.setText("Name must contain only letters and spaces");
            return false;
        }
        label.setText("");
        return true;
    }

    private static String generateUsername(String fullName) {
        Random random = new Random();
        String baseUsername = fullName.toLowerCase().replaceAll("\\s+", "");
        String newUsername;

        do {
            int randomNum = random.nextInt(9000) + 1000; // 1000 - 9999
            newUsername = baseUsername + randomNum;
        } while (isUsernameTaken(newUsername));

        return newUsername;
    }

    private static boolean isUsernameTaken(String username) {
        for (User user : User.users.values()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static boolean genderValidator(String gender, JLabel label) {
        if (gender == null) {
            label.setText("Please select a gender");
            return false;
        } else {
            label.setText("");
            return true;
        }
    }

    private static boolean emailAddressValidator(String email, JLabel label) {
        if (email.isEmpty()) {
            label.setText("Please enter your email address");
            return false;
        }

        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            for (User user : User.users.values()) {
                if (user.getUserEmail().equals(email)) {
                    label.setText("This email address has been taken!");
                    return false;
                }
            }
            label.setText("");
            return true;
        } else {
            label.setText("Invalid email format");
            return false;
        }
    }

    private static boolean phoneNumberValidator(String phone, JLabel label) {
        if (phone.isEmpty()) {
            label.setText("Please enter your phone number");
            return false;
        }

        phone = phone.replaceAll("[\\s-]", "");

        final String PHONE_REGEX = "^[1-9][0-9]{8,9}$";
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            for (User user : User.users.values()) {
                if (user.getPhoneNumber().equals(phone)) {
                    label.setText("This phone number has been taken!");
                    return false;
                }
            }
            label.setText("");
            return true;
        } else {
            label.setText("Invalid phone number");
            return false;
        }
    }

    private static boolean passwordValidation(char[] password, char[] confirmPassword, JLabel label1, JLabel label2) {
        String password1 = new String(password);
        String password2 = new String(confirmPassword);

        if (password1.isEmpty()) {
            label1.setText("Please enter your password");
            return false;
        } else if (password1.contains(" ")) {
            label1.setText("Password cannot contain spaces");
            return false;
        } else if (password1.length() < 8) {
            label1.setText("Password must contain at least 8 characters.");
            return false;
        } else if (!password1.equals(password2)) {
            label1.setText("");
            label2.setText("Password not match!");
            return false;
        } else {
            label1.setText("");
            label2.setText("");
            return true;
        }
    }

    private static void createNewUserAccount(String fullName, String gender, String email, String phone,
            char[] password) {
        String userPassword = new String(password);
        new User(fullName, gender, email, phone, generateUsername(fullName), userPassword);

        User.displayUsers();
        System.out.println(User.users);
    }
}