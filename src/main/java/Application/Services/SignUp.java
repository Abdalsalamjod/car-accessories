package Application.Services;
import Application.Entities.User;

public class SignUp {

    public String email;
    public String password;
    public boolean hasAccount;
    public int validationStatus;

    SignIn signIn;
    public SignUp() {
        email=null;
        password=null;
        hasAccount=false;
        validationStatus =-1;

    }
    public SignUp(String email, String password,boolean hasAccount, int validationStatus) {
        this.email=email;
        this.password=password;
        this.validationStatus=validationStatus;
        this.hasAccount=hasAccount;
    }


    public void creatAccount() {
        if (validationStatus == 0) {

            // this function should put data in DB
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
