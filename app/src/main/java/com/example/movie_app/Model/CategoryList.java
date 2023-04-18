package com.example.movie_app.Model;

import java.util.List;

public class CategoryList {
    private String nameCategory;
    private List<Movie> categoryMovieList;

    public CategoryList(String nameCategory, List<Movie> imageMovieList) {
        this.nameCategory = nameCategory;
        this.categoryMovieList = imageMovieList;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<Movie> getCategoryMovieList() {
        return categoryMovieList;
    }

    public void setCategoryMovieList(List<Movie> categoryMovieList) {
        this.categoryMovieList = categoryMovieList;
    }
}
