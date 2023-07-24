package com.example.madagascar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText nameEditText = (EditText) findViewById(R.id.names);
        String fullName = nameEditText.getText().toString();
        EditText currentWeightEditText = (EditText) findViewById(R.id.current_weight);
        String currentWeight = currentWeightEditText.getText().toString();
        EditText heightEditText = (EditText) findViewById(R.id.height);
        String Height = heightEditText.getText().toString();
        EditText goalWeightEditText = (EditText) findViewById(R.id.gol_weight);
        String GoalWeight = goalWeightEditText.getText().toString();
        EditText ageEditText = (EditText) findViewById(R.id.age);
        String age = ageEditText.getText().toString();
        EditText phoneEditText = (EditText) findViewById(R.id.names);
        String phone = phoneEditText.getText().toString();
        EditText addressEditText = (EditText) findViewById(R.id.names);
        String address = addressEditText.getText().toString();

        //initiate a check box
        CheckBox conditionsCheckBox = (CheckBox) findViewById(R.id.conditions_CheckBox);
        //check current state of the check box
        Boolean checkBoxState = conditionsCheckBox.isChecked();

    }

    public void radioButtonhandler(View view) {
        // Decide what happens when a user clicks on a button
    }

    public void submitbuttonHandler(View view) {
        //Decide what happens when the user clicks the submit button
    }


}