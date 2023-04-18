package com.example.movie_app.Database;

import android.app.Application;

import com.example.movie_app.Database.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
