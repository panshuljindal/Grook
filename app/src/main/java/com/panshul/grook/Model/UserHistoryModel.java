package com.panshul.grook.Model;

public class UserHistoryModel {
    String gname,sport,price,date,slot,bid,gid,address,gpic,gpic2;

    public UserHistoryModel(){

    }

    public UserHistoryModel(String gname, String sport, String price, String date,String gpic2, String slot, String bid, String gid, String address, String gpic) {
        this.gname = gname;
        this.sport = sport;
        this.price = price;
        this.date = date;
        this.slot = slot;
        this.bid = bid;
        this.gid = gid;
        this.address = address;
        this.gpic = gpic;
        this.gpic2=gpic2;
    }

    public String getGpic2() {
        return gpic2;
    }

    public void setGpic2(String gpic2) {
        this.gpic2 = gpic2;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGpic() {
        return gpic;
    }

    public void setGpic(String gpic) {
        this.gpic = gpic;
    }
}
