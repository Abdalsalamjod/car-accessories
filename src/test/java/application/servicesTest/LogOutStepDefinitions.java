package application.servicesTest;

import application.LoggerUtility;
import application.services.DatabaseService;
import application.services.LogOut;
import application.entities.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class LogOutStepDefinitions {
    User user;
    LogOut logOut;
    private static Logger logger = LoggerUtility.getLogger();

    public LogOutStepDefinitions(LogOut logOut) {
        this.user = new User();
        this.logOut = logOut;
    }

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        user.setSignInStatus(true);
        assertTrue(user.isSignInStatus());
    }
    @When("the user selects the option to log out")
    public void theUserSelectsTheOptionToLogOut() {
        user= logOut.performLogout(user, new DatabaseService());
        logger.info("You will exit the app, are you sure?");
    }
    @Then("the user should be successfully logged out")
    public void theUserShouldBeSuccessfullyLoggedOut() {
        assertNull(user);
    }
    @Then("the system should display a logout confirmation message")
    public void theSystemShouldDisplayALogoutConfirmationMessage() {
        logger.info(LogOut.GOOD_BAY_MESSAGE);
    }


    @Then("the system should display a logout warning message")
    public void theSystemShouldDisplayALogoutWarningMessage() {
        logger.severe(LogOut.WARNING_MESSAGE);
    }


    @When("the user selects the option to log out and something wrong happened")
    public void theUserSelectsTheOptionToLogOutAndSomethingWrongHappened() {
        user= logOut.performLogout(user, null);
    }



}
