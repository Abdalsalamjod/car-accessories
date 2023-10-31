package Application.interfaces_implementators.Premetive_Objects;
import Application.interfaces_implementators.QueryResultHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FloatResultHandler implements QueryResultHandler<Float> {
  @Override
  public Float handle(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      return resultSet.getFloat(1);
    }
    return 0.0f;
  }
}