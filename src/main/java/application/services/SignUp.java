package application.services;
import application.Main;
import application.database.premitive_objects.IntResultHandler;
import application.entities.Profile;
import application.entities.User;

public class SignUp {

    private String email;
    private String password;
    private Profile profile;
    private boolean hasAccount;
    private int validationStatus;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean isHasAccount() {
        return hasAccount;
    }

    public int getValidationStatus() {
        return validationStatus;
    }

    public SignIn getSignIn() {
        return signIn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }

    public void setValidationStatus(int validationStatus) {
        this.validationStatus = validationStatus;
    }

    public void setSignIn(SignIn signIn) {
        this.signIn = signIn;
    }
}
