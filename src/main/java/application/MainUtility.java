package application;
import application.entities.*;
import application.services.*;
import java.util.List;

import static application.Main.scanner;
import static application.services.MessagesGenerator.logger;


public class MainUtility {

    private static final String PLEASE_ENTER_VALID = "Invalid choice! \nPlease enter ";
    public static void userUtility(DatabaseService databaseService, User currentUser){

        boolean iterator = true;
        while (iterator) {

           MessagesGenerator.listGenerator("userList");
           String option = scanner.nextLine();
           switch ( option ){

               case "1" -> currentUser.browsProducts(databaseService);
               case "2" -> currentUser.showDetails(logger);
               case "3" -> {
                   MessagesGenerator.listGenerator("editProfile");
                   int optionIn = scanner.nextInt();
                   scanner.nextLine();  // Consume the newline
//                   currentUser.editDetails(optionIn, logger, scanner);
//                   for test
                   logger.info("what is the new value: ");
                   String newValue =scanner.nextLine();
                   currentUser.editDetails(optionIn, newValue,logger);
               }
               case "4" -> currentUser.viewInstallationRequests(databaseService);
               case "5" -> currentUser.viewRequisitesHistory(databaseService);
               case "6" -> currentUser.makeRequest(databaseService);
               case "7" -> currentUser.removeRequest(databaseService);
               case "8" -> iterator = false;
               default -> logger.info(PLEASE_ENTER_VALID + "1, 2, ... 8.\n");

           }
        }
    }

    public static void adminUtility(DatabaseService databaseService , Admin currentAdmin) {

        boolean iterator = true;
        while (iterator) {

            MessagesGenerator.listGenerator("adminList");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch ( option ) {
                case 1 -> {
                    MessagesGenerator.listGenerator("manageProductsList");
                    int browsOption = scanner.nextInt();
                    scanner.nextLine();
                    currentAdmin.manageProducts(browsOption, databaseService);
                }
                case 2 -> {
                    boolean iterator2 = true;
                    List<User> users =currentAdmin.viewUsers(databaseService);

                    User selectedUser=null ;
                    logger.info("select user id: ");
                    String tempId=scanner.nextLine();
                    for (User user: users)
                        if(user.getEmail().equals(tempId))
                            selectedUser = user;


                    int choice;
                    while (iterator2){
                        MessagesGenerator.listGenerator("ViewAndManageCustomersAccounts");
                        choice = scanner.nextInt();
                        if(choice==8){
                            iterator2=false;
                            continue;
                        }
                        logger.info("Enter new value to update: ");
                        String newValue = scanner.nextLine();
                        if(selectedUser != null)
                            currentAdmin.manageAcounts(databaseService,selectedUser,choice,newValue);
                        else
                            logger.severe("please enter valid email\n");
                    }
                }
                case 3 -> {

                }
                case 4 -> iterator = false;
                default -> logger.info(PLEASE_ENTER_VALID + "1, 2, ... 4.\n");
            }

        }

    }


    public static void installerUtility(DatabaseService databaseService, Installer currentInstaller) {
        boolean iterator = true;
        while (iterator) {
            MessagesGenerator.listGenerator("InstallationRequests");
            String option = scanner.nextLine();
            switch ( option ){

                case "1" -> currentInstaller.viewInstallationRequests(databaseService);
                case "2" -> currentInstaller.ScheduleAppointments(databaseService,scanner,false,"");
                case "3" -> currentInstaller.markAsDone(databaseService,scanner,false,"");
                case "4" -> iterator = false;
                default  -> logger.info( PLEASE_ENTER_VALID + "1, 2, ... 4.\n");
            }
        }
    }

    public static int signUpUtility(String email,String password){
        String name;
        String location;
        String phoneNumber;
        int validationStatus = ValidationUser.validation(email, password,new DatabaseService());
        if (validationStatus == ValidationUser.VALID) {
            logger.info("Please enter your name: ");
            name=scanner.nextLine();
            logger.info("Please enter your address: ");
            location=scanner.nextLine();
            logger.info("Please enter your phoneNumber: ");
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


    private static void adminControlUserUtility( DatabaseService databaseService, Admin currentAdmin) {
        boolean iterator = true;
        while (iterator) {
            MessagesGenerator.listGenerator("adminControlUserList");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume the newline
            switch (option) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:
                    iterator=false;
                    break;
                default:
                    logger.info(PLEASE_ENTER_VALID + "1, 2, ... 4.\n");
            }
        }
    }
}
