package com.example.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class acceptRequest extends Fragment {
    View view;
    private DatabaseReference mRef=FirebaseDatabase.getInstance().getReference();//give reference of specific area of database here parent node
    ListView lv_acceptRequest;
    ArrayList<String> list_acceptRequest=new ArrayList<>();
    ArrayAdapter<String> mArrayAdapter;
    private String LoginUID,LoginUserID,UserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_accept_request, container, false);
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(getActivity());
        LoginUID=signInAccount.getId();
        LoginUserID=signInAccount.getEmail();
        UserName=modifiedUserName(LoginUserID);

        //     SETTING UP ACCEPT LIST
        lv_acceptRequest=view.findViewById(R.id.lv_acceptRequest);
        mArrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list_acceptRequest);
        lv_acceptRequest.setAdapter(mArrayAdapter);

        // we are attaching listener to "Request" we must take care of multiple listener at same place
        //The listener is triggered once for the initial state of the data and again anytime the data changes
        mRef.child("users").child(UserName).child("Request").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren())
                        {
                            String key;//we are only worried about push ids as it contain the username through which request has been send to us and we want to display them in a list view
                            Set<String> set = new HashSet<String>();
                            Iterator i = snapshot.getChildren().iterator();
                            while(i.hasNext()){
                                key = ((DataSnapshot) i.next()).getKey();
                                if(!key.equalsIgnoreCase(UserName)) {//he cannot send request to himself
                                    set.add(key);
                                }
                            }
                            ArrayList<String> al=new ArrayList<String>();
                            al.addAll(set);
                            int size=al.size();
                                for (int j=0;j<size;j++)
                                {
                                    mRef.child("users").child(UserName).child("Request").child(al.get(j)).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String value=snapshot.getValue().toString();
                                            mArrayAdapter.add(value);
                                            mArrayAdapter.notifyDataSetChanged();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                                }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
        lv_acceptRequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String requestFromUser = ((TextView)view).getText().toString();// get username of accepted person as it is a simple text view
                confirmRequest(requestFromUser, "From");
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
        b.setMessage("Connect with " + OtherPlayer);
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRef.child("users").child(OtherPlayer).child("Request").push().setValue(UserName);
                if(reqType.equalsIgnoreCase("From")) {
                    StartGame(OtherPlayer + ":" + UserName, OtherPlayer, "From");
                }
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
    private String modifiedUserName(String userName) {
        int lastIndex=userName.length();
        String modifiedUserName=userName.substring(0,lastIndex-10);
        return modifiedUserName;
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



}