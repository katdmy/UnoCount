package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreRepository {

    private ScoreDao mScoreDao;
    private LiveData<List<Score>> mScore;
    private LiveData<List<String>> mActivePlayers;

    ScoreRepository(Application application) {
        ScoreDatabase db = ScoreDatabase.getDatabase(application);
        mScoreDao = db.scoreDao();
        mScore = mScoreDao.getAll();
        mActivePlayers = mScoreDao.getActivePlayers();
    }

    LiveData<List<Score>> getScore() {
        return mScore;
    }

    void deleteAll() {
        mScoreDao.deleteAll();
    }

    void insert(Score score) {
        ScoreDatabase.databaseWriteExecutor.execute(() ->
                mScoreDao.insertRound(score.gerScoreData())
        );
    }

    public LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }
}
