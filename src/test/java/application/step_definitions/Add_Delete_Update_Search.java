package application.step_definitions;

import application.dataBase.UserDefinedTypes.ProductResultHandler;
import application.entities.Product;
import application.services.DatabaseService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.*;

public class Add_Delete_Update_Search {

  private static DatabaseService dbs = new DatabaseService();
  private final Product tempProductToSend = new Product();
  private Product tempProductToReceive = new Product();
  private ResultSet tempResultSetToReceive;
  private Boolean done;
  private String query;
  private String errorMessage;



  //**********************FOR ADD*********************************
  @When("I add a new valid product with id={int} name = {string} category = {string} price = {double} quantity = {int}")
  public void i_add_a_new_valid_product_with_id_name_category_price_quantity(Integer id, String name, String category, Double price, Integer quantity) {

    this.setProductDetails(id, name, category, price, quantity, tempProductToSend);

    try {
        dbs.addObject(tempProductToSend, "Product");
    } catch ( SQLException e ) {
        e.printStackTrace();
    }

  }
  @Then("the product with id={int} name = {string} category = {string} price = {double} quantity = {int} should be added to the database")
  public void the_product_with_id_name_category_price_quantity_should_be_added_to_the_database(Integer id, String name, String category, Double price, Integer quantity) {

    query = "SELECT * FROM Product WHERE id=" + id;
    try {
      tempProductToReceive = dbs.executeQuery(query, new ProductResultHandler());
      assertEquals(tempProductToReceive.getId(), id);
      assertEquals(tempProductToReceive.getName(), name);
      assertEquals(tempProductToReceive.getCategory(), category);
      assertEquals(tempProductToReceive.getPrice(), price);
      assertEquals(tempProductToReceive.getQuantity(), quantity);

    } catch ( SQLException e ) {
      e.printStackTrace();
    }
  }


  @When("I add a product with duplicated id = {int} name = {string} category = {string} price = {double} quantity = {int}")
  public void i_add_a_product_with_duplicated_id_name_category_price_quantity(Integer id, String name, String category, Double price, Integer quantity) {
    this.setProductDetails(id, name, category, price, quantity, tempProductToSend);
    try {
      dbs.addObject(tempProductToSend, "Product");
      done = false;
    } catch ( SQLException e ) {
     assertTrue(true);
     done = true;
    }

  }
  @Then("the product with this id should not be added to the database")
  public void the_product_with_id_should_not_be_added_to_the_database() {
    assertTrue(done);
  }


  @When("I add a new product with invalid  ID {int}, name {string}, category {string}, price {double}, quantity {int}")
  public void i_add_a_new_product_with_invalid_id_name_category_price_quantity(Integer id, String name, String category, Double price, Integer quantity) {
    this.setProductDetails(id, name, category, price, quantity, tempProductToSend);
    errorMessage = tempProductToSend.validInformation();
  }


  @Then("the product should not be added to the database and the error message {string}")
  public void the_product_should_not_be_added_to_the_database_and_the_error_message(String string) {
    assertEquals(errorMessage, string);
  }

  //*************************************************************




  //*************************FOR DELETE**************************
  @When("the admin deletes a product with id={int}")
  public void the_admin_deletes_a_product_with_id(Integer id) {

    try {
      done =  dbs.deleteObject(id, "Product");
    } catch ( SQLException e ) {
      e.printStackTrace();
      done = false;
    }

  }
  @Then("the product with this id should removed from database")
  public void the_product_with_this_id_should_removed_from_database() {
    assertTrue(done);
  }

  @Then("nothing will happen")
  public void nothing_will_happen() {
    assertTrue(true);
  }

  //*************************************************************




  //**********************FOR UPDATE******************************
  @When("admin updates the product with ID {int} to have a new price {double}, and quantity {int}")
  public void admin_updates_the_product_with_id_to_have_a_new_price_and_quantity(Integer id, Double price, Integer quantity) {
    dbs = new DatabaseService();
    query = "SELECT * FROM Product WHERE id=" + id;
    try {
      tempProductToReceive = dbs.executeQuery(query, new ProductResultHandler());
    } catch ( SQLException e ) {
      e.printStackTrace();
    }

    tempProductToReceive.setPrice(price);
    tempProductToReceive.setQuantity(quantity);

    try {
      dbs.updateObject(tempProductToReceive, "Product", "id");
      done = true;
    } catch ( Exception e ) {
      done = false;
      e.printStackTrace();
    }

  }
  @Then("the with ID {int} price should be updated to {double} and the product quantity should be updated to {int}")
  public void the_with_id_price_should_be_updated_to_and_the_product_quantity_should_be_updated_to(Integer id, Double price, Integer quantity) {

    try {
      dbs = new DatabaseService();
      tempProductToReceive = Product.getProductById(id, dbs);

    } catch ( Exception e ) {
      e.printStackTrace();
    }
    assertEquals(tempProductToReceive.getPrice(), price, 1);
    assertEquals(tempProductToReceive.getQuantity(), quantity);
  }

