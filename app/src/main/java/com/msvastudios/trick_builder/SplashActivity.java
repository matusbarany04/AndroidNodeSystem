package com.msvastudios.trick_builder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    private final static int SPLASH_SCREEN_TIME_OUT = 500;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(Color.alpha(0));
        getWindow().setNavigationBarColor(Color.alpha(0));

        TextView textView = this.findViewById(R.id.text);
//        textView.setText(stringFromJNI());

        ImageView imageView = findViewById(R.id.gif_player);

        Glide.with(this).load(R.drawable.loading).into(imageView);

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            overridePendingTransition(0, 0);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();

        }, SPLASH_SCREEN_TIME_OUT);
    }
//    public native String stringFromJNI();
}
