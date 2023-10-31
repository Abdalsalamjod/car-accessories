package Application.Services;

import Application.entities.User;

public class SignIn {
    public String email;
    public String password;
    public String role;
    public boolean signedIn;
    public int validationStatus;
    public SignIn() {
        email=null;
        password=null;
        role=null;
        signedIn=false;
        validationStatus=-1;
    }

    public SignIn(String email, String password, String role, boolean signedIn, int validationStatus) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.signedIn=signedIn;
        this.validationStatus=validationStatus;
    }

    public User performLogIn() {
        if (validationStatus==0){
            this.signedIn=true;
            // connect with DB
            return new User(email, password, role, signedIn);
        }
        else {
            this.signedIn=false;
            return null;
        }

    }

}
