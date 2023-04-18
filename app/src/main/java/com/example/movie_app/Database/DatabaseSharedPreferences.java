package com.example.movie_app.Database;

import android.content.Context;
import android.content.SharedPreferences;

public class DatabaseSharedPreferences {
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private Context context;

    public DatabaseSharedPreferences(Context context){
        this.context = context;
    }

    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
