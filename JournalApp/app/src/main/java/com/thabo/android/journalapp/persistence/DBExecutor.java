package com.thabo.android.journalapp.persistence;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This class allows for making database calls outside the UI thread.
 * Only one instance of it will be required and will therefore be a
 * lazy singleton.
 */
public class DBExecutor {
    private static DBExecutor sInstance;
    private Executor diskIO;
    private final static Object LOCK = new Object();

    private DBExecutor(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static DBExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new DBExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    /**
     * This executor will be used for making database calls in threads separate
     * from the main thread.
     * @return
     */
    public Executor getDiskIO() {
        return diskIO;
    }
}
