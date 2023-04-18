package com.example.movie_app.Model;

public class Episode {
    private String nameMovie;
    private String imageEpisode;
    private String episodeTitle;
    private String playMovie;
    private boolean isCheck;

    public Episode(String nameMovie, String imageEpisode, String episodeTitle, String playMovie) {
        this.nameMovie = nameMovie;
        this.imageEpisode = imageEpisode;
        this.episodeTitle = episodeTitle;
        this.playMovie = playMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public String getPlayMovie() {
        return playMovie;
    }

    public void setPlayMovie(String playMovie) {
        this.playMovie = playMovie;
    }

    public String getImageEpisode() {
        return imageEpisode;
    }

    public void setImageEpisode(String imageEpisode) {
        this.imageEpisode = imageEpisode;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
