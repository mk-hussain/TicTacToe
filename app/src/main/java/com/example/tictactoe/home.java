package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home extends AppCompatActivity {
    TextView name,mail;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        name=findViewById(R.id.name);
        mail=findViewById(R.id.mail);
        logout=findViewById(R.id.logout);
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null)
        {
            name.setText(signInAccount.getDisplayName());

            mail.setText(signInAccount.getEmail());
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent in=new Intent(getApplicationContext(),login_activity.class);
        startActivity(in);
    }
}