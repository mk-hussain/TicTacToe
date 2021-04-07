package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class twoPlayer extends AppCompatActivity {
    TextView firstName,secondName,currentTurn;
    String firstPlayerName,secondPlayerName;
    ArrayList<Integer> Player1 = new ArrayList<>();
    ArrayList<Integer> Player2 = new ArrayList<>();
    int activePlayer=1;//1-Player1-X,2-player2-O
    private int playingState=0;
    // 1-playable further,2-some has won,3-draw
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        firstPlayerName=getIntent().getStringExtra("firstPlayerName");
        secondPlayerName=getIntent().getStringExtra("secondPlayerName");

        playingState=1;
        firstName=findViewById(R.id.firstName);
        secondName=findViewById(R.id.secondName);
        currentTurn=findViewById(R.id.current_turn);

        firstName.setText(firstPlayerName);
        secondName.setText(secondPlayerName);

    }

    public void tap(View view) {
        ImageView selectedImage=(ImageView)view;
        int tag;
        if (playingState==1&&activePlayer==1)
        {
            tag= Integer.parseInt(selectedImage.getTag().toString());
            selectedImage.setImageResource(R.drawable.x);
            selectedImage.setEnabled(false);
            currentTurn.setText(secondPlayerName);
            Player1.add(tag);
            activePlayer=2;
        }
        else if (playingState==1&&activePlayer==2)
        {
            tag= Integer.parseInt(selectedImage.getTag().toString());
            selectedImage.setImageResource(R.drawable.o);
            selectedImage.setEnabled(false);
            Player2.add(tag);
            currentTurn.setText(firstPlayerName);
            activePlayer=1;
        }
        CheckWinner();
    }
    private void CheckWinner(){
        int winner = 0;
        ArrayList<Integer> emptyBlocks = new ArrayList<>();

        /********* for Player 1 *********/
        if(Player1.contains(1) && Player1.contains(2) && Player1.contains(3)){ winner = 1; }
        if(Player1.contains(4) && Player1.contains(5) && Player1.contains(6)){ winner = 1; }
        if(Player1.contains(7) && Player1.contains(8) && Player1.contains(9)){ winner = 1; }
        if(Player1.contains(1) && Player1.contains(4) && Player1.contains(7)){ winner = 1; }
        if(Player1.contains(2) && Player1.contains(5) && Player1.contains(8)){ winner = 1; }
        if(Player1.contains(3) && Player1.contains(6) && Player1.contains(9)){ winner = 1; }
        if(Player1.contains(1) && Player1.contains(5) && Player1.contains(9)){ winner = 1; }
        if(Player1.contains(3) && Player1.contains(5) && Player1.contains(7)){ winner = 1; }

        /********* for Player 2 *********/
        if(Player2.contains(1) && Player2.contains(2) && Player2.contains(3)){ winner = 2; }
        if(Player2.contains(4) && Player2.contains(5) && Player2.contains(6)){ winner = 2; }
        if(Player2.contains(7) && Player2.contains(8) && Player2.contains(9)){ winner = 2; }
        if(Player2.contains(1) && Player2.contains(4) && Player2.contains(7)){ winner = 2; }
        if(Player2.contains(2) && Player2.contains(5) && Player2.contains(8)){ winner = 2; }
        if(Player2.contains(3) && Player2.contains(6) && Player2.contains(9)){ winner = 2; }
        if(Player2.contains(1) && Player2.contains(5) && Player2.contains(9)){ winner = 2; }
        if(Player2.contains(3) && Player2.contains(5) && Player2.contains(7)){ winner = 2; }

        if(winner != 0 && playingState == 1){
            if(winner == 1){
                ShowAlert( firstPlayerName+" is winner");
            }else if(winner == 2){
                ShowAlert(secondPlayerName+" is winner");
            }
            playingState = 2;
        }
        for(int i=1; i<=9; i++){
            if(!(Player1.contains(i) || Player2.contains(i))){
                emptyBlocks.add(i);
            }
        }
        if(emptyBlocks.size() == 0) {
            if(playingState == 1) {
                AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                ShowAlert("Draw");
            }
            playingState = 3;
        }
    }
    private void ShowAlert(String Title){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(Title)
                .setMessage("Start a new game?")
                .setNegativeButton("Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(twoPlayer.this,home.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(R.drawable.dialog_info)
                .show();
    }
}