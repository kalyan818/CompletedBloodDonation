package com.example.kalyansai.blooddonation1;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by kalyan sai on 03-Oct-17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
