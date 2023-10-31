package Application;

import Application.Services.MessagesGenerator;
import Application.Services.SignIn;
import Application.Services.SignUp;
import Application.Services.ValidationUser;
import Application.entities.User;

import java.util.Scanner;
import java.util.logging.Logger;



// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static Logger logger = LoggerUtility.getLogger();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.print("Hello and welcome!");

        String email = "";
        String password = "";
        int validationStatus;

        // Create a user object
        User currentUser = null;

        logger.info(" _____________________________________________________\n");
        logger.info("|       Welcome to HalaCar  accessories System :)     |\n");
        logger.info("| 1-If you want to SignUp                             |\n");
        logger.info("| 2-If you want to SignIn                             |\n");
        logger.info("|_____________________________________________________|\n");

        while (true) {
            String choice = scanner.nextLine();
            logger.info(" _____________________________________________________\n");
            logger.info("| 1-If you want to SignUp                             |\n");
            logger.info("| 2-If you want to SignIn                             |\n");
            logger.info("|_____________________________________________________|\n");
            System.out.println("Enter 1 for Sign Up, 2 for Sign In, or 3 to Quit:");

            if (choice.equals("1")) {
                // Sign Up
                System.out.println("Enter your email:");
                email = scanner.nextLine();
                System.out.println("Enter your password:");
                password = scanner.nextLine();

                validationStatus = ValidationUser.validation(email, password);

                if (validationStatus == ValidationUser.VALID) {
                    SignUp signUp = new SignUp();
                    signUp.email = email;
                    signUp.password = password;
                    signUp.creatAccount();
                    logger.info("Account created successfully!");
                } else {
                    logger.info(MessagesGenerator.SigningMessages(validationStatus));
                }
            } else if (choice.equals("2")) {
                // Sign In
                System.out.println("Enter your email:");
                email = scanner.nextLine();
                System.out.println("Enter your password:");
                password = scanner.nextLine();

                validationStatus = ValidationUser.validation(email, password);

                if (validationStatus == ValidationUser.VALID) {
                    SignIn signIn = new SignIn(email, password, "user", false, validationStatus);
                    currentUser = signIn.performLogIn();

                    if (currentUser != null && currentUser.isSignInStatus()) {
                        logger.info("Sign In successful. Welcome, " + currentUser.getName());
                        logger.info(MessagesGenerator.SigningMessages(validationStatus));

                    } else {
                        logger.info("Sign In failed. Please try again.");
                    }
                } else {
                    logger.info(MessagesGenerator.SigningMessages(validationStatus));
                }
            } else if (choice.equals("3")) {
                // Quit the application
                System.exit(0);
            } else {
                logger.info("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
}









//        int option=0;
//        String email;
//        String password;
//        logger.info(" _____________________________________________________\n");
//        logger.info("|       Welcome to HalaCar  accessories System :)     |\n");
//        logger.info("| 1-If you want to SignUp                             |\n");
//        logger.info("| 2-If you want to SignIn                             |\n");
//        logger.info("|_____________________________________________________|\n");
//        Scanner in = new Scanner(System.in);

//         option = in.nextInt();
//        logger.info("Please enter your email:");
//        email=in.nextLine();
//        logger.info("Please enter your password:");
//        password=in.nextLine();
//         if(option==1){
//             SignUp signUp=new SignUp();
//         }else if(option==2){
//
//         }

