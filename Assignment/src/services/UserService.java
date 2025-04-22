package services;

import database.UserDAO;
import datamodels.Admin;
import datamodels.Customer;
import datamodels.User;
import gui.OverflowMenu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
            phone = phone.replaceAll("[\\s-]", "");

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
        }

        User userAttempt = UserDAO.authenticateUserForgotPassword(email, phone);
        if (userAttempt != null)
            return true;

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
            User userAttempt = UserDAO.authenticateUserForgotPassword(email, phone);
            if (userAttempt != null) {
                UserDAO.updateUserColumnValue(userAttempt.getUserId(), "password", userPassword);

                return true;
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
            emailValidationLabel.setText("‎");
        }

        if (userPassword.isEmpty()) {
            passwordValidationLabel.setText("Please enter your password.");
            return false;
        } else {
            passwordValidationLabel.setText("‎");
        }

        User userAttempt = UserDAO.authenticateUser(email, userPassword);
        if (userAttempt != null)
            return true;

        emailValidationLabel.setText("Wrong email address or password.");
        passwordValidationLabel.setText("Wrong email address or password.");
        return false;
    }

    public static boolean validateUpdateProfileDetails(User user, String fullName, String username, String email,
            String phoneNumber, String identityCard,
            JLabel fullNameValidationLabel, JLabel usernameValidationLabel, JLabel emailValidationLabel,
            JLabel phoneNumberValidationLabel, JLabel icValidationLabel) {

        boolean isValidFullName = fullNameValidator(user, fullName, fullNameValidationLabel);
        boolean isValidUsername = usernameValidator(user, username, usernameValidationLabel);
        boolean isValidEmailAddress = emailAddressValidator(user, email, emailValidationLabel);
        boolean isValidPhoneNumber = phoneNumberValidator(user, phoneNumber, phoneNumberValidationLabel);

        if (user.getUserType().equals("Customer")) {
            boolean isValidLicense = identityCardValidator(user, identityCard, icValidationLabel);

            if (isValidFullName && isValidUsername && isValidEmailAddress && isValidPhoneNumber && isValidLicense) {
                fullName = capitalizeFullName(fullName);
                UserDAO.updateUserColumnValue(user.getUserId(), "full_name", fullName);
                UserDAO.updateUserColumnValue(user.getUserId(), "username", username);
                UserDAO.updateUserColumnValue(user.getUserId(), "user_email", email);
                UserDAO.updateUserColumnValue(user.getUserId(), "phone_number", phoneNumber);

                return true;
            }
        } else if (user.getUserType().equals("Admin")) {
            if (isValidFullName && isValidUsername && isValidEmailAddress && isValidPhoneNumber) {
                fullName = capitalizeFullName(fullName);
                UserDAO.updateUserColumnValue(user.getUserId(), "full_name", fullName);
                UserDAO.updateUserColumnValue(user.getUserId(), "username", username);
                UserDAO.updateUserColumnValue(user.getUserId(), "user_email", email);
                UserDAO.updateUserColumnValue(user.getUserId(), "phone_number", phoneNumber);

                return true;
            }
        }

        return false;
    }

    public static User signInUser(String email, char[] password) {
        String userPassword = new String(password);

        return UserDAO.authenticateUser(email, userPassword);
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

    private static boolean fullNameValidator(User user, String fullName, JLabel label) {
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            label.setText("Please enter your full name");
            return false;
        }

        if (user.getFullName().equals(fullName) && fullName.matches("[a-zA-Z ]+")) {
            label.setText("‎");
            return true;
        } else if (!fullName.matches("[a-zA-Z ]+")) {
            label.setText("Name must contain only letters and spaces");
            return false;
        }
        label.setText("‎");
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

    private static boolean usernameValidator(User user, String username, JLabel label) {
        if (user.getUsername().equals(username)) {
            label.setText("‎");
            return true;
        }

        if (username.isEmpty()) {
            label.setText("Please enter a username");
            return false;
        }

        if (!username.matches("^[a-z][a-z0-9_]{2,19}$")) {
            label.setText("3-20 chars: letters, numbers, _");
            return false;
        }

        if (isUsernameTaken(username)) {
            label.setText("Username is already taken");
            return false;
        }

        label.setText("‎");
        return true;
    }

    private static boolean isUsernameTaken(String username) {
        return UserDAO.usernameExists(username);
    }

    private static boolean genderValidator(String gender, JLabel label) {
        if (gender == null) {
            label.setText("Please select a gender");
            return false;
        } else {
            label.setText("‎");
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
            if (isEmailAddressTaken(email)) {
                label.setText("This email address has been taken!");
                return false;
            }

            label.setText("‎");
            return true;
        } else {
            label.setText("Invalid email format");
            return false;
        }
    }

    private static boolean emailAddressValidator(User currentUser, String email, JLabel label) {
        if (email.isEmpty()) {
            label.setText("Please enter your email address");
            return false;
        }

        final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if (currentUser.getUserEmail().equals(email) && matcher.matches()) {
            label.setText("‎");
            return true;
        } else if (matcher.matches()) {
            if (isEmailAddressTaken(email)) {
                label.setText("This email address has been taken!");
                return false;
            }

            label.setText("‎");
            return true;
        } else {
            label.setText("Invalid email format");
            return false;
        }
    }

    private static boolean isEmailAddressTaken(String email) {
        return UserDAO.emailExists(email);
    }

    private static boolean phoneNumberValidator(String phone, JLabel label) {
        if (phone.isEmpty()) {
            label.setText("Please enter your phone number");
            return false;
        }

        phone = phone.replaceAll("[\\s-]", "");

        final String PHONE_REGEX = "^(1[0-9]|[3-9])[0-9]{7,8}$";
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            if (isPhoneNumberTaken(phone)) {
                label.setText("This phone number has been taken!");
                return false;
            }

            label.setText("‎");
            return true;
        } else {
            label.setText("Invalid phone number");
            return false;
        }
    }

    private static boolean phoneNumberValidator(User currentUser, String phone, JLabel label) {
        if (phone.isEmpty()) {
            label.setText("Please enter your phone number");
            return false;
        }

        final String PHONE_REGEX = "^(1[0-9]|[3-9])[0-9]{7,8}$";
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);

        if (currentUser.getPhoneNumber().equals(phone) && matcher.matches()) {
            label.setText("‎");
            return true;
        } else if (matcher.matches()) {
            if (isPhoneNumberTaken(phone)) {
                label.setText("This phone number has been taken!");
                return false;
            }

            label.setText("‎");
            return true;
        } else {
            label.setText("Invalid phone number");
            return false;
        }
    }

    private static boolean isPhoneNumberTaken(String phoneNumber) {
        return UserDAO.phoneNumberExists(phoneNumber);
    }

    private static boolean identityCardValidator(User user, String identityCard, JLabel identityCardValidationLabel) {

        if (identityCard.equals(OverflowMenu.getGeneratedUUID())) {
            UserDAO.updateUserColumnValue(user.getUserId(), "usertype", "Admin");
            String position = capitalizeFullName(OverflowMenu.getPosition());
            UserDAO.addAdminPosition(user, position);
            return true;
        } else if (!identityCard.isEmpty()) {
            identityCard = identityCard.replaceAll("-", "").trim();

            boolean isValidIdentityCard = identityCardValidator(user, identityCard);
            if (isValidIdentityCard) {
                UserDAO.addCustomerDetails(user, "", identityCard);
                return true;
            } else {
                identityCardValidationLabel.setText("Invalid identity card");
                return false;
            }
        } else if (identityCard.isEmpty()) {
            return true;
        } else {
            identityCardValidationLabel.setText("Invalid identity card number");
            return false;
        }
    }

    private static boolean identityCardValidator(User user, String identityCard) {
        LocalDate date = LocalDate.now();
        String birthYear;
        String birthMonth;
        String birthDate;

        String birthDay;
        String birthPlace;
        String serialNumber; // odd for male, even for female

        if (identityCard.length() != 12)
            return false;

        // check every character is digit
        for (int i = 0; i < identityCard.length(); i++)
            if (!Character.isDigit(identityCard.charAt(i)))
                return false;

        birthDay = identityCard.substring(0, 6);
        birthPlace = identityCard.substring(6, 8);
        serialNumber = identityCard.substring(8, 12);

        birthYear = birthDay.substring(0, 2);
        birthMonth = birthDay.substring(2, 4);
        birthDate = birthDay.substring(4, 6);

        /*
         * convert birthYear to int, then calculate
         * example:
         * 2025 % 100 = 25
         * 06 <= 25 -> 2006
         * 98 >= 25 -> 1998
         */

        int yearInt = Integer.parseInt(birthYear);
        int monthInt = Integer.parseInt(birthMonth);
        int dateInt = Integer.parseInt(birthDate); // for checking month date

        if (monthInt < 1 || monthInt > 12)
            return false;

        switch (monthInt) {
            case 2 -> {
                if (yearInt % 4 == 0) {
                    if (dateInt < 1 || dateInt > 29)
                        return false;
                } else {
                    if (dateInt < 1 || dateInt > 28)
                        return false;
                }
            }

            case 1, 3, 5, 7, 8, 10, 12 -> {
                if (dateInt < 1 || dateInt > 31)
                    return false;
            }

            case 4, 6, 9, 11 -> {
                if (dateInt < 1 || dateInt > 30)
                    return false;
            }
        }

        int placeInt = Integer.parseInt(birthPlace);
        if (placeInt < 1 || placeInt > 59 || (placeInt >= 17 && placeInt <= 20))
            return false;

        String year = yearInt <= date.getYear() % 100 ? String.format("20" + birthYear)
                : String.format("19" + birthYear);

        // check if more than 17 years old
        int age = date.getYear() - Integer.parseInt(year);
        if (age <= 17)
            return false;

        int genderIndicator = Character.getNumericValue(serialNumber.charAt(serialNumber.length() - 1));
        if (genderIndicator % 2 == 0) {
            if (!user.getGender().equals("Female"))
                return false;
        } else {
            if (!user.getGender().equals("Male"))
                return false;
        }

        return true;
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
            label1.setText("‎");
            label2.setText("Password not match!");
            return false;
        } else {
            label1.setText("‎");
            label2.setText("‎");
            return true;
        }
    }

    private static void createNewUserAccount(String fullName, String gender, String email, String phone,
            char[] password) {
        String userPassword = new String(password);
        String username = generateUsername(fullName);

        UserDAO.addUser(fullName, gender, phone, email, username, userPassword, "Customer");
    }

    public static User loadCurrentUserFromFile(File accountsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
            String line = reader.readLine();
            if (line == null)
                return new User();
            return UserDAO.getUserById(Integer.parseInt(line.trim()));
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + accountsFile);
        }

        return new User();
    }

    public static int[] loadUserIDFromFile(int[] userAccountsID, File accountsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < userAccountsID.length) {
                try {
                    userAccountsID[index] = Integer.parseInt(line.trim());
                    index++;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid number format in file: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Could not locate file: " + accountsFile);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + accountsFile);
        }

        return userAccountsID;
    }

    public static void updateUserAccountsFile(User user, File accountsFile) {
        ArrayList<Integer> accountIDs = new ArrayList<>();
        if (accountsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        accountIDs.add(Integer.valueOf(line.trim()));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid number format in file: " + line);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading file: " + accountsFile);
            }
        }

        int currentUserId = user.getUserId();

        for (int i = 1; i < accountIDs.size(); i++) {
            if (accountIDs.get(i) == currentUserId) {
                accountIDs.remove(i);
                break;
            }
        }

        if (!accountIDs.isEmpty() && accountIDs.get(0) != currentUserId) {
            accountIDs.removeIf(id -> id == currentUserId);
            accountIDs.add(0, currentUserId);
        } else if (accountIDs.isEmpty()) {
            accountIDs.add(currentUserId);
        }

        // only 4 accounts
        while (accountIDs.size() > 4) {
            accountIDs.remove(accountIDs.size() - 1);
        }

        // write back to file
        try (FileWriter writer = new FileWriter(accountsFile)) {
            for (int i = 0; i < accountIDs.size(); i++) {
                writer.write(accountIDs.get(i) + "\n");
            }
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "Error saving accounts file");
        }
    }

    public static void removeUserFromAccountsFile(int userId, File accountsFile) {
        ArrayList<Integer> accountIDs = new ArrayList<>();

        if (accountsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        int id = Integer.parseInt(line.trim());
                        // add to array list if not the one that is signed out or deleted
                        if (id != userId) {
                            accountIDs.add(id);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid number format in file: " + line);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading file: " + accountsFile);
            }
        }

        try (FileWriter writer = new FileWriter(accountsFile)) {
            for (int id : accountIDs) {
                writer.write(id + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating accounts file");
        }
    }

    public static void deleteUser(User user) {
        if (user.getUserType().equals("Customer"))
            UserDAO.deleteCustomer(user);
        else if (user.getUserType().equals("Admin"))
            UserDAO.deleteAdmin(user);

        UserDAO.deleteUser(user);
    }

    public static String loadThemeFromFile(File themeFile) {
        String theme = "Light";

        try (BufferedReader reader = new BufferedReader(new FileReader(themeFile))) {
            theme = reader.readLine();
        } catch (FileNotFoundException e) {
            // write to file if does not exist
            try (FileWriter writer = new FileWriter(themeFile)) {
                writer.write("Light");
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null, "Could not locate file location: " + themeFile);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "Could not write file: " + themeFile);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not write file: " + themeFile);
        }

        return theme;
    }

    public static void applyNewTheme(String newTheme, File themeFile) {
        try (FileWriter writer = new FileWriter(themeFile)) {
            writer.write(newTheme);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Could not locate file location: " + themeFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not write file: " + themeFile);
        }
    }

    public static String adminPasswordValidation(String password, JTextField uuidField) {
        final String MANAGER_PASSWORD = "Manager";
        final String EMPLOYEE_PASSWORD = "Employee";

        switch (password) {
            case MANAGER_PASSWORD -> {
                OverflowMenu.setPosition("MANAGER");
                return "MANAGER";
            }
            case EMPLOYEE_PASSWORD -> {
                OverflowMenu.setPosition("EMPLOYEE");
                return "EMPLOYEE";
            }
            default -> uuidField.setText("Invalid Password. Please try again");
        }

        return "";
    }

    public static User getUserByObject(User user) {
        return UserDAO.getUserById(user);
    }

    public static User getUserByObject(int userId) {
        return UserDAO.getUserById(userId);
    }

    public static Customer getCustomerByObject(User user) {
        return UserDAO.getCustomerById(user);
    }

    public static Admin getAdminByObject(User user) {
        return UserDAO.getAdminById(user);
    }
}