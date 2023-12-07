package application.services;

import application.dataBase.UserDefinedTypes.ProfileResultHandler;
import application.dataBase.UserDefinedTypes.UserResultHandler;
import application.entities.Admin;
import application.entities.Installer;
import application.entities.User;

public class SignIn {
    public String email;
    public String password;
    public String role;
    public int profileid;
    public boolean signedIn;
    public int validationStatus;
    public SignIn() {
        email=null;
        password=null;
        role=null;
        signedIn=false;
        validationStatus=-1;
        profileid=-1;
    }

    public SignIn(String email, String password, String role, boolean signedIn, int validationStatus) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.signedIn=signedIn;
        this.validationStatus=validationStatus;
        profileid=-1;
    }

    public User performLogIn() {
        if (validationStatus==0){

            DatabaseService dbs =new DatabaseService();
            User tempUser = null;
            try {
                tempUser = dbs.executeQuery("SELECT * FROM `user` WHERE `email`='"+this.email+"'", new UserResultHandler());
                tempUser.setProfile(dbs.executeQuery("SELECT * FROM `Profile` WHERE `profileId`='"+tempUser.getProfile()+"'", new ProfileResultHandler()));
                tempUser.setSignInStatus(true);
                dbs.updateObject(tempUser,"user","email");
            } catch ( Exception e) {
                throw new RuntimeException(e);
            }

            return switch ( tempUser.getRole() ) {
                case 'u' ->
                  new User(tempUser.getEmail(), tempUser.getPassword(), tempUser.getRole(), tempUser.isSignInStatus(), tempUser.getProfileObject());
                case 'a' ->
                  new Admin(tempUser.getEmail(), tempUser.getPassword(), tempUser.getRole(), tempUser.isSignInStatus(), tempUser.getProfileObject());
                case 'i' ->
                  new Installer(tempUser.getEmail(), tempUser.getPassword(), tempUser.getRole(), tempUser.isSignInStatus(), tempUser.getProfileObject());
                default -> null;
            };
        }
        else {
            this.signedIn=false;
            return null;
        }

    }

}
