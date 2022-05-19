package com.msvastudios.trick_builder.io_utils.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.LauncherApps;

import androidx.room.Room;


public class DatabaseHandler {

    private static AlgorithmDatabase db;

    private DatabaseHandler() {}

    public static void init(Context context){
        newInstance(context);
    }
    private static void newInstance(Context context){
        db = Room.databaseBuilder(context, AlgorithmDatabase.class, AlgorithmDatabase.DATABASE_NAME).build();
    }

    public AlgorithmDatabase getInstance() {
        return db;
    }
}
