package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class blogPage extends Fragment {
    View view;
    FloatingActionButton fab;
    private FirebaseDatabase mDatabase;// gives reference of whole database
    private DatabaseReference mRef;//give ref of specific area of database here parent node
    private RecyclerView mBlogList;
    private PostAdapter postAdapter;

    @Override
    public void onStart() {
        super.onStart();
        postAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        postAdapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.fragment_blog_page, container, false);

        mBlogList=view.findViewById(R.id.recyclerView);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab=view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),postActivity.class);
                startActivity(i);
            }
        });

        mRef=FirebaseDatabase.getInstance().getReference().child("Blog");
        FirebaseRecyclerOptions<Blog> options= new FirebaseRecyclerOptions.Builder<Blog>().setQuery(mRef,Blog.class).build();
        postAdapter=new PostAdapter(options);
        mBlogList.setAdapter(postAdapter);

        return view;
    }

}