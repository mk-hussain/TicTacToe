package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class offline_Multiplayer extends AppCompatActivity {
    EditText firstPlayerName,secondPlayerName;
    String onePlayerName,twoPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline__multiplayer);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
    }

    public void next(View view) {
        firstPlayerName=findViewById(R.id.firstPlayerName);
        secondPlayerName=findViewById(R.id.secondPlayerName);
        onePlayerName=firstPlayerName.getText().toString();
        twoPlayerName=secondPlayerName.getText().toString();
        Intent i1=new Intent(this,twoPlayer.class);
        i1.putExtra("firstPlayerName",onePlayerName);
        i1.putExtra("secondPlayerName",twoPlayerName);
        startActivity(i1);
    }
}