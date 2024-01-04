package application.services;

import application.database.user_defined_types.ProfileResultHandler;
import application.database.user_defined_types.UserResultHandler;
import application.entities.Admin;
import application.entities.Installer;
import application.entities.User;

public class SignIn {
    private String email;
    private String password;
    private char role;
    private int profileid;
    private boolean signedIn;
    private int validationStatus;
    public SignIn() {
        email=null;
        password=null;
        role='\0';
        signedIn=false;
        validationStatus=-1;
        profileid=-1;
    }

    public SignIn(String email, String password, boolean signedIn, int validationStatus) {
        this.email=email;
        this.password=password;

        this.signedIn=signedIn;
        this.validationStatus=validationStatus;
        profileid=-1;
    }

    public User performLogIn(DatabaseService dbs) {
        if (validationStatus==0){

            User tempUser = null;

            try {
                tempUser = dbs.executeQuery("SELECT * FROM `user` WHERE `email`='"+this.email+"'", new UserResultHandler());
                tempUser.setProfile(dbs.executeQuery("SELECT * FROM `Profile` WHERE `profileId`='"+tempUser.getProfile()+"'", new ProfileResultHandler()));
                tempUser.setSignInStatus(true);
                dbs.updateObject(tempUser,"user","email");
            } catch ( Exception e) {
                this.signedIn=false;
            }
            if(tempUser == null) {
                tempUser =new User();
                tempUser.setRole('x');
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(char role) {
        this.role = role;
    }


    public void setValidationStatus(int validationStatus) {
        this.validationStatus = validationStatus;
    }



    public boolean isSignedIn() {
        return signedIn;
    }

    public int getValidationStatus() {
        return validationStatus;
    }
}