package application.remainingTestsForCoverage;

import application.database.premitive_objects.IntResultHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntResultHandlerRemainingCoverage {

  private ResultSet resultSet;
  private Integer result;

  @Given("a ResultSet with a single integer value {int}")
  public void aResultSetWithASingleIntegerValue(int value) throws SQLException {
    resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getInt(1)).thenReturn(value);
  }

  @Given("an empty ResultSet")
  public void anEmptyResultSet() throws SQLException {
    resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false);
  }

  @When("the IntResultHandler handles the ResultSet")
  public void theIntResultHandlerHandlesTheResultSet() throws SQLException {
    IntResultHandler intResultHandler = new IntResultHandler();
    result = intResultHandler.handle(resultSet);
  }

  @Then("the result should be {int}")
  public void theResultShouldBe(int expectedValue) {
    Assert.assertEquals(expectedValue, result.intValue());
  }
}
