package com.thabo.android.journalapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.thabo.android.journalapp.persistence.JournalAppDatabase;

public class AddDiaryEntryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final JournalAppDatabase mDatabase;
    private final int mEntryId;

    public AddDiaryEntryViewModelFactory(JournalAppDatabase db, int entryId) {
        this.mDatabase = db;
        this.mEntryId = entryId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> clazz) {
        return (T) new AddDiaryEntryViewModel(mDatabase, mEntryId);
    }
}
