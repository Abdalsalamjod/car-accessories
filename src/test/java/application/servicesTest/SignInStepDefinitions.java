package application.servicesTest;

import application.LoggerUtility;
import application.services.DatabaseService;
import application.services.MessagesGenerator;
import application.services.SignIn;
import application.services.ValidationUser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static application.services.ValidationUser.validation;
import static org.junit.Assert.*;

public class SignInStepDefinitions {
    private static Logger logger = LoggerUtility.getLogger();
    SignIn signIn ;
//    public SignInStepDefinitions() {
//        signIn=new SignIn();
//        signIn.email="valid@example.com";
//        signIn.password="validpass";
//    }

    public SignInStepDefinitions() {
        this.signIn = new SignIn();
    }

    @Given("the user accesses the sign-in command")
    public void theUserAccessesTheSignInCommand() {
        assertFalse(signIn.isSignedIn());
        logger.info("Please Enter your Email and password\n");
    }
    @When("the user provides valid information {string} , {string}")
    public void theUserProvidesValidInformationValidExampleComValidpass(String Email, String Password) {

    }
    @When("the user provides valid information {string} , {string} , {string}")
    public void theUserProvidesValidInformationUsernamePasswordRole(String Email, String Password,String Role) {
        signIn.setValidationStatus(validation(Email, Password, new DatabaseService()));
        assertEquals(0, signIn.getValidationStatus());
        signIn.setEmail(Email);
        signIn.setPassword(Password);
        signIn.setRole(Role.charAt(0));
    }
    @When("the user provides valid information {string} , {string} and sth went wrong")
    public void theUserProvidesValidInformationAndSthWentWrong(String Email, String Password) {
        signIn.setValidationStatus(0);
        signIn.performLogIn(null);
        assertFalse(signIn.isSignedIn());
        logger.info(MessagesGenerator.signingMessages(signIn.getValidationStatus()));

    }
    @When("the user provides valid information {string} , {string} and sth went wrong in sql")
    public void theUserProvidesValidInformationAndSthWentWrongInSql(String Email, String Password) {
        signIn.setValidationStatus(validation(Email, Password, null));
        assertNotEquals(0, signIn.getValidationStatus());
        signIn.setEmail(Email);
        signIn.setPassword(Password);
    }
    @Then("the user should be successfully logged in")
    public void theUserShouldBeSuccessfullyLoggedIn() {
        signIn.setValidationStatus(0);
        signIn.performLogIn(new DatabaseService());
        assertFalse(signIn.isSignedIn());
        logger.info(MessagesGenerator.signingMessages(signIn.getValidationStatus()));
    }



    @When("the user provides information {string} , {string}")
    public void theUserProvidesInformation(String Email, String Password) {
        signIn.setValidationStatus(validation(Email, Password, new DatabaseService()));
        assertNotEquals(0, signIn.getValidationStatus());
        ValidationUser.isExistPassword(Email,Password,null);
        signIn.getProfileid();
        signIn.getRole();
        signIn.getPassword();
    }
    @Then("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String string) {
        logger.info(MessagesGenerator.signingMessages(signIn.getValidationStatus()));
    }

    @Then("the user should not be logged in")
    public void theUserShouldNotBeLoggedIn() {
        signIn.performLogIn(new DatabaseService());
        assertFalse(signIn.isSignedIn());
        try {
            ValidationUser validationUser =new ValidationUser();

        }
        catch (Exception e){
            logger.severe("error 404\n");
        }
    }



}
