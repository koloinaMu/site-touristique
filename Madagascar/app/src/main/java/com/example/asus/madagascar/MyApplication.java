package com.example.asus.madagascar;

/**
 * Created by ASUS on 29/07/2023.
 */

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}