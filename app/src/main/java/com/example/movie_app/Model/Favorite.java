package com.example.movie_app.Model;

public class Favorite {
    private String favName;
    private String favImage;
    private String favDescription;
    private String movieId;

    public Favorite(){};
    public Favorite(String favName, String favImage, String favDescription, String movieId) {
        this.favName = favName;
        this.favImage = favImage;
        this.favDescription = favDescription;
        this.movieId = movieId;
    }

    public String getFavName() {
        return favName;
    }

    public void setFavName(String favName) {
        this.favName = favName;
    }

    public String getFavImage() {
        return favImage;
    }

    public void setFavImage(String favImage) {
        this.favImage = favImage;
    }

    public String getFavDescription() {
        return favDescription;
    }

    public void setFavDescription(String favDescription) {
        this.favDescription = favDescription;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
