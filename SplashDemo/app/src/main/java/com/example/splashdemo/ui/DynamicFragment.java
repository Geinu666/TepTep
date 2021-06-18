package com.example.splashdemo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.splashdemo.R;

public class DynamicFragment extends Fragment {

    private WebView webView;
    private long exitTime = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        Log.i("webView", "start");
        webView = (WebView) view.findViewById(R.id.dynamicWebView);
        webView.loadUrl("http://www.baidu.com");
        webView.setWebChromeClient(new WebChromeClient() {
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });


        webView.setWebViewClient(new WebViewClient() {
            //在webView里打开新链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return inflater.inflate(R.layout.fragment_dynamic, container, false);
    }
}