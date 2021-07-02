package com.example.splashdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shehuan.niv.NiceImageView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> mcommentList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View commentView;
        NiceImageView avatar;
        TextView commentNickname;
        TextView commentLastTime;
        ImageView starOne;
        ImageView starTwo;
        ImageView starThree;
        ImageView starFour;
        ImageView starFive;
        TextView commentContent;
        ImageView commentLike;
        TextView commentLikeCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentView = itemView;
            avatar = itemView.findViewById(R.id.comment_user_avatar);
            commentNickname = itemView.findViewById(R.id.comment_user_nickname);
            commentLastTime = itemView.findViewById(R.id.comment_last_time);
            starOne = itemView.findViewById(R.id.star_one);
            starTwo = itemView.findViewById(R.id.star_two);
            starThree = itemView.findViewById(R.id.star_three);
            starFour = itemView.findViewById(R.id.star_four);
            starFive = itemView.findViewById(R.id.star_five);
            commentContent = itemView.findViewById(R.id.comment_content);
            commentLike = itemView.findViewById(R.id.comment_like);
            commentLikeCount = itemView.findViewById(R.id.comment_like_count);
        }
    }

    public CommentAdapter(List<Comment> commentList) {
        this.mcommentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_cardview, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        mContext = parent.getContext();
        holder.commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Comment comment = mcommentList.get(position);
                Log.i("test", "comment被你点到啦" + comment.getContent());
            }
        });

        holder.commentLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeComment();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mcommentList.get(position);
        Glide.with(mContext)
                .load(comment.getAvatar())
                .into(holder.avatar);
        holder.commentNickname.setText(comment.getNickname());
        //commentLastTime
        holder.commentContent.setText(comment.getContent());
        //commentLike
        holder.commentLikeCount.setText(String.valueOf(comment.getLikesCount()));
        //经典回顾之假造星星
        int score = comment.getScore() / 2;
        if (score <= 1.0) {
            holder.starTwo.setVisibility(View.INVISIBLE);
            holder.starThree.setVisibility(View.INVISIBLE);
            holder.starFour.setVisibility(View.INVISIBLE);
            holder.starFive.setVisibility(View.INVISIBLE);
        } else if (score <= 2.0) {
            holder.starThree.setVisibility(View.INVISIBLE);
            holder.starFour.setVisibility(View.INVISIBLE);
            holder.starFive.setVisibility(View.INVISIBLE);
        } else if (score <= 3.0) {
            holder.starFour.setVisibility(View.INVISIBLE);
            holder.starFive.setVisibility(View.INVISIBLE);
        } else if (score <= 4.0) {
            holder.starFive.setVisibility(View.INVISIBLE);
        }
        if (comment.getLike()) {
            holder.commentLike.setImageResource(R.drawable.baseline_favorite_24);
        }
    }

    @Override
    public int getItemCount() {
        return mcommentList.size();
    }

    // TODO: 2021/7/2 写点赞评论
    public void likeComment(){};
}
