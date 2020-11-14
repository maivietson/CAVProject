package com.schickenon.cavproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetails extends AppCompatActivity {

    ImageView movieImage;
    TextView movieName;
    AppCompatButton playButton;

    String mName, mImage, mId, mFileUrl, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieImage = findViewById(R.id.movie_image);
        movieName = findViewById(R.id.movie_name);
        playButton = findViewById(R.id.play_button);

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