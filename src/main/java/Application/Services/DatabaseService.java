package Application.Services;

import Application.entities.Product;

import java.sql.*;


//create class for generating queries

public class DatabaseService {

  //delete all methods except opencon, clseconn, exequry()

  String databaseNameAndUser = "sql12654012";
  String databaseHost = "sql12.freesqldatabase.com";
  String databasePassword = "wzRUn4Cfmj";
  String portNumber = "3306";
  String url = "jdbc:mysql://" +
    databaseHost + ":" + portNumber + "/" + databaseNameAndUser;





  public Connection OpenConnection(){

    try{

      Connection connection = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12654012", this.databaseNameAndUser, this.databasePassword);
      System.out.println("Connected to the database!");
      return connection;

    } catch ( SQLException e) {
      e.printStackTrace();
      return null;
    }

  }
  //colse connection


  //make it general, add query selector
  public ResultSet returnAllTable(String tableName){
    try{

      Statement statement = this.OpenConnection().createStatement();
      String sqlQuery = "SELECT * FROM `" + tableName + "`";
      return statement.executeQuery(sqlQuery);


    }catch ( SQLException e ){
      e.printStackTrace();
      return null;
    }



  }

  //make it general
  public static boolean addProduct(Product product){
    //Add general entity

    return true;

  }

  public static Product getProductById(Integer id){


    return null;

  }



  public static void main( String[] args ) throws SQLException {
    DatabaseService dbs = new DatabaseService();
    ResultSet rs = dbs.returnAllTable("ShehabTest(remove it when add another)");


    while (rs.next()) {
      // Access columns by name or index
      int id = rs.getInt("id");
      String name = rs.getString("name");
      System.out.println("ID: " + id + ", Name: " + name);
    }

  }


}
