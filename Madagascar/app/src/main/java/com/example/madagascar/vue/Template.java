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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.madagascar.R;
import com.example.madagascar.services.NotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                        .replace(R.id.flFragment, accueil)
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
}