package Application.ServicesTest;
import Application.LoggerUtility;
import Application.Services.MessagesGenerator;
import Application.Services.SignIn;
import Application.Services.SignUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.logging.Logger;

import static Application.Services.ValidationUser.validation;
import static org.junit.Assert.*;

public class SignUpTest {

    private static final Logger logger = LoggerUtility.getLogger();
    SignUp signUp;
//    SignIn signIn;
    public SignUpTest(SignUp signUp, SignIn signIn) {
        this.signUp = signUp;
//        this.signIn = signIn;
        signUp.email   ="valid@example.com";
        signUp.password ="validpass";
    }

    @Given("the user accesses the sign-up command")
    public void theUserAccessesTheSignUpCommand() {
        assertFalse(signUp.hasAccount);
        logger.info("Please Enter your Email and password\n");
    }
    @When("the user provides valid registration information")
    public void theUserProvidesValidRegistrationInformation() {
       signUp.validationStatus = validation(signUp.email,signUp.password);
        assertEquals(signUp.validationStatus,0);
    }
    @Then("the user should be registered successfully")
    public void theUserShouldBeRegisteredSuccessfully() {
        signUp.creatAccount();
        assertTrue( signUp.hasAccount);
    }
    @Then("should receive a confirmation message")
    public void shouldReceiveAConfirmationMessage() {
        logger.info(MessagesGenerator.SigningMessages(signUp.validationStatus));
    }
    @Then("should be redirected to the user dashboard")
    public void shouldBeRedirectedToTheUserDashboardd() {
        signUp.performLogIn();
    }



    @When("the user provides {string}, {string}")
    public void theUserProvides(String Email, String Password)  {
        signUp.validationStatus =validation(Email,Password);
        assertNotEquals(signUp.validationStatus,0);
    }
    @Then("the system should respond with an error message")
    public void theSystemShouldRespondWithAnErrorMessage() {
        logger.info(MessagesGenerator.SigningMessages(signUp.validationStatus));
    }
    @Then("the user should not be registered")
    public void theUserShouldNotBeRegistered() {

    }
}


