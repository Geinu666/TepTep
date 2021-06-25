package com.example.splashdemo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splashdemo.JsJavaBridge;
import com.example.splashdemo.MainActivity;
import com.example.splashdemo.R;
import com.example.splashdemo.WebUtil;


public class PersonalFragment extends Fragment {
    private WebView personalWebView;
    private long exitTime = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        personalWebView = (WebView) view.findViewById(R.id.personal_webView);
        /*        WebSettings settings = personalWebView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setBuiltInZoomControls(true);
//
//        settings.setDisplayZoomControls(false);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        personalWebView.loadUrl("http://119.91.130.198/app/#/PersonalCenter");
//        personalWebView.setWebViewClient(new WebViewClient());

        settings.setSupportZoom(true);**/
        WebUtil webUtil = new WebUtil(personalWebView, getContext());
        webUtil.webViewSetting("PersonalCenter", 0);
        personalWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                    if (personalWebView != null && personalWebView.canGoBack()){
                        personalWebView.goBack();
                    } else {
                        getActivity().getFragmentManager().popBackStack();
                    }
                    return true;
                }
                return false;
            }
        });
        //给Vue调用
        personalWebView.addJavascriptInterface(new JsJavaBridge(getActivity(), personalWebView), "$App");
        return view;
    }
}