package Application;

import Application.DataBase.Premetive_Objects.ResultSetResultHandler;
import Application.DataBase.UserDefinedTypes.ProductResultHandler;
import Application.Entities.Product;
import Application.Entities.User;
import Application.Services.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;


import static Application.Main.scanner;
import static Application.Services.MessagesGenerator.logger;

public class MainUtility {


    public static int signUpUtility(String email,String password){

       int validationStatus = ValidationUser.validation(email, password);
        if (validationStatus == ValidationUser.VALID) {
            SignUp signUp = new SignUp(email,password,false,validationStatus);
            signUp.creatAccount();
        }
        return validationStatus;
    }

    public static int signInUtility(String email, String password, User currentUser){

      int   validationStatus = ValidationUser.validation(email, password);
        if (validationStatus == ValidationUser.VALID)
        {
            SignIn signIn = new SignIn(email, password, "user", false, validationStatus);
            currentUser = signIn.performLogIn();
        }
        return validationStatus;
    }

    public static void userUtility(DatabaseService databaseService, User currentUser){

        while (true) {
            MessagesGenerator.listGenerator("userList");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (option) {
                case 1:

                    break;
                case 2:
                    currentUser.showDetails(logger);

                    break;
                case 3:
                    MessagesGenerator.listGenerator("editProfile");
                    int optionIn = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    currentUser.editDetails(optionIn,logger,scanner);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
            }
        }
    }


    public static void adminUtility(DatabaseService databaseService) {

        String valid;
        while (true) {
            MessagesGenerator.listGenerator("productList");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (option) {

                case 1:
                    logger.info("Enter product details:");
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Quantity: ");
                    int quantity = scanner.nextInt();

                    Product product = new Product(id, name, category, price, quantity);
                    valid = product.validInformation();
                    if(valid.equals("")){

                        try {
                             databaseService.addObject(product, "Product");
                             logger.info("Product added successfully.");

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        logger.info("Cannot add product, it seems that there is invalid information");


                    break;

                case 2:
                    System.out.print("Enter the ID of the product to delete: ");
                    int deleteId = scanner.nextInt();
                    try {
                        boolean productDeleted = databaseService.deleteObject(deleteId, "Product");
                        if (productDeleted) {
                            logger.info("Product with ID " + deleteId + " deleted successfully.");
                        }
                    } catch (SQLException e) {
                        logger.info("Failed to delete the product.");
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    logger.info("Enter the ID of the product to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    try{

                        Product returnedProduct = databaseService.executeQuery("SELECT * FROM Product WHERE id=" + updateId, new ProductResultHandler());
                        System.out.println("The product:\n ID: " + returnedProduct.getId()+ "\nName: " + returnedProduct.getName() + "\nCategory: " + returnedProduct.getCategory() + "\nPrice: " + returnedProduct.getPrice() + "\nQuantity: " + returnedProduct.getQuantity());
                        System.out.print("Please enter the new price: ");
                        double updatedPrice = scanner.nextDouble();
                        System.out.print("Please enter the new quantity: ");
                        int updatedQuantity = scanner.nextInt();
                        Product updatedProduct = new Product(returnedProduct.getId(), returnedProduct.getName(), returnedProduct.getCategory(), updatedPrice, updatedQuantity);
                        valid = updatedProduct.validInformation();

                        if(valid.equals("")){
                            databaseService.updateObject(updatedProduct, "Product", "id");
                            logger.info("\n" + "The product has updated successfully !");
                        }
                        else{
                            logger.info("\n" + valid);
                        }
                    }catch ( Exception e ){
                        logger.info("Cannot update product, it seems that the product you entered does not exist !");
                        e.printStackTrace();
                    }

                    break;

                case 4:
                    logger.info("Please choose how do you want to search:\n1-By ID\n2-By name\n3-By category\n4-By price range ");
                    int scanned = scanner.nextInt();
                    switch (scanned){
                        case 1:
                            logger.info("Please enter the ID: ");
                            int ID = scanner.nextInt();
                            try{
                                Product returnedProduct = databaseService.executeQuery("SELECT * FROM Product WHERE id=" + ID, new ProductResultHandler());
                                System.out.println(returnedProduct.toString());
                            }catch ( SQLException e ){
                                logger.info("Cannot get product, it seems that the product you entered does not exist !");
                            }
                            break;
                        case 2:
                            logger.info("Please enter the name: ");
                            String name_2 = scanner.nextLine();
                            try{
                                Product returnedProduct = databaseService.executeQuery("SELECT * FROM Product WHERE name='" + name_2 + "'", new ProductResultHandler());
                                System.out.println(returnedProduct.toString());
                            }catch ( SQLException e ){
                                logger.info("Cannot get product, it seems that the product you entered does not exist !");
                            }
                            break;
                        case 3:
                            logger.info("Please enter the category: ");
                            String category_2 = scanner.nextLine();
                            try{
                                ResultSet rs = databaseService.executeQuery("SELECT * FROM Product WHERE category='" + category_2 + "'", new ResultSetResultHandler());
                                while ( rs.next() ){
                                    Product returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                                    System.out.println(returnedProduct);
                                }

                            }catch ( SQLException e ){
                                logger.info("There is some error");
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            logger.info("Please enter the lower price: ");
                            double lowerPrice = scanner.nextDouble();
                            logger.info("Please enter the upper price: ");
                            double upperPrice = scanner.nextDouble();

                            try{
                                ResultSet rs = databaseService.executeQuery("SELECT * FROM Product WHERE price BETWEEN " + lowerPrice + " AND " + upperPrice, new ResultSetResultHandler());
                                while ( rs.next() ){
                                    Product returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                                    System.out.println(returnedProduct);
                                }

                            }catch ( SQLException e ){
                                logger.info("There is some error");
                                e.printStackTrace();
                            }

                        default:
                            logger.info("Invalid option. Please select a valid option.");


                    }


                    break;

                case 5:
                    databaseService.closeConnection();
                    logger.info("back to main menu!");
                    System.exit(0);
                    break;

                default:
                    logger.info("Invalid option. Please select a valid option.");
            }
        }
    }

    public static void installerUtility(DatabaseService databaseService) {
    }

}
