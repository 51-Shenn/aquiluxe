package services;

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
        boolean isValidEmailAddress = emailAddressValidator(email);        // make sure it is not used by other users

        // phone number validation
        boolean isValidPhoneNumber = phoneNumberValidator(phone);

        // password validation
        boolean isValidPassword = passwordValidation(password, confirmPassword);

        return isValidFullName && isValidGender && isValidEmailAddress && isValidPhoneNumber && isValidPassword;
    }

    public static String capitalizeFullName(String fullName) {
        StringBuilder capitalizedName = new StringBuilder();
        String[] name = fullName.split(" ");

        for(String word : name) {
            char firstLetter = word.toUpperCase().charAt(0);
            String restOfName = word.substring(1).toLowerCase();
            String capitalizedWord = (firstLetter + restOfName).trim();
            capitalizedName.append(capitalizedWord).append(" ");
        }

        return capitalizedName.toString().trim();
    }

    public static boolean fullNameValidator(String fullName) {
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            System.out.println("Please enter your full name");
            return false;
        }
        else {
            fullName = capitalizeFullName(fullName);
            System.out.println(fullName);
            return true;
        }
    }

    public static boolean genderValidator(String gender) {
        if(gender == null) {
            System.out.println("Please select a gender");
            return false;
        }
        return true;
    }

    public static boolean emailAddressValidator(String email) {
        if(email.isEmpty())
            System.out.println("Please enter your email address");

        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean phoneNumberValidator(String phone) {
        if(phone.isEmpty())
            System.out.println("Please enter your phone number");

        phone = phone.replaceAll("[\\s-]", "");

        final String PHONE_REGEX = "^[1-9][0-9]{8,9}$";
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        System.out.println(phone);

        return matcher.matches();
    }

    public static boolean passwordValidation(char[] password, char[] confirmPassword) {
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
        else if(password1.length() <= 8) {
            System.out.println("Password must contain at least 8 characters.");
            return false;
        }
        else if(!password1.equals(password2)) {
            System.out.println("Password not match!");
            return false;
        }
        return true;
    }
}