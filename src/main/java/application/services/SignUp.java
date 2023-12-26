package application.services;
import application.Main;
import application.database.premitive_objects.IntResultHandler;
import application.entities.Profile;
import application.entities.User;

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


    public void creatAccount(DatabaseService dbs) {
        int lastProfieID;
        if (validationStatus == 0) {

            try {
                lastProfieID= dbs.executeQuery("SELECT MAX(`profileId`) FROM Profile",new IntResultHandler());
                profile.setProfileId(lastProfieID+1);
                User tempUser =new User(email,password,'u',false,profile);
                dbs.addObject(profile,"Profile");
                dbs.addObject(tempUser,"user");

            } catch (Exception e) {
                Main.logger.severe("Error: in creatAccount\n");
            }
            this.hasAccount = true;
        }
        else
        {
            this.hasAccount = false;
        }
    }
    public User performLogIn() {
      signIn=new SignIn(email, password, false, validationStatus);
      return signIn.performLogIn(new DatabaseService());
    }

}
