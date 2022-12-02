package com.example.myproject;

public class User {
   private String id,name,profailimagepath,phoneNum;

    public User(String id, String name, String profailimagepath, String phoneNum) {
        this.id = id;
        this.name = name;
        this.profailimagepath = profailimagepath;
        this.phoneNum = phoneNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfailimagepath() {
        return profailimagepath;
    }

    public void setProfailimagepath(String profailimagepath) {
        this.profailimagepath = profailimagepath;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
