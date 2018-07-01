package com.thabo.android.journalapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thabo.android.journalapp.persistence.DiaryEntry;
import com.thabo.android.journalapp.persistence.JournalAppDatabase;

import java.util.List;

/**
 * This is so that the database is queried in the ViewModel. This will
 * help in caching live data and having it available even when the screen
 * rotates.
 */
public class DiaryViewModel extends AndroidViewModel {

    private LiveData<List<DiaryEntry>> diaryEntries;

    public DiaryViewModel(@NonNull Application application) {
        super(application);

        JournalAppDatabase db = JournalAppDatabase.getInstance(this.getApplication());
        diaryEntries = db.diaryEntryDAO().loadAllEntries();
    }

    public LiveData<List<DiaryEntry>> getDiaryEntries() {
        return diaryEntries;
    }
}
