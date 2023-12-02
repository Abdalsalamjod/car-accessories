package Application.Entities;

import java.util.ArrayList;

public class Installer extends User{

    public Installer(String email, String password, String role, boolean SignInStatus, Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.SignInStatus = SignInStatus;
        this.requests=new ArrayList<>();
    }
}
