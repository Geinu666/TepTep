package com.example.teptep.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teptep.GameActivity;
import com.example.teptep.R;

import java.util.List;

import WebKit.Bean.AllBean;

/**
 * 排行榜适配器
 */
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder>{
    private List<AllBean.GameBean> mgameBeanList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rankView;
        TextView gameRank;
        ImageView gameIcon;
        TextView gameScore;
        TextView gameId;
        TextView gameName;
        TextView gameCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rankView = itemView;
            gameRank = itemView.findViewById(R.id.game_rank);
            gameIcon = itemView.findViewById(R.id.game_icon);
            gameScore = itemView.findViewById(R.id.game_score);
            gameId = itemView.findViewById(R.id.game_id);
            gameName = itemView.findViewById(R.id.game_name);
            gameCategory = itemView.findViewById(R.id.rank_category);
        }
    }

    public RankAdapter(List<AllBean.GameBean> gameList) {
        this.mgameBeanList = gameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_display_rank, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        mContext = parent.getContext();
        holder.rankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GameActivity.class);
                intent.putExtra("gameId", holder.gameId.getText().toString());
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllBean.GameBean game = mgameBeanList.get(position);
        holder.gameScore.setText(String.format("%.1f", game.getAvgScore()));
        Glide.with(mContext)
                .load(game.getIcon())
                .into(holder.gameIcon);
        holder.gameId.setText(game.getGameId());
        holder.gameRank.setText(String.valueOf(position + 1));
        holder.gameName.setText(game.getName());
        if (game.getCategory() != null) {
            holder.gameCategory.setVisibility(View.VISIBLE);
            holder.gameCategory.setText(game.getCategory().replace(" ", "·"));
        } else {
            holder.gameCategory.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mgameBeanList.size();
    }
}
