package application.entities;

import application.LoggerUtility;

import application.database.premitive_objects.ResultSetResultHandler;
import application.database.user_defined_types.ProductResultHandler;
import application.services.DatabaseService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static application.Main.scanner;
import static application.services.MessagesGenerator.LOGGER;

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
                default -> {
                        LOGGER.info("Invalid choice! \nPlease enter 1, 2, ... 6.\n");
                }
            }
        } catch (Exception e) {
                LOGGER.info("An error occurred during the operation.\n");
        }
    }

    private void showAllProducts(DatabaseService dbs) throws SQLException {
        ResultSet rs = Product.getAllProducts(dbs);
        while (rs.next()) {
            Product returnedProduct = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
            if (LOGGER.isLoggable(Level.INFO))
                LOGGER.info(returnedProduct.toString());

        }
    }
    private void addProduct(DatabaseService dbs, String tableName) throws SQLException {
        LOGGER.info("\nPlease enter the product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        LOGGER.info("Please enter the product name: ");
        String name = scanner.nextLine();
        LOGGER.info("Please enter the product category: ");
        String category = scanner.nextLine();
        LOGGER.info("Please enter the product price: ");
        double price = scanner.nextDouble();
        LOGGER.info("Please enter the product quantity: ");
        int quantity = scanner.nextInt();
        Product product = new Product(id, name, category, price, quantity);
        String valid = product.validInformation();
        if (valid.equals("")) {
            dbs.addObject(product, tableName);
            LOGGER.info("Product Added successfully!");
        } else {
            LOGGER.severe(valid + "\n");
        }
    }
    private void deleteProduct(DatabaseService dbs, String tableName) throws SQLException {
        LOGGER.info("\nPlease enter the product ID: ");
        int id = scanner.nextInt();
        boolean done = dbs.deleteObject(id, tableName);
        if (done)
            LOGGER.info("Product Deleted successfully!");
    }
    private void updateProduct(DatabaseService dbs, String tableName) throws SQLException {
        LOGGER.info("\nPlease enter the ID of the product to be updated: ");
        int id = scanner.nextInt();
        LOGGER.info("\nPlease enter the new price: ");
        double price = scanner.nextDouble();
        LOGGER.info("\nPlease enter the new quantity: ");
        int quantity = scanner.nextInt();
        if (price > 0 && quantity > 0) {
            Product oldProduct = dbs.executeQuery("SELECT * FROM Product WHERE id=" + id, new ProductResultHandler());
            Product newProduct = new Product(oldProduct.getId(), oldProduct.getName(), oldProduct.getCategory(), price, quantity);
            try {
                dbs.updateObject(newProduct, tableName, "id");
            } catch (Exception e) {
                LOGGER.severe("Error in updating Product\n");
            }
            LOGGER.info("Product updated successfully!\n");
        } else {
            LOGGER.info("Price and Quantity must be greater than zero!\n");
        }
    }
    private void exitApplication() {
        LOGGER.info("Good bye, have a nice day.");
        System.exit(0);
    }
    public void manageAcounts( User user ,int option, String newValue) {
        Logger logger = LoggerUtility.getLogger();

        switch (option) {
            case 1 -> user.showDetails(logger);
            case 2, 3, 4, 5, 6 -> user.editDetails(option - 1, newValue, logger);
            case 7 -> {
                // TODO: Implement the logic for deleting a user here
                // dbs.deleteObject(user.getEmail(), "user");
            }
            case 8 -> System.exit(0);
            default -> logger.severe("Please enter a valid input.");
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
                user.showDetails(LOGGER);
                LOGGER.info("\nPassword: "+ user.getPassword()+"\n");
            }
        } catch (SQLException e) {
            LOGGER.severe("Error: in viewUsers \n");
            e.printStackTrace();
        }
        return users;
    }

}
