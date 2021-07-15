package com.example.teptep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teptep.utils.SharedPreferencesUtil;

import java.io.File;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ad_image;
    private ImageView bottom_image;
    private Button ad_timer;
    private Integer ms = 4;//4
    private CountDownTimer textTimer;
    private Intent it;
    String adGameId;
    public static SplashActivity instance = null;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //全屏
        setContentView(R.layout.activity_splash);
        ad_image = (ImageView) findViewById(R.id.ad_image);
        ad_timer = (Button) findViewById(R.id.ad_timer);
        bottom_image = (ImageView) findViewById(R.id.bottom_image);
        ad_image.setOnClickListener(this);
        ad_timer.setOnClickListener(this);
        bottom_image.setOnClickListener(this);

        instance = this;
        //获取首图的游戏id
        adGameId = SharedPreferencesUtil.get(SplashActivity.this,
                "adGameId",
                "")
                .toString();

        File PicFile = new File(Environment.getExternalStorageDirectory(), "/adImage.jpg");
        if (PicFile.exists()){
            ad_image.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/adImage.jpg"));
            //ad_image.setImageURI(Uri.fromFile(PicFile));
        } else {
            ad_image.setImageResource(R.mipmap.welcome);
            //加载默认图
            SharedPreferencesUtil.put(SplashActivity.this, "version", "0");
        }

        verifyStoragePermissions(SplashActivity.this);
        adCountDown();
        //检测最近一次登陆时间，如果3分钟以内不显示开屏
//        long i = (long) SharedPreferencesUtil.get(SplashActivity.this, "latestLogin", System.currentTimeMillis() / 60000);
//        long j = System.currentTimeMillis() / 60000;
//        SharedPreferencesUtil.put(SplashActivity.this, "latestLogin", j);
//        if (j - i < 3) {
//            textTimer.cancel();
//            it = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(it);
//            Log.i("test", "最近登陆过");
//            finish();
//        }
        verifyNetwork();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ad_image:
                if (!adGameId.equals("")) {
                    textTimer.cancel();
                    it = new Intent(SplashActivity.this, GameActivity.class);
                    it.putExtra("gameId", adGameId);
                    startActivity(it);
                }
                break;
            case R.id.ad_timer:
            case R.id.bottom_image:
                textTimer.cancel();
                goNextActivity();
                break;
        }
    }

    /**
     * 动态获取读写权限
     * @param activity 开屏Activity
     */
    public static void verifyStoragePermissions(Activity activity){
        try {
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //没权限才获取
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 倒计时实现
     */
    public void adCountDown(){
        textTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ms -= 1;
                ad_timer.setText("跳过 " + ms + " s");
            }

            @Override
            public void onFinish() {
                Log.i("countDown finish", "goNextActivity");
                goNextActivity();
            }
        }.start();
    }

    /**
     * 联网检验
     */
    public void verifyNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ((networkInfo == null) || !networkInfo.isConnected()) {
            Toast.makeText(SplashActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Log.i("当前网络可用", networkInfo.toString());
        }
    }

    /**
     * 跳转主界面的转移
     */
    public void goNextActivity(){
        it = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}