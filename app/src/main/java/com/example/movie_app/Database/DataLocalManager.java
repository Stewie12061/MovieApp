package com.example.movie_app.Database;

import android.content.Context;

public class DataLocalManager {
    private static final String PREF_USER_UID = "uid";
    private static DataLocalManager instance;
    private DatabaseSharedPreferences myDatabaseSharedPreferences;

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.myDatabaseSharedPreferences = new DatabaseSharedPreferences(context);
    }

    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setUserUid(String uid){
        DataLocalManager.getInstance().myDatabaseSharedPreferences.putStringValue(PREF_USER_UID, uid);
    }

    public static String getUserUid(){
        return DataLocalManager.getInstance().myDatabaseSharedPreferences.getStringValue(PREF_USER_UID);
    }
}
