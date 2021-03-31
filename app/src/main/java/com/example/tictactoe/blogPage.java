package com.example.tictactoe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class blogPage extends Fragment {
    View view;
    private TextView tv;
    private EditText et;
    private Button send;
    private FirebaseDatabase mDatabase;// gives reference of whole database
    private DatabaseReference mRef;//give ref of specific area of database here parent node


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public blogPage() {
        // Required empty public constructor
    }

    public static blogPage newInstance(String param1, String param2) {
        blogPage fragment = new blogPage();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment -
        view= inflater.inflate(R.layout.fragment_blog_page, container, false);


        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();
        // no input means provide ref to parent node if provided path like"child/users",ref will be of that if not exist will be created
        send=view.findViewById(R.id.send);
        et=view.findViewById(R.id.editText);
        tv=view.findViewById(R.id.tv);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=et.getText().toString();
              //  mRef.setValue(text);//this will insert value to parent key   tic-tac-toe-a77af-default-rtdb: like this
                mRef.child("users").setValue(text);
                //mRef is pointing to a object not a string

                // key value pair in firebase is handled by hashmap
                mRef.child("users").addValueEventListener(new ValueEventListener() {  //this has a single value when is changed this is called

                    Map<String,Object> data;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       // triggered 1st time+data change
                        // get whole data in snapshot lika a photo
                       // data= (Map<String, Object>) snapshot.getValue();
                        //String name=data.get("Name").toString();
                        tv.setText(text);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //error handling here

                    }
                });

            }
        });




        return view;
    }
}