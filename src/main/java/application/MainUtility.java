package application;
import application.entities.*;
import application.services.*;
import java.util.List;

import static application.Main.scanner;
import static application.services.MessagesGenerator.LOGGER;


public class MainUtility {
    private static final String ETC ="1, 2, ... 4.\n";
    private static final String PLEASE_ENTER_VALID = "Invalid choice! \nPlease enter ";
    private MainUtility() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated\n");
    }
    public static void userUtility(DatabaseService databaseService, User currentUser){

        boolean iterator = true;
        while (iterator) {

           MessagesGenerator.listGenerator("userList");
           String option = scanner.nextLine();
           switch ( option ){

               case "1" -> currentUser.browsProducts(databaseService);
               case "2" -> currentUser.showDetails(LOGGER);
               case "3" -> {
                   MessagesGenerator.listGenerator("editProfile");
                   int optionIn = scanner.nextInt();
                   scanner.nextLine();
                   LOGGER.info("what is the new value: ");
                   String newValue =scanner.nextLine();
                   currentUser.editDetails(optionIn, newValue, LOGGER);
               }
               case "4" -> currentUser.viewInstallationRequests(databaseService);
               case "5" -> currentUser.viewRequisitesHistory(databaseService);
               case "6" -> currentUser.makeRequest(databaseService);
               case "7" -> currentUser.removeRequest(databaseService);
               case "8" -> iterator = false;
               default -> LOGGER.info(PLEASE_ENTER_VALID + "1, 2, ... 8.\n");

           }
        }
    }



    public static void adminUtility(DatabaseService databaseService , Admin currentAdmin) {
        boolean iterator = true;
        while (iterator) {
            MessagesGenerator.listGenerator("adminList");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> manageProducts(currentAdmin, databaseService);
                case 2 -> manageUsers(currentAdmin, databaseService);
                case 3 -> iterator = false;
                default -> LOGGER.info(PLEASE_ENTER_VALID + ETC);
            }
        }

    }
    private static void manageProducts(Admin currentAdmin, DatabaseService databaseService) {
        MessagesGenerator.listGenerator("manageProductsList");
        int browsOption = scanner.nextInt();
        scanner.nextLine();
        currentAdmin.manageProducts(browsOption, databaseService);
    }

    private static void manageUsers(Admin currentAdmin, DatabaseService databaseService) {
        boolean iterator2 = true;
        List<User> users = currentAdmin.viewUsers(databaseService);
        User selectedUser = selectUser(users);

        while (iterator2) {
            MessagesGenerator.listGenerator("ViewAndManageCustomersAccounts");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 8) {
                iterator2 = false;
                continue;
            }

            manageUserAccounts(currentAdmin, databaseService, selectedUser, choice);
        }
    }

    private static User selectUser(List<User> users) {
        LOGGER.info("\nselected user email: ");
        String tempId = scanner.nextLine();

        for (User user : users) {
            if (user.getEmail().equals(tempId)) {
                return user;
            }
        }
        return null;
    }

    private static void manageUserAccounts(Admin currentAdmin, DatabaseService databaseService, User selectedUser, int choice) {
        if (choice != 1) {
            LOGGER.info("Enter new value to update: ");
        }

        String newValue = scanner.nextLine();
        LOGGER.info("\n");

        if (selectedUser != null) {
            currentAdmin.manageAccounts( selectedUser, choice, newValue);
        } else {
            LOGGER.severe("Please enter valid email\n");
        }
    }






    public static void installerUtility(DatabaseService databaseService, Installer currentInstaller) {
        boolean iterator = true;
        while (iterator) {
            MessagesGenerator.listGenerator("InstallationRequests");
            String option = scanner.nextLine();
            switch ( option ){

                case "1" -> currentInstaller.viewInstallationRequests(databaseService);
                case "2" -> currentInstaller.scheduleAppointments(databaseService,scanner,false,"");
                case "3" -> currentInstaller.markAsDone(databaseService,scanner,false,"");
                case "4" -> iterator = false;
                default  -> LOGGER.info( PLEASE_ENTER_VALID + ETC);
            }
        }
    }

    public static int signUpUtility(String email,String password){
        String name;
        String location;
        String phoneNumber;
        int validationStatus = ValidationUser.validation(email, password,new DatabaseService());
        if (validationStatus == ValidationUser.VALID) {
            LOGGER.info("Please enter your name: ");
            name=scanner.nextLine();
            LOGGER.info("Please enter your address: ");
            location=scanner.nextLine();
            LOGGER.info("Please enter your phoneNumber: ");
            phoneNumber=scanner.nextLine();
            Profile profile=new Profile(-1,name,phoneNumber,location);
            SignUp signUp = new SignUp(email,password,false,validationStatus,profile);
            signUp.creatAccount(new DatabaseService());
        }
        return validationStatus;
    }
    public static User signInUtility(String email, String password,int validationStatus ){
        if (validationStatus == ValidationUser.VALID)
        {
            SignIn signIn = new SignIn(email, password, false, validationStatus);
            return signIn.performLogIn(new DatabaseService());
        }
        return null;
    }
}
