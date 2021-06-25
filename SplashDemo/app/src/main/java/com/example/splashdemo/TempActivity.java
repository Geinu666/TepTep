package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.splashdemo.R;
import com.example.splashdemo.WebUtil;

/**
 * 仅用于测试用的Activity类
 */
public class TempActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        webView = findViewById(R.id.tempWebView);
        WebUtil webUtil = new WebUtil(webView, getApplicationContext());
        webUtil.webViewSetting("PersonalCenter", 1);
    }
}