package com.example.movie_app.Model;

public class CastCrew {
    private String Cimage;
    private String Cname;

    public CastCrew(String imageUrl, String name) {
        this.Cimage = imageUrl;
        this.Cname = name;
    }

    public String getCimage() {
        return Cimage;
    }

    public void setCimage(String cimage) {
        this.Cimage = cimage;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        this.Cname = cname;
    }
}
