package com.panshul.grook.Model;

public class BookingModel {
    String bid;
    String gid;
    String sport;
    String slot;
    String date;
    String price;
    public BookingModel(){

    }

    public BookingModel(String bid, String gid, String sport, String slot, String date, String price) {
        this.bid = bid;
        this.gid = gid;
        this.sport = sport;
        this.slot = slot;
        this.date = date;
        this.price = price;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
