package com.brotherhood.gramatyka.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefsHelper {
    private static final String SHARED_PREFERENCES_NAME = "gramatica";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }

    public void putString(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }

    public long getLong(String key){
        return sharedPreferences.getLong(key, 0);
    }

    public void putLong(String key,long value){
        editor.putLong(key, value);
        editor.commit();
    }

    public float getFloat(String key){
        return sharedPreferences.getFloat(key, 0);
    }

    public void putFloat(String key,float value){
        editor.putFloat(key,value);
        editor.commit();
    }
}
