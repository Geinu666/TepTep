package com.example.splashdemo.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import com.example.splashdemo.entity.advertisement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Response;

/**
 * 网络请求获取最新开屏资源
 */
public class updateAdvertisement {
    private advertisement latestAd;
    private String currentVersion;
    private String latestVersion;
    private String imageUrl;

    private static final int LOAD_SUCCESS = 1;
    public static final int LOAD_ERROR = -1;
    private Handler handler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            switch (msg.what){
                case LOAD_SUCCESS:
                    Log.i("LOAD_SUCCESS", "图片下载成功");
                    break;
                case LOAD_ERROR:
                    Log.i("LOAD_ERROR", "图片下载失败");
                    break;
            }
        }
    };

    public void getLatestVersion(Context context, Response<List<advertisement>> response){
        //从SharedPreferences获取现有版本
        currentVersion = SharedPreferencesUtil
                .get(context, "version", "0").toString();
        Log.i("currentVersion", currentVersion);

        if (response.body().size() != 0){
            latestAd = response.body().get(response.body().size()-1);
            latestVersion = latestAd.getVersion();
            Log.i("latestVersion", latestVersion);
            //有新的广告则更新SharedPreferences
            if (!latestVersion.equals(currentVersion)){
                SharedPreferencesUtil.put(context, "updateTime", latestAd.getUpdateTime());
                SharedPreferencesUtil.put(context, "version", latestAd.getVersion());
                SharedPreferencesUtil.put(context, "imageUrl", latestAd.getImageUrl());
                SharedPreferencesUtil.put(context, "httpUrl", latestAd.getHttlUrl());
                imageUrl = latestAd.getImageUrl();
                //子线程加载图片
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getPicture();
                    }
                }).start();
            }
        }
        return;
    }

    /**
     * 获取头图
     */
    private void getPicture(){
        URL url = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            //用于获取头图的url
            url = new URL(imageUrl);
            //开启连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            //连接成功
            if(conn.getResponseCode() == 200){
                //连接图片输入流
                is = conn.getInputStream();
                //构造File对象存储图片
                File file = new File(Environment.getExternalStorageDirectory(), "adImage.jpg");
                fos = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1){
                    fos.write(buffer, 0, len);
                }
                fos.flush();
                handler.sendEmptyMessage(LOAD_SUCCESS);
            }
        } catch (Exception e){
            handler.sendEmptyMessage(LOAD_ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (is != null){
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e){
                handler.sendEmptyMessage(LOAD_ERROR);
                e.printStackTrace();
            }
        }
    }
}
