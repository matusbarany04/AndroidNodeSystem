package com.msvastudios.trick_builder.utils.shared_prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesAPI {

    SharedPreferencesAPI instance = new SharedPreferencesAPI();

    private SharedPreferencesAPI(){

    }

    public SharedPreferencesAPI getInstance()
    {
        if (instance == null) {
            instance = new SharedPreferencesAPI();
        }
        return instance;
    }

    public void save(Context context, String key){
        SharedPreferences.Editor sh = context.getSharedPreferences(key, 0).edit();
        sh.
    }

}
