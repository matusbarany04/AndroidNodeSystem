package com.msvastudios.trick_builder.io_utils.sqlite.nodes;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.AlgorithmDao;

public abstract class NodeDatabase extends RoomDatabase {
    public final static String DATABASE_NAME = "nodes";

    public abstract NodeDao nodeDao();

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
