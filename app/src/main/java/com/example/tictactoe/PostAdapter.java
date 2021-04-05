package com.example.tictactoe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PostAdapter extends FirebaseRecyclerAdapter<Blog, PostAdapter.BlogViewHolder> {


    public PostAdapter(@NonNull FirebaseRecyclerOptions<Blog> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull Blog post) {
        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
        holder.username.setText(post.getUserName());
        Picasso.get().load(post.getImage()).into(holder.post_image);
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row,parent,false);
        return new BlogViewHolder(view);
    }

    class BlogViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,username;
        ImageView post_image;
        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.post_title);
            description=itemView.findViewById(R.id.post_text);
            username=itemView.findViewById(R.id.userName);
            post_image=itemView.findViewById(R.id.post_image);
        }
        }
    }

