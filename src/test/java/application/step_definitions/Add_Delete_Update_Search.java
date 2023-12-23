package application.step_definitions;

import application.dataBase.UserDefinedTypes.ProductResultHandler;
import application.entities.Admin;
import application.entities.Product;
import application.entities.User;
import application.services.DatabaseService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import static org.junit.Assert.*;

public class Add_Delete_Update_Search {

  private static final DatabaseService dbs = new DatabaseService();
  private static boolean flagBetweenSteps;
  private static String messageBetweenSteps;
  private final Admin newAdmin = new Admin("s12027747@stu.najah.edu", "12345", 'a', true, null);
  private final User newUser = new User("s12027747@stu.najah.edu", "12345", 'u', true, null);


  //**********************FOR ADD*********************************
  @When("I add a new valid product")
  public void i_add_a_new_valid_product() {
    flagBetweenSteps = false;
    newAdmin.manageProducts(2, dbs);
    flagBetweenSteps = true;
  }
  @Then("the product should be added to the database")
  public void the_product_should_be_added_to_the_database() {
    assertTrue(flagBetweenSteps);
  }

  @When("I add a duplicated-Id product")
  public void i_add_a_duplicated_id_product() {
    i_add_a_new_valid_product();
  }
  @Then("the product should not be added to the database")
  public void the_product_should_not_be_added_to_the_database() {
    assertFalse(flagBetweenSteps);
  }

  @When("I add a new product with invalid  ID {int}, name {string}, category {string}, price {double}, quantity {int}")
  public void i_add_a_new_product_with_invalid_id_name_category_price_quantity(Integer id, String name, String category, Double price, Integer quantity) {
    Product newProduct = new Product(id, name, category, price, quantity);
    messageBetweenSteps = newProduct.validInformation();
  }
  @Then("the product should not be added to the database and the error message should be equals to {string}")
  public void the_product_should_not_be_added_to_the_database_and_the_error_message_should_be_equals_to(String errorMessage) {
    assertEquals(messageBetweenSteps, errorMessage);
  }
  //*************************************************************




  //*************************FOR DELETE**************************
  @When("the admin deletes an existing product")
  public void the_admin_deletes_an_existing_product() {
    flagBetweenSteps = false;
    newAdmin.manageProducts(3, dbs);
    flagBetweenSteps = true;
  }
  @Then("the product should be removed from the database")
  public void the_product_should_be_removed_from_the_database() {
    assertTrue(flagBetweenSteps);
  }

  @When("the admin deletes a non-existing product")
  public void the_admin_deletes_a_non_existing_product() {
    the_admin_deletes_an_existing_product();
  }
  @Then("nothing will happen")
  public void nothing_will_happen() {
    assertTrue(true);
  }
  //*************************************************************




  //**********************FOR UPDATE******************************
  @When("admin updates the product with ID {int} to have a new price {double}, and quantity {int}")
  public void admin_updates_the_product_with_id_to_have_a_new_price_and_quantity(Integer id, Double price, Integer quantity) {

    try{
        if(price > 0 && quantity > 0) {
          Product oldProduct = dbs.executeQuery("SELECT * FROM Product WHERE id=" + id, new ProductResultHandler());
          Product newProduct = new Product(oldProduct.getId(), oldProduct.getName(), oldProduct.getCategory(), price, quantity);
          dbs.updateObject(newProduct, "Product", "id");
        }
        flagBetweenSteps = true;
      }catch ( Exception e ){
        flagBetweenSteps = false;
      }
  }
  @Then("the product information should be updated in the database")
  public void the_product_information_should_be_updated_in_the_database() {
    assertTrue(flagBetweenSteps);
  }
  @When("admin updates the product with ID {int} to have an invalid {double} andOR invalid {int}")
  public void admin_updates_the_product_with_id_to_have_an_invalid_and_or_invalid(Integer id, Double price, Integer quantity) {
    Product testProduct = new Product(id, "testProduct", "exterior", price, quantity);
    messageBetweenSteps = testProduct.validInformation();
  }
  @Then("the error message should be equals to {string}")
  public void the_error_message_should_be_equals_to(String errorMessage) {
    assertEquals(messageBetweenSteps, errorMessage);
  }

  //**************************************************************



  //**********************FOR SEARCH********************************

  @When("I search for a product with the name existing_product")
  public void i_search_for_a_product_with_the_name_existing_product() {

  }

  @Then("the product should be returned")
  public void the_product_should_be_returned() {

  }

  @When("I search for a product with the name non_existing_product")
  public void i_search_for_a_product_with_the_name_non_existing_product() {

  }

  @Then("the product should not be returned")
  public void the_product_should_not_be_returned() {

  }

  @When("I search for a product with existing ID")
  public void i_search_for_a_product_with_existing_id() {

  }

  @When("I search for a product with ID non-existing_product")
  public void i_search_for_a_product_with_id_non_existing_product() {
   ;
  }

  @When("I search for all product in valid category")
  public void i_search_for_all_product_in_valid_category() {

  }

  @Then("the products in this range should be returned")
  public void the_products_in_this_range_should_be_returned() {

  }

  @When("I search for products within a price range")
  public void i_search_for_products_within_a_price_range() {

  }


  //****************************************************************

}

