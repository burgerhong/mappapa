package com.example.jason.mappapa;

public class locationDetails {
  //  private int id;
    private String user;
    private String pickUpTime;
    private String slotID;
 //   private double rating;
 //   private double price;


    public locationDetails() {
    }

    public locationDetails(/*int id,*/ String user, String pickUpTime ,String slotID/*, double rating, double price*/) {
      //  this.id = id;
       this.user = user;
        this.pickUpTime = pickUpTime;
        this.slotID = slotID;
     //   this.rating = rating;
     //   this.price = price;
    }


    public String getUser() {
        return user;
    }


    public String getPickUpTime() {
        return pickUpTime;
    }

    public String getSlotID(){return slotID;}

}