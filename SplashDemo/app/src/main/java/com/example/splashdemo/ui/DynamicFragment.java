package com.example.splashdemo.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.splashdemo.R;
import com.example.splashdemo.WebUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DynamicFragment extends Fragment {

    private WebView dynamicWebView;
    private FloatingActionButton button;
    private long exitTime = 0;
    public static DynamicFragment instance = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = this;
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        Log.i("dynamicWebView", "start");
        dynamicWebView = (WebView) view.findViewById(R.id.dynamicWebView);
        button = (FloatingActionButton) view.findViewById(R.id.floating);

        button.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        WebUtil webUtil = new WebUtil(dynamicWebView, getContext());
        webUtil.webViewSetting("TimeLine", 1);
        dynamicWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                    if (dynamicWebView != null && dynamicWebView.canGoBack()){
                        dynamicWebView.goBack();
                    } else {
                        getActivity().getFragmentManager().popBackStack();
                    }
                    return true;
                }
                return false;
            }
        });
        Log.i("dynamicWebView", "loadurl");
        return view;
    }


}