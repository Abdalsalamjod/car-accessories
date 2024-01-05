package application.entities;

import application.database.premitive_objects.ResultSetResultHandler;
import application.services.DatabaseService;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import static application.Main.*;
import static application.Main.logger;

public class Installer extends User{

    public Installer() {
        super();
    }

    protected List<Request> availableRequests;
    public Installer(String email, String password, char role, Boolean signInStatus, Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.signInStatus = signInStatus;
    }

    @Override
    public List<Request> viewInstallationRequests(DatabaseService dbs){
        ResultSet resultSet;
        Request request;
        this.availableRequests = new ArrayList<>();

        try {
            resultSet = dbs.executeQuery("SELECT * FROM `" + REQUEST + "` WHERE `done` = false", new ResultSetResultHandler());

            while ( resultSet.next() ) {
                request = new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate().atTime(LocalTime.MIDNIGHT),
                        resultSet.getString(5));

                if (logger.isLoggable(Level.INFO)) {
                    logger.info(request.toString());
                }
                availableRequests.add(request);
            }
            resultSet.close();
        } catch (Exception e) {
            logger.info("something went wrong\n");
        }
        return availableRequests;
    }


    private void populateAvailableRequests(DatabaseService databaseService) {
        ResultSet resultSet;
        Request request;
        this. availableRequests=new ArrayList<>();

        try {
            resultSet = databaseService.executeQuery("SELECT * FROM `"+ REQUEST +"` WHERE `selected` = true", new ResultSetResultHandler());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (resultSet.next()) {
                String date =  resultSet.getString(4).substring(0, 19);
                request = new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        LocalDateTime.parse(date, formatter),
                        resultSet.getString(5)
                );
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(request.toString());
                }
                availableRequests.add(request);
            }
            resultSet.close();

        } catch (Exception e) {
            logger.severe("something went wrong\n");
        }
    }

    public void scheduleAppointments(DatabaseService databaseService, Scanner scanner, boolean test, String testValue) {
        this.availableRequests = viewInstallationRequests(databaseService);
        processRequests(databaseService, scanner, test, testValue);
    }

    private void processRequests(DatabaseService databaseService, Scanner scanner, boolean test, String testValue) {
        boolean continueLoop = true;
        while (continueLoop)
        {
            try {
                    String requestId = getRequestInput(scanner, test, testValue);
                if (requestId.equalsIgnoreCase("Exit")) {
                    return;
                }


                int requestIdInt = Integer.parseInt(requestId);
                continueLoop = handleRequestUpdate(databaseService, requestIdInt);

            } catch (Exception e) {
                logger.info("Invalid input. Please, enter a valid number or 'Exit' to quit.\n");
            }
            continueLoop = !test && continueLoop;
        }
    }

    private String getRequestInput(Scanner scanner, boolean test, String testValue) {
        logger.info("Enter Request ID or 'Exit' to quit:");
        return (test) ? testValue : scanner.nextLine();
    }

    private boolean handleRequestUpdate(DatabaseService databaseService, int requestIdInt) {
        for (Request request : availableRequests) {
            if (request.getId() == requestIdInt) {
                updateRequest(databaseService, request);
                return false;
            }
        }
        logger.info("No matching Request ID found. Please, enter a valid number.\n");
        return true;
    }

    private void updateRequest(DatabaseService databaseService, Request request) {
        try {
            String query = "UPDATE Request SET selected=1 WHERE id=" + request.getId();
            Statement smt = databaseService.getConnection().createStatement();
            smt.executeUpdate(query);
            smt.close();
        }  catch (Exception e) {
            logger.severe("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void markAsDone(DatabaseService databaseService, Scanner scanner, boolean test, String testValue) {
        populateAvailableRequests(databaseService);
        processMarkAsDoneRequests(databaseService, scanner, test, testValue);
    }

    private void processMarkAsDoneRequests(DatabaseService databaseService, Scanner scanner, boolean test, String testValue) {

            String requestId = getRequestInput(scanner, test, testValue);
            if (requestId.equalsIgnoreCase("Exit")) {
                return;
            }

            try {
                int requestIdInt = Integer.parseInt(requestId);
                markRequestAsDone(databaseService, requestIdInt);

            } catch (Exception e) {
                logger.severe("An error occurred: " + e.getMessage());
            }
    }

    
    public void markRequestAsDone(DatabaseService databaseService, int requestIdInt) {
        for (Request request : availableRequests) {
            if (request.getId() == requestIdInt) {
                try {
                    String query = "UPDATE Request SET done=1 WHERE id=" + requestIdInt;
                    Statement smt = databaseService.getConnection().createStatement();
                    smt.executeUpdate(query);
                    smt.close();
                } catch (Exception e) {
                    logger.severe("Error updating the request: " + e.getMessage());
                    logger.severe("\nPlease, enter a valid number\n");
                }
            }
        }

    }

    public static void createNewRequest(Request request, DatabaseService dbs){

        try {
            dbs.addObject(request, REQUEST);
        } catch ( Exception e ) {
            logger.info(e.getMessage());
        }

    }

    public static void deleteExistingRequest(int id, DatabaseService dbs){
        try {
            dbs.deleteObject(id, REQUEST);
        } catch ( Exception e ) {
            logger.info(e.getMessage());
        }
    }

    public void setAvailableRequests(List<Request> availableRequests) {
        this.availableRequests = availableRequests;
    }
}
