package com.thabo.android.journalapp.persistence;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DBExecutorTest {

    @Test
    public void testDiskIOExecutorExists() {
        DBExecutor executor = DBExecutor.getInstance();
        assertNotNull(executor.getDiskIO());
    }

}