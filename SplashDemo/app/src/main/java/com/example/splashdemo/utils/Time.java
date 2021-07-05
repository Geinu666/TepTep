package com.example.splashdemo.utils;

import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用来将Timestamp转成人话的类
 */
public class Time {
    public static String CalculateTime(long time){
        long nowTime=System.currentTimeMillis();  //获取当前时间的毫秒数
        String msg = null;
        Timestamp t = new Timestamp(time);
        Log.i("test", t.toString());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//指定时间格式
        Date setTime = null;  //指定时间
        setTime = t;
        Log.i("test", setTime.toString());

        long reset=setTime.getTime();   //获取指定时间的毫秒数
        long dateDiff=nowTime-reset;

        Log.i("test", String.valueOf(dateDiff));

        if(dateDiff<0){
            msg="刚刚";
        }else{

            long dateTemp1=dateDiff/1000; //秒
            long dateTemp2=dateTemp1/60; //分钟
            long dateTemp3=dateTemp2/60; //小时
            long dateTemp4=dateTemp3/24; //天数
            long dateTemp5=dateTemp4/30; //月数
            long dateTemp6=dateTemp5/12; //年数

            if(dateTemp6>0){
                msg = dateTemp6+"年前";

            }else if(dateTemp5>0){
                msg = dateTemp5+"个月前";

            }else if(dateTemp4>0){
                msg = dateTemp4+"天前";

            }else if(dateTemp3>0){
                msg = dateTemp3+"小时前";

            }else if(dateTemp2>0){
                msg = dateTemp2+"分钟前";

            }else if(dateTemp1>0){
                msg = "刚刚";

            }
        }
        return msg;

    }
}
