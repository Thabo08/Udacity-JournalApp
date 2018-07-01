package com.thabo.android.journalapp.persistence;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateConverterTest {

    @Test
    public void testTypeConversionIsAsExpected() {
        assertTrue(DateConverter.toDate((long) 1655145) instanceof Date);
        assertTrue(DateConverter.toTimestamp(new Date()) instanceof Long);
    }

}