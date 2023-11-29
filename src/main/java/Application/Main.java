package Application;

import Application.Services.*;
import Application.Entities.User;

import java.util.Scanner;
import java.util.logging.Logger;


public class Main {
    public static Logger logger = LoggerUtility.getLogger();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String email = "";
        String password = "";
        User currentUser = null;

        int validationStatus;


        while (true) {
            MessagesGenerator.listGenerator("signingList");
            String choice = scanner.nextLine();
            logger.info("Enter your email:");
            email = scanner.nextLine();
            logger.info("Enter your password:");
            password = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    validationStatus= MainUtility.signUpUtility(email,password);
                    logger.info(MessagesGenerator.SigningMessages(validationStatus));
                }
                case "2" -> {
                    validationStatus=MainUtility.signInUtility(email,password,currentUser);
                    logger.info(MessagesGenerator.SigningMessages(validationStatus));

                    if (currentUser != null && currentUser.isSignInStatus())
                    {
                        logger.info(MessagesGenerator.SigningMessages(validationStatus));
                        DatabaseService databaseService = new DatabaseService();

                        switch (currentUser.getRole()){
                            case "user" ->{
                                MainUtility.userUtility(databaseService);
                            }
                            case "admin" ->{
                                MainUtility.manegerUtility(databaseService);
                            }
                            case "installer" ->{
                                MainUtility.installerUtility(databaseService);
                            }
                            default -> {

                            }
                        }
                    }
                    else
                    {
                        logger.info(MessagesGenerator.SigningMessages(5));
                    }
                }
                case "3" -> {System.exit(0);
                }
                default -> logger.info("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
}