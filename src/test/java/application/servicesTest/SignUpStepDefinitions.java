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

public class SignUpStepDefinitions {

    private static final Logger logger = LoggerUtility.getLogger();
    SignUp signUp;
    public SignUpStepDefinitions() {
        this.signUp = new SignUp();
    }

    @Given("the user accesses the sign-up command")
    public void theUserAccessesTheSignUpCommand() {
        assertFalse(signUp.isHasAccount());
        logger.info("Please Enter your Email and password\n");
    }
    @When("the user provides valid registration information")
    public void theUserProvidesValidRegistrationInformation() {
        Profile profile =new Profile(-1,"test","0000","tset");
        signUp.setEmail("valid@example.com");
        signUp.setPassword("validpass");
        signUp.setProfile(profile);
        signUp.setValidationStatus(validation(signUp.getEmail(), signUp.getPassword(), new DatabaseService()));
        assertNotEquals(signUp.getValidationStatus(),0);
    }
    @Then("the user should be registered successfully")
    public void theUserShouldBeRegisteredSuccessfully() {
        signUp.setValidationStatus(0);
        signUp.creatAccount(new DatabaseService());
        assertTrue(signUp.isHasAccount());
    }
    @Then("should receive a confirmation message")
    public void shouldReceiveAConfirmationMessage() {
        logger.info(MessagesGenerator.signingMessages(signUp.getValidationStatus()));
    }
    @Then("should be redirected to the user dashboard")
    public void shouldBeRedirectedToTheUserDashboardd() {
        signUp.performLogIn();
    }



    @When("the user provides {string}, {string}")
    public void theUserProvides(String Email, String Password)  {
        signUp.setValidationStatus(validation(Email, Password, new DatabaseService()));
//        assertNotEquals(signUp.validationStatus,0);
    }
    @Then("the system should respond with an error message")
    public void theSystemShouldRespondWithAnErrorMessage() {
        logger.info(MessagesGenerator.signingMessages(signUp.getValidationStatus()));
    }
    @Then("the user should not be registered")
    public void theUserShouldNotBeRegistered() {

    }

    @And("error in validation hapneed")
    public void errorInValidationHapneed() {
        signUp.setValidationStatus(5);
        signUp.creatAccount(new DatabaseService());
    }
    @When("the user provides valid registration information new constucotr")
    public void theUserProvidesValidRegistrationInformationNewConstucotr() {
        Profile profile =new Profile(-1,"test","0000","tset");
        this.signUp = new SignUp("valid@example.com","validpass",false,0,profile);
    }
    @When("the user provides valid registration information and sth went weong")
    public void theUserProvidesValidRegistrationInformationAndSthWentWeong() {
        signUp.setValidationStatus(0);
        signUp.creatAccount(null);
        assertTrue(signUp.isHasAccount());
    }
}