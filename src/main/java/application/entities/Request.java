package application.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Request {

  private int id;
  private int productId;
  private String userId;
  private LocalDateTime date;
  private String description;
  private static ArrayList<String> datesArray = new ArrayList<>();

  public Request(){
    this.date = null;
    this.description = "";
    this.userId = "";
    this.productId = -1;
    this.id = -1;
  }
  public Request(int id, int productId, String userId, LocalDateTime date, String description){
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
  public LocalDateTime getDate() {
    return date;
  }
  public void setDate( LocalDateTime date ) {
    this.date = date;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription( String description ) {
    this.description = description;
  }

  public static List<String> getDatesArray(){
    return Request.datesArray;
  }
  public static void setDatesArray(ArrayList<String> datesArray){
    Request.datesArray = datesArray;
  }
  public static void initializeDatesArray(){

    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime newDateTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime;

    for(int i=0; i<5; i++){
      newDateTime = currentDateTime.plusDays((i + (long) 1));

      if(i%2 == 0)
        newDateTime = newDateTime.plusHours(i);
      else
        newDateTime = newDateTime.minusHours(i);

      formattedDateTime = newDateTime.format(formatter);
      datesArray.add(i, formattedDateTime);
    }

  }


}
