package com.apps.gofindmovie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.gofindmovie.R;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 26 may 2020
 */
public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_TIME = 5000;

    Animation topAnimation, bottomAnimation;
    ImageView logo;
    TextView descLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Animation
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo = findViewById(R.id.logo);
        descLogo = findViewById(R.id.desc_logo);

        logo.setAnimation(topAnimation);
        descLogo.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
                finish();
            }
        }, SPLASH_TIME);
    }
}
