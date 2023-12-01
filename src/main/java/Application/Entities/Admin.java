package Application.Entities;

import java.util.ArrayList;

public class Admin extends User{

    public Admin(String email, String password, String role, boolean SignInStatus, Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.SignInStatus = SignInStatus;
        this.requests=new ArrayList<>();
    }
}
