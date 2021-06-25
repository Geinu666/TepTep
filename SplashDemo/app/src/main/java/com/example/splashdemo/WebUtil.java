package com.example.splashdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * WebView操作封装
 */
public class WebUtil {
    private final String baseUrl = "http://119.91.130.198/app/#/";
    private Context mcontext;
    private WebView webView;
    //从构造函数传入context

    /**
     * 封装WebView初始化操作
     *
     * @param func      vue布局名
     * @param MODE_CODE Fragment调用写0，Activity调用写1(忘了Fragment写1会怎样了🤣实际上也是写1)
     *
     */
    public void webViewSetting(String func, int MODE_CODE) {
        String address = baseUrl + func;
        webView.loadUrl(address);
        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(webSettings.LOAD_DEFAULT);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);
        if (MODE_CODE == 1) {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                    try {
                        if (url.startsWith("http:") || url.startsWith("https:")) {
                            view.loadUrl(url);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            mcontext.startActivity(intent);
                        }
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            });
        }
    }

    /**
     * 随时删的临时方法
     *
     * @param url
     * @param MODE_CODE
     */
    public void normalSurf(String url, int MODE_CODE) {
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(webSettings.LOAD_DEFAULT);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
//        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);
        //防止跳转
        if (MODE_CODE == 1) {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                    try {
                        if (url.startsWith("http:") || url.startsWith("https:")) {
                            view.loadUrl(url);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            mcontext.startActivity(intent);
                        }
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            });
        }
    }

    /**
     * 简简单单构造函数
     * @param webView
     * @param context
     */
    public WebUtil(WebView webView, Context context) {
        mcontext = context;
        this.webView = webView;
    }

    public WebUtil(WebView webView) {
        this.webView = webView;
    }
}
