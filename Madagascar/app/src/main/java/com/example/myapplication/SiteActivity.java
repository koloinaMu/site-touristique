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
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ImageView;
import android.view.View;
import android.graphics.drawable.Drawable;
import java.io.InputStream;
public class SiteActivity extends AppCompatActivity {
    private ListView listView;
    private List<Site> siteDataList;
    private CustomSiteDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);



        listView = findViewById(R.id.listeView);


        siteDataList = new ArrayList<>();
        adapter = new CustomSiteDataAdapter(this, R.layout.list_item_layout, siteDataList);

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
                            String region = jsonObject.getString("region");
                            String imagePosteur = jsonObject.getString("imagePosteur");
                            //atribut dans media

                            JSONArray mediaArray = jsonObject.getJSONArray("media");
                            JSONObject mediaObject = mediaArray.getJSONObject(0); // Assuming there's only one media object
                            String imageUrlMedia = mediaObject.getString("urlMedia");

                            Site siteData = new Site(siteName, siteDescription,region,imagePosteur,imageUrlMedia);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopup(siteDataList.get(position));
            }
        });
    }
    private void showPopup(Site siteData) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Set data to the popup views
        TextView popupNameTextView = dialog.findViewById(R.id.popup_name_textView);
        TextView popupDescriptionTextView = dialog.findViewById(R.id.popup_description_textView);
        TextView popup_region_textView= dialog.findViewById(R.id.popup_region_textView);
        ImageView popupImageView = dialog.findViewById(R.id.popup_imageView);

        ImageView popup_imageView_add = dialog.findViewById(R.id.popup_imageView_add);


        if (popupNameTextView != null) {
            popupNameTextView.setText(siteData.getNom());
        }
        if (popup_region_textView != null) {
            popup_region_textView.setText(siteData.getRegion());
        }
        if (popupDescriptionTextView != null) {
            popupDescriptionTextView.setText(siteData.getDescription());
        }

        if (popupImageView != null) {
            // Load the image dynamically using the imageSourceId from SiteData
            String imageSourceId = siteData.getImagePosteur();
            try {
                InputStream inputStream = getAssets().open("images/" + imageSourceId);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                popupImageView.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (popup_imageView_add != null) {
            // Load the image dynamically using the imageSourceId from SiteData
            String imageSourceId = siteData.getUrlMedia();
            try {
                InputStream inputStream = getAssets().open("images/" + imageSourceId);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                popup_imageView_add.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        dialog.show();
    }

}