package application.entities;

import application.database.premitive_objects.ResultSetResultHandler;
import application.services.DatabaseService;
import application.services.ValidationUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static application.Main.*;



public class User {

    protected String email;
    protected String password;
    protected Profile profile;
    protected char role;
    protected boolean signInStatus;


    public User() {
            this.profile=new Profile();
    }
    public User(String email, String password, char role, boolean signInStatus,Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.signInStatus = signInStatus;

    }
    public User(String email, String password, char role, boolean signInStatus,int profileId) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile=new Profile();
        this.profile.setProfileId(profileId);
        this.signInStatus = signInStatus;

    }

    public void showDetails(Logger logger) {
        logger.info("\nName: "+this.getProfileObject().getName());
        logger.info("\nLocation: "+this.getProfileObject().getLocation());
        logger.info("\nPhone number: "+this.getProfileObject().getPhoneNumber());
        logger.info("\nEmail : "+this.getEmail());
    }
    public void editDetails(int optionIn, String newValue, Logger logger) {
        DatabaseService dbs = new DatabaseService();

        switch ( optionIn ) {
            case 1 -> updateName(newValue, logger, dbs);
            case 2 -> updateEmail(newValue, logger, dbs);
            case 3 -> updatePassword(newValue, logger);
            case 4 -> updateLocation(newValue, logger, dbs);
            case 5 -> updatePhoneNumber(newValue, logger, dbs);
            default -> logger.info("Invalid option.");
        }
    }

    private void updateName(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("The current name is: " + this.getProfileObject().getName());
        this.getProfileObject().setName(newValue);
        updateProfileObject(logger, dbs, "Updated name to: " + newValue);
    }

    private void updateEmail(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("The current Email is: " + this.getEmail());
        if (ValidationUser.isValidEmail(newValue) && !ValidationUser.isExistEmail(newValue, dbs)) {
            this.setEmail(newValue);
            updateDatabaseObject(this, "user", "email", logger, "Updated email to: " + newValue);
        } else {
            logger.info("Error: exist or invalid email, try again!");
        }
    }

    private void updatePassword(String newValue, Logger logger) {
        logger.info("Updating password...");
        this.setPassword(newValue);
        updateDatabaseObject(this, "user", "email", logger, "Password updated.");
    }

    private void updateLocation(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("The current location is: " + this.getProfileObject().getLocation());
        this.getProfileObject().setLocation(newValue);
        updateProfileObject(logger, dbs, "Updated location to: " + newValue);
    }

    private void updatePhoneNumber(String newValue, Logger logger, DatabaseService dbs) {
        logger.info("The current Phone Number is: " + this.getProfileObject().getPhoneNumber());
        this.getProfileObject().setPhoneNumber(newValue);
        updateProfileObject(logger, dbs, "Updated phone number to: " + newValue);
    }

    public void updateDatabaseObject(Object object, String tableName, String columnName, Logger logger, String successMessage) {
        try {
            new DatabaseService().updateObject(object, tableName, columnName);
            logger.info(successMessage);
        } catch (Exception e) {
            logger.severe(EDIT_DETAILS_ERROR);
        }
    }

    public void updateProfileObject(Logger logger, DatabaseService dbs, String successMessage) {
        try {
            dbs.updateObject(profile, PROFILE, PROFILE_ID);
            logger.info(successMessage);
        } catch (Exception e) {
            logger.severe(EDIT_DETAILS_ERROR);
            e.printStackTrace();
        }
    }
    public static ResultSet getRequest( String query, DatabaseService dbs ) throws SQLException {
        return dbs.executeQuery(query, new ResultSetResultHandler());
    }
    private List<Request> fetchRequests(DatabaseService databaseService, String query, Level logLevel) {
        List<Request> availableRequests = new ArrayList<>();

        Request request;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        try {
            ResultSet resultSet =getRequest(query,databaseService);
            while (resultSet.next()) {
                String date = resultSet.getString(4).substring(0, 19);
                request = new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        LocalDateTime.parse(date, formatter),
                        resultSet.getString(5));
                if (logger.isLoggable(logLevel)) {
                    logger.log(logLevel, request.toString());
                }
                availableRequests.add(request);
            }
            resultSet.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "something went wrong\n");
        }
        return availableRequests;
    }

    public void viewRequisitesHistory(DatabaseService databaseService) {
        String query = "SELECT * FROM `Request` WHERE `done` = true AND `userId`= '" + this.email + "'";
        fetchRequests(databaseService, query, Level.INFO);
    }

    public List<Request> viewInstallationRequests(DatabaseService databaseService) {
        String query = "SELECT * FROM `Request` WHERE `selected` = true AND `userId`= '" + this.email + "'";
        return fetchRequests(databaseService, query, Level.INFO);
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
    public void setEmail(String email) {
        this.email = email;
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


