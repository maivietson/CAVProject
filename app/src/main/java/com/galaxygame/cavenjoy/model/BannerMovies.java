package com.galaxygame.cavenjoy.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BannerMovies {

    private String id;
    private String movieName;
    private String imageUrl;
    private String fileUrl;
    private String description;

    public BannerMovies() {
    }

    public BannerMovies(String id, String movieName, String imageUrl, String fileUrl, String description) {
        this.id = id;
        this.movieName = movieName;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
