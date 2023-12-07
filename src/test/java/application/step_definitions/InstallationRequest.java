package application.step_definitions;

import application.entities.Product;
import application.entities.User;
import application.services.EmailSender;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class InstallationRequest {

  boolean f_1 = false, f_2 = false;
  String subject = "", content = "";

  @Given("the user make a new installation request with valid information")
  public void the_user_make_a_new_installation_request_with_valid_information() {
    f_1 = f_2 = false;
    subject = "";
    content = "";
    User user = new User("s12027670@stu.najah.edu","12345", 'i',true,null);

    Product product = new Product(1,"Spoiler", "exterior", 25.25, 25);
    Date date = new Date();
    int id = 3;
    int productId = product.getId();
    String userId = user.getEmail();
    String description = "added!";
//    Request request = new Request(id, productId, userId,date, description);
//    f_1 = user.makeRequest(request);
//    if(f_1)
//      f_2 = EmailSender.sendEmail("s12027747@stu.najah.edu", "test", request.getDescription());
  }

  @Then("the request should be done")
  public void the_request_should_be_done() {
    assertTrue(f_1);
  }

  @Then("a conformation email will be sent to the user and installer")
  public void a_conformation_email_will_be_sent_to_the_user_and_installer() {
    assertTrue(f_2);
  }


  @Given("the user lists his requests and want to cancel one of them")
  public void the_user_lists_his_requests_and_want_to_cancel_one_of_them() {
    f_1 = f_2 = false;

    User user = new User("s12027670@stu.najah.edu","12345", 'i',true,null);

//    f_1 = user.removeRequest(3);
    if(f_1)
      f_2 = EmailSender.sendEmail("s12027747@stu.najah.edu", "test", "removed !");
  }

  @Then("the request should be removed")
  public void the_request_should_be_removed() {
    assertTrue(f_1);
  }


}
