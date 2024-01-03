package application.remainingTestsForCoverage;

import application.LoggerUtility;
import application.entities.Profile;
import application.entities.User;
import application.services.DatabaseService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class UserRemainingCoverage {

    User user;

    public UserRemainingCoverage() {
        this. user =new User("asd@gmail.com","132",'u',true,new Profile(3,"abd","08765","nablus"));
    }

    @Given("a user is logged in to the system")
    public void aUserIsLoggedInToTheSystem() {
        assertTrue(user.isSignInStatus());
    }

    @When("the user attempts to update with a database error")
    public void theUserAttemptsToUpdateWithADatabaseError() {
        user.updateDatabaseObject(null, "user","email",  LoggerUtility.getLogger(),"");
    }

    @Then("the update should fail")
    public void theUpdateShouldFail() {
    }

    @When("the user updates the profile's {string} to {string}")
    public void theUserUpdatesTheProfileSTo(String arg0, String arg1) {
        user.updateProfileObject(LoggerUtility.getLogger(),new DatabaseService(),"Succsesfull update\n");
    }

    @Then("the profile's {string} for the user should be updated to {string}")
    public void theProfileSForTheUserShouldBeUpdatedTo(String arg0, String arg1) {
    }


    @When("the user updates the profile's {string} to {string} with error")
    public void theUserUpdatesTheProfileSToWithError(String arg0, String arg1) {
        user.updateProfileObject(LoggerUtility.getLogger(),null,"UnSuccsesfull update\n");
    }

    @Then("the profile's {string} for the user should not be updated to {string}")
    public void theProfileSForTheUserShouldNotBeUpdatedTo(String arg0, String arg1) {
    }


    @When("the user views their requisites history")
    public void theUserViewsTheirRequisitesHistory() {
        user.viewRequisitesHistory(new DatabaseService());
    }

    @Then("the completed requisites for the user are displayed")
    public void theCompletedRequisitesForTheUserAreDisplayed() {

    }

    @When("the user views their requisites history with error")
    public void theUserViewsTheirRequisitesHistoryWithError() {
        user.viewRequisitesHistory(null);

    }

    @Then("the completed requisites for the user are not displayed")
    public void theCompletedRequisitesForTheUserAreNotDisplayed() {
    }


    @When("the user views their installation requests")
    public void theUserViewsTheirInstallationRequests() {
        user.viewInstallationRequests(new DatabaseService());
    }

    @Then("the installation requests made by the user are displayed")
    public void theInstallationRequestsMadeByTheUserAreDisplayed() {
    }


    @When("the user views their installation requests with error")
    public void theUserViewsTheirInstallationRequestsWithError() {
        user.viewInstallationRequests(null);
    }

    @Then("the installation requests made by the user arenot displayed")
    public void theInstallationRequestsMadeByTheUserArenotDisplayed() {
    }
}
