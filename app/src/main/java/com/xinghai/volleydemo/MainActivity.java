package com.xinghai.volleydemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.xinghai.volleydemo.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnGetPicture;
    private ImageView imageView;
    WindowManager wm;
    int width,height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
//        volleyGet();
//        volleyPost();
        //使用封装好的Volley
        String url = "http://apis.juhe.cn/mobile/get?phone=15934837166&key=b49a61c0a285c1cd98b62f2c536fbf5b";
        VolleyRequest.RequestGet(this, url, "Get", new VolleyInterface(this,VolleyInterface.listener,VolleyInterface.errorListener) {
            @Override
            public void onSuccess(Object result) {
                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VolleyError result) {
                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //使用ImageRequest加载网络图片
        initView();
    }

    private void initView() {
        btnGetPicture = findViewById(R.id.getPicture);
        imageView = findViewById(R.id.iv_img);
        btnGetPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg";
                //使用ImageRequest获取网络图片
//                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        imageView.setImageBitmap(response);
//                    }
//                }, width, height, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
//                    }
//                });
//                imageRequest.setTag("image");
//                MyApplication.getQueues().add(imageRequest);

                //使用ImageLoader获取网络图片
                ImageLoader loader = new ImageLoader(MyApplication.getQueues(),new BitmapCache());
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                loader.get(url,listener);

            }
        });
    }

    private void volleyPost() {
        //通过StringRequest获取
        stringRequestPost();
        //通过JsonObjectRequest获取--由于使用的是post，传入的参数是Json格式，服务器不支持，获取不到数据
        //不建议使用JsonObjectRequest
//        JsonObjectRequestPost();
    }

    private void JsonObjectRequestPost() {
        String url = "http://apis.juhe.cn/mobile/get?";
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("phone", "15934837166");
        hashMap.put("key", "b49a61c0a285c1cd98b62f2c536fbf5b");
        JSONObject object = new JSONObject(hashMap);
        JsonObjectRequest request = new JsonObjectRequest(url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i("TAG", jsonObject.toString());
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("Post");
        MyApplication.getQueues().add(request);
    }

    private void stringRequestPost() {
        String url = "http://apis.juhe.cn/mobile/get?";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("phone", "15934837166");
                hashMap.put("key", "b49a61c0a285c1cd98b62f2c536fbf5b");
                return hashMap;
            }
        };
        request.setTag("Post");
        MyApplication.getQueues().add(request);
    }

    private void volleyGet() {
        //通过StringRequest获取
        stringRequestGet();
        //通过JsonObjectRequest获取
//        jsonObjectRequestGet();
        //通过JsonArrayRequest获取
//        jsonArrayRequestGet();
    }

    private void jsonArrayRequestGet() {
        String url = "http://apis.juhe.cn/mobile/get?phone=15934837166&key=b49a61c0a285c1cd98b62f2c536fbf5b";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Toast.makeText(MainActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("Get");
        MyApplication.getQueues().add(request);
    }

    private void jsonObjectRequestGet() {
        String url = "http://apis.juhe.cn/mobile/get?phone=15934837166&key=b49a61c0a285c1cd98b62f2c536fbf5b";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("Get");
        MyApplication.getQueues().add(request);
    }

    private void stringRequestGet() {
        String url = "http://apis.juhe.cn/mobile/get?phone=15934837166&key=b49a61c0a285c1cd98b62f2c536fbf5b";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("Get");
        MyApplication.getQueues().add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getQueues().cancelAll("Get");
        MyApplication.getQueues().cancelAll("Post");
        MyApplication.getQueues().cancelAll("image");
    }
}
