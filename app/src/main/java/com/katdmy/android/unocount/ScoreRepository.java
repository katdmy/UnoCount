package com.katdmy.android.unocount;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ScoreRepository {

    private ScoreDao mScoreDao;
    private LiveData<List<Score>> mScore;
    private LiveData<List<String>> mActivePlayers;
    private LiveData<List<Integer>> mTotal;

    ScoreRepository(Application application) {
        ScoreDatabase db = ScoreDatabase.getDatabase(application);
        mScoreDao = db.scoreDao();
        mScore = mScoreDao.getAll();
        mActivePlayers = mScoreDao.getActivePlayers();
        mTotal = calculateTotal();
    }

    LiveData<List<Score>> getScore() {
        return mScore;
    }

    void deleteAll() {
        mScoreDao.deleteAll();
    }

    void insert(Score score) {
        ScoreDatabase.databaseWriteExecutor.execute(() ->
                mScoreDao.insertRound(score.getScoreData())
        );
        mTotal = calculateTotal();
    }

    public LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }

    private LiveData<List<Integer>> calculateTotal() {
//        MutableLiveData<List<Integer>> totalLiveData = new MutableLiveData<>();
//
//        List<String> activePlayers = mActivePlayers.getValue();
//        if (activePlayers != null) {
//            int playerCount = activePlayers.size();
//
//            List<Integer> total = new ArrayList<>();
//            for(int i = 0; i < playerCount; i++)
//                total.add(0);
//
//            List<Score> scores = mScore.getValue();
//            if (scores.size() > 0) {
//                for (int i = 0; i < scores.size(); i++) {
//                    List<Integer> currentRound = StringUtil.splitToIntList(scores.get(i).getScoreData());
//                    for (int j = 0; j < playerCount; j++)
//                        total.set(j, total.get(j) + currentRound.get(j));
//                }
//            }
//            totalLiveData.setValue(total);
//        }
//        return totalLiveData;

        MutableLiveData<List<Integer>> totalLiveData = new MutableLiveData<>();

        List<Score> scores = mScore.getValue();
        if (scores != null) {
            int playerCount = StringUtil.splitToIntList(scores.get(0).getScoreData()).size();

            List<Integer> total = new ArrayList<>();
            for (int i = 0; i < playerCount; i++)
                total.add(0);

            for (int i = 0; i < scores.size(); i++) {
                List<Integer> currentRound = StringUtil.splitToIntList(scores.get(i).getScoreData());
                for (int j = 0; j < playerCount; j++)
                    total.set(j, total.get(j) + currentRound.get(j));
            }
            totalLiveData.setValue(total);
        }
        return totalLiveData;
    }

    public LiveData<List<Integer>> getTotal() {
        return mTotal;
    }
}
