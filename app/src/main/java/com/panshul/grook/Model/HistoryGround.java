package com.panshul.grook.Model;

public class HistoryGround {
    String date,gid,sname,time;

    public HistoryGround(){

    }

    public HistoryGround(String date, String gid, String sname, String time) {
        this.date = date;
        this.gid = gid;
        this.sname = sname;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
