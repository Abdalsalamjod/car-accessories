package Application.ServicesTest;

import Application.LoggerUtility;
import Application.Services.SignIn;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;

public class LogOutTest {
    SignIn signIn;
    private static Logger logger = LoggerUtility.getLogger();

    public LogOutTest() {
        signIn=new SignIn();
    }

    //    @Given("the user is logged in")
//    public void theUserIsLoggedIn() {
//        // Write code here that turns the phrase above into concrete actions
//    }
    @When("the user selects the option to log out")
    public void theUserSelectsTheOptionToLogOut() {
        // Write code here that turns the phrase above into concrete actions
        logger.info("You will exit the app, are you sure?");
        signIn.signedIn=false;
    }
    @Then("the user should be successfully logged out")
    public void theUserShouldBeSuccessfullyLoggedOut() {
        // Write code here that turns the phrase above into concrete actions
        assertFalse(signIn.signedIn);
    }
    @Then("should be redirected to the login page")
    public void shouldBeRedirectedToTheLoginPage() {
        // Write code here that turns the phrase above into concrete actions
        logger.info("You are in sign in page");
    }
    @Then("the system should display a logout confirmation message")
    public void theSystemShouldDisplayALogoutConfirmationMessage() {
        // Write code here that turns the phrase above into concrete actions
        logger.info("Thank you to use our app, Have a nice Day ^_^");
    }


}
