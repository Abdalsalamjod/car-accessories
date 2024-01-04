package application;
import application.database.premitive_objects.ResultSetResultHandler;
import application.database.user_defined_types.ProductResultHandler;
import application.entities.*;
import application.services.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static application.Main.*;
import static application.Main.DATE_FORMAT;
import static application.services.MessagesGenerator.LOGGER;
import static java.lang.System.exit;


public class MainUtility {

    private static final String ETC ="1, 2, ... 4.\n";
    private static final String PLEASE_ENTER_VALID = "Invalid choice! \nPlease enter ";
    private MainUtility() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated\n");
    }



    public static void userUtility(DatabaseService databaseService, User currentUser){

        boolean iterator = true;
        while (iterator) {

           MessagesGenerator.listGenerator("userList");
           String option = scanner.nextLine();
           switch ( option ){

               case "1" -> browsProducts(databaseService);
               case "2" -> currentUser.showDetails(LOGGER);
               case "3" -> {
                   MessagesGenerator.listGenerator("editProfile");
                   int optionIn = scanner.nextInt();
                   scanner.nextLine();
                   LOGGER.info("what is the new value: ");
                   String newValue =scanner.nextLine();
                   currentUser.editDetails(optionIn, newValue, LOGGER);
               }
               case "4" -> currentUser.viewInstallationRequests(databaseService);
               case "5" -> currentUser.viewRequisitesHistory(databaseService);
               case "6" -> makeRequest(databaseService, currentUser);
               case "7" -> removeRequest(databaseService, currentUser);
               case "8" -> iterator = false;
               default -> LOGGER.info(PLEASE_ENTER_VALID + "1, 2, ... 8.\n");

           }
        }
    }
    public static void adminUtility(DatabaseService databaseService , Admin currentAdmin) {
        boolean iterator = true;
        while (iterator) {
            MessagesGenerator.listGenerator("adminList");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> manageProducts(databaseService);
                case 2 -> manageUsers(currentAdmin, databaseService);
                case 3 -> iterator = false;
                default -> LOGGER.info(PLEASE_ENTER_VALID + ETC);
            }
        }

    }
    private static void manageProducts(DatabaseService databaseService) {
        MessagesGenerator.listGenerator("manageProductsList");
        int browsOption = scanner.nextInt();
        scanner.nextLine();
        manageProducts(browsOption, databaseService);
    }
    private static void manageUsers(Admin currentAdmin, DatabaseService databaseService) {
        boolean iterator2 = true;
        List<User> users = currentAdmin.viewUsers(databaseService);
        User selectedUser = selectUser(users);

        while (iterator2) {
            MessagesGenerator.listGenerator("ViewAndManageCustomersAccounts");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 8) {
                iterator2 = false;
                continue;
            }

            manageUserAccounts(currentAdmin, selectedUser, choice);
        }
    }
    private static User selectUser(List<User> users) {
        LOGGER.info("\nselected user email: ");
        String tempId = scanner.nextLine();

        for (User user : users) {
            if (user.getEmail().equals(tempId)) {
                return user;
            }
        }
        return null;
    }
    private static void manageUserAccounts(Admin currentAdmin, User selectedUser, int choice) {
        if (choice != 1) {
            LOGGER.info("Enter new value to update: ");
        }

        String newValue = scanner.nextLine();
        LOGGER.info("\n");

        if (selectedUser != null) {
            currentAdmin.manageAccounts( selectedUser, choice, newValue);
        } else {
            LOGGER.severe("Please enter valid email\n");
        }
    }
    public static void installerUtility(DatabaseService databaseService, Installer currentInstaller) {
        boolean iterator = true;
        while (iterator) {
            MessagesGenerator.listGenerator("InstallationRequests");
            String option = scanner.nextLine();
            switch ( option ){

                case "1" -> currentInstaller.viewInstallationRequests(databaseService);
                case "2" -> currentInstaller.scheduleAppointments(databaseService,scanner,false,"");
                case "3" -> currentInstaller.markAsDone(databaseService,scanner,false,"");
                case "4" -> iterator = false;
                default  -> LOGGER.info( PLEASE_ENTER_VALID + ETC);
            }
        }
    }
    public static int signUpUtility(String email,String password){
        String name;
        String location;
        String phoneNumber;
        int validationStatus = ValidationUser.validation(email, password,new DatabaseService());
        if (validationStatus == ValidationUser.VALID) {
            LOGGER.info("Please enter your name: ");
            name=scanner.nextLine();
            LOGGER.info("Please enter your address: ");
            location=scanner.nextLine();
            LOGGER.info("Please enter your phoneNumber: ");
            phoneNumber=scanner.nextLine();
            Profile profile=new Profile(-1,name,phoneNumber,location);
            SignUp signUp = new SignUp(email,password,false,validationStatus,profile);
            signUp.creatAccount(new DatabaseService());
        }
        return validationStatus;
    }
    public static User signInUtility(String email, String password,int validationStatus ){
        if (validationStatus == ValidationUser.VALID)
        {
            SignIn signIn = new SignIn(email, password, false, validationStatus);
            return signIn.performLogIn(new DatabaseService());
        }
        return null;
    }


    public static void manageProducts(int option, DatabaseService dbs){
        String tableName = "Product";

        try {
            switch (option) {
                case 1 -> showAllProducts(dbs);
                case 2 -> addProduct(dbs, tableName);
                case 3 -> deleteProduct(dbs, tableName);
                case 4 -> updateProduct(dbs, tableName);
                case 5 -> exitApplication();
                default -> logger.info("Invalid choice! \nPlease enter 1, 2, ... 6.\n");
            }
        } catch (Exception e) {
            logger.severe("An error occurred during the operation.\n");
        }
    }
    private static void showAllProducts(DatabaseService dbs) throws SQLException {
        ResultSet rs = Product.getAllProducts(dbs);
        while (rs.next()) {
            Product returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
            if (LOGGER.isLoggable(Level.INFO))
                LOGGER.info(returnedProduct.toString());
        }
    }
    private static void addProduct(DatabaseService dbs, String tableName) throws SQLException {
        LOGGER.info("\nPlease enter the product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        LOGGER.info("Please enter the product name: ");
        String name = scanner.nextLine();
        LOGGER.info("Please enter the product category: ");
        String category = scanner.nextLine();
        LOGGER.info("Please enter the product price: ");
        double price = scanner.nextDouble();
        LOGGER.info("Please enter the product quantity: ");
        int quantity = scanner.nextInt();
        Product product = new Product(id, name, category, price, quantity);
        String valid = product.validInformation() + "\n";
        if (valid.equals("\n")) {
            dbs.addObject(product, tableName);
            LOGGER.info("Product Added successfully!");
        } else {
            LOGGER.severe(valid);
        }
    }
    private static void deleteProduct(DatabaseService dbs, String tableName) throws SQLException {
        LOGGER.info("\nPlease enter the product ID: ");
        int id = scanner.nextInt();
        boolean done = dbs.deleteObject(id, tableName);
        if (done)
            LOGGER.info("Product Deleted successfully!");
    }
    private static void updateProduct(DatabaseService dbs, String tableName) throws SQLException {
        LOGGER.info("\nPlease enter the ID of the product to be updated: ");
        int id = scanner.nextInt();
        LOGGER.info("\nPlease enter the new price: ");
        double price = scanner.nextDouble();
        LOGGER.info("\nPlease enter the new quantity: ");
        int quantity = scanner.nextInt();
        if (price > 0 && quantity > 0) {
            Product oldProduct = dbs.executeQuery("SELECT * FROM Product WHERE id=" + id, new ProductResultHandler());
            Product newProduct = new Product(oldProduct.getId(), oldProduct.getName(), oldProduct.getCategory(), price, quantity);
            try {
                dbs.updateObject(newProduct, tableName, "id");
            } catch (Exception e) {
                LOGGER.severe("Error in updating Product\n");
            }
            LOGGER.info("Product updated successfully!\n");

        } else {
            LOGGER.info("Price and Quantity must be greater than zero!\n");
        }


    }
    private static void exitApplication() {
        LOGGER.info("Good bye, have a nice day.");
        System.exit(0);
    }


    public static void browsProducts(DatabaseService dbs) {
        String option;
        do {
            MessagesGenerator.listGenerator("browsProductsList");
            option = scanner.nextLine();
            try {
                handleProductBrowsingOption(option, dbs);
            } catch (Exception e) {
                LOGGER.severe("Cannot get the products, something went wrong!\n");
                exit(0);
            }
        } while (!option.equals("6"));
    }
    private static void handleProductBrowsingOption(String option, DatabaseService dbs) throws SQLException {
        switch (option) {
            case "1":
                viewAllProducts(dbs);
                break;
            case "2":
                searchProductById(dbs);
                break;
            case "3":
                searchProductByName(dbs);
                break;
            case "4":
                searchProductByCategory(dbs);
                break;
            case "5":
                searchProductByPriceRange(dbs);
                break;
            case "6":

                break;
            default:
                LOGGER.info("Invalid choice! \nPlease enter 1, 2, ... 6.\n");
                break;
        }
    }
    private static void viewAllProducts(DatabaseService dbs) throws SQLException {
        ResultSet rs = Product.getAllProducts(dbs);
        while (rs.next()) {
            Product returnedProduct = new Product(rs.getInt("id"), rs.getString("name"), rs.getString(CATEGORY), rs.getDouble(PRICE), rs.getInt(QUANTITY));
            logProductInfo(returnedProduct);
        }
    }
    private static void searchProductById(DatabaseService dbs) throws SQLException {
        LOGGER.info("\nPlease enter the product ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Product returnedProduct = Product.getProductById(id, dbs);
        logProductInfo(returnedProduct);
    }
    private static void searchProductByName(DatabaseService dbs) {
        LOGGER.info("Please enter the product name:");
        String name = scanner.nextLine();
        Product returnedProduct = null;
        try {
            returnedProduct = Product.getProductByName(name, dbs);
        } catch (SQLException e) {
            LOGGER.severe("There is no products with the name you entered\n");        }
        if (returnedProduct == null) {
            LOGGER.severe("There is no product with the name you entered\n");
        } else {
            logProductInfo(returnedProduct);
        }
    }
    private static void searchProductByCategory(DatabaseService dbs) throws SQLException {
        LOGGER.info("Please enter the product category:");
        String category = scanner.nextLine();
        ResultSet rs = Product.getProductsByCategory(category, dbs);
        while (rs.next()) {
            Product returnedProduct = new Product(rs.getInt("id"), rs.getString("name"), rs.getString(CATEGORY), rs.getDouble(PRICE), rs.getInt(QUANTITY));
            logProductInfo(returnedProduct);
        }
    }
    private static void searchProductByPriceRange(DatabaseService dbs) throws SQLException {
        LOGGER.info("Please enter the lower price:");
        double lower = scanner.nextDouble();
        LOGGER.info("Please enter the upper price:");
        double upper = scanner.nextDouble();
        scanner.nextLine(); // consume the rest of the line
        ResultSet rs = Product.getProductsByPriceRange(lower, upper, dbs);
        while (rs.next()) {
            Product returnedProduct = new Product(rs.getInt("id"), rs.getString("name"), rs.getString(CATEGORY), rs.getDouble(PRICE), rs.getInt(QUANTITY));
            logProductInfo(returnedProduct);
        }
    }
    private static void logProductInfo(Product product) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(product.toString());
        }
    }
    public static void makeRequest(DatabaseService databaseService, User currentUser){

        try{

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            Request.initializeDatesArray();
            ArrayList<String> datesArray = ( ArrayList<String> ) Request.getDatesArray();


            LOGGER.info("Please enter the request ID: ");
            int requestID = scanner.nextInt();


            LOGGER.info("\nPlease select the product you want to install\n");
            ResultSet rs = Product.getAllProductsNames(databaseService);
            LOGGER.info("ID" + "  " + "Name\n");
            while ( rs.next() )
                if (LOGGER.isLoggable(Level.INFO))
                    LOGGER.info(String.format("%d  %s%n", rs.getInt(1), rs.getString(2)));


            LOGGER.info("\n");
            int productID = scanner.nextInt();


            LOGGER.info("\nPlease select one of the available dates\n");
            for(int i=0; i<datesArray.size(); i++)
                if (LOGGER.isLoggable(Level.INFO))
                    LOGGER.info(String.format("%d- %s%n", i + 1, datesArray.get(i)));
            int dateIndex = scanner.nextInt() - 1;
            String selectedDate = datesArray.get(dateIndex);
            LocalDateTime dateToStore = LocalDateTime.parse(selectedDate, formatter);


            LOGGER.info("\nPlease enter the description, what exactly do you want to do:\n");
            scanner.nextLine();
            String description = scanner.nextLine();


            Request request = new Request(requestID, productID, currentUser.getEmail(), dateToStore, description);
            request.setDone(0);
            request.setSelected(0);
            databaseService.addObject(request,"Request");
            LOGGER.info("\nRequest Added Successfully, you can check your email for further information\n");
            EmailSender.sendEmail("s12027747@stu.najah.edu", "Hala-car accessories system", "Your request has been added successfully");

            datesArray.remove(dateIndex);
            Request.setDatesArray(datesArray);


        }catch ( Exception e ){
            Main.logger.info(e.getMessage());
            LOGGER.severe("Sorry, something went wrong!\n");
            exit(0);
        }

    }
    public static void removeRequest(DatabaseService databaseService, User currentUser){

        try{

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            ArrayList<Request> returnedRequests = new ArrayList<>();
            ArrayList<String> datesArray = ( ArrayList<String> ) Request.getDatesArray();

            LOGGER.info("Please enter the number of the request you want to remove:\n");
            ResultSet rs = databaseService.executeQuery("SELECT * FROM Request WHERE userId ='" + currentUser.getEmail() + "'", new ResultSetResultHandler());
            while ( rs.next() ){
                String date =  rs.getString(4).substring(0, 19);
                returnedRequests.add(new Request(rs.getInt(1), rs.getInt(2), rs.getString(3), LocalDateTime.parse(date, formatter), rs.getString(5)));
            }

            LOGGER.info("\n");
            String statementToPrint;
            for(int j=0; j<returnedRequests.size(); j++){
                statementToPrint = j+1 + "- " + returnedRequests.get(j);
                LOGGER.info(statementToPrint);
            }

            int requestNumber = scanner.nextInt();
            LocalDateTime removedDate = returnedRequests.get(requestNumber-1).getDate();

            datesArray.add(String.valueOf(removedDate));
            Request.setDatesArray(datesArray);


            databaseService.deleteObject(returnedRequests.get(requestNumber-1).getId(), "Request");
            LOGGER.info("\nRequest Removed Successfully, you can check your email for further information\n");
            EmailSender.sendEmail("s12027747@stu.najah.edu", "Hala-car accessories system", "Your request has been removed successfully");
            rs.close();
        }catch ( Exception e ){
            Main.logger.info(e.getMessage());
            LOGGER.severe("Sorry, something went wrong!");
            exit(0);
        }


    }












}
