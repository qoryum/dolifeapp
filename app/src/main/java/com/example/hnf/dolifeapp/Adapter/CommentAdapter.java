package com.example.hnf.dolifeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hnf.dolifeapp.Model.Comment;
import com.example.hnf.dolifeapp.R;

import java.util.List;

/**
 * Created by HNF on 4/5/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.userText.setText(comment.getUsername());
        holder.commentText.setText(comment.getMessage());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userText, commentText;

        public ViewHolder(View itemView) {
            super(itemView);
            userText = itemView.findViewById(R.id.commentUser);
            commentText = itemView.findViewById(R.id.commentMessage);
        }
    }
}
