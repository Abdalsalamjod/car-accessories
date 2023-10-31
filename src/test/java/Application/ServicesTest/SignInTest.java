package Application.ServicesTest;

import Application.LoggerUtility;
import Application.Services.MessagesGenerator;
import Application.Services.SignIn;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static Application.Services.ValidationUser.validation;
import static org.junit.Assert.*;

public class SignInTest {
    private static Logger logger = LoggerUtility.getLogger();
    SignIn signIn ;
    public SignInTest() {
        signIn=new SignIn();
        signIn.email="valid@example.com";
        signIn.password="validpass";
    }
    @Given("the user accesses the sign-in command")
    public void theUserAccessesTheSignInCommand() {
        assertFalse(signIn.signedIn);
        logger.info("Please Enter your Email and password\n");
    }
    @When("the user provides valid information")
    public void theUserProvidesValidInformation() {
      signIn.validationStatus=  validation(signIn.email,signIn.password);
       assertEquals(signIn.validationStatus,0);
    }
    @Then("the user should be successfully logged in")
    public void theUserShouldBeSuccessfullyLoggedIn() {

        signIn.performLogIn();
        assertTrue(signIn.signedIn);
        logger.info(MessagesGenerator.SigningMessages(signIn.validationStatus));
    }



    @When("the user provides information {string} , {string}")
    public void theUserProvidesInformation(String Email, String Password) {
        signIn.validationStatus=validation(Email,Password);
        assertNotEquals(signIn.validationStatus,0);
    }
    @Then("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String string) {
        logger.info(MessagesGenerator.SigningMessages(signIn.validationStatus));
    }

    @Then("the user should not be logged in")
    public void theUserShouldNotBeLoggedIn() {
        signIn.performLogIn();
        assertFalse( signIn.signedIn);
    }

}
