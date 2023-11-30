package Application.DataBase;

import Application.Entities.Profile;
import Application.Entities.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.zh_cn.假如;

import static org.junit.Assert.assertTrue;

public class UserProfileTest {
    private User user;
    private Profile profile;
    boolean flag;

//    public UserProfileTest(User user, Profile profile) {
//        this.user = user;
//        this.profile = profile;
//        flag=false;
//    }

    @Before
    public void setUp() {
        // Assume a User and Profile class with necessary methods and properties
        profile = new Profile(1,"valid","number","location");
        user = new User("valid@domain.com","1234","u",true,profile);
    }

    @Given("the customer is logged in")
    public void theCustomerIsLoggedIn() {
        assertTrue(user.SignInStatus);
    }

    @When("the customer accesses their profile settings")
    public void theCustomerAccessesTheirProfileSettings() {

    }

    @When("updates their contact information")
    public void updatesTheirContactInformation() {
        profile.setPhoneNumber("number");
    }

    @Then("the changes should be saved successfully")
    public void theChangesShouldBeSavedSuccessfully() {
        assertTrue(profile.areChangesSaved());
    }
    @When("updates their location")
    public void updatesTheirLocation() {
        profile.setLocation("location");
    }
    @When("updates their name")
    public void updatesTheirName() {
      profile.setName("name");
    }


    @When("updates their email")
    public void updatesTheirEmail() {
        // Write code here that turns the phrase above into concrete actions
user.setEmail("new email");

    }


    @When("updates their password")
    public void updatesTheirPassword() {
        // Write code here that turns the phrase above into concrete actions
        user.setPassword("new password");
    }


    @When("selects the order history section")
    public void selectsTheOrderHistorySection() {
        flag=false;
      flag=  profile.viewOrderHistory();
    }

    @Then("they should see a list of their past orders")
    public void theyShouldSeeAListOfTheirPastOrders() {
        assertTrue(flag);
    }

    @When("selects the installation requests section")
    public void selectsTheInstallationRequestsSection() {
        flag=false;
        flag=  profile.viewInstallationRequests();
    }

    @Then("they should see a list of their installation requests")
    public void theyShouldSeeAListOfTheirInstallationRequests() {
        assertTrue(flag);
    }
}
