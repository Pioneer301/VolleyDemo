package com.xinghai.volleydemo;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.xinghai.volleydemo.application.MyApplication;

import java.util.Map;

/**
 * author：Created by DIY on 2018/9/1.
 * function：封装的VolleyRequest请求
 */

public class VolleyRequest {
    public static StringRequest request;
    public static Context mContext;

    public static void RequestGet(Context mContext, String url, String tag,VolleyInterface vif) {
        MyApplication.getQueues().cancelAll(tag);
        //创建StringRequest对象时，传入两个回调接口
        request = new StringRequest(Request.Method.GET, url, vif.succeedListener(),vif.errorListener);
        request.setTag(tag);
        MyApplication.getQueues().add(request);
    }

    public static void RequestPost(Context mContext, String url, String tag, final Map<String,String> params, VolleyInterface vif) {
        MyApplication.getQueues().cancelAll(tag);
        //创建StringRequest对象时，传入两个回调接口，以及重写getParams()方法
        request = new StringRequest(Request.Method.POST,url,vif.succeedListener(),vif.errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        request.setTag(tag);
        MyApplication.getQueues().add(request);
    }
}
