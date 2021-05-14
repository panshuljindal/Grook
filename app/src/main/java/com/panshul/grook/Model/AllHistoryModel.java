package com.panshul.grook.Model;

public class AllHistoryModel {

    String name,slot,sport,date,bid;


    public AllHistoryModel(){

    }

    public AllHistoryModel(String name, String slot, String sport, String date,String bid) {
        this.name = name;
        this.slot = slot;
        this.sport = sport;
        this.date = date;
        this.bid = bid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
