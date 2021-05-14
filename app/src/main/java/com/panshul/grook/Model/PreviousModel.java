package com.panshul.grook.Model;

public class PreviousModel {
    String name;
    String sport;
    String date;

    public  PreviousModel(){

    }

    public PreviousModel(String name, String sport, String date) {
        this.name = name;
        this.sport = sport;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
