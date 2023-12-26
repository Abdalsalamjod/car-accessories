package application.entities;

import application.database.premitive_objects.ResultSetResultHandler;
import application.services.DatabaseService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static application.Main.logger;

public class Installer extends User{
    public Installer() {
        super();
    }

    public List<Request> availableRequests ;
    public Installer(String email, String password, char role, Boolean SignInStatus, Profile profile) {
        this.email=email;
        this.password=password;
        this.role=role;
        this.profile = profile;
        this.signInStatus = SignInStatus;
    }

    public List<Request> viewInstallationRequests(DatabaseService dbs){
        ResultSet resultSet;
        Request request;
        availableRequests =new ArrayList<>();

        try {
            resultSet=  dbs.executeQuery("SELECT * FROM `Request` WHERE `done` = false" , new ResultSetResultHandler());
            while ( resultSet.next() ) {
                request= new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                         resultSet.getDate(4).toLocalDate().atTime(LocalTime.MIDNIGHT),
                        resultSet.getString(5));

                logger.info(request.toString());
                availableRequests.add(request);
            }
        } catch (Exception e) {
            logger.info("something went wrong\n");
        }
        return availableRequests;
    }


    private void populateAvailableRequests(DatabaseService databaseService) {

        ResultSet resultSet;
        Request request;
        availableRequests.clear();

        try {
            resultSet = databaseService.executeQuery("SELECT * FROM `Request` WHERE `selected` = true", new ResultSetResultHandler());
            while (resultSet.next()) {
                request = new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate().atTime(LocalTime.MIDNIGHT),
                        resultSet.getString(5));

                logger.info(request.toString());
                availableRequests.add(request);
            }
        } catch (Exception e) {
            logger.severe("something went wrong\n");
        }
    }

    public void ScheduleAppointments(DatabaseService databaseService, Scanner scanner, boolean test, String testValue) {
        boolean continueLoop = true;
        availableRequests = viewInstallationRequests(databaseService);

        while (continueLoop) {
            logger.info("Enter Request ID or 'Exit' to quit:");
            String requestId = (test) ? testValue : scanner.nextLine();

            if (requestId.equalsIgnoreCase("Exit")) {
                break;
            }

            try {
                int requestIdInt = Integer.parseInt(requestId);
                boolean exist = false;
                for (Request request : availableRequests) {
                    if (request.getId() == requestIdInt) {
                        exist = true;

                        try {
                          request.setSelected(1);
                            databaseService.updateObject(request, "Request", "id");
                            continueLoop = false; // Exit loop after successful update
                        } catch (SQLException e) {
                            logger.severe("Error updating the request: " + e.getMessage());
                            // Optionally, add logic here to decide next steps, like retrying or breaking the loop
                            break; // This will exit the loop in case of SQL exception
                        } catch (Exception e) {
                            logger.severe("An unexpected error occurred: " + e.getMessage());
                            // Handle other exceptions
                        }

                        break;
                    }
                }

                if (!exist) {
                    logger.info("No matching Request ID found. Please, enter a valid number.");
                }
            } catch (NumberFormatException e) {
                logger.info("Invalid input. Please, enter a valid number or 'Exit' to quit.");
            }
        }
    }


    public void markAsDone(DatabaseService databaseService, Scanner scanner, boolean test, String testValue) {
        boolean continueLoop = true;

        populateAvailableRequests(databaseService); // Loading available requests

        while (continueLoop) {
            logger.info("Enter Request Id to mark as done or 'Exit' to quit:");
            String requestId = (test) ? testValue : scanner.nextLine();

            if (requestId.equalsIgnoreCase("Exit")) {
                break;
            }

            try {
                boolean exist = false;
                for (Request request : availableRequests) {
                    if (request.getId() == Integer.parseInt(requestId)) {
                        exist = true;
                        request.setDone(1);
                        databaseService.updateObject(request, "Request", "id");
                        continueLoop = false; // Exit loop after successful update
                        break;
                    }
                }
                if (!exist) {
                    logger.severe("Please, enter valid number\n");
                }
            } catch (NumberFormatException e) {
                logger.severe("Invalid input. Please, enter a valid number\n");
            } catch (Exception e) {
                logger.severe("An error occurred: " + e.getMessage());
            }
        }
    }

    public static void createNewRequest(Request request, DatabaseService dbs){

        try {
            dbs.addObject(request, "Request");
        } catch ( SQLException e ) {
            logger.info(e.getMessage());
        }

    }

    public static void deleteExistingRequest(int id, DatabaseService dbs){
        try {
            dbs.deleteObject(id, "Request");
        } catch ( SQLException e ) {
            logger.info(e.getMessage());
        }
    }
}
