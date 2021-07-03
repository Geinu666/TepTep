package com.example.splashdemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shehuan.niv.NiceImageView;

import java.util.List;

import WebKit.Bean.LikeBean;
import WebKit.RetrofitFactory;
import WebKit.Service.CommentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> mcommentList;
    private Context mContext;
    private String userId;
    private String gameName;
    private String iconUrl;
    private float commentScore;

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
        TextView commentId;
        TextView commentWriterId;
        RelativeLayout commentEdit;
        TextView commentGameId;
        TextView commentGameName;

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
            commentId = itemView.findViewById(R.id.comment_id);
            commentWriterId = itemView.findViewById(R.id.comment_writer_id);
            commentEdit = itemView.findViewById(R.id.comment_edit);
            commentGameId = itemView.findViewById(R.id.comment_game_id);
            commentGameName = itemView.findViewById(R.id.comment_game_name);
        }
    }

    public CommentAdapter(List<Comment> commentList, String userId, String gameName, String iconUrl) {
        this.mcommentList = commentList;
        this.userId = userId;
        this.gameName = gameName;
        this.iconUrl = iconUrl;
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
            }
        });

        holder.commentLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //没登陆?
                if (userId == null) {
                    Toast.makeText(mContext, "尚未登陆！", Toast.LENGTH_SHORT).show();
                } else {
                    int position = holder.getAdapterPosition();
                    Comment comment = mcommentList.get(position);
                    String commentId = comment.getCommentId();
                    CommentService service = RetrofitFactory.getCommentService(mContext);
                    Call<LikeBean> call = service.postLikeGame(commentId);
                    call.enqueue(new Callback<LikeBean>() {
                        @Override
                        public void onResponse(Call<LikeBean> call, Response<LikeBean> response) {
                            if (response.isSuccessful()) {
                                LikeBean result = response.body();
                                if (result != null) {
                                    if (result.getData().isLikes()) {
                                        holder.commentLike.setImageResource(R.drawable.baseline_favorite_24);
                                        int i = Integer.parseInt(holder.commentLikeCount.getText().toString());
                                        holder.commentLikeCount.setText(String.valueOf(i + 1));
                                    } else {
                                        holder.commentLike.setImageResource(R.drawable.baseline_favorite_border_24);
                                        int i = Integer.parseInt(holder.commentLikeCount.getText().toString());
                                        holder.commentLikeCount.setText(String.valueOf(i - 1));
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LikeBean> call, Throwable t) {
                            Toast.makeText(mContext, "点赞失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
//                likeComment(commentId);
                }
            }
        });

        holder.commentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("gameId", holder.commentGameId.getText().toString());
                intent.putExtra("gameName", gameName);
                intent.putExtra("iconUrl", iconUrl);
                intent.putExtra("userId", userId);
                intent.putExtra("content", holder.commentContent.getText().toString());
                intent.putExtra("rating", commentScore / 2);
                intent.putExtra("isChange", true);
                intent.putExtra("commentId", holder.commentId.getText().toString());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mcommentList.get(position);
        Glide.with(mContext)
                .load(comment.getAvatar() == null?"https://img.taplb.com/md5/22/f1/22f1196f825298281376608459bfa7fe": comment.getAvatar())
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

        holder.commentId.setText(comment.getCommentId());
        holder.commentWriterId.setText(comment.getUserId());
        if (comment.getUserId().equals(userId)) {
            holder.commentEdit.setVisibility(View.VISIBLE);
        } else {
            holder.commentEdit.setVisibility(View.INVISIBLE);
        }
        holder.commentGameId.setText(comment.getGameId());
        holder.commentGameName.setText(gameName);
        commentScore = comment.getScore();
    }

    @Override
    public int getItemCount() {
        return mcommentList.size();
    }

    // TODO: 2021/7/2 写点赞评论
    public void likeComment(String commentId){
        CommentService service = RetrofitFactory.getCommentService(mContext);
        Call<LikeBean> call = service.postLikeGame(commentId);
        call.enqueue(new Callback<LikeBean>() {
            @Override
            public void onResponse(Call<LikeBean> call, Response<LikeBean> response) {
                if (response.isSuccessful()) {
                    LikeBean result = response.body();
                    if (result.getData().isLikes()) {

                    }
                }
            }

            @Override
            public void onFailure(Call<LikeBean> call, Throwable t) {
                Toast.makeText(mContext, "点赞失败！", Toast.LENGTH_SHORT).show();
            }
        });
    };
}
