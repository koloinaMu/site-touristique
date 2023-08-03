package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.widget.TextView;
import java.util.*;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class SiteActivity extends AppCompatActivity {
    private ListView listView;
    private List<Site> siteDataList;
    private ArrayAdapter<Site> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);

        listView = findViewById(R.id.listeView);
        siteDataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_item_layout, siteDataList);
        listView.setAdapter(adapter);

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3000/sites";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myresponse = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(myresponse);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String siteName = jsonObject.getString("nom");
                            String siteDescription = jsonObject.getString("description");

                            Site siteData = new Site(siteName, siteDescription);
                            siteDataList.add(siteData);
                        }
                        SiteActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}