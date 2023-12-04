package Application.Entities;

import Application.DataBase.UserDefinedTypes.ProductResultHandler;
import Application.Services.DatabaseService;

import java.sql.ResultSet;
import java.util.ArrayList;

import static Application.Main.scanner;
import static Application.Services.MessagesGenerator.logger;

public class Admin extends User{

    public Admin(String email, String password, char role, boolean SignInStatus, Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.signInStatus = SignInStatus;

    }

    public void manageProducts(int option, DatabaseService dbs){

        ResultSet rs;
        Product returnedProduct;
        String errorMsg = null;
        int id, quantity;
        double price;
        String name, category;

        try{
            switch ( option ) {
                case 1 -> {
                    errorMsg = "show all products";
                    rs = Product.getAllProducts(dbs);
                    while ( rs.next() ) {
                        returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                        logger.info(returnedProduct + "\n");
                    }
                }
                case 2 -> {
                    errorMsg = "add the product";
                    logger.info("\nPlease enter the product ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    logger.info("Please enter the product name: ");
                    name = scanner.nextLine();
                    logger.info("Please enter the product category: ");
                    category = scanner.nextLine();
                    logger.info("Please enter the product price: ");
                    price = scanner.nextDouble();
                    logger.info("Please enter the product quantity: ");
                    quantity = scanner.nextInt();
                    Product product = new Product(id, name, category, price, quantity);
                    String valid =  product.validInformation();
                    if(valid.equals("")){
                        dbs.addObject(product, "Product");
                        logger.info("Product Added successfully!");
                    }
                    else
                        logger.info(valid + "\n");

                }
                case 3 -> {
                    errorMsg = "delete the product";
                    logger.info("\nPlease enter the product ID: ");
                    id = scanner.nextInt();
                    boolean done = dbs.deleteObject(id, "Product");
                    if(done)
                        logger.info("Product Deleted successfully!");

                }
                case 4 -> {
                    errorMsg = "update the product";
                    logger.info("\nPlease enter the ID of the product to be updated: ");
                    id = scanner.nextInt();
                    logger.info("\nPlease enter the new price: ");
                    price = scanner.nextDouble();
                    logger.info("\nPlease enter the new quantity: ");
                    quantity = scanner.nextInt();
                    if(price > 0 && quantity > 0) {
                        Product oldProduct = dbs.executeQuery("SELECT * FROM Product WHERE id=" + id, new ProductResultHandler());
                        Product newProduct = new Product(oldProduct.getId(), oldProduct.getName(), oldProduct.getCategory(), price, quantity);
                        dbs.updateObject(newProduct, "Product", "id");
                        logger.info("Product updated successfully!");
                    } else {
                        logger.info("Price and Quantity must be greater than zero!\n");
                    }

                }
                case 5 -> {
                    logger.info("Good bye, have a nice day.");
                    System.exit(0);
                }
                default -> logger.info("Invalid choice! \nPlease enter 1, 2, ... 6.\n");
            }

        }catch ( Exception e){
            logger.info("Cannot " + errorMsg + ", something went wrong!\n");
        }

    }

}
