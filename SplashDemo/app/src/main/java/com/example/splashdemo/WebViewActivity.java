package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private WebSettings webSettings;
    private ProgressBar progressBar;
    private String adNameNo;
    private String httpUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        adNameNo = getIntent().getStringExtra("adNameNo");
    }

    /**
     * 等待完善
     * @param adNameNo
     * @return
     */
    private String getHttpUrl(String adNameNo){
        return null;
    }
}