  @When("admin updates the product with ID {int} to have an invalid {double} andOR invalid {int}")
  public void admin_updates_the_product_with_id_to_have_an_invalid_and_or_invalid(Integer id, Double price, Integer quantity) {
    try {
      dbs = new DatabaseService();
      tempProductToReceive = Product.getProductById(id, dbs);
      tempProductToReceive.setId(id);
      tempProductToReceive.setPrice(price);
      tempProductToReceive.setQuantity(quantity);
      errorMessage = tempProductToReceive.validInformation();
    } catch ( Exception e ) {
      e.printStackTrace();
    }

  }
  @Then("the error message should be equals to {string}")
  public void the_error_message_should_be_equals_to(String errorMessage) {
    assertEquals(this.errorMessage, errorMessage);
  }

  //**************************************************************



  //**********************FOR SEARCH********************************
  @When("I search for a product with name = {string}")
  public void i_search_for_a_product_with_name(String name) {

    try {
      tempProductToReceive = Product.getProductByName(name, dbs);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  @Then("the product should be returned")
  public void the_product_should_be_returned() {
    assertNotNull(tempProductToReceive);
  }

  @When("I search for a product with the name {string}")
  public void i_search_for_a_product_with_the_name(String name) {

    try {
      tempProductToReceive =Product.getProductByName(name, dbs);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  @Then("the product should not be returned")
  public void the_product_should_not_be_returned() {
    assertNull(tempProductToReceive);
  }

  @When("I search for a product with id={int}")
  public void i_search_for_a_product_with_id(Integer id) {

    try {
      tempProductToReceive = Product.getProductById(id, dbs);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  @When("I search for all products within {string} category")
  public void i_search_for_all_products_within_category(String category) {
    try {
      tempResultSetToReceive = Product.getProductsByCategory(category, dbs);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  @Then("the products in {string} category should be returned")
  public void the_products_in_category_should_be_returned(String category) {
    try{
      while ( tempResultSetToReceive.next() )
        assertEquals(category, tempResultSetToReceive.getString(3));

    }catch ( Exception e ){
      e.printStackTrace();
    }
  }

  @When("I search for all products within a lower price = {int} and upper price = {int}")
  public void i_search_for_all_products_within_a_lower_price_and_upper_price(Integer lowerPrice, Integer upperPrice) {
    try {
      tempResultSetToReceive = Product.getProductsByPriceRange(lowerPrice, upperPrice, dbs);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  @Then("the products in range lower = {int} and upper = {int} should be returned")
  public void the_products_in_range_lower_and_upper_should_be_returned(Integer lowerPrice, Integer upperPrice) {
      try{
        while(tempResultSetToReceive.next()){
          if(tempResultSetToReceive.getDouble(4) >= lowerPrice && tempResultSetToReceive.getDouble(4) <= upperPrice)
            assertTrue(true);
          else
            fail();
        }


      }catch(Exception e){
        e.printStackTrace();
      }
  }

  @When("I need to see all product")
  public void i_need_to_see_all_product() {
    try {
      tempResultSetToReceive = Product.getAllProducts(dbs);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  @Then("all products should be returned")
  public void all_products_should_be_returned() {

    try{
      while(tempResultSetToReceive.next())
        assertNotNull(tempResultSetToReceive);

    }catch(Exception e){
      e.printStackTrace();
    }
  }

  @When("I need to see all products names")
  public void i_need_to_see_all_products_names() {

    try {
      tempResultSetToReceive = Product.getAllProductsNames(dbs);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  @Then("all products names should be returned")
  public void all_products_names_should_be_returned() {
    try{
      while(tempResultSetToReceive.next())
        assertNotNull(tempResultSetToReceive.getString(2));

    }catch(Exception e){
      e.printStackTrace();
    }

  }

  //****************************************************************




  private void setProductDetails(Integer id, String name, String category, Double price, Integer quantity, Product product) {
    product.setId(id);
    product.setName(name);
    product.setCategory(category);
    product.setPrice(price);
    product.setQuantity(quantity);
  }

}


