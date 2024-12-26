package com.app.LMS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static long TIME = 2000;
    private TextView text;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        text = findViewById(R.id.splash_text);
        img  = findViewById(R.id.splash_logo);

        Animation text_anim = AnimationUtils.loadAnimation(this, R.anim.anim_text);
        Animation img_anim  = AnimationUtils.loadAnimation(this, R.anim.anim_img);

        text.startAnimation(text_anim);
        img.startAnimation(img_anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), PanelSelection.class));
                finish();
            }
        },TIME);
    }
}