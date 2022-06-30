package com.msvastudios.trick_builder.utils.shared_prefs;

public class SharedPreferencesAPI {

    SharedPreferencesAPI instance = new SharedPreferencesAPI();

    private SharedPreferencesAPI(){

    }

    public SharedPreferencesAPI getInstance(){
        return instance;
    }

//    public void save(Context context, String key){
//        SharedPreferences.Editor sh = context.getSharedPreferences(key, 0).edit();
//        sh.
//    }

}
