package com.schickenon.cavproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.schickenon.cavproject.adapter.BannerMoviesPagerAdapter;
import com.schickenon.cavproject.adapter.MainListCategoryAdapter;
import com.schickenon.cavproject.adapter.MainRecyclerAdapter;
import com.schickenon.cavproject.model.AllCategory;
import com.schickenon.cavproject.model.BannerMovies;
import com.schickenon.cavproject.model.CategoryItem;
import com.schickenon.cavproject.model.VideoItem;
import com.schickenon.cavproject.popup.PopupCustom;
import com.schickenon.cavproject.popup.UnlockAdultModePopup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MovieActivity extends AppCompatActivity {

    private static final String TAG = "MovieActivity";

    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerMoviesViewPager;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    List<BannerMovies> bannerList = new ArrayList<>();
    List<AllCategory> allCategoryList = new ArrayList<>();
    List<VideoItem> allVideoItemList = new ArrayList<>();

    MainRecyclerAdapter mainRecyclerAdapter;
    MainListCategoryAdapter mainListCategoryAdapter;
    RecyclerView mainRecycler;

    FloatingActionButton fabNotify;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceNewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayoutMovie);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);
        fabNotify = findViewById(R.id.fabNotify);

        // init database reference
        firebaseDatabase = FirebaseDatabase.getInstance();

        getDataBannerMovies("movie/newMovies/homeBanner");
        getCategoryHome("movie/categoryHome");

        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        setScrollDefaultState();
                        getListVideoForCategory("movie/lists/phimle");
                        return;
                    case 2:
                        setScrollDefaultState();
                        getListVideoForCategory("movie/lists/phimbo");
                        return;
                    case 3:
                        setScrollDefaultState();
                        getListVideoForCategory("movie/lists/phimhoathinh");
                        return;
                    default:
                        setScrollDefaultState();
                        getDataBannerMovies("movie/newMovies/homeBanner");
                        getCategoryHome("movie/categoryHome");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fabNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnlockAdultModePopup popupCustom = new UnlockAdultModePopup(MovieActivity.this);
                popupCustom.showPopupWindow(view);
            }
        });
    }

    private void getListVideoForCategory(String path) {
        firebaseDatabase.getReference(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allVideoItemList.clear();
                for(DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    VideoItem item = childDataSnapshot.getValue(VideoItem.class);
                    allVideoItemList.add(item);
                }

                setListVideoForCategory(allVideoItemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCategoryHome(String path) {
        firebaseDatabase.getReference(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allCategoryList.clear();
                for(DataSnapshot childDataSnapshot : snapshot.getChildren()) {

                    AllCategory allCategory = childDataSnapshot.getValue(AllCategory.class);

                    List<CategoryItem> categoryItemList = new ArrayList<>();

                    for(DataSnapshot childCategory : childDataSnapshot.child("listMovies").getChildren()) {
                        CategoryItem item = childCategory.getValue(CategoryItem.class);
                        categoryItemList.add(item);
                    }

                    allCategory.setCategoryItemList(categoryItemList);
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

            MovieActivity.this.runOnUiThread(new Runnable() {
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

    public void setListVideoForCategory(List<VideoItem> listVideoForCategory) {
        Log.d("SON", "" + Integer.toString(listVideoForCategory.size()));
        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainListCategoryAdapter = new MainListCategoryAdapter(this, listVideoForCategory);
        mainRecycler.setAdapter(mainListCategoryAdapter);
    }

    private void setScrollDefaultState() {
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}