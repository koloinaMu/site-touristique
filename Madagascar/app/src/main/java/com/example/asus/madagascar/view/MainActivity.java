package com.example.asus.madagascar.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

//import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.asus.madagascar.R;
import com.example.asus.madagascar.controlleur.MyDataAPI;
import com.example.asus.madagascar.modele.Site;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.*;


public class MainActivity extends AppCompatActivity {

    private TextView dataTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataTextView = findViewById(R.id.textId);
        /*
        new GetDataTask().execute();
        Créer un objet Retrofit
        */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //instance
        MyDataAPI mysiteApi=retrofit.create(MyDataAPI.class);

        Call<Site> call = mysiteApi.getSite();
        call.enqueue(new Callback<Site>() {
            @Override
            public void onResponse(Call<Site> call, Response<Site> response) {
                if (response.isSuccessful()) {
                    String data = response.body().getNom();
                    dataTextView.setText(data);
                    // Faire quelque chose avec les données reçues
                } else {
                    // Traitement des erreurs de l'API
                }
            }

            @Override
            public void onFailure(Call<Site> call, Throwable t) {
                // Gérer les erreurs de requête (par exemple, problèmes de réseau)
            }
        });
    }

}






