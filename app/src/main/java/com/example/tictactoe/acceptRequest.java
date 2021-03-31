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

    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public acceptRequest() {
        // Required empty public constructor
    }
    public static acceptRequest newInstance(String param1, String param2) {
        acceptRequest fragment = new acceptRequest();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_accept_request, container, false);

        lv_acceptRequest=view.findViewById(R.id.lv_acceptRequest);
        mArrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list_acceptRequest);
        lv_acceptRequest.setAdapter(mArrayAdapter);


        //mArrayAdapter.clear();//this clears previous values,items add code must be below this
        //list_acceptRequest.add("Kainat");
        //list_acceptRequest.add("Uzma");
        //AcceptRequests();

        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(getActivity());
        String LoginUID=signInAccount.getId();//LoginUID IS A INTEGER EG.116171117870835209803
        String LoginUserID=signInAccount.getEmail();//this we should show some were so that user can know with which id he is logged in
        String UserName=modifiedUserName(LoginUserID);//we remove @gmail.com as firebase wont accept it  as string

       /* mRef.child("users").child(UserName).child("Request").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key=snapshot.getKey();
                String value=snapshot.child(key).getValue().toString();
                mArrayAdapter.add(value);
                mArrayAdapter.notifyDataSetChanged();
                //mRef.child("users").child(UserName).child("request").setValue(LoginUID);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        mRef.child("users").child(UserName).child("Request").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String key;//we are only worried about push ids as it contain the username to which request is send
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
                        if (size!=0){
                            for (int j=0;j<size;j++)
                            {
                                mRef.child("users").child(UserName).child("Request").child(al.get(j)).addListenerForSingleValueEvent(new ValueEventListener() {
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


                          // Map<String,Object> map =  (Map<String, Object>) dataSnapshot.getValue();
                        //Map<String,Object> map=new HashMap<>();
                        //map= (Map<String, Object>) dataSnapshot.getValue();
                           /*if(map != null){
                                String value = "";
                                for(String key:map.keySet()){
                                    value = (String) map.get(key);
                                    mArrayAdapter.add(value);
                                    mArrayAdapter.notifyDataSetChanged();
                                    mRef.child("users").child(UserName).child("request").setValue(LoginUID);
                                }
                            }*/
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

        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(getActivity());
        String LoginUserID=signInAccount.getEmail();
        String Username=signInAccount.getEmail();
        String UserName=modifiedUserName(Username);
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
        //userName=kainathussain63@gmail.com,we want to remove from @ part as firebase result in error,length=24,@-com 10,index
        int lastIndex=userName.length();
        String modifiedUserName=userName.substring(0,lastIndex-10);
        return modifiedUserName;//returns kainathussain63
    }

    private void StartGame(String PlayerGameID, String OtherPlayer, String requestType){
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(getActivity());
        String Username=signInAccount.getEmail();
        String UserName=modifiedUserName(Username);
        String LoginUID=signInAccount.getId();
        mRef.child("playing").child(PlayerGameID).removeValue();
        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.putExtra("player_session", PlayerGameID);
        intent.putExtra("user_name", UserName);
        intent.putExtra("other_player", OtherPlayer);
        intent.putExtra("login_uid", LoginUID);
        intent.putExtra("request_type", requestType);
        startActivity(intent);
    }



}