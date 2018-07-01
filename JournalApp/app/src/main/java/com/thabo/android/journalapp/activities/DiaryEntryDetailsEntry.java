package com.thabo.android.journalapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.thabo.android.journalapp.R;
import com.thabo.android.journalapp.persistence.DiaryEntry;
import com.thabo.android.journalapp.persistence.JournalAppDatabase;
import com.thabo.android.journalapp.viewmodel.AddDiaryEntryViewModel;
import com.thabo.android.journalapp.viewmodel.AddDiaryEntryViewModelFactory;

import java.io.Serializable;

public class DiaryEntryDetailsEntry extends AppCompatActivity implements Serializable {

    public static final String EXTRA_DIARY_DETAIL = "DIARY_DETAIL";
    private static final int DEFAULT_ID = -1;
    private int mEntryId = DEFAULT_ID;
    private JournalAppDatabase mDatabase;
    private TextView mDetailsTitle;
    private TextView mDetailsContent;
    private Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_details_layout);

        Context context = getApplicationContext();
        mDatabase = JournalAppDatabase.getInstance(context);
        mDetailsTitle = findViewById(R.id.diaryEntryDetailTitle);
        mDetailsContent = findViewById(R.id.diaryEntryDetailContent);

        mHelper = Helper.getInstance();
        Intent intent = getIntent();
        handleIntent(intent);
        handleFABClick();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (mHelper.getTitle() != null && mHelper.getContent() != null) {
            mDetailsContent.setText(mHelper.getContent());
            mDetailsTitle.setText(mHelper.getTitle());
        }
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_DIARY_DETAIL)) {
            if (mEntryId == DEFAULT_ID) {
                mEntryId = intent.getIntExtra(EXTRA_DIARY_DETAIL, DEFAULT_ID);

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

    private void updateUI(DiaryEntry diaryEntry) {
        if (diaryEntry == null) {
            return;
        }
        mDetailsTitle.setText(diaryEntry.getEntry());
        mDetailsContent.setText(diaryEntry.getContent());
    }

    private AddDiaryEntryViewModel viewModel() {
        AddDiaryEntryViewModelFactory factory = new AddDiaryEntryViewModelFactory(mDatabase, mEntryId);
        return ViewModelProviders.of(this, factory).get(AddDiaryEntryViewModel.class);
    }

    private void handleFABClick() {
        FloatingActionButton fab = findViewById(R.id.edit_entry_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DiaryEntryDetailsEntry.this, DiaryEntryActivity.class);
                intent.putExtra(DiaryEntryActivity.EXTRA_ENTRY_ID, mEntryId);
                startActivity(intent);
            }
        });
    }

}
