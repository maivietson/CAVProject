package com.galaxygame.cavenjoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.galaxygame.cavenjoy.adapter.MainListChapterMovieAdapter;
import com.galaxygame.cavenjoy.model.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = "MovieDetails";

    ImageView movieImage;
    TextView movieName, movieDescription;
    AppCompatButton playButton;

    RecyclerView recyclerViewChapter;
    MainListChapterMovieAdapter mainListChapterMovieAdapter;
    List<CategoryItem> listChapterForMovie = new ArrayList<>();

    String mName, mImage, mId, mFileUrl, mDescription, hasAds;

//    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String AD_UNIT_ID = "ca-app-pub-1097952400927041/5091521916";

    private InterstitialAd interstitialAd;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        firebaseDatabase = FirebaseDatabase.getInstance();

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
                Log.d(TAG, "onAdFailedToLoad() with error: " + error);
//                onReLoadAds();
            }
        });

        getViewOfActivity();

        getDataFromPreviousActivity();

        // set data to layout
        Glide.with(MovieDetails.this).load(mImage).into(movieImage);
        movieName.setText(mName);
        movieDescription.setText(mDescription);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitialAd();
            }
        });

        startLoadAds();
    }

    private void setChapterForMovie() {
        // get data chapter for movie
        boolean isModeMovie = mDescription.contains("Movie Mode");
        boolean isPhimBo = mFileUrl.contains("phimbo_");
        if(isModeMovie && isPhimBo) {
            playButton.setEnabled(false);
            getListChapterForMovie(mFileUrl);
        }
    }

    private void getDataFromPreviousActivity() {
        // set data pass from activity previous
        mName = getIntent().getStringExtra("movieName");
        mImage = getIntent().getStringExtra("imageUrl");
        mId = getIntent().getStringExtra("MovieId");
        mFileUrl = getIntent().getStringExtra("fileUrl");
        mDescription = getIntent().getStringExtra("description");
        hasAds = getIntent().getStringExtra("hasAds");

        setChapterForMovie();
    }

    private void getViewOfActivity() {
        movieImage = findViewById(R.id.movie_image);
        movieName = findViewById(R.id.movie_name);
        playButton = findViewById(R.id.play_button);
        movieDescription = findViewById(R.id.movie_description);
    }

    private void getListChapterForMovie(String movieName) {
        String path = "movie/phimbo/" + movieName;
        firebaseDatabase.getReference(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listChapterForMovie.clear();
                for(DataSnapshot childDataSnapshot : snapshot.getChildren()) {

                    CategoryItem chapterMovie = childDataSnapshot.getValue(CategoryItem.class);

                    listChapterForMovie.add(chapterMovie);
                }

                setListChapterForMovie(listChapterForMovie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setListChapterForMovie(List<CategoryItem> listChapter) {
        recyclerViewChapter = findViewById(R.id.recycler_chapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewChapter.setLayoutManager(layoutManager);
        mainListChapterMovieAdapter = new MainListChapterMovieAdapter(this, listChapter);
        recyclerViewChapter.setAdapter(mainListChapterMovieAdapter);
    }

    private void PlayVideo() {
        // Load the next interstital
         onReLoadAds();

        // open activity to play video
        boolean isModeMovie = mDescription.contains("Movie Mode");
        Intent i;
        if(isModeMovie) {
            i = new Intent(MovieDetails.this, WebViewActivity.class);
            i.putExtra("url", mFileUrl);
        } else {
            i = new Intent(MovieDetails.this, VideoPlayerActivity.class);
            i.putExtra("url", mFileUrl);
            i.putExtra("hasAds", hasAds);
        }
        startActivity(i);
    }

    private void startLoadAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    private void onReLoadAds() {
        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
        }
    }

    private void showInterstitialAd() {
        if(interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
//            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
//            onReLoadAds();
            PlayVideo();
        }
    }
}