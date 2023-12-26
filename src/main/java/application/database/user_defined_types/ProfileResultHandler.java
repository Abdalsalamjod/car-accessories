package application.database.user_defined_types;

import application.entities.Profile;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.database.QueryResultHandler;




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
