package com.schickenon.cavproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MovieDetails extends AppCompatActivity {

    ImageView movieImage;
    TextView movieName;
    AppCompatButton playButton;

    String mName, mImage, mId, mFileUrl, mDescription, hasAds;

    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";

    private InterstitialAd interstitialAd;

//    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Initialize the mobile Ads SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        // Create the InterstitialAd and set the adUnitId
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AD_UNIT_ID);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                PlayVideo();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                String error = String.format("domain: %s, code: %d, message: %s", loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                Toast.makeText(MovieDetails.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                Toast.makeText(MovieDetails.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
            }

        });

        movieImage = findViewById(R.id.movie_image);
        movieName = findViewById(R.id.movie_name);
        playButton = findViewById(R.id.play_button);

        // set data pass from activity previous
        mName = getIntent().getStringExtra("movieName");
        mImage = getIntent().getStringExtra("imageUrl");
        mId = getIntent().getStringExtra("MovieId");
        mFileUrl = getIntent().getStringExtra("fileUrl");
        mDescription = getIntent().getStringExtra("description");
        hasAds = getIntent().getStringExtra("hasAds");

        Log.d("SON", "" + hasAds);

        // set data to layout
        Glide.with(MovieDetails.this).load(mImage).into(movieImage);
        movieName.setText(mName);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitialAd();
            }
        });

        startLoadAds();
    }

    private void PlayVideo() {
        // Load the next interstital
        onReLoadAds();

        // open activity to play video
        Intent i = new Intent(MovieDetails.this, VideoPlayerActivity.class);
        i.putExtra("url", mFileUrl);
        i.putExtra("hasAds", hasAds);
        startActivity(i);
    }

    private void startLoadAds() {
        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
        }
    }

    private void onReLoadAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    private void showInterstitialAd() {
        if(interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            onReLoadAds();
        }
    }
}