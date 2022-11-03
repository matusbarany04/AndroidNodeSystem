package com.msvastudios.trick_builder.utils.sqlite.groups;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.msvastudios.trick_builder.utils.sqlite.Converters;

@Database(entities = {GroupEntity.class},version = 1)
@TypeConverters({Converters.class})
public abstract class GroupDatabase extends RoomDatabase {
    public final static String DATABASE_NAME = "group";

    public abstract GroupDao groupDao();

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
