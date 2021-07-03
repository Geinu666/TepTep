package com.example.splashdemo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import WebKit.Bean.AllBean;

public class GameAdapter extends BaseQuickAdapter<AllBean.GameBean, BaseViewHolder> {
    private Context context;
    public GameAdapter(int layoutResId, @Nullable List<AllBean.GameBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, AllBean.GameBean game) {
        baseViewHolder.setText(R.id.rv_item_game_name, game.getName())
                .setText(R.id.rv_item_game_score, String.format("%.1f", game.getAvgScore()))
                .setText(R.id.rv_item_game_id, game.getGameId())
                .setText(R.id.rv_item_game_description, game.getBriefIntro());
        MaterialButton sortBtn = baseViewHolder.getView(R.id.rv_item_game_sort);
        sortBtn.setVisibility(View.GONE);
//        MaterialButton rankBtn = baseViewHolder.getView(R.id.rv_item_game_rank);
//        if (game.getRank() != null) {
//            rankBtn.setVisibility(View.VISIBLE);
//            rankBtn.setText(game.getRank());
//        } else {
//            rankBtn.setVisibility(View.GONE);
//        }
        //加载大图
        ImageView gameImg = baseViewHolder.getView(R.id.rv_item_game_img);
        Glide.with(getContext())
                .load(game.getDisplayDrawings())
                .into(gameImg);
    }
}

