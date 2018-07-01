package com.thabo.android.journalapp.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * This class implements the Singleton pattern to return one and only
 * one instance of the database.
 */
@Database(entities = {DiaryEntry.class}, version = 3, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class JournalAppDatabase extends RoomDatabase {

    private static JournalAppDatabase sDBInstance;
    private static final Object LOCK = new Object();
    private static final String TAG = JournalAppDatabase.class.getSimpleName();


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE DiaryEntries "
                    + " ADD COLUMN content TEXT");
            Log.d(TAG, "Migrated");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d(TAG, "Migrated 2 -> 3");

            database.execSQL("ALTER TABLE DiaryEntries "
                    + " ADD COLUMN content TEXT");
        }
    };

    /**
     * This returns a static instance of the database.
     * @param context
     * @return
     */
    public static JournalAppDatabase getInstance(Context context) {

        if (sDBInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Building database instance");
                sDBInstance = Room.databaseBuilder(context.getApplicationContext(), JournalAppDatabase.class, ApplicationConstants.DB_NAME)
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .build();
            }
        }
        Log.d(TAG, "Returning database instance");
        return sDBInstance;
    }


    public abstract DiaryEntryDAO diaryEntryDAO();

}
