package application.remainingTestsForCoverage;

import application.entities.Product;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ProductRemainingCoverage {

  private Product product;


  @When("I create a product by the constructor")
  public void i_create_a_product_by_the_constructor() {
    this.product = new Product(100, "coverage", "exterior", 120.0, 120);
  }

  @Then("the product should be created")
  public void the_product_should_be_created() {
    assertNotNull(this.product);
  }

  @Then("when calling toString it should be in the form was set")
  public void when_calling_to_string_it_should_be_in_the_form_was_set() {
    String returnedFromToString = this.product.toString();
    assertTrue(returnedFromToString.contains("ID:"));
    assertTrue(returnedFromToString.contains("Name:"));
    assertTrue(returnedFromToString.contains("Category:"));
    assertTrue(returnedFromToString.contains("Price:"));
    assertTrue(returnedFromToString.contains("Quantity:"));
  }

}
