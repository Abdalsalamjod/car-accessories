package application.entities;

import application.dataBase.Premetive_Objects.ResultSetResultHandler;
import application.dataBase.UserDefinedTypes.ProductResultHandler;
import application.services.DatabaseService;

import java.sql.ResultSet;

public class Product {

  private Integer id;
  private String name;
  private String category;
  private Double price;
  private Integer quantity;


  //constructors
  public Product(){
    this.id = null;
    this.name = null;
    this.category = null;
    this.price = null;
    this.quantity = null;

  }
  public Product(Integer id, String name, String category, Double price, Integer quantity){
    this.id = id;
    this.name = name;
    this.category = category;
    this.price = price;
    this.quantity = quantity;
  }


  //check if valid information
  public String validInformation(){
    if(this.name.contains("#"))
      return "Invalid Name!";
    else if (!("interior".equals(this.category) || "exterior".equals(this.category) || "electronics".equals(this.category)))
      return "Invalid Category!";
    else if(this.price < 0)
      return "Invalid Price!";
    else if(this.quantity < 0)
      return "Invalid Quantity!";

    return "";
  }


  //getters and setters
  public Integer getId() {
    return id;
  }
  public void setId( Integer id ) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName( String name ) {
    this.name = name;
  }
  public String getCategory() {
    return category;
  }
  public void setCategory( String category ) {
    this.category = category;
  }
  public Double getPrice() {
    return price;
  }
  public void setPrice( Double price ) {
    this.price = price;
  }
  public Integer getQuantity() {
    return quantity;
  }
  public void setQuantity( Integer quantity ) {
    this.quantity = quantity;
  }


  public static ResultSet getAllProducts( DatabaseService dbs ) throws Exception{
    return dbs.executeQuery("SELECT * FROM Product", new ResultSetResultHandler());
  }

  public static ResultSet getAllProductsNames( DatabaseService dbs ) throws Exception{
    return dbs.executeQuery("SELECT id, name FROM Product", new ResultSetResultHandler());
  }
  public static Product getProductById( int id, DatabaseService dbs ) throws Exception{
    return dbs.executeQuery("SELECT * FROM Product WHERE id=" + id, new ProductResultHandler());
  }
  public static Product getProductByName( String name, DatabaseService dbs ) throws Exception{
    return dbs.executeQuery("SELECT * FROM Product WHERE name='" + name + "'", new ProductResultHandler());
  }
  public static ResultSet getProductsByCategory( String category, DatabaseService dbs ) throws Exception{
    return dbs.executeQuery("SELECT * FROM Product WHERE category='" + category + "'", new ResultSetResultHandler());
  }
  public static ResultSet getProductsByPriceRange( double lowerPrice, double upperPrice, DatabaseService dbs ) throws Exception{
    return dbs.executeQuery("SELECT * FROM Product WHERE price BETWEEN " + lowerPrice + " AND " + upperPrice, new ResultSetResultHandler());
  }


  @Override
  public String toString(){
    return  "ID: " + this.id + ", Name: " + this.name + ", Category: " + this.category + ", Price: " + this.price + ", Quantity: " + this.quantity;
  }
}
