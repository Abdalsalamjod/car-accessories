package application.database.user_defined_types;

import application.database.QueryResultHandler;
import application.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultHandler implements QueryResultHandler<User> {
  @Override
  public User handle(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      User user = new User();
     user.setEmail(resultSet.getString("email"));
     user.setPassword(resultSet.getString("password"));
     user.setRole(resultSet.getString("role").charAt(0));
     user.setProfileId(resultSet.getInt("profileId"));
     user.setSignInStatus(resultSet.getBoolean("signInStatus"));

      return user;
    }
    return null; // Return null if no result
  }
}