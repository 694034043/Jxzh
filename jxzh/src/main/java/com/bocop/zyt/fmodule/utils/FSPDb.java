package com.bocop.zyt.fmodule.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lt on 16/11/29.
 */
public class FSPDb {

    private final Context context;
    private final String DBName;

    public FSPDb(Context context, String DBName) {
        this.context = context;
        this.DBName = DBName;
    }

    public void save( String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(DBName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String get( String key) {
        SharedPreferences preferences = context.getSharedPreferences(DBName,
                Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
    public String get( String key,String defaultStr) {
        SharedPreferences preferences = context.getSharedPreferences(DBName,
                Context.MODE_PRIVATE);
        return preferences.getString(key, defaultStr);
    }
}
