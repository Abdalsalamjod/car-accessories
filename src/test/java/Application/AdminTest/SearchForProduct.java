package Application.AdminTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchForProduct{


  @When("I search for a product with the name {string}")
  public void i_search_for_a_product_with_the_name(String string) {

  }

  @Then("the system should display the product with name {string}")
  public void the_system_should_display_the_product_with_name(String string) {

  }

  @Then("the system should display a message indicating that the product was not found")
  public void the_system_should_display_a_message_indicating_that_the_product_was_not_found() {

  }

  @When("I search for a product with ID {int}")
  public void i_search_for_a_product_with_id(Integer int1) {

  }

  @Then("the system should display the product with ID {int}")
  public void the_system_should_display_the_product_with_id(Integer int1) {

  }

  @When("I search for products in the category {string}")
  public void i_search_for_products_in_the_category(String string) {

  }

  @Then("the system should display all products within category ExistingCategory and with their respective details")
  public void the_system_should_display_all_products_within_category_existing_category_and_with_their_respective_details() {

  }

  @When("I search for products within the price range ${double} to ${double}")
  public void i_search_for_products_within_the_price_range_$_to_$(Double double1, Double double2) {

  }

  @Then("the system should display all products within this price range and with their respective details")
  public void the_system_should_display_all_products_within_this_price_range_and_with_their_respective_details() {

  }


}