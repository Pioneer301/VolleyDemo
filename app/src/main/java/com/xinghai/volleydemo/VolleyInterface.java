package com.xinghai.volleydemo;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * author：Created by DIY on 2018/9/1.
 * function：封装Listener和ErrorListener
 */

public abstract class VolleyInterface {
    public Context mContext;
    public static Response.Listener listener;
    public static Response.ErrorListener errorListener;

    public abstract void onSuccess(Object result);
    public abstract void onError(VolleyError result);

    public VolleyInterface(Context mContext, Response.Listener listener, Response.ErrorListener errorListener) {
        this.mContext = mContext;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    //成功时回调的接口，同时调用onSuccess()方法
    public Response.Listener succeedListener(){
        listener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                onSuccess(response);
            }
        };
        return listener;
    }

    //失败时回调的接口，同时调用errorListener()方法
    public Response.ErrorListener errorListener(){
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onError(error);
            }
        };
        return errorListener;
    }
}
