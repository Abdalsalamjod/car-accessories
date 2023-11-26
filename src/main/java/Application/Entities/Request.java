package Application.Entities;

import java.util.Date;

public class Request {

  private Product product;
  private User user;
  private Date date;
  private String description;


  public Request(){
    this.product = new Product();
    this.date = new Date();
    this.description = "";
    this.user = new User();
  }
  public Request(Product product, User user, Date date, String description){
    this.product = product;
    this.user = user;
    this.date = date;
    this.description = description;
  }

  public void setProduct( Product product ) {
    this.product = product;
  }
  public void setUser( User user ) {
    this.user = user;
  }
  public void setDate( Date date ) {
    this.date = date;
  }
  public void setDescription( String description ) {
    this.description = description;
  }



  public Product getProduct() {
    return product;
  }
  public User getUser() {
    return user;
  }
  public Date getDate() {
    return date;
  }
  public String getDescription() {
    return description;
  }


}
