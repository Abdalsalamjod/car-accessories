package Application.Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public  boolean valid;
    public boolean hasAccount;
    public String email;
    public String password;
    public String message;



    public SignUp() {
        message="empty String, put if you see this messge in any case -> there an error in signup";
        hasAccount=false;
        valid=false;
    }
    public String generateMessage() {
        //maybe this code have a problem , becos i nedd database
        if (!isValidEmail(this.email)) {
            message = "Invalid email address\n";
        } else if (!isExistEmail(this.email)) {
            message = "Email address is already in use\n";
        } else {
            message = "Successful Registration, you are welcome\n";
        }
        return message;
    }


    public void validation(String email, String password) {
        valid= isValidEmail(email) && isExistEmail(email);
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isExistEmail(String email) {
         // check data in DB
        return false;
    }

    public void creatAccount(String email, String password) {
        if (valid){
            // this function should put data in DB
            this.hasAccount=true;
        }
    }


}
