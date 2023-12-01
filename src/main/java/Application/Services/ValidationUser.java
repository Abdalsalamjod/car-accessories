package Application.Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUser {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static final int VALID = 0;
    public static final int NULL_EMAIL = 1;
    public static final int INVALID_EMAIL = 2;
    public static final int EMAIL_NOT_EXIST = 3;
    public static final int INVALID_PASSWORD = 4;
    public static final int INVALID = 5;
    public static final int OTHER_ERROR = -1;

    public static int validation(String email, String password) {
        if (email == null || email.isEmpty()) {
            return NULL_EMAIL;
        } else if (!isValidEmail(email)) {
            return INVALID_EMAIL;
        } else if (!isExistEmail(email)) {
            return EMAIL_NOT_EXIST;
        } else if (!isExistPassword(password)) {
            return INVALID_PASSWORD;
        }
        return VALID;
    }
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isExistEmail(String email) {
        // check data in DB
        return true;
    }
    public static boolean isExistPassword(String password) {
        // check data in DB
        return true;
    }
}
