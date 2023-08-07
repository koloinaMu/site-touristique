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
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.madagascar.R;
import com.example.madagascar.model.Site;
import com.example.madagascar.services.NotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

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
    Accueil accueil=new Accueil();
    SiteFragment site=new SiteFragment();

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
}