package com.schickenon.cavproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.schickenon.cavproject.MovieDetails;
import com.schickenon.cavproject.R;
import com.schickenon.cavproject.WebViewActivity;
import com.schickenon.cavproject.model.CategoryItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainListChapterMovieAdapter extends RecyclerView.Adapter<MainListChapterMovieAdapter.MainViewHolder> {

    Context context;
    List<CategoryItem> categoryItemList;

    public MainListChapterMovieAdapter() {
    }

    public MainListChapterMovieAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainListChapterMovieAdapter.MainViewHolder(LayoutInflater.from(context).inflate(R.layout.chapter_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Glide.with(context).load(categoryItemList.get(position).getImageUrl()).into(holder.imageViewMovie);
        String chapterName = "Tập " + Integer.toString(position + 1);
        holder.chapterMovie.setText(chapterName);

        holder.chapterMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, WebViewActivity.class);
                i.putExtra("url", categoryItemList.get(position).getFileUrl());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewMovie;
        TextView chapterMovie;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMovie = itemView.findViewById(R.id.item_image_movie);
            chapterMovie = itemView.findViewById(R.id.chapterMovie);
        }
    }
}
