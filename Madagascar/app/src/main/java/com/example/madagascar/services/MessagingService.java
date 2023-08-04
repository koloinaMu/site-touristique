package com.example.madagascar.services;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.madagascar.R;
import com.example.madagascar.model.Identification;
import com.example.madagascar.model.Site;
import com.example.madagascar.model.Tokens;
import com.example.madagascar.vue.Template;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessagingService extends FirebaseMessagingService {
    private static  String CHANNEL_ID ;
    Activity currentAct;
    private static final String TAG = "MessagingService";
    private DatabaseReference mDatabase;

    private String apiUrl ;

    public MessagingService() {
        //System.out.println("MESSSAAAGGGIIIIINNNGGGG SSSSEERRRVVVIIIIICCCCEEE");
    }

    public MessagingService(Activity current) {
        currentAct=current;
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        //Log.d(TAG,"Refreshed token: "+token);
        //Log.e("newToken", token);//Add your token in your sharepreferences.
        String oldToken=getToken(getApplicationContext());
        apiUrl= getApplicationContext().getResources().getString(R.string.apiUrl);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
        CHANNEL_ID=getToken(getApplicationContext());
        Identification id=new Identification(token,oldToken);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(id);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonStr);
        String url=apiUrl+"user/newToken";
        Request request = new Request.Builder()
                .url(url)
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
                String reponse=(response.body().string());
                Log.e("TOKEN",reponse);
                Log.e("TOKEN",token);
            }
        }
        );
        //Log.e("newToken", token);//Add your token in your sharepreferences.
    }


    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "empty");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //System.out.println("RECCEEEEEIIIIIIIVVVVVEEEEDDDD");

        //Log.i("PVL", "MESSAGE RECEIVED!!");
        if (remoteMessage.getNotification().getBody() != null) {
            Log.i("PVL", "TITLE: " + remoteMessage.getNotification().getTitle());
            Log.i("PVL", "RECEIVED MESSAGE: " + remoteMessage.getNotification().getBody());
            Log.i("DATAAA",remoteMessage.getData().toString());
            String json=remoteMessage.getData().toString();
            Gson g=new Gson();
            JsonObject obj=g.fromJson(json, JsonObject.class);
            String objet=obj.get("data").toString();
            System.out.println(objet);
            Site site=g.fromJson(objet,Site.class);
            System.out.println(site);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_nofitications_foreground)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(site.getDescription()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(2, builder.build());


        } else {
            Log.i("PVL", "RECEIVED MESSAGE: " + remoteMessage.getData().get("message"));
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Madagascar";
            String description = "Suggestion";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}