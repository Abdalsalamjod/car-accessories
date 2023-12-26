package application.step_definitions;

import application.dataBase.Premetive_Objects.ResultSetResultHandler;
import application.dataBase.UserDefinedTypes.RequestResultHandler;
import application.entities.Installer;
import application.entities.Request;
import application.services.DatabaseService;
import application.services.EmailSender;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class InstallationRequest {

  private final DatabaseService dbs = new DatabaseService();
  private final Request tempRequestToSend = new Request();
  private Request tempRequestToReceive = new Request();
  String removedDate = "";


  @When("user make a new installation request with id = {int}, productId = {int}, userId = {string}, description = {string}")
  public void user_make_a_new_installation_request_with_id_product_id_user_id_description(Integer id, Integer productId, String userId, String description) {

    Request.initializeDatesArray();
    String dateString = Request.getDatesArray().get(2);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime parsedDate = LocalDateTime.parse(dateString, formatter);

    tempRequestToSend.setId(id);
    tempRequestToSend.setProductId(productId);
    tempRequestToSend.setUserId(userId);
    tempRequestToSend.setDate(parsedDate);
    tempRequestToSend.setDescription(description);
    tempRequestToSend.setDone(0);
    tempRequestToSend.setSelected(0);
    System.out.println(tempRequestToSend);
    Installer.createNewRequest(tempRequestToSend, dbs);

  }

  @Then("the installation request with id = {int} should be added to the database")
  public void the_installation_request_with_id_should_be_added_to_the_database(Integer id) {

    try {

      String query = "SELECT * FROM Request WHERE id=" + id;
      tempRequestToReceive = dbs.executeQuery(query, new RequestResultHandler());
      assertEquals(tempRequestToReceive.getId(), tempRequestToSend.getId());
      assertEquals(tempRequestToReceive.getProductId(), tempRequestToSend.getProductId());
      assertEquals(tempRequestToReceive.getUserId(), tempRequestToSend.getUserId());
      System.out.println("tempRequestToReceive.getDate(): " + tempRequestToReceive.getDate());
      System.out.println("tempRequestToSend.getDate()" + tempRequestToSend.getDate());
      assertEquals(tempRequestToReceive.getDate(), tempRequestToSend.getDate());
      assertEquals(tempRequestToReceive.getDescription(), tempRequestToSend.getDescription());
      assertEquals(tempRequestToReceive.getDone(), tempRequestToSend.getDone());
      assertEquals(tempRequestToReceive.getSelected(), tempRequestToSend.getSelected());

    } catch ( SQLException e ) {
      throw new RuntimeException(e);
    }

  }

  @Then("an email should be send to the installer with email = {string} and user with id= = {string}")
  public void an_email_should_be_send_to_the_installer_with_email_and_user_with_id(String installerEmail, String userEmail) {
    String subject = "Hala-car accessories system";
    String body = "Done!";
    EmailSender.sendEmail(installerEmail, subject, body);
    EmailSender.sendEmail(userEmail, subject, body);
  }


  @Then("the date with index = {int} should be removed from dates array")
  public void the_date_with_index_should_be_removed_from_dates_array(int dateIndex) {

    ArrayList<String> datesArray = ( ArrayList<String> ) Request.getDatesArray();
    removedDate = datesArray.get(dateIndex);
    datesArray.remove(dateIndex);
    Request.setDatesArray(datesArray);

  }


  @When("user remove the request with id={int}")
  public void user_remove_the_request_with_id(Integer id) {
    Installer.deleteExistingRequest(id, dbs);
  }

  @Then("the request with id={int} should be removed")
  public void the_request_with_id_should_be_removed(Integer id) {
    try {
      tempRequestToReceive = dbs.executeQuery("SELECT * FROM Request WHERE id=" + id, new RequestResultHandler());
      assertNull(tempRequestToReceive);
    } catch ( SQLException e ) {
      throw new RuntimeException(e);
    }

  }



  @Then("the removed date should be available again in the dates array")
  public void the_removed_date_should_be_available_again_in_the_dates_array() {

    try{
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      ArrayList<Request> returnedRequests = new ArrayList<>();
      ArrayList<String> datesArray = ( ArrayList<String> ) Request.getDatesArray();

      ResultSet rs = dbs.executeQuery("SELECT * FROM Request WHERE userId ='s12027747@stu.najah.edu'", new ResultSetResultHandler());
      while ( rs.next() ){
        String date =  rs.getString(4).substring(0, 19);
        returnedRequests.add(new Request(rs.getInt(1), rs.getInt(2), rs.getString(3), LocalDateTime.parse(date, formatter), rs.getString(5)));
      }


      datesArray.add(this.removedDate);
      Request.setDatesArray(datesArray);

    }catch ( Exception e ){
      e.printStackTrace();
    }

  }






}
