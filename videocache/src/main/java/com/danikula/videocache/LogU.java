package com.danikula.videocache;

import android.util.Log;

public class LogU {

    public static void d(String msg){
        Log.d("hapivideocache",msg);
    }
    public static void d(String msg,Throwable e){
        Log.d("hapivideocache",msg+e.getMessage());  }

    public static void e(String msg,Throwable e){
        Log.e("hapivideocache",msg+e.getMessage());  }
    public static void e(String msg){
        Log.e("hapivideocache",msg);  }
}
