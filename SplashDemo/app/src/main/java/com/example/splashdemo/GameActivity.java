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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.splashdemo.databinding.ActivityGameBinding;

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

        gameLike.setOnClickListener(this);
        gameForum.setOnClickListener(this);
        gameComment.setOnClickListener(this);
        gameBack.setOnClickListener(this);

        gameId = getIntent().getStringExtra("gameId");
        gameId = "2";//TODO:暂时这样设置，以后改

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