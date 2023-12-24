package application.entities;

import application.dataBase.Premetive_Objects.ResultSetResultHandler;
import application.services.DatabaseService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static application.Main.logger;
import static application.Main.scanner;

public class Installer extends User{
public List<Request> availableRequests ;
    public Installer(String email, String password, char role, boolean SignInStatus, Profile profile) {
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
        } catch (SQLException e) {
            logger.info("something went wrong\n");
        }
        return availableRequests;
    }

    public void ScheduleAppointments(DatabaseService databaseService) {
        boolean iterator = true;
        boolean exist=false;
        Request selectedRequest;
        availableRequests =viewInstallationRequests(databaseService);

        while (iterator) {
            logger.info("Enter Request Id to Schedule it: ");
            logger.info("Exit to get out");
            String RequestId  = scanner.nextLine();
            if(RequestId.equalsIgnoreCase("Exit"))
            {
                iterator=false;
                continue;
            }
            try {
                for (Request request1 : availableRequests)
                    if (request1.getId() == Integer.parseInt(RequestId)) {
                        exist = true;
                        request1.setSelected(true);
                        databaseService.updateObject(request1,"Request","id");
                        break;
                    }
            }catch (Exception e){
                logger.info("Please, enter valid input\n");
                e.printStackTrace();
            }
            if(!exist)
                logger.info("Please, enter valid number\n");
        }
    }

    public void markAsDone(DatabaseService databaseService) {
        ResultSet resultSet;
        Request request;
        boolean iterator = true;
        boolean exist=false;
        Request selectedRequest;
        availableRequests =new ArrayList<>();
        try {
            resultSet=  databaseService.executeQuery("SELECT * FROM `Request` WHERE `selected` = true" , new ResultSetResultHandler());
            while ( resultSet.next() ) {
                request= new Request(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate().atTime(LocalTime.MIDNIGHT),
                        resultSet.getString(5));

                logger.info(request.toString());
                availableRequests.add(request);
            }
        } catch (SQLException e) {
            logger.severe("something went wrong\n");
        }
        databaseService =new DatabaseService();
        while (iterator) {
            logger.info("Enter Request Id to cancel it\n");
            logger.info("Exit to get out\n");
            String RequestId  = scanner.nextLine();
            if(RequestId.equalsIgnoreCase("Exit"))
            {
                iterator=false;
                continue;
            }
            try {
                for (Request request1 : availableRequests)
                    if (request1.getId() == Integer.parseInt(RequestId)) {
                        exist = true;
                        request1.setDone(true);
                        databaseService.updateObject(request1,"Request","id");
                        break;
                    }
            }catch (Exception e){
                logger.severe("Please, enter valid input\n");
            }
            if(!exist)
                logger.severe("Please, enter valid number\n");
        }

    }
}
