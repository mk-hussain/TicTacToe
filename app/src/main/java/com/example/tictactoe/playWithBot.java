package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.util.ArrayList;
import java.util.Random;

public class playWithBot extends AppCompatActivity {

    TextView name;
    ImageView i0, i1, i2, i3, i4, i5, i6, i7, i8;
    MediaPlayer sound;
    ArrayList<Integer> emptyBlock = new ArrayList<>();
    int activePlayer = 0;// 0-X-user,1-O-Bot-(Definition)
    int winner=0; //1-user won,2-bot won
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};//2-Not filled yet,1-filled by BOT,0-filled by User
    // sample gameState in which user has won={0,0,0,2,1,2,1,2,2}
    int playingState;
    /* 1-can be played further(if all blocks are not filled and no one has win),2-someone has win(winning condition satisfied),
    3-DRAW(all blocks filled but no one has win)*/
    int[][] winningPosition = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};//2D array in java,winningPosition[3][2]=6

    String bot = "BOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_bot);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        String userName = signInAccount.getDisplayName();// name of user is stored in userName
        name = findViewById(R.id.userName);

        playingState=1;//only true if started 1 st time

        if (signInAccount != null) {

            name.setText(userName);
            // mail.setText(signInAccount.getEmail());- way to get mail id
        }

    }


    public void tap(View view) {
        ImageView img = (ImageView) view;//tapped image view is stored in img
        int tag = Integer.parseInt(img.getTag().toString());//tag of image view is stored in tag eg: tag=2

        for (int index = 0; index<9;index++) {
            if (gameState[0] == 2) {
                emptyBlock.add(index);
            }
        }
        int length=emptyBlock.size();
        //if length=0 ,draw or someone has win

        if (gameState[tag]==2 && playingState==1) {
            if (activePlayer == 0) //user is active,obviously
            {
                img.setImageResource(R.drawable.x);
                gameState[tag] = 0;
                winnerCheck();
                activePlayer = 1;// Bot chance
                botPlay();
            }
        } else Toast.makeText(this, "ALREADY FILLED", Toast.LENGTH_SHORT).show();
    }

    private void botPlay() {
        ImageView i0 = findViewById(R.id.i0);
        ImageView i1 = findViewById(R.id.i1);
        ImageView i2 = findViewById(R.id.i2);
        ImageView i3 = findViewById(R.id.i3);
        ImageView i4 = findViewById(R.id.i4);
        ImageView i5 = findViewById(R.id.i5);
        ImageView i6 = findViewById(R.id.i6);
        ImageView i7 = findViewById(R.id.i7);
        ImageView i8 = findViewById(R.id.i8);

        for (int index = 0; index<9;index++) {
            if (gameState[0] == 2) {
                emptyBlock.add(index);
            }
        }
        int length=emptyBlock.size();//if length=0,draw
        if (length==0)
        {
            alertShow("Game Draw");
        }
        else {
            //let emptyBlock={0,5,9}

            //Random r = new Random();
            //int randomIndex = r.nextInt(emptyBlock.size() - 1);
            //int selectedBlock = emptyBlock.get(randomIndex);
            double randomNo = Math.random();//randomNo~[0,1)
            randomNo = randomNo * (length);
            int randomInt = (int) randomNo;//let randomInt=1-actual index of empty block then emptyBlock[randomint ie1]=5 is the actual index of game state empty=
            int randomIndex = emptyBlock.get(randomInt);//-This gives the index of gameState array which are empty the same is Tag of images eg:randomIndex=3
            ImageView img;
            switch (randomIndex) {
                case 0: img = i0;break;
                case 1: img = i1;break;
                case 2: img = i2;break;
                case 3: img = i3;break;
                case 4: img = i4;break;
                case 5: img = i5;break;
                case 6: img = i6;break;
                case 7: img = i7;break;
                case 8: img = i8;break;
                default: img = null;
            }
            img.setImageResource(R.drawable.o);
            gameState[randomIndex] = 1;
            winnerCheck();
            activePlayer = 0;//user chance
        }
        }

    private void winnerCheck() {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        String userName = signInAccount.getDisplayName();
        Intent i1 = new Intent(playWithBot.this, congratulation.class);
        Intent i2 = new Intent(playWithBot.this, loose.class);
        i1.putExtra("winnerName", userName);
        i2.putExtra("loserName", userName);

        if ((gameState[0] == gameState[1]) && (gameState[1] == gameState[2]) && (gameState[0] == 0)) { winner=1;}
        if ((gameState[3] == gameState[4]) && (gameState[4] == gameState[5]) && (gameState[3] == 0)) { winner=1;}
        if ((gameState[6] == gameState[7]) && (gameState[7] == gameState[8]) && (gameState[6] == 0)) { winner=1;}
        if ((gameState[0] == gameState[3]) && (gameState[3] == gameState[6]) && (gameState[0] == 0)) { winner=1;}
        if ((gameState[1] == gameState[4]) && (gameState[4] == gameState[7]) && (gameState[1] == 0)) { winner=1;}
        if ((gameState[2] == gameState[5]) && (gameState[5] == gameState[8]) && (gameState[2] == 0)) { winner=1;}
        if ((gameState[0] == gameState[4]) && (gameState[4] == gameState[8]) && (gameState[0] == 0)) { winner=1;}
        if ((gameState[2] == gameState[4]) && (gameState[4] == gameState[6]) && (gameState[2] == 0)) { winner=1;}

        if ((gameState[0] == gameState[1]) && (gameState[1] == gameState[2]) && (gameState[0] == 1)) { winner=2;}
        if ((gameState[3] == gameState[4]) && (gameState[4] == gameState[5]) && (gameState[3] == 1)) { winner=2;}
        if ((gameState[6] == gameState[7]) && (gameState[7] == gameState[8]) && (gameState[6] == 1)) { winner=2;}
        if ((gameState[0] == gameState[3]) && (gameState[3] == gameState[6]) && (gameState[0] == 1)) { winner=2;}
        if ((gameState[1] == gameState[4]) && (gameState[4] == gameState[7]) && (gameState[1] == 1)) { winner=2;}
        if ((gameState[2] == gameState[5]) && (gameState[5] == gameState[8]) && (gameState[2] == 1)) { winner=2;}
        if ((gameState[0] == gameState[4]) && (gameState[4] == gameState[8]) && (gameState[0] == 1)) { winner=2;}
        if ((gameState[2] == gameState[4]) && (gameState[4] == gameState[6]) && (gameState[2] == 1)) { winner=2;}

        if (winner==1||winner==2)
        {
            if (winner==1)
            {
                startActivity(i1);
            }
            else
            {
                startActivity(i2);
            }
            playingState=2;//someone has won
        }
    }
    private void reset() {

        ArrayList<Integer> emptyBlock = new ArrayList<>();
        int activePlayer = 0;// 0-X-user,1-O-Bot-(Definition)
        int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};//2-Not filled yet,1-filled by BOT,0-filled by User
        // sample gameState in which user has won={0,0,0,2,1,2,1,2,2}
        int playingState = 1;

        i0.setImageResource(0);
        i1.setImageResource(0);
        i2.setImageResource(0);
        i3.setImageResource(0);
        i4.setImageResource(0);
        i5.setImageResource(0);
        i6.setImageResource(0);
        i7.setImageResource(0);
        i8.setImageResource(0);
    }
    private void alertShow(String text)
    {
        AlertDialog.Builder b= new AlertDialog.Builder(this);
        b.setTitle(text);
        b.setMessage("Play Again")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reset();
                }
            })
            .setNegativeButton("Home", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), home.class);
                    startActivity(intent);
                    finish();
                }
            })
            .setIcon(android.R.drawable.ic_dialog_info)
            .show();
    }

}