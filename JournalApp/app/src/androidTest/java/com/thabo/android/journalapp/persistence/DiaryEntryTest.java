package com.thabo.android.journalapp.persistence;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DiaryEntryTest {

    private DiaryEntry diaryEntry;
    private int entryId = 1;
    private String entry = "This is a diary entry";
    private Date entryDate = new Date();
    private String content = "Content";

    @Test
    public void testDiaryEntryValuesAreAsInjectedForDBConstructor() {

        diaryEntry = new DiaryEntry(entryId, content, entry, entryDate);

        assertEquals(entryId, diaryEntry.getEntryId());
        assertEquals(entry, diaryEntry.getEntry());
        assertEquals(content, diaryEntry.getContent());
        assertEquals(entryDate, diaryEntry.getEntryDate());
    }

    @Test
    public void testDiaryEntryValuesAreAsInjected() {

        diaryEntry = new DiaryEntry(content, entry, entryDate);

        assertEquals(entry, diaryEntry.getEntry());
        assertEquals(entryDate, diaryEntry.getEntryDate());
    }

}