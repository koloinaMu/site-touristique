package com.example.madagascar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.madagascar.vue.Accueil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void submitbuttonHandler(View view) {
        //Decide what happens when the user clicks the submit button
        EditText mailEditText = (EditText) findViewById(R.id.mail);
        String mail = mailEditText.getText().toString();

        EditText mdpEditText = (EditText) findViewById(R.id.mdp);
        String mdp = mdpEditText.getText().toString();
        System.out.println("mail="+mail+" et mdp="+mdp);
        Intent intent = new Intent(this, Accueil.class);
        startActivity(intent);


    }


}