package com.thabo.android.journalapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.thabo.android.journalapp.R;
import com.thabo.android.journalapp.persistence.DBExecutor;
import com.thabo.android.journalapp.persistence.DiaryEntry;
import com.thabo.android.journalapp.persistence.JournalAppDatabase;
import com.thabo.android.journalapp.recyclerviews.DiaryEntryAdapter;
import com.thabo.android.journalapp.viewmodel.DiaryViewModel;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class DiaryActivity extends AppCompatActivity implements DiaryEntryAdapter.DiaryItemClickListener {

    private static final String TAG = DiaryActivity.class.getSimpleName();
    private JournalAppDatabase mDatabase;
    private RecyclerView mEntriesRecyclerView;
    private DiaryEntryAdapter mDiaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        mDatabase = JournalAppDatabase.getInstance(getApplicationContext());
        setUpRecyclerView();
        decorateUI();

        itemTouchHelperForDeletes().attachToRecyclerView(mEntriesRecyclerView);

        handleFABClick();
        extractExistingDiaryEntries();
    }

    @NonNull
    private ItemTouchHelper itemTouchHelperForDeletes() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                DBExecutor instance = DBExecutor.getInstance();
                instance.getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int index = viewHolder.getAdapterPosition();
                        List<DiaryEntry> entries = mDiaryAdapter.getDiaryEntries();
                        mDatabase.diaryEntryDAO().deleteDiaryEntry(entries.get(index));
                        Log.i(TAG, "Diary entry deleted");
                    }
                });
            }
        });
    }

    private void handleFABClick() {
        FloatingActionButton fab = findViewById(R.id.add_entry_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEntryIntent = new Intent(DiaryActivity.this, DiaryEntryActivity.class);
                startActivity(addEntryIntent);
            }
        });
    }

    /**
     * This initialises a recycler view and attaches an adapter to it.
     */
    private void setUpRecyclerView() {
        mEntriesRecyclerView = findViewById(R.id.rv_diaryEntries);
        mEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDiaryAdapter = new DiaryEntryAdapter(this, this);
        mEntriesRecyclerView.setAdapter(mDiaryAdapter);
    }

    private void decorateUI() {
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mEntriesRecyclerView.addItemDecoration(decoration);
    }

    private void extractExistingDiaryEntries() {

        DiaryViewModel model = ViewModelProviders.of(this).get(DiaryViewModel.class);
        model.getDiaryEntries().observe(this, new Observer<List<DiaryEntry>>() {
            @Override
            public void onChanged(@Nullable List<DiaryEntry> diaryEntries) {
                mDiaryAdapter.setDiaryEntries(diaryEntries);
            }
        });
    }

    @Override
    public void onDiaryItemClickListener(int itemId) {

        Intent entryDetailsIntent = new Intent(DiaryActivity.this, DiaryEntryDetailsEntry.class);
        entryDetailsIntent.putExtra(DiaryEntryDetailsEntry.EXTRA_DIARY_DETAIL, itemId);
        startActivity(entryDetailsIntent);

    }
}
