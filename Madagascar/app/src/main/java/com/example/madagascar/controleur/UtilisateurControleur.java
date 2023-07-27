package com.example.madagascar.controleur;

import android.content.Intent;
import android.widget.EditText;

import com.example.madagascar.MainActivity;
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

public class UtilisateurControleur {

    public void login(EditText mailEditText, EditText mdpEditText, MainActivity main){
        String mail=mailEditText.getText().toString();
        String mdp=mdpEditText.getText().toString();
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
                Intent intent = new Intent(main.getApplicationContext(), Accueil.class);
                main.startActivity(intent);
            }
        }
        );
    }

    public void inscrire(EditText nomEditText, EditText prenomEditText, EditText mailEditText,
                         EditText mdpEditText, Inscription inscri){
        String nom=nomEditText.getText().toString();
        String prenom=prenomEditText.getText().toString();
        String mail=mailEditText.getText().toString();
        String mdp=mdpEditText.getText().toString();
        Utilisateur user=new Utilisateur(nom,prenom,mail,mdp);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        //RequestBody body = RequestBody.create(mediaType, "{\r\n    \"mail\":\"kolo@gmail.com\",\r\n    \"mdp\":\"koloina\"\r\n}");
        RequestBody body = RequestBody.create(mediaType, jsonStr);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:3000/user/inscription")
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
                Intent intent = new Intent(inscri.getApplicationContext(), Accueil.class);
                inscri.startActivity(intent);
            }
        }
        );
    }
}
