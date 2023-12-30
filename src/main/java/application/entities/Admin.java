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

    public Admin(String email, String password, char role, boolean signInStatus, Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.signInStatus = signInStatus;

    }

    public void manageProducts(int option, DatabaseService dbs){
        String tableName = "Product";

        try {
            switch (option) {
                case 1 -> showAllProducts(dbs);
                case 2 -> addProduct(dbs, tableName);
                case 3 -> deleteProduct(dbs, tableName);
                case 4 -> updateProduct(dbs, tableName);
                case 5 -> exitApplication();
                default -> logger.info("Invalid choice! \nPlease enter 1, 2, ... 6.\n");
            }
        } catch (Exception e) {
            logger.info("An error occurred during the operation.\n");
        }
    }

    private void showAllProducts(DatabaseService dbs) throws SQLException {
        ResultSet rs = Product.getAllProducts(dbs);
        while (rs.next()) {
            Product returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
            if (returnedProduct != null)
                logger.info(returnedProduct.toString());
        }
    }
    private void addProduct(DatabaseService dbs, String tableName) throws SQLException {
        logger.info("\nPlease enter the product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        logger.info("Please enter the product name: ");
        String name = scanner.nextLine();
        logger.info("Please enter the product category: ");
        String category = scanner.nextLine();
        logger.info("Please enter the product price: ");
        double price = scanner.nextDouble();
        logger.info("Please enter the product quantity: ");
        int quantity = scanner.nextInt();
        Product product = new Product(id, name, category, price, quantity);
        String valid = product.validInformation();
        if (valid.equals("")) {
            dbs.addObject(product, tableName);
            logger.info("Product Added successfully!");
        } else {
            logger.severe(valid + "\n");
        }
    }
    private void deleteProduct(DatabaseService dbs, String tableName) throws SQLException {
        logger.info("\nPlease enter the product ID: ");
        int id = scanner.nextInt();
        boolean done = dbs.deleteObject(id, tableName);
        if (done)
            logger.info("Product Deleted successfully!");
    }
    private void updateProduct(DatabaseService dbs, String tableName) throws SQLException {
        logger.info("\nPlease enter the ID of the product to be updated: ");
        int id = scanner.nextInt();
        logger.info("\nPlease enter the new price: ");
        double price = scanner.nextDouble();
        logger.info("\nPlease enter the new quantity: ");
        int quantity = scanner.nextInt();
        if (price > 0 && quantity > 0) {
            Product oldProduct = dbs.executeQuery("SELECT * FROM Product WHERE id=" + id, new ProductResultHandler());
            Product newProduct = new Product(oldProduct.getId(), oldProduct.getName(), oldProduct.getCategory(), price, quantity);
            try {
                dbs.updateObject(newProduct, tableName, "id");
            } catch (Exception e) {
                logger.severe("Error in updating Product\n");
            }
            logger.info("Product updated successfully!\n");
        } else {
            logger.info("Price and Quantity must be greater than zero!\n");
        }
    }
    private void exitApplication() {
        logger.info("Good bye, have a nice day.");
        System.exit(0);
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


    public List<User> viewUsers(DatabaseService dbs) {
        ResultSet resultSet;
        ArrayList<User> users= new ArrayList<>();
        User user;
        try {
            resultSet=dbs.executeQuery("SELECT * FROM `user`",new ResultSetResultHandler());
            while ( resultSet.next() ) {
                user = new User(resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role").charAt(0),
                        resultSet.getBoolean("signInStatus"),
                        resultSet.getInt("profileId")
                );
                Profile profile1 =dbs.executeQuery("SELECT * FROM `Profile` WHERE `profileID` = "+user.getProfile(),new application.database.user_defined_types.ProfileResultHandler());
                user.setProfile(profile1);
                users.add(user);
                user.showDetails(logger);
                logger.info("\nPassword: "+ user.getPassword()+"\n");
            }
        } catch (SQLException e) {
            logger.severe("Error: in viewUsers \n");
            e.printStackTrace();
        }
        return users;
    }

}
