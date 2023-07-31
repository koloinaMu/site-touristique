package com.example.madagascar.controleur;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.madagascar.MainActivity;
import com.example.madagascar.model.Utilisateur;
import com.example.madagascar.vue.Accueil;
import com.example.madagascar.vue.Inscription;
import com.example.madagascar.vue.Template;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(mail);
        boolean matchFound = matcher.find();
        if(matchFound) {
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
                    String reponse=(response.body().string());
                    if(reponse.equals("null")){
                        main.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(main, "Erreur d'authentification",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Intent intent = new Intent(main.getApplicationContext(), Template.class);
                        main.startActivity(intent);
                    }
                }
            }
            );
        } else {
            main.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(main, "Mail invalide",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void inscrire(EditText nomEditText, EditText prenomEditText, EditText mailEditText,
                         EditText mdpEditText, Inscription inscri){
        String nom=nomEditText.getText().toString();
        String prenom=prenomEditText.getText().toString();
        String mail=mailEditText.getText().toString();
        String mdp=mdpEditText.getText().toString();

        Pattern patternMail = Pattern.compile("^[a-zA-Z0-9.]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$");
        Matcher matcherMail = patternMail.matcher(mail);
        boolean matchFoundMail = matcherMail.find();

        //Pattern patternMdp = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-.])(?=.*?[0-9]).{8,}$");
        Pattern patternMdp = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&–:;',?/*~$^+=<>]).{8,20}$");
        Matcher matcherMdp = patternMdp.matcher(mdp);
        boolean matchFoundMdp = matcherMdp.find();

        //System.out.println("mail="+matchFoundMail);
        //System.out.println("mdp="+matchFoundMdp);

        if(matchFoundMail==true && matchFoundMdp==true){
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
                    System.out.println(response.code());
                    if(response.code()==200){
                        Intent intent = new Intent(inscri.getApplicationContext(), Accueil.class);
                        inscri.startActivity(intent);
                    }else{
                        inscri.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(inscri,  "Une erreur est survenue. Veuillez recommencer.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            );
        }else{
            if(matchFoundMdp==false) {
                inscri.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(inscri,  "Mot de passe invalide",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                inscri.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(inscri,  "Mail invalide",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
