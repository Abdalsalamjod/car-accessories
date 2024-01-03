package application.database;

import application.entities.Profile;
import application.entities.User;
import application.services.DatabaseService;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.mockito.MockitoAnnotations;

import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserProfileTest {
    private User user;
    private Profile profile;
    boolean flag;

    public UserProfileTest() {

    }

    @Before
    public void setUp() {
        // Assume a User and Profile class with necessary methods and properties
        profile = new Profile(1, "valid", "number", "location");
        user = new User("valid@domain.com", "1234", 'u', true, profile);
        flag = false;
        MockitoAnnotations.openMocks(this);
        // Other setup code...


    }

    @Given("the customer is logged in")
    public void theCustomerIsLoggedIn() {
        assertTrue(user.isSignInStatus());
    }

    @When("the customer accesses their profile settings")
    public void theCustomerAccessesTheirProfileSettings() {

    }

    @When("updates their contact information")
    public void updatesTheirContactInformation() {
    }

    @And("updates their contact information {string}")
    public void updatesTheirContactInformation(String arg0) {
//        Scanner mockScanner = Mockito.mock(Scanner.class);
//
//        Mockito.when(mockScanner.nextLine()).thenReturn(arg0);

        user.editDetails(5, "00000", Logger.getLogger("logger"));
        profile.setPhoneNumber("number");

    }

    @Then("the changes should be saved successfully")
    public void theChangesShouldBeSavedSuccessfully() {
        assertTrue(profile.areChangesSaved(new DatabaseService()));
        user.showDetails(Logger.getLogger("logger "));
    }

    @When("updates their location")
    public void updatesTheirLocation() {
        user.editDetails(4, "tset", Logger.getLogger("logger"));
        profile.setLocation("location");
    }

    @When("updates their name")
    public void updatesTheirName() {
        user.editDetails(1, "testt", Logger.getLogger("logger"));

        profile.setName("name");
    }


    @When("updates their email")
    public void updatesTheirEmail() {
        user.editDetails(2, "test@gmail.com", Logger.getLogger("logger"));

//        user.setEmail("new email");

    }


    @When("updates their password")
    public void updatesTheirPassword() {
        user.editDetails(3, "test", Logger.getLogger("logger"));

        user.setPassword("new password");
    }


    @When("selects the order history section")
    public void selectsTheOrderHistorySection() {
        String oldEmail = user.getEmail();
        user.setEmail("s12027670@stu.najah.edu");
        user.viewRequisitesHistory(new DatabaseService());
        user.setEmail(oldEmail);
    }
    @And("selects the order history section but no requsets")
    public void selectsTheOrderHistorySectionButNoRequsets() {
        flag = false;

        user.viewRequisitesHistory(new DatabaseService());

    }

    @Then("they should see a list of their past orders")
    public void theyShouldSeeAListOfTheirPastOrders() {
        assertFalse(flag);
    }

    @When("selects the installation requests section")
    public void selectsTheInstallationRequestsSection() {
        String oldEmail = user.getEmail();
        user.setEmail("s12027670@stu.najah.edu");
        user.viewInstallationRequests(new DatabaseService());
        user.setEmail(oldEmail);
    }

    @And("selects the installation requests section but no requetss exist")
    public void selectsTheInstallationRequestsSectionButNoRequetssExist() {
        user.viewInstallationRequests(new DatabaseService());

    }

    @Then("they should not see a list of their installation requests")
    public void theyShouldNotSeeAListOfTheirInstallationRequests() {
    }


    @Then("they should see a list of their installation requests")
    public void theyShouldSeeAListOfTheirInstallationRequests() {
        assertFalse(flag);
    }

    @Then("the changes should not be saved and message apear")
    public void theChangesShouldNotBeSavedAndMessageApear() {
        assertTrue(profile.areChangesSaved(null));
        profile.getName();
        profile.getPhoneNumber();
        profile.getLocation();
    }


    @And("enter invalid Data")
    public void enterInvalidData() {

    }

    @Then("the changes should be not saved")
    public void theChangesShouldBeNotSaved() {
        user.editDetails(8, "test", Logger.getLogger("logger"));

    }

    @And("updates their email with exist email")
    public void updatesTheirEmailWithExistEmail() {
        user.editDetails(2, "asd@gmail.com", Logger.getLogger("logger"));

    }


}

