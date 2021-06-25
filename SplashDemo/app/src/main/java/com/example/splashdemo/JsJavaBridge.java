package com.example.splashdemo;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * 通过WebView与前端交互的类，
 * 封装一些函数供Vue使用
 */
public class JsJavaBridge {

    private Activity activity;
    private WebView webView;

    public JsJavaBridge(Activity activity, WebView webView){
        this.activity = activity;
        this.webView = webView;
    }

    @JavascriptInterface
    public void onFinishActivity(){
        activity.finish();
    }

    @JavascriptInterface
    public void showToast(String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
