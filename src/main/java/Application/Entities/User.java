package Application.Entities;

import Application.Services.DatabaseService;
import Application.Services.ValidationUser;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static Application.Main.scanner;
import static Application.Services.MessagesGenerator.logger;


public class User {
    public String email;
    public String password;
    public String role;
    public Profile profile;
    public List<Request> requests;
    public boolean SignInStatus;

    public User() {

    }
    public User(String email, String password, String role, boolean SignInStatus,Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.SignInStatus = SignInStatus;
        this.requests=new ArrayList<>();
    }


//methods

    public void showDetails(Logger logger) {
        logger.info("Name: "+this.getProfile().getName());
        logger.info("Location: "+this.getProfile().getLocation());
        logger.info("Phone number: "+this.getProfile().getPhoneNumber());
        logger.info("Email : "+this.getEmail());
    }
    public void editDetails(int optionIn, Logger logger, Scanner scanner) {
        String newChoice;
        switch ( optionIn ) {
            case 1 -> {
                logger.info("The current name is: " + this.getProfile().getName());
                logger.info("Please enter the new name: ");
                newChoice = scanner.nextLine();
                this.getProfile().setName(newChoice);
            }
            case 2 -> {
                logger.info("Please enter your password first: ");
                newChoice = scanner.nextLine();
                if ( this.getPassword().equals(newChoice) ) {
                    logger.info("The current Email is: " + this.getEmail());
                    logger.info("Please enter the new email: ");
                    newChoice = scanner.nextLine();
                    if ( ValidationUser.isValidEmail(newChoice) && !ValidationUser.isExistEmail(newChoice) && !newChoice.isEmpty() )
                        this.setEmail(newChoice);
                    else logger.info("Error: exist or invalid email, try again!");
                } else logger.info("Error: the password you entered not valid, try again!");
            }
            case 3 -> {
                logger.info("Please enter your password first: ");
                newChoice = scanner.nextLine();
                if ( this.getPassword().equals(newChoice) ) {
                    logger.info("Please enter new password: ");
                    newChoice = scanner.nextLine();
                    this.setPassword(newChoice);
                } else logger.info("Error: the password you entered not valid, try again!");
            }
            case 4 -> {
                logger.info("The current location is: " + this.getProfile().getLocation());
                logger.info("Please enter the new location: ");
                newChoice = scanner.nextLine();
                this.getProfile().setLocation(newChoice);
            }
            case 5 -> {
                logger.info("The current Phone Number is: " + this.getProfile().getPhoneNumber());
                logger.info("Please enter the new Phone Number: ");
                newChoice = scanner.nextLine();
                this.getProfile().setPhoneNumber(newChoice);
            }
            default -> {
            }
        }
    }
    public boolean makeRequest(Request request){
        try{
            DatabaseService dbs = new DatabaseService();
            return dbs.addObject(request,"Request");

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean removeRequest(int id){
        try{
            DatabaseService dbs = new DatabaseService();
            dbs.deleteObject(id, "Request");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
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

    public void browsProducts(int option, DatabaseService dbs){

        ResultSet rs;
        Product returnedProduct;
        String errorMsg = null;
        try{
            switch ( option ) {
                case 1 -> {
                    errorMsg = "";
                    rs = Product.getAllProducts(dbs);
                    while ( rs.next() ) {
                        returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                        logger.info(returnedProduct + "\n");
                    }
                }
                case 2 -> {
                    errorMsg = "by ID";
                    logger.info("\nPlease enter the product ID:");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    returnedProduct = Product.getProductById(id, dbs);
                    logger.info(returnedProduct.toString() + "\n");
                }
                case 3 -> {
                    errorMsg = "by name";
                    logger.info("Please enter the product name:");
                    String name = scanner.nextLine();
                    if(Product.getProductByName(name, dbs) == null)
                        logger.info("There is no products with the name you entered\n");
                    else
                        logger.info(Product.getProductByName(name, dbs) + "\n");
                }
                case 4 -> {
                    errorMsg = "by category";
                    logger.info("Please enter the product category:");
                    String category = scanner.nextLine();
                    rs = Product.getProductsByCategory(category, dbs);
                    while ( rs.next() ) {
                        returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                        logger.info(returnedProduct + "\n");
                    }
                }
                case 5 -> {
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
                case 6 -> {
                    logger.info("Good bye, have a nice day.");
                    System.exit(0);
                }
                default -> logger.info("Invalid choice! \nPlease enter 1, 2, ... 6.\n");
            }

        }catch ( Exception e){
            logger.info("Cannot get the product " + errorMsg + ", something went wrong\n");
        }

    }













//getter
    public boolean isSignInStatus() {return SignInStatus;}
    public String getEmail() {
        return email;
    }
    public String getRole() {return role;}
    public String getPassword() {
        return password;
    }
    public Profile getProfile() {
        return profile;
    }
    public List<Request> getRequests() {
        return requests;
    }

//setter
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {this.role = role;}
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
    public void setSignInStatus(boolean signInStatus) {
        SignInStatus = signInStatus;
    }


}


