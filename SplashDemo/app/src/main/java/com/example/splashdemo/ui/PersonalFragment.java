package com.example.splashdemo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.example.splashdemo.LoginActivity;
import com.example.splashdemo.MainActivity;
import com.example.splashdemo.R;
import com.example.splashdemo.WebUtil;

import WebKit.Bean.LoginBean;
import WebKit.RetrofitFactory;
import WebKit.Service.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalFragment extends Fragment {
    private WebView personalWebView;
    private long exitTime = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        judgeState();

        personalWebView = (WebView) view.findViewById(R.id.personal_webView);

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

    private void judgeState(){
        LoginService service = RetrofitFactory.getLoginService(getActivity());
        Call<LoginBean> call = service.getUserMessage();
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {

            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(getContext(), "尚未登陆！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent, 100);
                getActivity().overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Boolean result = data.getExtras().getBoolean("result", false);
        if (result) {
            //登陆成功则刷新WebView
            personalWebView.reload();
        }
    }
}