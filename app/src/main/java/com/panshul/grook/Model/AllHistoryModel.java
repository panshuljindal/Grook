package com.panshul.grook.Model;

public class AllHistoryModel {

    String gpic,gname,gaddress,gtiming,glocsed,gsport,bid,gid,sport,name,date,slot;


    public AllHistoryModel(){

    }

    public AllHistoryModel(String gpic, String gname, String gaddress, String gtiming, String glocsed, String gsport, String bid, String gid, String sport, String name, String date,String slot) {
        this.gpic = gpic;
        this.gname = gname;
        this.gaddress = gaddress;
        this.gtiming = gtiming;
        this.glocsed = glocsed;
        this.gsport = gsport;
        this.bid = bid;
        this.gid = gid;
        this.sport = sport;
        this.name = name;
        this.date = date;
        this.slot = slot;
    }

    public String getGpic() {
        return gpic;
    }

    public void setGpic(String gpic) {
        this.gpic = gpic;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGaddress() {
        return gaddress;
    }

    public void setGaddress(String gaddress) {
        this.gaddress = gaddress;
    }

    public String getGtiming() {
        return gtiming;
    }

    public void setGtiming(String gtiming) {
        this.gtiming = gtiming;
    }

    public String getGlocsed() {
        return glocsed;
    }

    public void setGlocsed(String glocsed) {
        this.glocsed = glocsed;
    }

    public String getGsport() {
        return gsport;
    }

    public void setGsport(String gsport) {
        this.gsport = gsport;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
