package com.katdmy.android.unocount;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class}, version = 1, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {

    public abstract ScoreDao scoreDao();

    public abstract PlayerDao playerDao();

    private static volatile ScoreDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ScoreDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScoreDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ScoreDatabase.class, "score_db")
//                            .addCallback(mScoreDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    static ScoreDatabase.Callback mScoreDatabaseCallback = new ScoreDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//
//            databaseWriteExecutor.execute(() -> {
//                ScoreDao dao = INSTANCE.scoreDao();
//                dao.deleteAll();
//            });
//        }
//    };
}
