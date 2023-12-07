package application.services;

import application.entities.User;

public class LogOut {
        public User performLogout(User user) {

           DatabaseService dbs =new DatabaseService();
           user.setSignInStatus(false);
            try {
                dbs.updateObject(user,"user","email");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            user =null;
           return user;
        }
}
