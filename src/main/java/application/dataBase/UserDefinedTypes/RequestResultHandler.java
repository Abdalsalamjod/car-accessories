package application.dataBase.UserDefinedTypes;

import application.dataBase.QueryResultHandler;
import application.entities.Product;
import application.entities.Request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

public class RequestResultHandler implements QueryResultHandler<Request> {
    @Override
    public Request handle(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Request request =new Request();
            request.setDone(resultSet.getBoolean("done"));
//          request.setDate(resultSet.getDate("date").toLocalDate());
            request.setDescription(resultSet.getString("description"));
            request.setId(resultSet.getInt("id"));
            request.setUserId(resultSet.getString("userId"));
            request.setProductId(resultSet.getInt("productId"));
            return request;
        }
        return null;
    }
}
