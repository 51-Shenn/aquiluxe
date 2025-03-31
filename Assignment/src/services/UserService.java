package services;

import database.UserDAO;
import datamodels.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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

    public static boolean validateForgotPasswordDetails(String email, String phone, JLabel emailValidationLabel, JLabel phoneValidationLabel) {
        if (email.isEmpty()) {
            emailValidationLabel.setText("Username / Email Address cannot be blank");
            return false;
        } else if (phone.isEmpty()) {
            phoneValidationLabel.setText("Please enter your phone number.");
            return false;
        } 

        UserDAO userDAO = new UserDAO();
        User userAttempt = userDAO.authenticateUserForgotPassword(email, phone);
        if (userAttempt != null) 
            return true;

        emailValidationLabel.setText("Wrong email address or phone number.");
        phoneValidationLabel.setText("Wrong email address or phone number.");
        return false;
    }

    public static boolean validateForgotPasswordDetails(String email, String phone, char[] password,
            char[] confirmPassword, JLabel passwordValidationLabel, JLabel confirmPasswordValidationLabel) {
        boolean isValidPassword = passwordValidation(password, confirmPassword, passwordValidationLabel, confirmPasswordValidationLabel);
        String userPassword = new String(password);

        if (isValidPassword) {
            UserDAO userDAO = new UserDAO();
            User userAttempt = userDAO.authenticateUserForgotPassword(email, phone);
            if (userAttempt != null) {
                userDAO.updateUserColumnValue(userAttempt.getUserId(), "password", userPassword);
                User.setUsers(userDAO.getAllUsers());

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

        UserDAO userDAO = new UserDAO();
        User userAttempt = userDAO.authenticateUser(email, userPassword);
        if (userAttempt != null) 
            return true;

        emailValidationLabel.setText("Wrong email address or password.");
        passwordValidationLabel.setText("Wrong email address or password.");
        return false;
    }

    public static boolean validateUpdateProfileDetails(User user, String fullName, String username, String email,
            String phoneNumber, String drivingLicense,
            JLabel fullNameValidationLabel, JLabel usernameValidationLabel, JLabel emailValidationLabel,
            JLabel phoneNumberValidationLabel, JLabel drivingLicenseValidationLabel) {

        boolean isValidFullName = fullNameValidator(user, fullName, fullNameValidationLabel);
        boolean isValidUsername = usernameValidator(user, username, usernameValidationLabel);
        boolean isValidEmailAddress = emailAddressValidator(user, email, emailValidationLabel);
        boolean isValidPhoneNumber = phoneNumberValidator(user, phoneNumber, phoneNumberValidationLabel);

        if (isValidFullName && isValidUsername && isValidEmailAddress && isValidPhoneNumber) {
            UserDAO userDAO = new UserDAO();
            fullName = capitalizeFullName(fullName);
            userDAO.updateUserColumnValue(user.getUserId(), "full_name", fullName);
            userDAO.updateUserColumnValue(user.getUserId(), "username", username);
            userDAO.updateUserColumnValue(user.getUserId(), "user_email", email);
            userDAO.updateUserColumnValue(user.getUserId(), "phone_number", phoneNumber);

            User.setUsers(userDAO.getAllUsers());
            return true;
        } else
            return false;
    }

    public static User signInUser(String email, char[] password) {
        String userPassword = new String(password);

        return new UserDAO().authenticateUser(email, userPassword);
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
        label.setText("‎");
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
        return new UserDAO().usernameExists(username);
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
            if(isEmailAddressTaken(email)) {
                label.setText("This email address has been taken!");
                return false;
            }

            label.setText("‎");
            return true;
        } 
        else {
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
        } 
        else if (matcher.matches()) {
            if(isEmailAddressTaken(email)) {
                label.setText("This email address has been taken!");
                return false;
            }
            
            label.setText("‎");
            return true;
        } 
        else {
            label.setText("Invalid email format");
            return false;
        }
    }

    private static boolean isEmailAddressTaken(String email) {
        return new UserDAO().emailExists(email);
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
        } 
        else if (matcher.matches()) {
            if (isPhoneNumberTaken(phone)) {
                label.setText("This phone number has been taken!");
                return false;
            }

            label.setText("‎");
            return true;
        } 
        else {
            label.setText("Invalid phone number");
            return false;
        }
    }

    private static boolean isPhoneNumberTaken(String phoneNumber) {
        return new UserDAO().phoneNumberExists(phoneNumber);
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

        UserDAO userDAO = new UserDAO();
        userDAO.addUser(fullName, gender, phone, email, username, userPassword, "Customer");
        User.setUsers(userDAO.getAllUsers());

        System.out.println(User.getUsers());
    }

    public static User loadCurrentUserFromFile(File accountsFile) {
        UserDAO userDAO = new UserDAO();

        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
            String line = reader.readLine();
            return userDAO.getUserById(Integer.parseInt(line.trim()));
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

    public static String loadThemeFromFile(File themeFile) {
        String theme = "Light";

        try (BufferedReader reader = new BufferedReader(new FileReader(themeFile))) {
            theme = reader.readLine();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Could not locate file location: " + themeFile);
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
}