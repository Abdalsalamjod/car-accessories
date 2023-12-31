package application.entities;

import application.Main;
import application.database.premitive_objects.ResultSetResultHandler;
import application.services.DatabaseService;
import application.services.EmailSender;
import application.services.MessagesGenerator;
import application.services.ValidationUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static application.Main.*;
import static application.services.MessagesGenerator.LOGGER;
import static java.lang.System.exit;


public class User {

    protected String email;
    protected String password;
    protected Profile profile;
    protected char role;
    protected boolean signInStatus;


    public User() {
            this.profile=new Profile();
    }
    public User(String email, String password, char role, boolean signInStatus,Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.signInStatus = signInStatus;

    }
    public User(String email, String password, char role, boolean signInStatus,int profileId) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile=new Profile();
        this.profile.setProfileId(profileId);
        this.signInStatus = signInStatus;

    }

    public void showDetails(Logger logger) {
        logger.info("\nName: "+this.getProfileObject().getName());
        logger.info("\nLocation: "+this.getProfileObject().getLocation());
        logger.info("\nPhone number: "+this.getProfileObject().getPhoneNumber());
        logger.info("\nEmail : "+this.getEmail());
    }
    public void editDetails(int optionIn, String newValue, Logger logger) {
        DatabaseService dbs = new DatabaseService();

        switch (optionIn) {
            case 1:
                updateName(newValue, logger, dbs);
                break;
            case 2:
                updateEmail(newValue, logger, dbs);
                break;
            case 3:
                updatePassword(newValue, logger, dbs);
                break;
            case 4:
                updateLocation(newValue, logger, dbs);
                break;
            case 5:
                updatePhoneNumber(newValue, logger, dbs);
                break;
            default:
                logger.info("Invalid option.");
                break;
        }
    }

    private void updateName(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("The current name is: " + this.getProfileObject().getName());
        this.getProfileObject().setName(newValue);
        updateProfileObject(logger, dbs, "Updated name to: " + newValue);
    }

    private void updateEmail(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("The current Email is: " + this.getEmail());
        if (ValidationUser.isValidEmail(newValue) && !ValidationUser.isExistEmail(newValue, dbs)) {
            this.setEmail(newValue);
            updateDatabaseObject(this, "user", "email", logger, "Updated email to: " + newValue);
        } else {
            logger.info("Error: exist or invalid email, try again!");
        }
    }

    private void updatePassword(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("Updating password...");
        this.setPassword(newValue);
        updateDatabaseObject(this, "user", "email", logger, "Password updated.");
    }

    private void updateLocation(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("The current location is: " + this.getProfileObject().getLocation());
        this.getProfileObject().setLocation(newValue);
        updateProfileObject(logger, dbs, "Updated location to: " + newValue);
    }

    private void updatePhoneNumber(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("The current Phone Number is: " + this.getProfileObject().getPhoneNumber());
        this.getProfileObject().setPhoneNumber(newValue);
        updateProfileObject(logger, dbs, "Updated phone number to: " + newValue);
    }

    private void updateDatabaseObject(Object object, String tableName, String columnName, Logger logger, String successMessage) {
        try {
            new DatabaseService().updateObject(object, tableName, columnName);
            logger.info(successMessage);
        } catch (Exception e) {
            logger.severe(EDIT_DETAILS_ERROR);
        }
    }

    private void updateProfileObject(Logger logger, DatabaseService dbs, String successMessage) {
        try {
            dbs.updateObject(profile, PROFILE, PROFILE_ID);
            logger.info(successMessage);
        } catch (Exception e) {
            logger.severe(EDIT_DETAILS_ERROR);
        }
    }


    public void viewRequisitesHistory(DatabaseService databaseService){
        ResultSet resultSet;
        Request request;
        List<Request> availableRequests =new ArrayList<>();

        try {
            resultSet=  databaseService.executeQuery("SELECT * FROM `Request` WHERE `done` = true AND `userId`= "+this.email , new ResultSetResultHandler());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

            while ( resultSet.next() ) {
                String date =  resultSet.getString(4).substring(0, 19);
                request= new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        LocalDateTime.parse(date, formatter),
                        resultSet.getString(5));

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(request.toString());
                }
                availableRequests.add(request);
            }
        } catch (SQLException e) {
            Main.logger.info("something went wrong\n");
        }
    }
    public List<Request> viewInstallationRequests(DatabaseService databaseService) {
        ResultSet resultSet;
        Request request;
        List<Request> availableRequests =new ArrayList<>();


        try {
            resultSet = databaseService.executeQuery("SELECT * FROM `Request` WHERE `selected` = true AND `userId`= "+this.email , new ResultSetResultHandler());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            while (resultSet.next()) {
                String date =  resultSet.getString(4).substring(0, 19);
                request = new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        LocalDateTime.parse(date, formatter),
                        resultSet.getString(5)
                );
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(request.toString());
                }
                availableRequests.add(request);
            }

        } catch (Exception e) {
            Main.logger.severe("something went wrong\n");
        }

        return availableRequests;
    }



    public void browsProducts(DatabaseService dbs) {
        String option;
        do {
            MessagesGenerator.listGenerator("browsProductsList");
            option = scanner.nextLine();
            try {
                handleProductBrowsingOption(option, dbs);
            } catch (Exception e) {
                LOGGER.severe("Cannot get the product " + option + ", something went wrong!\n");
                exit(0);
            }
        } while (!option.equals("6"));
    }

    private void handleProductBrowsingOption(String option, DatabaseService dbs) throws SQLException {
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

    private void viewAllProducts(DatabaseService dbs) throws SQLException {
        ResultSet rs = Product.getAllProducts(dbs);
        while (rs.next()) {
            Product returnedProduct = new Product(rs.getInt("id"), rs.getString("name"), rs.getString(CATEGORY), rs.getDouble(PRICE), rs.getInt(QUANTITY));
            logProductInfo(returnedProduct);
        }
    }

    private void searchProductById(DatabaseService dbs) throws SQLException {
        LOGGER.info("\nPlease enter the product ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Product returnedProduct = Product.getProductById(id, dbs);
        logProductInfo(returnedProduct);
    }

    private void searchProductByName(DatabaseService dbs) {
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

    private void searchProductByCategory(DatabaseService dbs) throws SQLException {
        LOGGER.info("Please enter the product category:");
        String category = scanner.nextLine();
        ResultSet rs = Product.getProductsByCategory(category, dbs);
        while (rs.next()) {
            Product returnedProduct = new Product(rs.getInt("id"), rs.getString("name"), rs.getString(CATEGORY), rs.getDouble(PRICE), rs.getInt(QUANTITY));
            logProductInfo(returnedProduct);
        }
    }

    private void searchProductByPriceRange(DatabaseService dbs) throws SQLException {
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

    private void logProductInfo(Product product) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(product.toString());
        }
    }
    public void makeRequest(DatabaseService databaseService){

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


            Request request = new Request(requestID, productID, this.getEmail(), dateToStore, description);
            request.setDone(0);
            request.setSelected(0);
            databaseService.addObject(request,"Request");
            LOGGER.info("\nRequest Added Successfully, you can check your email for further information\n");
            EmailSender.sendEmail("s12027747@stu.najah.edu", "Installation Request", "Added successfully");

            datesArray.remove(dateIndex);
            Request.setDatesArray(datesArray);


        }catch ( Exception e ){
            Main.logger.info(e.getMessage());
            LOGGER.severe("Sorry, something went wrong!\n");
            exit(0);
        }

    }
    public void removeRequest(DatabaseService databaseService){


        try{


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            ArrayList<Request> returnedRequests = new ArrayList<>();
            ArrayList<String> datesArray = ( ArrayList<String> ) Request.getDatesArray();

            LOGGER.info("Please enter the number of the request you want to remove:\n");
            ResultSet rs = databaseService.executeQuery("SELECT * FROM Request WHERE userId ='" + this.getEmail() + "'", new ResultSetResultHandler());
            while ( rs.next() ){
               String date =  rs.getString(4).substring(0, 19);
               returnedRequests.add(new Request(rs.getInt(1), rs.getInt(2), rs.getString(3), LocalDateTime.parse(date, formatter), rs.getString(5)));
            }

            LOGGER.info("\n");
            String statementToPrint = "";
            for(int j=0; j<returnedRequests.size(); j++){
                statementToPrint = j+1 + "- " + returnedRequests.get(j);
                LOGGER.info(statementToPrint);
            }

            int requestID = scanner.nextInt();
            LocalDateTime removedDate = returnedRequests.get(requestID-1).getDate();

            datesArray.add(String.valueOf(removedDate));
            Request.setDatesArray(datesArray);


            databaseService.deleteObject(requestID, "Request");
            LOGGER.info("\nRequest Removed Successfully, you can check your email for further information\n");
            EmailSender.sendEmail("s12027747@stu.najah.edu", "Installation Request", "Removed successfully");

        }catch ( Exception e ){
            Main.logger.info(e.getMessage());
            LOGGER.severe("Sorry, something went wrong!");
            exit(0);
        }


    }



    public boolean isSignInStatus() {return signInStatus;}
    public String getEmail() {
        return email;
    }
    public char getRole() {
        return role;
    }
    public String getPassword() {
        return password;
    }
    public int getProfile() {
        return profile.getProfileId();
    }
    public Profile getProfileObject() {
        return profile;

    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(char role) {this.role = role;}
    public void setProfileId(int profileId) {
        this.profile.setProfileId(profileId);
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public void setSignInStatus(boolean signInStatus) {
        this.signInStatus = signInStatus;
    }

}


