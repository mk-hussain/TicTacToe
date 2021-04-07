package com.example.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    //playerSession for each game combination   is unique
    //requestType=to-i have send request first,from-i have accepted the request
    String playerSession,userName,otherPlayer,LoginUID,requestType,myGameSign;
    TextView msg,opponentMessage,firstPlayer,secondPlayer,currentTurn;
    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
    MediaPlayer tapSound;
    //playingState=1-can be played further,2-someone has won,3-draw
    int playingState = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();// gives reference to whole database
    DatabaseReference myRef = database.getReference();// gives reference to parent node
    //activePlayer=1- i have chance right now,2-2nd player has the chance(think in term of both the player)
    int activePlayer = 1;
    //to define which which block no player1 and player2 has filled
    ArrayList<Integer> Player1 = new ArrayList<>();
    ArrayList<Integer> Player2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        // initializing the views
        currentTurn=findViewById(R.id.currentTurn);
        firstPlayer=findViewById(R.id.firstPlayer);
        tapSound=MediaPlayer.create(this,R.raw.tapsound);
        firstPlayer.setText(userName);
        secondPlayer=findViewById(R.id.secondPlayer);secondPlayer.setText(otherPlayer);

        i1=findViewById(R.id.i1);
        i2=findViewById(R.id.i2);
        i3=findViewById(R.id.i3);
        i4=findViewById(R.id.i4);
        i5=findViewById(R.id.i5);
        i6=findViewById(R.id.i6);
        i7=findViewById(R.id.i7);
        i8=findViewById(R.id.i8);
        i9=findViewById(R.id.i9);

        playerSession="";userName="";otherPlayer="";LoginUID="";requestType="";myGameSign = "X";

        userName = getIntent().getExtras().get("user_name").toString();
        LoginUID = getIntent().getExtras().get("login_uid").toString();
        otherPlayer = getIntent().getExtras().get("other_player").toString();
        requestType = getIntent().getExtras().get("request_type").toString();
        playerSession = getIntent().getExtras().get("player_session").toString();

        playingState = 1;// at the starting of the game obvously it can be playable

        // setting opponent message into TextView
        final String[] oppMsg = new String[1];
        opponentMessage=findViewById(R.id.opponentMessage);
        myRef.child("chat").child(playerSession).child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                oppMsg[0] =snapshot.getValue().toString();
                opponentMessage.setText(oppMsg[0]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(requestType.equals("From")){// ie if i am accepting the request
            myGameSign = "0";
            currentTurn.setText("Your turn");// those who accept request will get 1st chance
            myRef.child("playing").child(playerSession).child("turn").setValue(userName);
        }else{// ie i have send the request first
            myGameSign = "X";
            currentTurn.setText(otherPlayer);// i will get 2nd chance
            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);
        }
        myRef.child("playing").child(playerSession).child("turn").addValueEventListener(new ValueEventListener() {
            // changing the turn every time
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    String value = (String) dataSnapshot.getValue();
                    if(value.equals(userName)) {// i am active
                        currentTurn.setText("Your turn");// my turn
                        setEnableClick(true);// i can click
                        activePlayer = 1;// i am active
                    }else if(value.equals(otherPlayer)){// not my turn
                        currentTurn.setText(otherPlayer);
                        setEnableClick(false);// i cannot click
                        activePlayer = 2;// i am not active
                    }
                }catch (Exception e){
                    e.printStackTrace();// print whaterver the exception is
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("playing").child(playerSession).child("game")
                .addValueEventListener(new ValueEventListener() {//this is triggered as a result of adding a block by someone
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            Player1.clear();
                            Player2.clear();
                            activePlayer = 2;
                            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                            if(map != null){
                                String value = "";
                                String firstPlayer = userName;
                                for(String key:map.keySet()){// for each key in the keySet
                                    value = (String) map.get(key);
                                    if(value.equals(userName)){
                                        activePlayer = 2;
                                    }else{
                                        activePlayer = 1;
                                    }
                                    firstPlayer = value;
                                    String[] splitID = key.split(":");//
                                    OtherPlayer(Integer.parseInt(splitID[1]));// i get to know which block other user has tap
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }

    public void tap(View view) {
        ImageView selectedImage = (ImageView) view;
        tapSound.start();
        if(playerSession.length() <= 0){
            Intent intent = new Intent(getApplicationContext(),home.class);
            startActivity(intent);
            finish();
        }else {
            int selectedBlock = Integer.parseInt(selectedImage.getTag().toString());
            myRef.child("playing").child(playerSession).child("game").child("block:"+selectedBlock).setValue(userName);//add the block and name to firebase
            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);//update the turn
            setEnableClick(false);
            activePlayer = 2;
            PlayGame(selectedBlock, selectedImage);
        }
    }

    public void PlayGame(int selectedBlock, ImageView selectedImage){
        if(playingState == 1) {
            if (requestType.equals("To")){
                if (activePlayer == 1) {
                    selectedImage.setImageResource(R.drawable.x);
                    Player1.add(selectedBlock);
                }else if (activePlayer == 2) {
                    selectedImage.setImageResource(R.drawable.o);
                    Player2.add(selectedBlock);
                }
            }
            else if (requestType.equals("From")){
                if (activePlayer == 1) {
                    selectedImage.setImageResource(R.drawable.o);
                    Player1.add(selectedBlock);
                }else if (activePlayer == 2) {
                    selectedImage.setImageResource(R.drawable.x);
                    Player2.add(selectedBlock);
                }
            }
            selectedImage.setEnabled(false);
            CheckWinner();
        }
    }

    private void CheckWinner(){
        int winner = 0;// no one has won
        ArrayList<Integer> emptyBlocks = new ArrayList<>();// to find draw case

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

        if(winner != 0 && playingState == 1){//some one is winner
            if(winner == 1){
                ShowAlert(otherPlayer +" is winner");
            }else if(winner == 2){
                ShowAlert("You won the game");
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

    public void OtherPlayer(int selectedBlock) {
        ImageView selectedImage =findViewById(R.id.i1);
        switch (selectedBlock)
        {
            case 1: selectedImage =findViewById(R.id.i1);break;
            case 2: selectedImage =findViewById(R.id.i2);break;
            case 3: selectedImage =findViewById(R.id.i3);break;
            case 4: selectedImage =findViewById(R.id.i4);break;
            case 5: selectedImage =findViewById(R.id.i5);break;
            case 6: selectedImage =findViewById(R.id.i6);break;
            case 7: selectedImage =findViewById(R.id.i7);break;
            case 8: selectedImage =findViewById(R.id.i8);break;
            case 9: selectedImage =findViewById(R.id.i9);break;
        }
        PlayGame(selectedBlock, selectedImage);
    }
    private void ResetGame(){
        playingState = 1;
        activePlayer = 1;
        Player1.clear();
        Player2.clear();
        myRef.child("playing").child(playerSession).removeValue();
        i1.setImageResource(0);i1.setEnabled(true);
        i2.setImageResource(0);i2.setEnabled(true);
        i3.setImageResource(0);i3.setEnabled(true);
        i4.setImageResource(0);i4.setEnabled(true);
        i5.setImageResource(0);i5.setEnabled(true);
        i6.setImageResource(0);i6.setEnabled(true);
        i7.setImageResource(0);i7.setEnabled(true);
        i8.setImageResource(0);i8.setEnabled(true);
        i9.setImageResource(0);i9.setEnabled(true);

    }
    private void ShowAlert(String Title){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(Title)
                .setMessage("Start a new game?")
                .setNegativeButton("Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetGame();
                        myRef.child("users").child(userName).child("Request").setValue(LoginUID);
                        //RemoveRequest();
                        Intent intent = new Intent(getApplicationContext(),home.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(R.drawable.dialog_info)
                .show();
    }
    private void setEnableClick(boolean b){
        i1.setClickable(b);i2.setClickable(b);
        i3.setClickable(b);i4.setClickable(b);
        i5.setClickable(b);i6.setClickable(b);
        i7.setClickable(b);i8.setClickable(b);
              i9.setClickable(b);
    }

    public void send(View view) {
        msg=findViewById(R.id.msg);
        String message=msg.getText().toString();
        myRef.child("chat").child(playerSession).child(otherPlayer).setValue(message);
        msg.setText("");
    }

    @Override
    protected void onDestroy() {// if back is pressed
        myRef.child("users").child(userName).child("Request").setValue(LoginUID);
        super.onDestroy();
    }
}