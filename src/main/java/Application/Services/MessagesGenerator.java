package Application.Services;

import Application.LoggerUtility;

import java.util.logging.Logger;

import static Application.Services.ValidationUser.*;

public class MessagesGenerator {
    public static Logger logger = LoggerUtility.getLogger();
    public static String SigningMessages(int validationStatus) {
        return switch (validationStatus) {
            case NULL_EMAIL -> "Empty, not a valid empty Email";
            case INVALID_EMAIL -> "Invalid email address";
            case EMAIL_NOT_EXIST -> "Email address is already in use";
            case INVALID_PASSWORD -> "Invalid password";
            case VALID -> "Successful Registration, you are welcome";
            case INVALID ->"Sign In failed. Please try again";
            default -> "Unknown validation status: " + validationStatus;
        };
    }
    public static void listGenerator(String type){
        switch (type){
            case "signingList":
                logger.info("\n _____________________________________________________\n");
                logger.info("|       Welcome to HalaCar  accessories System :)     |\n");
                logger.info("| 1-If you want to SignUp                             |\n");
                logger.info("| 2-If you want to SignIn                             |\n");
                logger.info("|_____________________________________________________|\n");
                break;
            case "productList":
                logger.info("\nMenu:\n");
                logger.info("1. Add a product\n");
                logger.info("2. Delete a product\n");
                logger.info("3. Update a product\n");
                logger.info("4. Search for a product\n");
                logger.info("5. Exit\n");
                logger.info("Select an option: \n");
                break;
            default:

            break;
        }
    }
}