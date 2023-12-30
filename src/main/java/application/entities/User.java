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
import java.util.logging.Logger;

import static application.Main.*;
import static application.services.MessagesGenerator.logger;
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
    public void editDetails(int optionIn, String newValue, Logger logger) {        DatabaseService dbs = new DatabaseService();

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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while ( resultSet.next() ) {
                String date =  resultSet.getString(4).substring(0, 19);
                request= new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        LocalDateTime.parse(date, formatter),
                        resultSet.getString(5));

                Main.logger.info(request.toString());
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (resultSet.next()) {
                String date =  resultSet.getString(4).substring(0, 19);
                request = new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        LocalDateTime.parse(date, formatter),
                        resultSet.getString(5)
                );

                Main.logger.info(request.toString());
                availableRequests.add(request);
            }

        } catch (Exception e) {
            Main.logger.severe("something went wrong\n");
        }

        return availableRequests;
    }



    public void browsProducts(DatabaseService dbs){

        ResultSet rs;
        Product returnedProduct;
        String errorMsg = null;

        boolean iterator = true;
        while (iterator){

            MessagesGenerator.listGenerator("browsProductsList");
            String option = scanner.nextLine();

            try{

                switch ( option ) {

                    case "1" -> {
                        errorMsg = "";
                        rs = Product.getAllProducts(dbs);
                        while ( rs.next() ) {
                            returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                            logger.info(returnedProduct.toString());
                        }
                    }
                    case "2" -> {
                        errorMsg = "by ID";
                        logger.info("\nPlease enter the product ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        returnedProduct = Product.getProductById(id, dbs);
                        logger.info(returnedProduct.toString());
                    }
                    case "3" -> {
                        errorMsg = "by name";
                        logger.info("Please enter the product name:");
                        String name = scanner.nextLine();
                        if(Product.getProductByName(name, dbs) == null)
                            logger.info("There is no products with the name you entered\n");
                        else
                            logger.info(Product.getProductByName(name, dbs).toString());
                    }
                    case "4" -> {
                        errorMsg = "by category";
                        logger.info("Please enter the product category:");
                        String category = scanner.nextLine();
                        rs = Product.getProductsByCategory(category, dbs);
                        while ( rs.next() ) {
                            returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                            logger.info(returnedProduct.toString());
                        }
                    }
                    case "5" -> {
                        errorMsg = "by price range";
                        logger.info("Please enter the lower price:");
                        double lower = scanner.nextDouble();
                        logger.info("Please enter the upper price:");
                        double upper = scanner.nextDouble();
                        rs = Product.getProductsByPriceRange(lower, upper, dbs);
                        while ( rs.next() ) {
                            returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                            logger.info(returnedProduct.toString());
                        }
                    }
                    case "6" -> iterator = false;
                    default -> logger.info("Invalid choice! \nPlease enter 1, 2, ... 6.\n");

                }

            }catch ( Exception e){
                logger.info("Cannot get the product " + errorMsg + ", something went wrong!\n");
                exit(0);
            }

        }

    }

    public void makeRequest(DatabaseService databaseService){

        try{

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Request.initializeDatesArray();
            ArrayList<String> datesArray = ( ArrayList<String> ) Request.getDatesArray();


            logger.info("Please enter the request ID: ");
            int requestID = scanner.nextInt();


            logger.info("\nPlease select the product you want to install\n");
            ResultSet rs = Product.getAllProductsNames(databaseService);
            logger.info("ID" + "  " + "Name\n");
            while ( rs.next() )
                logger.info(String.format("%d  %s%n", rs.getInt(1), rs.getString(2)));


            logger.info("\n");
            int productID = scanner.nextInt();


            logger.info("\nPlease select one of the available dates\n");
            for(int i=0; i<datesArray.size(); i++)
                logger.info(String.format("%d- %s%n", i + 1, datesArray.get(i)));
            int dateIndex = scanner.nextInt() - 1;
            String selectedDate = datesArray.get(dateIndex);
            LocalDateTime dateToStore = LocalDateTime.parse(selectedDate, formatter);


            logger.info("\nPlease enter the description, what exactly do you want to do:\n");
            scanner.nextLine();
            String description = scanner.nextLine();


            Request request = new Request(requestID, productID, this.getEmail(), dateToStore, description);
            request.setDone(0);
            request.setSelected(0);
            databaseService.addObject(request,"Request");
            logger.info("\nRequest Added Successfully, you can check your email for further information\n");
            EmailSender.sendEmail("s12027747@stu.najah.edu", "Installation Request", "Added successfully");

            datesArray.remove(dateIndex);
            Request.setDatesArray(datesArray);


        }catch ( Exception e ){
            Main.logger.info(e.getMessage());
            logger.severe("Sorry, something went wrong!\n");
            exit(0);
        }

    }
    public void removeRequest(DatabaseService databaseService){


        try{


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ArrayList<Request> returnedRequests = new ArrayList<>();
            ArrayList<String> datesArray = ( ArrayList<String> ) Request.getDatesArray();

            logger.info("Please enter the number of the request you want to remove:\n");
            ResultSet rs = databaseService.executeQuery("SELECT * FROM Request WHERE userId ='" + this.getEmail() + "'", new ResultSetResultHandler());
            while ( rs.next() ){
               String date =  rs.getString(4).substring(0, 19);
               returnedRequests.add(new Request(rs.getInt(1), rs.getInt(2), rs.getString(3), LocalDateTime.parse(date, formatter), rs.getString(5)));
            }

            logger.info("\n");
            String statementToPrint = "";
            for(int j=0; j<returnedRequests.size(); j++){
                statementToPrint = j+1 + "- " + returnedRequests.get(j);
                logger.info(statementToPrint);
            }

            int requestID = scanner.nextInt();
            LocalDateTime removedDate = returnedRequests.get(requestID-1).getDate();

            datesArray.add(String.valueOf(removedDate));
            Request.setDatesArray(datesArray);


            databaseService.deleteObject(requestID, "Request");
            logger.info("\nRequest Removed Successfully, you can check your email for further information\n");
            EmailSender.sendEmail("s12027747@stu.najah.edu", "Installation Request", "Removed successfully");

        }catch ( Exception e ){
            Main.logger.info(e.getMessage());
            logger.severe("Sorry, something went wrong!");
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


