package com.example.madagascar.services;


import android.content.Context;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationService extends Worker {


    public NotificationService(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {

        return null;
    }
}
