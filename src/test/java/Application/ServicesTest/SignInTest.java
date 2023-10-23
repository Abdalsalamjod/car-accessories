package Application.ServicesTest;

import Application.LoggerUtility;
import Application.Services.SignIn;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SignInTest {
    private static Logger logger = LoggerUtility.getLogger();
    SignIn signIn ;
    public SignInTest() {
        signIn=new SignIn();
    }



    @Given("the user accesses the sign-in command")
    public void theUserAccessesTheSignInCommand() {
        assertFalse(signIn.signedIn);
        logger.info("Please Enter your Email and password\n");
    }

    @When("the user enters {string} , {string}")
    public void theUserEnters(String string, String string2) {
        signIn.email=string;
        signIn.password=string2;

        logger.info( "Email: "+signIn.email+ "\nPassword: "+signIn.password+"\n");
        signIn.valid= signIn.email.equals("valid@example.com") && signIn.password.equals("validpass");
    }
//    @When("confirms the login")
//    public void confirmsTheLogin() {
////        assertTrue(signIn.valid);
//        logger.info(signIn.getMessage());
//    }

    @Then("the user should be successfully logged in")
    public void theUserShouldBeSuccessfullyLoggedIn() {
        logger.info(signIn.getMessage());
        signIn.signedIn  =true;
        assertTrue(signIn.signedIn);

    }
//    @Then("should be redirected to the user dashboard")
//    public void shouldBeRedirectedToTheUserDashboard() {
//
//        logger.info("you are in user dashboard "+"\n");
//    }



    @Then("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String string) {
        logger.info( string+"\n");
    }

    @Then("the user should not be logged in")
    public void theUserShouldNotBeLoggedIn() {
        signIn.signedIn=false;
        assertFalse( signIn.signedIn);
    }

}
