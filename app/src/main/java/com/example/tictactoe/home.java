package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class home extends AppCompatActivity {
    TextView name,mail;
    ListView usersList;
    ArrayList<String> myArrayList=new ArrayList<>();
    DatabaseReference mRef;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.playWithBot:
                Intent i=new Intent(this,playWithBot.class);
                startActivity(i);
                Toast.makeText(this, "PLAY!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent in=new Intent(getApplicationContext(),login_activity.class);
                startActivity(in);
            case R.id.doublePlayer:
                Intent i2=new Intent(home.this,offline_Multiplayer.class);
                startActivity(i2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRef= FirebaseDatabase.getInstance().getReference();
        //name=findViewById(R.id.name);
        //mail=findViewById(R.id.mail);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null)
        {
           // name.setText(signInAccount.getDisplayName());

            //mail.setText(signInAccount.getEmail());
        }
    }

    public void blogOpen(View view) {
        Intent i=new Intent(home.this,blog.class);
        startActivity(i);
    }
}