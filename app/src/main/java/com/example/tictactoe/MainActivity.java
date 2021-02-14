package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button blog;
    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
    EditText msg;
    MediaPlayer tapsound;
    int player1=0;//x
    int player2=1;//0
    int activePlayer=(int)Math.random()+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i1=findViewById(R.id.i1);
        i2=findViewById(R.id.i2);
        i3=findViewById(R.id.i3);
        i4=findViewById(R.id.i4);
        i5=findViewById(R.id.i5);
        i6=findViewById(R.id.i6);
        i7=findViewById(R.id.i7);
        i8=findViewById(R.id.i8);
        i9=findViewById(R.id.i9);


    }

    public void tap(View view) {
        tapsound=MediaPlayer.create(this,R.raw.tapsound);
        tapsound.start();
        ImageView img =(ImageView) view;
        int active=activePlayer;
        if (active==1)
        {
          String tag=view.getTag().toString();
          img.setImageResource(R.drawable.x);
          activePlayer=0;

        }
        else
        {
            img.setImageResource(R.drawable.o);
            activePlayer=1;
        }
    }

    public void send(View view) {
        msg=findViewById(R.id.msg);
        msg.setText("");
    }

    public void blog(View view) {
    }
}