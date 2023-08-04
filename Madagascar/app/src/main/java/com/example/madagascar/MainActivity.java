package com.example.madagascar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.splashscreen.SplashScreen;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.madagascar.controleur.UtilisateurControleur;
import com.example.madagascar.services.MessagingService;
import com.example.madagascar.services.NotificationService;
import com.example.madagascar.vue.Inscription;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private UtilisateurControleur userControl=new UtilisateurControleur();

    public static final String IS_DARK = "IS_DARK";

    @Override
    protected void attachBaseContext(Context baseContext) {
        super.attachBaseContext(baseContext);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(baseContext);
        boolean isDark = prefs.getBoolean(IS_DARK, false);

        if (isDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        System.out.println(MessagingService.getToken(baseContext));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(Task<String> task) {
                Log.d("MAIN",task.getResult().toString());

                WorkRequest myWorkRequest = OneTimeWorkRequest.from(NotificationService.class);
                WorkManager
                        .getInstance(getApplicationContext())
                        .enqueue(myWorkRequest);
            }
        });
    }

    public void submitbuttonHandler(View view) throws Exception {
        //Decide what happens when the user clicks the submit button
        EditText mailEditText = (EditText) findViewById(R.id.mail);
        EditText mdpEditText = (EditText) findViewById(R.id.mdp);
        userControl.setCurrentActivity(getApplicationContext());
        userControl.login(mailEditText,mdpEditText,this);
    }

    public void inscription(View view) throws Exception {
        userControl.setCurrentActivity(getApplicationContext());
        Intent intent = new Intent(this, Inscription.class);
        startActivity(intent);
    }

}