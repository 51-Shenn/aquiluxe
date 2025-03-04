package services;

public class UserService {

    public static boolean validateNewUserDetails(String fullName, String gender, String email, String phone) {
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            System.out.println("Please enter your full name");
            return false;
        }
        if(gender == null) {
            System.out.println("Please select a gender");
            return false;
        }
        if(email.isEmpty()) {
            System.out.println("Please enter your email address");
            return false;
        }
        if(phone.isEmpty()) {
            System.out.println("Please enter your phone number");
            return false;
        }
        return true;
    }
}