package Application.Services;

public class SignIn {
    public boolean signedIn;
    public boolean valid;
    public String email;
    public String password;



    public SignIn() {
        signedIn=false;
        valid=false;
        email=null;
        password=null;
    }

    public String getMessage() {
        if (valid )
            return "successful sign in\n";
        return "xxxxx";
    }
}
