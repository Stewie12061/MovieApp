package com.example.movie_app.Model;

public class ContinueWatching {
    private String Cuid, Cname, Ctrailer, Cfavorite;
    private int CcurentTime, Cduration;

    public ContinueWatching(){}

    public ContinueWatching(String cuid, String cname, String ffavorite, String ctrailer, int ccurentTime, int cduration) {
        Cuid = cuid;
        Cname = cname;
        Cfavorite = ffavorite;
        Ctrailer = ctrailer;
        CcurentTime = ccurentTime;
        Cduration = cduration;
    }

    public String getCuid() {
        return Cuid;
    }

    public void setCuid(String cuid) {
        Cuid = cuid;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getCtrailer() {
        return Ctrailer;
    }

    public void setCtrailer(String Ctrailer) {
        Ctrailer = Ctrailer;
    }

    public int getCcurentTime() {
        return CcurentTime;
    }

    public void setCcurentTime(int ccurentTime) {
        CcurentTime = ccurentTime;
    }

    public int getCduration() {
        return Cduration;
    }

    public void setCduration(int cduration) {
        Cduration = cduration;
    }

    public String getCfavorite() {
        return Cfavorite;
    }

    public void setCfavorite(String ffavorite) {
        Cfavorite = ffavorite;
    }
}
