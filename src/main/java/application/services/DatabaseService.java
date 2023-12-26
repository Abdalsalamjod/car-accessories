package application.services;

import application.dataBase.QueryResultHandler;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Objects;
import org.h2.security.SHA256;


public class DatabaseService implements Serializable {

  private final String LITERAL_FOR_PROFILE = "profile";
  private static Connection connection;


  public DatabaseService() {
    String databaseUser = System.getenv("DATABASE_USER");
    String databasePassword = System.getenv("DATABASE_PASSWORD");

    try {
      connection = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12654012", databaseUser, databasePassword);
    } catch (SQLException e) {
        e.printStackTrace(); // This will print the stack trace and the SQL error code.
        System.out.println("\nNot Connected to the database! Please try again.\n");
  }
  }

  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch ( SQLException e ) {
        System.out.println("\nSomething went wrong, Connection not closed successfully.\n");
      }
    }
  }

  public <T> T executeQuery(String query, QueryResultHandler<T> resultHandler) throws SQLException{

    ResultSet resultSet;
    PreparedStatement statement ;

    statement = connection.prepareStatement(query);
    resultSet = statement.executeQuery();
    return resultHandler.handle(resultSet);

  }


  public  <T> boolean addObject(T object, String tableName) throws SQLException{

    StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");

    //get the fields names (class must have getters)
    Field[] fields = object.getClass().getDeclaredFields();
    int fieldsLength = (Objects.equals(tableName, "Request")) ? fields.length - 1 : fields.length;
    for (int i = 0; i < fieldsLength; i++) {
      if(fields[i].getName().equals(LITERAL_FOR_PROFILE))
        insertQuery.append("profileId");
      else
        insertQuery.append(fields[i].getName());
      if (i < fieldsLength - 1) {
        insertQuery.append(", ");
      }
    }


    //add ? in the VALUES (?, ?, ?) -> PreparedStatement
    insertQuery.append(") VALUES (");
    for (int i = 0; i < fieldsLength; i++) {
      insertQuery.append("?");
      if (i < fieldsLength - 1) {
        insertQuery.append(", ");
      }
    }
    insertQuery.append(")");

    //replace ? with actual values
    PreparedStatement statement = connection.prepareStatement(insertQuery.toString());
    for (int i = 0; i < fieldsLength; i++) {
      fields[i].setAccessible(true);
      try {
        if(fields[i].toString().equals("public Application.Entities.Profile Application.Entities.User.profile"))
          statement.setObject(i + 1, fields[i].get(object).toString());
        else
          statement.setObject(i + 1, fields[i].get(object).toString());
      }catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    //execute updates
    statement.executeUpdate();
    statement.close();
    return true;

  }


  public boolean deleteObject(int id, String tableName) throws SQLException{


    String deleteQuery = "DELETE FROM " + tableName + " WHERE id = ?";
    try ( PreparedStatement statement = connection.prepareStatement(deleteQuery) ) {
      statement.setInt(1, id);
      statement.executeUpdate();
      return true;
    }
  }

  public  <T> void updateObject(T object, String tableName, String primaryKeyField) throws Exception {



    StringBuilder updateQuery = new StringBuilder("UPDATE " + tableName + " SET ");

    //get the fields names (class must have getters)
    Field[] fields = object.getClass().getDeclaredFields();
    int fieldsLength = ( Objects.equals(tableName, "Request") ) ? fields.length-1: fields.length;
    for (int i = 0; i < fieldsLength; i++) {
      if (!fields[i].getName().equals(primaryKeyField)) {
        if(fields[i].getName().equals(LITERAL_FOR_PROFILE))
          updateQuery.append("profileId").append(" = ?");
        else
          updateQuery.append(fields[i].getName()).append(" = ?");
        if (i < fields.length - 1) {
          updateQuery.append(", ");
        }
      }
    }
    updateQuery.append(" WHERE ").append(primaryKeyField).append(" = ?");

    PreparedStatement statement ;

      statement = connection.prepareStatement(updateQuery.toString());


      int paramIndex = 1;
      for (Field field : fields) {
        if (!field.getName().equals(primaryKeyField)) {
          field.setAccessible(true);
          if (field.getName().equals(LITERAL_FOR_PROFILE) || field.getName().equals("role"))
            statement.setObject(paramIndex, field.get(object).toString());
          else
            statement.setObject(paramIndex, field.get(object));
          paramIndex++;
        }
      }


      Field primaryKey = object.getClass().getDeclaredField(primaryKeyField);
      primaryKey.setAccessible(true);
      statement.setObject(paramIndex, primaryKey.get(object));

      statement.executeUpdate();
      statement.close();
    }

}