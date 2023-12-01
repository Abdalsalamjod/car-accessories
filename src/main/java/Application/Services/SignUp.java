package Application.Services;
import Application.Entities.Profile;
import Application.Entities.User;

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
        validationStatus =-1;

    }
    public SignUp(String email, String password,boolean hasAccount, int validationStatus,Profile profile) {
        this.email=email;
        this.password=password;
        this.validationStatus=validationStatus;
        this.hasAccount=hasAccount;
        this.profile=profile;
    }


    public void creatAccount() {
        if (validationStatus == 0) {

            // this function should put data in DB
            // TODO creat profile
            // assign profile id becuse it -1
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
