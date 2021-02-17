package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {



    TextView userName,opponentName;
    Boolean gameActive=true;
    Button blog;
    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
    EditText msg;
    MediaPlayer tapsound;



    //player 0-X,1-O
    int player1=0;//x
    int player2=1;//0
    int activePlayer=0;
    int[] gameState ={2,2,2,2,2,2,2,2,2};
    // 0-x,1-O,2-null
    int [] [] winningPosition= {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference =mFirebaseDatabase.getReference().child("message");

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
        String userName="KAINAT";
        String opponentName="OPPONENT";
        Intent i1=new Intent(this,congratulation.class);
        i1.putExtra("userName",userName);
        i1.putExtra("opponentName",opponentName);
        Intent i2=new Intent(this,loose.class);
        /*i2.putExtra(EXTRA_TEXT,userName);
        i2.putExtra(EXTRA_TEXT2,opponentName);*/
        ImageView img =(ImageView) view;
        int tag= Integer.parseInt(img.getTag().toString());
        if (gameState[tag]==2) {

            tapsound=MediaPlayer.create(this,R.raw.tapsound);
        tapsound.start();
        gameState[tag]=activePlayer;

        if (activePlayer==0)//1-O & 0-X
        {
            img.setImageResource(R.drawable.x);
          activePlayer=1;
        }
        else
        {
            img.setImageResource(R.drawable.o);
            activePlayer=0;
        }

        }
        else Toast.makeText(this, "Already Filled tap another grid", Toast.LENGTH_SHORT).show();
        // i will make a winner function to check if one has won

        if ((gameState[0]==gameState[1])&&(gameState[1]==gameState[2]))//0-X,1-O
        {
            if (gameState[0]==0)
            {
                startActivity(i1);
                Toast.makeText(this, "X has win the game", Toast.LENGTH_SHORT).show();
            }
            else if (gameState[0]==1)
            {
                startActivity(i1);
                Toast.makeText(this, "O has win the game", Toast.LENGTH_SHORT).show();
            }
        }
        if ((gameState[3]==gameState[4])&&(gameState[4]==gameState[5]))//0-X,1-O
        {
            if (gameState[3]==0)
            {
                startActivity(i1);
                Toast.makeText(this, "X has win the game", Toast.LENGTH_SHORT).show();
            }
            else if (gameState[3]==1)
            {
                startActivity(i1);
                Toast.makeText(this, "O has win the game", Toast.LENGTH_SHORT).show();
            }
        }
        if ((gameState[6]==gameState[7])&&(gameState[7]==gameState[8]))//0-X,1-O
        {
            if (gameState[6]==0)
            {
                startActivity(i1);
                Toast.makeText(this, "X has win the game", Toast.LENGTH_SHORT).show();
            }
            else if (gameState[6]==1)
            {
                startActivity(i1);
                Toast.makeText(this, "O has win the game", Toast.LENGTH_SHORT).show();
            }
        }
        if ((gameState[0]==gameState[3])&&(gameState[3]==gameState[6]))//0-X,1-O
        {
            if (gameState[0]==0)
            {
                startActivity(i1);
                Toast.makeText(this, "X has win the game", Toast.LENGTH_SHORT).show();
            }
            else if (gameState[0]==1)
            {
                startActivity(i1);
                Toast.makeText(this, "O has win the game", Toast.LENGTH_SHORT).show();
            }
        }
        if ((gameState[1]==gameState[4])&&(gameState[4]==gameState[7]))//0-X,1-O
        {
            if (gameState[1]==0)
            {
                startActivity(i1);
                Toast.makeText(this, "X has win the game", Toast.LENGTH_SHORT).show();
            }
            else if (gameState[1]==1)
            {
                startActivity(i1);
                Toast.makeText(this, "O has win the game", Toast.LENGTH_SHORT).show();
            }
        }
        if ((gameState[2]==gameState[5])&&(gameState[5]==gameState[8]))//0-X,1-O
        {
            if (gameState[2]==0)
            {
                startActivity(i1);
                Toast.makeText(this, "X has win the game", Toast.LENGTH_SHORT).show();
            }
            else if (gameState[2]==1)
            {
                startActivity(i1);
                Toast.makeText(this, "O has win the game", Toast.LENGTH_SHORT).show();
            }
        }
        if ((gameState[0]==gameState[4])&&(gameState[4]==gameState[8]))//0-X,1-O
        {
            if (gameState[0]==0)
            {
                startActivity(i1);
                Toast.makeText(this, "X has win the game", Toast.LENGTH_SHORT).show();
            }
            else if (gameState[0]==1)
            {
                startActivity(i1);
                Toast.makeText(this, "O has win the game", Toast.LENGTH_SHORT).show();
            }
        }
        if ((gameState[2]==gameState[4])&&(gameState[4]==gameState[6]))//0-X,1-O
        {
            if (gameState[2]==0)
            {
                startActivity(i1);
                Toast.makeText(this, "X has win the game", Toast.LENGTH_SHORT).show();
            }
            else if (gameState[2]==1)
            {
                startActivity(i1);
                Toast.makeText(this, "O has win the game", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void send(View view) {
        msg=findViewById(R.id.msg);
        String s=msg.getText().toString();
        mFirebaseDatabaseReference.push().setValue(s);
        msg.setText("");
    }

    public void blog(View view)
    {
        Intent i=new Intent(MainActivity.this,blog.class);
        startActivity(i);
    }

    public void registerActivity(View view) {
        Intent i=new Intent(this,login_activity.class);
        startActivity(i);
    }
}