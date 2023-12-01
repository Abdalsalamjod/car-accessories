package Application.Services;

import Application.Entities.Profile;
import Application.Entities.User;

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
            this.signedIn=true;
            // connect with DB
             Profile profile=new Profile();
            return new User(email, password, role, signedIn,profile);
        }
        else {
            this.signedIn=false;
            return null;
        }

    }

}
