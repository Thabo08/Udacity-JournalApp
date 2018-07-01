package com.thabo.android.journalapp.persistence;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * This class will do type conversion for date values
 */
public class DateConverter {
    /**
     * This does type conversion for reading from a table.
     * @param timestamp
     * @return Date instance
     */
    @TypeConverter
    public static Date toDate(Long timestamp) {
        if (timestamp != null)
            return new Date(timestamp);
        return null;
    }

    /**
     * This does type conversion for writing to a table.
     * @param date
     * @return Long value from a Date value.
     */
    @TypeConverter
    public static Long toTimestamp(Date date) {
        if (date != null)
            return date.getTime();
        return null;
    }
}
