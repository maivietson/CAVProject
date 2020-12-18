package com.galaxygame.cavenjoy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxygame.cavenjoy.R;
import com.galaxygame.cavenjoy.model.VideoItem;

import java.util.List;

public class MainListCategoryAdapter extends RecyclerView.Adapter<MainListCategoryAdapter.MainViewHolder> {

    Context context;
    List<VideoItem> allVideoItemList;

    public MainListCategoryAdapter(Context context, List<VideoItem> allVideoItemList) {
        this.context = context;
        this.allVideoItemList = allVideoItemList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainListCategoryAdapter.MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.categoryName.setText("List all videos");
        holder.allVideo.setText(Integer.toString(allVideoItemList.size()) + " videos");
        setItemRecycler(holder.itemRecycler, allVideoItemList);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        TextView allVideo;
        RecyclerView itemRecycler;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.item_category);
            allVideo = itemView.findViewById(R.id.all_video);
            itemRecycler = itemView.findViewById(R.id.item_recycler);
        }
    }

    private void setItemRecycler(RecyclerView recyclerView, List<VideoItem> allVideoItemList) {
        ItemOfCategoryAdapter itemRecyclerAdapter = new ItemOfCategoryAdapter(context, allVideoItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }
}
