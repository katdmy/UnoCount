package com.katdmy.android.unocount;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

class ScoreRepository {

    private MutableLiveData<ScoreMatrixCursor> mScoreMatrixCursor = new MutableLiveData<>();
    private int mPlayerCount;
    private String LOG_TAG = ScoreRepository.class.getSimpleName();

    ScoreRepository(int playerCount) {
        setPlayerCount(playerCount);
    }

    void setPlayerCount(int playerCount) {
        mPlayerCount = playerCount;

        String[] players = new String[mPlayerCount];
        for (int i = 0; i < mPlayerCount; i++)
            players[i] = "player" + i;
        ScoreMatrixCursor cursor = new ScoreMatrixCursor(players);
        mScoreMatrixCursor.postValue(cursor);
    }

    LiveData<ScoreMatrixCursor> getCursor() {
        return mScoreMatrixCursor;
    }

    void deleteAll() {
        String[] players = new String[mPlayerCount];
        for (int i = 0; i < mPlayerCount; i++)
            players[i] = "player" + i;
        mScoreMatrixCursor.postValue(new ScoreMatrixCursor(players));
    }

    void insert(List<Integer> currentScore) {
        ScoreMatrixCursor cursor = mScoreMatrixCursor.getValue();
        if (cursor != null) {
            cursor.addRow(currentScore);
            mScoreMatrixCursor.setValue(cursor);
        } else {
            Log.e(LOG_TAG, "Error in accessing score storage class");
        }
    }

}
