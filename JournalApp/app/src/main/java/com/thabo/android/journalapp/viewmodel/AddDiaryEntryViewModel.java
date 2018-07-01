package com.thabo.android.journalapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.thabo.android.journalapp.persistence.DiaryEntry;
import com.thabo.android.journalapp.persistence.JournalAppDatabase;

public class AddDiaryEntryViewModel extends ViewModel {

    private LiveData<DiaryEntry> entryLiveData;

    public AddDiaryEntryViewModel(JournalAppDatabase database, int entryId) {
        entryLiveData = database.diaryEntryDAO().loadEntryById(entryId);
    }

    public LiveData<DiaryEntry> getEntryLiveData() {
        return entryLiveData;
    }
}
