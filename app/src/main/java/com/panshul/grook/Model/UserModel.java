package com.panshul.grook.Model;

public class UserModel {
    String uid;
    String city;
    String phone;
    String name;
    String email;
    boolean isPremium;

    public UserModel(){

    }

    public UserModel(String uid, String city, String phone, String name, String email,boolean isPremium) {
        this.uid = uid;
        this.city = city;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.isPremium=isPremium;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
