package com.example.splashdemo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        WebUtil webUtil = new WebUtil(personalWebView, getContext());
        webUtil.webViewSetting("PersonalCenter");

        return view;
    }
}