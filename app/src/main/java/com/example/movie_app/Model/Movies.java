package com.example.movie_app.Model;

public class Movies {
    private String movieName;
    private String movieImage;
    private String movieDescription;
    private String movieCateId;
    private String movieTrailer;
    private String movieId;
    public Movies(){}

    public Movies(String movieName, String movieImage, String movieDescription, String movieCateId, String movieTrailer, String movieId) {
        this.movieName = movieName;
        this.movieImage = movieImage;
        this.movieDescription = movieDescription;
        this.movieCateId = movieCateId;
        this.movieTrailer = movieTrailer;
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieCateId() {
        return movieCateId;
    }

    public void setMovieCateId(String movieCateId) {
        this.movieCateId = movieCateId;
    }

    public String getMovieTrailer() {
        return movieTrailer;
    }

    public void setMovieTrailer(String movieTrailer) {
        this.movieTrailer = movieTrailer;
    }
}
