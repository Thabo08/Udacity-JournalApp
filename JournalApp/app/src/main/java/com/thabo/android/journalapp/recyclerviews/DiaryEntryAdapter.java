package com.thabo.android.journalapp.recyclerviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thabo.android.journalapp.R;
import com.thabo.android.journalapp.persistence.DiaryEntry;

import java.util.List;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder> {

    private Context mContext;
    private DiaryItemClickListener mDiaryItemClickListener;
    private List<DiaryEntry> mDiaryEntries;


    public DiaryEntryAdapter(Context context, DiaryItemClickListener listener) {
        mContext = context;
        this.mDiaryItemClickListener = listener;
    }

    @NonNull
    @Override
    public DiaryEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.diary_entry_layout,
                viewGroup, false);
        return new DiaryEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryEntryViewHolder diaryEntryViewHolder, int index) {
        DiaryEntry entry = mDiaryEntries.get(index);
        diaryEntryViewHolder.diaryEntryDescriptionView.setText(entry.getEntry());
        diaryEntryViewHolder.diaryEntryUpdatedAtView.setText(entry.formattedDate());
    }

    @Override
    public int getItemCount() {
        return mDiaryEntries == null ? 0 : mDiaryEntries.size();
    }


    public void setDiaryEntries(List<DiaryEntry> entries) {
        mDiaryEntries = entries;
        notifyDataSetChanged();
    }

    public List<DiaryEntry> getDiaryEntries() {
        return mDiaryEntries;
    }

    class DiaryEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView diaryEntryDescriptionView;
        private TextView diaryEntryUpdatedAtView;

        public DiaryEntryViewHolder(@NonNull View view) {
            super(view);

            diaryEntryDescriptionView = view.findViewById(R.id.diaryEntryDescription);
            diaryEntryUpdatedAtView = view.findViewById(R.id.entryUpdatedAt);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int entryId = mDiaryEntries.get(getAdapterPosition()).getEntryId();
            mDiaryItemClickListener.onDiaryItemClickListener(entryId);
        }
    }

    public interface DiaryItemClickListener {
        void onDiaryItemClickListener(int itemId);
    }
}
