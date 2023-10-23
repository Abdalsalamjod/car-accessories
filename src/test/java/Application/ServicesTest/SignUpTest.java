package Application.ServicesTest;
import Application.LoggerUtility;
import Application.Services.SignIn;
import Application.Services.SignUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class SignUpTest {

    private static final Logger logger = LoggerUtility.getLogger();
    SignUp signUp;
    SignIn signIn;
    public SignUpTest(SignUp signUp, SignIn signIn) {
        this.signUp = signUp;
        this.signIn = signIn;
    }
    String Email   ="valid@example.com";
    String Password ="validpass";
    @Given("the user accesses the sign-up command")
    public void theUserAccessesTheSignUpCommand() {
        assertFalse(signUp.hasAccount);
        logger.info("Please Enter your Email and password\n");
    }
    @When("the user provides valid registration information")
    public void theUserProvidesValidRegistrationInformation() {
        signUp.validation(Email,Password);
        assertTrue(signUp.valid);
    }
    @Then("the user should be registered successfully")
    public void theUserShouldBeRegisteredSuccessfully() {

        signUp.creatAccount(Email,Password);
        assertTrue( signUp.hasAccount);
    }
    @Then("should receive a confirmation message")
    public void shouldReceiveAConfirmationMessage() {
        logger.info("");
    }
    @Then("should be redirected to the user dashboard")
    public void shouldBeRedirectedToTheUserDashboardd() {

        signIn.valid=true;
        signIn.signedIn=true;
        signIn.email =signUp.email;
        signIn.password=signUp.password;
        signIn.getMessage();
    }



    @When("the user provides {string}, {string}")
    public void theUserProvides(String Email, String Password)  {
        signUp.validation(Email,Password);
        assertFalse(signUp.valid);
    }
    @Then("the system should respond with an error message")
    public void theSystemShouldRespondWithAnErrorMessage() {
        signUp.email=Email;

        logger.info(signUp.generateMessage());
    }
    @Then("the user should not be registered")
    public void theUserShouldNotBeRegistered() {
        signIn.signedIn=false;
    }
}


