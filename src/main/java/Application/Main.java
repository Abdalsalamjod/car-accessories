package Application;

import Application.Services.*;
import Application.Entities.User;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static Logger logger = LoggerUtility.getLogger();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        User currentUser = null;
        String email;
        String password;
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
                            case "u" ->{
                                MainUtility.userUtility(databaseService,currentUser);
                            }
                            case "a" ->{
                                MainUtility.adminUtility(databaseService,currentUser);
                            }
                            case "i" ->{
                                MainUtility.installerUtility(databaseService);
                            }
                            default -> {
                                logger.info("Error: something went wrong, please run application again!\n");
                            }
                        }
                    }
                    else
                    {
                        logger.info(MessagesGenerator.SigningMessages(5));
                    }
                }
                case "3" -> {
                    System.exit(0);
                }
                default -> logger.info("Invalid choice! \nPlease enter 1, 2, or 3.\n");
            }
        }
    }
}