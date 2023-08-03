package com.example.madagascar.services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.madagascar.model.Tokens;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class MessagingService extends FirebaseMessagingService {
    Activity currentAct;
    private static final String TAG = "MessagingService";
    private DatabaseReference mDatabase;

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
        Log.e("newToken", token);//Add your token in your sharepreferences.
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();


        mDatabase = FirebaseDatabase.getInstance().getReference("Tokens");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                List<String> post = dataSnapshot.getValue(List.class);
                if(post!=null){
                    post.add(getToken(getApplicationContext()));
                    mDatabase.setValue(post);
                }else{
                    post=new ArrayList<String>();
                    post.add(getToken(getApplicationContext()));
                    mDatabase.setValue(post);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Tokens");

        myRef.setValue(getToken(getApplicationContext()));*/
        //System.out.println("token send successfuly");
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "empty");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        /*String TAG=currentAct.getLocalClassName();
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if ( true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
                System.out.println(remoteMessage.getData());
            } else {
                // Handle message within 10 seconds
                System.out.println("data error");
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.i("PVL", "MESSAGE RECEIVED!!");
        if (remoteMessage.getNotification().getBody() != null) {
            Log.i("PVL", "RECEIVED MESSAGE: " + remoteMessage.getNotification().getBody());
        } else {
            Log.i("PVL", "RECEIVED MESSAGE: " + remoteMessage.getData().get("message"));
        }
    }
}