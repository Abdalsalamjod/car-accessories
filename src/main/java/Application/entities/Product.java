package Application.entities;

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
    else if (!("Interior".equals(this.category) || "Exterior".equals(this.category) || "Electronic".equals(this.category)))
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
}
