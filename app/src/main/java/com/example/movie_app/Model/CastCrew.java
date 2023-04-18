package com.example.movie_app.Model;

public class CastCrew {
    private String castName;
    private String castUrl;

    public CastCrew(){}

    public CastCrew(String castName, String castUrl) {
        this.castName = castName;
        this.castUrl = castUrl;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getCastUrl() {
        return castUrl;
    }

    public void setCastUrl(String castUrl) {
        this.castUrl = castUrl;
    }
}
