package com.xinghai.volleydemo.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * author：Created by DIY on 2018/9/1.
 * function：自定义Application
 */

public class MyApplication extends Application {
    public static RequestQueue queues;

    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getQueues() {
        return queues;
    }
}
