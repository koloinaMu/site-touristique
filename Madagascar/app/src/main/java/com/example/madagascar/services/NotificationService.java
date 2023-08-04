package com.example.madagascar.services;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.madagascar.R;
import com.example.madagascar.model.Identification;
import com.example.madagascar.vue.Template;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationService extends Worker {

    private final String apiUrl= getApplicationContext().getResources().getString(R.string.apiUrl) ;

    public NotificationService(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        String token = MessagingService.getToken(getApplicationContext());
        System.out.println("TOOOKKKKEEENNNNN="+token);
        Identification id=new Identification(token,"old");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(id);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonStr);
        String url=apiUrl+"site/firebase";
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("ERRROOOOORRRR",response.toString());
                    throw new IOException("Unexpected code " + response);
                }
                Log.d("NOTIFF",response.toString());
            }
        }
        );
        return Result.success();
    }
}
