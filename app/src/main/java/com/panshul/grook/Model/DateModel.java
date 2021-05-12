package com.panshul.grook.Model;

public class DateModel {
    String date;
    String isPresent;

    public DateModel(){

    }

    public DateModel(String date, String isPresent) {
        this.date = date;
        this.isPresent = isPresent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }
}

