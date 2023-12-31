package application.services;

import application.database.QueryResultHandler;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.lang.reflect.AccessibleObject;
import static application.services.MessagesGenerator.LOGGER;


public class DatabaseService implements Serializable {

  private static final String LITERAL_FOR_PROFILE = "profile";
  private static final String DATABASE_USER = System.getenv("DATABASE_USER");
  private static final String DATABASE_PASSWORD = System.getenv("DATABASE_PASSWORD");
  private final String LITERAL_FOR_REQUEST_TABLE = "Request";
  private static Connection connection;

  static {
    try {
      connection = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12654012", DATABASE_USER, DATABASE_PASSWORD);
    } catch (SQLException e) {
      LOGGER.info("\nNot Connected to the database! Please try again.\n");
    }
  }


  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch ( SQLException e ) {
        LOGGER.info("\nSomething went wrong, Connection not closed successfully.\n");
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
  public <T> boolean addObject(T object, String tableName) throws SQLException {
    StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");

    Field[] fields = object.getClass().getDeclaredFields();
    int fieldsLength = (Objects.equals(tableName, "Request")) ? fields.length - 1 : fields.length;

    boolean[] fieldAccessible = new boolean[fieldsLength];

    for (int i = 0; i < fieldsLength; i++) {
      if (fields[i].getName().equals(LITERAL_FOR_PROFILE)) {
        insertQuery.append("profileId");
      } else {
        insertQuery.append(fields[i].getName());
      }

      fieldAccessible[i] = fields[i].canAccess(object);

      if (i < fieldsLength - 1) {
        insertQuery.append(", ");
      }
    }

    insertQuery.append(") VALUES (");
    for (int i = 0; i < fieldsLength; i++) {
      insertQuery.append("?");
      if (i < fieldsLength - 1) {
        insertQuery.append(", ");
      }
    }
    insertQuery.append(")");

    try {
      PreparedStatement statement = connection.prepareStatement(insertQuery.toString());
      for (int i = 0; i < fieldsLength; i++) {
        if (!fieldAccessible[i]) {
          fields[i].setAccessible(true);
        }
        statement.setObject(i + 1, fields[i].get(object).toString());
      }

      statement.executeUpdate();
      statement.close();
      return true;

    } catch (Exception e) {
      return false;
    } finally {
      for (int i = 0; i < fieldsLength; i++) {
        if (!fieldAccessible[i]) {
          fields[i].setAccessible(false);
        }
      }
    }
  }



  public boolean deleteObject(int id, String tableName) throws SQLException {
    if (!isValidTableName(tableName)) {
      throw new IllegalArgumentException("Invalid table name");
    }

    String deleteQuery = "DELETE FROM " + tableName + " WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
      statement.setInt(1, id);
      statement.executeUpdate();
      return true;
    }
  }

  private boolean isValidTableName(String tableName) {
    List<String> allowedTableNames = Arrays.asList("Product", "Profile", "Request", "user");
    return allowedTableNames.contains(tableName);
  }


  public <T> void updateObject(T object, String tableName, String primaryKeyField) throws SQLException, NoSuchFieldException, IllegalAccessException {
    String updateQuery = buildUpdateQuery(object, tableName, primaryKeyField);
    try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
      setParameters(statement, object, primaryKeyField);
      statement.executeUpdate();
    }
  }

  private <T> String buildUpdateQuery(T object, String tableName, String primaryKeyField) {
    StringBuilder updateQuery = new StringBuilder("UPDATE " + tableName + " SET ");

    Field[] fields = object.getClass().getDeclaredFields();
    int fieldsLength = (Objects.equals(tableName, LITERAL_FOR_REQUEST_TABLE)) ? fields.length - 1 : fields.length;

    for (int i = 0; i < fieldsLength; i++) {
      if (!fields[i].getName().equals(primaryKeyField)) {
        appendFieldToQuery(updateQuery, fields[i]);
        if (i < fieldsLength - 1) {
          updateQuery.append(", ");
        }
      }
    }
    return updateQuery.append(" WHERE ").append(primaryKeyField).append(" = ?").toString();
  }

  private void appendFieldToQuery(StringBuilder query, Field field) {
    String fieldName = field.getName();
    if (fieldName.equals(LITERAL_FOR_PROFILE)) {
      query.append("profileId = ?");
    } else {
      query.append(fieldName).append(" = ?");
    }
  }

  private <T> void setParameters(PreparedStatement statement, T object, String primaryKeyField) throws IllegalAccessException, NoSuchFieldException, SQLException {
    Field[] fields = object.getClass().getDeclaredFields();
    int fieldsLength = (Objects.equals(LITERAL_FOR_REQUEST_TABLE, primaryKeyField)) ? fields.length - 1 : fields.length;

    int paramIndex = 1;
    for (int i = 0; i < fieldsLength; i++) {
      if (!fields[i].getName().equals(primaryKeyField)) {
        setParameter(statement, fields[i], object, paramIndex);
        paramIndex++;
      }
    }

    Field primaryKey = object.getClass().getDeclaredField(primaryKeyField);

    AccessibleObject.setAccessible(new AccessibleObject[]{primaryKey}, true); // Set accessibility to true

    try {
      statement.setObject(paramIndex, primaryKey.get(object));
    } finally {
      AccessibleObject.setAccessible(new AccessibleObject[]{primaryKey}, false); // Set accessibility back to false
    }
  }



  private void setParameter(PreparedStatement statement, Field field, Object object, int paramIndex) throws IllegalAccessException, SQLException {
    AccessibleObject.setAccessible(new AccessibleObject[]{field}, true); // Set accessibility to true

    try {
      if (field.getName().equals(LITERAL_FOR_PROFILE) || field.getName().equals("role")) {
        statement.setObject(paramIndex, field.get(object).toString());
      } else {
        statement.setObject(paramIndex, field.get(object));
      }
    } finally {
      AccessibleObject.setAccessible(new AccessibleObject[]{field}, false); // Set accessibility back to false
    }
  }




}
