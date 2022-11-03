package com.msvastudios.trick_builder.utils.sqlite.tricks;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.msvastudios.trick_builder.utils.sqlite.Converters;

@Database(entities = {TrickEntity.class},version = 1)
@TypeConverters({Converters.class})
public abstract class TrickDatabase extends RoomDatabase {
        public final static String DATABASE_NAME = "tricks";

        public abstract TrickDao trickDao();

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
