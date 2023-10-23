package Application;

import Application.Services.SignIn;
import io.cucumber.java.en.Given;

import static org.junit.Assert.*;

public class CommonUserTest {
    SignIn signIn;
    public CommonUserTest() {
        signIn=new SignIn();
    }

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        // Write code here that turns the phrase above into concrete actions
        assertFalse(signIn.signedIn);
    }

}
