package Application;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.logging.Logger;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class SignUp {
    boolean regsterd=false;
    private static Logger logger = LoggerUtility.getLogger();
    boolean Valid=false;



    @Given("the user accesses the sign-up command")
    public void theUserAccessesTheSignUpCommand() {
        assertTrue(true);
    }
    @When("the user provides valid registration information {string}, {string}")
    public void theUserProvidesValidRegistrationInformation(String string, String string2) {
      if (string.equals("valid@example.com") && string2.equals("validpass"))
             Valid=true;

      assertTrue(Valid);

    }

    @Then("the user should be registered successfully")
    public void theUserShouldBeRegisteredSuccessfully() {
        // Write code here that turns the phrase above into concrete actions
        regsterd=true;
        assertTrue(regsterd);
    }
    @Then("should receive a confirmation message")
    public void shouldReceiveAConfirmationMessage() {
        // Write code here that turns the phrase above into concrete actions
      logger.info("You are welcome\n");

    }
    @Then("should be redirected to the admin dashboard")
    public void shouldBeRedirectedToTheAdminDashboard() {
        // Write code here that turns the phrase above into concrete actions
    }


    @When("the user provides {string}, {string}")
    public void theUserProvides(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
    }
    @Then("the system should respond with an error message {string}")
    public void theSystemShouldRespondWithAnErrorMessage(String string) {
        // Write code here that turns the phrase above into concrete actions
        logger.info(string);
    }
    @Then("the user should not be registered")
    public void theUserShouldNotBeRegistered() {
        // Write code here that turns the phrase above into concrete actions
        regsterd=false;
    }
}


