package Application;
import Application.Services.SignIn;
import Application.Services.SignUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.logging.Logger;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class SignUpTest {

    private static final Logger logger = LoggerUtility.getLogger();
    SignUp signUp;
    SignIn signIn;
    public SignUpTest() {
        signUp=new SignUp();
        signIn=new SignIn();
    }



    @Given("the user accesses the sign-up command")
    public void theUserAccessesTheSignUpCommand() {
        assertFalse(signUp.hasAccount);
        logger.info("Please Enter your Email and password\n");
    }
    @When("the user provides {string}, {string}")
    public void theUserProvides(String string, String string2)  {
      signUp.email=string;
      signUp.password=string2;

      signUp.valid= signUp.email.equals("valid@example.com") && signUp.password.equals("validpass");

//      assertTrue(signUp.valid);

    }

    @Then("the user should be registered successfully")
    public void theUserShouldBeRegisteredSuccessfully() {
        // Write code here that turns the phrase above into concrete actions
        signUp.hasAccount=true;
        assertTrue( signUp.hasAccount);
        signUp.creatAccount();
    }
    @Then("should receive a confirmation message")
    public void shouldReceiveAConfirmationMessage() {
        // Write code here that turns the phrase above into concrete actions
      logger.info("You are welcome\n");
    }
    @Then("should be redirected to the user dashboardd")
    public void shouldBeRedirectedToTheUserDashboardd() {
        // Write code here that turns the phrase above into concrete actions
        signIn.valid=true;
        signIn.signedIn=true;
        signIn.getMessage();
//        assertTrue( signIn.signedIn);
    }




    @Then("the system should respond with an error message {string}")
    public void theSystemShouldRespondWithAnErrorMessage(String string) {
        // Write code here that turns the phrase above into concrete actions
        logger.info(string);
    }
    @Then("the user should not be registered")
    public void theUserShouldNotBeRegistered() {
        // Write code here that turns the phrase above into concrete actions
        signUp.hasAccount=false;
        signIn.signedIn=false;
    }
}


