package application;

import application.entities.Admin;
import application.entities.Installer;
import application.entities.User;
import application.services.*;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static final Logger logger = LoggerUtility.getLogger();
    public static final Scanner scanner = new Scanner(System.in);
    public static final String EDIT_DETAILS_ERROR ="Error: in editDetails\n";
    public static final String PROFILE="Profile";
    public static final String PROFILE_ID="profileId";
    public static final String REQUEST ="Request" ;
    public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    public static final String CATEGORY= "category";
    public static final String PRICE ="price";

    public static final String QUANTITY="quantity";

    public static void main(String[] args) {

        User currentUser = null;
        String email;
        String password;
        int validationStatus;
        boolean iterator = true;

        while (iterator) {
            MessagesGenerator.listGenerator("signingList");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> {
                    logger.info("Enter your email: ");
                    email = scanner.nextLine();
                    logger.info("Enter your password: ");
                    password = scanner.nextLine();
                    validationStatus= MainUtility.signUpUtility(email,password);
                    if (logger.isLoggable(Level.INFO))
                         logger.info(MessagesGenerator.signingMessages(validationStatus));
                }
                case "2" -> {
                    logger.info("Enter your email: ");
                    email = scanner.nextLine();
                    logger.info("Enter your password: ");
                    password = scanner.nextLine();

                    validationStatus = ValidationUser.validation(email, password,new DatabaseService());
                    currentUser=MainUtility.signInUtility(email,password,validationStatus );

                    if (logger.isLoggable(Level.INFO))
                        logger.info(MessagesGenerator.signingMessages(validationStatus));

                    if (currentUser != null && currentUser.isSignInStatus())
                    {
                        DatabaseService databaseService = new DatabaseService();
                        logger.info("Welcome dear " + currentUser.getProfileObject().getName());
                        switch (currentUser.getRole()){

                            case 'u' -> MainUtility.userUtility(databaseService,currentUser);
                            case 'a' ->{
                                Admin currentAdmin =(Admin) currentUser;
                                MainUtility.adminUtility(databaseService,currentAdmin);
                            }
                            case 'i' ->{

                                Installer currentInstaller =(Installer) currentUser;
                                MainUtility.installerUtility(databaseService,currentInstaller);
                            }
                            default ->
                              logger.severe("Error: something went wrong, please run application again!\n");
                        }
                    }
                    else
                    {
                        if (logger.isLoggable(Level.INFO))
                            logger.severe(MessagesGenerator.signingMessages(5));
                    }
                }
                case "3" -> {
                    iterator=false;
                    logger.info("Good bye, have a nice day.");
                }
                case "4" -> {
                    LogOut logOut =new LogOut();
                    logOut.performLogout(currentUser,new DatabaseService());
                }
                default -> logger.severe("\nInvalid choice!, Please enter 1, 2, or 3.\n");
            }
        }
    }
}
