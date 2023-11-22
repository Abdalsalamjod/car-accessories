package Application;

import Application.Entities.Product;
import Application.Entities.User;
import Application.Services.*;

import java.sql.SQLException;
import java.util.Scanner;


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

    public static void userUtility(DatabaseService databaseService ){



    }

    public static void manegerUtility(DatabaseService databaseService) {
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

                    try {
                        boolean productAdded = databaseService.addObject(product, "Product");
                        if (productAdded) {
                            logger.info("Product added successfully.");
                        } else {
                            logger.info("Failed to add the product.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    System.out.print("Enter the ID of the product to delete: ");
                    int deleteId = scanner.nextInt();
                    try {
                        boolean productDeleted = databaseService.deleteObject(deleteId, "Product");
                        if (productDeleted) {
                            logger.info("Product with ID " + deleteId + " deleted successfully.");
                        } else {
                            logger.info("Failed to delete the product.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    System.out.print("Enter the ID of the product to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    System.out.print("Enter the new price: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("Enter the new quantity: ");
                    int newQuantity = scanner.nextInt();

//                                    try {
//                                        boolean productUpdated = databaseService.updateProduct(updateId, newPrice, newQuantity);
//                                        if (productUpdated) {
//                                            logger.info("Product with ID " + updateId + " updated successfully.");
//                                        } else {
//                                            logger.info("Failed to update the product.");
//                                        }
//                                    } catch (SQLException e) {
//                                        e.printStackTrace();
//                                    }
                    break;

                case 4:
                    System.out.print("Enter the name or ID of the product to search: ");
                    String searchTerm = scanner.nextLine();

//                                    try {
//                                        List<Product> products = databaseService.searchProduct(searchTerm);
//                                        if (products.isEmpty()) {
//                                            logger.info("No products found matching the search term.");
//                                        } else {
//                                            logger.info("Products found:");
//                                            for (Product p : products) {
//                                                logger.info("ID: " + p.getId() + ", Name: " + p.getName() + ", Category: " + p.getCategory() + ", Price: " + p.getPrice() + ", Quantity: " + p.getQuantity());
//                                            }
//                                        }
//                                    } catch (SQLException e) {
//                                        e.printStackTrace();
//                                    }
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
