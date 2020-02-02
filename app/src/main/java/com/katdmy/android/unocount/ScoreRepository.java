package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreRepository {

    private ScoreDao mScoreDao;
    private LiveData<List<Score>> mScore;
    private LiveData<Score> mTotal;
    private LiveData<List<String>> mActivePlayers;

    ScoreRepository(Application application) {
        ScoreDatabase db = ScoreDatabase.getDatabase(application);
        mScoreDao = db.scoreDao();
        mScore = mScoreDao.getAll();
        mTotal = mScoreDao.getTotal();
        mActivePlayers = mScoreDao.getActivePlayers();
    }

    LiveData<List<Score>> getScore() {
        return mScore;
    }

    LiveData<Score> getTotal() {
        return mTotal;
    }

    void deleteAll() {
        mScoreDao.deleteAll();
    }

    void insert(final Score score) {
        ScoreDatabase.databaseWriteExecutor.execute(() ->
                mScoreDao.insertRound(score.getPlayer1(),
                        score.getPlayer2(),
                        score.getPlayer3(),
                        score.getPlayer4(),
                        score.getPlayer5())
        );
    }

    public LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }
}
