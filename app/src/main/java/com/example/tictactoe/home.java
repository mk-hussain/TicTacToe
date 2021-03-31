package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    ListView usersList;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
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

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));//set action bar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewpager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        //setupViewPager(viewPager);
        //tabLayout.setupWithViewPager(viewPager);


        viewPagerAdapter.AddFragment(new UsersList(),"SEND REQUEST");
        viewPagerAdapter.AddFragment(new acceptRequest(),"ACCEPT REQUEST");
        viewPagerAdapter.AddFragment(new blogPage(),"BLOG");//add fragments here

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(0).setIcon(R.drawable.list);//set icon to tabs

        mRef= FirebaseDatabase.getInstance().getReference();

        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        String LoginUID=signInAccount.getId();//LoginUID IS A INTEGER EG.116171117870835209803
        String LoginUserID=signInAccount.getEmail();//this we should show some were so that user can know with which id he is logged in
        String UserName=modifiedUserName(LoginUserID);//we remove @gmail.com as firebase wont accept it  as string
        mRef.child("users").child(UserName).child("Request").setValue(LoginUID); // we must first check weather we have any request or not then update it

        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK

    }

    private String modifiedUserName(String userName) {
        //userName=kainathussain63@gmail.com,we want to remove from @ part as firebase result in error,length=24,@-com 10,index
        int lastIndex=userName.length();
        String modifiedUserName=userName.substring(0,lastIndex-10);
        return modifiedUserName;//returns kainathussain63
    }

    }
   /* private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment();ddFragment(new UsersList(),"USERS");
        viewPagerAdapter.addFragment(new blogPage(),"BLOG");

    }*/