package application.services;

import application.LoggerUtility;

import java.util.logging.Logger;

import static application.services.ValidationUser.NULL_EMAIL;
import static application.services.ValidationUser.INVALID_EMAIL;
import static application.services.ValidationUser.EMAIL_NOT_EXIST;
import static application.services.ValidationUser.INVALID_PASSWORD;
import static application.services.ValidationUser.VALID;
import static application.services.ValidationUser.INVALID;

public class MessagesGenerator {
    public static final Logger LOGGER = LoggerUtility.getLogger();
    public static final String SELECT_OPTION = "\nSelect an option: ";
    public static final String MENU = "\nMenu:\n";
    public static final String NEW_LINE = "\n";
    public static final String EXIT_OPTION = "5. Exit" + NEW_LINE;

    private static final String HEADER =
        "   _____________________________________________________   " + NEW_LINE +
        "|       Welcome to HalaCar accessories System :)          |" + NEW_LINE +
        "| 1. If you want to SignUp                                |" + NEW_LINE +
        "| 2. If you want to SignIn                                |" + NEW_LINE +
        "| 3. Exit                                                 |" + NEW_LINE +
        "   _____________________________________________________   " + NEW_LINE;
    

    public static final String SEARCH_TYPE_LIST =
        "1. Search by ID" + NEW_LINE +
        "2. Search by name" + NEW_LINE +
        "3. Search by Category" + NEW_LINE +
        "4. Search in price range" + NEW_LINE +
        EXIT_OPTION;

    public static final String USER_LIST =
        "1. Browse products" + NEW_LINE +
        "2. View profile" + NEW_LINE +
        "3. Edit profile" + NEW_LINE +
        "4. View installations requests" + NEW_LINE +
        "5. View requests history" + NEW_LINE +
        "6. Make new installation request" + NEW_LINE +
        "7. Cancel existing installation request" + NEW_LINE +
        "8. Exit" + NEW_LINE;

    public static final String BROWS_PRODUCT_LIST =
        "1. View all products" + NEW_LINE +
        "2. Search for specific product by ID" + NEW_LINE +
        "3. Search for specific product by name" + NEW_LINE +
        "4. Filter the products by category" + NEW_LINE +
        "5. Filter the products by price range" + NEW_LINE +
        "6. Exit" + NEW_LINE;

    public static final String MANAGE_PRODUCT_LIST =
        "1. View all products" + NEW_LINE +
        "2. Add Product" + NEW_LINE +
        "3. Delete Product" + NEW_LINE +
        "4. Update Product" + NEW_LINE;

    public static final String ADMIN_LIST =
        "1. View and Manage products" + NEW_LINE +
        "2. View and Manage customers accounts" + NEW_LINE +
        "3. Exit" + NEW_LINE;

    public static final String EDIT_PROFILE =
        "1. Edit Name" + NEW_LINE +
        "2. Edit Email" + NEW_LINE +
        "3. Edit Password" + NEW_LINE +
        "4. Edit Location" + NEW_LINE +
        "5. Edit phone number" + NEW_LINE +
        "6. Exit" + NEW_LINE;

    public static final String ADMIN_CONTROL_USER_LIST =
         "1. View Customer" + NEW_LINE +
        "2. Create Account" + NEW_LINE +
        "3. Delete Account" + NEW_LINE +
        "4. Edit Account" + NEW_LINE;

    public static final String INSTALLER_LIST =
        "1. View installation requests" + NEW_LINE +
        "2. Schedule appointments" + NEW_LINE +
        "3. Exit" + NEW_LINE;

    public static final String INSTALLATION_REQUESTS =
        "1. View installation request" + NEW_LINE +
        "2. Approve installation request" + NEW_LINE +
        "3. Mark installation request as done" + NEW_LINE +
        EXIT_OPTION;

    public static final String VIEW_AND_MANAGE_CUSTOMER_ACCOUNTS =
        "1. View user details" + NEW_LINE +
        "2. Edit user name" + NEW_LINE +
        "3. Edit user Email" + NEW_LINE +
        "4. Edit user password" + NEW_LINE +
        "5. Edit user location" + NEW_LINE +
        "6. Edit user contact information" + NEW_LINE +
        "7. Delete user account" + NEW_LINE +
        "8. Exit" + NEW_LINE;

    private MessagesGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String signingMessages(int validationStatus) {
        return switch (validationStatus) {
            case NULL_EMAIL -> "Empty, not a valid empty Email" + NEW_LINE;
            case INVALID_EMAIL -> "Invalid email address" + NEW_LINE;
            case EMAIL_NOT_EXIST -> "Email address is already in use" + NEW_LINE;
            case INVALID_PASSWORD -> "Invalid password" + NEW_LINE;
            case VALID -> "Successful Registration, you are welcome" + NEW_LINE;
            case INVALID -> "Sign In failed. Please try again" + NEW_LINE;
            default -> "Unknown validation status: " + validationStatus;
        };
    }

    public static void listGenerator(String type) {
        switch (type) {
            case "signingList" -> {
                LOGGER.info(HEADER);
                LOGGER.info(SELECT_OPTION);
            }
            case "searchTypeList" -> {
                LOGGER.info(MENU);
                LOGGER.info(SEARCH_TYPE_LIST);
                LOGGER.info(EXIT_OPTION);
                LOGGER.info(SELECT_OPTION);
            }
            case "userList" -> {
                LOGGER.info(MENU);
                LOGGER.info(USER_LIST);
                LOGGER.info(SELECT_OPTION);
            }
            case "browsProductsList" -> {
                LOGGER.info(MENU);
                LOGGER.info(BROWS_PRODUCT_LIST);
                LOGGER.info(SELECT_OPTION);
            }
            case "manageProductsList" -> {
                LOGGER.info(MENU);
                LOGGER.info(MANAGE_PRODUCT_LIST);
                LOGGER.info(EXIT_OPTION);
                LOGGER.info(SELECT_OPTION);
            }
            case "adminList" -> {
                LOGGER.info(MENU);
                LOGGER.info(ADMIN_LIST);
                LOGGER.info(SELECT_OPTION);
            }
            case "editProfile" -> {
                LOGGER.info(MENU);
                LOGGER.info(EDIT_PROFILE);
                LOGGER.info(SELECT_OPTION);
            }
            case "adminControlUserList" -> {
                LOGGER.info(MENU);
                LOGGER.info(ADMIN_CONTROL_USER_LIST);
                LOGGER.info(EXIT_OPTION);
                LOGGER.info(SELECT_OPTION);
            }
            case "installerList" -> {
                LOGGER.info(MENU);
                LOGGER.info(INSTALLER_LIST);
                LOGGER.info(SELECT_OPTION);
            }
            case "InstallationRequests" -> {
                LOGGER.info(MENU);
                LOGGER.info(INSTALLATION_REQUESTS);
                LOGGER.info(SELECT_OPTION);
            }
            case "ViewAndManageCustomersAccounts" -> {
                LOGGER.info(MENU);
                LOGGER.info(VIEW_AND_MANAGE_CUSTOMER_ACCOUNTS);
                LOGGER.info(SELECT_OPTION);
            }
            default -> LOGGER.severe("Error in generating list, try again" + NEW_LINE);
        }
    }
}
