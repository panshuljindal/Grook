package com.panshul.grook.Model;

public class SportModel {
    String sid;
    String sname;
    String sprice;
    String isPresent;
    public SportModel(){

    }

    public SportModel(String sid, String sname, String sprice, String isPresent) {
        this.sid = sid;
        this.sname = sname;
        this.sprice = sprice;
        this.isPresent = isPresent;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }
}
