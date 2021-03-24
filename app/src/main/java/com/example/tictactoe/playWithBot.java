package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
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
    ArrayList<Integer> emptyBlock = new ArrayList<Integer>();
    ArrayList<Integer> userBlock=new ArrayList<Integer>();
    ArrayList<Integer> botBlock=new ArrayList<Integer>();
    int activePlayer = 0;// 0-X-user,1-O-Bot-(Definition)
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};//2-Not filled yet,1-filled by BOT,0-filled by User
    // sample gameState in which user has won={0,0,0,2,1,2,1,2,2}
    int playingState = 1;
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

        if (signInAccount != null) {

            name.setText(userName);

            //  mail.setText(signInAccount.getEmail());
        }

    }


    public void tap(View view) {
        ImageView img = (ImageView) view;//tapped image view is stored in img
        int tag = Integer.parseInt(img.getTag().toString());//tag of image view is stored in tag eg: tag=2
       // if (playingState == 2 || playingState == 3) {
         //   reset();
        //}
        for (int index = 0; index<9;index++) {
            if (gameState[0] == 2) {
                emptyBlock.add(index);
            }
        }
        int length=emptyBlock.size();
        //if length=0 ,draw

        if (gameState[tag] == 2) {
            if (activePlayer == 0) //user is active,obviously
            {
                img.setImageResource(R.drawable.x);
                gameState[tag] = 0;
                winnerCheckForUser();
                activePlayer = 1;// Bot chance
                botPlay();
            }


        } else Toast.makeText(this, "ALREADY FILLED", Toast.LENGTH_SHORT).show();
    }

    private void winnerCheckForUser() {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        String userName = signInAccount.getDisplayName();
        Intent i = new Intent(playWithBot.this, congratulation.class);
        i.putExtra("winnerName", userName);
        if ((gameState[0] == gameState[1]) && (gameState[1] == gameState[2]) && (gameState[0] == 0)) {
            startActivity(i);
        }
        if ((gameState[3] == gameState[4]) && (gameState[4] == gameState[5]) && (gameState[3] == 0)) {
            startActivity(i);
        }
        if ((gameState[6] == gameState[7]) && (gameState[7] == gameState[8]) && (gameState[6] == 0)) {
            startActivity(i);
        }
        if ((gameState[0] == gameState[3]) && (gameState[3] == gameState[6]) && (gameState[0] == 0)) {
            startActivity(i);
        }
        if ((gameState[1] == gameState[4]) && (gameState[4] == gameState[7]) && (gameState[1] == 0)) {
            startActivity(i);
        }
        if ((gameState[2] == gameState[5]) && (gameState[5] == gameState[8]) && (gameState[2] == 0)) {
            startActivity(i);
        }
        if ((gameState[0] == gameState[4]) && (gameState[4] == gameState[8]) && (gameState[0] == 0)) {
            startActivity(i);
        }
        if ((gameState[2] == gameState[4]) && (gameState[4] == gameState[6]) && (gameState[2] == 0)) {
            startActivity(i);
        }
    }

    private void reset() {
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

        //int index=0;

        for (int index = 0; index<9;index++) {
            if (gameState[0] == 2) {
                emptyBlock.add(index);
            }
        }
        int length=emptyBlock.size();//if length=0,draw
        //let emptyBlock={0,5,9}

        //Random r = new Random();
        //int randomIndex = r.nextInt(emptyBlock.size() - 1);
        //int selectedBlock = emptyBlock.get(randomIndex);
        double randomNo=Math.random();//randomNo~[0,1)
        randomNo=randomNo*(length);
        int randomInt=(int)randomNo;//let randomInt=1-actual index of empty block then emptyBlock[randomint ie1]=5 is the actual index of game state empty=
        int randomIndex=emptyBlock.get(randomInt);//-This gives the index of gameState array which are empty the same is Tag of images eg:randomIndex=3
        ImageView img;
        switch (randomIndex)
        {
            case 0:img=i0;
                break;
            case 1:img=i1;
                break;
            case 2:img=i2;
                break;
            case 3:img=i3;
                break;
            case 4:img=i4;
                break;
            case 5:img=i5;
                break;
            case 6:img=i6;
                break;
            case 7:img=i7;
                break;
            case 8:img=i8;
                break;
            default:img=null;
        }

        if (length!=0)
        {
            img.setImageResource(R.drawable.o);
            gameState[randomIndex]=1;
            activePlayer=0;//user chance
        }


        if (emptyBlock.size() == 0) {
            winnerCheckForBot();
            winnerCheckForUser();//draw

           // if (gameState[]== 1) {
                //AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                //ShowAlert("Draw");
             //   Toast.makeText(this, "GAME DRAW", Toast.LENGTH_SHORT).show();
            }
            playingState = 3;
            activePlayer = 0;
            winnerCheckForBot();
        }


    private void winnerCheckForBot() {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        String userName = signInAccount.getDisplayName();
        Intent i = new Intent(playWithBot.this, loose.class);
        i.putExtra("loserName", userName);
        if ((gameState[0] == gameState[1]) && (gameState[1] == gameState[2]) && (gameState[0] == 1)) {
            startActivity(i);
        }
        if ((gameState[3] == gameState[4]) && (gameState[4] == gameState[5]) && (gameState[3] == 1)) {
            startActivity(i);
        }
        if ((gameState[6] == gameState[7]) && (gameState[7] == gameState[8]) && (gameState[6] == 1)) {
            startActivity(i);
        }
        if ((gameState[0] == gameState[3]) && (gameState[3] == gameState[6]) && (gameState[0] == 1)) {
            startActivity(i);
        }
        if ((gameState[1] == gameState[4]) && (gameState[4] == gameState[7]) && (gameState[1] == 1)) {
            startActivity(i);
        }
        if ((gameState[2] == gameState[5]) && (gameState[5] == gameState[8]) && (gameState[2] == 1)) {
            startActivity(i);
        }
        if ((gameState[0] == gameState[4]) && (gameState[4] == gameState[8]) && (gameState[0] == 1)) {
            startActivity(i);
        }
        if ((gameState[2] == gameState[4]) && (gameState[4] == gameState[6]) && (gameState[2] == 1)) {
            startActivity(i);
        }
    }
}