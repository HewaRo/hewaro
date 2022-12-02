package com.example.myproject;

import java.util.ArrayList;

public class Ad {
   private String key, uid,phoneNumber, type,location, description, information, price, date, time;
  private ArrayList<String> imagesPaths;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Ad(String key, String uid, String phoneNumber, String type, String location, String description, String information, String price, String date, String time, ArrayList<String> imagesPaths) {
      this.key = key;
      this.uid = uid;
      this.phoneNumber = phoneNumber;
      this.type = type;
      this.location = location;
      this.description = description;
      this.information = information;
      this.price = price;
      this.date = date;
      this.time = time;
      this.imagesPaths = imagesPaths;
   }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Ad() {
   }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setImagesPaths(ArrayList<String> imagesPaths) {
        this.imagesPaths = imagesPaths;
    }

    public String getKey() {
      return key;
   }

   public String getUid() {
      return uid;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public String getType() {
      return type;
   }

   public String getLocation() {
      return location;
   }

   public String getDescription() {
      return description;
   }

   public String getInformation() {
      return information;
   }

   public ArrayList<String> getImagesPaths() {
      return imagesPaths;
   }
}
