package com.example.splashdemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.splashdemo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import WebKit.AddCookiesInterceptor;
import WebKit.Bean.LoginBean;
import WebKit.Service.LoginService;
import WebKit.ReceivedCookiesInterceptor;
import me.yokeyword.fragmentation.SupportActivity;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends SupportActivity {
    private ActivityMainBinding binding;
    private boolean isEx = false;
    private PopupWindow popupWindow;
    public static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        instance = this;

        setContentView(binding.getRoot());
        //设置浅色主题状态栏(Android6以上原生)
        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dynamic, R.id.navigation_personal)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
        //不是很懂，人已经晕了
        setNavigationItemView(findViewById(R.id.navigation_home));
        setNavigationItemView(findViewById(R.id.navigation_rank));
        setNavigationItemView(findViewById(R.id.navigation_dynamic));
        setNavigationItemView(findViewById(R.id.navigation_personal));

        SplashActivity.instance.finish();

    }

    /**
     * 处理Fragment中的点击事件
     * @param v 控件实例
     */
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.newActivity:
                Log.i("press", "true");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
                break;
            case R.id.floating:
                initPopupWindow(v);
                Log.i("floating", "clicked");
                break;
            case R.id.http_test:
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new AddCookiesInterceptor(getApplicationContext()))
                        .addInterceptor(new ReceivedCookiesInterceptor(getApplicationContext()))
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://119.91.130.198/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                LoginService service = retrofit.create(LoginService.class);
                Call<LoginBean> call = service.postLogin("test001", "test001");
                call.enqueue(new Callback<LoginBean>() {
                    @Override
                    public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                        if (response.isSuccessful()) {
                            LoginBean bean = response.body();
                            if (bean != null) {
                                Log.i("httptest", bean.getMsg());
                                Log.i("httptest", String.valueOf(bean.getStatus()));
                                Log.i("httptest", bean.getData().toString());
                            }
                            Headers headers = response.headers();
                            Log.i("header", headers.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginBean> call, Throwable t) {

                    }
                });
                break;
            case R.id.getGame:
                Intent intent1 = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent1);
                break;
        }
    }

    /**
     * 弹PopupWindow时设置背景用(暂时没用)
     * @param bgAlpha 1代表原来的透明度
     */
    public void backgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;//0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 取消底部按钮的长按弹字
     * @param view BottomNavigationItemView实例
     */
    public void setNavigationItemView(BottomNavigationItemView view){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }
    /**
     * 初始化设置DynamicFragment里面的弹出按钮
     * @param v
     */
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
                Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        CookieSyncManager.createInstance(getApplicationContext());
        CookieSyncManager.getInstance().startSync();
    }

    public void jumpToItem(int id) {
        binding.navView.findViewById(R.id.navigation_home).performClick();
    }


}