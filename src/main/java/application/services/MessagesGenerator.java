package application.services;

import application.LoggerUtility;

import java.util.logging.Logger;

import static application.services.ValidationUser.*;

public  class MessagesGenerator {
    public static final Logger LOGGER = LoggerUtility.getLogger();
    public static final String SELECT_OPTION="Select an option: ";
    public static final String MENU ="\nMenu:\n";
    private MessagesGenerator() {
        throw new IllegalStateException("Utility class");
    }
    public static String signingMessages(int validationStatus) {
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
                LOGGER.info("   _____________________________________________________  \n");
                LOGGER.info("|       Welcome to HalaCar accessories System :)          |\n");
                LOGGER.info("| 1. If you want to SignUp                                |\n");
                LOGGER.info("| 2. If you want to SignIn                                |\n");
                LOGGER.info("| 3. Exit                                                 |\n");
                LOGGER.info("   _____________________________________________________   \n");
                LOGGER.info(SELECT_OPTION);
            }
            case "searchTypeList" -> {
                LOGGER.info(MENU);
                LOGGER.info("1. Search by ID\n");
                LOGGER.info("2. Search by name\n");
                LOGGER.info("3. Search by Category\n");
                LOGGER.info("4. Search in price range\n");
                LOGGER.info(EXIT);
                LOGGER.info(SELECT_OPTION);
            }
            case "userList" -> {
                LOGGER.info(MENU);
                LOGGER.info("1. Browse products\n");
                LOGGER.info("2. View profile\n");
                LOGGER.info("3. Edit profile\n");
                LOGGER.info("4. View installations requests\n");
                LOGGER.info("5. View requests history\n");
                LOGGER.info("6. Make new installation request\n");
                LOGGER.info("7. Cancel existing installation request\n");
                LOGGER.info("8. Exit\n");
                LOGGER.info(SELECT_OPTION);
            }
            case "browsProductsList" -> {
                LOGGER.info(MENU);
                LOGGER.info("1. View all products\n");
                LOGGER.info("2. Search for specific product by ID\n");
                LOGGER.info("3. Search for specific product by name\n");
                LOGGER.info("4. Filter the products by category \n");
                LOGGER.info("5. Filter the products by price range\n");
                LOGGER.info("6. Exit\n");
                LOGGER.info(SELECT_OPTION);
            }
            case "manageProductsList" -> {
                LOGGER.info(MENU);
                LOGGER.info("1. View all products\n");
                LOGGER.info("2. Add Product\n");
                LOGGER.info("3. Delete Product\n");
                LOGGER.info("4. Update Product\n");
                LOGGER.info(EXIT);
                LOGGER.info(SELECT_OPTION);
            }
            case "adminList" -> {
                LOGGER.info(MENU);
                LOGGER.info("1. View and Manage products\n");
                LOGGER.info("2. View and Manage customers accounts \n");
                LOGGER.info("3. Exit\n");
                LOGGER.info(SELECT_OPTION);
            }
            case "editProfile" -> {
                LOGGER.info(MENU);
                LOGGER.info("1. Edit Name\n");
                LOGGER.info("2. Edit Email\n");
                LOGGER.info("3. Edit Password\n");
                LOGGER.info("4. Edit Location\n");
                LOGGER.info("5. Edit phone number\n");
                LOGGER.info("6. Exit\n");
                LOGGER.info(SELECT_OPTION);
            }
            case "adminControlUserList"-> {
                LOGGER.info(MENU);
                LOGGER.info("1. View Customer\n");
                LOGGER.info("2. Create Account\n");
                LOGGER.info("3. Delete Account\n");
                LOGGER.info("4. Edit Account\n");
                LOGGER.info(EXIT);
                LOGGER.info(SELECT_OPTION);
            }
            case "installerList"-> {
                LOGGER.info(MENU);
                LOGGER.info("1. View installation requests\n");
                LOGGER.info("2. Schedule appointments\n");
                LOGGER.info("3. Exit\n");
                LOGGER.info(SELECT_OPTION);
            }
            case "InstallationRequests" -> {
                LOGGER.info(MENU);
                LOGGER.info("1. View installation request\n");
                LOGGER.info("2. Approve installation request\n");
                LOGGER.info("3. Mark installation request as done\n");
                LOGGER.info("4. Exit\n");
                LOGGER.info(SELECT_OPTION);
            }
            case "ViewAndManageCustomersAccounts" -> {
                LOGGER.info(MENU);
                LOGGER.info("1. View user details\n");
                LOGGER.info("2. Edit user name\n");
                LOGGER.info("3. Edit user Email\n");
                LOGGER.info("4. Edit user password\n");
                LOGGER.info("5. Edit user location\n");
                LOGGER.info("6. Edit user contact information\n");
                LOGGER.info("7. Delete user account\n");
                LOGGER.info("8. Exit\n");
                LOGGER.info(SELECT_OPTION);
            }
        }
    }
}
