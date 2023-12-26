package application.entities;

import application.Main;
import application.dataBase.Premetive_Objects.ResultSetResultHandler;
import application.services.DatabaseService;
import application.services.EmailSender;
import application.services.MessagesGenerator;
import application.services.ValidationUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import static application.Main.scanner;
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
    public User(String email, String password, char role, boolean SignInStatus,Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.signInStatus = SignInStatus;

    }
    public User(String email, String password, char role, boolean SignInStatus,int profileId) {
        this.email=email;
        this.password=password;
        this.role=role;
        profile=new Profile();
        this.profile.setProfileId(profileId);
        this.signInStatus = SignInStatus;

    }

    public void showDetails(Logger logger) {
        logger.info("Name: "+this.getProfileObject().getName());
        logger.info("Location: "+this.getProfileObject().getLocation());
        logger.info("Phone number: "+this.getProfileObject().getPhoneNumber());
        logger.info("Email : "+this.getEmail());
    }
    public void editDetails(int optionIn, String newValue, Logger logger) {
        switch (optionIn) {
            case 1 -> {
                // Update name
                logger.info("The current name is: " + this.getProfileObject().getName());
                this.getProfileObject().setName(newValue);
                logger.info("Updated name to: " + newValue);
            }
            case 2 -> {
                // Update email after password check
//                logger.info("Please enter your password for verification: ");
                // Assume a method 'verifyPassword' that checks the password
//                if (verifyPassword(newValue)) {
                    logger.info("The current Email is: " + this.getEmail());
                    // 'newValue' should be the new email in this case
                    // You might need to adjust how you pass and handle these values
                    if (ValidationUser.isValidEmail(newValue) && !ValidationUser.isExistEmail(newValue, new DatabaseService())) {
                        this.setEmail(newValue);
                        logger.info("Updated email to: " + newValue);
                    } else {
                        logger.info("Error: exist or invalid email, try again!");
                    }
//                } else {
//                    logger.info("Error: the password you entered not valid, try again!");
//                }
            }
            case 3 -> {
                // Update password
                // Here, you might need a separate method or way to verify the current password first
                logger.info("Updating password...");
                this.setPassword(newValue);
                logger.info("Password updated.");
            }
            case 4 -> {
                // Update location
                logger.info("The current location is: " + this.getProfileObject().getLocation());
                this.getProfileObject().setLocation(newValue);
                logger.info("Updated location to: " + newValue);
            }
            case 5 -> {
                // Update phone number
                logger.info("The current Phone Number is: " + this.getProfileObject().getPhoneNumber());
                this.getProfileObject().setPhoneNumber(newValue);
                logger.info("Updated phone number to: " + newValue);
            }
            default -> {
                // Handle default case
                logger.info("Invalid option.");
            }
        }
    }

//    public void editDetailsOLD(int optionIn, Logger logger, Scanner scanner) {
//        String newChoice;
//
//        switch ( optionIn ) {
//            case 1 -> {
//                logger.info("The current name is: " + this.getProfileObject().getName());
//                logger.info("Please enter the new name: ");
//                newChoice = scanner.nextLine();
//                this.getProfileObject().setName(newChoice);
//            }
//            case 2 -> {
//                logger.info("Please enter your password first: ");
//                newChoice = scanner.nextLine();
//                if ( this.getPassword().equals(newChoice) ) {
//                    logger.info("The current Email is: " + this.getEmail());
//                    logger.info("Please enter the new email: ");
//                    newChoice = scanner.nextLine();
//
//                    if (ValidationUser.isValidEmail(newChoice) && !ValidationUser.isExistEmail(newChoice,new DatabaseService()) && !newChoice.isEmpty())
//
//                        this.setEmail(newChoice);
//                    else logger.info("Error: exist or invalid email, try again!");
//                } else logger.info("Error: the password you entered not valid, try again!");
//            }
//            case 3 -> {
//                logger.info("Please enter your password first: ");
//                newChoice = scanner.nextLine();
//                if ( this.getPassword().equals(newChoice) ) {
//                    logger.info("Please enter new password: ");
//                    newChoice = scanner.nextLine();
//                    this.setPassword(newChoice);
//
//                } else logger.info("Error: the password you entered not valid, try again!");
//            }
//            case 4 -> {
//                logger.info("The current location is: " + this.getProfileObject().getLocation());
//                logger.info("Please enter the new location: ");
//                newChoice = scanner.nextLine();
//                this.getProfileObject().setLocation(newChoice);
//            }
//            case 5 -> {
//                logger.info("The current Phone Number is: " + this.getProfileObject().getPhoneNumber());
//                logger.info("Please enter the new Phone Number: ");
//                newChoice = scanner.nextLine();
//                this.getProfileObject().setPhoneNumber(newChoice);
//            }
//            default -> {
//            }
//        }
//    }

    public void viewRequisitesHistory(DatabaseService databaseService){
        ResultSet resultSet;
        Request request;
        List<Request> availableRequests =new ArrayList<>();

        try {
            resultSet=  databaseService.executeQuery("SELECT * FROM `Request` WHERE `done` =true AND `userId`="+this.email , new ResultSetResultHandler());
            while ( resultSet.next() ) {
                request= new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate().atTime(LocalTime.MIDNIGHT),
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
            resultSet=  databaseService.executeQuery("SELECT * FROM `Request` WHERE `done` =false AND `userId`="+this.email , new ResultSetResultHandler());
            while ( resultSet.next() ) {
                request= new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate().atTime(LocalTime.MIDNIGHT),
                        resultSet.getString(5));

                Main.logger.info(request.toString());
                availableRequests.add(request);
            }
        } catch (SQLException e) {
            Main.logger.info("something went wrong\n");
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
                            logger.info(returnedProduct + "\n");
                        }
                    }
                    case "2" -> {
                        errorMsg = "by ID";
                        logger.info("\nPlease enter the product ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        returnedProduct = Product.getProductById(id, dbs);
                        logger.info(returnedProduct.toString() + "\n");
                    }
                    case "3" -> {
                        errorMsg = "by name";
                        logger.info("Please enter the product name:");
                        String name = scanner.nextLine();
                        if(Product.getProductByName(name, dbs) == null)
                            logger.info("There is no products with the name you entered\n");
                        else
                            logger.info(Product.getProductByName(name, dbs) + "\n");
                    }
                    case "4" -> {
                        errorMsg = "by category";
                        logger.info("Please enter the product category:");
                        String category = scanner.nextLine();
                        rs = Product.getProductsByCategory(category, dbs);
                        while ( rs.next() ) {
                            returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                            logger.info(returnedProduct + "\n");
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
                            logger.info(returnedProduct + "\n");
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
            for(int j=0; j<returnedRequests.size(); j++)
                logger.info(j+1 + "- " + returnedRequests.get(j).toString());
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
        DatabaseService dbs = new DatabaseService();
        //todo: connect to db
        this.email = email;
        dbs=null;
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
//    @Override
//    public String toString(){
//        showDetails(Logger.getLogger("logger"));
//        return "Email: "+this.email+"\n"+
//                "Password: "+this.password+"\n";
//
//    }
}


