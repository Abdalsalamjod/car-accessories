package Application.DataBase.Premetive_Objects;
import Application.DataBase.QueryResultHandler;

import java.sql.ResultSet;


//after using this class, you need to manually close the resultSet
public class ResultSetResultHandler implements QueryResultHandler<ResultSet> {
  @Override
  public ResultSet handle(ResultSet resultSet) {
    return resultSet;
  }
}