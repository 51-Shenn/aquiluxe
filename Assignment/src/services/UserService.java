package services;

import datamodels.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    public static boolean validateNewUserDetails(String fullName, String gender, String email, String phone) {

        // full name validation
        boolean isValidFullName = fullNameValidator(fullName);

        // gender validation
        boolean isValidGender = genderValidator(gender);

        // email validation
        boolean isValidEmailAddress = emailAddressValidator(email);        // make sure it is not used by other users

        // phone number validation
        boolean isValidPhoneNumber = phoneNumberValidator(phone);

        return isValidFullName && isValidGender && isValidEmailAddress && isValidPhoneNumber;
    }

    public static boolean validateNewUserDetails(String fullName, String gender, String email, String phone, char[] password, char[] confirmPassword) {

        // full name validation
        boolean isValidFullName = fullNameValidator(fullName);

        // gender validation
        boolean isValidGender = genderValidator(gender);

        // email validation
        boolean isValidEmailAddress = emailAddressValidator(email);

        // phone number validation
        boolean isValidPhoneNumber = phoneNumberValidator(phone);

        // password validation
        boolean isValidPassword = passwordValidation(password, confirmPassword);

        if(isValidFullName && isValidGender && isValidEmailAddress && isValidPhoneNumber && isValidPassword) {
            fullName = capitalizeFullName(fullName);
            createNewUserAccount(fullName, gender, email, phone, password);
        }

        return isValidFullName && isValidGender && isValidEmailAddress && isValidPhoneNumber && isValidPassword;
    }

    public static boolean validateSignInDetails(String email, char[] password) {
        String userPassword = new String(password);

        if(email.isEmpty()) {
            System.out.println("Email address cannot be blank");
            return false;
        }
        else if(userPassword.isEmpty()) {
            System.out.println("Please enter your password.");
            return false;
        }
        else {
            for(User user : User.users.values()) {
                if(user.getUserEmail().equals(email) && user.getPassword().equals(userPassword)) {
                    System.out.println("Login successful!");
                    return true;
                }
                else {
                    System.out.println("Wrong email address or password.");
                    return false;
                }
            }
        }

        System.out.println("Email address not found.");
        return false;
    }

    private static String capitalizeFullName(String fullName) {
        StringBuilder capitalizedName = new StringBuilder();
        String[] name = fullName.trim().split("\\s+");

        for(String word : name) {
            char firstLetter = word.toUpperCase().charAt(0);
            String restOfName = word.substring(1).toLowerCase();
            String capitalizedWord = (firstLetter + restOfName).trim();
            capitalizedName.append(capitalizedWord).append(" ");
        }

        return capitalizedName.toString().trim();
    }

    private static boolean fullNameValidator(String fullName) {
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            System.out.println("Please enter your full name");
            return false;
        }
        else
            return true;
    }

    private static boolean genderValidator(String gender) {
        if(gender == null) {
            System.out.println("Please select a gender");
            return false;
        }
        return true;
    }

    private static boolean emailAddressValidator(String email) {
        if(email.isEmpty())
            System.out.println("Please enter your email address");

        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches()) {
            for(User user : User.users.values()) {
                if(user.getUserEmail().equals(email)) {
                    System.out.println("This email address has been taken!");
                    return false;
                }
            }
            return true;
        }
        else
            return false;
    }

    private static boolean phoneNumberValidator(String phone) {
        if(phone.isEmpty())
            System.out.println("Please enter your phone number");

        phone = phone.replaceAll("[\\s-]", "");

        final String PHONE_REGEX = "^[1-9][0-9]{8,9}$";
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);

        if(matcher.matches()) {
            for(User user : User.users.values()) {
                if(user.getPhoneNumber().equals(phone)) {
                    System.out.println("This phone number has been taken!");
                    return false;
                }
            }
            return true;
        }
        else
            return false;
    }

    private static boolean passwordValidation(char[] password, char[] confirmPassword) {
        String password1 = new String(password).trim();
        String password2 = new String(confirmPassword).trim();

        if(password1.isEmpty()) {
            System.out.println("Please enter your password");
            return false;
        }
        else if(password1.contains(" ")) {
            System.out.println("Password cannot contain spaces");
            return false;
        }
        else if(password1.length() < 8) {
            System.out.println("Password must contain at least 8 characters.");
            return false;
        }
        else if(!password1.equals(password2)) {
            System.out.println("Password not match!");
            return false;
        }
        return true;
    }

    private static void createNewUserAccount(String fullName, String gender, String email, String phone, char[] password) {
        String userPassword = new String(password);
        new User(fullName, gender, null, email, phone, userPassword);

        User.displayUsers();
    }
}