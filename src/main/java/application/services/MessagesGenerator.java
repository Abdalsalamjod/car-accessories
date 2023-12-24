package application.services;

import application.LoggerUtility;

import java.util.logging.Logger;

import static application.services.ValidationUser.*;

public class MessagesGenerator {
    public static Logger logger = LoggerUtility.getLogger();
    public static final String SELECT_OPTION="Select an option: ";
    public static final String MENU ="\nMenu:\n";
    public static String SigningMessages(int validationStatus) {
        return switch (validationStatus) {
            case NULL_EMAIL -> "Empty, not a valid empty Email\n";
            case INVALID_EMAIL -> "Invalid email address\n";
            case EMAIL_NOT_EXIST -> "Email address is already in use\n";
            case INVALID_PASSWORD -> "Invalid password\n";
            case VALID -> "Successful Registration, you are welcome\n";
            case INVALID ->"Sign In failed. Please try again\n";
            default -> "Unknown validation status: " + validationStatus;
        };
    }
    public static void listGenerator(String type){

        final String EXIT = "5. Exit\n";

        switch ( type ) {
            case "signingList" -> {
                logger.info("   _____________________________________________________  \n");
                logger.info("|       Welcome to HalaCar accessories System :)          |\n");
                logger.info("| 1. If you want to SignUp                                |\n");
                logger.info("| 2. If you want to SignIn                                |\n");
                logger.info("| 3. Exit                                                 |\n");
                logger.info("   _____________________________________________________   \n");
                logger.info(SELECT_OPTION);
            }
            case "searchTypeList" -> {
                logger.info(MENU);
                logger.info("1. Search by ID\n");
                logger.info("2. Search by name\n");
                logger.info("3. Search by Category\n");
                logger.info("4. Search in price range\n");
                logger.info(EXIT);
                logger.info(SELECT_OPTION);
            }
            case "userList" -> {
                logger.info(MENU);
                logger.info("1. Browse products\n");
                logger.info("2. View profile\n");
                logger.info("3. Edit profile\n");
                logger.info("4. View installations requests\n");
                logger.info("5. View requests history\n");
                logger.info("6. Make new installation request\n");
                logger.info("7. Cancel existing installation request\n");
                logger.info("8. Exit\n");
                logger.info(SELECT_OPTION);
            }
            case "browsProductsList" -> {
                logger.info(MENU);
                logger.info("1. View all products\n");
                logger.info("2. Search for specific product by ID\n");
                logger.info("3. Search for specific product by name\n");
                logger.info("4. Filter the products by category \n");
                logger.info("5. Filter the products by price range\n");
                logger.info("6. Exit\n");
                logger.info(SELECT_OPTION);
            }
            case "manageProductsList" -> {
                logger.info(MENU);
                logger.info("1. View all products\n");
                logger.info("2. Add Product\n");
                logger.info("3. Delete Product\n");
                logger.info("4. Update Product\n");
                logger.info(EXIT);
                logger.info(SELECT_OPTION);
            }
            case "adminList" -> {
                logger.info(MENU);
                logger.info("1. View and Manage products\n");
                logger.info("2. View and Manage customers accounts \n");
                logger.info("3. Exit\n");
                logger.info(SELECT_OPTION);
            }
            case "editProfile" -> {
                logger.info(MENU);
                logger.info("1. Edit Name\n");
                logger.info("2. Edit Email\n");
                logger.info("3. Edit Password\n");
                logger.info("4. Edit Location\n");
                logger.info("5. Edit phone number\n");
                logger.info("6. Exit\n");
                logger.info(SELECT_OPTION);
            }
            case "adminControlUserList"-> {
                logger.info(MENU);
                logger.info("1. View Customer\n");
                logger.info("2. Create Account\n");
                logger.info("3. Delete Account\n");
                logger.info("4. Edit Account\n");
                logger.info(EXIT);
                logger.info(SELECT_OPTION);
            }
            case "installerList"-> {
                logger.info(MENU);
                logger.info("1. View installation requests\n");
                logger.info("2. Schedule appointments\n");
                logger.info("3. Exit\n");
                logger.info(SELECT_OPTION);
            }
            case "InstallationRequests" -> {
                logger.info(MENU);
                logger.info("1. View installation request\n");
                logger.info("2. Approve installation request\n");
                logger.info("3. Mark installation request as done\n");
                logger.info("4. Exit\n");
                logger.info(SELECT_OPTION);
            }
            default->{

            }
        }
    }
}
