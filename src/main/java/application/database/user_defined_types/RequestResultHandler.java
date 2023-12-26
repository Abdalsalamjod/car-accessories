package application.database.user_defined_types;

import application.database.QueryResultHandler;
import application.entities.Request;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class RequestResultHandler implements QueryResultHandler<Request> {
    @Override
    public Request handle(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String date = resultSet.getString(4).substring(0, 19);
            return new Request(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), LocalDateTime.parse(date, formatter), resultSet.getString(5));
        }
        return null;
    }
}
