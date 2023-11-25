package Application.DataBase;

import Application.Entities.Profile;
import Application.Entities.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class UserProfileTest {
    private User user;
    private Profile profile;

    public UserProfileTest(User user, Profile profile) {
        this.user = user;
        this.profile = profile;
    }

    @Given("the customer is logged in")
    public void theCustomerIsLoggedIn() {
//        assertTrue(user.SignInStatus);
    }
    @When("the customer accesses their profile settings")
    public void theCustomerAccessesTheirProfileSettings() {
    profile.updateUserProfile("email@domain.com","12345");
    }


    @When("updates their contact information")
    public void updatesTheirContactInformation() {

    }

    @Then("the changes should be saved successfully")
    public void theChangesShouldBeSavedSuccessfully() {

    }


    @When("selects the order history section")
    public void selectsTheOrderHistorySection() {

    }

    @Then("they should see a list of their past orders")
    public void theyShouldSeeAListOfTheirPastOrders() {

    }


    @When("selects the installation requests section")
    public void selectsTheInstallationRequestsSection() {

    }

    @Then("they should see a list of their installation requests")
    public void theyShouldSeeAListOfTheirInstallationRequests() {

    }
}
