package com.katdmy.android.unocount;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ScoreRepository {

    private ScoreDao mScoreDao;
    private MutableLiveData<ScoreMatrixCursor> mScoreMatrixCursor = new MutableLiveData<>();
    private LiveData<List<String>> mActivePlayers;
    private int mPlayerCount;

    ScoreRepository(Application application) {
        ScoreDatabase db = ScoreDatabase.getDatabase(application);
        mScoreDao = db.scoreDao();
        mActivePlayers = mScoreDao.getActivePlayers();

        AsyncTask.execute(() -> {
            mPlayerCount = mScoreDao.getPlayerCount();

            String[] players = new String[mPlayerCount];
            for (int i = 0; i < mPlayerCount; i++)
                players[i] = "player" + i;
            ScoreMatrixCursor cursor = new ScoreMatrixCursor(players);
            mScoreMatrixCursor.postValue(cursor);
        });
    }

    public LiveData<ScoreMatrixCursor> getCursor() {
        return mScoreMatrixCursor;
    }

    public void deleteAll() {
        String[] players = new String[mPlayerCount];
        for (int i = 0; i < mPlayerCount; i++)
            players[i] = "player" + i;
        mScoreMatrixCursor.postValue(new ScoreMatrixCursor(players));
    }

    public void insert(List<Integer> currentScore) {
        ScoreMatrixCursor cursor = mScoreMatrixCursor.getValue();
        cursor.addRow(currentScore);
        mScoreMatrixCursor.setValue(cursor);
    }

    public LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }
}
