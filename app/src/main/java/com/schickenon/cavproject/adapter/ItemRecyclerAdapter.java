package com.schickenon.cavproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.schickenon.cavproject.R;
import com.schickenon.cavproject.model.CategoryItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {

    Context context;
    List<CategoryItem> categoryItemList;

    public ItemRecyclerAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(categoryItemList.get(position).getImageUrl()).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_image);
        }
    }
}
