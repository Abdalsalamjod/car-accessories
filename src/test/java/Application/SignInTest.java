package Application;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SignIn {
    private static Logger logger = LoggerUtility.getLogger();
    boolean regsterd=false;

    boolean Valid=false;


    @Given("the user accesses the sign-in command")
    public void theUserAccessesTheSignInCommand() {

        assertFalse(regsterd);
        logger.info("you are accesses the sign-in command "+"\n");
    }


    @When("the user enters valid login information {string}, {string}")
    public void theUserEntersValidLoginInformation(String string, String string2) {


        logger.info( string+" "+ string2  +"\n");
        if (string.equals("valid@example.com") && string2.equals("validpass") )
            Valid=true;
        assertTrue(Valid);
    }
    @When("confirms the login")
    public void confirmsTheLogin() {
        logger.info("welcome "+"\n");
    }
    @Then("the user should be successfully logged in")
    public void theUserShouldBeSuccessfullyLoggedIn() {
        regsterd  =true;
    }
    @Then("should be redirected to the user dashboard")
    public void shouldBeRedirectedToTheUserDashboard() {
        logger.info("you are in user dashboard "+"\n");
    }






    @When("the user enters {string} and {string}")
    public void theUserEntersAnd(String string, String string2) {
        logger.info( string+" "+ string2  +"\n");
    }

    @Then("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String string) {
        logger.info( string+"\n");    }
    @Then("the user should not be logged in")
    public void theUserShouldNotBeLoggedIn() {
        regsterd=false;
    }

}
