package com.msvastudios.trick_builder.io_utils.sqlite.algorithms;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {AlgorithmEntity.class}, version = 1)
public abstract class AlgorithmDatabase extends RoomDatabase {
    public static String DATABASE_NAME = "algos";
    public abstract AlgorithmDao algorithmDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }


}


