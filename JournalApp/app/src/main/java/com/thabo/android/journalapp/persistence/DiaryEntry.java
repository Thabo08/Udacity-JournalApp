package com.thabo.android.journalapp.persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.thabo.android.journalapp.persistence.ApplicationConstants.DATE_FORMAT;

/**
 * This class is an {@link android.arch.persistence.room.Entity} and represents
 * a diary entry in the database.
 */


@Entity(tableName = ApplicationConstants.DATABASE_TABLE_NAME)
public class DiaryEntry {

    @PrimaryKey(autoGenerate = true)
    private int entryId;
    private String entry;
    private Date entryDate;
    private String content;

    /* For database where entryId acts as an auto-generated primary key  */
    public DiaryEntry(int entryId, String content, String entry, Date entryDate) {
        this.entryId = entryId;
        this.entry = capitaliseFirstLetter(entry);
        this.entryDate = entryDate;
        this.content = capitaliseFirstLetter(content);
    }

    /**
     * Constructor to create diary entry. It is ignored so it won't be used to
     * create a database entry.
     * @param entry Description of entry
     * @param entryDate Date the entry was created
     */
    @Ignore
    public DiaryEntry(String content, String entry, Date entryDate) {
        this.entry = capitaliseFirstLetter(entry);
        this.entryDate = entryDate;
        this.content = capitaliseFirstLetter(content);
    }

    public int getEntryId() {
        return entryId;
    }

    public String getEntry() {
        return entry;
    }
    public String getContent() {
        return content;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    @Ignore
    public String formattedDate() {
        return ApplicationConstants.FORMATTER.format(entryDate);
    }

    @Ignore
    private String capitaliseFirstLetter(String input) {
        if (input != null)
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        else
            return "";
    }

}
