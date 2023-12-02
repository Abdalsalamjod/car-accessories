package Application.Services;

import Application.Entities.Admin;
import Application.Entities.Installer;
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
        char Role='u';// change it
        if (validationStatus==0){
            this.signedIn=true;
            // connect with DB
            // assign real profile

            Profile profile=new Profile();
            switch (Role){
                case 'u':
                    return new User(email, password, role, signedIn,profile);
                case 'a':
                    return new Admin(email, password, role, signedIn,profile);
                case 'i':
                    return new Installer(email, password, role, signedIn,profile);
            }
           return null;
        }
        else {
            this.signedIn=false;
            return null;
        }

    }

}
