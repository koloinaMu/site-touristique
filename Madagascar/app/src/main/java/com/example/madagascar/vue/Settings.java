package com.example.madagascar.vue;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.madagascar.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Settings extends Fragment {


    private String IS_DARK;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println(AppCompatDelegate.getDefaultNightMode());
        View rootView=inflater.inflate(R.layout.fragment_settings, container, false);
        Switch switchtheme=rootView.findViewById(R.id.switchtheme);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        switchtheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                prefs.edit().putBoolean(IS_DARK, true).apply();
            }
        });
        return rootView;
    }
}