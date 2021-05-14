package com.panshul.grook.Model;

public class UserModel {
    String uid;
    String city;
    String phone;
    String name;
    String email;

    public UserModel(){

    }

    public UserModel(String uid, String city, String phone, String name, String email) {
        this.uid = uid;
        this.city = city;
        this.phone = phone;
        this.name = name;
        this.email = email;
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
