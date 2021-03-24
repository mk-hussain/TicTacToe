package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class congratulation extends AppCompatActivity {
    TextView nameOfWinner;
    Button returnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        nameOfWinner=findViewById(R.id.nameOfWinner);
        String winnerName=getIntent().getStringExtra("winnerName");
        nameOfWinner.setText(winnerName);
    }

    public void returnHome(View view) {
        Intent i=new Intent(this,home.class);
        startActivity(i);
    }
}