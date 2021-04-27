package com.panshul.grook.Model;

public class HistoryGroundModel {
    String himage,hname,haddress,hsport,htimeslot,hrating;

    public HistoryGroundModel(String himage, String hname, String haddress, String hsport, String htimeslot, String hrating) {
        this.himage = himage;
        this.hname = hname;
        this.haddress = haddress;
        this.hsport = hsport;
        this.htimeslot = htimeslot;
        this.hrating = hrating;
    }

    public String getHimage() {
        return himage;
    }

    public void setHimage(String himage) {
        this.himage = himage;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getHaddress() {
        return haddress;
    }

    public void setHaddress(String haddress) {
        this.haddress = haddress;
    }

    public String getHsport() {
        return hsport;
    }

    public void setHsport(String hsport) {
        this.hsport = hsport;
    }

    public String getHtimeslot() {
        return htimeslot;
    }

    public void setHtimeslot(String htimeslot) {
        this.htimeslot = htimeslot;
    }

    public String getHrating() {
        return hrating;
    }

    public void setHrating(String hrating) {
        this.hrating = hrating;
    }
}
