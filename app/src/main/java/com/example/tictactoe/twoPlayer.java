package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class twoPlayer extends AppCompatActivity {
    TextView firstName,secondName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        String firstPlayerName=getIntent().getStringExtra("firstPlayerName");
        String secondPlayerName=getIntent().getStringExtra("secondPlayerName");

        firstName=findViewById(R.id.firstName);
        secondName=findViewById(R.id.secondName);
        firstName.setText(firstPlayerName);
        secondName.setText(secondPlayerName);

    }

    public void tap(View view) {
    }
}