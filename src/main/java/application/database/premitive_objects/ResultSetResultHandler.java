package application.database.premitive_objects;
import application.database.QueryResultHandler;

import java.sql.ResultSet;


//after using this class, you need to manually close the resultSet
public class ResultSetResultHandler implements QueryResultHandler<ResultSet> {
  @Override
  public ResultSet handle(ResultSet resultSet) {
    return resultSet;
  }
}