package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class SiteActivity extends AppCompatActivity {
    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        textview= findViewById(R.id.text_view);

        OkHttpClient client= new OkHttpClient();
        String url= "http://10.0.2.2:3000/sites";
        Request request= new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String myresponse = response.body().string();
                    SiteActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textview.setText(myresponse);
                        }
                    });
                }
            }
        });
    }
}