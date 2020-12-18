package com.galaxygame.cavenjoy.model;

public class VideoItem {

    private String id;
    private String movieName;
    private String imageUrl;
    private String fileUrl;
    private String description;
    private String date;
    private Integer view;
    private String hasAds;

    public VideoItem() {
    }

    public VideoItem(String id, String movieName, String imageUrl, String fileUrl, String description, String date, Integer view, String hasAds) {
        this.id = id;
        this.movieName = movieName;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
        this.description = description;
        this.date = date;
        this.view = view;
        this.hasAds = hasAds;
    }

    public String getHasAds() {
        return hasAds;
    }

    public void setHasAds(String hasAds) {
        this.hasAds = hasAds;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }
}
