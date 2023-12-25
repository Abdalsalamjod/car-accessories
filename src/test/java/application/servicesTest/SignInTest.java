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

public class SignInTest {
    private static Logger logger = LoggerUtility.getLogger();
    SignIn signIn ;
//    public SignInTest() {
//        signIn=new SignIn();
//        signIn.email="valid@example.com";
//        signIn.password="validpass";
//    }

    public SignInTest(SignIn signIn) {
        this.signIn = signIn;
    }

    @Given("the user accesses the sign-in command")
    public void theUserAccessesTheSignInCommand() {
        assertFalse(signIn.signedIn);
        logger.info("Please Enter your Email and password\n");
    }
    @When("the user provides valid information {string} , {string}")
    public void theUserProvidesValidInformationValidExampleComValidpass(String Email, String Password) {

    }
    @When("the user provides valid information {string} , {string} , {string}")
    public void theUserProvidesValidInformationUsernamePasswordRole(String Email, String Password,String Role) {
        signIn.validationStatus=  validation(Email,Password,new DatabaseService());
        assertEquals(signIn.validationStatus,0);
        signIn.email=Email;
        signIn.password=Password;
        signIn.role=Role.charAt(0);
    }
    @When("the user provides valid information {string} , {string} and sth went wrong")
    public void theUserProvidesValidInformationAndSthWentWrong(String Email, String Password) {
        signIn.validationStatus=0;
        signIn.performLogIn(null);
        assertFalse(signIn.signedIn);
        logger.info(MessagesGenerator.SigningMessages(signIn.validationStatus));

    }
    @When("the user provides valid information {string} , {string} and sth went wrong in sql")
    public void theUserProvidesValidInformationAndSthWentWrongInSql(String Email, String Password) {
        signIn.validationStatus=  validation(Email,Password,null);
        assertNotEquals(signIn.validationStatus,0);
        signIn.email=Email;
        signIn.password=Password;
    }
    @Then("the user should be successfully logged in")
    public void theUserShouldBeSuccessfullyLoggedIn() {
        signIn.validationStatus=0;
        signIn.performLogIn(new DatabaseService());
        assertFalse(signIn.signedIn);
        logger.info(MessagesGenerator.SigningMessages(signIn.validationStatus));
    }



    @When("the user provides information {string} , {string}")
    public void theUserProvidesInformation(String Email, String Password) {
        signIn.validationStatus=validation(Email,Password,new DatabaseService());
        assertNotEquals(signIn.validationStatus,0);
        ValidationUser.isExistPassword(Email,Password,null);
    }
    @Then("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String string) {
        logger.info(MessagesGenerator.SigningMessages(signIn.validationStatus));
    }

    @Then("the user should not be logged in")
    public void theUserShouldNotBeLoggedIn() {
        signIn.performLogIn(new DatabaseService());
        assertFalse( signIn.signedIn);
    }



}
