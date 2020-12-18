package com.galaxygame.cavenjoy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.galaxygame.cavenjoy.MovieDetails;
import com.galaxygame.cavenjoy.R;
import com.galaxygame.cavenjoy.model.BannerMovies;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class BannerMoviesPagerAdapter extends PagerAdapter {
    
    Context context;
    List<BannerMovies> bannerMoviesList;

    public BannerMoviesPagerAdapter(Context context, List<BannerMovies> bannerMoviesList) {
        this.context = context;
        this.bannerMoviesList = bannerMoviesList;
    }

    @Override
    public int getCount() {
        return bannerMoviesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie_layout, null);
        ImageView bannerImage = view.findViewById(R.id.banner_image);

        //here use glide library
        Glide.with(context).load(bannerMoviesList.get(position).getImageUrl()).into(bannerImage);
        container.addView(view);

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "" + bannerMoviesList.get(position).getMovieName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, MovieDetails.class);
                i.putExtra("MovieId", bannerMoviesList.get(position).getId());
                i.putExtra("movieName", bannerMoviesList.get(position).getMovieName());
                i.putExtra("imageUrl", bannerMoviesList.get(position).getImageUrl());
                i.putExtra("fileUrl", bannerMoviesList.get(position).getFileUrl());
                i.putExtra("description", bannerMoviesList.get(position).getDescription());

                context.startActivity(i);
            }
        });

        return view;
    }
}
