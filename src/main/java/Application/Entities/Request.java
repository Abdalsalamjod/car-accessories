package Application.Entities;

import Application.Services.DatabaseService;

import java.util.Date;

public class Request {

  private int id;
  private int productId;
  private String userId;
  private Date date;
  private String description;

  public Request(){
    this.date = null;
    this.description = "";
    this.userId = "";
    this.productId = -1;
    this.id = -1;
  }
  public Request(int id, int productId, String userId, Date date, String description){
    this.id = id;
    this.productId = productId;
    this.userId = userId;
    this.date = date;
    this.description = description;
  }

  public int getId() {
    return id;
  }
  public void setId( int id ) {
    this.id = id;
  }
  public int getProductId() {
    return productId;
  }
  public void setProductId( int productId ) {
    this.productId = productId;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId( String userId ) {
    this.userId = userId;
  }
  public Date getDate() {
    return date;
  }
  public void setDate( Date date ) {
    this.date = date;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription( String description ) {
    this.description = description;
  }
}
