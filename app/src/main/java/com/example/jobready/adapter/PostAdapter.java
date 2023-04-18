package com.example.jobready.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jobready.R;
import com.example.jobready.model.Post;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private List<Post> postList;
    private Context context;

    public PostAdapter(List<Post> postList, Context context){
        super(context, R.layout.list_post, postList);
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_post, null, true);

        TextView tUsername = view.findViewById(R.id.profileUsername);
        TextView tContent = view.findViewById(R.id.postContent);
        TextView tHeadline = view.findViewById(R.id.profileHeadline);

        Post post = postList.get(position);
        tUsername.setText(post.getUsername());
        tContent.setText(post.getContent());
        tHeadline.setText(post.getHeadline());

        return view;
    }
}
