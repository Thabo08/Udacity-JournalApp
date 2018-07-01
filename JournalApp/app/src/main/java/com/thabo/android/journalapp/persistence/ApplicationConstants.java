package com.thabo.android.journalapp.persistence;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * This just holds static constant values for the database.
 */
public class ApplicationConstants {

    public final static String DATABASE_TABLE_NAME = "DiaryEntries";
    public final static String DATABASE_TABLE_NAME_DETAILED = "DetailedDiaryEntries";
    public final static String DB_NAME = "JournalAppDB";


    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = String.format("%s HH:mm", DATE_FORMAT);
    public static SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
}
