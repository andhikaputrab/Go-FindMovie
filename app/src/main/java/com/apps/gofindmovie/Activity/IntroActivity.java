package com.apps.gofindmovie.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.apps.gofindmovie.Adapter.IntroViewPagerAdapter;
import com.apps.gofindmovie.R;
import com.apps.gofindmovie.SharedPreferences.SharedPreference;
import com.apps.gofindmovie.model.IntroItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 27 may 2020
 */

public class IntroActivity extends AppCompatActivity {

    private ViewPager introViewPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabLayout;
    private Button btnNext,btnStart;
    private int position = 0;
    private Animation btnStartAnimation;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // mengecek apakah sudah pernah membuka aplikasi atau belum
        if (SharedPreference.getFirstTimeStatus(getBaseContext())){
            goToHomeScreen();
            finish();
        }

        // Membuat activity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro_screen);

        // initialize views
        tabLayout = findViewById(R.id.intro_tablayout);
        btnNext = findViewById(R.id.btn_next);
        btnStart = findViewById(R.id.btn_start);
        btnStartAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        // fill list screen
        final List<IntroItem> listItem = new ArrayList<>();
        listItem.add(new IntroItem("Find Your Movie", "", R.drawable.welcome));
        listItem.add(new IntroItem("Bookmark Your Movie", "", R.drawable.ic_aboutme));
        listItem.add(new IntroItem("Let's Started!", "", R.drawable.ic_lets_start));

        // setup ViewPager
        introViewPager = findViewById(R.id.intro_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, listItem);
        introViewPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager
        tabLayout.setupWithViewPager(introViewPager);

        // button next click listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = introViewPager.getCurrentItem();
                if (position < listItem.size()){
                    position++;
                    introViewPager.setCurrentItem(position);
                }

                if (position == listItem.size()-1){
                    loadLastScreen();
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });

    }

    // show GETSTARTED button and hide the indicator and the next button
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);

        // add animation button get started
        btnStart.setAnimation(btnStartAnimation);
    }

    private void goToHomeScreen(){
        SharedPreference.setFirstTimeStatus(true, getBaseContext());
        startActivity(new Intent(IntroActivity.this, HomeActivity.class));
        finish();
    }
}
