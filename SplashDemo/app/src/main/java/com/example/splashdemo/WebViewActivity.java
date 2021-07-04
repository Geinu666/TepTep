package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * 用以接受各种intent跳转到WebView
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private String gameId = null;
    private String url = null;
    private String func;
    private FloatingActionButton button;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);
        //从intent里找有没有传gameId
        url = getIntent().getExtras().getString("url", null);
        gameId = getIntent().getExtras().getString("gameId", null);
        func = getIntent().getExtras().getString("func");
        button = (FloatingActionButton) findViewById(R.id.floating);

        button.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        //这个方法仅限论坛跳动态
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initPopupWindow(v);
                Intent intent = new Intent(WebViewActivity.this, WebViewActivity.class);
                intent.putExtra("url", "CreatePost/" + gameId);
                intent.putExtra("func", "CreatePost");
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
            }
        });

        if (!func.equals("GameForum")) {
            button.setVisibility(View.INVISIBLE);
        }

        webView = findViewById(R.id.tempWebView);
        WebUtil webUtil = new WebUtil(webView, getApplicationContext());
        webUtil.webViewSetting(url, 1);
        webView.addJavascriptInterface(new JsJavaBridge(this, webView, getApplicationContext()), "$App");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }
    //暂时没用
    public void initPopupWindow(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_style, null, false);
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        //设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //偏移量
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        //
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, screenWidth - 100, screenHeight - 600);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //popupwindow的工具，暂时没用
    public void backgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;//0.0-1.0
        getWindow().setAttributes(lp);
    }
}