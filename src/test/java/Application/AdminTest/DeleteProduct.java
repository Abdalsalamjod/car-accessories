package Application.AdminTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteProduct{

  @Given("a product with ID {string}")
  public void a_product_with_id(String string) {

  }

  @When("I delete the product with ID {string}")
  public void i_delete_the_product_with_id(String string) {

  }

  @Then("the product with ID {string} should be removed from the database")
  public void the_product_with_id_should_be_removed_from_the_database(String string) {

  }

  @When("I delete a product with ID {int} that does not exist")
  public void i_delete_a_product_with_id_that_does_not_exist(Integer int1) {

  }

  @Then("the system should display an error message indicating the product does not exist")
  public void the_system_should_display_an_error_message_indicating_the_product_does_not_exist() {

  }

}