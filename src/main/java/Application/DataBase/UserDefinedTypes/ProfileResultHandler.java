package Application.DataBase.UserDefinedTypes;

import Application.Entities.Profile;
import Application.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import Application.DataBase.QueryResultHandler;
import Application.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileResultHandler implements QueryResultHandler<Profile> {
    @Override
    public Profile handle(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Profile profile = new Profile();
            profile.setProfileId(resultSet.getInt("profileId"));
            profile.setLocation(resultSet.getString("location"));
            profile.setPhoneNumber(resultSet.getString("phoneNumber"));
            profile.setName(resultSet.getString("name"));
            return profile;
        }
        return null; // Return null if no result
    }
}
