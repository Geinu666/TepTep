package com.example.splashdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.splashdemo.MainActivity;
import com.example.splashdemo.R;
import com.example.splashdemo.WebViewActivity;

import WebKit.CookieUtil;

/**
 * 通过WebView与前端交互的类，
 * 封装一些函数供Vue使用
 */
public class JsJavaBridge {

    private Activity activity;
    private WebView webView;
    private Context context;

    public JsJavaBridge(Activity activity, WebView webView, Context context){
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

    @JavascriptInterface
    public void toForum(String gameId) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", "GameForum/" + gameId);
        intent.putExtra("gameId", gameId);
        intent.putExtra("func", "GameForum");
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
    }

    @JavascriptInterface
    public void logout() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CookieUtil.removeCookie(activity);
                SharedPreferences sp = activity.getSharedPreferences("cookieData", activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("cookie");
                editor.commit();
                ((MainActivity) activity).jumpToItem(R.id.navigation_home);
            }
        });
    }

    @JavascriptInterface
    public void createPostQuit(){
        activity.finish();
    }
}
