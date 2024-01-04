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


  public <T> T executeQuery(String query, QueryResultHandler<T> resultHandler) throws SQLException {

      return resultHandler.handle(connection.prepareStatement(query).executeQuery());

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
      boolean wasAccessible = field.trySetAccessible();
      try {
        statement.setObject(i + 1, field.get(object).toString());
      } finally {
        if (wasAccessible) {
          field.setAccessible(false);
        }
      }
    }
  }



  public boolean deleteObject(int id, String tableName) throws SQLException {
    if (!isValidTableName(tableName)) {
      throw new IllegalArgumentException("Invalid table name");
    }

    String deleteQuery = buildDeleteQuery(tableName);
    try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
      statement.setInt(1, id);
      statement.executeUpdate();

      return true;
    }
  }


  private String buildDeleteQuery(String tableName) {
    return "DELETE FROM " + tableName + " WHERE id=?";
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
      if (!fields[i].getName().equals(primaryKeyField)) {
        setParameter(statement, fields[i], object, paramIndex);
        paramIndex++;
      }
    }

    Field primaryKey = object.getClass().getDeclaredField(primaryKeyField);

    boolean wasAccessible = primaryKey.trySetAccessible();
    try {
      statement.setObject(paramIndex, primaryKey.get(object));
    } finally {
      if (wasAccessible) {
        primaryKey.setAccessible(false);
      }
    }
  }


  private void setParameter(PreparedStatement statement, Field field, Object object, int paramIndex)
    throws IllegalAccessException, SQLException {
    boolean wasAccessible = field.trySetAccessible();

    try {
      if (field.getName().equals(LITERAL_FOR_PROFILE) || field.getName().equals("role")) {
        statement.setObject(paramIndex, field.get(object).toString());
      } else {
        statement.setObject(paramIndex, field.get(object));
      }
    } finally {
      if (wasAccessible) {
        field.setAccessible(false);
      }
    }
  }



}
