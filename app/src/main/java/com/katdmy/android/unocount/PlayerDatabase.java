package com.katdmy.android.unocount;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class}, version = 1, exportSchema = false)
abstract class PlayerDatabase extends RoomDatabase {

    abstract PlayerDao playerDao();

    private static volatile PlayerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PlayerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlayerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, PlayerDatabase.class, "score_db")
//                            .addCallback(mScoreDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
