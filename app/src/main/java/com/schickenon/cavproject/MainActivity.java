package com.schickenon.cavproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.schickenon.cavproject.adapter.BannerMoviesPagerAdapter;
import com.schickenon.cavproject.adapter.MainRecyclerAdapter;
import com.schickenon.cavproject.model.AllCategory;
import com.schickenon.cavproject.model.BannerMovies;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerMoviesViewPager;

    List<BannerMovies> bannerList = new ArrayList<>();
    List<AllCategory> allCategoryList = new ArrayList<>();

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceNewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayout);

        // init database reference
        firebaseDatabase = FirebaseDatabase.getInstance();

        getDataBannerMovies("videos/newMovies/homeBanner");
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        getDataBannerMovies("videos/newMovies/tvShowBanner");
                        return;
                    case 2:
                        getDataBannerMovies("videos/newMovies/moviesBanner");
                        return;
                    case 3:
                        getDataBannerMovies("videos/newMovies/kidsBanner");
                        return;
                    default:
                        getDataBannerMovies("videos/newMovies/homeBanner");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        allCategoryList = new ArrayList<>();
//        allCategoryList.add(new AllCategory(1, "Bollywood"));
//        allCategoryList.add(new AllCategory(1, "Hollywood"));
//        allCategoryList.add(new AllCategory(1, "Kids"));
//
//        // pass array to recycle setup method
//        setMainRecycler(allCategoryList);
        getCategoryHome("videos/categoryHome");
    }

    private void getCategoryHome(String path) {
        firebaseDatabase.getReference(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allCategoryList.clear();
                for(DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    AllCategory allCategory = childDataSnapshot.getValue(AllCategory.class);
                    allCategoryList.add(allCategory);
                }

                setMainRecycler(allCategoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
    }

    private void getDataBannerMovies(String path) {
        firebaseDatabase.getReference(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bannerList.clear();
                for(DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    BannerMovies bannerMovies =  childDataSnapshot.getValue(BannerMovies.class);
                    bannerList.add(bannerMovies);
                }

                setBannerMoviesPagerAdapter(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
    }

    private void setBannerMoviesPagerAdapter(List<BannerMovies> bannerMoviesList) {
        bannerMoviesViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviesPagerAdapter = new BannerMoviesPagerAdapter(this, bannerMoviesList);
        bannerMoviesViewPager.setAdapter(bannerMoviesPagerAdapter);

        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        indicatorTab.setupWithViewPager(bannerMoviesViewPager, true);
    }

    class AutoSlider extends TimerTask {

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(bannerMoviesViewPager.getCurrentItem() < bannerList.size() - 1) {
                        bannerMoviesViewPager.setCurrentItem(bannerMoviesViewPager.getCurrentItem() + 1);
                    } else {
                        bannerMoviesViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public void setMainRecycler(List<AllCategory> allCategoryList) {

        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);

    }
}