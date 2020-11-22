package com.schickenon.cavproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    String mUrl;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mUrl = getIntent().getStringExtra("url");
        if(mUrl.isEmpty()) {
            return;
        }

        webView = findViewById(R.id.webView);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(mUrl);
    }
}