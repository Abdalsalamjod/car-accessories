package Application;

import Application.Entities.Admin;
import Application.Entities.Installer;
import Application.Services.*;
import Application.Entities.User;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static Logger logger = LoggerUtility.getLogger();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        User currentUser;
        String email;
        String password;
        int validationStatus;

        while (true) {
            MessagesGenerator.listGenerator("signingList");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> {
                    logger.info("Enter your email:");
                    email = scanner.nextLine();
                    logger.info("Enter your password:");
                    password = scanner.nextLine();
                    validationStatus= MainUtility.signUpUtility(email,password);
                    logger.info(MessagesGenerator.SigningMessages(validationStatus));
                }
                case "2" -> {
                    logger.info("Enter your email:");
                    email = scanner.nextLine();
                    logger.info("Enter your password:");
                    password = scanner.nextLine();

                    validationStatus = ValidationUser.validation(email, password);
                    currentUser=MainUtility.signInUtility(email,password,validationStatus );

                    logger.info(MessagesGenerator.SigningMessages(validationStatus));

                    if (currentUser != null && currentUser.isSignInStatus())
                    {
                        logger.info(MessagesGenerator.SigningMessages(validationStatus));
                        DatabaseService databaseService = new DatabaseService();
                        System.out.println(currentUser.getProfileObject().getName());
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
                              logger.info("Error: something went wrong, please run application again!\n");

                        }
                    }
                    else
                    {
                        logger.info(MessagesGenerator.SigningMessages(5));
                    }
                }
                case "3" -> {
                    logger.info("Good bye, have a nice day.");
                    System.exit(0);
                }
                default -> logger.info("\nInvalid choice!, Please enter 1, 2, or 3.\n");

            }

        }
    }
}