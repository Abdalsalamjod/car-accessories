package Application.DataBase.Premetive_Objects;
import Application.DataBase.QueryResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleResultHandler implements QueryResultHandler<Double> {
  @Override
  public Double handle(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      return resultSet.getDouble(1);
    }
    return 0.0;
  }
}