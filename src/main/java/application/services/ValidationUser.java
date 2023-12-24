package application.services;

import application.dataBase.Premetive_Objects.StringResultHandler;
import application.Main;

import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static application.Main.logger;

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
        } else if (!isExistPassword(email,password)) {
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
        DatabaseService dbs =new DatabaseService();
        try {
            String string =dbs.executeQuery("SELECT `password` FROM `user` WHERE `email` = '"+email+"'",new StringResultHandler());
            if (string.isEmpty()) return false;
            return true;
        } catch (SQLException e) {

            logger.severe("SQLException in isExistEmail");
        } finally {
            dbs.closeConnection();
        }
        return false;
    }
    public static boolean isExistPassword(String email,String password) {
        DatabaseService dbs = new DatabaseService();
        try {
            String string = dbs.executeQuery("SELECT `password` FROM `user` WHERE `email` = '" + email + "'", new StringResultHandler());
            if (string.equals(password)) return true;
            return false;
        } catch (SQLException e) {
            logger.severe("SQLException in isExistPassword");
        } finally {
            dbs.closeConnection();
        }
        return false;
    }
}
