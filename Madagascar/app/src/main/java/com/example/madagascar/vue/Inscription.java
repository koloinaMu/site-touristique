package com.example.madagascar.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.madagascar.MainActivity;
import com.example.madagascar.R;
import com.example.madagascar.controleur.UtilisateurControleur;
import com.example.madagascar.model.Utilisateur;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Inscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
    }

    public void inscrire(View view) throws Exception {
        //Decide what happens when the user clicks the submit button

        EditText nomEditText = (EditText) findViewById(R.id.nom);
        EditText prenomEditText = (EditText) findViewById(R.id.prenom);
        EditText mailEditText = (EditText) findViewById(R.id.mail);
        EditText mdpEditText = (EditText) findViewById(R.id.mdp);
        UtilisateurControleur userControl=new UtilisateurControleur(this);
        userControl.inscrire(nomEditText,prenomEditText,mailEditText,mdpEditText,this);
    }

    public void connexion(View view) throws Exception {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}