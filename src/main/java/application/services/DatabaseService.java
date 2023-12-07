package application.services;
import application.entities.Product;
import application.dataBase.Premetive_Objects.IntResultHandler;
import application.dataBase.Premetive_Objects.ResultSetResultHandler;
import application.dataBase.Premetive_Objects.StringResultHandler;
import application.dataBase.QueryResultHandler;
import application.dataBase.UserDefinedTypes.ProductResultHandler;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;




public class DatabaseService implements Serializable {

  private final String LITERAL_FOR_PROFILE = "profile";
  private static Connection connection;
  private static final String DATABASE_NAME_AND_USER  = "sql12654012";
  private static final String DATABASE_PASSWORD = "wzRUn4Cfmj";


  //construct and establish the connection
  public DatabaseService(){
    try{
      connection = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12654012", DATABASE_NAME_AND_USER , DATABASE_PASSWORD);
      System.out.println("\nConnected to the database!\n");
    } catch ( SQLException e) {
        System.out.println("\nNot Connected to the database!, try again plz\n");
    }

  }

  //close the connection
  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
        System.out.println("\nConnection closed successfully.\n");
      } catch ( SQLException e ) {
        System.out.println("\nSomething went wrong, Connection not closed successfully.\n");
      }
    }
  }

  //do the query (SELECT with all possible scenarios) and return the result
  public <T> T executeQuery(String query, QueryResultHandler<T> resultHandler) throws SQLException{

    ResultSet resultSet;
    PreparedStatement statement = null;
   try {
     statement = connection.prepareStatement(query);
   }
   finally {
       assert statement != null;
       statement.close();
   }
    resultSet = statement.executeQuery();


    return resultHandler.handle(resultSet);

  }


  //add new object to the database
  public  <T> boolean addObject(T object, String tableName) throws SQLException{

    StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");

    //get the fields names (class must have getters)
    Field[] fields = object.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      if(fields[i].getName().equals(LITERAL_FOR_PROFILE))
          insertQuery.append("profileId");
        else
          insertQuery.append(fields[i].getName());
      if (i < fields.length - 1) {
        insertQuery.append(", ");
      }
    }


    //add ? in the VALUES (?, ?, ?) -> PreparedStatement
    insertQuery.append(") VALUES (");
    for (int i = 0; i < fields.length; i++) {
      insertQuery.append("?");
      if (i < fields.length - 1) {
        insertQuery.append(", ");
      }
    }
    insertQuery.append(")");

    //replace ? with actual values
    PreparedStatement statement = null;
    try  {
      statement = connection.prepareStatement(insertQuery.toString());
    }finally {
        assert statement != null;
        statement.close();
    }


      for (int i = 0; i < fields.length; i++) {
        fields[i].setAccessible(true);
        try {
          if (fields[i].toString().equals("public application.entities.Profile application.entities.User.profile"))
            statement.setObject(i + 1, fields[i].get(object).toString());
          else
            statement.setObject(i + 1, fields[i].get(object));
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }

      //execute updates
      statement.executeUpdate();
      statement.close();
    }
    return true;
  }

  //delete object based on id (static -> call it from class name)
  public boolean deleteObject(int id, String tableName) throws SQLException{


    String deleteQuery = "DELETE FROM " + tableName + " WHERE id = ?";
    PreparedStatement statement = null;
    try  {
      statement = connection.prepareStatement(deleteQuery);
      statement.setInt(1, id);
      statement.executeUpdate();

    }
    finally {
      if (statement != null) {
        statement.close();
      }
    }
    return true;

  }


  //update object base on id (static -> call it from class name)
  /*(object -> new instance with type of the object you need to update,
    set id to be equal to the id in the record in the database you need to update)
    (tableName -> table name in the database which contains the record you want to update)
    (primaryKeyField -> the name of the primary key in the table contains the record you want to update*/
  public  <T> void updateObject(T object, String tableName, String primaryKeyField) throws Exception {



    StringBuilder updateQuery = new StringBuilder("UPDATE " + tableName + " SET ");

    //get the fields names (class must have getters)
    Field[] fields = object.getClass().getDeclaredFields();

    for (int i = 0; i < fields.length; i++) {
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

    PreparedStatement statement = null;
    try  {
      statement = connection.prepareStatement(updateQuery.toString());
    }finally {
        assert statement != null;
        statement.close();
    }
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


      Field primaryKey = object.getClass().getDeclaredField(primaryKeyField);
      primaryKey.setAccessible(true);
      statement.setObject(paramIndex, primaryKey.get(object));

      statement.executeUpdate();
      statement.close();
    }


  }



  public static void main(String[] args) throws Exception {


    DatabaseService dbs = new DatabaseService();

    //if you need to return int
    int id = dbs.executeQuery("SELECT id FROM Product WHERE name='Spoiler'", new IntResultHandler());
    System.out.println(id);

    //if you need to return string
    String name = dbs.executeQuery("SELECT name FROM Product WHERE id=3", new StringResultHandler());
    System.out.println(name);

    //if you need to return ResultSet
    ResultSet rs = dbs.executeQuery("SELECT * FROM Product WHERE id=1", new ResultSetResultHandler());
    while ( rs.next() )
      System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDouble(4) + " " + rs.getInt(5));

    //if you need to return user-defined object and put it in new object
    Product product = dbs.executeQuery("SELECT * FROM Product WHERE id=2", new ProductResultHandler());
    System.out.println(product.getId() + " " + product.getName() + " " + product.getPrice() + " " + product.getCategory() + " " + product.getQuantity());


    //add new object
    Product productToBeAdded = new Product(4, "new", "new", 20.0, 20);
    dbs.addObject(productToBeAdded, "Product");

    //delete an object
    dbs.deleteObject(4, "Product");

    //update an object
    Product p = new Product(1, "Spoiler", "exterior", 20.0, 5);
    dbs.updateObject(p, "Product", "id");

    dbs.closeConnection();

  }

}