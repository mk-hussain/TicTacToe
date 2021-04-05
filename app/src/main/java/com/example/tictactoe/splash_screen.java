package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splash_screen extends AppCompatActivity {

    Animation topAnim,bottomAnim;
    ImageView ticTacToe,onlineMultiplayer;
    private static int SPLASH_SCREEN=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// removes status bar

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        topAnim.setDuration(2000);
        bottomAnim.setDuration(2000);

        ticTacToe=findViewById(R.id.ticTacToe);
        onlineMultiplayer=findViewById(R.id.onlineMultiplayer);

        ticTacToe.setAnimation(bottomAnim);
        onlineMultiplayer.setAnimation(topAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(splash_screen.this,login_activity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN);
    }
}