package Application.Entities;

import Application.Services.DatabaseService;

import java.util.Date;

public class User {
    private String name;
    private String email;
    private String password;
    public String role;
    public boolean SignInStatus;
    public User() {

    }
    public User(String email, String password, String role, boolean SignInStatus) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.SignInStatus = SignInStatus;

    }

    public boolean isSignInStatus() {return SignInStatus;}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getRole() {return role;}

    public String getPassword() {
        return password;
    }

    public boolean makeRequest(Request request){

        try{
            DatabaseService dbs = new DatabaseService();
            return dbs.addObject(request,"Request");

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeRequest(int id){
        try{
            DatabaseService dbs = new DatabaseService();
            dbs.deleteObject(id, "Request");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }




    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {this.role = role;}
    public void setSignInStatus(boolean signInStatus) {
        SignInStatus = signInStatus;
    }
}
