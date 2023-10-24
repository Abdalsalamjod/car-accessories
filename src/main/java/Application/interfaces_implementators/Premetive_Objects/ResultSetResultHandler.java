package Application.interfaces_implementators.Premetive_Objects;
import Application.interfaces_implementators.QueryResultHandler;
import java.sql.ResultSet;


//after using this class, you need to manually close the resultSet
public class ResultSetResultHandler implements QueryResultHandler<ResultSet> {
  @Override
  public ResultSet handle(ResultSet resultSet) {
    return resultSet;
  }
}