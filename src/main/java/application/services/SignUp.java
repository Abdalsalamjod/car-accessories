package application.services;
import application.dataBase.Premetive_Objects.IntResultHandler;
import application.entities.Profile;
import application.entities.User;

import java.sql.SQLException;

public class SignUp {

    public String email;
    public String password;
    public Profile profile;
    public boolean hasAccount;
    public int validationStatus;
    SignIn signIn;
    public SignUp() {
        email=null;
        password=null;
        hasAccount=false;
        validationStatus = -1;

    }
    public SignUp(String email, String password,boolean hasAccount, int validationStatus,Profile profile) {
        this.email=email;
        this.password=password;
        this.validationStatus=validationStatus;
        this.hasAccount=hasAccount;
        this.profile=profile;
    }


    public void creatAccount() {
        int lastProfieID;
        if (validationStatus == 0) {
            DatabaseService dbs =new DatabaseService();
            try {
                lastProfieID= dbs.executeQuery("SELECT MAX(`profileId`) FROM Profile",new IntResultHandler());
                profile.setProfileId(lastProfieID+1);
                User tempUser =new User(email,password,'u',false,profile);
                dbs.addObject(profile,"Profile");
                dbs.addObject(tempUser,"user");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.hasAccount = true;
        }
        else
        {
            this.hasAccount = false;
        }
    }
    public User performLogIn() {
      signIn=new SignIn(email, password, "user", false, validationStatus);
      return signIn.performLogIn();
    }

}
