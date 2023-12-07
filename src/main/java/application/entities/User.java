package application.entities;

import application.dataBase.Premetive_Objects.ResultSetResultHandler;
import application.services.DatabaseService;
import application.services.EmailSender;
import application.services.MessagesGenerator;
import application.services.ValidationUser;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;
import static application.Main.scanner;
import static application.services.MessagesGenerator.logger;
import static java.lang.System.exit;


public class User {

    protected String email;
    protected String password;
    protected char role;
    protected Profile profile;
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




    public void showDetails(Logger logger) {
        logger.info("Name: "+this.getProfileObject().getName());
        logger.info("Location: "+this.getProfileObject().getLocation());
        logger.info("Phone number: "+this.getProfileObject().getPhoneNumber());
        logger.info("Email : "+this.getEmail());
    }
    public void editDetails(int optionIn, Logger logger, Scanner scanner) {
        String newChoice;

        switch (optionIn) {
            case 1:
                logger.info("The current name is: " + this.getProfileObject().getName());
                logger.info("Please enter the new name: ");
                newChoice = scanner.nextLine();
                this.getProfileObject().setName(newChoice);
                break;
            case 2:

                logger.info("Please enter your password first: ");
                newChoice = scanner.nextLine();
                if (this.getPassword().equals(newChoice)) {
                    logger.info("The current Email is: " + this.getEmail());
                    logger.info("Please enter the new email: ");
                    newChoice = scanner.nextLine();
                    if (ValidationUser.isValidEmail(newChoice) && !ValidationUser.isExistEmail(newChoice) && !newChoice.isEmpty())
                        this.setEmail(newChoice);
                    else logger.info("Error: exist or invalid email, try again!");
                } else logger.info("Error: the password you entered not valid, try again!");
                break;
            case 3 :
                    logger.info("Please enter your password first: ");
                    newChoice = scanner.nextLine();
                    if (this.getPassword().equals(newChoice)) {
                        logger.info("Please enter new password: ");
                        newChoice = scanner.nextLine();
                        this.setPassword(newChoice);

                    } else
                        logger.info("Error: the password you entered not valid, try again!");
                    break;

            case 4:
                logger.info("The current location is: " + this.getProfileObject().getLocation());
                logger.info("Please enter the new location: ");
                newChoice = scanner.nextLine();
                this.getProfileObject().setLocation(newChoice);
                break;
            case 5:
                logger.info("The current Phone Number is: " + this.getProfileObject().getPhoneNumber());
                logger.info("Please enter the new Phone Number: ");
                newChoice = scanner.nextLine();
                this.getProfileObject().setPhoneNumber(newChoice);
                break;
            default:
                break;
        }
    }
    public void viewRequisitesHistory(){
        //TODO: connect to DB
//        for (Order order:this.orderHistory)
//        {
//            logger.info(order.toString());
//        }
    }
    public void viewInstallationRequests() {
        //TODO: connect to DB
//        for (InstallationRequest request :this.installationRequests)
//        {
//            logger.info(requests.toString());
//        }
    }
    public void setEmail(String email) {
        DatabaseService dbs = new DatabaseService();
        //todo: connect to db
        this.email = email;
        dbs=null;
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
            databaseService.addObject(request,"Request");
            logger.info("\nRequest Added Successfully, you can check your email for further information\n");
            EmailSender.sendEmail("s12027747@stu.najah.edu", "Installation Request", "Added successfully");

            datesArray.remove(dateIndex);
            Request.setDatesArray(datesArray);


        }catch ( Exception e ){
            logger.info("Sorry, something went wrong!\n");
            exit(0);
        }

    }
    public void removeRequest(DatabaseService databaseService){


        try{


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ArrayList<Request> returnedRequests = new ArrayList<>();
            ArrayList<String> datesArray = ( ArrayList<String> ) Request.getDatesArray();

            logger.info("Please enter the request ID you want to remove:\n");
            ResultSet rs = databaseService.executeQuery("SELECT * FROM Request WHERE userId ='" + this.getEmail() + "'", new ResultSetResultHandler());
            while ( rs.next() ){
               returnedRequests.add(new Request(rs.getInt(1), rs.getInt(2), rs.getString(3), LocalDateTime.parse(rs.getString(4), formatter), rs.getString(5)));
            }

            logger.info("\n");
            int requestID = scanner.nextInt();
            LocalDateTime removedDate = returnedRequests.get(requestID-1).getDate();

            datesArray.add(String.valueOf(removedDate));
            Request.setDatesArray(datesArray);


            databaseService.deleteObject(requestID, "Request");
            logger.info("\nRequest Removed Successfully, you can check your email for further information\n");
            EmailSender.sendEmail("s12027747@stu.najah.edu", "Installation Request", "Removed successfully");

        }catch ( Exception e ){
            logger.info("Sorry, something went wrong!");
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


