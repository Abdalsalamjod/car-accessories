package application.entities;

import application.LoggerUtility;

import application.database.premitive_objects.ResultSetResultHandler;
import application.services.DatabaseService;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static application.services.MessagesGenerator.LOGGER;

public class Admin extends User{

    public Admin(String email, String password, char role, boolean signInStatus, Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.signInStatus = signInStatus;

    }

    public void manageAccounts( User user , int option, String newValue) {
        Logger logger = LoggerUtility.getLogger();

        switch (option) {
            case 1 -> user.showDetails(logger);
            case 2, 3, 4, 5, 6 -> user.editDetails(option - 1, newValue, logger);
            case 7 -> deleteUser(user,logger);
            case 8 -> System.exit(0);
            default -> logger.severe("Please enter a valid input.");
        }
    }
    private void deleteUser(User user, Logger logger) {
        logger.info("The user"+ user.getEmail()+" deleted successfully\n");
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
        } catch (Exception e) {
            LOGGER.severe("Error: in viewUsers \n");
        }
        return users;
    }

}
