package application.step_definitions;


public class ProductManipulation {
//
//  private boolean doneFlag = false;
//  private String  errorMessage = "";
//  private String  validMessage = "";
//  private Product returnedProduct;

//
//  @Given( "I am an admin" )
//  public void i_am_an_admin() {
//    assertTrue(true);
//  }
//
//
//
//  //********************************************* ADD  ***************************
//
//  //Done
//  @When( "I add a new product with valid ID, name, category, price, quantity" )
//  public void i_add_a_new_product_with_valid_id_name_category_price_quantity() {
//
//    try{
//
//      doneFlag = true;
//      Product newProduct = new Product(4, "AddProduct", "interior", 80.0, 80);
//      DatabaseService dbs = new DatabaseService();
//      dbs.addObject(newProduct, "Product");
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//  }
//  @Then( "the product should be added to the database" )
//  public void the_product_should_be_added_to_the_database() {
//    if(doneFlag)
//      System.out.println("Product added successfully !");
//
//    else
//      fail();
//  }
//
//
//  //Done
//  @When( "I add a product with duplicated ID" )
//  public void i_add_a_product_with_duplicated_id() {
//
//     try{
//
//       Product newProduct = new Product(1, "AddProduct", "interior", 80.0, 80);
//       DatabaseService dbs = new DatabaseService();
//       dbs.addObject(newProduct, "Product");
//
//     }catch ( SQLException e ){
//       errorMessage = "Cannot add product, duplicated ID !";
//     }
//
//  }
//  @Then( "the system should show that the ID is already exist" )
//  public void the_system_should_show_that_the_id_is_already_exist() {
//
//    String actual = errorMessage;
//    String expected = "Cannot add product, duplicated ID !";
//    assertEquals(actual, expected);
//    System.out.println("Cannot add product, duplicated ID !");
//
//  }
//
//
//  //Done
//  @When( "I add a new product with invalid ID {int}, name {string}, category {string}, price {double}, quantity {int}" )
//  public void i_add_a_new_product_with_invalid_id_name_category_price_quantity( Integer id, String name, String category, Double price, Integer quantity ) {
//
//      doneFlag = true;
//      Product newProduct = new Product(id, name, category, price, quantity);
//      validMessage = newProduct.validInformation();
//
//  }
//  @Then( "the system should show invalid information" )
//  public void the_system_should_show_invalid_information() {
//    if(validMessage.equals(""))
//      fail();
//    else{
//      System.out.println(validMessage);
//      assertTrue(true);
//    }
//
//
//
//  }
//
//
// //************************** DELETE *************************************
//
//
//  //Done
//  @When("I delete existing product")
//  public void i_delete_existing_product() {
//
//    try{
//
//      doneFlag = true;
//      DatabaseService dbs = new DatabaseService();
//      dbs.deleteObject(1, "Product");
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//
//  }
//  @Then("the existing product should be deleted")
//  public void the_existing_product_should_be_deleted() {
//    assertTrue(doneFlag);
//  }
//
//  //Done
//  @When("I delete non-existing product")
//  public void i_delete_non_existing_product() {
//    try{
//
//      doneFlag = true;
//      DatabaseService dbs = new DatabaseService();
//      dbs.deleteObject(1000, "Product");
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//  }
//  @Then("the system should show that the product is not exist")
//  public void the_system_should_show_that_the_product_is_not_exist() {
//    assertTrue(doneFlag);
//    System.out.println("Cannot delete product, the product does not exist");
//  }
//
//
//  //************************* UPDATE ************************************
//
//
//  //Done
//  @When("I update the product with ID {int} to have a new name {string}, category {string}, price {double}, and quantity {int}")
//  public void i_update_the_product_with_id_to_have_a_new_name_category_price_and_quantity(Integer id, String name, String category, Double price, Integer quantity) {
//
//    try{
//
//      doneFlag = true;
//      Product newProduct = new Product(id, name, category, price, quantity);
//      DatabaseService dbs = new DatabaseService();
//      dbs.updateObject(newProduct, "Product", "id");
//
//    }catch ( Exception e ){
//      doneFlag = false;
//    }
//
//  }
//  @Then("the product information should be updated in the database")
//  public void the_product_information_should_be_updated_in_the_database() {
//    assertTrue(doneFlag);
//  }
//
//  //Done
//  @When("I update the product with ID {int}, name {string}, category {string}, price {double}, and quantity {int} to have invalid data")
//  public void i_update_the_product_with_id_name_category_price_and_quantity_to_have_invalid_data(Integer id, String name, String category, Double price, Integer quantity) {
//
//    doneFlag = true;
//    Product newProduct = new Product(id, name, category, price, quantity);
//    validMessage = newProduct.validInformation();
//
//
//  }
//  @Then("the system should display error message")
//  public void the_system_should_display_error_message() {
//    if(validMessage.equals(""))
//      fail();
//    else {
//      System.out.println(validMessage);
//      assertTrue(true);
//    }
//  }
//
//  //Done
//  @When("I update a non existing product")
//  public void i_update_a_non_existing_product() {
//    try{
//
//      doneFlag = true;
//      Product newProduct = new Product();
//      DatabaseService dbs = new DatabaseService();
//      dbs.updateObject(newProduct, "Product", "id");
//
//    }catch ( Exception e ){
//      doneFlag = false;
//    }
//  }
//  @Then("the system should display that the product does not exist")
//  public void the_system_should_display_that_the_product_does_not_exist() {
//    assertTrue(doneFlag);
//    System.out.println("Cannot update product, the product does not exist");
//  }
//
//
//
//  //************************* SEARCH ************************************
//
//
//  //Done
//  @When("I search for a product with name")
//  public void i_search_for_a_product_with_name() {
//
//    try{
//
//      doneFlag = true;
//      DatabaseService dbs = new DatabaseService();
//      returnedProduct = dbs.executeQuery("SELECT * FROM Product WHERE name='Spoiler'", new ProductResultHandler());
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//  }
//  @Then("the system should display the product with this name")
//  public void the_system_should_display_the_product_with_this_name() {
//    assertTrue(doneFlag);
//    System.out.println(returnedProduct.getId() + " " + returnedProduct.getName() + " " + returnedProduct.getPrice() + " " + returnedProduct.getCategory() + " " + returnedProduct.getQuantity());
//  }
//
//
//  //Done
//  @When("I search for a product with non existing name")
//  public void i_search_for_a_product_with_non_existing_name() {
//
//    try{
//
//      doneFlag = true;
//      DatabaseService dbs = new DatabaseService();
//      returnedProduct = dbs.executeQuery("SELECT * FROM Product WHERE name='non-existing'", new ProductResultHandler());
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//  }
//  @Then("the system should display that product does not exist")
//  public void the_system_should_display_that_product_does_not_exist() {
//    if(doneFlag)
//      System.out.println("The product with the name you entered does not exist");
//
//    assertTrue(doneFlag);
//  }
//
//
//  //Done
//  @When("I search for a product with ID")
//  public void i_search_for_a_product_with_id() {
//
//    try{
//
//      doneFlag = true;
//      DatabaseService dbs = new DatabaseService();
//      returnedProduct = dbs.executeQuery("SELECT * FROM Product WHERE id=2", new ProductResultHandler());
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//
//  }
//  @Then("the system should display the product with this ID")
//  public void the_system_should_display_the_product_with_this_id() {
//    assertTrue(doneFlag);
//    System.out.println(returnedProduct.getId() + " " + returnedProduct.getName() + " " + returnedProduct.getPrice() + " " + returnedProduct.getCategory() + " " + returnedProduct.getQuantity());
//  }
//
//
//  //Done
//  @When("I search for a product with non existing ID")
//  public void i_search_for_a_product_with_non_existing_id() {
//    try{
//
//      doneFlag = true;
//      DatabaseService dbs = new DatabaseService();
//      returnedProduct = dbs.executeQuery("SELECT * FROM Product WHERE id=789", new ProductResultHandler());
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//  }
//  @Then("the system should display that the product with this id does not exist")
//  public void the_system_should_display_that_the_product_with_this_id_does_not_exist() {
//
//    if(doneFlag)
//      System.out.println("The product with the id you entered does not exist");
//
//    assertTrue(doneFlag);
//  }
//
//
//  //Done
//  @When("I search for all product in a category")
//  public void i_search_for_all_product_in_a_category() {
//
//    try{
//
//      doneFlag = true;
//      DatabaseService dbs = new DatabaseService();
//      ResultSet rs = dbs.executeQuery("SELECT * FROM Product WHERE category='electronics'", new ResultSetResultHandler());
//      while ( rs.next() )
//        System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDouble(4) + " " + rs.getInt(5));
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//
//  }
//  @Then("the system should display all products within this category")
//  public void the_system_should_display_all_products_within_this_category() {
//    assertTrue(doneFlag);
//  }
//
//
//  @When("I search for products within a price range")
//  public void i_search_for_products_within_a_price_range() {
//
//    try{
//
//      doneFlag = true;
//      DatabaseService dbs = new DatabaseService();
//      ResultSet rs = dbs.executeQuery("SELECT * FROM Product WHERE price BETWEEN 10.0 AND 50.0", new ResultSetResultHandler());
//      while ( rs.next() )
//        System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDouble(4) + " " + rs.getInt(5));
//
//    }catch ( SQLException e ){
//      doneFlag = false;
//    }
//  }
//  @Then("the system should display all products within this range")
//  public void the_system_should_display_all_products_within_this_range() {
//    assertTrue(doneFlag);
//  }
//
//
//








}
