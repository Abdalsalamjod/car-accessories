package application.services;

import application.database.QueryResultHandler;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static application.services.MessagesGenerator.LOGGER;


public class DatabaseService implements Serializable {

  private static final String LITERAL_FOR_PROFILE = "profile";
  private static final String DATABASE_USER = System.getenv("DATABASE_USER");
  private static final String DATABASE_PASSWORD = System.getenv("DATABASE_PASSWORD");
  private static final String LITERAL_FOR_REQUEST_TABLE = "Request";
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
    String[] columnNames = getColumnNames(object, tableName);
    String insertQuery = buildInsertQuery(tableName, columnNames);

    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
      setStatementParameters(statement, object, columnNames);
      statement.executeUpdate();
      return true;
    } catch ( SQLException | IllegalAccessException e) {
      return false;
    }
  }

  private <T> String[] getColumnNames(T object, String tableName) {
    Field[] fields = object.getClass().getDeclaredFields();
    int fieldsLength = (Objects.equals(tableName, LITERAL_FOR_REQUEST_TABLE)) ? fields.length - 1 : fields.length;

    String[] columnNames = new String[fieldsLength];
    for (int i = 0; i < fieldsLength; i++) {
      columnNames[i] = fields[i].getName().equals(LITERAL_FOR_PROFILE) ? "profileId" : fields[i].getName();
    }
    return columnNames;
  }

  private String buildInsertQuery(String tableName, String[] columnNames) {
    StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");
    for (int i = 0; i < columnNames.length; i++) {
      insertQuery.append(columnNames[i]);
      if (i < columnNames.length - 1) {
        insertQuery.append(", ");
      }
    }
    insertQuery.append(") VALUES (");
    for (int i = 0; i < columnNames.length; i++) {
      insertQuery.append("?");
      if (i < columnNames.length - 1) {
        insertQuery.append(", ");
      }
    }
    insertQuery.append(")");
    return insertQuery.toString();
  }

  private <T> void setStatementParameters(PreparedStatement statement, T object, String[] columnNames)
    throws SQLException, IllegalAccessException {
    Field[] fields = object.getClass().getDeclaredFields();
    for (int i = 0; i < columnNames.length; i++) {
      Field field = fields[i];
      boolean originalAccessible = field.canAccess(object);
      field.setAccessible(true);
      try {
        statement.setObject(i + 1, field.get(object).toString());
      } finally {
        field.setAccessible(originalAccessible);
      }
    }
  }


  public boolean deleteObject(int id, String tableName) throws SQLException {
    if (!isValidTableName(tableName)) {
      throw new IllegalArgumentException("Invalid table name");
    }

    String deleteQuery = "DELETE FROM " + validateAndQuoteTableName(tableName) + " WHERE id = ?";

    try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
      statement.setInt(1, id);
      statement.executeUpdate();
      return true;
    }
  }

  private String validateAndQuoteTableName(String tableName) {
    return "`" + tableName + "`";
  }


  private boolean isValidTableName(String tableName) {
    List<String> allowedTableNames = Arrays.asList("Product", "Profile", LITERAL_FOR_REQUEST_TABLE, "user");
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




    private <T> void setParameters(PreparedStatement statement, T object, String primaryKeyField)
      throws IllegalAccessException, NoSuchFieldException, SQLException {
      Field[] fields = object.getClass().getDeclaredFields();
      int fieldsLength = (Objects.equals(LITERAL_FOR_REQUEST_TABLE, primaryKeyField)) ? fields.length - 1 : fields.length;

      int paramIndex = 1;
      for (int i = 0; i < fieldsLength; i++) {
        Field field = fields[i];
        if (!field.getName().equals(primaryKeyField)) {
          setParameter(statement, field, object, paramIndex);
          paramIndex++;
        }
      }

      Field primaryKey = object.getClass().getDeclaredField(primaryKeyField);
      setParameter(statement, primaryKey, object, paramIndex);
    }

    private <T> void setParameter(PreparedStatement statement, Field field, T object, int paramIndex)
      throws SQLException, IllegalAccessException {
      field.setAccessible(true);
      statement.setObject(paramIndex, field.get(object));
      field.setAccessible(false);
    }
}



