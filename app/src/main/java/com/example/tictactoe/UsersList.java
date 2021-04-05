package com.example.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UsersList extends Fragment {

    private DatabaseReference mRef= FirebaseDatabase.getInstance().getReference();//reference of  parent node
    ArrayList<String> listOfUsers=new ArrayList<>();
    ListView userList;
    ArrayAdapter<String> myArrayAdapter;
    View view;
    private String LoginUID,LoginUserID,UserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_users_list, container, false);

        //         SETTING UP USER LIST
        userList=view.findViewById(R.id.userList);//connect listView of userList here
        myArrayAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listOfUsers);
        userList.setAdapter(myArrayAdapter);

        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(getActivity());
        LoginUID=signInAccount.getId();//LoginUID IS A INTEGER EG.116171117870835209803
        LoginUserID=signInAccount.getEmail();//this we should show some were so that user can know with which id he is logged in
        UserName=modifiedUserName(LoginUserID);//we remove @gmail.com as firebase wont accept it  as string

        mRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;//we are only worried about key as it contain the username
                Set<String> set = new HashSet<String>();
                Iterator i = snapshot.getChildren().iterator();
                while(i.hasNext()){
                    key = ((DataSnapshot) i.next()).getKey();
                    if(!key.equalsIgnoreCase(UserName)) {//he cannot send request to himself
                        set.add(key);
                    }
                }
                myArrayAdapter.clear();//refresh list
                myArrayAdapter.addAll(set);
                myArrayAdapter.notifyDataSetChanged();//these add users to send request
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Error Handling here
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//click listener to each element of list view
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//this gives view of the selected item
                final String requestToUser = ((TextView)view).getText().toString();// get username of requested person as it is a simple text view
                confirmRequest(requestToUser,"To");// build a alert dialog to confirm send request "to" means we are sending request
            }
        });
        return view;
    }

    private void confirmRequest(final String OtherPlayer, final String reqType){
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.confirm_dialog, null);
        b.setView(dialogView);

        b.setTitle("Start Game?");
        b.setMessage("Connect with " + OtherPlayer);//string concatination here
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRef.child("users").child(OtherPlayer).child("Request").push().setValue(UserName);
                    StartGame(UserName + ":" + OtherPlayer, OtherPlayer, "To");
                    //PlayerGameId=jisne send kiya:jisne recieve kiya
            }
        });
        b.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        b.show();
    }

    private void StartGame(String PlayerGameID, String OtherPlayer, String requestType){
        mRef.child("playing").child(PlayerGameID).removeValue();
        mRef.child("chat").child(PlayerGameID).child(UserName).setValue("Messages Here");
        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.putExtra("player_session", PlayerGameID);
        intent.putExtra("user_name", UserName);
        intent.putExtra("other_player", OtherPlayer);
        intent.putExtra("login_uid", LoginUID);
        intent.putExtra("request_type", requestType);
        startActivity(intent);
    }

    private String modifiedUserName(String userName) {
        //userName=kainathussain63@gmail.com,we want to remove from @ part as firebase result in error,length=24,@-com 10,index
        int lastIndex=userName.length();
        String modifiedUserName=userName.substring(0,lastIndex-10);
        return modifiedUserName;//returns kainathussain63
    }
}