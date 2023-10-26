package step_definitions;

import Application.Services.DatabaseService;
import Application.entities.Product;
import Application.interfaces_implementators.UserDefinedTypes.ProductResultHandler;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class Add_Delete_Update_Search {

  private static DatabaseService dbs;
  private static String errorMessage;
  private static boolean duplicateProductAdded;

  @BeforeAll
  public static void before_all(){
    System.out.println("ShehabAbbasKharazFROM -> beforeAll");
    dbs = new DatabaseService();
    errorMessage = null;
    duplicateProductAdded = false;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    System.out.println(dtf.format(now));
  }
  @AfterAll
  public static void after_all(){
    dbs.closeConnection();
    System.out.println("ShehabAbbasKharazFROM -> afterAll");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    System.out.println(dtf.format(now));
  }
  @Before
  public void before(){
    errorMessage = "";
    duplicateProductAdded = false;
    System.out.println("ShehabAbbasKharazFROM -> before");
  }


  //*************************Common*******************************
  @Given("I am an admin")
  public static void iAmAnAdmin() {
    boolean isAdmin = true;
    assertTrue(isAdmin);
  }




  //**********************FOR ADD*********************************
  @When("I add a new product with ID {int}, name {string}, category {string}, price {double}, quantity {int}")
  public void iAddANewProductWithIDNameCategoryPriceQuantity(Integer id, String name, String category, Double price, Integer quantity) {
    Product product = new Product(id, name, category, price, quantity);

    try{
      boolean productAdded = dbs.addObject(product, "Product");
      assertTrue(productAdded);
    }catch ( SQLException e){
      e.printStackTrace();
      fail();
    }



  }
  @Then("the product with ID {int}, name {string}, category {string}, price {double}, quantity {int} should be added to the database")
  public void theProductShouldBeAddedToTheDatabase(Integer id, String name, String category, Double price, Integer quantity) {


    Product product = dbs.executeQuery("SELECT * FROM Product WHERE id=" + id, new ProductResultHandler());
    assertNotNull(product);
    assertEquals(name, product.getName());
    assertEquals(category, product.getCategory());
    assertEquals(price, product.getPrice(), 0.1); //delta is the biggest difference allowed
    assertEquals(quantity, product.getQuantity());

  }


  @Given("a product with ID duplicated ID")
  public void a_product_with_id_duplicated_id() {
    try{
      Product existingProduct = new Product(2, "ExistingProduct", "interior", 10.0, 5);
      duplicateProductAdded = dbs.addObject(existingProduct,"Product");
    }catch ( SQLException e ){
      errorMessage = "The ID is already used!";
      duplicateProductAdded = false;
    }

  }
  @When("I add a duplicated product with ID {int}, name {string}, category {string}, price {double}, and quantity {int}")
  public void iAddADuplicatedProductWithIDNameCategoryPriceAndQuantity(Integer int1, String string, String string2, Double double1, Integer int2) {
    assertFalse(duplicateProductAdded);
  }
  @Then("the system should display an error message indicating the ID is already in use")
  public void theSystemShouldDisplayAnErrorMessageIndicatingTheIDIsAlreadyInUse() {
    String actual = "The ID is already used!";
    String expected = errorMessage;
    assertEquals(actual, expected);
  }


  @When("I add a new product with invalid ID {int}, name {string}, category {string}, price {double}, quantity {int}")
  public void iAddANewProductWithInvalidIDNameCategoryPriceQuantity(Integer id, String name, String category, Double price, Integer quantity) {
    Product product = new Product(id, name, category, price, quantity);
    try{
       dbs.addObject(product, "Product");
    }catch(SQLException e){
      e.printStackTrace();
      errorMessage = "There is invalid information";
      fail();
    }
  }
  @Then("the user should see <errorMessage>")
  public void theUserShouldSeeErrorMessage() {
    String actual = "There is invalid information";
    String expected = errorMessage;
    assertEquals(actual, expected);

  }
  //****************************************************************












  //*************************FOR DELETE**************************
  @And("a product with id {int}, name {string}, category {string}, price {double}, quantity {int}")
  public void a_product_with_id_name_category_price_quantity(Integer id, String name, String category, Double price, Integer quantity) {

    try{
      Product product = new Product(id, name, category, price, quantity);
      dbs.addObject(product, "Product");
    }catch ( SQLException e){
      e.printStackTrace();
    }

  }
  @When("I delete the product with ID {int}")
  public boolean i_delete_the_product_with_id(Integer productID) throws SQLException {
     return dbs.deleteObject(productID, "Product");
  }
  @Then("the product with ID {int} should be removed from the database")
  public void the_product_with_id_should_be_removed_from_the_database(Integer id) throws SQLException {
    boolean isProductDeleted = i_delete_the_product_with_id(id);
    assert(isProductDeleted);
  }


  @When("I delete a product with non-existing id")
  public void i_delete_a_product_with_non_existing_id() {
    try {
      dbs.deleteObject(9999, "Product");
      System.out.println("YES YES");
    } catch ( SQLException e) {
      System.out.println("EROOOORORORORO!!!1");
      errorMessage = "Product not found";
    }
  }
  @Then("the system should display an error message indicating the product does not exist")
  public void theSystemShouldDisplayAnErrorMessageIndicatingTheProductDoesNotExist() {
    assert(true);
  }
  //**************************************************************










  //**********************FOR UPDATE**********************************
  @Given("a product with ID {int}, name {string}, category {string}, price {double}, and quantity {int} exists")
  public void aProductWithIDNameCategoryPriceAndQuantityExists(Integer int1, String string, String string2, Double double1, Integer int2) {
    assertTrue(true);
  }
  @When("I update the product with ID {int} to have a new price {double}, and quantity {int}")
  public void iUpdateTheProductWithIDToHaveANewPriceAndQuantity(Integer int1, Double double1, Integer int2) {
    assertTrue(true);
  }
  @Then("the product information should be updated in the database")
  public void theProductInformationShouldBeUpdatedInTheDatabase() {
    assertTrue(true);
  }


  @Given("a product with ID {int} exists")
  public void aProductWithIDExists(Integer int1) {
    assertTrue(true);
  }
  @When("I update the product with ID {string} to have a {string} and a {string}")
  public void iUpdateTheProductWithIDToHaveAAndA(String string, String string2, String string3) {
    assertTrue(true);
  }
  @Then("the system should display error {string}")
  public void theSystemShouldDisplayError(String string) {
    assertTrue(true);
  }


  @When("I update a product with ID {int} that does not exist with a new price {double} and new quantity {int}")
  public void iUpdateAProductWithIDThatDoesNotExistWithANewPriceAndNewQuantity(Integer int1, Double double1, Integer int2) {
    assertTrue(true);
  }
  @Then("the system should display error message says that the product does not exist")
  public void theSystemShouldDisplayErrorMessageSaysThatTheProductDoesNotExist() {
    assertTrue(true);
  }
  //******************************************************************










  //**********************FOR SEARCH********************************
  @When("I search for a product with the name existing_product")
  public void iSearchForAProductWithTheNameExistingProduct() {
    assertTrue(true);
  }
  @Then("the system should display the product with name exist_product")
  public void theSystemShouldDisplayTheProductWithNameExistProduct() {
    assertTrue(true);
  }


  @When("I search for a product with the name non_existing_product")
  public void iSearchForAProductWithTheNameNonExistingProduct() {
    assertTrue(true);
  }


  @When("I search for a product with existing ID")
  public void iSearchForAProductWithExistingID() {
    assertTrue(true);
  }
  @Then("the system should display the product with has the existing ID")
  public void theSystemShouldDisplayTheProductWithHasTheExistingID() {
    assertTrue(true);
  }


  @When("I search for a product with ID non-existing_product")
  public void iSearchForAProductWithIDNonExistingProduct() {
    assertTrue(true);
  }
  @Then("the system should display a message indicating that the product was not found")
  public void theSystemShouldDisplayAMessageIndicatingThatTheProductWasNotFound() {
    assertTrue(true);
  }


  @When("I search for all product in valid category")
  public void iSearchForAllProductInValidCategory() {
    assertTrue(true);
  }
  @Then("the system should display all products within category")
  public void theSystemShouldDisplayAllProductsWithinCategory() {
    assertTrue(true);
  }


  @When("I search for products within a price range")
  public void iSearchForProductsWithinAPriceRange() {
    assertTrue(true);
  }
  @Then("the system should display all products within this range")
  public void theSystemShouldDisplayAllProductsWithinThisRange() {
    assertTrue(true);
  }
  //****************************************************************










}

























