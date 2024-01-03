package application.remainingTestsForCoverage;

import application.services.EmailSender;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertTrue;


public class EmailSenderRemainingCoverage {

  private boolean flagForException;

  @When("I create an EmailSender by the constructor")
  public void i_create_an_email_sender_by_the_constructor() {

    try{
       EmailSender.createInstance();
    }catch ( UnsupportedOperationException e ){
      flagForException = true;
    }
  }


  @Then("UnsupportedOperationException will be thrown")
  public void unsupported_operation_exception_will_be_thrown() {
      assertTrue(flagForException);
  }


}
