package com.thabo.android.journalapp.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * This interface acts as an access to the database to perform the following:
 * 1. Selects/Queries
 * 2. Diary entry inserts
 * 3. Diary entry updates
 * 4. Diary entry deletions
 */

@Dao
public interface DiaryEntryDAO {

    @Query("select * from DiaryEntries order by entryDate desc")
    LiveData<List<DiaryEntry>> loadAllEntries();

    @Query("select * from DiaryEntries where entryId = :entryId")
    LiveData<DiaryEntry> loadEntryById(int entryId);

    @Insert
    void insertDiaryEntry(DiaryEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDiaryEntry(DiaryEntry diaryEntry);

    @Delete
    void deleteDiaryEntry(DiaryEntry diaryEntry);
}
