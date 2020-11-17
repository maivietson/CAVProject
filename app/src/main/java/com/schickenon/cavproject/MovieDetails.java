package com.schickenon.cavproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MovieDetails extends AppCompatActivity {

    ImageView movieImage;
    TextView movieName;
    AppCompatButton playButton;

    String mName, mImage, mId, mFileUrl, mDescription;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieImage = findViewById(R.id.movie_image);
        movieName = findViewById(R.id.movie_name);
        playButton = findViewById(R.id.play_button);

        mAdView = findViewById(R.id.adViewBanner);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mName = getIntent().getStringExtra("movieName");
        mImage = getIntent().getStringExtra("imageUrl");
        mId = getIntent().getStringExtra("MovieId");
        mFileUrl = getIntent().getStringExtra("fileUrl");
        mDescription = getIntent().getStringExtra("description");

        // set data to layout
        Glide.with(MovieDetails.this).load(mImage).into(movieImage);
        movieName.setText(mName);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieDetails.this, VideoPlayerActivity.class);
                i.putExtra("url", mFileUrl);
                startActivity(i);
            }
        });
    }
}