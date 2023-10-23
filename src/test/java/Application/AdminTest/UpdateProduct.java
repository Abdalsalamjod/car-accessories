package Application.AdminTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateProduct{


  @Given("a product with ID {int}, name {string}, category {string}, price {double}, and quantity {int} exists")
  public void a_product_with_id_name_category_price_and_quantity_exists(Integer int1, String string, String string2, Double double1, Integer int2) {

  }

  @When("I update the product with ID {int} to have a new price {double}, and quantity {int}")
  public void i_update_the_product_with_id_to_have_a_new_price_and_quantity(Integer int1, Double double1, Integer int2) {

  }

  @Then("the product information should be updated in the database")
  public void the_product_information_should_be_updated_in_the_database() {

  }

  @Given("a product with ID {int} exists")
  public void a_product_with_id_exists(Integer int1) {

  }

  @When("I update the product with ID {string} to have a {string} and a {string}")
  public void i_update_the_product_with_id_to_have_a_and_a(String string, String string2, String string3) {

  }

  @Then("the system should display error {string}")
  public void the_system_should_display_error(String string) {

  }

  @When("I update a product with ID {int} that does not exist with a new price {double} and new quantity {int}")
  public void i_update_a_product_with_id_that_does_not_exist_with_a_new_price_and_new_quantity(Integer int1, Double double1, Integer int2) {

  }

  @Then("the system should display error message says that the product does not exist")
  public void the_system_should_display_error_message_says_that_the_product_does_not_exist() {

  }


}