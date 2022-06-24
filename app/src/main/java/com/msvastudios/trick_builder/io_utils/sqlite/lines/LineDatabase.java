package com.msvastudios.trick_builder.io_utils.sqlite.lines;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.msvastudios.trick_builder.io_utils.sqlite.Converters;
import com.msvastudios.trick_builder.io_utils.sqlite.nodes.NodeDao;
import com.msvastudios.trick_builder.io_utils.sqlite.nodes.NodeEntity;

@Database(entities = {LineEntity.class},version = 1)
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