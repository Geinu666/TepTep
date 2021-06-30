package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.nio.channels.InterruptedByTimeoutException;

/**
 * 显示游戏信息的界面
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private String gameId;
    private ImageView gameIcon;
    private RelativeLayout gameForum;
    private RelativeLayout gameLike;
    private RelativeLayout gameComment;
    private ImageView gameBack;
    private RatingBar ratingBar;
    private TextView gameName;
    private float score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);

        gameIcon = findViewById(R.id.game_icon);
        gameForum = findViewById(R.id.game_forum);
        gameLike = findViewById(R.id.game_like);
        gameComment = findViewById(R.id.game_comment);
        gameBack = findViewById(R.id.game_back);
        ratingBar = findViewById(R.id.rating_bar);
        gameName = findViewById(R.id.game_name);

        gameLike.setOnClickListener(this);
        gameForum.setOnClickListener(this);
        gameComment.setOnClickListener(this);
        gameBack.setOnClickListener(this);

        score = getIntent().getFloatExtra("score", 0);
        gameId = getIntent().getStringExtra("gameId");
        score = (float) 9.9 / 2;
        gameId = "2";//TODO:暂时这样设置，以后改

        ratingBar.setRating(score);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.i("test", String.valueOf(rating * 2));
                Intent intent1 = new Intent(GameActivity.this, CommentActivity.class);
                intent1.putExtra("icon", "")
                        .putExtra("rating", rating)
                        .putExtra("gameId", gameId)
                        .putExtra("gameName", gameName.getText().toString());
                startActivity(intent1);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
            }
        });

        Glide.with(getApplicationContext())
                .load("https://dss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/c7540fa48a64bbaadb88ce07f0d6a1bb_264_264.jpg")
                .into(gameIcon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.game_like:
                break;
            case R.id.game_comment:
                break;
            case R.id.game_forum:
                Log.i("test", "onclick");
                Intent intent = new Intent(GameActivity.this, WebViewActivity.class);
                intent.putExtra("url", "GameForum/" + gameId);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
                break;
            case R.id.game_back:
                finish();
                break;

        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }
}