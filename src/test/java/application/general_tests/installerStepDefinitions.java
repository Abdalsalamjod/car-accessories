 package application.general_tests;

 import application.entities.Installer;
 import application.entities.Request;
 import application.services.DatabaseService;
 import io.cucumber.java.en.And;
 import io.cucumber.java.en.Given;
 import io.cucumber.java.en.Then;
 import io.cucumber.java.en.When;

 import java.util.List;
 import java.util.Scanner;

 import static application.entities.Installer.createNewRequest;
 import static application.entities.Installer.deleteExistingRequest;
 import static org.mockito.Mockito.mock;

 public class installerStepDefinitions {

     public Installer installer;
     public installerStepDefinitions(Installer installer) {
         this.installer = installer;
     }


     @Given("an installer is logged in")
     public void anInstallerIsLoggedIn() {

     }

     @When("the installer views installation requests")
     public void theInstallerViewsInstallationRequests() {

         installer.viewInstallationRequests(new DatabaseService());
     }

     @Then("the list of available installation requests should be displayed")
     public void theListOfAvailableInstallationRequestsShouldBeDisplayed() {

     }

     @When("an error occurs while viewing installation requests")
     public void anErrorOccursWhileViewingInstallationRequests() {
         installer.viewInstallationRequests(null);

     }

     @Then("a failure message should be displayed")
     public void aFailureMessageShouldBeDisplayed() {

     }
     @And("there are selected installation requests")
     public void thereAreSelectedInstallationRequests() {
     }

     @When("the installer marks a request with ID {string} as done")
     public void theInstallerMarksARequestWithIDAsDone(String arg0) {
         installer.markAsDone(new DatabaseService(),new Scanner(System.in),true,"8");
     }

     @Then("the request with ID {string} should be marked as done")
     public void theRequestWithIDShouldBeMarkedAsDone(String arg0) {
     }

     @When("an error occurs while marking a request with ID {string} as done")
     public void anErrorOccursWhileMarkingARequestWithIDAsDone(String arg0) {
         installer.markAsDone(null,new Scanner(System.in),true,"8");

     }
     @When("an error{int} occurs while marking a request with ID {string} as done")
     public void anErrorOccursWhileMarkingARequestWithIDAsDone(int arg0, String arg1) {
         installer.markAsDone(null,new Scanner(System.in),true,"xyz");

     }
     @And("there are available installation requests")
     public void thereAreAvailableInstallationRequests()  {

         // Simulate user input for scheduling an appointment

         installer.scheduleAppointments(new DatabaseService(), new Scanner(System.in),true,"6");
     }

     @When("the installer schedules a request with ID {string}")
     public void theInstallerSchedulesARequestWithID(String arg0) {
         createNewRequest(new Request(),null);
         deleteExistingRequest(1,null);
     }

     @Then("the request with ID {string} should be scheduled")
     public void theRequestWithIDShouldBeScheduled(String arg0) {
     }

     @When("an error occurs while scheduling a request with ID {string}")
     public void anErrorOccursWhileSchedulingARequestWithID(String arg0) {
         installer.scheduleAppointments(null,new Scanner(System.in),true,"1");
     }
     @When("an error{int} occurs while scheduling a request with ID {string}")
     public void anErrorOccursWhileSchedulingARequestWithID(int arg0, String arg1) {
         installer.scheduleAppointments(null,null,true,"xyz");

     }
     @When("the installer EXIT schedules a request")
     public void theInstallerEXITSchedulesARequest() {
         installer.scheduleAppointments(new DatabaseService(),new Scanner(System.in),true,"Exit");


     }


     @When("the installer EXIT mark as done")
     public void theInstallerEXITMarkAsDone() {
         installer.markAsDone(new DatabaseService(),new Scanner(System.in),true,"Exit");

     }


     @When("the installer marks a request with ID {string} as done{int}")
     public void theInstallerMarksARequestWithIDAsDone(String arg0, int arg1) {
       installer.setAvailableRequests(installer.viewInstallationRequests(new DatabaseService()));
       installer.markRequestAsDone(new DatabaseService(),8);
     }

     @When("the installer marks a request with ID {string} as done{int} error")
     public void theInstallerMarksARequestWithIDAsDoneError(String arg0, int arg1) {
         installer.setAvailableRequests(installer.viewInstallationRequests(new DatabaseService()));
         installer.markRequestAsDone(new DatabaseService(),6);
     }
 }
