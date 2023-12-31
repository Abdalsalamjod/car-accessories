package application.services;

import application.Main;
import application.entities.User;

public class LogOut {
    public static final String GOOD_BAY_MESSAGE = "Thank you to use our app, Have a nice Day ^_^";
    public static final String WARNING_MESSAGE = "Error: sth wrong happened, try again";

    public User performLogout(User user,DatabaseService dbs) {
           user.setSignInStatus(false);
            try {
                dbs.updateObject(user,"user","email");
                user =null;
            } catch (Exception e) {
                Main.logger.severe(WARNING_MESSAGE);
            }
           return user;
        }
}
