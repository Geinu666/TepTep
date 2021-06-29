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
    private ActivityGameBinding binding;
    private ImageView forumImage;
    private TextView forumText;
    private Button testbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);
        gameIcon = binding.gameIcon;
        forumImage = binding.forumIcon;
        forumText = binding.forumText;
        testbutton = binding.testbutton;
        gameId = getIntent().getStringExtra("gameId");
        gameId = "2";
        Log.i("test", "before glide");
        Glide.with(getApplicationContext())
                .load("https://dss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/c7540fa48a64bbaadb88ce07f0d6a1bb_264_264.jpg")
                .into(gameIcon);
        Log.i("test", "after glide");
    }

    /**
     * 等待完善
     * @param gameId
     * @return
     */
    private String getHttpUrl(String gameId){

        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forum_icon:
            case R.id.forum_text:
            case R.id.testbutton:
                Log.i("test", "onclick");
                Intent intent = new Intent(GameActivity.this, WebViewActivity.class);
                intent.putExtra("url", "GameForum/" + gameId);
                startActivity(intent);
                break;
        }
    }
}