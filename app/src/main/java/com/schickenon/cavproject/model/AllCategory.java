package com.schickenon.cavproject.model;

public class AllCategory {

    private String categoryTitle;
    private Integer categoryId;

    public AllCategory(String categoryTitle, Integer categoryId) {
        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
