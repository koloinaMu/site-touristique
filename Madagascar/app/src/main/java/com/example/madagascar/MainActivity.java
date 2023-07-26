package com.example.madagascar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import androidx.core.splashscreen.SplashScreen;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

    }

    public void submitbuttonHandler(View view) throws Exception {
        //Decide what happens when the user clicks the submit button
        EditText mailEditText = (EditText) findViewById(R.id.mail);
        String mail = mailEditText.getText().toString();

        EditText mdpEditText = (EditText) findViewById(R.id.mdp);
        String mdp = mdpEditText.getText().toString();
        System.out.println("mail="+mail+" et mdp="+mdp);
        Utilisateur user=new Utilisateur(mail,mdp);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonStr);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:3000/user/login")
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
                    throw new IOException("Unexpected code " + response);
                }

                // you code to handle response
                System.out.println(response);
                Intent intent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(intent);
            }
        }
        );
    }

    public void inscription(View view) throws Exception {
        Intent intent = new Intent(this, Inscription.class);
        startActivity(intent);
    }

}