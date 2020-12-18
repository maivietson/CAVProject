package com.galaxygame.cavenjoy.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class AllCategory {

    private String categoryId;
    private String categoryTitle;
    private List<CategoryItem> categoryItemList = null;

    public AllCategory() {
    }

    public AllCategory(String categoryId, String categoryTitle) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
    }

    public AllCategory(String categoryId, String categoryTitle, List<CategoryItem> categoryItemList) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryItemList = categoryItemList;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }
}
