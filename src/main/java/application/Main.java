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
    public static final String EDIT_DETAILS_ERROR = "Error: in editDetails\n";
    public static final String PROFILE = "Profile";
    public static final String PROFILE_ID = "profileId";
    public static final String REQUEST = "Request" ;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String CATEGORY = "category";
    public static final String PRICE = "price";
    public static final String QUANTITY = "quantity";

    public static void main(String[] args) {
        User currentUser = null;
        boolean iterator = true;

        while (iterator) {
            MessagesGenerator.listGenerator("signingList");
            String choice = scanner.nextLine();
            currentUser = handleUserAction(choice, currentUser);

            if ("3".equals(choice)) {
                iterator = false;
                logger.info("Good bye, have a nice day.");
            }
        }
    }

    private static User handleUserAction(String choice, User currentUser) {
        switch (choice) {
            case "1" -> currentUser = handleSignUp();
            case "2" -> currentUser = handleSignIn(currentUser);
            case "4" -> performLogout(currentUser);
            default -> logger.severe("\nInvalid choice!, Please enter 1, 2, or 3.\n");
        }
        return currentUser;
    }

    private static User handleSignUp() {
        logger.info("Enter your email: ");
        String email = scanner.nextLine();
        logger.info("Enter your password: ");
        String password = scanner.nextLine();
        int validationStatus = MainUtility.signUpUtility(email, password);
        if (logger.isLoggable(Level.INFO))
            logger.info(MessagesGenerator.signingMessages(validationStatus));
        return null;
    }

    private static User handleSignIn(User currentUser) {
        logger.info("Enter your email: ");
        String email = scanner.nextLine();
        logger.info("Enter your password: ");
        String password = scanner.nextLine();

        int validationStatus = ValidationUser.validation(email, password, new DatabaseService());
        User newUser = MainUtility.signInUtility(email, password, validationStatus);

        if (logger.isLoggable(Level.INFO)) {
            logger.info(MessagesGenerator.signingMessages(validationStatus));
        }

        if (newUser != null && newUser.isSignInStatus()) {
            DatabaseService databaseService = new DatabaseService();
            logger.info("Welcome dear " + newUser.getProfileObject().getName());
            handleUserRole(newUser, databaseService);
            return newUser;
        } else {
            if (logger.isLoggable(Level.INFO)) {
                logger.severe(MessagesGenerator.signingMessages(5));
            }
            return currentUser;
        }
    }


    private static void handleUserRole(User currentUser, DatabaseService databaseService) {
        switch (currentUser.getRole()) {
            case 'u' -> MainUtility.userUtility(databaseService, currentUser);
            case 'a' -> {
                Admin currentAdmin = (Admin) currentUser;
                MainUtility.adminUtility(databaseService, currentAdmin);
            }
            case 'i' -> {
                Installer currentInstaller = (Installer) currentUser;
                MainUtility.installerUtility(databaseService, currentInstaller);
            }
            default -> logger.severe("Error: something went wrong, please run application again!\n");
        }
    }

    private static void performLogout(User currentUser) {
        new LogOut().performLogout(currentUser, new DatabaseService());
    }
}
