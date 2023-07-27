package com.example.madagascar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import androidx.core.splashscreen.SplashScreen;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.madagascar.controleur.UtilisateurControleur;
import com.example.madagascar.model.Utilisateur;
import com.example.madagascar.vue.Accueil;
import com.example.madagascar.vue.Inscription;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    final public UtilisateurControleur userControl=new UtilisateurControleur();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

    }

    public void submitbuttonHandler(View view) throws Exception {
        //Decide what happens when the user clicks the submit button
        EditText mailEditText = (EditText) findViewById(R.id.mail);
        EditText mdpEditText = (EditText) findViewById(R.id.mdp);
        userControl.login(mailEditText,mdpEditText,this);
    }

    public void inscription(View view) throws Exception {
        Intent intent = new Intent(this, Inscription.class);
        startActivity(intent);
    }

}