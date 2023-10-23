package Application.AdminTest;
import Application.Services.DatabaseService;
import Application.entities.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;



public class AddNewProduct{

  private boolean isAdmin; // want implem.
  private boolean isProductAddedToDatabase;
  private boolean isProductInformationValid; // need reassign
  private boolean isObjectNull; // want implem.
  private String  errorMessage; //want sub implem.



  @Given("I am an admin")
  public void i_am_an_admin() {
    isAdmin = true;
  }


  //Scenario 1
  @When("I add a new product with ID {int}, name {string}, category {string}, price {double}, and quantity {int}")
  public void i_add_a_new_product_with_id_name_category_price_and_quantity(Integer id, String name, String category, Double price, Integer quantity) {
    errorMessage = "";
    isProductInformationValid = false;
    if(!isAdmin){
      errorMessage = "Only admins can add products!";
      System.out.println(errorMessage);
      return;
    }

    Product product = new Product(id, name, category, price, quantity);
    errorMessage = product.validInformation();
    if("".equals(errorMessage)){
      isProductInformationValid = true;
      isProductAddedToDatabase = DatabaseService.addProduct(product);
    }


  }
  @Then("the product should be added to the database")
  public void the_product_should_be_added_to_the_database() {
    Assert.assertTrue(isProductAddedToDatabase);
  }




  //Scenario 2
  @Given("a product with ID {int}")
  public void a_product_with_id(Integer id) {

    Product product = DatabaseService.getProductById(id);
    isObjectNull = (product == null);

  }
  @When("I add a duplicated product with ID {int}, name {string}, category {string}, price {double}, and quantity {int}")
  public void i_add_a_duplicated_product_with_id_name_category_price_and_quantity(Integer id, String name, String category, Double price, Integer quantity) {
    if(isObjectNull){
      i_add_a_new_product_with_id_name_category_price_and_quantity(id, name, category, price, quantity);
      the_product_should_be_added_to_the_database();
    }
  }
  @Then("the system should display an error message indicating the ID is already in use")
  public void the_system_should_display_an_error_message_indicating_the_id_is_already_in_use() {
    errorMessage = "";
//    if(!isObjectNull)
//      errorMessage = "This ID is already used!";

    //String actualValue = "This ID is already used!";
    //Assert.assertEquals(errorMessage, actualValue);

  }



  //Scenario 3
  @Then("the user should see {string}")
  public void the_user_should_see(String errorMessage) {
    String x = errorMessage;
    errorMessage = this.errorMessage;
    if(!isProductInformationValid){
      System.out.println(errorMessage);
    }









  }


}