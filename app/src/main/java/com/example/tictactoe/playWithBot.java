package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class playWithBot extends AppCompatActivity {
    TextView name;
    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
    MediaPlayer tapsound;
    int activePlayer=0;// 0-X-user,1-O-Bot
    int[] gameState ={2,2,2,2,2,2,2,2,2};

    // 0-x,1-O,2-null
    int [] [] winningPosition= {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};//2D array in java,winningPosition[3][2]=7

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_bot);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        name=findViewById(R.id.userName);
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null)
        {
            name.setText(signInAccount.getDisplayName());

          //  mail.setText(signInAccount.getEmail());
        }

    }

    public void tap(View view) {
        View image=view;
        ImageView img= (ImageView) view;
        int tag= Integer.parseInt(img.getTag().toString());
        if(gameState[tag]==2)
        {

                img.setImageResource(R.drawable.x);
                gameState[tag]=0;
                botPlay();


        }
        else Toast.makeText(this, "ALREADY FILLED", Toast.LENGTH_SHORT).show();
    }

    private void botPlay() {
        ImageView i1=findViewById(R.id.i1);
        ImageView i2=findViewById(R.id.i2);
        ImageView i3=findViewById(R.id.i3);
        ImageView i4=findViewById(R.id.i4);
        ImageView i5=findViewById(R.id.i5);
        ImageView i6=findViewById(R.id.i6);
        ImageView i7=findViewById(R.id.i7);
        ImageView i8=findViewById(R.id.i8);
        ImageView i9=findViewById(R.id.i9);
        Boolean ig1=i1.getDrawable()==null;
        Boolean ig2=i2.getDrawable()==null;
        Boolean ig3=i3.getDrawable()==null;
        Boolean ig4=i4.getDrawable()==null;
        Boolean ig5=i5.getDrawable()==null;
        Boolean ig6=i6.getDrawable()==null;
        Boolean ig7=i7.getDrawable()==null;
        Boolean ig8=i8.getDrawable()==null;
        Boolean ig9=i9.getDrawable()==null;

        if (i1.getDrawable()==null)
        {
            i1.setImageResource(R.drawable.o);
        }
        if (i2.getDrawable()==null)
        {
            i2.setImageResource(R.drawable.o);
            return;
        }
        if (i3.getDrawable()==null)
        {
            i3.setImageResource(R.drawable.o);
            return;
        }
        if (i4.getDrawable()==null)
        {
            i4.setImageResource(R.drawable.o);
            return;
        }
        if (i5.getDrawable()==null)
        {
            i5.setImageResource(R.drawable.o);
            return;
        }
        if (i6.getDrawable()==null)
        {
            i6.setImageResource(R.drawable.o);
            return;
        }
        if (i7.getDrawable()==null)
        {
            i7.setImageResource(R.drawable.o);
            return;
        }
        if (i8.getDrawable()==null)
        {
            i8.setImageResource(R.drawable.o);
            return;
        }
        if (i9.getDrawable()==null)
        {
            i9.setImageResource(R.drawable.o);
            return;
        }

         //ImageView img=(ImageView)view;
         //img.setImageResource(R.drawable.o);

    }
}