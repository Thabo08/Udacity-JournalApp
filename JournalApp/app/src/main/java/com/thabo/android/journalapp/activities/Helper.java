package com.thabo.android.journalapp.activities;

import com.thabo.android.journalapp.persistence.DiaryEntry;

public class Helper {

    private static Helper sInstance;
    private DiaryEntry diaryEntry;
    private static final Object LOCK = new Object();


    public static Helper getInstance() {
        synchronized (LOCK) {
            if (sInstance == null) {
                sInstance = new Helper();
            }
        }
        return sInstance;
    }

    private Helper() {}

    public String getTitle() {
        if (diaryEntry != null) {
            return diaryEntry.getEntry();
        }
        return "";
    }

    public String getContent() {
        if (diaryEntry != null) {
            return diaryEntry.getContent();
        }
        return "";
    }


    public void attachDairyEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
    }
}
