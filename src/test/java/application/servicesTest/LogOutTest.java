package application.servicesTest;

import application.LoggerUtility;
import application.services.LogOut;
import application.entities.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class LogOutTest {
    User user;
    LogOut logOut;
    private static Logger logger = LoggerUtility.getLogger();

    public LogOutTest() {
       user =new User();
        logOut =new LogOut();
    }

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        user.setSignInStatus(true);
        assertTrue(user.isSignInStatus());

    }
    @When("the user selects the option to log out")
    public void theUserSelectsTheOptionToLogOut() {
        // Write code here that turns the phrase above into concrete actions
        logger.info("You will exit the app, are you sure?");
    }
    @Then("the user should be successfully logged out")
    public void theUserShouldBeSuccessfullyLoggedOut() {

        assertNull(logOut.performLogout(user));
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
