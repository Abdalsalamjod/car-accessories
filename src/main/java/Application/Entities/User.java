package Application.Entities;


import Application.LoggerUtility;
import Application.Services.DatabaseService;
import Application.Services.ValidationUser;

import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;


public class User {
    private String email;
    private String password;
    public String role;
    public Profile profile;
    public boolean SignInStatus;


    public User() {

    }

    public User(String email, String password, String role, boolean SignInStatus,Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.SignInStatus = SignInStatus;
        this.profile = profile;
    }


    public boolean isSignInStatus() {return SignInStatus;}

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



    public void setEmail(String email) {
        DatabaseService dbs = new DatabaseService();
        //todo: connect to db
        this.email = email;
        dbs=null;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {this.role = role;}
    public void setSignInStatus(boolean signInStatus) {
        SignInStatus = signInStatus;
    }

    public void showDetails(Logger logger) {
        logger.info("Name: "+this.getProfile().getName());
        logger.info("Location: "+this.getProfile().getLocation());
        logger.info("Phone number: "+this.getProfile().getPhoneNumber());
        logger.info("Email : "+this.getEmail());
    }

    public void editDetails(int optionIn, Logger logger, Scanner scanner) {
        String newChoice;
        switch (optionIn){
            case 1:
                logger.info("The current name is: "+this.getProfile().getName());
                logger.info("Please enter the new name: ");
                newChoice=scanner.nextLine();
                this.getProfile().setName(newChoice);
                break;
            case 2:
                logger.info("Please enter your password first: ");
                newChoice=scanner.nextLine();
                if(this.getPassword().equals(newChoice))
                {
                        logger.info("The current Email is: "+this.getEmail());
                    logger.info("Please enter the new email: ");
                    newChoice=scanner.nextLine();
                    if(ValidationUser.isValidEmail(newChoice) && !ValidationUser.isExistEmail(newChoice) && !newChoice.isEmpty())
                        this.setEmail(newChoice);
                    else
                        logger.info("Error: exist or invalid email, try again!");
                }
                else
                    logger.info("Error: the password you entered not valid, try again!");

                break;
            case 3:
                logger.info("Please enter your password first: ");
                newChoice=scanner.nextLine();
                if(this.getPassword().equals(newChoice))
                {
                    logger.info("Please enter new password: ");
                    newChoice=scanner.nextLine();
                    this.setPassword(newChoice);
                }
                else
                    logger.info("Error: the password you entered not valid, try again!");


                break;
            case 4:
                logger.info("The current location is: "+this.getProfile().getLocation());
                logger.info("Please enter the new location: ");
                newChoice=scanner.nextLine();
                this.getProfile().setLocation(newChoice);
                break;
            case 5:
                logger.info("The current Phone Number is: "+this.getProfile().getPhoneNumber());
                logger.info("Please enter the new Phone Number: ");
                newChoice=scanner.nextLine();
                this.getProfile().setPhoneNumber(newChoice);
                break;
            default:
                break;
        }
    }
}