package com.example.teptep.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.teptep.utils.JsJavaBridge;
import com.example.teptep.LoginActivity;
import com.example.teptep.MainActivity;
import com.example.teptep.R;
import com.example.teptep.utils.WebUtil;

import WebKit.Bean.LoginBean;
import WebKit.RetrofitFactory;
import WebKit.Service.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DynamicFragment extends Fragment {

    private WebView dynamicWebView;
    public static DynamicFragment instance = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        judgeState();
        instance = this;

        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);

        dynamicWebView = (WebView) view.findViewById(R.id.dynamicWebView);

        WebUtil webUtil = new WebUtil(dynamicWebView, getContext());
        webUtil.webViewSetting("TimeLine", 1);

        //好像没有效果的监听返回
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
        dynamicWebView.addJavascriptInterface(new JsJavaBridge(getActivity(), dynamicWebView, getContext()), "$App");
        return view;
    }

    /**
     * 检测登陆状态
     * 未登录则跳转
     */
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
            dynamicWebView.reload();
        } else {
            //未登录则跳转首页
            MainActivity.instance.jumpToItem(R.id.navigation_home);
        }
    }


}