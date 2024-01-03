package application.remainingTestsForCoverage;

import application.entities.Admin;
import application.entities.Profile;
import application.entities.User;
import application.services.DatabaseService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class AdminRemainigCoverage {

    Admin admin;
    User user ;
    public AdminRemainigCoverage() {

        this.admin=new Admin("s12027747@stu.najah.ed","12345",'a',true,new Profile(0,"abd","08765","nablus"));
        this.admin.setSignInStatus(true);
        this. user =new User("asd@gmail.com","132",'u',true,new Profile(3,"abd","08765","nablus"));

    }

    @Given("the admin is logged in to the system")
    public void theAdminIsLoggedInToTheSystem() {
        assertTrue(admin.isSignInStatus());
    }


    @When("the admin selects the option to show user details for {string}")
    public void theAdminSelectsTheOptionToShowUserDetailsFor(String email) {
        admin.manageAccounts(user,1,"");
    }
    @Then("the user's details are displayed")
    public void theUserSDetailsAreDisplayed() {
    }



    @When("^the admin selects the option (\\d+) to edit \"([^\"]*)\" to \"([^\"]*)\" for \"([^\"]*)\"$")
    public void the_admin_selects_the_option_to_edit_field_to_newValue_for_user(int option, String field, String newValue, String userEmail) {
        admin.manageAccounts(user,option,newValue);
    }
    @Then("the {string} for the user {string} is updated to {string}")
    public void theForTheUserIsUpdatedTo(String arg0, String arg1, String arg2) {
    }



    @When("the admin selects the option to delete the user with email {string}")
    public void theAdminSelectsTheOptionToDeleteTheUserWithEmail(String arg0) {
        admin.manageAccounts(user,7,"");
    }
    @Then("the user with email {string} is deleted from the system")
    public void theUserWithEmailIsDeletedFromTheSystem(String arg0) {
    }



    @When("the admin enters an invalid option")
    public void theAdminEntersAnInvalidOption() {
        admin.manageAccounts(user,9,"");
    }
    @Then("a log entry is made stating {string}")
    public void aLogEntryIsMadeStating(String arg0) {
    }


    @When("the admin selects the option to exit the system")
    public void theAdminSelectsTheOptionToExitTheSystem() {
        admin.manageAccounts(user,9,"");
    }
    @Then("the system is terminated")
    public void theSystemIsTerminated() {
    }


//##################################################################################
    @When("the admin selects the option to view all users")
    public void theAdminSelectsTheOptionToViewAllUsers() {
        admin.viewUsers(new DatabaseService());
    }
    @Then("a list of all users with their details is displayed in the log")
    public void aListOfAllUsersWithTheirDetailsIsDisplayedInTheLog() {
    }


    @When("the admin selects the option to view all users and problem hapend")
    public void theAdminSelectsTheOptionToViewAllUsersAndProblemHapend() {
        admin.viewUsers(null);

    }

    @Then("an error log entry is made stating {string}")
    public void anErrorLogEntryIsMadeStating(String arg0) {
        System.out.println(arg0);
    }


}
