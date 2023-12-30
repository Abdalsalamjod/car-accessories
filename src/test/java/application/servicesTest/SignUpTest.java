package application.servicesTest;
import application.LoggerUtility;
import application.entities.Profile;
import application.services.DatabaseService;
import application.services.MessagesGenerator;
import application.services.SignUp;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.logging.Logger;

import static application.services.ValidationUser.validation;
import static org.junit.Assert.*;

public class SignUpTest {

    private static final Logger logger = LoggerUtility.getLogger();
    SignUp signUp;
    public SignUpTest(SignUp signUp) {
        this.signUp = signUp;
    }

    @Given("the user accesses the sign-up command")
    public void theUserAccessesTheSignUpCommand() {
        assertFalse(signUp.hasAccount);
        logger.info("Please Enter your Email and password\n");
    }
    @When("the user provides valid registration information")
    public void theUserProvidesValidRegistrationInformation() {
        Profile profile =new Profile(-1,"test","0000","tset");
        signUp.email   ="valid@example.com";
        signUp.password ="validpass";
        signUp.profile=profile;
        signUp.validationStatus = validation(signUp.email,signUp.password,new DatabaseService());
        assertNotEquals(signUp.validationStatus,0);
    }
    @Then("the user should be registered successfully")
    public void theUserShouldBeRegisteredSuccessfully() {
        signUp.validationStatus=0;
        signUp.creatAccount(new DatabaseService());
        assertTrue( signUp.hasAccount);
    }
    @Then("should receive a confirmation message")
    public void shouldReceiveAConfirmationMessage() {
        logger.info(MessagesGenerator.signingMessages(signUp.validationStatus));
    }
    @Then("should be redirected to the user dashboard")
    public void shouldBeRedirectedToTheUserDashboardd() {
        signUp.performLogIn();
    }



    @When("the user provides {string}, {string}")
    public void theUserProvides(String Email, String Password)  {
        signUp.validationStatus =validation(Email,Password,new DatabaseService());
//        assertNotEquals(signUp.validationStatus,0);
    }
    @Then("the system should respond with an error message")
    public void theSystemShouldRespondWithAnErrorMessage() {
        logger.info(MessagesGenerator.signingMessages(signUp.validationStatus));
    }
    @Then("the user should not be registered")
    public void theUserShouldNotBeRegistered() {

    }

    @And("error in validation hapneed")
    public void errorInValidationHapneed() {
        signUp.validationStatus=5;
        signUp.creatAccount(new DatabaseService());
    }
    @When("the user provides valid registration information new constucotr")
    public void theUserProvidesValidRegistrationInformationNewConstucotr() {
        Profile profile =new Profile(-1,"test","0000","tset");
        this.signUp = new SignUp("valid@example.com","validpass",false,0,profile);
    }
    @When("the user provides valid registration information and sth went weong")
    public void theUserProvidesValidRegistrationInformationAndSthWentWeong() {
        signUp.validationStatus=0;
        signUp.creatAccount(null);
        assertTrue( signUp.hasAccount);
    }
}