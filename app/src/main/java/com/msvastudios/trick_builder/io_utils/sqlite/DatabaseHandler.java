package com.msvastudios.trick_builder.io_utils.sqlite;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.Algorithm;
import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.AlgorithmDao;
import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.AlgorithmDatabase;

import java.util.List;


public class DatabaseHandler {

    private AlgorithmDatabase algorithmDatabase;

    private DatabaseHandler() {}

    public static DatabaseHandler build(Context context){
        instance = new DatabaseHandler();
        instance.algorithmDatabase = Room.databaseBuilder(context, AlgorithmDatabase.class, AlgorithmDatabase.DATABASE_NAME).build();

        return instance;
    }

    public void saveAlgorithm(Algorithm algorithm) {
        new Thread(() -> algorithmDatabase.algorithmDao().insertAll(algorithm)).start();
    }

    public void printAlgorithms(){
        new Thread(()-> {
            Log.d("printing ... ", " \\|/");
            for (Algorithm algo : algorithmDatabase.algorithmDao().getAll()) {
                Log.d("algo", algo.name);
            }
        }).start();
    }

    public static DatabaseHandler instance;
    public static DatabaseHandler getInstance() {
        return instance;
    }
}
