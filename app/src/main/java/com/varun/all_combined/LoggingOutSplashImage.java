package com.varun.all_combined;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LoggingOutSplashImage extends AppCompatActivity {

    private static int SPLASH_SCREEN = 1500;

    Animation topAnim, bottomAnim;
    ImageView splashImage;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_out_splash_image);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        splashImage = findViewById(R.id.splashImage);
        logo = findViewById(R.id.logo);

        splashImage.setAnimation(topAnim);
        splashImage.setAnimation(bottomAnim);
        logo.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoggingOutSplashImage.this, MainPage.class);
                startActivity(intent);
            }
        },SPLASH_SCREEN);

    }
}