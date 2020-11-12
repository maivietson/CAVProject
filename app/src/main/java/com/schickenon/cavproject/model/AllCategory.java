package com.schickenon.cavproject.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;

@IgnoreExtraProperties
public class AllCategory {

    private Integer categoryId;
    private String categoryTitle;
    private String categoryRef;
    private List<CategoryItem> categoryItemList = null;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public AllCategory() {
    }

    public AllCategory(Integer categoryId, String categoryTitle, String categoryRef) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryRef = categoryRef;

        firebaseDatabase.getReference(categoryRef).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryItemList.clear();
                int child = snapshot.getChildrenCount() < 10 ? (int) snapshot.getChildrenCount() : 10;
                for(DataSnapshot)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getCategoryRef() {
        return categoryRef;
    }

    public void setCategoryRef(String categoryRef) {
        this.categoryRef = categoryRef;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
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
