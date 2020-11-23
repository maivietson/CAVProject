package com.schickenon.cavproject;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class VideoPlayerActivity extends AppCompatActivity {

    private PlayerView videoPlayer;
    private SimpleExoPlayer simpleExoPlayer;
    private static final String FILE_URL = "";
    private String hasAds;
    private int isShowAds;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video_player);

        mAdView = findViewById(R.id.admobView);

        hasAds = getIntent().getStringExtra("hasAds");
        if(hasAds != null) {
            isShowAds = Integer.parseInt(hasAds);
            if( isShowAds == 1) {
                MobileAds.initialize(this, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {

                    }
                });

                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);

                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        Toast.makeText(VideoPlayerActivity.this, "Ads loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        //String error = String.format("domain: %s, code: %d, message: %s", loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        //Toast.makeText(VideoPlayerActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT).show();
                        super.onAdFailedToLoad(loadAdError);
                    }

                    @Override
                    public void onAdClosed() {
                        mAdView.setVisibility(View.GONE);
                    }
                });

            } else {
                mAdView.setVisibility(View.GONE);
            }
        } else {
            mAdView.setVisibility(View.GONE);
        }

        videoPlayer = findViewById(R.id.exo_player);
        setupExoPlayer(getIntent().getStringExtra("url"));
    }

    private void setupExoPlayer(String url) {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        videoPlayer.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "movieapp"));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}