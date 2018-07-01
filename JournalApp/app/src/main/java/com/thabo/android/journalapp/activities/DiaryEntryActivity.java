package com.thabo.android.journalapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thabo.android.journalapp.R;
import com.thabo.android.journalapp.persistence.DBExecutor;
import com.thabo.android.journalapp.persistence.DiaryEntry;
import com.thabo.android.journalapp.persistence.JournalAppDatabase;
import com.thabo.android.journalapp.viewmodel.AddDiaryEntryViewModel;
import com.thabo.android.journalapp.viewmodel.AddDiaryEntryViewModelFactory;

import java.util.Date;

/**
 * This activity handles the following responsibilities:
 * 1. Add a diary entry
 * 2. Modifies/updates a diary entry
 * 3. Deletes a diary entry
 */
public class DiaryEntryActivity extends AppCompatActivity {

    public static final String EXTRA_ENTRY_ID = "ENTRY_ID";
    private Button mSaveButton;
    private EditText mAddEntryEditText;
    private EditText mAddDiaryContentEditText;
    private JournalAppDatabase mDatabase;
    public static final String INSTANCE_ENTRY_ID = "INSTANCE_ID";
    private static final int DEFAULT_ID = -1;
    private int mEntryId = DEFAULT_ID;
    private Toast mToast;
    private static final String TAG = DiaryEntryActivity.class.getSimpleName();
    private Helper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_diary_entry);

        initialiseViews();

        Context context = getApplicationContext();
        mDatabase = JournalAppDatabase.getInstance(context);
        mHelper = Helper.getInstance();

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ENTRY_ID)) {
            mEntryId = savedInstanceState.getInt(INSTANCE_ENTRY_ID, DEFAULT_ID);
        }

        Intent intent = getIntent();
        handleUpdates(intent);
    }

    private void handleUpdates(final Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_ENTRY_ID)) {
            mSaveButton.setText(R.string.update_entry_btn);

            if (mEntryId == DEFAULT_ID) {
                mEntryId = intent.getIntExtra(EXTRA_ENTRY_ID, DEFAULT_ID);

                final AddDiaryEntryViewModel model = viewModel();
                model.getEntryLiveData().observe(this, new Observer<DiaryEntry>() {
                    @Override
                    public void onChanged(@Nullable DiaryEntry diaryEntry) {
                        model.getEntryLiveData().removeObserver(this);
                        updateUI(diaryEntry);
                    }
                });
            }
        }
    }

    private AddDiaryEntryViewModel viewModel() {
        AddDiaryEntryViewModelFactory factory = new AddDiaryEntryViewModelFactory(mDatabase, mEntryId);
        return ViewModelProviders.of(this, factory).get(AddDiaryEntryViewModel.class);
    }

    private void updateUI(DiaryEntry entry) {
        if (entry == null) {
            return;
        }

        mAddEntryEditText.setText(entry.getEntry());
        mAddDiaryContentEditText.setText(entry.getContent());
    }

    private void initialiseViews() {
        mAddEntryEditText = findViewById(R.id.editDiaryEntry);
        mAddDiaryContentEditText = findViewById(R.id.editDiaryContent);
        mSaveButton = findViewById(R.id.saveDiaryEntryButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {

        String entryDetails = mAddEntryEditText.getText().toString();
        String content = mAddDiaryContentEditText.getText().toString();
        if (!entryDetails.isEmpty() || !content.isEmpty()) {

            final DiaryEntry diaryEntry = new DiaryEntry(content, entryDetails, new Date());
            DBExecutor dbExecutor = DBExecutor.getInstance();
            dbExecutor.getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mEntryId == DEFAULT_ID) {
                        mDatabase.diaryEntryDAO().insertDiaryEntry(diaryEntry);
                        Log.i(TAG, "New diary entry added");
                    } else {
                        diaryEntry.setEntryId(mEntryId);
                        mDatabase.diaryEntryDAO().updateDiaryEntry(diaryEntry);
                        mHelper.attachDairyEntry(diaryEntry);
                        Log.i(TAG, "Existing entry edited");
                    }
                    finish();
                }
            });
            reinitializeToastAndShow("Diary Entry Added");
        } else {
            reinitializeToastAndShow("Cannot Add Empty Entry");
            finish();
        }
    }

    private void reinitializeToastAndShow(String toastMessage) {
        if (mToast != null) {
            mToast = null;
        }
        mToast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
        mToast.show();
    }
}
