package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class loose extends AppCompatActivity {
    TextView nameOfLoser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loose);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        nameOfLoser=findViewById(R.id.nameOfLoser);
        String loserName= getIntent().getStringExtra("loserName");
        nameOfLoser.setText(loserName);
    }

    public void returnHome(View view) {
        Intent i= new Intent(this,home.class);
        startActivity(i);
    }
}