package Application;

import Application.Entities.Product;
import Application.Services.*;
import Application.Entities.User;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;


public class Main {
    private static Logger logger = LoggerUtility.getLogger();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String email = "";
        String password = "";
        int validationStatus;

        User currentUser = null;

        while (true) {
            logger.info("\n _____________________________________________________\n");
            logger.info("|       Welcome to HalaCar  accessories System :)     |\n");
            logger.info("| 1-If you want to SignUp                             |\n");
            logger.info("| 2-If you want to SignIn                             |\n");
            logger.info("|_____________________________________________________|\n");
            String choice = scanner.nextLine();
            if (choice.equals("1"))
            {

                logger.info("Enter your email:");
                email = scanner.nextLine();
                logger.info("Enter your password:");
                password = scanner.nextLine();

                validationStatus = ValidationUser.validation(email, password);

                if (validationStatus == ValidationUser.VALID) {
                    SignUp signUp = new SignUp();
                    signUp.email = email;
                    signUp.password = password;
                    signUp.creatAccount();
                    logger.info("Account created successfully!");
                } else {
                    logger.info(MessagesGenerator.SigningMessages(validationStatus));
                }
            }
            else if (choice.equals("2")) {
                // Sign In
                logger.info("Enter your email:");
                email = scanner.nextLine();
                logger.info("Enter your password:");
                password = scanner.nextLine();

                validationStatus = ValidationUser.validation(email, password);

                if (validationStatus == ValidationUser.VALID) {
                    SignIn signIn = new SignIn(email, password, "user", false, validationStatus);
                    currentUser = signIn.performLogIn();

                    if (currentUser != null && currentUser.isSignInStatus()) {
                        //  logger.info("Sign In successful. Welcome, " + currentUser.getName());
                        logger.info(MessagesGenerator.SigningMessages(validationStatus));
                    //#####################################################################################################
                        DatabaseService databaseService = new DatabaseService();
                        while (true) {
                            logger.info("\nMenu:\n");
                            logger.info("1. Add a product\n");
                            logger.info("2. Delete a product\n");
                            logger.info("3. Update a product\n");
                            logger.info("4. Search for a product\n");
                            logger.info("5. Exit\n");
                            System.out.print("Select an option: \n");

                            Scanner scanner = new Scanner(System.in);
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
                    //#####################################################################################################



                } else {
                        logger.info("Sign In failed. Please try again.");
                    }
                } else {
                    logger.info(MessagesGenerator.SigningMessages(validationStatus));
                }
            }
            else if (choice.equals("3")) {
                // Quit the application
                System.exit(0);
            }
            else {
                logger.info("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
}