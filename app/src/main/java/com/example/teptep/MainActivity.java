package com.example.teptep;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.PopupWindow;

import com.example.teptep.databinding.ActivityMainBinding;
import com.example.teptep.utils.LightStatusBarUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {
    private ActivityMainBinding binding;
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

        if (SplashActivity.instance != null) {
            SplashActivity.instance.finish();
        }
    }

    /**
     * 处理Fragment中的点击事件
     * @param v 控件实例
     */
    public void onClick(View v){

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

    @Override
    public void onResume(){
        super.onResume();
        CookieSyncManager.createInstance(getApplicationContext());
        CookieSyncManager.getInstance().startSync();
    }
    //操作切换Fragment
    public void jumpToItem(int id) {
        binding.navView.findViewById(id).performClick();
    }
}