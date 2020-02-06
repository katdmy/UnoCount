package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreRepository {

    private ScoreDao mScoreDao;
    private ScoreMatrixCursor mScoreMatrixCursor;
    private LiveData<List<String>> mActivePlayers;
    private LiveData<List<Integer>> mTotal;

    ScoreRepository(Application application) {
        ScoreDatabase db = ScoreDatabase.getDatabase(application);
        mScoreDao = db.scoreDao();
        mActivePlayers = mScoreDao.getActivePlayers();

        String[] players = new String[2];
        for (int i = 0; i < 2; i++)
            players[i] = "player" + i;
        mScoreMatrixCursor = new ScoreMatrixCursor(players);
        mTotal = mScoreMatrixCursor.getTotal();
    }

    public List<Integer> getRound(int roundNumber) {
        return mScoreMatrixCursor.getRound(roundNumber);
    }

//    public void deleteAll() {
//        mScoreDao.deleteAll();
//    }

    public void insert(List<Integer> currentScore) {
        mScoreMatrixCursor.addRow(currentScore);
    }

    public LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }

    public LiveData<List<Integer>> getTotal() {
        return mTotal;
    }
}
