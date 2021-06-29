package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 显示游戏信息的界面
 */
public class GameActivity extends AppCompatActivity {
    private WebView webView;
    private WebSettings webSettings;
    private ProgressBar progressBar;
    private String gameId;
    private String httpUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameId = getIntent().getStringExtra("gameId");
    }

    /**
     * 等待完善
     * @param gameId
     * @return
     */
    private String getHttpUrl(String gameId){

        return null;
    }
}