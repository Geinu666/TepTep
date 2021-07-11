package com.example.splashdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.splashdemo.utils.JsJavaBridge;
import com.example.splashdemo.utils.LightStatusBarUtils;
import com.example.splashdemo.utils.WebUtil;
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
    private WebViewActivity instance;

    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        instance = this;
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

        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                uploadFile = uploadFile;
                openFileChooseProcess();
            }
        });

        webView.addJavascriptInterface(new JsJavaBridge(instance, webView, getApplicationContext()), "$App");
    }

    /**
     * 选择文件
     */
    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "选择文件"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (uploadFile != null) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (uploadFiles != null) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (uploadFile != null) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }
            if (uploadFiles != null) {
                uploadFiles.onReceiveValue(null);
                uploadFiles = null;
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}