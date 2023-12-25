package application.dataBase.UserDefinedTypes;

import application.dataBase.QueryResultHandler;
import application.entities.Product;
import application.entities.Request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RequestResultHandler implements QueryResultHandler<Request> {
    @Override
    public Request handle(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


//            request.setDone(resultSet.getInt("done"));




//            request.setDate(LocalDateTime.paresultSete(resultSet.getString(4), formatter));
//            request.setDescription(resultSet.getString("description"));
//            request.setId(resultSet.getInt("id"));
//            request.setUserId(resultSet.getString("userId"));
//            request.setProductId(resultSet.getInt("productId"));
            Request request = new Request(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), LocalDateTime.parse(resultSet.getString(4), formatter), resultSet.getString(5));
            return request;
        }
        return null;
    }
}
