package com.msvastudios.trick_builder.utils.shared_prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesAPI {

    public final static String CHOSEN_ALGORITHM = "chosen";
    private static SharedPreferencesAPI instance;
    Context context;

    private SharedPreferencesAPI(Context context){
        this.context = context;
    }

    public void build(Context context){
        instance = new SharedPreferencesAPI(context);
    }

    public static SharedPreferencesAPI getInstance(Context context)
    {
        if (instance == null) {
            instance = new SharedPreferencesAPI(context);
        }
        return instance;
    }

    public void save(String key, String data){
        SharedPreferences.Editor sh = context.getSharedPreferences("com.msvastudios.trickbuilder", Context.MODE_PRIVATE).edit();
        sh.putString(key, data);
        sh.commit();
    }

    public String get(String key){
        SharedPreferences sh = context.getSharedPreferences("com.msvastudios.trickbuilder", Context.MODE_PRIVATE);
        return sh.getString(key, null);
    }

}
