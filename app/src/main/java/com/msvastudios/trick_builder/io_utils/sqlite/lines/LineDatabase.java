package com.msvastudios.trick_builder.io_utils.sqlite.lines;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.msvastudios.trick_builder.io_utils.sqlite.nodes.NodeDao;

public abstract class LineDatabase extends RoomDatabase {
    public final static String DATABASE_NAME = "lines";

    public abstract LineDao lineDao();

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