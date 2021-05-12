package com.panshul.grook.Model;

import java.util.List;

public class GroundModel {


    String gaddress;
    String gbookingfees;
    String gcity;
    String gclosed;
    String gid;
    String glocality;
    String gname;
    String gpic;
    String gpic2;
    String gtiming;
    String gsport;
    public GroundModel() {

    }

    public GroundModel(String gid, String gpic2,String gaddress, String gbookingfees, String gcity, String gclosed, String glocality, String gname, String gpic, String gtiming, String gsport) {
        this.gid = gid;
        this.gaddress = gaddress;
        this.gbookingfees = gbookingfees;
        this.gcity = gcity;
        this.gclosed = gclosed;
        this.glocality = glocality;
        this.gname = gname;
        this.gpic = gpic;
        this.gpic2=gpic2;
        this.gtiming = gtiming;
        this.gsport = gsport;
    }

    public String getGpic2() {
        return gpic2;
    }

    public void setGpic2(String gpic2) {
        this.gpic2 = gpic2;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGaddress() {
        return gaddress;
    }

    public void setGaddress(String gaddress) {
        this.gaddress = gaddress;
    }

    public String getGbookingfees() {
        return gbookingfees;
    }

    public void setGbookingfees(String gbookingfees) {
        this.gbookingfees = gbookingfees;
    }

    public String getGcity() {
        return gcity;
    }

    public void setGcity(String gcity) {
        this.gcity = gcity;
    }

    public String getGclosed() {
        return gclosed;
    }

    public void setGclosed(String gclosed) {
        this.gclosed = gclosed;
    }

    public String getGlocality() {
        return glocality;
    }

    public void setGlocality(String glocality) {
        this.glocality = glocality;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGpic() {
        return gpic;
    }

    public void setGpic(String gpic) {
        this.gpic = gpic;
    }

    public String getGtiming() {
        return gtiming;
    }

    public void setGtiming(String gtiming) {
        this.gtiming = gtiming;
    }

    public String getGsport() {
        return gsport;
    }

    public void setGsport(String gsport) {
        this.gsport = gsport;
    }
}
