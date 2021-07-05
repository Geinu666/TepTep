package com.example.splashdemo.utils;

import android.app.Activity;
import android.view.View;

/**
 * 此类用于设置浅色状态栏
 */
public class LightStatusBarUtils {
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(0);
        }
    }
}