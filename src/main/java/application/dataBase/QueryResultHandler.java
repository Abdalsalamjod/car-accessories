package application.dataBase;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryResultHandler<T> {
  T handle(ResultSet resultSet) throws SQLException;
  }

