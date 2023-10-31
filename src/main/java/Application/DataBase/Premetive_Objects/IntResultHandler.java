package Application.DataBase.Premetive_Objects;
import Application.DataBase.QueryResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntResultHandler implements QueryResultHandler<Integer> {
  @Override
  public Integer handle(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      return resultSet.getInt(1);
    }
    return 0;
  }
}