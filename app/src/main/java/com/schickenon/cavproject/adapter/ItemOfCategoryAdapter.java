package com.schickenon.cavproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.schickenon.cavproject.MovieDetails;
import com.schickenon.cavproject.R;
import com.schickenon.cavproject.model.VideoItem;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOfCategoryAdapter extends RecyclerView.Adapter<ItemOfCategoryAdapter.MainViewHolder> {

    Context context;
    List<VideoItem> allVideoItemList;

    public ItemOfCategoryAdapter(Context context, List<VideoItem> allVideoItemList) {
        this.context = context;
        this.allVideoItemList = allVideoItemList;
    }

    @NonNull
    @Override
    public ItemOfCategoryAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemOfCategoryAdapter.MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_list_video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOfCategoryAdapter.MainViewHolder holder, int position) {
        holder.name_movie.setText(allVideoItemList.get(position).getMovieName());
        String infoMovies = Integer.toString(allVideoItemList.get(position).getView()) + " views - " + allVideoItemList.get(position).getDate();
        holder.info_movie.setText(infoMovies);
        holder.description_movie.setText(allVideoItemList.get(position).getDescription());
        Glide.with(context).load(allVideoItemList.get(position).getImageUrl()).into(holder.imageViewVideo);

        String random = "avatar" + Integer.toString(new Random().nextInt(14) + 1);
        int id = context.getResources().getIdentifier(random, "mipmap", context.getPackageName());
        holder.imageViewAvatar.setImageResource(id);

        holder.imageViewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MovieDetails.class);
                i.putExtra("MovieId", allVideoItemList.get(position).getId());
                i.putExtra("movieName", allVideoItemList.get(position).getMovieName());
                i.putExtra("imageUrl", allVideoItemList.get(position).getImageUrl());
                i.putExtra("fileUrl", allVideoItemList.get(position).getFileUrl());
                i.putExtra("description", allVideoItemList.get(position).getDescription());
                i.putExtra("hasAds", allVideoItemList.get(position).getHasAds());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allVideoItemList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewVideo;
        ImageView imageViewAvatar;
        TextView name_movie;
        TextView info_movie;
        TextView description_movie;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewVideo = itemView.findViewById(R.id.image_movie_item);
            imageViewAvatar = itemView.findViewById(R.id.image_avatar);
            name_movie = itemView.findViewById(R.id.name_video_item);
            info_movie = itemView.findViewById(R.id.info_movie);
            description_movie = itemView.findViewById(R.id.description_movie);
        }
    }

}
