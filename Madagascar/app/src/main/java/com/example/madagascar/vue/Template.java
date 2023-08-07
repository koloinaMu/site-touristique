package com.example.madagascar.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.madagascar.MainActivity;
import com.example.madagascar.R;
import com.example.madagascar.model.Site;
import com.example.madagascar.services.NotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Template extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
    Settings sett=new Settings();
    SiteFragment site=new SiteFragment();
    RechercheFragment rechercheFragment=new RechercheFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, sett)
                        .commit();
                return true;

            case R.id.home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, site)
                        .commit();
                return true;

            case R.id.deconnecter:
                final SharedPreferences prefs = getSharedPreferences("_", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("fcm_token").apply();
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

        }
        return false;
    }

    public void setPreferedHour(View view) throws Exception{
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        EditText minuteEdit= view.findViewById(R.id.minute);
        int minute = Integer.parseInt(minuteEdit.getText().toString());
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        WorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(NotificationService.class)
                .setConstraints(myConstraints)
                .build();
        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(myWorkRequest);
        getSharedPreferences("_", MODE_PRIVATE).edit().putInt("preferedMinutes", minute).apply();
        System.out.println(WorkManager.getInstance(view.getContext()));

    }

    public void showPopup(Site siteData) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Set data to the popup views
        TextView popupNameTextView = dialog.findViewById(R.id.popup_name_textView);
        TextView popupDescriptionTextView = dialog.findViewById(R.id.popup_description_textView);
        TextView popup_region_textView= dialog.findViewById(R.id.popup_region_textView);
        //ImageView popupImageView = dialog.findViewById(R.id.popup_imageView);
        VideoView popupVideoView = dialog.findViewById(R.id.popup_videoView);
        ImageView popup_imageView_add = dialog.findViewById(R.id.popup_imageView_add);


        if (popupNameTextView != null) {
            popupNameTextView.setText(siteData.getNom());
        }
        if (popup_region_textView != null) {
            popup_region_textView.setText(siteData.getRegion());
        }
        if (popupDescriptionTextView != null) {
            popupDescriptionTextView.setText(siteData.getDescriptionMedia());
        }
        if (popupVideoView != null) {
            popupVideoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String videoUrl = siteData.getUrlVideo();
                    Uri videoUri = Uri.parse(videoUrl);
                    popupVideoView.setVideoURI(videoUri);
                    popupVideoView.start();
                }
            });
        }
        Button pauseButton = dialog.findViewById(R.id.pause_button);
        Button forwardButton = dialog.findViewById(R.id.forward_button);
        Button playButton = dialog.findViewById(R.id.play_button);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupVideoView.isPlaying()) {
                    popupVideoView.pause();
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupVideoView.start();
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = popupVideoView.getCurrentPosition();
                int duration = popupVideoView.getDuration();
                int forwardTime = 10000; // Avancer de 10 secondes
                int newPosition = currentPosition + forwardTime;

                if (newPosition > duration) {
                    newPosition = duration;
                }

                popupVideoView.seekTo(newPosition);
            }
        });

       /* if (popupImageView != null) {
            // Load the image dynamically using the imageSourceId from SiteData
            String imageSourceId = siteData.getImagePosteur();
            try {
                InputStream inputStream = getAssets().open("images/" + imageSourceId);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                popupImageView.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

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

    public void rechercher(View view) throws  Exception{
        EditText rechercheTxt=findViewById(R.id.txtRecherche);
        System.out.println("IDDD   EDDDIIIITTTT TXT="+R.id.txtRecherche);
        System.out.println("VIEEWWWW="+view);
        System.out.println("EDDDIIIITTTT TXT="+rechercheTxt);
        String txtRecherche=rechercheTxt.getText().toString();
        OkHttpClient client = new OkHttpClient();
        String apiUrl= this.getResources().getString(R.string.apiUrl) ;
        String url = apiUrl+"site/sites/"+txtRecherche;
        Request request = new Request.Builder()
                .url(url)
                .build();
        List<Site> siteDataList=new ArrayList<Site>();
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
                            String id = jsonObject.getString("_id");
                            String siteName = jsonObject.getString("nom");
                            String siteDescription = jsonObject.getString("description");
                            String region = jsonObject.getString("region");
                            String imagePosteur = jsonObject.getString("imagePosteur");
                            //atribut dans media

                            JSONArray mediaArray = jsonObject.getJSONArray("media");
                            JSONObject mediaObject = mediaArray.getJSONObject(0); // Assuming there's only one media object
                            String imageUrlMedia = mediaObject.getString("urlMedia");
                            String urlVideo=mediaObject.getString("urlVideo");
                            String descriptionMedia=mediaObject.getString("descriptionMedia");
                            Site siteData = new Site(id,siteName, siteDescription,region,imageUrlMedia,descriptionMedia,urlVideo,imagePosteur);
                            siteDataList.add(siteData);
                            System.out.println("RECHHHEEERRRCCHHHEEERRR");
                            System.out.println(siteDataList);
                            //site.adapter = new SiteAdapter(this, siteDataList);
                            SiteAdapter siteAdapt=new SiteAdapter(getApplicationContext(), siteDataList);
                            siteAdapt.onRefreshAdapter(siteDataList);
                            site.getRecyclerView().setAdapter(siteAdapt);
                            /*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    site.adapter.notifyDataSetChanged();
                                }
                            });*/
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}