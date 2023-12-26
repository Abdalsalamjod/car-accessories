package application.entities;

import application.LoggerUtility;
import application.database.premitive_objects.ResultSetResultHandler;
import application.database.user_defined_types.ProductResultHandler;
import application.services.DatabaseService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static application.Main.scanner;
import static application.services.MessagesGenerator.logger;

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
        String tableName="Product";

        try{
            switch ( option ) {
                case 1 -> {
                    errorMsg = "show all products";
                    rs = Product.getAllProducts(dbs);
                    while ( rs.next() ) {
                        returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                        logger.info(returnedProduct.toString());
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
                        dbs.addObject(product, tableName);
                        logger.info("Product Added successfully!");
                    }
                    else
                        logger.severe(valid + "\n");

                }
                case 3 -> {
                    errorMsg = "delete the product";
                    logger.info("\nPlease enter the product ID: ");
                    id = scanner.nextInt();
                    boolean done = dbs.deleteObject(id, tableName);
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
                        dbs.updateObject(newProduct, tableName, "id");
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

    public void manageAcounts(DatabaseService dbs, User user ,int option, String newValue) {
        switch (option){

            case 1 ->{
                user.showDetails( LoggerUtility.getLogger());
            }  case 2 ->{
                user.editDetails(1,newValue,LoggerUtility.getLogger());
            }  case 3 ->{
                user.editDetails(2,newValue,LoggerUtility.getLogger());
            }  case 4 ->{
                user.editDetails(3,newValue,LoggerUtility.getLogger());
            }  case 5 ->{
                user.editDetails(4,newValue,LoggerUtility.getLogger());
            }  case 6 ->{
                user.editDetails(5,newValue,LoggerUtility.getLogger());

            }  case 7 ->{
//           TODO:     dbs.deleteObject(user.getEmail(),"user");
            }  case 8->{
                System.exit(0);
            }default->{
                logger.severe("please enter valid input ");
            }

        }

    }
    public List<User> viewUsers(DatabaseService dbs)
    {
        ResultSet resultSet;
        ArrayList<User> users= new ArrayList<>();
        User user;
        try {
            resultSet=dbs.executeQuery("SELECT * FROM `user`",new ResultSetResultHandler());
            while ( resultSet.next() ) {
                user = new User(resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString(role).charAt(0),
                        resultSet.getBoolean("signInStatus"),
                        resultSet.getInt("profileId")
                );
                users.add(user);
                user.showDetails(logger);
                logger.info("Password: "+ user.getPassword());
            }
        } catch (SQLException e) {
            logger.severe("Error: in viewUsers ");
        }
        return users;
    }

}
