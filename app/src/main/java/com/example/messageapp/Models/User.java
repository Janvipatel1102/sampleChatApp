package com.example.messageapp.Models;

public class User {
    private String uid,uname,phoneNumber,profileImage;
    //Working with FireBase we need a empty Constructer;


    public User() {
    }

    public User(String uid, String uname, String phoneNumber, String profileImage) {
        this.uid = uid;
        this.uname = uname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
