package Application.DataBase.UserDefinedTypes;

import Application.DataBase.QueryResultHandler;
import Application.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultHandler implements QueryResultHandler<User> {
  @Override
  public User handle(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      User user = new User();
     // user.setId(resultSet.getInt("id"));
    //  user.setUsername(resultSet.getString("username"));
      // Set other fields as needed
      return user;
    }
    return null; // Return null if no result
  }
}