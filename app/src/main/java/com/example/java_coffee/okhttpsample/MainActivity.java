package com.example.java_coffee.okhttpsample;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.resText);
    }
    public void doGet(View view){
        //1.拿到okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造request请求
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url("http://www.google.com/").build();
        //将request封装为Call
        Call call = okHttpClient.newCall(request);
        //使用消息队列（异步操作）执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(Constants.TAG,"onFailure:"+e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(Constants.TAG,"onResponse");
                //由于传回来的数据大小未知，防止发生ANR或者内存不足，回调还在子线程中
                //InputStream is = response.body().byteStream();  //可以对response进行IO操作
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(res);
                    }
                });
            }
        });
    }
}
